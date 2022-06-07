/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.abilitazione;

import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="getAbilitazioniResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAbilitazioniResponse", propOrder = {
		"listaAbilitazioni"
})
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
