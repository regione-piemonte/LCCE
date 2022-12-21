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
import it.csi.configuratorews.business.service.GetRuoloService;
import it.csi.configuratorews.business.service.UtenteService;
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.RuoliGetRuoloValidator;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class RuoliGetService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());

	private Integer limit;
	private Integer offset;
	
	public RuoliGetService(String shibIdentitaCodiceFiscale, String xRequestID,
                           String xForwardedFor, String xCodiceServizio,
                           SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, Integer limit, Integer offset ) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.limit=limit;
		this.offset = offset;
	}

	@Autowired
	LogService logService;

	@Autowired
	RuoliGetRuoloValidator ruoliGetRuoloValidator;

	@Autowired
	GetRuoloService getRuoloService;
	
	@Autowired
	UtenteService utenteService;

	public static final String url_pre = "/ruoli";

	public static final String NOME_SERVIZIO = "RuoliGetService";

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "RuoliGetService versione 1.0.0");
		String request = generateRequest(url_pre);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		CsiLogAuditDto csiLogAuditDto = null;
		Pagination<ModelRuolo> modelRuoloOutput = null;
        try{
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
				Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.READ, Constants.GET_RUO, uuidString, xForwadedForInHeader,
					shibIdentitaCodiceFiscale, xRequestID, xCodiceServizio, request);
			
			//utenteService.aggiornaAccessoPua(shibIdentitaCodiceFiscale);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = ruoliGetRuoloValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio);
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}

			modelRuoloOutput = getRuoloService.getRuolo(limit,offset);

			if(modelRuoloOutput != null) response = modelRuoloOutput.getListaRis().toString();

        }catch(RESTException e){
        	log.error("GetRuoloService", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("GetRuoloService", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
        	 */
			logService.updateLog(csiLogAuditDto, response);
        }
        Response responseOk = Response.ok(modelRuoloOutput.getListaRis()).build();
        responseOk.getMetadata().add("X-Total-Elements", modelRuoloOutput.getCount());
		return responseOk;
	}
}
