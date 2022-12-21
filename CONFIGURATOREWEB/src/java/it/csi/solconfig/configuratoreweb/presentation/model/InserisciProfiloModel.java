/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class InserisciProfiloModel {

	private String codice;
	private String descrizione;
	private Long idApplicazione;
	private List<String> listaIdFunzioniSelezionata;
	private List<String> listaRuoliSelezionata;
	private List<String> ruoli;
	private boolean active;

	public InserisciProfiloModel() {
		super();
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getidApplicazione() {
		return idApplicazione;
	}

	public void setidApplicazione(Long idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public List<String> getListaIdFunzioniSelezionata() {
		return listaIdFunzioniSelezionata;
	}

	public void setListaIdFunzioniSelezionata(List<String> listaIdFunzioniSelezionata) {
		this.listaIdFunzioniSelezionata = listaIdFunzioniSelezionata;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getListaRuoliSelezionata() {
		return listaRuoliSelezionata;
	}

	public void setListaRuoliSelezionata(List<String> listaRuoliSelezionata) {
		this.listaRuoliSelezionata = listaRuoliSelezionata;
	}

	public List<String> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<String> ruoli) {
		this.ruoli = ruoli;
	}
	
}
