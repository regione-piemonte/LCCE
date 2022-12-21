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
import it.csi.configuratorews.business.service.GetAziendaUtenteService;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.AbilitazioniValidator;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.CollocazioneValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloUtentiValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;
import it.csi.configuratorews.validator.UtenteValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class AziendaUtenteGetService extends SOLRESTBaseService {

	// SER-27
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codiceFiscale;
	private String codiceRuolo;
	private String codiceApplicazione;
	private String codiceCollocazione;

	private static final String CodiceLogAudit = "AUTH_LOG_168";
	private static final String CodiceServizio = "GET-CODAZIENDA";
	public static final String url = "/utenti/{codice_fiscale}/azienda";

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	UtenteValidator utenteValidator;

	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;

	@Autowired
	CollocazioneValidator collocazioneValidator;

	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;

	@Autowired
	RuoliGetRuoloUtentiValidator ruoliGetRuoloUtentiValidator;

	@Autowired
	AbilitazioniValidator abilitazioniValidator;

	@Autowired
	GetAziendaUtenteService getAziendaUtenteService;

	@Autowired
	LogAuditService logAuditService;

	public AziendaUtenteGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest request, String codiceCollocazione, String codiceRuolo, String codiceApplicazione) {

		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders,
				request);

		this.codiceApplicazione = codiceApplicazione;
		this.codiceCollocazione = codiceCollocazione;
		this.codiceFiscale = codiceFiscale;
		this.codiceRuolo = codiceRuolo;

	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Codice Azienda");
		String codiceAzienda = null;
		boolean itsAllOk = false;

		try {

			/* validazione header e codice collocazione */
			ErroreBuilder erroreBuilder = collocazioneValidator.validate(shibIdentitaCodiceFiscale, xRequestID,
					xForwardedFor, xCodiceServizio, codiceCollocazione);
			if (erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				return returnResponse;
			}

			/* validazione codice fiscale */
			ErroreBuilder erroreCFBuilder = utenteValidator.validateCodiceFiscale(codiceFiscale);
			if (erroreCFBuilder != null) {
				Response returnResponse = erroreCFBuilder.response();
				return returnResponse;
			}

			/* validazione codice ruolo */
			ErroreBuilder erroreRuoloBuilder = ruoliGetRuoloUtentiValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceRuolo);
			if (erroreRuoloBuilder != null) {
				Response returnResponse = erroreRuoloBuilder.response();
				return returnResponse;
			}

			/* validazione codice applicazione */
			ErroreBuilder applicazioneValidatorResponse = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceApplicazione);
			if (applicazioneValidatorResponse != null) {
				Response returnResponse = applicazioneValidatorResponse.response();
				return returnResponse;
			}

			/* verifica abilitazione attiva dati i parametri passati */
			ErroreBuilder abilitazioneUtenteResponse = abilitazioniValidator.verificaAbilitazioneUtente(codiceFiscale,
					codiceCollocazione, codiceRuolo, codiceApplicazione);
			if (abilitazioneUtenteResponse != null) { // AUTH_ER_668
				Response returnResponse = abilitazioneUtenteResponse.response();
				return returnResponse;
			}

			codiceAzienda = getAziendaUtenteService.getAzienda(codiceFiscale, codiceCollocazione, codiceRuolo,
					codiceApplicazione);
			
			itsAllOk = true;

		} catch (RESTException e) {
			log.error("AziendaUtenteGetService", "Errore RESTException: ", e);
			throw e;
		} catch (Exception e) {
			log.error("AziendaUtenteGetService", "Errore Exception: ", e);
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

		Response responseOk = Response.ok(codiceAzienda).build();
		return responseOk;

	}

}
