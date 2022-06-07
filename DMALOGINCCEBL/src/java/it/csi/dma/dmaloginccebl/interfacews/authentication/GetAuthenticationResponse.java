/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.interfacews.authentication;



import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;

/**
 * 
 * @author DXC
 * @version $Id: $
 */
@XmlRootElement(name="getAuthenticationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAuthenticationResponse", propOrder = {
		"authenticationToken"
})
public class GetAuthenticationResponse extends ServiceResponse {

	@XmlElement(namespace = "http://dma.csi.it/")
	private String authenticationToken;
	
	public GetAuthenticationResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	public GetAuthenticationResponse() {
		super();
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	

	

}
