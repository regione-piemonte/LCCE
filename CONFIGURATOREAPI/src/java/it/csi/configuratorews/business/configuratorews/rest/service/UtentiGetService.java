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
import it.csi.configuratorews.business.service.GetUtentiProfiliFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.UtenteApplicazioni;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.CollocazioneNotMandatoryValidator;
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloUtentiValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class UtentiGetService extends SOLRESTBaseService {
	
	//SER-19
	/**
	 * 
	 */
	private static final long serialVersionUID = -5274934311803764890L;
	private String ruolo;
	private String collocazione;
	private String codiceAzienda;
	private String limit;
	private String offset;
	private static final String CodiceLogAudit = "AUTH_LOG_143";
	private static final String CodiceServizio = "GETPROFUNUTE-RUOCOL";

	public static final String url = "/utenti";
	@Autowired
	LogService logService;
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	RuoliGetRuoloUtentiValidator ruoliGetRuoloUtentiValidator;
	@Autowired
	CollocazioneNotMandatoryValidator collocazioneValidator;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	@Autowired
	GetUtentiProfiliFunzionalitaService getUtentiApplicazioniService;
	@Autowired
	HeaderLimitOffsetValidator headerLimitOffsetValidator;

	@Autowired
	LogAuditService logAuditService;
	
	public UtentiGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest request, String ruolo, String collocazione, String limit, String offset, String codiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.collocazione = collocazione;
		this.ruolo = ruolo;
		this.limit = limit;
		this.offset = offset;
		this.codiceAzienda = codiceAzienda;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Utenti Profilo");
		Pagination<UtenteApplicazioni> utente = null;
		boolean itsAllOk = false;
		try {
			
			/* validazione input */
			ErroreBuilder headerLimitOffsetResponse = headerLimitOffsetValidator.validate(shibIdentitaCodiceFiscale, xRequestID,
					xForwardedFor, xCodiceServizio, limit, offset);
			if (headerLimitOffsetResponse != null) {
				Response returnResponse = headerLimitOffsetResponse.response();
				return returnResponse;
			}

			ErroreBuilder aziendaValidatorResponse = aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (aziendaValidatorResponse != null) {
				Response returnResponse = aziendaValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor,
					xCodiceServizio, codiceAzienda);
			if (sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders, xCodiceServizio);
			if(sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			} 	
			
			ErroreBuilder ruoloValidatorResponse = ruoliGetRuoloUtentiValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio,
					ruolo);
			if (ruoloValidatorResponse != null) {
				Response returnResponse = ruoloValidatorResponse.response();
				return returnResponse;
			}
			ErroreBuilder collocazioneValidatorResponse = collocazioneValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio,
					collocazione);
			if (collocazioneValidatorResponse != null) {
				Response returnResponse = collocazioneValidatorResponse.response();
				return returnResponse;
			}
			
			utente = getUtentiApplicazioniService.getUtentiApplicazione(ruolo,collocazione, codiceAzienda, Integer.parseInt(limit), Integer.parseInt(offset));
						
			itsAllOk = true;

		} catch (RESTException e) {
			log.error("UtentiGetService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("UtentiGetService", "Errore rest: ", e);
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

		Response responseOk = Response.ok(utente.getListaRis()).build();
		responseOk.getMetadata().add("X-Total-Elements", utente.getCount());
		return responseOk;
	}
}
