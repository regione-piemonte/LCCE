/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.controller;

import it.csi.dma.puawa.business.dao.util.Constants;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.presentation.constants.ConstantsWebApp;
import it.csi.dma.puawa.presentation.model.Data;
import it.csi.dma.puawa.util.UrlUtils;
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


	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	private UrlUtils urlUtils;

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelAndView mav) {
		try{
			Data data = getData();

			LogAuditRichiedente logAuditRichiedente = new LogAuditRichiedente();
			logAuditRichiedente.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
			logAuditRichiedente.setCodiceRuoloRichiedente(data.getCodiceRuoloSelezionato());
			logAuditRichiedente.setIpChiamante(data.getUtente().getIpAddress());
			if (data.getUtente().getViewCollocazione() == null) {
				setLogAudit(logAuditRichiedente, ConstantsWebApp.LOGOUT_PUA_LOG, null, null, null, null, null);
			} else {
				setLogAudit(logAuditRichiedente, ConstantsWebApp.LOGOUT_PUA_LOG,
						data.getUtente().getViewCollocazione().getColCodAzienda(),
						data.getUtente().getViewCollocazione().getColCodice(), null, null, null);
			}
			updateData(null);
			session.invalidate();
		}catch(Exception e){
			log.error("ERROR: logout - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return "redirect:" + urlUtils.getLogoutUrl();
	}

	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public ModelAndView cambiaRuolo(ModelAndView mav) {
		try{
			initializeData();
			mav.setViewName(ConstantsWebApp.REDIRECT_SCELTA_RUOLI);
		}catch (Exception e){
			log.error("ERROR: cambiaRuolo - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
}
