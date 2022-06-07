/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.collocazioni.client;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.interfacews.msg.Errore;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;
import it.csi.dma.puawa.interfacews.msg.ServiceResponse;

@XmlRootElement(name = "getCollocazioniResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCollocazioniResponse", propOrder = { "collocazioni" })
public class GetCollocazioniResponse extends ServiceResponse {

	@XmlElement(namespace = "http://dmac.csi.it/")
	private Collection<Collocazione> collocazioni;

	public GetCollocazioniResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public GetCollocazioniResponse(RisultatoCodice esito) {
		super(esito);
	}

	public GetCollocazioniResponse() {
		super();
	}

	public Collection<Collocazione> getCollocazioni() {
		return collocazioni;
	}

	public void setCollocazioni(Collection<Collocazione> collocazioni) {
		this.collocazioni = collocazioni;
	}
}
