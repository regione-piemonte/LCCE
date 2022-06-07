/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.controller;

import java.util.ArrayList;
import java.util.List;

import it.csi.dma.puawa.business.dao.util.CatalogoLog;
import it.csi.dma.puawa.integration.log.LogDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.csi.dma.puawa.business.dao.dto.LogAuditDto;
import it.csi.dma.puawa.business.dao.util.Constants;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.integration.log.LogGeneralDaoBean;
import it.csi.dma.puawa.integration.notificheCittASR.client.EmailMessageType;
import it.csi.dma.puawa.integration.notificheCittASR.client.ErroreType;
import it.csi.dma.puawa.integration.notificheCittASR.client.MexMessageType;
import it.csi.dma.puawa.integration.notificheCittASR.client.NotificaRequest;
import it.csi.dma.puawa.integration.notificheCittASR.client.NotificaResponse;
import it.csi.dma.puawa.integration.notificheCittASR.client.NotificheCittASRServiceClient;
import it.csi.dma.puawa.integration.notificheCittASR.client.PushMessageType;
import it.csi.dma.puawa.integration.notificheCittASR.client.SmsMessageType;
import it.csi.dma.puawa.integration.reports.common.Errore;
import it.csi.dma.puawa.presentation.constants.ConstantsWebApp;
import it.csi.dma.puawa.presentation.model.Data;
import it.csi.dma.puawa.presentation.model.NotificaData;
import it.csi.dma.puawa.presentation.validator.NotificaValidator;
import it.csi.dma.puawa.util.Utils;

@Controller
@Scope("prototype")
public class NotificaController extends BaseController {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	private NotificheCittASRServiceClient notificheCittASRServiceClient;

	@Autowired
	private NotificaValidator notificaValidator;

	@Autowired
	LogDao logDao;

	@RequestMapping(value = "/notifica", method = RequestMethod.GET)
	public ModelAndView notifica(ModelAndView mav) {

		try {
			mav.addObject(new NotificaData());
			mav.setViewName(ConstantsWebApp.NOTIFICA);
		} catch (Exception e) {
			log.error("ERROR: notifica- ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		return mav;
	}

	@RequestMapping(value = "/comunicazioneCittadino", method = RequestMethod.POST)
	public ModelAndView comunicazioneCittadino(ModelAndView mav, @ModelAttribute NotificaData notificaData) {

		mav.setViewName(ConstantsWebApp.NOTIFICA);
		try {
			Data data = getData();
			List<Errore> errori = new ArrayList<Errore>();

			// Controllo campi
			errori = notificaValidator.validateCampi(notificaData);

			if (errori.isEmpty()) {
				NotificaRequest notificaRequest = createNotificaRequest(notificaData, data);

				// Creazione LogAudit
				LogAuditRichiedente logAuditRichiedente = popolaLogAudit(data);

				LogAuditDto logAuditDto = new LogAuditDto();
				LogGeneralDaoBean logGeneralDaoBean = new LogGeneralDaoBean();

				NotificaResponse notificaResponse = notificheCittASRServiceClient.call(notificaRequest,
						logAuditRichiedente, logGeneralDaoBean);

				logAuditDto = setLogAudit(logAuditRichiedente, ConstantsWebApp.NOTIFICA_CITTADINO_LOG,
						data.getUtente().getViewCollocazione().getColCodAzienda(),
						data.getUtente().getViewCollocazione().getColCodice(), null, null,
						logGeneralDaoBean.getMessaggiDto());
				if (notificaResponse.getElencoErrori() != null
						&& (notificaResponse.getElencoErrori().getErrore() != null
								&& !notificaResponse.getElencoErrori().getErrore().isEmpty())) {
					for (ErroreType errore : notificaResponse.getElencoErrori().getErrore()) {
						Errore err = new Errore();
						err.setCodice(errore.getCodEsito());
						err.setDescrizione(errore.getEsito());
						errori.add(err);
					}
				}else{
					errori.add(logDao.getErrore(CatalogoLog.NOTIFICA_IN_CARICO.getValue()));
				}
			}
			mav.addObject("errori", errori);

		} catch (Exception e) {
			log.error("ERROR: notifica- ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		return mav;
	}

	private NotificaRequest createNotificaRequest(NotificaData notificaData, Data data) {
		NotificaRequest notificaRequest = new NotificaRequest();
		notificaRequest.setCodiceAzienda(data.getUtente().getViewCollocazione().getColCodAzienda().substring(3));
		notificaRequest.setCodiceFiscaleAssistito(notificaData.getCodiceFiscaleAssistito().toUpperCase());
		notificaRequest.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
		PushMessageType pushMessageType = new PushMessageType();
		pushMessageType.setTitolo(notificaData.getTitoloPush());
		pushMessageType.setTesto(notificaData.getTestoPush() + " Contatti:" + notificaData.getEmailAzienda()
				+ "-" + notificaData.getNumeroTelefonoAzienda());
		notificaRequest.setMessaggioPush(pushMessageType);

		EmailMessageType emailMessageType = new EmailMessageType();
		emailMessageType.setOggetto(notificaData.getOggettoEmail());
		emailMessageType.setTesto(notificaData.getTestoEmail() + " Contatti:"
				+ notificaData.getEmailAzienda() + "-" + notificaData.getNumeroTelefonoAzienda());
		notificaRequest.setMessaggioEmail(emailMessageType);

		SmsMessageType smsMessageType = new SmsMessageType();
		smsMessageType.setContenuto("Non applicabile");
		notificaRequest.setMessaggioSms(smsMessageType);

		MexMessageType mexMessageType = new MexMessageType();
		mexMessageType.setTitolo(notificaData.getTestoSito() + " Contatti:"
				+ notificaData.getEmailAzienda() + "-" + notificaData.getNumeroTelefonoAzienda());
		// mexMessageType.setMex
		notificaRequest.setMessaggioSito(mexMessageType);
		return notificaRequest;
	}

	private LogAuditRichiedente popolaLogAudit(Data data) {
		LogAuditRichiedente logAuditRichiedente = new LogAuditRichiedente();
		logAuditRichiedente.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
		logAuditRichiedente.setIpChiamante(data.getUtente().getIpAddress());
		logAuditRichiedente.setCodiceRuoloRichiedente(data.getCodiceRuoloSelezionato());
		logAuditRichiedente.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
		logAuditRichiedente.setDataIniziale(Utils.sysdate().toString());
		logAuditRichiedente.setDataFinale(Utils.sysdate().toString());
		return logAuditRichiedente;
	}
}
