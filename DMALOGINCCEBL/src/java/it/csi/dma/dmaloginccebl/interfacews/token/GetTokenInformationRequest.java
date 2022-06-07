/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * @author DXC
 * 
 */
@XmlRootElement(name = "getTokenInformationRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformationRequest", propOrder = {
	    "token",
	    "applicazione",
	    "ipBrowser"
})
public class GetTokenInformationRequest {
	
	@XmlElement(namespace = "http://dma.csi.it/")
    protected String token;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected String applicazione;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected String ipBrowser;
    
    /** Properties obbligatorie */
    public static String[] requiredProperties = { "token",
    		"applicazione", "ipBrowser"
    	    };

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public String getIpBrowser() {
		return ipBrowser;
	}

	public void setIpBrowser(String ipBrowser) {
		this.ipBrowser = ipBrowser;
	}

	

	
    
    

}
