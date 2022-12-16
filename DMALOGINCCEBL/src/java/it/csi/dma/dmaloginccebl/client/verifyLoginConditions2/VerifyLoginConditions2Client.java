/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.client.verifyLoginConditions2;


import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.CredenzialiServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziRichiamatiXmlDto;
import it.csi.dma.dmaloginccebl.util.VerifyServicePasswordCallback;

public class VerifyLoginConditions2Client {

    VerifyLoginConditions2Service verifyLoginConditions2Service;


    public VerifyLoginConditions2Response verifyLoginConditions2(VerifyLoginConditions2Request verifyLoginConditionsRequest,
                                                                 ApplicazioneDto applicazioneDto, CredenzialiServiziDto credenzialiServiziDto, ServiziRichiamatiXmlDto serviziRichiamatiXmlDto){


        BindingProvider verifyLoginConditionsPortBP = (BindingProvider) verifyLoginConditions2Service;
        verifyLoginConditionsPortBP.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                applicazioneDto.getUrlVerifyLoginConditions());

        Client client = ClientProxy.getClient(verifyLoginConditions2Service);

        Endpoint cxfEndpoint = client.getEndpoint();



        /* Interceptors OUT */
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        VerifyServicePasswordCallback clientPasswordCallback = new VerifyServicePasswordCallback();
        clientPasswordCallback.setPasswordDMA(credenzialiServiziDto.getPassword());
        clientPasswordCallback.setUserDMA(credenzialiServiziDto.getUsername());
        outProps.put("passwordCallbackRef", clientPasswordCallback);
        outProps.put("passwordType", "PasswordText");
        outProps.put("user", credenzialiServiziDto.getUsername());
        outProps.put("mustUnderstand", "0");
        WSS4JOutInterceptor outInterceptor = new WSS4JOutInterceptor(outProps);

        cxfEndpoint.getOutInterceptors().add(outInterceptor);
        
        Security.addProvider(new BouncyCastleProvider());
        Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 0);
        
        Map<String, Object> requestContext = verifyLoginConditionsPortBP.getRequestContext();
        requestContext.put(ServiziRichiamatiXmlDto.class.getSimpleName(), serviziRichiamatiXmlDto);
        
        return verifyLoginConditions2Service.verifyLoginConditions2(verifyLoginConditionsRequest);
    }

    public VerifyLoginConditions2Service getVerifyLoginConditionsService() {
        return verifyLoginConditions2Service;
    }

    public void setVerifyLoginConditionsService(VerifyLoginConditions2Service verifyLoginConditionsService) {
        this.verifyLoginConditions2Service = verifyLoginConditionsService;
    }
}
