/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.service.GetRuoloService;
import it.csi.configuratorews.business.service.UtenteService;
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.UtenteValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class UtenteGetService extends SOLRESTBaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected LogUtil log = new LogUtil(this.getClass());

	public UtenteGetService(String shibIdentitaCodiceFiscale, String xRequestID,
                           String xForwardedFor, String xCodiceServizio,
                           SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
	}

	@Autowired
	LogService logService;

	@Autowired
	UtenteValidator utenteValidator;

	@Autowired
	UtenteService utenteService;

	public static final String url_pre = "/utente";

	public static final String NOME_SERVIZIO = "UtenteService";

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, " Start");
		String request = generateRequest(url_pre);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		CsiLogAuditDto csiLogAuditDto = null;
		ModelUtenteBase utenteInfo = new ModelUtenteBase();
        try{
        	
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
			 * validazione parametri di input
			 */
			ErroreBuilder erroreBuilder = utenteValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			utenteInfo = utenteService.getUtenteInfo(shibIdentitaCodiceFiscale);

			if(utenteInfo != null) response = utenteInfo.toString();

        }catch(RESTException e){
        	log.error("UtenteGetService", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("UtenteGetService", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }

		return Response.ok(utenteInfo).build();
	}
}
