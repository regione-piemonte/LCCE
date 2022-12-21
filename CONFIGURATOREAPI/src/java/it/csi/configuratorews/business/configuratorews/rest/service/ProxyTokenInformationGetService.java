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
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.ModelTokenInformazione;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.interfacews.client.abilitazione.AbilitazioniServiceClient;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.client.collocazioni.CollocazioniServiceClient;
import it.csi.configuratorews.interfacews.client.collocazioni.GetCollocazioniResponse;
import it.csi.configuratorews.interfacews.client.ruoliUtente.GetRuoliUtenteResponse;
import it.csi.configuratorews.interfacews.client.ruoliUtente.RuoliUtenteServiceClient;
import it.csi.configuratorews.interfacews.client.tokenInformation.GetTokenInformation2Response;
import it.csi.configuratorews.interfacews.client.tokenInformation.TokenInformationServiceClient;
import it.csi.configuratorews.interfacews.mapper.ProxyAbilitazioniGetMapper;
import it.csi.configuratorews.interfacews.mapper.ProxyCollocazioniGetMapper;
import it.csi.configuratorews.interfacews.mapper.ProxyRuoliUtenteGetMapper;
import it.csi.configuratorews.interfacews.mapper.ProxyTokenInformationGetMapper;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.ProxyAbilitazioniGetValidator;
import it.csi.configuratorews.validator.ProxyCollocazioneGetValidator;
import it.csi.configuratorews.validator.ProxyRuoliUtenteGetValidator;
import it.csi.configuratorews.validator.ProxyTokenInformationGetValidator;

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
public class ProxyTokenInformationGetService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());
	private String token;

	public ProxyTokenInformationGetService(String shibIdentitaCodiceFiscale, String xRequestID,
                                       String xForwardedFor, String xCodiceServizio, String token,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		
		this.token = token;
	}

	@Autowired
	LogService logService;

	@Autowired
	ProxyTokenInformationGetValidator proxyTokenInformationGetValidator;

	@Autowired
	ProxyTokenInformationGetMapper proxyTokenInformationGetMapper;

	@Autowired
	TokenInformationServiceClient tokenInformationServiceClient;

	public static final String url = "/login/token-information2";

	public static final String NOME_SERVIZIO = "ProxyTokenInformationGetService";

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "ProxyTokenInformationGetService versione 1.0.0");
		String request = generateRequest(url);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		ModelTokenInformazione modelTokenInformazione = null;
		CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();
        try{
        	
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
				Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.SOAP, Constants.PROXY_INFORMATION, uuidString, xForwadedForInHeader,
					shibIdentitaCodiceFiscale, xRequestID, xCodiceServizio, request);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = proxyTokenInformationGetValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio, token);
			
						
			
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
			GetTokenInformation2Response getTokenInformation2Response = tokenInformationServiceClient.call(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio, token);

			if(getTokenInformation2Response.getEsito() == RisultatoCodice.FALLIMENTO){
				if(getTokenInformation2Response.getErrori() != null && !getTokenInformation2Response.getErrori().isEmpty()){
					

					Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST)
							.codice(getTokenInformation2Response.getErrori().get(0).getCodice()).descrizione(getTokenInformation2Response.getErrori().get(0).getDescrizione()).response();
					
					response = returnResponse.getEntity().toString();
					return returnResponse;
					}
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}

			modelTokenInformazione = proxyTokenInformationGetMapper.mapSoapResponseToRest(getTokenInformation2Response);
			if(modelTokenInformazione != null) {
				response = modelTokenInformazione.toString();
			}
        }catch(RESTException e){
        	log.error("TokenInformationGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("TokenInformationGet", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
        	 */
			logService.updateLog(csiLogAuditDto, response);
        }

		return Response.ok(modelTokenInformazione).build();
	}
}
