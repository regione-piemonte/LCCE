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

import it.csi.configuratorews.business.configuratorews.PreferencesUsersApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesUserGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesUserPostService;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesUserPutService;
import it.csi.configuratorews.dto.configuratorews.ContattiDigitali;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PreferencesUsersApiImpl implements PreferencesUsersApi{

	@Override
	public Response getContattoDigitale(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio, String utente,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		return ServiceExecutor.execute(PreferencesUserGetService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, utente);
	}

	@Override
	public Response salvaContattoDigitale(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio, String utente,
			ContattiDigitali input, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		return ServiceExecutor.execute(PreferencesUserPutService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, utente, input);
	}

	@Override
	public Response eliminaContattoDigitale(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio, String utente,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		return ServiceExecutor.execute(PreferencesUserPostService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, utente);
	}

}
