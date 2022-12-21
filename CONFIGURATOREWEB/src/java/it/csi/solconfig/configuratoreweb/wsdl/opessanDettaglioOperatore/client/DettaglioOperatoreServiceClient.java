/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.client;

import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetDettaglioOperatore;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatore;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConfResponse;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreResponse.Return;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.common.request.Header;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

public class DettaglioOperatoreServiceClient {

    private GetDettaglioOperatore dettaglioOperatore;
    
    private String sender;

    public GetDettaglioOperatore getDettaglioOperatore() {
        return dettaglioOperatore;
    }

    public void setDettaglioOperatore(GetDettaglioOperatore dettaglioOperatore) {
        this.dettaglioOperatore = dettaglioOperatore;
    }

    public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Return callInfoOperatore(GetInfoOperatore.Arg0 request) {

        Client client = ClientProxy.getClient(dettaglioOperatore);
        Endpoint cxfEndpoint = client.getEndpoint();

        return dettaglioOperatore.getInfoOperatore(request);
    }
    
    public GetInfoOperatoreConfResponse.Return callInfoOperatoreConf(GetInfoOperatoreConf.Arg0 request) {
    	if(request.getHeader() == null) request.setHeader(new Header());
    	request.getHeader().setSender(sender);
    	
        Client client = ClientProxy.getClient(dettaglioOperatore);
        Endpoint cxfEndpoint = client.getEndpoint();

        return dettaglioOperatore.getInfoOperatoreConf(request);
    }
}
