/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.impl;

import it.csi.configuratorews.business.configuratorews.ApplicazioneSolApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.ApplicazioneSolGetCollocazioniSOLService;
import it.csi.configuratorews.business.configuratorews.rest.service.ApplicazioneSolGetUtentiSOLService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ApplicazioneSolApiServiceImpl implements ApplicazioneSolApi {
	@Deprecated
	public Response applicazioneSolApplicazioneCollocazioniGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String applicazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		return ServiceExecutor.execute(ApplicazioneSolGetCollocazioniSOLService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio,
				applicazione, securityContext, httpHeaders, request, false);
	}
	@Deprecated
	public Response applicazioneSolApplicazioneUtentiGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			String applicazione, String azienda, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		return ServiceExecutor.execute(ApplicazioneSolGetUtentiSOLService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio,
				applicazione, azienda, securityContext, httpHeaders, request, false);
	}
}
