/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.abilitazione;


import it.csi.configuratorews.interfacews.client.base.Errore;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.client.base.ServiceResponse;

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
