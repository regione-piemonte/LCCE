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

import it.csi.configuratorews.business.configuratorews.PreferencesApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesOperatorGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesOperatorPutService;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesService;
import it.csi.configuratorews.business.configuratorews.rest.service.PreferencesTokenPutService;
import it.csi.configuratorews.dto.configuratorews.FilterPreferences;
import it.csi.configuratorews.dto.configuratorews.Preferences;
import it.csi.configuratorews.dto.configuratorews.TokenConfiguration;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PreferencesApiImpl implements PreferencesApi{



	@Override
	public Response getUtentiNotifiche(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, FilterPreferences input, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request) {
		 return ServiceExecutor.execute(PreferencesService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, input);
	}

	@Override
	public Response getPreferenzeOperatore(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String utente, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request) {
		return ServiceExecutor.execute(PreferencesOperatorGetService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, utente);
	}

	@Override
	public Response salvaPreferenzeOperatore(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String utente, Preferences input, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest request) {
		 return ServiceExecutor.execute(PreferencesOperatorPutService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, utente, input);
	}

	@Override
	public Response saveTokenConfiguration(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			TokenConfiguration input, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		 return ServiceExecutor.execute(PreferencesTokenPutService.class, shibIdentitaCodiceFiscale, xRequestId,
	              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, input);
	}

}
