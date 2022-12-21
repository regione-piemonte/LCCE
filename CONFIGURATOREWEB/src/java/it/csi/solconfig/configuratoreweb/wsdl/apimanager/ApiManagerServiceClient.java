/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.apimanager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.axis.encoding.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ApiManagerServiceClient {
	
	@Value("${apiManagerConsumerKey}")
	private String apiManagerConsumerKey;
	@Value("${apiManagerConsumerSecret}")
	private String apiManagerConsumerSecret;
	@Value("${apiManagerUrl}")
	private String apiManagerUrl;
	
	
	
	private volatile String token = null;

	/*@PostConstruct
	public void init() throws Exception{
		//callToken();
	}*/
	
	public String getAccessToken() throws IOException{
		if(this.token == null){
			callToken();
		}
		return this.token;
	}
	
	public String refreshToken() throws IOException{
		callToken();
		return this.token;
	}
	
	private synchronized void callToken() throws IOException{
		
		
		RestTemplate rt2 = new RestTemplate();
		
		HttpHeaders headers2 = new HttpHeaders();
		
		
		String auth2 = apiManagerConsumerKey + ":" + apiManagerConsumerSecret;
        //String encodedAuth2 = org.jboss.resteasy.util.Base64.encodeBytes(auth2.getBytes( ));
		String encodedAuth2 = Base64.encode(auth2.getBytes( ));
        String authHeader2 = "Basic " + new String( encodedAuth2 );
		
        headers2.add("Authorization", authHeader2); 
        headers2.add("Content-Type", "application/x-www-form-urlencoded");
        String strBody2 = "grant_type=client_credentials";
        HttpEntity<String> httpEntity2 = new HttpEntity<>(strBody2, headers2);
        
        ResponseEntity<String> re2 =  rt2.exchange(apiManagerUrl, HttpMethod.POST, httpEntity2, String.class);
        String json = re2.getBody();
        
		ObjectMapper mapper = new ObjectMapper();        		
		TokenApiManager tokenApi = mapper.readValue(json ,new TypeReference<TokenApiManager>() {
		});
		token =  new StringBuffer("Bearer").append(" ").append(tokenApi.getAccessToken()).toString() ;
	}
	

	}
