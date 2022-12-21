/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class RicercaRuoloModel extends RicercaModel{
	
	private long id;
	private String codice;
	private String descrizione;
	private String flagConfiguratore; 
	private String flagAttivo;
	private String flagNonAttivo;
	
	public RicercaRuoloModel() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public String getFlagConfiguratore() {
		return flagConfiguratore;
	}

	public void setFlagConfiguratore(String flagConfiguratore) {
		this.flagConfiguratore= flagConfiguratore;
	}
	
	public String isFlagAttivo() {
		return flagAttivo;
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

}
