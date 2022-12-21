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
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.interfacews.client.abilitazione.AbilitazioniServiceClient;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.mapper.ProxyAbilitazioniGetMapper;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.ProxyAbilitazioniGetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class ProxyAbilitazioniGetService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());

	private String codiceRuolo;
	private String codiceCollocazione;
	private String codiceAzienda;

	public ProxyAbilitazioniGetService(String shibIdentitaCodiceFiscale, String xRequestID,
                                       String xForwardedFor, String xCodiceServizio,String codiceRuolo,String codiceCollocazione,String codiceAzienda,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceRuolo = codiceRuolo;
		this.codiceCollocazione = codiceCollocazione;
		this.codiceAzienda = codiceAzienda;
	}

	@Autowired
	LogService logService;

	@Autowired
	ProxyAbilitazioniGetValidator proxyAbilitazioniGetValidator;

	@Autowired
	ProxyAbilitazioniGetMapper proxyAbilitazioniGetMapper;

	@Autowired
	AbilitazioniServiceClient abilitazioniServiceClient;

	public static final String url = "/login/abilitazioni";

	public static final String NOME_SERVIZIO = "ProxyAbilitazioniGetService";

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "ProxyAbilitazioniGetService versione 1.0.0");
		String request = generateRequest(url);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		ModelAbilitazione[] arrayModelAbilitazione = null;
		CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();
        try{
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
			/*
				Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.SOAP, Constants.PROXY_ABI, uuidString, xForwadedForInHeader,
					shibIdentitaCodiceFiscale, xRequestID, xCodiceServizio, request);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = proxyAbilitazioniGetValidator.validate(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio,
					codiceRuolo, codiceCollocazione, codiceAzienda);

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
			GetAbilitazioniResponse getAbilitazioniResponse = abilitazioniServiceClient.call(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio,
					codiceRuolo, codiceCollocazione, codiceAzienda);

			if(getAbilitazioniResponse.getEsito() == RisultatoCodice.FALLIMENTO){
				if(getAbilitazioniResponse.getErrori() != null && !getAbilitazioniResponse.getErrori().isEmpty()){
					
					Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST)
							.codice(getAbilitazioniResponse.getErrori().get(0).getCodice()).descrizione(getAbilitazioniResponse.getErrori().get(0).getDescrizione()).response();
					
					response = returnResponse.getEntity().toString();
					return returnResponse;
				}
				
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				response = returnResponse.getEntity().toString();
				return returnResponse;
			}

			arrayModelAbilitazione = proxyAbilitazioniGetMapper.mapSoapResponseToRest(getAbilitazioniResponse);
			response = generateOKResponseLog(arrayModelAbilitazione);
			
        }catch(RESTException e){
        	log.error("AbilitazioniGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("AbilitazioniGet", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
        	 */
        	try {
				logService.updateLog(csiLogAuditDto, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

		return Response.ok(arrayModelAbilitazione).build();
	}
	
	/**
	 * @param arrayModelCollocazioni
	 * @return
	 */
	public String generateOKResponseLog(ModelAbilitazione[] arrayModelAbilitazione) {
		String response;
		StringBuilder responseAbilitazioni = new StringBuilder("Status: " + Response.Status.OK.getStatusCode() + "\n");
		for(ModelAbilitazione modelCollocazioni : arrayModelAbilitazione) {
			
			responseAbilitazioni.append(modelCollocazioni.toString() + "\n");
			
		}
		response = responseAbilitazioni.toString();
		return response;
	}
}
