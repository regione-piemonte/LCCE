/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;

public class InserisciRuoloModel {
	
	private long id;
	
	private String codice;
	private String descrizione;
	private String flagAttivo;
	
	private List<String> ruoliSel;

	private List<String> ruoliCompatibili;	

	public InserisciRuoloModel() {
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
	
	public String getFlagAttivo() {
		return flagAttivo;
	}
	
	public void setFlagAttivo(String flagAttivo) {
		this.flagAttivo = flagAttivo;
	}
	
	public List<String> getRuoliCompatibili() {
		if (ruoliCompatibili == null)
			ruoliCompatibili = new ArrayList<>();
		return ruoliCompatibili;
	}

	public void setRuoliCompatibili(List<String> ruoliCompatibili) {
		this.ruoliCompatibili = ruoliCompatibili;
	}

	public List<String> getRuoliSel() {
		if (ruoliSel == null)
			ruoliSel = new ArrayList<>();
		return ruoliSel;
	}
	
	public List<RuoloSelCollTipo> getRuoliSelCollTipo() {
		return (List<RuoloSelCollTipo>) ruoliSel.stream().map(i -> RuoloSelCollTipo.valueOf(i))
				.collect(Collectors.toList());
	}	

	public void setRuoliSel(List<String> l) {
		this.ruoliSel = l;
	}

}
