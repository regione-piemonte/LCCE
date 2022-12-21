/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.service.GetUtentiRuoloService;
import it.csi.configuratorews.business.service.GetUtentiSOLService;
import it.csi.configuratorews.business.service.UtenteService;
import it.csi.configuratorews.dto.configuratorews.ModelUtente;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.interfacews.mapper.ProxyAbilitazioniGetMapper;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.ApplicazioneSolGetUtentiSOLValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloUtentiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.UUID;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class RuoliGetRuoloUtentiService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());

	private String codiceRuolo;

	public RuoliGetRuoloUtentiService(String shibIdentitaCodiceFiscale, String xRequestID,
                                      String xForwardedFor, String xCodiceServizio, String codiceRuolo,
                                      SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceRuolo = codiceRuolo;
	}

	@Autowired
	LogService logService;

	@Autowired
	RuoliGetRuoloUtentiValidator ruoliGetRuoloUtentiValidator;

	@Autowired
	GetUtentiRuoloService getUtentiRuoloService;
	
	@Autowired
	UtenteService utenteService;

	public static final String url_pre = "ruoli/";
	public static final String url_post = "/utenti";

	public static final String NOME_SERVIZIO = "RuoliGetRuoloUtentiService";

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "RuoliGetRuoloUtentiService versione 1.0.0");
		String request = generateRequest(url_pre + codiceRuolo + url_post);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		CsiLogAuditDto csiLogAuditDto = null;
		List<ModelUtenteBase> modelUtenteOutput = null;
        try{
        	
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
				Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.READ, Constants.GET_RUO_UTE, uuidString, xForwadedForInHeader,
					shibIdentitaCodiceFiscale, xRequestID, xCodiceServizio, request);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = ruoliGetRuoloUtentiValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio,
					codiceRuolo);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}

			modelUtenteOutput = getUtentiRuoloService.getUtentiRuolo(codiceRuolo);
			
			utenteService.aggiornaAccessoPua(shibIdentitaCodiceFiscale);

			if(modelUtenteOutput != null) response = modelUtenteOutput.toString();

        }catch(RESTException e){
        	log.error("RuoliGetRuoloUtentiService", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("RuoliGetRuoloUtentiService", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
        	 */
			logService.updateLog(csiLogAuditDto, response);
        }

		return Response.ok(modelUtenteOutput).build();
	}
}
