/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.puawa.integration.authentication2.client;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.interfacews.msg.Errore;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;
import it.csi.dma.puawa.interfacews.msg.ServiceResponse;

/**
 * 
 * @author DXC
 * @version $Id: $
 */
@XmlRootElement(name="getAuthentication2Response")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAuthentication2Response", propOrder = {
		"authenticationToken"
})
public class GetAuthentication2Response extends ServiceResponse {

	@XmlElement(namespace = "http://dma.csi.it/")
	private String authenticationToken;
	
	public GetAuthentication2Response(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	public GetAuthentication2Response() {
		super();
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	

	

}
