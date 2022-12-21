/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.mapper;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.dto.configuratorews.Token;
import it.csi.configuratorews.interfacews.client.authentication2.GetAuthentication2Response;

@Component
public class ProxyTokenAuthenticationGetMapper { 

    public Token mapSoapResponseToRest(GetAuthentication2Response getAuthentication2Response){
    	Token token = new Token();
    	token.setToken(getAuthentication2Response.getAuthenticationToken());
        return token;
    }
}
