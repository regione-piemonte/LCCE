/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client;


import it.csi.solconfig.configuratoreweb.business.dao.CredenzialiServiziLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ServiziLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CredenzialiServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Servizi;
import it.csi.solconfig.configuratoreweb.util.ServicePasswordCallback;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class TokenInformationServiceClient {

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	private TokenInformationService tokenInformationService;

	public GetTokenInformation2Response call(GetTokenInformation2Request getTokenInformation2Request) {
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

		cxfEndpoint.getOutInterceptors().add(outInterceptor);

		return tokenInformationService.getTokenInformation2(getTokenInformation2Request);
	}

	public TokenInformationService getTokenInformationService() {
		return tokenInformationService;
	}

	public void setTokenInformationService(TokenInformationService tokenInformationService) {
		this.tokenInformationService = tokenInformationService;
	}
}
