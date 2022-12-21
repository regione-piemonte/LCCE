/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.collocazioni;

import it.csi.configuratorews.business.dao.CredenzialiServiziLowDao;
import it.csi.configuratorews.business.dao.ServiziLowDao;
import it.csi.configuratorews.business.dto.CredenzialiServiziDto;
import it.csi.configuratorews.business.dto.ServiziDto;
import it.csi.configuratorews.util.Constants;
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

	public GetCollocazioniResponse call(String shibIdentitaCodiceFiscale, String xRequestID,
			String xForwardedFor, String xCodiceServizio,String codiceRuolo) {
		
		GetCollocazioniRequest getCollocazioniRequest = createGetCollocazioniRequest(shibIdentitaCodiceFiscale, xForwardedFor, codiceRuolo, xCodiceServizio);

		
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
		cxfEndpoint.getInInterceptors().add(new LoggingInInterceptor());
		cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
		cxfEndpoint.getOutInterceptors().add(outInterceptor);

		return collocazioniService.GetCollocazioni(getCollocazioniRequest);
	}

	private GetCollocazioniRequest createGetCollocazioniRequest(String shibIdentitaCodiceFiscale, String xForwardedFor,
			String codiceRuolo, String xCodiceServizio) {
		GetCollocazioniRequest getCollocazioniRequest = new GetCollocazioniRequest();
		Richiedente richiedente = new Richiedente();
		richiedente.setIpChiamante(xForwardedFor);
		richiedente.setCodiceRuoloRichiedente(codiceRuolo);
		richiedente.setApplicazioneChiamante(xCodiceServizio);
		richiedente.setCodiceFiscaleRichiedente(shibIdentitaCodiceFiscale);
		getCollocazioniRequest.setRichiedente(richiedente);
		return getCollocazioniRequest;
	}
}
