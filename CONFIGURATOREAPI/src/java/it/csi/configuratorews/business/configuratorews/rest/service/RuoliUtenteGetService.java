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
import it.csi.configuratorews.business.service.GetRuoliUtenteService;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;
import it.csi.configuratorews.validator.UtenteValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class RuoliUtenteGetService extends SOLRESTBaseService {

	// SER-24
	private static final long serialVersionUID = 1487198718817237315L;

	private String codiceFiscale;
	private Integer offset;
	private Integer limit;
	private String codiceAzienda;

	private static final String CodiceLogAudit = "AUTH_LOG_153";
	private static final String CodiceServizio = "GET-RUOUTE";
	public static final String url = "/utenti/{codice_fiscale}/ruoli";

	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;

	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	
	@Autowired
	HeaderLimitOffsetValidator headerLimitOffsetValidator;
	
	@Autowired
	UtenteValidator utenteValidator;

	@Autowired
	GetRuoliUtenteService getRuoliUtenteService;

	@Autowired
	LogAuditService logAuditService;

	public RuoliUtenteGetService(String shibIdentitaCodiceFiscale, String xRequestId, String xForwardedFor,
			String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, Integer offset, Integer limit, String codiceAzienda) {

		super(shibIdentitaCodiceFiscale, xRequestId, xForwardedFor, xCodiceServizio, securityContext, httpHeaders,
				request);
		this.codiceFiscale = codiceFiscale;
		this.offset = offset;
		this.limit = limit;
		this.codiceAzienda = codiceAzienda;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Ruoli Utente");
		Pagination<Ruolo> ruoliUtente = null;
		boolean itsAllOk = false;

		try {
			
			/* validazione input */
			ErroreBuilder headerLimitOffsetResponse = headerLimitOffsetValidator.validate(shibIdentitaCodiceFiscale, xRequestID,
					xForwardedFor, xCodiceServizio, limit, offset);
			if (headerLimitOffsetResponse != null) {
				Response returnResponse = headerLimitOffsetResponse.response();
				return returnResponse;
			}
			
			/* validazione codice fiscale */
			ErroreBuilder erroreCFBuilder = utenteValidator.validateCodiceFiscale(codiceFiscale);
			if(erroreCFBuilder != null) {
				Response returnResponse = erroreCFBuilder.response();
				return returnResponse;
			}

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

			log.info("RuoliUtenteGetService",
					" getRuoliUtente Utente  cf: " + codiceFiscale + " , offset: " + offset + " , limit: " + limit);

			ruoliUtente = getRuoliUtenteService.getRuoliUtente(codiceFiscale, offset, limit);

			itsAllOk = true;

		} catch (RESTException e) {
			log.error("RuoliUtenteGetService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("RuoliUtenteGetService", "Errore rest: ", e);
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

		Response responseOk = Response.ok(ruoliUtente.getListaRis()).build();
		responseOk.getMetadata().add("X-Total-Elements", ruoliUtente.getCount());
		return responseOk;
	}

}
