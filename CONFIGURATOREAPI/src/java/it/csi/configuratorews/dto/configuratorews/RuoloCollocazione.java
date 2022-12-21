/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RuoloCollocazione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ruolo;
	private String collocazione;
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getCollocazione() {
		return collocazione;
	}
	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}
	@Override
	public int hashCode() {
		return Objects.hash(collocazione, ruolo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuoloCollocazione other = (RuoloCollocazione) obj;
		return Objects.equals(collocazione, other.collocazione) && Objects.equals(ruolo, other.ruolo);
	}
	public RuoloCollocazione(String ruolo, String collocazione) {
		super();
		this.ruolo = ruolo;
		this.collocazione = collocazione;
	}
	public RuoloCollocazione() {
		super();
	}
	@Override
	public String toString() {
		return ruolo+", "+collocazione;		
	}
	
}
