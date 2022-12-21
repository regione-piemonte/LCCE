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
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.service.GetProfiliFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.ModelFunzionalitaProfilo;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class FunzionalitaApplicazioneGetService extends SOLRESTBaseService {

	// SER-26
	/**
	 * 
	 */
	private static final long serialVersionUID = -2863224417913802351L;
	private static final String CodiceLogAudit = "AUTH_LOG_152";
	private static final String CodiceServizio = "GETFUNZ";
	public static final String url = "/applicazioni/{applicazione}/funzionalita";
	public static final String NOME_SERVIZIO = "FunzionalitaApplicazioneGetService";

	private String codiceApplicazione;
	private String codiceAzienda;
	private String limit;
	private String offset;

	@Autowired
	LogAuditService logAuditService;

	@Autowired
	HeaderLimitOffsetValidator headerLimitOffsetValidator;

	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;

	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;

	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;

	@Autowired
	GetProfiliFunzionalitaService getProfiliFunzionalitaService;

	public FunzionalitaApplicazioneGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceApplicazione, String codiceAzienda, String limit, String offset) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders,
				request);
		this.codiceApplicazione = codiceApplicazione;
		this.codiceAzienda = codiceAzienda;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	protected Response execute() {

		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Funzionalita profili");
		Pagination<ModelFunzionalitaProfilo> funzionalita = null;
		boolean itsAllOk = false;

		try {

			/* validazione input */
			ErroreBuilder headerLimitOffsetResponse = headerLimitOffsetValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, limit, offset);
			if (headerLimitOffsetResponse != null) {
				Response returnResponse = headerLimitOffsetResponse.response();
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

			/* validazione codice applicazione */
			ErroreBuilder erroreBuilder = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale, xRequestID,
					xForwardedFor, xCodiceServizio, codiceApplicazione);
			if (erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				return returnResponse;
			}

			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders,
					xCodiceServizio);
			if (sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			}

			funzionalita = getProfiliFunzionalitaService.getFunzionalitaProfiliByApp(codiceApplicazione, codiceAzienda,
					Integer.parseInt(limit), Integer.parseInt(offset));

			itsAllOk = true;

		} catch (RESTException e) {
			log.error("ApplicazioniGetService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("ApplicazioniGetService", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/* Update logAudit */
			try {
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
		Response responseOk = Response.ok(funzionalita.getListaRis()).build();
		responseOk.getMetadata().add("X-Total-Elements", funzionalita.getCount());
		return responseOk;
	}

}
