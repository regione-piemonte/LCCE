/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class RicercaProfiloModel extends RicercaModel{

	private String codice;
	private String descrizione;
	private Long idApplicazione;
	private String flagAttivo;
	private String flagNonAttivo;
	private List<String> ruoli;

	public RicercaProfiloModel() {
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

	public String getFlagAttivo() {
		return flagAttivo;
	}

	public void setFlagAttivo(String flagAttivo) {
		this.flagAttivo = flagAttivo;
	}

	public String getFlagNonAttivo() {
		return flagNonAttivo;
	}

	public void setFlagNonAttivo(String flagNonAttivo) {
		this.flagNonAttivo = flagNonAttivo;
	}

	public List<String> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<String> ruoli) {
		this.ruoli = ruoli;
	}
	
	
}
