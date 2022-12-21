/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.client;



import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConfResponse2;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.OperatoreDipendente;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.common.request.Header;

public class OperatoreDipendenteServiceClient {

    private OperatoreDipendente operatoreDipendente;
    
    private String sender;

    public OperatoreDipendente getOperatoreDipendente() {
        return operatoreDipendente;
    }

    public void setOperatoreDipendente(OperatoreDipendente operatoreDipendente) {
        this.operatoreDipendente = operatoreDipendente;
    }
    
    public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

    public GetDettaglioConfResponse2.Return call(GetDettaglioConf.Arg0 request) {
    	if(request.getHeader() == null) request.setHeader(new Header());
    	request.getHeader().setSender(sender);
    	 
        Client client = ClientProxy.getClient(operatoreDipendente);
        Endpoint cxfEndpoint = client.getEndpoint();

        return operatoreDipendente.getDettaglioConf(request);
    }
}
