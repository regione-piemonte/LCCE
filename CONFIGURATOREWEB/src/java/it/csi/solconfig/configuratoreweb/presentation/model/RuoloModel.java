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

public class RuoloModel {

	private long id;
	private String codice;
	private String descrizione;
	private boolean flagAttivo;

	private List<String> ruoliSel;

	private List<String> ruoliCompatibili;

	public RuoloModel() {
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

	public boolean getFlagAttivo() {
		return flagAttivo;
	}

	public void setFlagAttivo(boolean flagAttivo) {
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
		List<RuoloSelCollTipo> l;
		if (ruoliSel != null && ruoliSel.size() > 0) {
			l = ruoliSel.stream().map(RuoloSelCollTipo::valueOf).collect(Collectors.toList());
		} else {
			l = new ArrayList<>();
		}
		return l;
	}

	public void setRuoliSel(List<String> l) {
		this.ruoliSel = l;
	}
	

}
