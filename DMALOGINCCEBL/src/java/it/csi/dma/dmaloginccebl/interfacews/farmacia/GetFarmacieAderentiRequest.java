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

@XmlRootElement(name="getFarmacieAderentiRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFarmacieAderentiRequest", propOrder = {
		"richiedente",
		"datiFarmacia"
})
public class GetFarmacieAderentiRequest {

	
	@XmlElement(namespace = "http://dma.csi.it/")
	Richiedente richiedente;

	@XmlElement(namespace = "http://dma.csi.it/")
	DatiFarmacia datiFarmacia;

	public GetFarmacieAderentiRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}

	public DatiFarmacia getDatiFarmacia() {
		return datiFarmacia;
	}

	public void setDatiFarmacia(DatiFarmacia datiFarmacia) {
		this.datiFarmacia = datiFarmacia;
	}
	
}