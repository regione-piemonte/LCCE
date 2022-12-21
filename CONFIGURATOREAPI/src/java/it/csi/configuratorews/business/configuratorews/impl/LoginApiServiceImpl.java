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

import it.csi.configuratorews.business.configuratorews.LoginApi;
import it.csi.configuratorews.business.configuratorews.rest.ServiceExecutor;
import it.csi.configuratorews.business.configuratorews.rest.service.AbilitazioniService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProxyAbilitazioniGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProxyCollocazioneGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProxyRuoliUtenteGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProxyTokenAuthenticationGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.ProxyTokenInformationGetService;
import it.csi.configuratorews.business.configuratorews.rest.service.UtenteGetService;
import it.csi.configuratorews.dto.configuratorews.ParametriAutenticazione;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class LoginApiServiceImpl implements LoginApi {
    
	
	public Response proxyAbilitazioniGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,String codiceRuolo,String codiceCollocazione,String codiceAzienda,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {

      return ServiceExecutor.execute(ProxyAbilitazioniGetService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, codiceRuolo, codiceCollocazione, codiceAzienda,
                  securityContext, httpHeaders, request);
    }
      
    public Response applicazioniGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,String codiceRuolo,String codiceCollocazione,String codiceAzienda,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {

        return ServiceExecutor.execute(AbilitazioniService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, codiceRuolo, codiceCollocazione, codiceAzienda,
                securityContext, httpHeaders, request);
    }
    
    public Response proxyCollocazioniGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,String codiceRuolo,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {
      // do some magic!
      return ServiceExecutor.execute(ProxyCollocazioneGetService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, codiceRuolo,
              securityContext, httpHeaders, request);
    }
    
    public Response proxyRuoliGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {
      // do some magic!
    	  return ServiceExecutor.execute(ProxyRuoliUtenteGetService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio,
                  securityContext, httpHeaders, request);
    }
    
    public Response proxyTokenAuthenticationPost(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,ParametriAutenticazione parametriAutenticazione,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {
      // do some magic!
      return ServiceExecutor.execute(ProxyTokenAuthenticationGetService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, parametriAutenticazione,
              securityContext, httpHeaders, request);
    }
    
    
    public Response proxyTokenInformationGet(String shibIdentitaCodiceFiscale,String xRequestId,String xForwardedFor,String xCodiceServizio,String token,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request ) {
      // do some magic!
    	  return ServiceExecutor.execute(ProxyTokenInformationGetService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, token,
                  securityContext, httpHeaders, request);
    }
    
    
	@Override
	public Response utenteGet(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request) {
		return ServiceExecutor.execute(UtenteGetService.class, shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
	}


}
