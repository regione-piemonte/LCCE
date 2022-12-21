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
import it.csi.configuratorews.business.service.PostProfiloFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.ResponseWrite;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.ProfiliFunzionalitaPostValidator;
import it.csi.configuratorews.validator.HeaderValidator;
import it.csi.configuratorews.validator.ProfiloGetValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ProfiliFunzionalitaPostService extends SOLRESTBaseService {

	//SER-17
	/**
	 * 
	 */
	private static final long serialVersionUID = 8402086163045525267L;

	private String codiceApplicazione;
	private InserimentoProfiloFunzionalitaBody body;
	private static final String CodiceLogAudit = "AUTH_LOG_146";
	private static final String CodiceServizio = "POSTPROFUN";
	
	private String codiceAzienda;
	private String codiceProfilo;
	private String descrizioneProfilo;
	private String codiceFunzionalita;
	private String descrizioneFunzionalita;

	public static final String url = "/applicazioni/{codice_applicazione}/profili_funzionalita";

	@Autowired
	LogService logService;

	@Autowired
	LogAuditService logAuditService;

	@Autowired
	HeaderValidator headerValidator;

	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;

	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;

	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;

	@Autowired
	ProfiloGetValidator profiloValidator;

	@Autowired
	ProfiliFunzionalitaPostValidator profiliFunzionalitaValidator;
	
	@Autowired
	PostProfiloFunzionalitaService postProfiloFunzionalitaService;

	public ProfiliFunzionalitaPostService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceApplicazione, InserimentoProfiloFunzionalitaBody body,
			String codiceAzienda, String codiceProfilo, String descrizioneProfilo, String codiceFunzionalita,
			String descrizioneFunzionalita) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders,
				request);
		this.body = body;
		this.codiceApplicazione = codiceApplicazione;
		this.codiceAzienda = codiceAzienda;
		this.codiceProfilo = codiceProfilo;
		this.descrizioneProfilo = descrizioneProfilo;
		this.codiceFunzionalita = codiceFunzionalita;
		this.descrizioneFunzionalita = descrizioneFunzionalita;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "ProfiliFunzionalitaPostService");
		ResponseWrite responseWrite = new ResponseWrite();
		responseWrite.setStatus(500);
		responseWrite.setCode("001");
		responseWrite.setTitle("KO");
		boolean itsAllOk = false;
		try {

			/* validazione input */
			ErroreBuilder erroreBuilder = headerValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor,
					xCodiceServizio);
			if (erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				return returnResponse;
			}
			
			/* validazione codice azienda */
			ErroreBuilder aziendaValidatorResponse = aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (aziendaValidatorResponse != null) {
				Response returnResponse = aziendaValidatorResponse.response();
				return returnResponse;
			}

			/* validazione sistema richiedente */
			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator
					.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders, xCodiceServizio);
			if(sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			} 	

			/* validazione codice applicazione */
			ErroreBuilder applicazioneValidatorResponse = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceApplicazione);
			if (applicazioneValidatorResponse != null) {
				Response returnResponse = applicazioneValidatorResponse.response();
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

			/* verifica corretta compilazione dei campi */
			ErroreBuilder campiObbligatoriResponse = profiliFunzionalitaValidator.validateCampiObbligatori(
					codiceFunzionalita, codiceProfilo, descrizioneProfilo, descrizioneFunzionalita);
			if (campiObbligatoriResponse != null) {
				Response returnResponse = campiObbligatoriResponse.response();
				return returnResponse;
			}

			/*
			 * verifica non esistenza codice_profilo per l’applicazione in caso di
			 * inserimento nuovo profilo e verifica lista ruoli
			 */
			ErroreBuilder profiloValidatorResponse = profiloValidator.validateNuovoProfilo(codiceProfilo,
					codiceApplicazione, body);
			if (profiloValidatorResponse != null) {
				Response returnResponse = profiloValidatorResponse.response();
				return returnResponse;
			}

			/*
			 * verifica non esistenza del codice funzionalita verifica esistenza lista
			 * codici profilo per l’applicazione verifica tipologia dato
			 */
			ErroreBuilder profilifunzionalitaValidatorResponse = profiliFunzionalitaValidator
					.validateNuovaFunzionalita(codiceFunzionalita, codiceApplicazione, codiceAzienda, body);
			if (profilifunzionalitaValidatorResponse != null) {
				Response returnResponse = profilifunzionalitaValidatorResponse.response();
				return returnResponse;
			}

			/* se valorizzato solo il profilo, il servizio procede con registrare i dati del
			 * profilo. Se valorizzata la funzionalita', il servizio procede con registrare
			 * i dati della funzionalita' e le associazioni con i profili indicati. */

			if (codiceProfilo != null) {
				postProfiloFunzionalitaService.insertNuovoProfilo(codiceProfilo, descrizioneProfilo,
						codiceApplicazione, shibIdentitaCodiceFiscale, body);
			}

			if (codiceFunzionalita != null) {
				postProfiloFunzionalitaService.insertNuovaFunzionalita(codiceFunzionalita, descrizioneFunzionalita,
						codiceApplicazione, body, codiceAzienda, shibIdentitaCodiceFiscale);
			}

			responseWrite = new ResponseWrite();
			responseWrite.setStatus(200);
			responseWrite.setCode("000");
			responseWrite.setTitle("OK");
			
			itsAllOk = true;


		} catch (RESTException e) {
			log.error("ProfiliFunzionalitaPostService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("ProfiliFunzionalitaPostService", "Errore rest: ", e);
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
