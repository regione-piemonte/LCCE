/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;

import it.csi.configuratorews.util.Constants;
import org.apache.commons.lang3.StringUtils;

import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.ApisanFseStatus;
import org.springframework.web.bind.annotation.RequestHeader;


public abstract class SOLRESTBaseService extends RESTBaseService {

	protected String shibIdentitaCodiceFiscale;
	protected String xRequestID;
	protected String xForwardedFor;
	protected String xCodiceServizio;

	public final BigDecimal ESITO_POSITIVO = new BigDecimal(0000);
	public final BigDecimal ESITO_NEGATIVO = new BigDecimal(9999);


	public SOLRESTBaseService(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(securityContext, httpHeaders, request);
		
		this.shibIdentitaCodiceFiscale = getHeaderParam("Shib-Identita-CodiceFiscale");
		this.xRequestID = getHeaderParam("X-Request-Id");
		this.xForwardedFor = getHeaderParam("X-Forwarded-For");
		logRequestContext();
	}

	public SOLRESTBaseService(String shibIdentitaCodiceFiscale, String xRequestID,
			String xForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(securityContext, httpHeaders, request);

		this.shibIdentitaCodiceFiscale = shibIdentitaCodiceFiscale;
		this.xRequestID = xRequestID;
		this.xForwardedFor = xForwardedFor;
		logRequestContext();
	}

	public SOLRESTBaseService(String shibIdentitaCodiceFiscale, String xRequestID,
							  String xForwardedFor, String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(securityContext, httpHeaders, request);

		this.shibIdentitaCodiceFiscale = shibIdentitaCodiceFiscale;
		this.xRequestID = xRequestID;
		this.xForwardedFor = xForwardedFor;
		this.xCodiceServizio = xCodiceServizio;
		logRequestContext();
	}
	
	@Override
	public Response executeService() {
//		if(StringUtils.isBlank(xRequestID)) {
//			return ErroreBuilder.from(ApisanFseStatus.PARAMETRO_NON_VALIDO, "X-Request-ID").exception().getResponse();
//		}
		
		super.executeService();
		if(xRequestID!=null && res != null && res.getMetadata() != null) {
			res.getMetadata().add("X-Request-ID", xRequestID);
		}
		
		return res;
	}

	protected String getIPRemoteAddress() {
		if (xForwardedFor != null && !"".equals(xForwardedFor)) {
			return (new StringTokenizer(xForwardedFor, ",").nextToken().trim());
		}
		return this.request.getRemoteAddr();
	}

	protected boolean isUtenteAutorizzato(String cf) {
		return shibIdentitaCodiceFiscale != null && shibIdentitaCodiceFiscale.equalsIgnoreCase(cf);
	}

	protected void checkUtenteAutorizzato(String cf) {
		checkCondition( isUtenteAutorizzato(cf), ErroreBuilder.from(ApisanFseStatus.UTENTE_NON_AUTORIZZATO).exception());
	}
	
	protected boolean isCodiceFiscaleValido(String cf) {
		return cf != null && (cf.length() == 16);
		//return cf != null && cf.matches("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]");
	}

	protected void checkCodiceFiscaleValido(String cf) {
		checkCondition(isCodiceFiscaleValido(cf), ErroreBuilder.from(ApisanFseStatus.CODICE_FISCALE_NON_VALIDO, cf).exception());
	}

	protected String getHeaderParam(String headerParam) {
		List<String> values = httpHeaders.getRequestHeader(headerParam);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}
	
	@Deprecated
	protected ResponseBuilder headerCrossSite(ResponseBuilder rb) {
		return rb.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
	}
	
	protected void logRequestContext() {
		log.info("logRequestContext", "shibIdentitaCodiceFiscale: %s - xRequestID: %s - xForwardedFor: %s - UserPrincipal: %s", shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, securityContext!= null && securityContext.getUserPrincipal()!= null? securityContext.getUserPrincipal().getName() :"DEFAULT");
	}

	protected String generateRequest(String url){
		StringBuilder request = new StringBuilder(url);
		request.append(" RequestHeader." + Constants.SHIB_IDENTITA_DIGITALE + "=" + shibIdentitaCodiceFiscale);
		request.append(" RequestHeader." + Constants.X_CODICE_SERVIZIO + "=" + xCodiceServizio);
		request.append(" RequestHeader." + Constants.X_REQUEST_ID + "=" + xRequestID);
		request.append(" RequestHeader." + Constants.X_FORWARED_FOR + "=" + xForwardedFor);
		return request.toString();
    }
	
	protected String extractXForwadedFor(String xForwadedFor) {
		String result = "";
		if (xForwadedFor.contains(",")) {
			result = xForwadedFor.split(",")[0].trim();
		} else {
			result = xForwadedFor;
		}
		return result;
	}
}
