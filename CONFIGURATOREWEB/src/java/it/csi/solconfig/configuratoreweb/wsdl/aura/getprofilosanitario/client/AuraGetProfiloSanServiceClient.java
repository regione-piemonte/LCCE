/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.client;

import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.AnagrafeSanitariaSoap;
import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.SoggettoAuraMsg;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

public class AuraGetProfiloSanServiceClient {

    public AnagrafeSanitariaSoap getAuraGetProfiloSanClient() {
        return auraGetProfiloSanClient;
    }

    public void setAuraGetProfiloSanClient(AnagrafeSanitariaSoap auraGetProfiloSanClient) {
        this.auraGetProfiloSanClient = auraGetProfiloSanClient;
    }

    private AnagrafeSanitariaSoap auraGetProfiloSanClient;

    public SoggettoAuraMsg call(String auraId) {

        Client client = ClientProxy.getClient(auraGetProfiloSanClient);
        Endpoint cxfEndpoint = client.getEndpoint();

        return auraGetProfiloSanClient.getProfiloSanitario(auraId);
    }

}
