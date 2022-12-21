/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.impl;


import it.csi.configuratorews.business.configuratorews.RuoliApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.RuoliGetRuoloUtentiService;
import it.csi.configuratorews.business.configuratorews.rest.service.RuoliGetService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class RuoliApiServiceImpl implements RuoliApi {
      public Response ruoliCodiceRuoloUtentiGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,String codiceRuolo,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {
          return ServiceExecutor.execute(RuoliGetRuoloUtentiService.class, shibIdentitaCodiceFiscale, xRequestId,
                  xForwardedFor, xCodiceServizio, codiceRuolo, securityContext, httpHeaders, request);
  }
      public Response ruoliGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, Integer limit, Integer offset ) {
      return ServiceExecutor.execute(RuoliGetService.class, shibIdentitaCodiceFiscale, xRequestId,
              xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request,limit, offset );

  }
}
