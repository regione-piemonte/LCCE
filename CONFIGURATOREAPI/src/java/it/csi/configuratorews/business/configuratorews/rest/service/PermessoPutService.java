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
import it.csi.configuratorews.business.service.PutPermessoService;
import it.csi.configuratorews.dto.configuratorews.ListaPermessi;
import it.csi.configuratorews.dto.configuratorews.ResponseWrite;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.FunzionalitaPerApplicazioneValidator;
import it.csi.configuratorews.validator.PermessiValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PermessoPutService extends SOLRESTBaseService {

	// SER-23
	/**
	 * 
	 */
	private static final long serialVersionUID = 5150826535386945749L;
	private String codiceApplicazione;
	private String codiceFunzionalita;
	private String codiceAzienda;
	private ListaPermessi permessi;
	private static final String CodiceLogAudit = "AUTH_LOG_150";
	private static final String CodiceServizio = "PUTFUN_DATIPER";

	@Autowired
	LogAuditService logAuditService;

	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	@Autowired
	FunzionalitaPerApplicazioneValidator funzionalitaPerApplicazioneValidator;
	@Autowired
	PermessiValidator permessiValidator;

	@Autowired
	PutPermessoService putPermessoService;

	@Autowired
	LogService logService;
	public static final String url = "/applicazioni/{codice_applicazione}/funzionalita/{codice}/tipologia_dato_permesso";

	public PermessoPutService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceApplicazione, String codiceFunzionalita, String codiceAzienda,
			ListaPermessi permessi) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders,
				request);
		this.codiceApplicazione = codiceApplicazione;
		this.codiceFunzionalita = codiceFunzionalita;
		this.codiceAzienda = codiceAzienda;
		this.permessi = permessi;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PermessoPutService versione 1.0.0");
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

			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator
					.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}

			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders,
					xCodiceServizio);
			if (sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			}

			ErroreBuilder erroreBuilder = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale, xRequestID,
					xForwardedFor, xCodiceServizio, codiceApplicazione);
			if (erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				return returnResponse;
			}

			ErroreBuilder funzioneValidatorResponse = funzionalitaPerApplicazioneValidator.validate(codiceFunzionalita,
					codiceApplicazione);
			if (funzioneValidatorResponse != null) {
				Response returnResponse = funzioneValidatorResponse.response();
				return returnResponse;
			}

			ErroreBuilder permessiValidatorResponse = permessiValidator.validate(permessi);
			if (permessiValidatorResponse != null) {
				Response returnResponse = permessiValidatorResponse.response();
				return returnResponse;
			}

			putPermessoService.updatePermesso(codiceFunzionalita, codiceApplicazione, shibIdentitaCodiceFiscale,
					codiceAzienda, permessi);

			responseWrite = new ResponseWrite();
			responseWrite.setStatus(200);
			responseWrite.setCode("000");
			responseWrite.setTitle("OK");

			itsAllOk = true;

		} catch (RESTException e) {
			log.error("PermessoPutService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("PermessoPutService", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/*
			 * Update logAudit
			 */
			try {
				// String uuidString = UUID.randomUUID().toString();
				// String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				// String request = generateRequest(url);
				if (itsAllOk) {
					if (logAuditService.insertLogAudit(request, CodiceLogAudit, shibIdentitaCodiceFiscale,
							CodiceServizio, xForwardedFor, xRequestID, xCodiceServizio)) {
						// OK
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
