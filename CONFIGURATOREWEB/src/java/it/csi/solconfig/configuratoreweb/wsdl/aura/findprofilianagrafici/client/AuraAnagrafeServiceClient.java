/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.client;

import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.AnagrafeFindSoap;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.DatiAnagraficiMsg;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.FindProfiliAnagraficiRequest;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

public class AuraAnagrafeServiceClient {

    public AnagrafeFindSoap getAnagrafeFindSoap() {
        return anagrafeFindSoap;
    }

    public void setAnagrafeFindSoap(AnagrafeFindSoap anagrafeFindSoap) {
        this.anagrafeFindSoap = anagrafeFindSoap;
    }

    private AnagrafeFindSoap anagrafeFindSoap;

    public DatiAnagraficiMsg call(FindProfiliAnagraficiRequest request) {

        Client client = ClientProxy.getClient(anagrafeFindSoap);
        Endpoint cxfEndpoint = client.getEndpoint();

        return anagrafeFindSoap.findProfiliAnagrafici(request);
    }
}
