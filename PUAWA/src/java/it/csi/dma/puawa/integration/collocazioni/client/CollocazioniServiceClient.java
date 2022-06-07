/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.collocazioni.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.dma.puawa.business.dao.CredenzialiServiziLowDao;
import it.csi.dma.puawa.business.dao.ServiziLowDao;
import it.csi.dma.puawa.business.dao.dto.CredenzialiServiziDto;
import it.csi.dma.puawa.business.dao.dto.ServiziDto;
import it.csi.dma.puawa.business.dao.util.Servizi;
import it.csi.dma.puawa.util.ServicePasswordCallback;
import it.csi.dma.puawa.util.Utils;

public class CollocazioniServiceClient {

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	private CollocazioniService collocazioniService;

	public CollocazioniService getCollocazioniService() {
		return collocazioniService;
	}

	public void setCollocazioniService(CollocazioniService collocazioniService) {
		this.collocazioniService = collocazioniService;
	}

	public GetCollocazioniResponse call(GetCollocazioniRequest getCollocazioniRequest) {
		ServiziDto serviziDto = Utils
				.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.COLLOCAZIONI.getValue()));
		CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
		credenzialiServiziDto.setServiziDto(serviziDto);
		credenzialiServiziDto = Utils
				.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));

		Client client = ClientProxy.getClient(collocazioniService);
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

		return collocazioniService.GetCollocazioni(getCollocazioniRequest);
	}
}
