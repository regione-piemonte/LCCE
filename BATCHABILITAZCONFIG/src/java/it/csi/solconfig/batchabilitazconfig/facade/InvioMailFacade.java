/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.facade;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.csi.solconfig.batchabilitazconfig.dao.InvioMailDAO;
import it.csi.solconfig.batchabilitazconfig.dto.Applicazione;
import it.csi.solconfig.batchabilitazconfig.dto.Collocazione;
import it.csi.solconfig.batchabilitazconfig.dto.Funzionalita;
import it.csi.solconfig.batchabilitazconfig.dto.Mail;
import it.csi.solconfig.batchabilitazconfig.dto.RichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.dto.Utente;
import it.csi.solconfig.batchabilitazconfig.dto.UtenteRichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.logger.BatchLoggerFactory;
import it.csi.solconfig.batchabilitazconfig.util.Checker;
import it.csi.solconfig.batchabilitazconfig.util.Constants;
import it.csi.solconfig.batchabilitazconfig.util.JavaMailUtil;

public class InvioMailFacade {
	
	private InvioMailDAO dao;
	
	public InvioMailFacade() throws ClassNotFoundException, SQLException {
		this.dao = new InvioMailDAO();
	}

	public InvioMailDAO getDao() {
		return dao;
	}

	public void setDao(InvioMailDAO dao) {
		this.dao = dao;
	}
	
	public void closeDao() {
		dao.closeAll();
	}
	
	 public void invioMailAUtenteProfilato(Long idUtente, List<RichiestaMassiva> richiesteMassive) {
		 try {
		 	BatchLoggerFactory.getLogger(this).info("--- RECUPERO INDIRIZZO EMAIL DELL'UTENTE ID: "+ idUtente +" ---");
		 	String emailUtente = dao.findEmailUtente(idUtente);
		 	
	        if (emailUtente != null) {
	        	Set<Applicazione> abilitazioniAura = new HashSet<Applicazione>();
	        	Set<Applicazione> disabilitazioniAura = new HashSet<Applicazione>();
	        	for(RichiestaMassiva richiesta : richiesteMassive) {
	        		if(richiesta.getIsInvioMailAura()) {
	        			Applicazione app = dao.findApplicazione(richiesta.getListaUtenti().get(0).getIdAbilitazione());
	        			if(richiesta.isDisabilitazione()) disabilitazioniAura.add(app);
	        			else abilitazioniAura.add(app);
	        		}
	        	}
		            
	        	// Recuper oggetto e testo mail da db
	            Mail mailConf = getConfigurazionMail();
	
	            if(!Checker.isEmpty(mailConf.getSubject()) && !Checker.isEmpty(mailConf.getBody())) {
	            	BatchLoggerFactory.getLogger(this).info("--- PREPARO CORPO MAIL ---");
		            String corpoMail = preparaCorpoMail(idUtente, mailConf, abilitazioniAura, disabilitazioniAura);
					
		            JavaMailUtil javaMailUtil = new JavaMailUtil();
					javaMailUtil.sendSimpleMailMessage(emailUtente,	mailConf.getSubject(), corpoMail);
					BatchLoggerFactory.getLogger(this).info("--- INVIO MAIL A UTENTE ID: "+ idUtente +" AVVENUTO CON SUCCESSO ---");
	            }
	        } else {
	        	BatchLoggerFactory.getLogger(this).info("--- INDIRIZZO EMAIL PER UTENTE ID: "+ idUtente +" NON TROVATO ---");
	        }
		 } catch (Exception e) {
			 BatchLoggerFactory.getLogger(this).info("--- ERRORE DURANTE INVIO MAIL A UTENTE ID: "+ idUtente +" ---");
		 }
    }

	private String preparaCorpoMail(Long idUtente, Mail mailConf, Set<Applicazione> abilitazioniAura, Set<Applicazione> disabilitazioniAura) throws SQLException {
		List<Collocazione> collocazioniUtente = dao.findCollocazioniUtente(idUtente);
		List<String> idCollocazioniUtente = collocazioniUtente.stream().map(Collocazione::getId).map(Object::toString).collect(Collectors.toList());
		List<String> idRuoliUtente = dao.findRuoliUtente(idUtente).stream().map(Object::toString).collect(Collectors.toList());
		List<String> listaProfili = ricercaServiziOnLineByIdUtenteAndData(idUtente, idCollocazioniUtente, idRuoliUtente);
		
		String solProfiliCollocazioniAbilitateEAttive = preparaTestoAbilitazioni(collocazioniUtente, listaProfili);
		
		String corpoMail = mailConf.getBody() + "\n -  " + solProfiliCollocazioniAbilitateEAttive;
		
		if(!abilitazioniAura.isEmpty() || !disabilitazioniAura.isEmpty()) {
			// aggiunta testo per abilitazioni/disabilitazioni da notificare ad AURA
        	corpoMail += "\n";
        	if(!abilitazioniAura.isEmpty()) {
        		for(Applicazione app : abilitazioniAura)
        			corpoMail += "\n"+mailConf.getTestoMailUserAbil().replace("@APP@", app.getCodice());
        	}
        	if(!disabilitazioniAura.isEmpty()) {
        		for(Applicazione app : disabilitazioniAura)
        		corpoMail += "\n"+mailConf.getTestoMailUserDisabil().replace("@APP@", app.getCodice());
        	}
		}
		
		corpoMail += "\n\n\n\n " + mailConf.getFooter();
		return corpoMail;
	}

	private String preparaTestoAbilitazioni(List<Collocazione> collocazioniUtente, List<String> listaProfili) {
		String solProfiliCollocazioniAbilitateEAttive = listaProfili.stream().map(profiloSol -> {
		    String[] split = profiloSol.split("\\|");
		    String testo;

		    Applicazione applicazioneDto = dao.findApplicazione(Long.valueOf(split[0]));

		    Collocazione collocazioneDto = collocazioniUtente.stream().filter(c -> c.getId().equals(Long.valueOf(split[1]))).findAny().orElse(null); 
		    Funzionalita funzionalitaDto = dao.findFunzionalita(Long.valueOf(split[2])); 

		    testo = applicazioneDto.getDescrizione() + ", " + funzionalitaDto.getDescrizione() + " - "
		            + collocazioneDto.getColCodAzienda() + " " + collocazioneDto.getColDescAzienda();
		    if (!Checker.isEmpty(collocazioneDto.getColCodice()) && !Checker.isEmpty(collocazioneDto.getColDescrizione())) {
		        String codiceStruttura = collocazioneDto.getColCodice();
		        String descrizioneStruttura = collocazioneDto.getColDescrizione();
		        if ((collocazioneDto.getFlagAzienda() == null || !collocazioneDto.getFlagAzienda().equalsIgnoreCase("S")) && !descrizioneStruttura.equals("-"))
		            testo += " - " + codiceStruttura + " " + descrizioneStruttura;
		        else if ((collocazioneDto.getFlagAzienda() == null || !collocazioneDto.getFlagAzienda().equalsIgnoreCase("S")))
		            testo += " - " + codiceStruttura;
		        else if (!descrizioneStruttura.equals("-"))
		            testo += " - " + descrizioneStruttura;
		    }
		    
		    testo += " - data fine validita': ";
            if (split.length == 5 && !split[4].isEmpty()) {
            	testo += split[4];
			} else {
				testo += "senza scadenza";
			}
		    
		    return testo;
		}).collect(Collectors.joining("\n -  "));
		return solProfiliCollocazioniAbilitateEAttive;
	}

    private Mail getConfigurazionMail() throws Exception {
    	Mail mailConf = new Mail();
    	String subject = dao.findConfigurazione(Constants.MAIL_SUBJECT);
    	String body = dao.findConfigurazione(Constants.MAIL_BODY);
    	String footer = dao.findConfigurazione(Constants.MAIL_FOOTER);
    	if(subject != null && body != null && footer != null)
    		mailConf = new Mail(subject,body,footer);
    	
    	mailConf.setEmailAura(dao.findConfigurazione(Constants.CONF_MAIL_EMAIL_AURA));
    	mailConf.setOggettoMailAura(dao.findConfigurazione(Constants.CONF_MAIL_OGGETTO_AURA));
    	mailConf.setTestoPrimaParteMailAura(dao.findConfigurazione(Constants.CONF_MAIL_CORPO_AURA));
    	mailConf.setTestoElencoMailAura(dao.findConfigurazione(Constants.CONF_MAIL_ELENCO_AURA));
    	mailConf.setTestoMailUserAbil(dao.findConfigurazione(Constants.CONF_MAIL_USER_ABI));
    	mailConf.setTestoMailUserDisabil(dao.findConfigurazione(Constants.CONF_MAIL_USER_DISABI));
    	
    	return mailConf;
	}
    
	private List<String> ricercaServiziOnLineByIdUtenteAndData(Long idUtente, List<String> collocazioni, List<String> ruoli) throws SQLException {
        List<String> listaSOL = dao.findAbilitazioniString(idUtente); 

        List<String> newSol = new ArrayList<>();
        if (listaSOL != null && !listaSOL.isEmpty()) {
            for (String sol : listaSOL) {
                try {
                    String[] parts = sol.split("\\|");
                    String collocazione = parts[1];
                    String ruolo = parts[3];

                    if (collocazioni.stream().anyMatch(c -> c.equals(collocazione) || c.equals(collocazione + "ro"))
                    		&& ruoli.stream().anyMatch(r -> r.equals(ruolo) || r.equals(ruolo+"ro"))) {
                        String solNuovo = parts[0] + '|' + parts[1] + '|' + parts[2] + '|' + parts[3];
                        if (parts.length == 5 && !parts[4].isEmpty()) {
                            String dataFineValidita = parts[4];
                            String newDate = dataFineValidita.substring(8, dataFineValidita.indexOf(' ')) + "/"
                                    + dataFineValidita.substring(5, 7) + "/" + dataFineValidita.substring(0, 4);
                            solNuovo = solNuovo + '|' + newDate;
                        }
                        newSol.add(solNuovo);
                    }
                } catch (Exception e) {
                    //
                }
            }
        }
        return newSol;
    }
	
	 public void invioMailConfAdAura(RichiestaMassiva richiestaMassiva) {
		 BatchLoggerFactory.getLogger(this).info("--- INIZIO INVIO EMAIL AD AURA. RICHIESTA ID: "+richiestaMassiva.getId()+" ---");
		 try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Applicazione app = dao.findApplicazione(richiestaMassiva.getListaUtenti().get(0).getIdAbilitazione());
			String azione = richiestaMassiva.isDisabilitazione() ? "disabilitazione" : "abilitazione";
		 	
			BatchLoggerFactory.getLogger(this).info("--- PREPARO EMAIL DA INVIARE AD AURA PER "+ (richiestaMassiva.isDisabilitazione() ? "DISABILITAZIONE" : "ABILITAZIONE") + " " + app.getCodice() +" ---");        	
		 	// Recuper oggetto e testo mail da db
            Mail mailConf = getConfigurazionMail();
	        
            for (UtenteRichiestaMassiva utente : richiestaMassiva.getListaUtenti()) {
            	if(utente.getErroreInterno() == null && utente.getIdErroreUtente() == null) {
	            	Utente utenteDto = dao.findUtente(utente.getIdUtente());
	            	Funzionalita profilo = dao.findFunzionalita(utente.getIdProfilo());
	            	
	            	String oggettoAura = mailConf.getOggettoMailAura();
	            	oggettoAura = oggettoAura.replace("@AZIONE@", azione);
	        		
	        		String testoAura = mailConf.getTestoPrimaParteMailAura();
	        		testoAura = testoAura.replace("@AZIONE@", azione)
	        							 .replace("@CODICE_FISCALE@", utenteDto.getCodiceFiscale())
	        							 .replace("@NOME@", utenteDto.getNome())
	        							 .replace("@COGNOME@", utenteDto.getCognome())
	        							 .replace("@EMAIL,TELEFONO@", utenteDto.getEmail() + 
	        									 (Checker.isEmpty(utenteDto.getTelefono()) ? "" : ", "+utenteDto.getTelefono()) );
	        		
	        		String elencoAura = "\n -  " + mailConf.getTestoElencoMailAura();
	        		elencoAura = elencoAura.replace("@COD_RUOLO@ - @DESC_RUOLO@", dao.findDescRuolo(utente.getIdRuolo()))
	    					  .replace("@COL_CODICE@ - @COL_DESC@", dao.findDescCollocazione(utente.getIdCollocazione()))
	    					  .replace("@COD_AZIENDA@ - @DESC_AZIENDA@", dao.findDescAzienda(utente.getIdCollocazione()))
	    					  .replace("@APP@", app.getCodice())
	    					  .replace("@COD_PROF@", profilo.getCodice())
	    					  .replace("@DESC_PROF@", profilo.getDescrizione())
	    					  .replace("@DATA_FINE_VAL@", !richiestaMassiva.isDisabilitazione() ? "senza scadenza" : sdf.format(new Date()));
		        	
	        		testoAura += elencoAura + "\n\n\n\n " + mailConf.getFooter();
		
		            if(!Checker.isEmpty(mailConf.getSubject()) && !Checker.isEmpty(mailConf.getBody())) {
		            	BatchLoggerFactory.getLogger(this).info("--- PREPARO CORPO MAIL ---");
						
			            JavaMailUtil javaMailUtil = new JavaMailUtil();
			            javaMailUtil.sendSimpleMailMessage(mailConf.getEmailAura(), oggettoAura, testoAura);
						BatchLoggerFactory.getLogger(this).info("--- INVIO MAIL AD AURA PER "+ (richiestaMassiva.isDisabilitazione() ? "DISABILITAZIONE" : "ABILITAZIONE") + " " + app.getCodice() +" AVVENUTO CON SUCCESSO ---");
		            }
            	}
	        }
		 } catch (Exception e) {
			 BatchLoggerFactory.getLogger(this).info("--- ERRORE DURANTE INVIO MAIL AD AURA. RICHIESTA ID: "+richiestaMassiva.getId()+" ---");
		 }
    }
		
}
