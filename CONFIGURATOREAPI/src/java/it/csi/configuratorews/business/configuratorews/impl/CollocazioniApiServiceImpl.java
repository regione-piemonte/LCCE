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

import it.csi.configuratorews.business.configuratorews.CollocazioniApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.CollocazioniAziendaGetService;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CollocazioniApiServiceImpl implements CollocazioniApi {
	@Override
	public Response collocazioniByAziendaGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceAzienda, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceStruttura, Integer limit, Integer offset) {
		 return ServiceExecutor.execute(CollocazioniAziendaGetService.class,  shibIdentitaCodiceFiscale,  xRequestId,  xForwardedFor,
					 xCodiceServizio,  codiceAzienda,  securityContext,  httpHeaders,
					 request,  codiceStruttura,  limit,  offset);
	}
}
