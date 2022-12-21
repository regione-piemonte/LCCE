/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.ruoliUtente;


import it.csi.configuratorews.interfacews.client.base.Errore;
import it.csi.configuratorews.interfacews.client.base.RisultatoCodice;
import it.csi.configuratorews.interfacews.client.base.ServiceResponse;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.List;

@XmlRootElement(name = "getRuoliUtenteResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRuoliUtenteResponse", propOrder = { "ListaRuoli" })
public class GetRuoliUtenteResponse extends ServiceResponse {

	@XmlElement(namespace = "http://dmac.csi.it/")
	protected Collection<Ruolo> ListaRuoli;

	public GetRuoliUtenteResponse() {
		super();
	}

	public GetRuoliUtenteResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public GetRuoliUtenteResponse(RisultatoCodice esito) {
		super(esito);
	}

	public Collection<Ruolo> getListaRuoli() {
		return ListaRuoli;
	}

	public void setListaRuoli(Collection<Ruolo> listaRuoli) {
		ListaRuoli = listaRuoli;
	}
}
