/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.authentication2;

import it.csi.configuratorews.business.dao.CredenzialiServiziLowDao;
import it.csi.configuratorews.business.dao.ServiziLowDao;
import it.csi.configuratorews.business.dto.CredenzialiServiziDto;
import it.csi.configuratorews.business.dto.ServiziDto;
import it.csi.configuratorews.dto.configuratorews.ParametriAutenticazione;
import it.csi.configuratorews.dto.configuratorews.ParametroLogin;
import it.csi.configuratorews.interfacews.client.base.ParametriLogin;
import it.csi.configuratorews.interfacews.client.tokenInformation.GetTokenInformation2Request;
import it.csi.configuratorews.util.ServicePasswordCallback;
import it.csi.configuratorews.util.Servizi;
import it.csi.configuratorews.util.Utils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authentication2ServiceClient {

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	private Authentication2Service authentication2Service;

	public Authentication2Service getAuthentication2Service() {
		return authentication2Service;
	}

	public void setAuthentication2Service(Authentication2Service authentication2Service) {
		this.authentication2Service = authentication2Service;
	}

	public GetAuthentication2Response call(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio, ParametriAutenticazione parametriAutenticazione) {
		
		GetAuthentication2Request getTokenAuthetication2Request = createGetTokenAuthenticationRequest(shibIdentitaCodiceFiscale, xForwardedFor, xCodiceServizio, parametriAutenticazione);
		
		ServiziDto serviziDto = Utils
				.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION_2.getValue()));
		CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
		credenzialiServiziDto.setServiziDto(serviziDto);
		credenzialiServiziDto = Utils
				.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));

		Client client = ClientProxy.getClient(authentication2Service);
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
		cxfEndpoint.getInInterceptors().add(new LoggingInInterceptor());
		cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
		cxfEndpoint.getOutInterceptors().add(outInterceptor);

		return authentication2Service.getAuthentication2(getTokenAuthetication2Request);
	}

	private GetAuthentication2Request createGetTokenAuthenticationRequest(String shibIdentitaCodiceFiscale,
			String xForwardedFor, String xCodiceServizio, ParametriAutenticazione parametriAutenticazione) {
		GetAuthentication2Request getAuthentication2Request = new GetAuthentication2Request();
		if(parametriAutenticazione.getParametriLogin() != null || parametriAutenticazione.getParametriLogin().isEmpty()) {
			it.csi.configuratorews.interfacews.client.base.ParametriLogin parametriLogin = new ParametriLogin();
			List<it.csi.configuratorews.interfacews.client.base.ParametriLogin> parametriLoginList = new ArrayList<it.csi.configuratorews.interfacews.client.base.ParametriLogin>();
			for(ParametroLogin dto : parametriAutenticazione.getParametriLogin()) {
				parametriLogin.setCodice(dto.getCodice());
				parametriLogin.setValore(dto.getValore());
				parametriLoginList.add(parametriLogin);
			}
			getAuthentication2Request.setParametriLogin(parametriLoginList);
		}
		Richiedente richiedente = new Richiedente();
		richiedente.setApplicazioneChiamante(xCodiceServizio);
		richiedente.setIpClient(xForwardedFor);
		richiedente.setCodiceCollocazione(parametriAutenticazione.getCollocazione());
		richiedente.setCodiceFiscaleRichiedente(shibIdentitaCodiceFiscale);
		richiedente.setCollCodiceAzienda(parametriAutenticazione.getAzienda());
		richiedente.setRuolo(parametriAutenticazione.getRuolo());
		richiedente.setApplicazioneRichiesta(parametriAutenticazione.getApplicazione());
		getAuthentication2Request.setRichiedente(richiedente);
		
		return getAuthentication2Request;
	}
}
