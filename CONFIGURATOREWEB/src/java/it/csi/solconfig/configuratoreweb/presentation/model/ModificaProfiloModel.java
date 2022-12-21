/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class ModificaProfiloModel {

	private String codice;
	private String descrizione;
	private Long idApplicazione;
	private List<String> listaIdFunzioniSelezionata;
	private List<String> ruoli;
	private boolean active;
	private Long idFunzione;

	public ModificaProfiloModel() {
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

	public Long getIdFunzione() {
		return idFunzione;
	}

	public void setIdFunzione(Long idFunzione) {
		this.idFunzione = idFunzione;
	}

	public List<String> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<String> ruoli) {
		this.ruoli = ruoli;
	}
	
}
