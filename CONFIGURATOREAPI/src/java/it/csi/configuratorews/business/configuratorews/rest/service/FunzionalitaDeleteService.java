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
import it.csi.configuratorews.business.service.PutAbilitazioniProfiloPreferenzeService;
import it.csi.configuratorews.dto.configuratorews.ResponseWrite;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.FunzionalitaPerApplicazioneValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class FunzionalitaDeleteService extends SOLRESTBaseService{
	
	// SER-22
	/**
	 * 
	 */
	private static final long serialVersionUID = 3563740487737929686L;
	private String codiceApplicazione;
	private String codiceFunzionalita;
	private String codiceAzienda;
	private static final String CodiceLogAudit = "AUTH_LOG_149";
	private static final String CodiceServizio = "DELFUN";
	
	@Autowired
	LogService logService;

	@Autowired
	LogAuditService logAuditService;
	
	public FunzionalitaDeleteService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String codiceApplicazione, String codiceFunzionalita,
			String codiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceApplicazione = codiceApplicazione;
		this.codiceFunzionalita = codiceFunzionalita;
		this.codiceAzienda = codiceAzienda;
	}
	@Autowired
	PutAbilitazioniProfiloPreferenzeService putAbilitazioniProfiloService;
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	@Autowired
	FunzionalitaPerApplicazioneValidator funzionalitaPerApplicazioneValidator;
	
	public static final String url = "/applicazioni/{codice_applicazione}/funzionalita/{codice_funzionalita}";
	
	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "FunzionalitaDeleteService versione 1.0.0");
		ResponseWrite responseWrite = new ResponseWrite();
		responseWrite.setStatus(500);
		responseWrite.setCode("001");
		responseWrite.setTitle("KO");
		boolean itsAllOk = false;
		try {
			
			ErroreBuilder aziendaValidatorResponse = aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (aziendaValidatorResponse != null) {
				Response returnResponse = aziendaValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator.validate(shibIdentitaCodiceFiscale, xRequestID, 
					xForwardedFor, xCodiceServizio, codiceAzienda);
			if(sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders, xCodiceServizio);
			if(sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			} 	
			
			ErroreBuilder erroreBuilder = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceApplicazione);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				return returnResponse;
			}
			
			/* Verifica blocco modifica su codice applicazione */
			ErroreBuilder applicazioneModificabileValidatorResponse = applicazioneSolValidator.validateFlagBloccoModifica(codiceApplicazione);
			if (applicazioneModificabileValidatorResponse != null) {
				Response returnResponse = applicazioneModificabileValidatorResponse.response();
				return returnResponse;
			}
			
			/* validazione coerenza codice azienda e legame applicazione-azienda */
			ErroreBuilder applicazioneAziendaValidatorResponse = applicazioneSolValidator.validateCoerenzaAzienda(codiceApplicazione, codiceAzienda);
			if (applicazioneAziendaValidatorResponse != null) {
				Response returnResponse = applicazioneAziendaValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder funzioneValidatorResponse = funzionalitaPerApplicazioneValidator.validate(codiceFunzionalita,codiceApplicazione);
			if (funzioneValidatorResponse != null) {
				Response returnResponse = funzioneValidatorResponse.response();
				return returnResponse;
			}
			
			putAbilitazioniProfiloService.updateFunzionalita(codiceFunzionalita, codiceApplicazione, shibIdentitaCodiceFiscale, codiceAzienda);

			responseWrite = new ResponseWrite();
			responseWrite.setStatus(200);
			responseWrite.setCode("000");
			responseWrite.setTitle("OK");
			
			itsAllOk = true;

		} catch (RESTException e) {
			log.error("FunzionalitaDeleteService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("FunzionalitaDeleteService", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/*
			 * Update logAudit
			 */
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
		
		return Response.ok(responseWrite).build();
	}
}
