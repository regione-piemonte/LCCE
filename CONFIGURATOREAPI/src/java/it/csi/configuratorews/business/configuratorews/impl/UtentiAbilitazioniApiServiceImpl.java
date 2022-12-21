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

import it.csi.configuratorews.business.configuratorews.UtentiAbilitazioniApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.UtentiProfiloGetService;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UtentiAbilitazioniApiServiceImpl implements UtentiAbilitazioniApi{
	@Override
	public Response utentiGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String ruolo, String collocazione, String limit,
			String offset, String codiceAzienda) {
		return ServiceExecutor.execute(UtentiProfiloGetService.class, shibIdentitaCodiceFiscale, xRequestId,
				xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request, ruolo, collocazione, limit, offset, codiceAzienda);
	}
}
