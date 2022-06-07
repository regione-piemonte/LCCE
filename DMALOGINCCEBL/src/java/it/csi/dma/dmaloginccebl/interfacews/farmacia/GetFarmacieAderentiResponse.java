/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;

@XmlRootElement(name="getFarmacieAderentiResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getFarmacieAderentiResponse", propOrder = {
		"farmacie"
})
public class GetFarmacieAderentiResponse extends ServiceResponse {
	
	@XmlElement(namespace = "http://dma.csi.it/")
	Farmacie farmacie;

	public GetFarmacieAderentiResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	
	public GetFarmacieAderentiResponse(RisultatoCodice esito) {
		super(esito);
	}

	public GetFarmacieAderentiResponse() {
		super();
	}

	public Farmacie getFarmacie() {
		return farmacie;
	}

	public void setFarmacie(Farmacie farmacie) {
		this.farmacie = farmacie;
	}

	
}
