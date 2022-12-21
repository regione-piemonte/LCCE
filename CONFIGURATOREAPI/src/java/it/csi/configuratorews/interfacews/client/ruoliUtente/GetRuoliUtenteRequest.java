/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.ruoliUtente;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "getRuoliUtenteRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRuoliUtenteRequest", propOrder = { "richiedente" })
public class GetRuoliUtenteRequest {

	@XmlElement(namespace = "http://dmac.csi.it/")
	private Richiedente richiedente;

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}
}