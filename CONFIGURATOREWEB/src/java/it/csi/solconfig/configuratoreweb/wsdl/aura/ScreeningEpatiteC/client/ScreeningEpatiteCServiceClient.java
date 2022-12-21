/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.client;

import it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.Request;
import it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.Response;
import it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.ScreeningEpatiteCSoap;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

public class ScreeningEpatiteCServiceClient {

    public ScreeningEpatiteCSoap getScreeningEpatiteCClient() {
        return screeningEpatiteCClient;
    }

    public void setScreeningEpatiteCClient(ScreeningEpatiteCSoap screeningEpatiteCClient) {
        this.screeningEpatiteCClient = screeningEpatiteCClient;
    }

    private ScreeningEpatiteCSoap screeningEpatiteCClient;

    public Response call(Request assistito) throws SOAPFaultException {

        Client client = ClientProxy.getClient(screeningEpatiteCClient);
        Endpoint cxfEndpoint = client.getEndpoint();

        return screeningEpatiteCClient.screeningEpatiteC(assistito);
    }

}
