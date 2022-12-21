/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.configuratorews.UtentiApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.AutorizzazioneUtenteGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.AziendaUtenteGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.CollocazioniUtenteGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.RuoliUtenteGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.UtenteAbilitazioniGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.UtentiGetService;

//CAPIRE LEGAME APPLICAZIONE UNTENTE =>


@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UtentiApiImplServiceImpl implements UtentiApi{

	@Override
	public Response utentiGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String ruolo, String collocazione, String limit,
			String offset, String codiceAzienda) {
		return ServiceExecutor.execute(UtentiGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, ruolo, collocazione, limit, offset, codiceAzienda);
	}
	@Override
	public Response abilitazioniGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String ruolo, String collocazione, String limit,
			String offset, String codiceAzienda,String codiceFiscale) {
		return ServiceExecutor.execute(UtenteAbilitazioniGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, ruolo, collocazione, limit, offset, codiceAzienda, codiceFiscale);
	}

	@Override
	public Response collocazioniGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String codiceFiscale, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest request, String ruoloCodice, String offset, String limit, String codiceAzienda) {
		return ServiceExecutor.execute(CollocazioniUtenteGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceFiscale, ruoloCodice, offset, limit,codiceAzienda);
	}

	@Override
	public Response autorizzazioneUtenteGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String ruoloCodice, String applicazioneCodice, String collocazioneCodice,
			String profiloCodice, String funzionalitaCodice, String codiceAzienda) {
		return ServiceExecutor.execute(AutorizzazioneUtenteGetService.class, shibIdentitaCodiceFiscale,  xRequestId,  xForwardedFor,
			 xCodiceServizio,  codiceFiscale,  securityContext,  httpHeaders,
			 request,  ruoloCodice,  applicazioneCodice,  collocazioneCodice,
			 profiloCodice,  funzionalitaCodice,codiceAzienda);
	}

	@Override
	public Response ruoliUtentiGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, Integer offset, Integer limit, String codiceAzienda) {
		return ServiceExecutor.execute(RuoliUtenteGetService.class, shibIdentitaCodiceFiscale,  xRequestId,  xForwardedFor,
				 xCodiceServizio,  codiceFiscale,  securityContext,  httpHeaders,
				 request,  offset,  limit, codiceAzienda);
	}
	@Override
	public Response aziendaUtenteGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceCollocazione, String codiceRuolo, String codiceApplicazione) {

		return ServiceExecutor.execute(AziendaUtenteGetService.class, shibIdentitaCodiceFiscale,  xRequestId,  xForwardedFor,
				 xCodiceServizio,  codiceFiscale,  securityContext,  httpHeaders,
				 request,  codiceCollocazione,  codiceRuolo, codiceApplicazione);
	}
	

}
