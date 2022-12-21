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

import it.csi.configuratorews.business.configuratorews.ApplicazioniApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.ApplicazioneSolGetCollocazioniSOLService;
import it.csi.configuratorews.business.configuratorews.rest.service.ApplicazioneSolGetUtentiSOLService;
import it.csi.configuratorews.business.configuratorews.rest.service.ApplicazioniGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.FunzionalitaDeleteService;
import it.csi.configuratorews.business.configuratorews.rest.service.PermessoPutService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProfiliFunzionalitaGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProfiliFunzionalitaPostService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProfiliFunzionalitaPutService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProfiloDeleteService;
import it.csi.configuratorews.business.configuratorews.rest.service.FunzionalitaApplicazioneGetService;
import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.ListaPermessi;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalitaBody;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ApplicazioniApiServiceImpl implements ApplicazioniApi {

	public Response applicazioniGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceAzienda, String limit, String offset) {
		return ServiceExecutor.execute(ApplicazioniGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceAzienda, limit, offset);

	}

	@Override
	public Response getProfiliFunzionalita(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceApplicazione, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String limit, String offset, String codiceAzienda) {
		return ServiceExecutor.execute(ProfiliFunzionalitaGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceApplicazione, limit,
				offset, codiceAzienda);
	}

	@Override
	public Response modificaProfiliFunzionalita(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			String codiceApplicazione, String codiceFunzionalita, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, ProfiloFunzionalitaBody body, String codiceAzienda ) {
		return ServiceExecutor.execute(ProfiliFunzionalitaPutService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceApplicazione,codiceFunzionalita, body, codiceAzienda);
	}

	public Response applicazioneSolApplicazioneCollocazioniGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String applicazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest request, Integer limit, Integer offset, String codiceAzienda) {
		return ServiceExecutor.execute(ApplicazioneSolGetCollocazioniSOLService.class, shibIdentitaCodiceFiscale,
				xRequestId, xForwardedFor, xCodiceServizio, applicazione, securityContext, httpHeaders, request, limit,
				offset, codiceAzienda, true);
	}

	public Response applicazioneSolApplicazioneUtentiGet(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String applicazione, String azienda,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, Integer limit,
			Integer offset) {
		return ServiceExecutor.execute(ApplicazioneSolGetUtentiSOLService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, applicazione, azienda, limit,
				offset, true);
	}

	@Override
	public Response inserisciProfiliFunzionalita(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String codiceApplicazione, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest request, InserimentoProfiloFunzionalitaBody body, String codiceAzienda,
			String codiceProfilo, String descrizioneProfilo, String codiceFunzionalita,
			String descrizioneFunzionalita) {
		return ServiceExecutor.execute(ProfiliFunzionalitaPostService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceApplicazione, body,
				codiceAzienda, codiceProfilo, descrizioneProfilo, codiceFunzionalita, descrizioneFunzionalita);
	}

	@Override
	public Response cancellaFunzionalita(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			String codiceApplicazione, String codiceFunzionalita, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request,
			String codiceAzienda) {
		return ServiceExecutor.execute(FunzionalitaDeleteService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceApplicazione,codiceFunzionalita,  codiceAzienda);
	
	}
	@Override
	public Response cancellaProfilo(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			String codiceApplicazione, String codiceFunzionalita, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request,
			String codiceAzienda) {
		return ServiceExecutor.execute(ProfiloDeleteService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceApplicazione,codiceFunzionalita,  codiceAzienda);
		
	}

	@Override
	public Response modificaPermesso(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			String codiceApplicazione, String codiceFunzionalita, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request,
			String codiceAzienda, ListaPermessi input) {
		return ServiceExecutor.execute(PermessoPutService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, codiceApplicazione,codiceFunzionalita,  codiceAzienda, input);
	}

	@Override
	public Response getApplicazioneFunzionalita(String shibIdentitaCodiceFiscale, String xRequestId,
			String xForwardedFor, String xCodiceServizio, String applicazione, String azienda,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String limit,
			String offset) {

		return ServiceExecutor.execute(FunzionalitaApplicazioneGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, applicazione, azienda, limit, offset);
		
	}

}
