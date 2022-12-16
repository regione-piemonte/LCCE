/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.client.verifyLoginConditions;


import java.security.Security;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziRichiamatiXmlDto;

public class VerifyLoginConditionsClient {

    VerifyLoginConditionsService verifyLoginConditionsService;


    public VerifyLoginConditionsResponse verifyLoginConditions(VerifyLoginConditionsRequest verifyLoginConditionsRequest, ApplicazioneDto applicazioneDto, ServiziRichiamatiXmlDto serviziRichiamatiXmlDto){

//        Security.addProvider(new BouncyCastleProvider());
//
//        final javax.xml.namespace.QName verifyLoginConditionsPort = new javax.xml.namespace.QName(
//                "http://dmacc.csi.it/", "verifyLoginConditionsPort");
//
//        javax.xml.ws.Service service = javax.xml.ws.Service.create(null);
//
//        service.addPort(verifyLoginConditionsPort, javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING,
//                applicazioneDto.getUrlVerifyLoginConditions());
//
//
//        VerifyLoginConditionsService verifyLoginConditionsService = service.getPort(verifyLoginConditionsPort,
//                VerifyLoginConditionsService.class);
//
//
//        Client client = ClientProxy.getClient(verifyLoginConditionsService);
//
//        Endpoint cxfEndpoint = client.getEndpoint();
//
//
//
//        /* Interceptors IN */
////        Map<String, Object>  inProps              = new HashMap<String, Object>();
//        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
//
//
//
//        /* Interceptors OUT */
//        Map<String, Object> outProps = new HashMap<String, Object>();
//        outProps.put("action", " UsernameToken " + WSHandlerConstants.TIMESTAMP);
//        VerifyServicePasswordCallback clientPasswordCallback = new VerifyServicePasswordCallback();
//        clientPasswordCallback.setPasswordDMA(credenzialiServiziDto.getPassword());
//        clientPasswordCallback.setUserDMA(credenzialiServiziDto.getUsername());
//        outProps.put("passwordCallbackRef", clientPasswordCallback);
//        outProps.put("passwordType", "PasswordText");
//        outProps.put("user", credenzialiServiziDto.getUsername());
//        WSS4JOutInterceptor outInterceptor = new WSS4JOutInterceptor(outProps);
//
//
//
//        /* Append Interceptors */
//        cxfEndpoint.getInInterceptors().add(loggingInInterceptor);
//        cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
//        cxfEndpoint.getOutInterceptors().add(outInterceptor);
//
//
//
//
//        BindingProvider verifyLoginConditionsPortBP = (BindingProvider) verifyLoginConditionsService;
//
//        java.util.List<Handler> handlers = verifyLoginConditionsPortBP.getBinding().getHandlerChain();
//        handlers.add(new MyMessageHandlerCustom());
//        verifyLoginConditionsPortBP.getBinding().setHandlerChain(handlers);
//
//        setupTLS(seFSERDClient);
//
//        return seFSERDClient.verifyLoginConditions(verifyLoginConditionsRequest);

        BindingProvider verifyLoginConditionsPortBP = (BindingProvider) verifyLoginConditionsService;
        verifyLoginConditionsPortBP.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                applicazioneDto.getUrlVerifyLoginConditions());

        Security.addProvider(new BouncyCastleProvider());
        Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 0);

        
        Map<String, Object> requestContext = verifyLoginConditionsPortBP.getRequestContext();
        requestContext.put(ServiziRichiamatiXmlDto.class.getSimpleName(), serviziRichiamatiXmlDto);
        
        return verifyLoginConditionsService.verifyLoginConditions(verifyLoginConditionsRequest);
    }

    public VerifyLoginConditionsService getVerifyLoginConditionsService() {
        return verifyLoginConditionsService;
    }

    public void setVerifyLoginConditionsService(VerifyLoginConditionsService verifyLoginConditionsService) {
        this.verifyLoginConditionsService = verifyLoginConditionsService;
    }

//    public static void setupTLS(Object port){
//
//        HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
//
//        TLSClientParameters tlsCP = new TLSClientParameters();
//        String keyPassword = "PIEMONTE";
//        KeyStore keyStore = KeyStore.getInstance("JKS");
//        String keyStoreLoc = contextPath + "/keyStore.jks";
//        keyStore.load(new FileInputStream(keyStoreLoc), keyPassword.toCharArray());
//        KeyManager[] myKeyManagers = getKeyManagers(keyStore, keyPassword);
//        tlsCP.setKeyManagers(myKeyManagers);
//
//        KeyStore trustStore = KeyStore.getInstance("JKS");
//        String trustStoreLoc = contextPath + "/trustStore.jks";
//        trustStore.load(new FileInputStream(trustStoreLoc), keyPassword.toCharArray());
//        TrustManager[] myTrustStoreKeyManagers = new TrustManager[] {
//                new X509TrustManager() {
//
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//                    public void checkClientTrusted(X509Certificate[] certificate, String str) {}
//                    public void checkServerTrusted(X509Certificate[] certificate, String str) {}
//                }
//        };
//        tlsCP.setTrustManagers(myTrustStoreKeyManagers);
//
//        // The following is not recommended and would not be done in a
//        // prodcution environment,
//        // this is just for illustrative purpose
//        tlsCP.setDisableCNCheck(true);
//
//        httpConduit.setTlsClientParameters(tlsCP);
//
//        // setting timeout
//        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//        httpClientPolicy.setReceiveTimeout(30000);
//        httpClientPolicy.setAllowChunking(false);
//        httpClientPolicy.setConnectionTimeout(30000);
//        httpConduit.
//        httpConduit.setClient(httpClientPolicy);
//
//    }
//
//    private static TrustManager[] getTrustManagers(KeyStore trustStore)
//            throws NoSuchAlgorithmException, KeyStoreException {
//        String alg = KeyManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory fac = TrustManagerFactory.getInstance(alg);
//        fac.init(trustStore);
//        return fac.getTrustManagers();
//    }
}
