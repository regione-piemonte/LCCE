/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;

public class Fruitore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8149117517432024004L;
	private String codice;
	private String descrizione;
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
	@Override
	public String toString() {
		return "Fruitore [codice=" + codice + ", descrizione=" + descrizione + "]";
	}
	
}
