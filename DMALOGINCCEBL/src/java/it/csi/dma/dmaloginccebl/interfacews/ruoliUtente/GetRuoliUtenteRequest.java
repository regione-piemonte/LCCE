/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.ruoliUtente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="getRuoliUtenteRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRuoliUtenteRequest", propOrder = {
		"richiedente"
})
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