/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client;


import it.csi.solconfig.configuratoreweb.interfacews.msg.Errore;
import it.csi.solconfig.configuratoreweb.interfacews.msg.ParametriLogin;
import it.csi.solconfig.configuratoreweb.interfacews.msg.RisultatoCodice;
import it.csi.solconfig.configuratoreweb.interfacews.msg.ServiceResponse;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 
 * @author DXC
 * @version $Id: $
 */
@XmlRootElement(name="getTokenInformationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformationResponse", propOrder = {
		"richiedente",
		"codiceFiscaleAssistito",
		"parametriLogin"
})
public class GetTokenInformationResponse extends ServiceResponse {

	@XmlElement(namespace = "http://dmac.csi.it/")
    protected Richiedente richiedente;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected String codiceFiscaleAssistito;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected ParametriLogin parametriLogin;
	
	public GetTokenInformationResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	public GetTokenInformationResponse() {
		super();
	}
	public Richiedente getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}
	public String getCodiceFiscaleAssistito() {
		return codiceFiscaleAssistito;
	}
	public void setCodiceFiscaleAssistito(String codiceFiscaleAssistito) {
		this.codiceFiscaleAssistito = codiceFiscaleAssistito;
	}
	public ParametriLogin getParametriLogin() {
		return parametriLogin;
	}
	public void setParametriLogin(ParametriLogin parametriLogin) {
		this.parametriLogin = parametriLogin;
	}
}
