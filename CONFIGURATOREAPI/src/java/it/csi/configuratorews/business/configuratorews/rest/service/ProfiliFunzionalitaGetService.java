/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.service.GetProfiliFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalita;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ProfiliFunzionalitaGetService  extends SOLRESTBaseService {

	// SER-11
	/**
	 * 
	 */
	private static final long serialVersionUID = -6750614391685697971L;
	private String codiceApplicazione;
	private String limit;
	private String offset;
	private String codiceAzienda;
	private static final String CodiceLogAudit = "AUTH_LOG_141";
	private static final String CodiceServizio = "GETPROFUN";
	
	@Autowired
	HeaderLimitOffsetValidator headerLimitOffsetValidator;	
	@Autowired
	GetProfiliFunzionalitaService getProfiliFunzionalitaService;
	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;

	@Autowired
	LogAuditService logAuditService;
	
	@Autowired
	LogService logService;
	
	public static final String url = "/applicazioni/{codice_applicazione}/profili_funzionalita";
	
	public ProfiliFunzionalitaGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request,String codiceApplicazione, String limit, 
			String offset, String codiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceApplicazione = codiceApplicazione;
		this.limit=limit;
		this.offset= offset;
		this.codiceAzienda= codiceAzienda;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Profili Funzionalita");
		Pagination<ProfiloFunzionalita> profiliFunzionalita = null;
		boolean itsAllOk = false;
		try {
			
			/* validazione input */
			ErroreBuilder headerLimitOffsetResponse = headerLimitOffsetValidator.validate(shibIdentitaCodiceFiscale, xRequestID,
					xForwardedFor, xCodiceServizio, limit, offset);
			if (headerLimitOffsetResponse != null) {
				Response returnResponse = headerLimitOffsetResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder aziendaValidatorResponse =  aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if(aziendaValidatorResponse != null) {
				Response returnResponse = aziendaValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder erroreBuilder = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceApplicazione);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator.validate(shibIdentitaCodiceFiscale, 
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if(sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders, xCodiceServizio);
			if(sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			} 	
						
			profiliFunzionalita = getProfiliFunzionalitaService.getProfiliFunzionalita(codiceApplicazione, Integer.parseInt(limit), 
					Integer.parseInt(offset), codiceAzienda);

			itsAllOk = true;

		}  catch(RESTException e){
        	log.error("PreferenceGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("PreferenceGet", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/* Update logAudit */
			try {
				//String uuidString = UUID.randomUUID().toString();
				//String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				//String request = generateRequest(url); 
				if (itsAllOk) {
					if (logAuditService.insertLogAudit(request, CodiceLogAudit, shibIdentitaCodiceFiscale, CodiceServizio, xForwardedFor, xRequestID, xCodiceServizio)) {
						//OK
						log.info(METHOD_NAME, "log Audit inserito correttamente");
					} else {
						throw new Exception("Errore inserimento log Audit");
					}
				}
			} catch (Exception e) {
				log.error("Errore log audit", "Codice log Audit: " + CodiceLogAudit, e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
        }
		
		Response responseOk = Response.ok(profiliFunzionalita.getListaRis()).build();
		responseOk.getMetadata().add("X-Total-Elements", profiliFunzionalita.getCount());
		return responseOk;
	}

}
