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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.service.GetUtentiService;
import it.csi.configuratorews.dto.configuratorews.ModelUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.ApplicazioneSolGetUtentiSOLValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ApplicazioneSolGetUtentiSOLService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());

	private String applicazione;
	private String codiceAzienda;
	private Integer limit;
	private Integer offset;
	private Boolean checkCodiceAzienda;

	public ApplicazioneSolGetUtentiSOLService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			String applicazione, String codiceAzienda, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request,
			Boolean checkCodiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.applicazione = applicazione;
		this.codiceAzienda = codiceAzienda;
		this.checkCodiceAzienda = checkCodiceAzienda;
	}

	public ApplicazioneSolGetUtentiSOLService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String applicazione, String codiceAzienda, Integer limit,
			Integer offset, Boolean checkCodiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.applicazione = applicazione;
		this.codiceAzienda = codiceAzienda;
		this.limit = limit;
		this.offset = offset;
		this.checkCodiceAzienda = checkCodiceAzienda;
	}

	@Autowired
	LogService logService;

	@Autowired
	ApplicazioneSolGetUtentiSOLValidator applicazioneSolGetUtentiSOLValidator;
	
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;

	@Autowired
	GetUtentiService getUtentiService;

	public static final String url_pre = "sol/";
	public static final String url_post = "/utenti";

	public static final String NOME_SERVIZIO = "ApplicazioneSolGetUtentiSOLService";

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "ApplicazioneSolGetUtentiSOLService versione 1.0.0");
		String request = generateRequest(url_pre + applicazione + url_post);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		CsiLogAuditDto csiLogAuditDto = null;
		Pagination<ModelUtente> modelUtenteOutput = null;
		try {
			String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
			 * Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.READ, Constants.GET_UTE_SOL, uuidString, xForwadedForInHeader, shibIdentitaCodiceFiscale,
					xRequestID, xCodiceServizio, request);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = applicazioneSolGetUtentiSOLValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader,
					xCodiceServizio, applicazione, codiceAzienda);
			
			/* validazione azienda: se la chiamata proviene da interfaccia ApplicazioniApi va effettuata la validazione codice azienda */
			if(checkCodiceAzienda) {
				/* validazione codice azienda */
				ErroreBuilder aziendaValidatorResponse = aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale,
						xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
				if (aziendaValidatorResponse != null) {
					Response returnResponse = aziendaValidatorResponse.response();
					response = returnResponse.getEntity().toString();
					return returnResponse;
				}
				
				/* validazione sistema richiedente */
				ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator
						.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
				if (sistemiRichiedentiValidatorResponse != null) {
					Response returnResponse = sistemiRichiedentiValidatorResponse.response();
					response = returnResponse.getEntity().toString();
					return returnResponse;
				}
			}

			if (erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}

			modelUtenteOutput = getUtentiService.getUtentiSol(applicazione, codiceAzienda, limit, offset);

			if (modelUtenteOutput != null && modelUtenteOutput.getListaRis() != null)
				response = modelUtenteOutput.getListaRis().toString();

		} catch (RESTException e) {
			log.error("ApplicazioneSolGetUtentiSOLService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("ApplicazioneSolGetUtentiSOLService", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() + " " + Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/*
			 * Update logAudit
			 */
			logService.updateLog(csiLogAuditDto, response);
		}

		Response responseOk = Response.ok(modelUtenteOutput.getListaRis()).build();
		if (modelUtenteOutput.getCount() != null) {
			responseOk.getMetadata().add("X-Total-Elements", modelUtenteOutput.getCount());
		}
		return responseOk;
	}
}
