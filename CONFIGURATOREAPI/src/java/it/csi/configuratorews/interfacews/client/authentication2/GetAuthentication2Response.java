/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.configuratorews.interfacews.client.authentication2;

import it.csi.configuratorews.interfacews.client.base.Errore;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.client.base.ServiceResponse;

import javax.xml.bind.annotation.*;
import java.util.List;

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
