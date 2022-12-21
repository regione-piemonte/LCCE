/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.business.service.RicercaProfiliService;
import it.csi.solconfig.configuratoreweb.business.service.RuoloService;
import it.csi.solconfig.configuratoreweb.business.service.UtenteService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.AbilitazioneMassivaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.SelectPaginati;
import it.csi.solconfig.configuratoreweb.presentation.model.CredenzialiRuparModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaApplicazioneModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaFunzionalitaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaProfiloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaRuoloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaUtenteModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class HomePageController extends BaseController {
	
	@Autowired
	RicercaProfiliService ricercaProfiliService;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	CollocazioneService collocazioneService;
	
	@Autowired
	RuoloService ruoloService;

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@RequestMapping(value = "/utenti", method = RequestMethod.GET)
		public ModelAndView utenti(ModelAndView mav) {

		try {
			Data data = getData();
			mav.addObject("utente", new RicercaUtenteModel());
			mav.setViewName(ConstantsWebApp.GESTIONE_UTENTI);
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: utenti - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/profili", method = RequestMethod.GET)
	public ModelAndView profili(ModelAndView mav) {

		try {
			Data data = getData();
			data.setApplicazioneDtoList(ricercaProfiliService.ricercaListaApplicazioni(getData()));
			mav.setViewName(ConstantsWebApp.GESTIONE_PROFILI);
			mav.addObject("profilo", new RicercaProfiloModel());
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: profili - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/ruoli", method = RequestMethod.GET)
	public ModelAndView ruoli(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.GESTIONE_RUOLI);
			mav.addObject("ruolo", new RicercaRuoloModel());
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: ruoli - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	@RequestMapping(value = "/applicazioni", method = RequestMethod.GET)
	public ModelAndView applicazioni(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.GESTIONE_APPLICAZIONI);
			mav.addObject("applicazione", new RicercaApplicazioneModel());
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: applicazione - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	@RequestMapping(value = "/funzionalita", method = RequestMethod.GET)
	public ModelAndView funzionalita(ModelAndView mav) {

		try {
			Data data = getData();
			data.setApplicazioneDtoList(ricercaProfiliService.ricercaListaApplicazioni(getData()));
			mav.setViewName(ConstantsWebApp.GESTIONE_FUNZIONALITA);
			mav.addObject("funzionalita", new RicercaFunzionalitaModel());
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: funzionalita - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	@RequestMapping(value = "/rupar", method = RequestMethod.GET)
	public ModelAndView rupar(ModelAndView mav) {

		try {
			Data data = getData();
			mav.addObject("rupar", new CredenzialiRuparModel());
			mav.setViewName(ConstantsWebApp.GESTIONE_RUPAR);
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: utenti - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	@RequestMapping(value = "/abilitazione-massiva", method = RequestMethod.GET)
	public ModelAndView abilitazioneMassiva(ModelAndView mav) {

		try {
			Data data = getData();
			mav.addObject("abilitazione", new AbilitazioneMassivaModel());
			mav.addObject("aziende", collocazioneService.getAllAziende(getData()));
			mav.addObject("ruoli", filtraRuoliSelezionabili(
					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
					ruoloService.ricercaTuttiRuoli(getData()), getData()));
			List<String> stati= new ArrayList<String>();
			stati.add(ConstantsWebApp.INELAB);
			stati.add(ConstantsWebApp.DAELAB);
			mav.addObject("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
			
			mav.setViewName(ConstantsWebApp.ABILITAZIONE_MASSIVA);
			
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: abilitazione massiva - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	@RequestMapping(value = "/disabilitazione-massiva", method = RequestMethod.GET)
	public ModelAndView disabilitazioneMassiva(ModelAndView mav) {

		try {
			Data data = getData();
			mav.addObject("abilitazione", new AbilitazioneMassivaModel());
			mav.addObject("aziende", collocazioneService.getAllAziende(getData()));
			mav.addObject("ruoli", filtraRuoliSelezionabili(
					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
					ruoloService.ricercaTuttiRuoli(getData()), getData()));
			List<String> stati= new ArrayList<String>();
			stati.add(ConstantsWebApp.INELAB);
			stati.add(ConstantsWebApp.DAELAB);
			mav.addObject("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
			
			mav.setViewName(ConstantsWebApp.DISABILITAZIONE_MASSIVA);
			
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: disabilitazione massiva - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	private List<RuoloDTO> filtraRuoliSelezionabili(List<RuoloDTO> ruoliSelezionabili, List<RuoloDTO> ruoli, Data data) {
		boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
		List<RuoloDTO> rl = ruoli.stream().collect(Collectors.toList());
		if(!superUser && !ruoliSelezionabili.isEmpty()) {
			Set<Long> idSet = ruoliSelezionabili.stream().map(RuoloDTO::getId).collect(Collectors.toSet());
			rl = rl.stream().filter(r -> idSet.contains(r.getId())).collect(Collectors.toList());
		}
		return rl;
	}


}