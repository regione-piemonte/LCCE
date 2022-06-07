/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.abilitazioni.client;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.interfacews.msg.Errore;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;
import it.csi.dma.puawa.interfacews.msg.ServiceResponse;

@XmlRootElement(name = "getAbilitazioniResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAbilitazioniResponse", propOrder = { "listaAbilitazioni" })
public class GetAbilitazioniResponse extends ServiceResponse {

	@XmlElement(namespace = "http://dmac.csi.it/")
	protected List<Abilitazione> listaAbilitazioni;

	public GetAbilitazioniResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public GetAbilitazioniResponse(RisultatoCodice esito) {
		super(esito);
	}

	public GetAbilitazioniResponse() {
		super();
	}

	public List<Abilitazione> getListaAbilitazioni() {
		return listaAbilitazioni;
	}

	public void setListaAbilitazioni(List<Abilitazione> listaAbilitazioni) {
		this.listaAbilitazioni = listaAbilitazioni;
	}
}
