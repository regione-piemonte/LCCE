/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.facade;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;



import it.csi.solconfig.batchabilitazconfig.dao.CSVRichiestaDAO;
import it.csi.solconfig.batchabilitazconfig.dto.Applicazione;
import it.csi.solconfig.batchabilitazconfig.dto.Funzionalita;
import it.csi.solconfig.batchabilitazconfig.dto.RichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.dto.Utente;
import it.csi.solconfig.batchabilitazconfig.dto.UtenteRichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.logger.BatchLoggerFactory;
import it.csi.solconfig.batchabilitazconfig.util.Constants;

public class CSVRichiestaFacade {
	
	private CSVRichiestaDAO dao;
	
	public CSVRichiestaFacade() throws ClassNotFoundException, SQLException {
		this.dao = new CSVRichiestaDAO();
	}

	public CSVRichiestaDAO getDao() {
		return dao;
	}

	public void setDao(CSVRichiestaDAO dao) {
		this.dao = dao;
	}
	
	public void closeDao() {
		dao.closeAll();
	}

	public void creaFileCsv(List<RichiestaMassiva> richiesteMassive) throws SQLException {
		for(RichiestaMassiva richiesta : richiesteMassive) {

			BatchLoggerFactory.getLogger(this).info("--- CREO CSV PER RICHIESTE CON ERRORI ---");
			List<UtenteRichiestaMassiva> utentiConErrori = richiesta.getListaUtenti().stream()
					.filter(u -> u.getErroreInterno() != null || u.getIdErroreUtente() != null).collect(Collectors.toList());
			byte[] csvConErrori = elaboraCSV(utentiConErrori, true);
			BatchLoggerFactory.getLogger(this).info("--- SALVO CSV PER RICHIESTE CON ERRORI ---");
			dao.saveCSV(richiesta.getId(), Base64.getEncoder().encode(csvConErrori), true);

			BatchLoggerFactory.getLogger(this).info("--- CREO CSV PER RICHIESTE COMPLETATE ---");
			List<UtenteRichiestaMassiva> utentiSenzaErrori = richiesta.getListaUtenti().stream()
					.filter(u -> u.getErroreInterno() == null && u.getIdErroreUtente() == null).collect(Collectors.toList());
			byte[] csvSenzaErrori = elaboraCSV(utentiSenzaErrori, false);
			BatchLoggerFactory.getLogger(this).info("--- SALVO CSV PER RICHIESTE COMPLETATE ---");
			dao.saveCSV(richiesta.getId(), Base64.getEncoder().encode(csvSenzaErrori), false);
			
		}
		
	}
	
	private byte[] elaboraCSV(List<UtenteRichiestaMassiva> utenti, boolean conErrori) throws SQLException {
		StringBuilder csv = new StringBuilder();	
		addHeaderCsv(conErrori, csv);
		
		for(UtenteRichiestaMassiva utente : utenti) {
			addRowCsv(conErrori, csv, utente);
		}
		return csv.toString().getBytes();
	}

	private void addRowCsv(boolean conErrori, StringBuilder csv, UtenteRichiestaMassiva utente) throws SQLException {
		Utente findUtente = dao.findUtente(utente.getIdUtente());
		csv.append(findUtente.getNome()).append(Constants.CSV_SEPARATOR);
		csv.append(findUtente.getCognome()).append(Constants.CSV_SEPARATOR);
		csv.append(findUtente.getCodiceFiscale()).append(Constants.CSV_SEPARATOR);

		String descRuolo = dao.findDescRuolo(utente.getIdRuolo());
		csv.append(descRuolo).append(Constants.CSV_SEPARATOR);
		
		String descCollocazione = dao.findDescCollocazione(utente.getIdCollocazione());
		csv.append(descCollocazione).append(Constants.CSV_SEPARATOR);
		
		Applicazione applicazione = dao.findApplicazione(utente.getIdAbilitazione());
		csv.append(applicazione.getCodice()).append(Constants.STRING_SEPARATOR)
		   .append(applicazione.getDescrizione()).append(Constants.CSV_SEPARATOR);
		
		Funzionalita funzionalita = dao.findFunzionalita(utente.getIdProfilo());
		csv.append(funzionalita.getCodice()).append(Constants.STRING_SEPARATOR)
		   .append(funzionalita.getDescrizione()).append(Constants.CSV_SEPARATOR);
		
		if(conErrori) {
			String descErrore = dao.findDescErrore(utente.getIdErroreUtente());
			csv.append(descErrore).append(Constants.CSV_SEPARATOR);
		}
		csv.append(Constants.CSV_NEW_LINE);
	}

	private void addHeaderCsv(boolean conErrori, StringBuilder csv) {
		csv.append("nome").append(Constants.CSV_SEPARATOR)
		   .append("cognome").append(Constants.CSV_SEPARATOR)
		   .append("codice fiscale").append(Constants.CSV_SEPARATOR)
		   .append("ruolo").append(Constants.CSV_SEPARATOR)
		   .append("collocazione").append(Constants.CSV_SEPARATOR)
		   .append("sol").append(Constants.CSV_SEPARATOR)
		   .append("profilo").append(Constants.CSV_SEPARATOR);
		if(conErrori)	csv.append("errore_utente").append(Constants.CSV_SEPARATOR);
		csv.append(Constants.CSV_NEW_LINE);
	}
	
	
	

}
