/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.opessan.client;

import it.csi.solconfig.configuratoreweb.wsdl.opessan.GetOperatoreAttivoResponse;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.RicercaOperatore;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.RicercaOperatore_Type;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

public class RicercaOperatoreServiceClient {

    private RicercaOperatore ricercaOperatore;

    public RicercaOperatore getRicercaOperatore() {
        return ricercaOperatore;
    }

    public void setRicercaOperatore(RicercaOperatore ricercaOperatore) {
        this.ricercaOperatore = ricercaOperatore;
    }

    public GetOperatoreAttivoResponse.Return call(RicercaOperatore_Type request) {

        Client client = ClientProxy.getClient(ricercaOperatore);
        Endpoint cxfEndpoint = client.getEndpoint();

        return ricercaOperatore.getOperatoreAttivo(request);
    }
}
