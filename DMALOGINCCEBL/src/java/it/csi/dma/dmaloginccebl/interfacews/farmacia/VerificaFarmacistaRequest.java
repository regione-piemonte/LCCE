/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="verificaFarmacistaRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificaFarmacistaRequest", propOrder = {
		"farmacia",
		"codiceFiscaleFarmacista"
})

public class VerificaFarmacistaRequest {

	@XmlElement(namespace = "http://dma.csi.it/")
	Farmacia farmacia;

	@XmlElement(namespace = "http://dma.csi.it/")
	String codiceFiscaleFarmacista;

	public Farmacia getFarmacia() {
		return farmacia;
	}

	public void setFarmacia(Farmacia farmacia) {
		this.farmacia = farmacia;
	}

	public String getCodiceFiscaleFarmacista() {
		return codiceFiscaleFarmacista;
	}

	public void setCodiceFiscaleFarmacista(String codiceFiscaleFarmacia) {
		this.codiceFiscaleFarmacista = codiceFiscaleFarmacia;
	}

}
