/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.abilitazione;

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

public class AbilitazioniServiceClient {

	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	@Autowired
	private ServiziLowDao serviziLowDao;

	private AbilitazioneService abilitazioneService;

	public AbilitazioneService getAbilitazioniService() {
		return abilitazioneService;
	}

	public void setAbilitazioniService(AbilitazioneService abilitazioneService) {
		this.abilitazioneService = abilitazioneService;
	}

	public GetAbilitazioniResponse call(String shibIdentitaCodiceFiscale, String xRequestID,
										String xForwardedFor, String xCodiceServizio,String codiceRuolo,String codiceCollocazione,String codiceAzienda) {

		GetAbilitazioniRequest getAbilitazioniRequest = createGetAbilitazioniRequest(shibIdentitaCodiceFiscale, xForwardedFor, codiceRuolo, codiceCollocazione, codiceAzienda, xCodiceServizio);

		ServiziDto serviziDto = Utils
				.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.ABILITAZIONI.getValue()));
		CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
		credenzialiServiziDto.setServiziDto(serviziDto);
		credenzialiServiziDto = Utils
				.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));

		Client client = ClientProxy.getClient(abilitazioneService);
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

		return abilitazioneService.getAbilitazioni(getAbilitazioniRequest);
	}

	private GetAbilitazioniRequest createGetAbilitazioniRequest(String shibIdentitaCodiceFiscale, String xForwardedFor, String codiceRuolo, String codiceCollocazione, String codiceAzienda, String xCodiceServizio) {
		GetAbilitazioniRequest getAbilitazioniRequest = new GetAbilitazioniRequest();
		Richiedente richiedente = new Richiedente();
		richiedente.setIpChiamante(xForwardedFor);
		richiedente.setCodiceRuoloRichiedente(codiceRuolo);
		richiedente.setCodiceCollocazioneRichiedente(codiceCollocazione);
		richiedente.setCollCodiceAziendaRichiedente(codiceAzienda);
		richiedente.setApplicazioneChiamante(xCodiceServizio);
		richiedente.setCodiceFiscaleRichiedente(shibIdentitaCodiceFiscale);
		getAbilitazioniRequest.setRichiedente(richiedente);
		return getAbilitazioniRequest;
	}
}
