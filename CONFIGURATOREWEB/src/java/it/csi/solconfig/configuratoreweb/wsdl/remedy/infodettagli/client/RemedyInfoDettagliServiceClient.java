/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.infodettagli.client;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.wsdl.apimanager.ApiManagerServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.BaseRemedyClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
public class RemedyInfoDettagliServiceClient extends BaseRemedyClient {

    @Value("${remedyInfoDettagliAddress}")
    private String remedyInfoDettagliAddress;
    @Value("${remedyInfoDettagliUsername}")
    private String remedyInfoDettagliUsername;
    @Value("${remedyInfoDettagliPassword}")
    private String remedyInfoDettagliPassword;
    @Autowired
    ApiManagerServiceClient apiManagerServiceClient; 

    public String getRemedyInfoDettagliAddress() {
        return remedyInfoDettagliAddress;
    }

    public void setRemedyInfoDettagliAddress(String remedyInfoDettagliAddress) {
        this.remedyInfoDettagliAddress = remedyInfoDettagliAddress;
    }

    public String getRemedyInfoDettagliUsername() {
        return remedyInfoDettagliUsername;
    }

    public void setRemedyInfoDettagliUsername(String remedyInfoDettagliUsername) {
        this.remedyInfoDettagliUsername = remedyInfoDettagliUsername;
    }

    public String getRemedyInfoDettagliPassword() {
        return remedyInfoDettagliPassword;
    }

    public void setRemedyInfoDettagliPassword(String remedyInfoDettagliPassword) {
        this.remedyInfoDettagliPassword = remedyInfoDettagliPassword;
    }



    public ResponseEntity<String> infoDettagli(String ticketId, StringWriter filePerRichiestaCredenziali) throws UnsupportedEncodingException, Exception {
        String infoDettagliUrl = getRemedyInfoDettagliAddress().replace("{ticketId}", ticketId);
        HttpHeaders headers = setHeaders(false);

        ByteArrayResource contentsAsResource = new ByteArrayResource(filePerRichiestaCredenziali.toString()
                .getBytes(StandardCharsets.UTF_8)){
            @Override
            public String getFilename(){
                try {
                    return getValoreConf(Constants.REMEDY_ALLEGATO_INFO_DETT);
                } catch (Exception e) {
                    log.error("Impossibile recuperare nome allegato info dett remedy da configurazione");
                }
                return null;
            }
        };
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add(RIEPILOGO, getValoreConf(Constants.REMEDY_RIEPILOGO_INFO_DETT));
        formData.add(TIPOLOGIA, getValoreConf(Constants.REMEDY_TIP_INFO_DETT));
        formData.add(NOTE, getValoreConf(Constants.REMEDY_NOTE_INFO_DETT));
        formData.add(NOME_ALLEGATO_1, getValoreConf(Constants.REMEDY_ALLEGATO_INFO_DETT));
        formData.add(getValoreConf(Constants.REMEDY_ALLEGATO_INFO_DETT), contentsAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        RestTemplate restTemplate = new RestTemplate();
        
        try {
			return restTemplate
			        .exchange(infoDettagliUrl, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<String>() {
			        });
		} catch (HttpClientErrorException e) {
			if(e.getRawStatusCode() == 401){
				//rifare la chiamata qui cambiando l'authorizarion
				headers = setHeaders(true);
				HttpEntity<MultiValueMap<String, Object>> requestEntityRetry = new HttpEntity<>(formData, headers);
				
				return restTemplate
				        .exchange(infoDettagliUrl, HttpMethod.PUT, requestEntityRetry, new ParameterizedTypeReference<String>() {
				        });
			}
			throw e;
		}
    }

    protected HttpHeaders setHeaders(boolean refresh) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(X_REQUEST_ID, getValoreConf(Constants.X_REQUEST_ID_INFO_DETT));
        headers.set(X_FORWARDED_FOR, getValoreConf(Constants.X_FORWARDER_FOR_INFO_DETT));
        if(refresh){
        	headers.set(AUTHORIZATION, apiManagerServiceClient.refreshToken());
        }else{
        	headers.set(AUTHORIZATION, apiManagerServiceClient.getAccessToken());
        }

        return headers;
    }
}
