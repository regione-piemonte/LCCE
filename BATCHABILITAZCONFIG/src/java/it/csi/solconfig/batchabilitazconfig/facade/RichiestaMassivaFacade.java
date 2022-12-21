/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.facade;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import it.csi.solconfig.batchabilitazconfig.dao.RichiestaMassivaDAO;
import it.csi.solconfig.batchabilitazconfig.dto.Abilitazione;
import it.csi.solconfig.batchabilitazconfig.dto.RichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.dto.UtenteRichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.exception.BatchException;
import it.csi.solconfig.batchabilitazconfig.logger.BatchLoggerFactory;
import it.csi.solconfig.batchabilitazconfig.util.Checker;
import it.csi.solconfig.batchabilitazconfig.util.Constants;

public class RichiestaMassivaFacade {
	
	private RichiestaMassivaDAO dao;
	
	public RichiestaMassivaFacade() throws ClassNotFoundException, SQLException {
		this.dao = new RichiestaMassivaDAO();
	}

	public RichiestaMassivaDAO getDao() {
		return dao;
	}

	public void setDao(RichiestaMassivaDAO dao) {
		this.dao = dao;
	}
	
	public void closeDao() {
		dao.closeAll();
	}
	
	public List<RichiestaMassiva> ricercaRichiesteMassive() throws SQLException, BatchException {
		// Cerco richieste abilitazione o disabilitazione massiva da elaborare
		List<RichiestaMassiva> richiesteMassive = dao.findRichiesteMassive();
		
		if(richiesteMassive != null && !richiesteMassive.isEmpty()) {
			BatchLoggerFactory.getLogger(this).info("--- "+ richiesteMassive.size() +" RICHIESTE MASSIVE TROVATE ---");
			for(RichiestaMassiva richiesta : richiesteMassive) {
				elaboraRichiesta(richiesta);
			}
		} else {
			BatchLoggerFactory.getLogger(this).info("--- NESSUNA RICHIESTA MASSIVE TROVATA ---");
		}
		return richiesteMassive;
	}

	private void elaboraRichiesta(RichiestaMassiva richiesta) throws SQLException, BatchException {
		BatchLoggerFactory.getLogger(this).info("--- RECUPERO UTENTI RICHIESTA "+ (richiesta.isDisabilitazione() ? "DISABILITAZIONE" : "ABILITAZIONE") +" MASSIVA ID: "+ richiesta.getId() +" ---");
		// Recupero utenti richiesta abilitazione o disabilitazione massiva
		richiesta.setListaUtenti(dao.findUtentiRichiestaMassiva(richiesta.getId()));
		
		if(!Checker.isEmptyList(richiesta.getListaUtenti())) {
			checkStartRichiesta(richiesta);

			// verifico se l'abilitazione/disabilitazione richiesta debba essere notificata ad AURA
			richiesta.setIsInvioMailAura(dao.checkApplicazioneConInvioMailAura(richiesta.getListaUtenti().get(0).getIdAbilitazione()));
			
			for(UtenteRichiestaMassiva utente : richiesta.getListaUtenti()) {
				checkStartUtenteRichiesta(utente);
				
				BatchLoggerFactory.getLogger(this).info("--- CERCO ABILITAZIONE ATTIVA PER UTENTE RICHIESTA ID: "+ utente.getId() +" ---");
				// cerco abilitazione attiva per utente, ruolo, collocazione, applicazione e profilo
				Long funzTreeId = dao.findFunzTreeId(utente.getIdProfilo());
				Abilitazione abilitazioneAttiva = dao.findAbilitazioneAttiva(utente.getIdUtente(), 
						utente.getIdRuolo(), utente.getIdCollocazione(), utente.getIdAbilitazione(), funzTreeId);
				
				if(!richiesta.isDisabilitazione()) {
					// ABILITAZIONE MASSIVA
					elaboraAbilitazioneMassiva(utente, abilitazioneAttiva);
				} else {
					// DISABILITAZIONE MASSIVA
					elaboraDisabilitazioneMassiva(utente, abilitazioneAttiva);
				}
				
				BatchLoggerFactory.getLogger(this).info("--- TERMINO UTENTE RICHIESTA ID: "+ utente.getId() +" INSERENDO DATA_FINE ---");
				dao.endUtenteRichiesta(utente);
			}
			
		}
		BatchLoggerFactory.getLogger(this).info("--- TERMINO RICHIESTA ID: "+ richiesta.getId() +" INSERENDO DATA_FINE ED ESITO ---");
		String esito = richiesta.getListaUtenti().stream().anyMatch(u -> 
			!Checker.isEmpty(u.getErroreInterno()) || u.getIdErroreUtente() != null) 
				? Constants.BATCH_STATO_COMPLETATO_CON_ERRORI : Constants.BATCH_STATO_COMPLETATO;
		dao.endRichiesta(richiesta.getId(), esito);
	}

	private void elaboraDisabilitazioneMassiva(UtenteRichiestaMassiva utente, Abilitazione abilitazioneAttiva) throws BatchException {
		if(abilitazioneAttiva != null) {
			//disabilitazione utente
			disabilitaAbilitazione(new Timestamp(System.currentTimeMillis()), utente, abilitazioneAttiva);
		} else {
			BatchLoggerFactory.getLogger(this).info("--- UTENTE ID: "+ utente.getIdUtente() +" NON ABILITATO AL SOL ID: "+ utente.getIdAbilitazione() +" ---");
			//utente scartato && errori?
			setErroreUtenteRichiesta(utente, Constants.ERR_MASS_UTENTE_NON_ABILITATO, null);
			BatchLoggerFactory.getLogger(this).info("--- PROSEGUO CON IL PROSSIMO UTENTE ---");
		}
		utente.setDataFine(new Timestamp(System.currentTimeMillis()));
	}

	private void elaboraAbilitazioneMassiva(UtenteRichiestaMassiva utente, Abilitazione abilitazioneAttiva)	throws SQLException, BatchException {
		if(abilitazioneAttiva != null) {
			BatchLoggerFactory.getLogger(this).info("--- UTENTE ID: "+ utente.getIdUtente() +" GIA' ABILITATO AL SOL ID: "+ utente.getIdAbilitazione() +" ---");
			//utente scartato && errori?
			setErroreUtenteRichiesta(utente, Constants.ERR_MASS_UTENTE_ABILITATO, null);
			BatchLoggerFactory.getLogger(this).info("--- PROSEGUO CON IL PROSSIMO UTENTE RICHIESTA ---");
		} else {
			//abilitazione utente
			inserisciNuovaAbilitazione(utente);
		}
		utente.setDataFine(new Timestamp(System.currentTimeMillis()));
	}

	private void inserisciNuovaAbilitazione(UtenteRichiestaMassiva utente) throws SQLException, BatchException {
		Long idRuoloUtente = dao.findRuoloUtente(utente.getIdUtente(), utente.getIdRuolo());
		Long idCollocazioneUtente = dao.findCollocazioneUtente(utente.getIdUtente(), utente.getIdCollocazione());
		Long funzTreeId = dao.findFunzTreeId(utente.getIdProfilo());
		if(idRuoloUtente != null && idCollocazioneUtente != null && funzTreeId != null) {
			try {
				BatchLoggerFactory.getLogger(this).info("--- INSERISCO ABILITAZIONE ID: "+ utente.getIdAbilitazione() +" PER UTENTE ID: "+ utente.getIdUtente() +" ---");
				dao.insertAbilitazione(idRuoloUtente, idCollocazioneUtente, utente.getIdAbilitazione(), funzTreeId, utente.getDataFineAbilitazione());
				gestisciPreferenze(utente, false, null);
			} catch (SQLException e) {
				BatchLoggerFactory.getLogger(this).info("--- ABILITAZIONE ID: "+ utente.getIdAbilitazione() +" PER UTENTE ID: "+ utente.getIdUtente() +" NON RIUSCITA ---");
				setErroreUtenteRichiesta(utente, Constants.ERR_MASS_GENERICO, e);		
			}
		} else {
			BatchLoggerFactory.getLogger(this).info("--- RUOLO ID: "+utente.getIdRuolo() +
					" O COLLOCAZIONE ID: "+utente.getIdCollocazione()+" O PROFILO ID: "+utente.getIdProfilo()+" NON TROVATI PER UTENTE ID:  "+utente.getIdUtente()+" ---");
			setErroreUtenteRichiesta(utente, Constants.ERR_MASS_GENERICO, null);
		}
	}

	private void disabilitaAbilitazione(Timestamp dataFine, UtenteRichiestaMassiva utente, Abilitazione abilitazione) throws BatchException {
		try {
			BatchLoggerFactory.getLogger(this).info("--- DISABILITO ABILITAZIONE ID: "+ utente.getIdAbilitazione() +" PER UTENTE ID: "+ utente.getIdUtente() +" ---");
			dao.updateAbilitazione(dataFine, abilitazione.getId());
			gestisciPreferenze(utente, true, abilitazione);
		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info("--- DISABILITAZIONE ID: "+ utente.getIdAbilitazione() +" PER UTENTE ID: "+ utente.getIdUtente() +" NON RIUSCITA ---");
			setErroreUtenteRichiesta(utente, Constants.ERR_MASS_GENERICO, e);							
		}
	}

	private void gestisciPreferenze(UtenteRichiestaMassiva utente, boolean isDisabilitazione, Abilitazione abilitazione) throws SQLException {
		Long idPreferenzaRuolo = dao.findPreferenzaRuolo(utente.getIdUtente(), utente.getIdRuolo());
		Long idPreferenzaRuoloCollocazione = dao.findPreferenzaRuoloCollocazione(utente.getIdUtente(), utente.getIdRuolo(), utente.getIdCollocazione());
		Long idPreferenzaApplicazione = dao.findPreferenzaApplicazione(utente.getIdUtente(), utente.getIdAbilitazione());
		
		if(isDisabilitazione) {
			Timestamp dataCancellazione = new Timestamp(System.currentTimeMillis());
			List<Abilitazione> abilByRuolo = dao.findAbilitazioniAttiveByIdRuoloUtente(abilitazione.getIdRuoloUtente());
			if(abilByRuolo.isEmpty()) {
				if(idPreferenzaRuolo != null) 	dao.updatePreferenza(idPreferenzaRuolo, dataCancellazione);
				if(idPreferenzaRuoloCollocazione != null) 	dao.updatePreferenza(idPreferenzaRuoloCollocazione, dataCancellazione);
			} else {
				List<Abilitazione> abiByColl = abilByRuolo.stream().filter(a -> a.getUtecolId().equals(abilitazione.getUtecolId())).collect(Collectors.toList());
				if(abiByColl.isEmpty()) {
					if(idPreferenzaRuoloCollocazione != null) 	dao.updatePreferenza(idPreferenzaRuoloCollocazione, dataCancellazione);
				}
			}
			
			List<Abilitazione> abilByApp = dao.findAbilitazioniAttiveByUserIdAndIdApp(utente.getIdUtente(), utente.getIdAbilitazione());
			if(abilByApp.isEmpty()) {
				if(idPreferenzaApplicazione != null) 	dao.updatePreferenza(idPreferenzaApplicazione, dataCancellazione);
			}
			
		} else {
			if(idPreferenzaRuolo == null) 				dao.insertPreferenza(utente.getIdUtente(), null, utente.getIdRuolo(), null);
			else 										dao.updatePreferenza(idPreferenzaRuolo, null);
			if(idPreferenzaRuoloCollocazione == null) 	dao.insertPreferenza(utente.getIdUtente(), null, utente.getIdRuolo(), utente.getIdCollocazione());
			else 										dao.updatePreferenza(idPreferenzaRuoloCollocazione, null);
			if(idPreferenzaApplicazione == null) 		dao.insertPreferenza(utente.getIdUtente(), utente.getIdAbilitazione(), null, null);
			else 										dao.updatePreferenza(idPreferenzaApplicazione, null);
		}
	}

	private void checkStartUtenteRichiesta(UtenteRichiestaMassiva utente) throws SQLException {
		if(utente.getDataInizio() == null) {
			BatchLoggerFactory.getLogger(this).info("--- START UTENTE RICHIESTA ID: "+ utente.getId() +" ---");
			// inizio nuova abilitazione utente richiesta
			dao.startUtenteRichiesta(utente.getId());
		} else {
			BatchLoggerFactory.getLogger(this).info("--- RIPRENDO UTENTE RICHIESTA ID: "+ utente.getId() +" ---");
			// riprendo abilitazione utente richiesta gia' iniziata
		}
	}

	private void checkStartRichiesta(RichiestaMassiva richiesta) throws SQLException {
		if(richiesta.getDataInizio() == null) {
			BatchLoggerFactory.getLogger(this).info("--- START RICHIESTA ID: "+ richiesta.getId() +" ---");
			// inizio nuova richiesta
			dao.startRichiesta(richiesta.getId());
		} else {
			BatchLoggerFactory.getLogger(this).info("--- RIPRENDO RICHIESTA ID: "+ richiesta.getId() +" ---");
			// riprendo richiesta gia' iniziata
		}
	}
	
	private void setErroreUtenteRichiesta(UtenteRichiestaMassiva utente, String codErrUtente, Exception ex) throws BatchException  {
		try {
			Long idErrMsg = dao.findMessaggioUtente(codErrUtente);
			if(ex == null) utente.setErroreInterno(dao.findDescErrore(idErrMsg));
			else utente.setErroreInterno(ex.getLocalizedMessage());
			utente.setIdErroreUtente(dao.findMessaggioUtente(codErrUtente));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BatchException(e.getMessage());
		}
	} 
	
}
