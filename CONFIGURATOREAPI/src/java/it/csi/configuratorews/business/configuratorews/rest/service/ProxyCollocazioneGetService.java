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
import it.csi.configuratorews.dto.configuratorews.ModelAbilitazione;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.interfacews.client.abilitazione.AbilitazioniServiceClient;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.client.collocazioni.CollocazioniServiceClient;
import it.csi.configuratorews.interfacews.client.collocazioni.GetCollocazioniResponse;
import it.csi.configuratorews.interfacews.mapper.ProxyAbilitazioniGetMapper;
import it.csi.configuratorews.interfacews.mapper.ProxyCollocazioniGetMapper;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.ProxyAbilitazioniGetValidator;
import it.csi.configuratorews.validator.ProxyCollocazioneGetValidator;

import org.apache.cxf.endpoint.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ProxyCollocazioneGetService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());

	private String codiceRuolo;

	public ProxyCollocazioneGetService(String shibIdentitaCodiceFiscale, String xRequestID,
                                       String xForwardedFor, String xCodiceServizio,String codiceRuolo,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceRuolo = codiceRuolo;
	}

	@Autowired
	LogService logService;

	@Autowired
	ProxyCollocazioneGetValidator proxyCollocazioneGetValidator;

	@Autowired
	ProxyCollocazioniGetMapper proxyCollocazioniGetMapper;

	@Autowired
	CollocazioniServiceClient collocazioniServiceClient;

	public static final String url = "/login/collocazioni";

	public static final String NOME_SERVIZIO = "ProxyCollocazioniGetService";

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "ProxyCollocazioniGetService versione 1.0.0");
		String request = generateRequest(url);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		ModelCollocazione[] arrayModelCollocazioni = null;
		CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();
        try{
        	
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
				Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.SOAP, Constants.PROXY_COLLOCAZIONE, uuidString, xForwadedForInHeader,
					shibIdentitaCodiceFiscale, xRequestID, xCodiceServizio, request);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = proxyCollocazioneGetValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio,
					codiceRuolo);
			
			if(erroreBuilder != null) {
				Response returnResponse = erroreBuilder.response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}
			/*
				Logica service:
					Client:
						Creazione Request
						chiamata Soap
					Controllo esito
					Se negativo rispondo con 502 + errori nel dettaglio
					Se positivo:
						classe di mapping
							Creazione Response
			 */
			GetCollocazioniResponse getCollocazioniResponse = collocazioniServiceClient.call(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio,
					codiceRuolo);

			if(getCollocazioniResponse.getEsito() == RisultatoCodice.FALLIMENTO){
				if(getCollocazioniResponse.getErrori() != null && !getCollocazioniResponse.getErrori().isEmpty()){
					

					Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST)
							.codice(getCollocazioniResponse.getErrori().get(0).getCodice()).descrizione(getCollocazioniResponse.getErrori().get(0).getDescrizione()).response();
					
					response = returnResponse.getEntity().toString();
					return returnResponse;
					}
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}

			arrayModelCollocazioni = proxyCollocazioniGetMapper.mapSoapResponseToRest(getCollocazioniResponse);
			response = generateOKResponseLog(arrayModelCollocazioni);

        }catch(RESTException e){
        	log.error("CollocazioniGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("CollocazioniGet", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
        	 */
			logService.updateLog(csiLogAuditDto, response);
        }

		return Response.ok(arrayModelCollocazioni).build();
	}

	/**
	 * @param arrayModelCollocazioni
	 * @return
	 */
	public String generateOKResponseLog(ModelCollocazione[] arrayModelCollocazioni) {
		String response;
		StringBuilder responseCollocazioni = new StringBuilder("Status: " + Response.Status.OK.getStatusCode() + "\n");
		for(ModelCollocazione modelCollocazioni : arrayModelCollocazioni) {
			
			responseCollocazioni.append(modelCollocazioni.toString() + "\n");
			
		}
		response = responseCollocazioni.toString();
		return response;
	}
}
