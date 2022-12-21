/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.collocazioni;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "getCollocazioniRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCollocazioniRequest", propOrder = { "richiedente" })
public class GetCollocazioniRequest {

	@XmlElement(namespace = "http://dmac.csi.it/")
	protected Richiedente richiedente;

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}
}
