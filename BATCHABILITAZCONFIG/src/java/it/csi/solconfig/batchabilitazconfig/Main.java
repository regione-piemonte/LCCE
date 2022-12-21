/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.csi.solconfig.batchabilitazconfig.dto.RichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.dto.UtenteRichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.exception.ConnectionException;
import it.csi.solconfig.batchabilitazconfig.facade.CSVRichiestaFacade;
import it.csi.solconfig.batchabilitazconfig.facade.InvioMailFacade;
import it.csi.solconfig.batchabilitazconfig.facade.RichiestaMassivaFacade;
import it.csi.solconfig.batchabilitazconfig.logger.BatchLoggerFactory;
import it.csi.solconfig.batchabilitazconfig.util.Checker;

public class Main {

	private RichiestaMassivaFacade richiestaMassivaFacade;
	private InvioMailFacade invioMailFacade;
	private CSVRichiestaFacade csvRichiestaFacade;

	public static void main(String[] args) {
		new Main().run();

	}

	/**
	 * Crea la connessione a db e inizializza i facade
	 * 
	 * @throws ConnectionException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void init() throws ConnectionException {
		try {
			BatchLoggerFactory.getLogger(this).info("--- CONNESSIONE AL DATABASE IN CORSO ---");
			richiestaMassivaFacade = new RichiestaMassivaFacade();
			invioMailFacade = new InvioMailFacade();
			csvRichiestaFacade = new CSVRichiestaFacade();
			BatchLoggerFactory.getLogger(this).info("--- CONNESSIONE EFFETTUATA ---");
					
		} catch (SQLException e) {
			throw new ConnectionException("Database connection fail: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ConnectionException("Database connection fail: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() {
//		String ipAddress;
//		try {
//			ipAddress = InetAddress.getLocalHost().getHostAddress();
//		} catch (UnknownHostException e1) {
//			ipAddress=null;
//			e1.printStackTrace();
//		}
		
		// audit params
//		final String idApp = "SOLCONFIG";
//		final String utente = "SOLCONFIG";
//		final String operazione = "read";
//		final String nomeServizio = "batchAbilitazConfig"; // OGG_OPER
//		final String keyOper = null;
//		final String idRequest = null;
//		final String xCodServizio = null;
		
		try {
			
			// Stabilisco la connessione con il Database
			BatchLoggerFactory.getLogger(this).info("--- AVVIO BATCH ---");
			init();

			// Cerco ed elaboro richieste
			BatchLoggerFactory.getLogger(this).info("--- RICERCA RICHIESTE ABILITAZIONE/DISABILITAZIONE MASSIVA DA ELABORARE ---");
			List<RichiestaMassiva> richiesteMassive = richiestaMassivaFacade.ricercaRichiesteMassive();
			
			if(!Checker.isEmptyList(richiesteMassive)) {
				
				// Invio mail ad AURA per le abilitazioni/disabilitazioni da notificare
				for(RichiestaMassiva richiesta : richiesteMassive) {
					if(richiesta.getIsInvioMailAura())
						invioMailFacade.invioMailConfAdAura(richiesta);
				}
				
				// Invio mail a utenti correttamente abilitati/disabilitati
				List<Long> utentiIds = filtraUtentiSenzaErrori(richiesteMassive);
				for(Long idUtente : utentiIds) {
					BatchLoggerFactory.getLogger(this).info("--- INVIO MAIL A UTENTE ID: "+ idUtente +" ---");
					invioMailFacade.invioMailAUtenteProfilato(idUtente, richiesteMassive);
				}
				
				// creo file CSV per ogni richiesta elaborata
				BatchLoggerFactory.getLogger(this).info("--- CREO FILE CSV PER OGNI RICHIESTA ELABORATA ---");
				csvRichiestaFacade.creaFileCsv(richiesteMassive);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			String msg = null;
//			try {
//				if(e instanceof BatchException)
//					msg = ((BatchException) e).getMsg();
//				else msg = dao.getParametro(Constants.ERROR_CODE_INTERNO_SERVER);
				
//				BatchLoggerFactory.getLogger(this).error("--- ERRORE GENERICO: " + msg);
				// log error
//				dao.insertAudit(idApp, ipAddress, utente, utenteDel, utenteBen, operazione,
//						nomeServizio + " - response", idRequest, xCodServizio, "KO - " + msg);

//			} catch (SQLException e2) {
//				BatchLoggerFactory.getLogger(this).error("--- ERRORE GENERICO: " + e2.getMessage());
//				e2.printStackTrace();
//			}

			System.exit(100);
		} finally {
			richiestaMassivaFacade.closeDao();
			invioMailFacade.closeDao();
			csvRichiestaFacade.closeDao();
			BatchLoggerFactory.getLogger(this).info("--- END BATCH ---");
		}
	}

	private List<Long> filtraUtentiSenzaErrori(List<RichiestaMassiva> richiesteMassive) {
		List<Long> utentiIds = new ArrayList<Long>();
		for(RichiestaMassiva richiesta : richiesteMassive) {
			List<Long> collect = richiesta.getListaUtenti().stream()
										.filter(u -> u.getErroreInterno() == null && u.getIdErroreUtente() == null)
										.map(UtenteRichiestaMassiva::getIdUtente).collect(Collectors.toList());
			utentiIds.addAll(collect);
		}
		utentiIds = utentiIds.stream().distinct().collect(Collectors.toList());
		return utentiIds;
	}

}

