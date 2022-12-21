/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.RuoloCompatibilitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloSelezionabileLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloCompatibilita;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.business.service.InserisciRuoliService;
import it.csi.solconfig.configuratoreweb.business.service.RuoliService;
import it.csi.solconfig.configuratoreweb.business.service.RuoloService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.InserisciRuoloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloModel;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;

@Controller
@Scope("prototype")
public class InserisciRuoliController extends BaseController {
	
	@Autowired
	InserisciRuoliService inserisciRuoliService;

	@Autowired
	RuoliService ruoliService;

	@Autowired
	private CollocazioneService collocazioneService;
	
	@Autowired
	private RuoloService ruoloService;
	
	@Autowired
	private RuoloCompatibilitaLowDao ruoloCompatibilitaLowDao;
	
	@Autowired
	private RuoloSelezionabileLowDao ruoloSelezionabileLowDao;

	
	List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

	
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
	
	
	private List<String> initComboRuoli(ModelAndView mav) {
		List<RuoloDTO> tuttiRuoli = ruoloService.ricercaTuttiRuoli(getData());
		mav.getModel().put("ruoli", tuttiRuoli);
		mav.getModel().put("collocazioniTipo", collocazioneService.getCollocazioniTipo());
		return tuttiRuoli.stream().map(e -> (e.getId() != null ? e.getId().toString() : ""))
				.collect(Collectors.toList());
	}

	
	@RequestMapping(value = "/insertRuoli", method = RequestMethod.POST)
	public ModelAndView insertRuoli(@ModelAttribute("ruolo") RuoloModel inserisciRuoloModel,
			ModelAndView mav) throws Exception {
		
		try {
			data= getData(); 
			
			//
			initComboRuoli(mav);
			
			if((inserisciRuoloModel.getCodice()==null || inserisciRuoloModel.getCodice().isEmpty())
				|| (inserisciRuoloModel.getDescrizione()==null || inserisciRuoloModel.getDescrizione().isEmpty())){
				log.error("WARNING: E' necessario specificare sia il codice che la descrizione");
				messaggiErrore.add(inserisciRuoliService.aggiungiErrori(ConstantsWebApp.CAMPI_NON_VALORIZZATI));
				mav.setViewName(ConstantsWebApp.INSERISCI_RUOLI);
				return mav;
			}
			
			RuoloDto ruoloDto= new RuoloDto();
			
			//l'utente inserisce i parametri 
			ruoloDto.setCodice(inserisciRuoloModel.getCodice().toUpperCase());
			ruoloDto.setDescrizione(inserisciRuoloModel.getDescrizione());
			
				
				//controlla se il codice esiste
			if(inserisciRuoliService.checkCodicePresente(ruoloDto)==true) {
				log.error("WARNING: Ruolo esistente");
				Object[] parametri= new Object[]{ruoloDto.getCodice()};
				messaggiErrore.add(inserisciRuoliService.aggiungiErrori(ConstantsWebApp.RUOLO_ESISTENTE, parametri));
				mav.setViewName(ConstantsWebApp.INSERISCI_RUOLI);
				return mav;
				} 
				
				boolean stato= inserisciRuoloModel.getFlagAttivo();
				
				//inserimento ruolo
				String cfUtente= data.getUtente().getCodiceFiscale();
				RuoloDto ruolo = inserisciRuoliService.insertRuolo(ruoloDto, cfUtente, stato,
						inserisciRuoloModel.getRuoliCompatibili(), inserisciRuoloModel.getRuoliSelCollTipo());

				//setLogAuditSOL(OperazioneEnum.INSERT, "INS_RUO");
				// Arrays.toString(inserisciRuoloModel.getRuoliCompatibili())
				String ruoliSelCollTipoForLogAudit = "";
				for(RuoloSelCollTipo item:inserisciRuoloModel.getRuoliSelCollTipo()) {
					
					List<RuoloSelezionabileDto> ruoliSelez = ruoloSelezionabileLowDao.findByIdRuoli(ruolo.getId(), Long.parseLong(item.getIdRuolo()));
					for(RuoloSelezionabileDto i:ruoliSelez)
						if (ruoliSelCollTipoForLogAudit == "")
							ruoliSelCollTipoForLogAudit = "" + i.getId();
						else
							ruoliSelCollTipoForLogAudit = ruoliSelCollTipoForLogAudit+","+i.getId();
				}
				String ruoliCompatibiliForLogAudit = "";
				for(String item:inserisciRuoloModel.getRuoliCompatibili()) {
					
					List<RuoloCompatibilita> ruoliComp = ruoloCompatibilitaLowDao.findByIdRuoli(ruolo.getId(), Long.parseLong(item));
					for(RuoloCompatibilita i:ruoliComp)
						if (ruoliCompatibiliForLogAudit == "")
							ruoliCompatibiliForLogAudit = ""+i.getId();
						else
							ruoliCompatibiliForLogAudit = ruoliCompatibiliForLogAudit+","+i.getId();
				}
				
				// Scrittura log Audit
	            ruoliService.setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_INSERISCI_RUOLO, null, 
	            		UUID.randomUUID().toString(), ruolo.getId().toString()+"-"+ruoliSelCollTipoForLogAudit+"-"+ruoliCompatibiliForLogAudit, ConstantsWebApp.INSERISCI_RUOLO, getData());
				
	            mav.addObject("ruolo", new InserisciRuoloModel());
				messaggiErrore.add(inserisciRuoliService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_RIUSCITA));
				mav.setViewName(ConstantsWebApp.INSERISCI_RUOLI);
				updateData(data);
			
					
		} catch (Exception e) {
			log.error("ERROR: ruoli - " , e);
			messaggiErrore.add(inserisciRuoliService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_NON_RIUSCITA));
			mav.setViewName(ConstantsWebApp.ERROR);
			
		} finally {
			
			mav.addObject("errori", messaggiErrore);	
		}
		return mav;
	}
	
	@RequestMapping(value = "/annullaInsert", method = RequestMethod.GET)
	public ModelAndView ruoli(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.INSERISCI_RUOLI);
			mav.addObject("ruolo", new InserisciRuoloModel());
			data.setRuoloDtoList(null);
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		return mav;
	}
	
}


