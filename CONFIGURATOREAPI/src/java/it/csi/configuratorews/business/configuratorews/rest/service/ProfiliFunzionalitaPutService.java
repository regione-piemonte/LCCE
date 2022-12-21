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

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.service.PutAbilitazioniProfiloPreferenzeService;
import it.csi.configuratorews.business.service.PutProfiloFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.ResponseWrite;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;
import it.csi.configuratorews.validator.FieldMandatoryValidator;
import it.csi.configuratorews.validator.ProfiloOrFunzionalitaGetValidator;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ProfiliFunzionalitaPutService extends SOLRESTBaseService {

	// SER-12
	/**
	 * 
	 */
	private static final long serialVersionUID = 5126406629258188560L;
	private String codiceApplicazione;
	private String codiceFunzionalita;
	private String codiceAzienda;
	private ProfiloFunzionalitaBody body;
	private static final String CodiceLogAudit = "AUTH_LOG_142";
	private static final String CodiceServizio = "PUTPROFUN";

	@Autowired
	PutProfiloFunzionalitaService putProfiloFunzionalitaService;
	@Autowired
	PutAbilitazioniProfiloPreferenzeService putAbilitazioniProfiloService;
	@Autowired
	SistemiRichiedentiValidator sistemiRichiedentiValidator;
	@Autowired
	ApplicazioneSolValidator applicazioneSolValidator;
	@Autowired
	FieldMandatoryValidator fieldMandatoryValidator;
	@Autowired
	ProfiloOrFunzionalitaGetValidator profiloOrFunzionalitaGetValidator;
	@Autowired
	AziendaGetCollocazioniValidator aziendaGetCollocazioniValidator;
	@Autowired
	LogService logService;

	@Autowired
	LogAuditService logAuditService;
	
	public static final String url = "/applicazioni/{codice_applicazione}/profili_funzionalita/{codice_funzionalita}";
	
	public ProfiliFunzionalitaPutService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String codiceApplicazione, String codiceFunzionalita,
			ProfiloFunzionalitaBody body, String codiceAzienda) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceApplicazione = codiceApplicazione;
		this.body = body;
		this.codiceFunzionalita = codiceFunzionalita;
		this.codiceAzienda = codiceAzienda;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "ProfiliFunzionalitaModifyService versione 1.0.0");
		String response = "error";
		ResponseWrite responseWrite = new ResponseWrite();
		responseWrite.setStatus(500);
		responseWrite.setCode("001");
		responseWrite.setTitle("KO");
		boolean itsAllOk = false;
		try {
			
			// validazioni
			ErroreBuilder aziendaValidatorResponse = aziendaGetCollocazioniValidator.validate(shibIdentitaCodiceFiscale,
					xRequestID, xForwardedFor, xCodiceServizio, codiceAzienda);
			if (aziendaValidatorResponse != null) {
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
			
			ErroreBuilder erroreBuilder = applicazioneSolValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, codiceApplicazione);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			
			/* Verifica blocco modifica su codice applicazione */
			ErroreBuilder applicazioneModificabileValidatorResponse = applicazioneSolValidator.validateFlagBloccoModifica(codiceApplicazione);
			if (applicazioneModificabileValidatorResponse != null) {
				Response returnResponse = applicazioneModificabileValidatorResponse.response();
				return returnResponse;
			}
			
			String[] valoriAmmessi = {FunzionalitaLowDaoImpl.CODICE_TIPO_FUNZIONALITA,FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO};
			ErroreBuilder erroreTipoBuilder = fieldMandatoryValidator.validate(body.getTipo(), Constants.FUNZIONALITA_TIPO,valoriAmmessi);
			if(erroreTipoBuilder != null) {
				Response returnResponse = erroreTipoBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			// VALIDATORE funzionalita/profilo esistente legato all'applicazione
			ErroreBuilder erroreProfiloFunzionalita = profiloOrFunzionalitaGetValidator.validate( shibIdentitaCodiceFiscale,  xRequestID,
					  xForwardedFor,  xCodiceServizio,  codiceFunzionalita,body.getTipo(),codiceApplicazione );
			if(erroreProfiloFunzionalita != null) {
				Response returnResponse = erroreProfiloFunzionalita.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			/*
			if (body.getDataFineValidita() != null) {
				//TODO Se l'invalidazione rimarra' una data realizzare validator
				// altrimenti rimuovere errore dalle costanti constats.java
				// AUTH_ER_638  Il campo data_fine_validita deve contenere la data corrente.
				// aggiorna preferenze
				// aggiorna abilitazioni
				// aggiorna tree profilo funzionalita
				putAbilitazioniProfiloService.updateAbilitazioniProfiloPreferenze(codiceFunzionalita, codiceApplicazione, body, body.getDataFineValidita(),
						shibIdentitaCodiceFiscale);

			}
			*/
			if (body.getDescrizione() != null) {
				// aggiorna descrizione auth_t_funzionalita
				putProfiloFunzionalitaService.updateProfiloFunzionalita(codiceFunzionalita, codiceApplicazione, body, codiceAzienda,
						shibIdentitaCodiceFiscale);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			responseWrite = new ResponseWrite();
			responseWrite.setStatus(200);
			responseWrite.setCode("000");
			responseWrite.setTitle("OK");
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseWrite);
			
			itsAllOk = true;

		} catch (RESTException e) {
			log.error("ProfiliFunzionalitaModifyService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("ProfiliFunzionalitaModifyService", "Errore rest: ", e);
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
