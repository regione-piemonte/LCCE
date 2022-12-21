/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.tokenInformation;

import it.csi.configuratorews.business.dao.CredenzialiServiziLowDao;
import it.csi.configuratorews.business.dao.ServiziLowDao;
import it.csi.configuratorews.business.dto.CredenzialiServiziDto;
import it.csi.configuratorews.business.dto.ServiziDto;
import it.csi.configuratorews.interfacews.client.ruoliUtente.GetRuoliUtenteRequest;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Richiedente;
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

import java.util.HashMap;
import java.util.Map;

public class TokenInformationServiceClient {

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	private TokenInformationService tokenInformationService;

	public GetTokenInformation2Response call(String shibIdentitaCodiceFiscale, String xRequestID,
			String xForwardedFor, String xCodiceServizio, String token) {
		
		GetTokenInformation2Request getTokenInformation2Request = createGetTokenInformationRequest(shibIdentitaCodiceFiscale, xForwardedFor, xCodiceServizio, token);
		
		ServiziDto serviziDto = Utils
				.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.TOKEN.getValue()));
		CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
		credenzialiServiziDto.setServiziDto(serviziDto);
		credenzialiServiziDto = Utils
				.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));

		Client client = ClientProxy.getClient(tokenInformationService);
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

		return tokenInformationService.getTokenInformation2(getTokenInformation2Request);
	}

	public TokenInformationService getTokenInformationService() {
		return tokenInformationService;
	}

	public void setTokenInformationService(TokenInformationService tokenInformationService) {
		this.tokenInformationService = tokenInformationService;
	}
	
	private GetTokenInformation2Request createGetTokenInformationRequest(String shibIdentitaCodiceFiscale, String xForwardedFor, String xCodiceServizio, String token) {
		GetTokenInformation2Request getTokenInformation2Request = new GetTokenInformation2Request();
		getTokenInformation2Request.setApplicazione(xCodiceServizio);
		getTokenInformation2Request.setIpBrowser(xForwardedFor);
		getTokenInformation2Request.setToken(token);
		return getTokenInformation2Request;
	}
}
