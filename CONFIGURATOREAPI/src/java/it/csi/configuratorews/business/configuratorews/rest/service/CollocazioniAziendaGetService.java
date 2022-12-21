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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.service.GetCollocazioniAziendaService;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;
import it.csi.configuratorews.validator.StrutturaGetCollocazioniValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class CollocazioniAziendaGetService extends SOLRESTBaseService {

	// SER-25
	/**
	 * 
	 */
	private static final long serialVersionUID = 8697379246452654759L;
	private String codiceAzienda;
	private String codiceStruttura;
	private Integer limit;
	private Integer offset;

	private static final String CodiceLogAudit = "AUTH_LOG_154";
	private static final String CodiceServizio = "GET-COLAZIE";
	public static final String url = "/collocazioni/{codice_azienda}";

	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;

	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;

	@Autowired
	StrutturaGetCollocazioniValidator strutturaGetCollocazioniValidator;
	
	@Autowired
	HeaderLimitOffsetValidator headerLimitOffsetValidator;

	@Autowired
	GetCollocazioniAziendaService getCollocazioniAziendaService;

	@Autowired
	LogAuditService logAuditService;

	public CollocazioniAziendaGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceAzienda, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceStruttura, Integer limit, Integer offset) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders,
				request);
		this.codiceAzienda = codiceAzienda;
		this.codiceStruttura = codiceStruttura;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	protected Response execute() {

		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Collocazioni by Azienda");
		Pagination<CollocazioneUtente> collocazioniUtente = null;
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

			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator
					.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}

			if (StringUtils.isNotBlank(codiceStruttura)) {
				ErroreBuilder strutturaValidatorResponse = strutturaGetCollocazioniValidator.validate(
						shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceStruttura);
				if (strutturaValidatorResponse != null) {
					Response returnResponse = strutturaValidatorResponse.response();
					return returnResponse;
				}
			}

			log.info("CollocazioniAziendaGetService", "getCollocazioniAziendaService codiceAzienda: " + codiceAzienda
					+ ",codiceStruttura: " + codiceStruttura + ", offset: " + offset + ", limit: " + limit);

			collocazioniUtente = getCollocazioniAziendaService.getCollocazioniAzienda(codiceAzienda, codiceStruttura,
					limit, offset);

			itsAllOk = true;

		} catch (RESTException e) {
			log.error("CollocazioniUtenteGetService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("CollocazioniUtenteGetService", "Errore rest: ", e);
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

		Response responseOk = Response.ok(collocazioniUtente.getListaRis()).build();
		responseOk.getMetadata().add("X-Total-Elements", collocazioniUtente.getCount());
		return responseOk;
	}

}
