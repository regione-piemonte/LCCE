/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.dto.configuratorews.ContattiDigitali;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PreferencesUserGetService extends SOLRESTBaseService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String CODICE_LOG_AUDIT = "AUTH_LOG_164";
	private static final String CODICE_SERVIZIO = "GETCONTATTI";
	@Autowired
	LogAuditService logAuditService;
	@Autowired
	UtenteLowDao utente;
	
	private String cf;
	
	public static final String url = "/preferencesUsers/{utente}/search";
	
	public PreferencesUserGetService(String shibIdentitaCodiceFiscale, String xRequestID,
            String xForwardedFor, String xCodiceServizio,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String input) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.cf = input;
	}
	
	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PreferencesUserGetService versione 1.0.0");
		String response = "error";
		try {
			if(StringUtils.isEmpty(cf)) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			Collection<UtenteDto> utenteDTO = utente.findByCodiceFiscaleValido(cf);
			ContattiDigitali cd = new ContattiDigitali();
			if(utenteDTO != null && utenteDTO.size()==1){
				for(UtenteDto utente: utenteDTO) {
					cd.setEmail(utente.getEmailPreferenze());
					cd.setTelefono(utente.getTelefonoPreferenze());
					if(utente.getTokenPush()!=null) {
						List<String> utentePush = Arrays.asList(StringUtils.split(utente.getTokenPush(), ","));
						cd.setTokenPush(utentePush);
					}
				}
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cd);
			return Response.ok(cd).build();
		}  catch(RESTException e){
        	log.error("PreferenceGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("PreferenceGet", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/* Update logAudit */
			try {
				String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				if (logAuditService.insertLogAudit(request, CODICE_LOG_AUDIT, shibIdentitaCodiceFiscale, CODICE_SERVIZIO, xForwadedForInHeader, xRequestID, xCodiceServizio)) {
					log.info(METHOD_NAME, "log Audit in PreferencesUserGetService inserito correttamente");
				} else {
					throw new Exception("Errore inserimento log Audit in PreferencesUserGetService");
				}
			} catch (Exception e) {
				log.error("PreferencesUserGetService", "log audit: ", e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
        }
	}

}
