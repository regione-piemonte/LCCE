/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import it.csi.solconfig.configuratoreweb.business.dao.ConfigurazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ConfigurazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.RuoliService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class GenericController extends BaseController {


	private static final String NOME_MANUALE = "NOME_MANUALE";
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	ConfigurazioneLowDao configurazioneLowDao;
	
	
	@Autowired
	RuoliService ruoliService;


	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ModelAndView help(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.HELP);
			String pathManuale = "/manuali/" + prelevaDatiManuale().getValore();
		       ruoliService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_HELP_AUDIT, null, 
		        		UUID.randomUUID().toString(), null, ConstantsWebApp.HELP_AUDIT, getData());
			mav.addObject("manuale", pathManuale);
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: utenti - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(ModelAndView mav) {

		try {
			//Setto subito il logAudit cosi' da poter utilizzare i dati in sessione
		    // Scrittura log Audit
	        ruoliService.setLogAuditSOLNew(OperazioneEnum.LOGOUT, ConstantsWebApp.KEY_OPER_RITORNO_PUA, null, 
	        		null, null, null, getData());
			//Inizializzo la sessione per svuotarla
			initializeData();
			session.invalidate();
			mav.setViewName("redirect:" + urlLogout);
		} catch (Exception e) {
			log.error("ERROR: logout - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}


	/**
	 * @return
	 * @throws Exception
	 */
	public ConfigurazioneDto prelevaDatiManuale() throws Exception {
		ConfigurazioneDto configurazioneDto = new ConfigurazioneDto();
		configurazioneDto.setChiave(NOME_MANUALE);
		List<ConfigurazioneDto> manuale = new ArrayList<ConfigurazioneDto>();
		manuale = (List<ConfigurazioneDto>) configurazioneLowDao.findByFilter(configurazioneDto);
		return Utils.getFirstRecord(manuale);
	}
}
