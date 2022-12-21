/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.configuratorews.interfacews.client.tokenInformation;

import it.csi.configuratorews.interfacews.client.base.Errore;
import it.csi.configuratorews.interfacews.client.base.ParametriLogin;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.client.base.ServiceResponse;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 
 * @author DXC
 * @version $Id: $
 */
@XmlRootElement(name="getTokenInformation2Response")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTokenInformation2Response", propOrder = {
		"richiedente",
		"parametriLogin",
		"funzionalitaAbilitate"
})
public class GetTokenInformation2Response extends ServiceResponse {

	@XmlElement(namespace = "http://dma.csi.it/")
    protected Richiedente richiedente;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected ParametriLogin parametriLogin;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected FunzionalitaAbilitate funzionalitaAbilitate;
	
	public GetTokenInformation2Response(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	public GetTokenInformation2Response() {
		super();
	}
	public Richiedente getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}
	public ParametriLogin getParametriLogin() {
		return parametriLogin;
	}
	public void setParametriLogin(ParametriLogin parametriLogin) {
		this.parametriLogin = parametriLogin;
	}

	public FunzionalitaAbilitate getFunzionalitaAbilitate() {
		return funzionalitaAbilitate;
	}

	public void setFunzionalitaAbilitate(FunzionalitaAbilitate funzionalitaAbilitate) {
		this.funzionalitaAbilitate = funzionalitaAbilitate;
	}
}
