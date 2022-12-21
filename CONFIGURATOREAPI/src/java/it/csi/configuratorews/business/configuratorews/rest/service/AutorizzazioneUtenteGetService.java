/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

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

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.service.GetAbilitazioniService;
import it.csi.configuratorews.dto.configuratorews.AutorizzazioneUtente;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.CollocazioneValidator;
import it.csi.configuratorews.validator.FunzionalitaGetValidator;
import it.csi.configuratorews.validator.FunzionalitaIfProfiloValidator;
import it.csi.configuratorews.validator.ProfiloGetValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloUtentiValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class AutorizzazioneUtenteGetService extends SOLRESTBaseService {

	// SER-14
	/**
	 * 
	 */
	private static final long serialVersionUID = 8697379246452654759L;
	private String ruoloCodice;
	private String codiceFiscale;
	private String applicazioneCodice; 
	private String collocazioneCodice;
	private String profiloCodice;
	private String funzionalitaCodice;
	private String codiceAzienda;
	private static final String CodiceLogAudit = "AUTH_LOG_144";
	private static final String CodiceServizio = "GETUTEABIL_RUO_COL";
	
	public static final String url = "/utenti";
	@Autowired
	LogService logService;
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	RuoliGetRuoloUtentiValidator ruoliGetRuoloUtentiValidator;
	@Autowired
	CollocazioneValidator collocazioneValidator;
	@Autowired
	ProfiloGetValidator profiloGetValidator;
	@Autowired
	FunzionalitaGetValidator funzionalitaGetValidator;
	@Autowired
	FunzionalitaIfProfiloValidator funzionalitaIfProfiloValidator;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;

	@Autowired
	LogAuditService logAuditService;
	
	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;
	
	@Autowired
	GetAbilitazioniService getAbilitazioniService;
	/**
	 * CODICE PROFILO E CODICE FUNZIONALITA sono due codici funzione di Auth_t_FUNZIONALITA con due codici tipo differenti FUNZ E PROF
	 * @param shibIdentitaCodiceFiscale
	 * @param xRequestId
	 * @param xForwardedFor
	 * @param xCodiceServizio
	 * @param securityContext
	 * @param httpHeaders
	 * @param request
	 * @param codiceFiscale 
	 * @param ruoloCodice
	 * @param applicazioneCodice
	 * @param collocazioneCodice
	 * @param profiloCodice
	 * @param funzionalitaCodice
	 */
	public AutorizzazioneUtenteGetService(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String ruoloCodice, String applicazioneCodice, String collocazioneCodice,
			String profiloCodice, String funzionalitaCodice,String codiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceFiscale= codiceFiscale;
		this.ruoloCodice=ruoloCodice;
		this.applicazioneCodice = applicazioneCodice;
		this.collocazioneCodice= collocazioneCodice;
		this.profiloCodice= profiloCodice;
		this.funzionalitaCodice=funzionalitaCodice;
		this.codiceAzienda = codiceAzienda;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Autorizzazione Utente");
		String response = "error";
		AutorizzazioneUtente autorizzazione = new AutorizzazioneUtente(); 
		boolean itsAllOk = false;
		try {
			
			ErroreBuilder aziendaValidatorResponse =  aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if(aziendaValidatorResponse != null) {
				Response returnResponse = aziendaValidatorResponse.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator.validate(shibIdentitaCodiceFiscale, 
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if(sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders, xCodiceServizio);
			if(sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			} 	
			
			ErroreBuilder erroreBuilder = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale, xRequestID, 
					xForwardedFor, xCodiceServizio, applicazioneCodice);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			ErroreBuilder ruoloValidatorResponse =  ruoliGetRuoloUtentiValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, ruoloCodice);
			if(ruoloValidatorResponse != null) {
				Response returnResponse = ruoloValidatorResponse.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			ErroreBuilder collocazioneValidatorResponse =  collocazioneValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, collocazioneCodice);
			if(collocazioneValidatorResponse != null) {
				Response returnResponse = collocazioneValidatorResponse.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			if(StringUtils.isNotBlank(profiloCodice)) {
				ErroreBuilder profiloValidatorResponse =  profiloGetValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, profiloCodice);
				if(profiloValidatorResponse != null) {
					Response returnResponse = profiloValidatorResponse.response();
					response = returnResponse.getEntity().toString();
					return returnResponse;
				}
			}
			if(StringUtils.isNotBlank(funzionalitaCodice)) {
				ErroreBuilder funzionalitaValidatorResponse =  funzionalitaGetValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, funzionalitaCodice);
				if(funzionalitaValidatorResponse != null) {
					Response returnResponse = funzionalitaValidatorResponse.response();
					response = returnResponse.getEntity().toString();
					return returnResponse;
				}
				ErroreBuilder funzionalitaIfProfiloValidatorResponse =  funzionalitaIfProfiloValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, funzionalitaCodice,profiloCodice);
				if(funzionalitaIfProfiloValidatorResponse != null) {
					Response returnResponse = funzionalitaIfProfiloValidatorResponse.response();
					response = returnResponse.getEntity().toString();
					return returnResponse;
				}
				
			}
			
			
			log.info("AutorizzazioneUtenteGetService", "getAutorizzazione Utente  cf: "+codiceFiscale
					+ " , applicazioneCodice: "+applicazioneCodice
					+ " , collocazioneCodice: "+collocazioneCodice
					+ " , profiloCodice: "+profiloCodice
					+ " , funzionalitaCodice: "+funzionalitaCodice);
	
			autorizzazione = getAbilitazioniService.getAbilitazioneUtente( codiceFiscale, 
					  ruoloCodice,  applicazioneCodice,  collocazioneCodice,
						 profiloCodice,  funzionalitaCodice,codiceAzienda);

			if(autorizzazione != null) {
				response = autorizzazione.toString();
			} 
			
			itsAllOk = true;

		}  catch(RESTException e){
        	log.error("AutorizzazioneUtenteGetService", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("AutorizzazioneUtenteGetService", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
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
		
		Response responseOk = Response.ok(autorizzazione).build();
		return responseOk;
	}

	
}
