/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.client;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.wsdl.apimanager.ApiManagerServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.BaseRemedyClient;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.InfoNota;
// import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.CategoriaApplicativa;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.CategoriaOperativa;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.Categorizzazione;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.Lavorazione;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.Richiedente;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.Ticket;

@Component
public class RemedyInsertTicketServiceClient extends BaseRemedyClient {

    @Value("${remedyInserimentoTicketAddress}")
    private String remedyInserimentoTicketAddress;
    @Value("${remedyInserimentoTicketUsername}")
    private String remedyInserimentoTicketUsername;
    @Value("${remedyInserimentoTicketPassword}")
    private String remedyInserimentoTicketPassword;
    @Autowired
    ApiManagerServiceClient apiManagerServiceClient; 
    

    public String getRemedyInserimentoTicketAddress() {
        return remedyInserimentoTicketAddress;
    }

    public void setRemedyInserimentoTicketAddress(String remedyInserimentoTicketAddress) {
        this.remedyInserimentoTicketAddress = remedyInserimentoTicketAddress;
    }

    public String getRemedyInserimentoTicketUsername() {
        return remedyInserimentoTicketUsername;
    }

    public void setRemedyInserimentoTicketUsername(String remedyInserimentoTicketUsername) {
        this.remedyInserimentoTicketUsername = remedyInserimentoTicketUsername;
    }

    public String getRemedyInserimentoTicketPassword() {
        return remedyInserimentoTicketPassword;
    }

    public void setRemedyInserimentoTicketPassword(String remedyInserimentoTicketPassword) {
        this.remedyInserimentoTicketPassword = remedyInserimentoTicketPassword;
    }

    // Parametri header
    private static final String X_REQUEST_ID = "X-Request-ID";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String AUTHORIZATION = "Authorization";


    public ResponseEntity<String> insertTicket() throws RestClientException, IOException, Exception {
        // body request
        Ticket ticket = new Ticket();

        ticket.setRiepilogo(getValoreConf(Constants.REMEDY_RIEPILOGO));
        ticket.setDettaglio(getValoreConf(Constants.REMEDY_DETTAGLIO));
        ticket.setImpatto(getValoreConf(Constants.REMEDY_IMPATTO));
        ticket.setUrgenza(getValoreConf(Constants.REMEDY_URGENZA));
        ticket.setTipologia(getValoreConf(Constants.REMEDY_TIPOLOGIA));

        Richiedente richiedente = new Richiedente();
        richiedente.setPersonId(getValoreConf(Constants.REMEDY_PERSONAID));
        // richiedente.setNome(getNomeRichiedente());
        // richiedente.setCognome(getCognomeRichiedente());
        // richiedente.setEmail("prova.prova@prova.com");  // TODO emailRichiedente mancante nel file excel che ci hanno fornito
        // richiedente.setEnte(getCompanyRichiedente());
        ticket.setRichiedente(richiedente);

        Categorizzazione categorizzazione = new Categorizzazione();
        CategoriaOperativa categoriaOperativa = new CategoriaOperativa();
        categoriaOperativa.setEnte(getValoreConf(Constants.REMEDY_COMPANY));
        categoriaOperativa.setLivello1(getValoreConf(Constants.REMEDY_CAT_L1));
        categoriaOperativa.setLivello2(getValoreConf(Constants.REMEDY_CAT_L2));
        categoriaOperativa.setLivello3(getValoreConf(Constants.REMEDY_CAT_L3));
        categoriaOperativa.setTipologia(getValoreConf(Constants.REMEDY_CAT_TIPOLOGIA));
        categorizzazione.setCategoriaOperativa(categoriaOperativa);
        // CategoriaApplicativa categoriaApplicativa = new CategoriaApplicativa();
        // categoriaApplicativa.setEnte(getCompanyRichiedente());
        ticket.setCategorizzazione(categorizzazione);

        Lavorazione lavorazione = new Lavorazione();
        lavorazione.setStato(getValoreConf(Constants.REMEDY_STATO_LAVORAZIONE));
        InfoNota infoNota = new InfoNota();
        infoNota.setRiepilogo(getValoreConf(Constants.REMEDY_NOTE_RIEPILOGO));
        infoNota.setTipologia(getValoreConf(Constants.REMEDY_NOTE_TIPOLOGIA));
        infoNota.setAllegati(Collections.singletonList(getValoreConf(Constants.REMEDY_ALLEGATI)));
        lavorazione.setNoteUtente(Collections.singletonList(infoNota));
        ticket.setLavorazione(lavorazione);

        HttpHeaders headers = setHeaders(false);
        ObjectMapper ow = new ObjectMapper();
        ow.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);

        String requestBody = ow.writeValueAsString(ticket);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        try {
			return restTemplate
			        .exchange(getRemedyInserimentoTicketAddress(), HttpMethod.POST, new HttpEntity<>(requestBody, headers), new ParameterizedTypeReference<String>() {
			        });
		} catch (HttpClientErrorException e) {
			if(e.getRawStatusCode() == 401){
				//rifare la chiamata qui cambiando l'authorizarion
				return restTemplate
				        .exchange(getRemedyInserimentoTicketAddress(), HttpMethod.POST, new HttpEntity<>(requestBody, setHeaders(true)), new ParameterizedTypeReference<String>() {
				        });
			}
			throw e;
		}
    }

    protected HttpHeaders setHeaders(boolean refresh) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(X_REQUEST_ID, getValoreConf(Constants.X_REQUEST_ID_INS_TICKET));
        headers.set(X_FORWARDED_FOR, getValoreConf(Constants.X_FORWARDER_FOR_INS_TICKET));
        if(refresh){
        	headers.set(AUTHORIZATION, apiManagerServiceClient.refreshToken());
        }else{
        	headers.set(AUTHORIZATION, apiManagerServiceClient.getAccessToken());
        }
        

        return headers;
    }
}
