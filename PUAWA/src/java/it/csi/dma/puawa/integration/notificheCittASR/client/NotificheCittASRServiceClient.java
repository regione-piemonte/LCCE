/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.notificheCittASR.client;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.puawa.business.dao.CredenzialiServiziLowDao;
import it.csi.dma.puawa.business.dao.ServiziLowDao;
import it.csi.dma.puawa.business.dao.dto.CredenzialiServiziDto;
import it.csi.dma.puawa.business.dao.dto.MessaggiDto;
import it.csi.dma.puawa.business.dao.dto.ServiziDto;
import it.csi.dma.puawa.business.dao.dto.ServiziRichiamatiXmlDto;
import it.csi.dma.puawa.business.dao.util.CatalogoLog;
import it.csi.dma.puawa.business.dao.util.Servizi;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.integration.log.LogDao;
import it.csi.dma.puawa.integration.log.LogGeneralDaoBean;
import it.csi.dma.puawa.integration.reports.common.Errore;
import it.csi.dma.puawa.integration.reports.common.ServiceResponse;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;
import it.csi.dma.puawa.util.ServicePasswordCallback;
import it.csi.dma.puawa.util.Utils;

public class NotificheCittASRServiceClient {

	@Autowired
	private LogDao logDao;

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	private NotificheCittASRService notificheCittASRService;

	public NotificheCittASRService getNotificheCittASRService() {
		return notificheCittASRService;
	}

	public void setNotificheCittASRService(NotificheCittASRService notificheCittASRService) {
		this.notificheCittASRService = notificheCittASRService;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public NotificaResponse call(NotificaRequest notificaRequest, LogAuditRichiedente logAuditRichiedente,
			LogGeneralDaoBean logGeneralDaoBean) {

		NotificaResponse notificaResponse = new NotificaResponse();
		try {
			ServiziDto serviziDto = Utils.getFirstRecord(
					serviziLowDao.findByCodice(new ServiziDto(), Servizi.NOTIFICA_CITTADINO.getValue()));
			MessaggiDto messaggiDto = getMessaggiDto(notificaRequest, logAuditRichiedente, serviziDto);

			ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = getServiziRichiamatiXmlDto(notificaRequest, serviziDto, messaggiDto);
			logGeneralDaoBean.setServiziRichiamatiXmlDto(serviziRichiamatiXmlDto);

			logGeneralDaoBean.setMessaggiDto(messaggiDto);

			logGeneralDaoBean = logDao.logStart(logGeneralDaoBean, null);

			// Configurazione chiamata al servizio
			CredenzialiServiziDto credenzialiServiziDto = getCredenzialiServiziDto(serviziDto);

			Client client = ClientProxy.getClient(notificheCittASRService);
			Endpoint cxfEndpoint = client.getEndpoint();

			/* Interceptors OUT */
			Map<String, Object> outProps = new HashMap<String, Object>();
			outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			ServicePasswordCallback clientPasswordCallback = new ServicePasswordCallback();
			clientPasswordCallback.setPassword(credenzialiServiziDto.getPassword());
			clientPasswordCallback.setUsername(credenzialiServiziDto.getUsername());
			outProps.put("passwordCallbackRef", clientPasswordCallback);
			outProps.put("passwordType", "PasswordText");
			outProps.put("user", credenzialiServiziDto.getUsername());
			outProps.put("mustUnderstand", "0");
			WSS4JOutInterceptor outInterceptor = new WSS4JOutInterceptor(outProps);

			cxfEndpoint.getOutInterceptors().add(outInterceptor);
			notificaResponse = notificheCittASRService.notificaMessaggi(notificaRequest);
		} catch (Exception e) {
			e.printStackTrace();
			notificaResponse = setErroreGenericoResponse();
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(notificaResponse);
			List<Errore> errori = popolaErrori(notificaResponse);
			logDao.logEnd(logGeneralDaoBean, null,
					new ServiceResponse(errori, RisultatoCodice.fromValue(notificaResponse.getEsito())), xmlOut, null,
					null);
		}

		return notificaResponse;
	}

	private CredenzialiServiziDto getCredenzialiServiziDto(ServiziDto serviziDto) {
		CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
		credenzialiServiziDto.setServiziDto(serviziDto);
		credenzialiServiziDto = Utils
				.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));
		return credenzialiServiziDto;
	}

	private ServiziRichiamatiXmlDto getServiziRichiamatiXmlDto(NotificaRequest notificaRequest, ServiziDto serviziDto, MessaggiDto messaggiDto) {
		ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = new ServiziRichiamatiXmlDto();
		serviziRichiamatiXmlDto.setMessaggiDto(messaggiDto);
		serviziRichiamatiXmlDto.setServiziDto(serviziDto);
		String xmlIn = Utils.xmlMessageFromObject(notificaRequest);
		serviziRichiamatiXmlDto.setXmlIn(xmlIn != null ? xmlIn.getBytes() : null);
		serviziRichiamatiXmlDto.setDataChiamata(Utils.sysdate());
		return serviziRichiamatiXmlDto;
	}

	private MessaggiDto getMessaggiDto(NotificaRequest notificaRequest, LogAuditRichiedente logAuditRichiedente, ServiziDto serviziDto) {
		MessaggiDto messaggiDto = new MessaggiDto();
		messaggiDto.setServiziDto(serviziDto);
		messaggiDto.setCfRichiedente(notificaRequest.getCodiceFiscaleRichiedente());
		messaggiDto.setRuoloRichiedente(logAuditRichiedente.getCodiceRuoloRichiedente());
		messaggiDto.setApplicazione(logAuditRichiedente.getApplicazioneChiamante());
		messaggiDto.setClientIp(logAuditRichiedente.getIpChiamante());
		messaggiDto.setDataInserimento(Utils.sysdate());
		messaggiDto.setDataRicezione(Timestamp.valueOf(LocalDateTime.now()));
		return messaggiDto;
	}

	private List<Errore> popolaErrori(NotificaResponse notificaResponse) {
		List<Errore> errori = new ArrayList<Errore>();
		if(notificaResponse.getElencoErrori() != null && notificaResponse.getElencoErrori().getErrore() != null &&
			!notificaResponse.getElencoErrori().getErrore().isEmpty()){
			for (ErroreType errore : notificaResponse.getElencoErrori().getErrore()) {
				errori.add(new Errore(errore.getCodEsito(), errore.getEsito()));
			}
		}
		return errori;
	}

	private NotificaResponse setErroreGenericoResponse() {
		NotificaResponse notificaResponse;
		notificaResponse = new NotificaResponse();
		notificaResponse.setElencoErrori(new ErroriArrayType());
		Errore erroreGenerico = logDao.getErrore(CatalogoLog.ERRORE_INTERNO.getValue());
		ErroreType erroreType = new ErroreType();
		erroreType.setCodEsito(erroreGenerico.getCodice());
		erroreType.setEsito(erroreGenerico.getDescrizione());
		notificaResponse.getElencoErrori().getErrore().add(erroreType);
		notificaResponse = new NotificaResponse(RisultatoCodice.FALLIMENTO.getValue(),
				notificaResponse.getElencoErrori());
		return notificaResponse;
	}
}
