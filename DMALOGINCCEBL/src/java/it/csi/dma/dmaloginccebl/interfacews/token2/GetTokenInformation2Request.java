/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token2;

import javax.xml.bind.annotation.*;


/**
 * 
 * @author DXC
 * 
 */
@XmlRootElement(name = "getTokenInformation2Request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformation2Request", propOrder = {
	    "token",
	    "applicazione",
	    "ipBrowser"
})
public class GetTokenInformation2Request {
	
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
