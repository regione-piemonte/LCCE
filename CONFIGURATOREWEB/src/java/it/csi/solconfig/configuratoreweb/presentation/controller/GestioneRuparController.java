/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FaqRuparDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.RuparService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.CredenzialiRuparModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RichiestaCredenzialiView;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Controller
@Scope("prototype")
public class GestioneRuparController extends BaseController {
	
	@Autowired
	RuparService ruparService;
	
protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
	
	@RequestMapping(value = "/ricercaRupar", method = RequestMethod.POST)
	public ModelAndView ricercaRupar(@ModelAttribute("rupar") CredenzialiRuparModel ruparModel,
			ModelAndView mav) throws Exception {
		
		
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
	
		try {
			log.info(" -- Ricerca richieste credenziali rupar -- ");
			data= getData();
			mav.setViewName(ConstantsWebApp.GESTIONE_RUPAR);

			if (validateRicercaRupar(ruparModel, messaggiErrore)) return mav;

			PaginaDTO<RichiestaCredenzialiView> paginaDTO = ruparService.ricercaRichiesteRupar(ruparModel, data);

				if (paginaDTO.getElementiTotali() == 0) {
					log.error("WARNING: Nessuna richiesta trovata");
					messaggiErrore.add(ruparService.aggiungiErrori(ConstantsWebApp.RICHIESTA_NON_TROVATA));
					mav.setViewName(ConstantsWebApp.GESTIONE_RUPAR);
					return mav;
				}
			
				mav.addObject("paginaRupar", paginaDTO);
				mav.addObject("rupar", ruparModel);
			
				/*
				LogAudit
			 		*/
			    // Scrittura log Audit
				ruparService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_RICERCA_RUPAR, null, 
		        		UUID.randomUUID().toString(), null, ConstantsWebApp.RICERCA_RUPAR, getData());
				updateData(data);
		
	} catch (Exception e) {
			log.error("ERROR: rupar - " , e);
			messaggiErrore.add(ruparService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);
		
	} finally {
		
			mav.addObject("errori", messaggiErrore);
		
	}
		return mav;
	
	}

	private boolean validateRicercaRupar(CredenzialiRuparModel ruparModel, List<MessaggiUtenteDto> messaggiErrore) throws Exception {
		boolean error = false;

		if(!validitaCodiceFiscale(ruparModel.getCfUtente())){
			log.error("WARNING: Codice Fiscale ricerca rupar non corretto  " + ruparModel.getCfUtente());
			messaggiErrore.add(ruparService.aggiungiErrori((ConstantsWebApp.CODICE_FISCALE_ERRATO), ruparModel.getCfUtente()));
			return true;
		}
		if(!validitaCodiceFiscale(ruparModel.getCfOperatore())){
			log.error("WARNING: Codice Fiscale ricerca rupar non corretto " + ruparModel.getCfOperatore());
			messaggiErrore.add(ruparService.aggiungiErrori((ConstantsWebApp.CODICE_FISCALE_ERRATO), ruparModel.getCfOperatore()));
			return true;
		}


		//Visualizzare MSGCONF032 DATA_NON_VALIDA
		if(!controlloFormatoData(ruparModel.getDataRichiestaDa()) || !controlloFormatoData(ruparModel.getDataRichiestaA())
			|| !checkOrdineData(ruparModel.getDataRichiestaDa(), ruparModel.getDataRichiestaA())) {
			log.error("WARNING: Data non valida");
			messaggiErrore.add(ruparService.aggiungiErrori(ConstantsWebApp.DATA_NON_VALIDA));
			error = true;
		}


		return error;
	}


	@RequestMapping(value = "/annullaRicercaRichieste", method = RequestMethod.GET)
	public ModelAndView rupar(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.GESTIONE_RUPAR);
			mav.addObject("rupar", new CredenzialiRuparModel());
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		return mav;
	}
	

	@RequestMapping(value = "/downloadFaq", method = RequestMethod.GET)
	public void downloadFaq(ModelAndView mav,HttpServletResponse response) {
		
		

		try {
			FaqRuparDto faqRupar=ruparService.downloadFaq();
			byte[] faq = null;
			File reportOut = null;
			String fileName =  "Faq_Rupar.pdf";
			faq=faqRupar.getValore();
			reportOut = File.createTempFile("Faq_Rupar",".pdf");
			FileUtils.writeByteArrayToFile(reportOut, faq);
			FileUtils.copyFile(reportOut, response.getOutputStream());
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=\"Faq_Rupar.pdf\"");
			response.flushBuffer();
			
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		
		
	}
	
	
	 public boolean controlloFormatoData(String data) throws ParseException   {
		 
		if(data!= null && !data.isEmpty()) {
			
			String dateRegex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
			
				if(data.matches(dateRegex)){
					try{
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						sdf.parse(data);  
						return true;
					} catch(ParseException e){
						return false;
					} 
				}return false;
		} 
		return true;
	}
	 
	 public boolean checkOrdineData(String dataDa, String dataA) {
		if((dataDa != null && !dataDa.isEmpty()) && (dataA!=null && !dataA.isEmpty())) {
			
			Timestamp dataInizio = Utils.toTimestampFromStringDDMMYYYY(dataDa);
			Timestamp dataFine = Utils.toTimestampFromStringDDMMYYYY(dataA);
			if ((dataInizio !=null && dataFine !=null)
					&& (dataFine.before(dataInizio))) return false;
		}
		return true;
	 }
	 
	  //metodo che controlla la validita del cf inserito dall'utente preso dalla classe CodiceFiscale e adattato
	    private boolean validitaCodiceFiscale(String codice) {
		 
		 final String CF_PATTERN = "^(?:[A-Z][AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$";

	        if (codice != null && !codice.isEmpty()) {
	            codice = codice.toUpperCase();
	            return codice.matches(CF_PATTERN) && checkUltimaLettera(codice);
	        }

	        return true;
	        
	    }
	    
	    private boolean checkUltimaLettera(String cf) {

	        if ("".equals(cf) || cf == null || cf.length() != 16)
	            return false;

	        cf = cf.toUpperCase();

	        String validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	        for (int i = 0; i < 16; i++) {
	            if (validi.indexOf(cf.charAt(i)) == -1)
	                return false;
	        }

	        String set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";

	        int s = 0;

	        for (int k = 1; k <= 13; k += 2)
	            s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(k))));

	        for (int l = 0; l <= 14; l += 2)
	            s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(l))));


	        return s % 26 == (int) cf.charAt(15) - (int) "A".charAt(0);

	    } 
	
}


