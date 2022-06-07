/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reportRefertiScaricati.client;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;
import it.csi.dma.puawa.util.ServicePasswordCallback;
import it.csi.dma.puawa.util.Utils;

public class ReportRefertiScaricatiServiceClient {

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	@Autowired
	private LogDao logDao;

	private ReportRefertiScaricatiService reportRefertiScaricatiService;

	public ReportRefertiScaricatiService getReportRefertiScaricatiService() {
		return reportRefertiScaricatiService;
	}

	public void setReportRefertiScaricatiService(ReportRefertiScaricatiService reportRefertiScaricatiService) {
		this.reportRefertiScaricatiService = reportRefertiScaricatiService;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ReportRefertiScaricatiResponse call(ReportRefertiScaricatiRequest reportRefertiScaricatiRequest,
			LogAuditRichiedente logAuditRichiedente, LogGeneralDaoBean logGeneralDaoBean) {

		ReportRefertiScaricatiResponse reportRefertiScaricatiResponse = new ReportRefertiScaricatiResponse();
		try {
			ServiziDto serviziDto = Utils.getFirstRecord(
					serviziLowDao.findByCodice(new ServiziDto(), Servizi.REPORT_REFERTI_SCARTATI.getValue()));
			MessaggiDto messaggiDto = new MessaggiDto();
			messaggiDto.setServiziDto(serviziDto);
			messaggiDto.setCfRichiedente(reportRefertiScaricatiRequest.getRichiedente().getCodiceFiscale());
			messaggiDto.setRuoloRichiedente(reportRefertiScaricatiRequest.getRichiedente().getRuolo().getCodice());
			messaggiDto.setClientIp(logAuditRichiedente.getIpChiamante());
			messaggiDto.setDataInserimento(Utils.sysdate());
			messaggiDto.setDataRicezione(Timestamp.valueOf(LocalDateTime.now()));
			messaggiDto.setEsito(RisultatoCodice.SUCCESSO.getValue());

			ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = new ServiziRichiamatiXmlDto();
			serviziRichiamatiXmlDto.setMessaggiDto(messaggiDto);
			serviziRichiamatiXmlDto.setServiziDto(serviziDto);
			String xmlIn = Utils.xmlMessageFromObject(reportRefertiScaricatiRequest);
			serviziRichiamatiXmlDto.setXmlIn(xmlIn != null ? xmlIn.getBytes() : null);
			serviziRichiamatiXmlDto.setDataChiamata(Utils.sysdate());
			logGeneralDaoBean.setServiziRichiamatiXmlDto(serviziRichiamatiXmlDto);

			logGeneralDaoBean.setMessaggiDto(messaggiDto);

			// TODO: Mettere come secondo parametro il servizio
			logGeneralDaoBean = logDao.logStart(logGeneralDaoBean, null);

			// Configurazione ID Messaggio
			reportRefertiScaricatiRequest.setIdMessaggio(logGeneralDaoBean.getMessaggiDto().getId().toString());

			// Configurazione chiamata al servizio
			CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
			credenzialiServiziDto.setServiziDto(serviziDto);
			credenzialiServiziDto = Utils
					.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));

			Client client = ClientProxy.getClient(reportRefertiScaricatiService);
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

			reportRefertiScaricatiResponse = reportRefertiScaricatiService
					.getReportRefertiScaricati(reportRefertiScaricatiRequest);

		} catch (Exception e) {
			if (reportRefertiScaricatiResponse.getErrori() == null) {
				reportRefertiScaricatiResponse.setErrori(new ArrayList<Errore>());
			}
			reportRefertiScaricatiResponse.getErrori()
					.add(new Errore("AUTH_ER_000", CatalogoLog.ERRORE_INTERNO.getValue()));
			reportRefertiScaricatiResponse = new ReportRefertiScaricatiResponse(
					reportRefertiScaricatiResponse.getErrori(), RisultatoCodice.FALLIMENTO);
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(reportRefertiScaricatiResponse);

			// TODO: update dei log: secondo parametro abilitazione, terzo parametro la
			// response della chiamata al servizio, token e xmlOut
			logDao.logEnd(logGeneralDaoBean, null, reportRefertiScaricatiResponse, xmlOut, null, null);
		}
		return reportRefertiScaricatiResponse;
	}
}
