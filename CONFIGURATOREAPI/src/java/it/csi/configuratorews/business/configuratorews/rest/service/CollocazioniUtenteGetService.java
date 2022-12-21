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
import it.csi.configuratorews.business.service.GetCollocazioniUtenteService;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloUtentiValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class CollocazioniUtenteGetService extends SOLRESTBaseService {

	// SER-15
	/**
	 * 
	 */
	private static final long serialVersionUID = 8697379246452654759L;
	private String ruoloCodice;
	private String codiceFiscale;
	private String limit;
	private String offset;
	private String codiceAzienda;
	private static final String CodiceLogAudit = "AUTH_LOG_145";
	private static final String CodiceServizio = "GETUTEPROFUN-RUOCOL";
	
	public static final String url = "/utenti";
	@Autowired
	LogService logService;
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	RuoliGetRuoloUtentiValidator ruoliGetRuoloUtentiValidator;
	@Autowired
	GetCollocazioniUtenteService getCollocazioniUtenteService;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	@Autowired
	HeaderLimitOffsetValidator headerLimitOffsetValidator;

	@Autowired
	LogAuditService logAuditService;
	
	public CollocazioniUtenteGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio, SecurityContext securityContext,
			HttpHeaders httpHeaders, HttpServletRequest request, String codiceFiscale, String ruoloCodice, String offset, String limit,String codiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceFiscale= codiceFiscale;
		this.ruoloCodice=ruoloCodice;
		this.limit=limit;
		this.offset=offset;
		this.codiceAzienda= codiceAzienda;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "GET Collocazioni Utente");
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
			
			ErroreBuilder aziendaValidatorResponse =  aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if(aziendaValidatorResponse != null) {
				Response returnResponse = aziendaValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorResponse = sistemiRichiedentiValidator.validate(shibIdentitaCodiceFiscale, 
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if(sistemiRichiedentiValidatorResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorResponse.response();
				return returnResponse;
			}
			
			ErroreBuilder sistemiRichiedentiValidatorJWTResponse = sistemiRichiedentiValidator.validateJWT(httpHeaders, xCodiceServizio);
			if(sistemiRichiedentiValidatorJWTResponse != null) {
				Response returnResponse = sistemiRichiedentiValidatorJWTResponse.response();
				return returnResponse;
			} 	
			
			ErroreBuilder ruoloValidatorResponse =  ruoliGetRuoloUtentiValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, ruoloCodice);
			if(ruoloValidatorResponse != null) {
				Response returnResponse = ruoloValidatorResponse.response();
				return returnResponse;
			}
						
			collocazioniUtente = getCollocazioniUtenteService.getCollocazioniUtente(codiceFiscale, ruoloCodice, 
					Integer.parseInt(offset), Integer.parseInt(limit), codiceAzienda);

			itsAllOk = true;

		}  catch(RESTException e){
        	log.error("CollocazioniUtenteGetService", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("CollocazioniUtenteGetService", "Errore rest: ", e);
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
		
		Response responseOk = Response.ok(collocazioniUtente.getListaRis()).build();
		responseOk.getMetadata().add("X-Total-Elements", collocazioniUtente.getCount());
		return responseOk;
	}

}
