/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dto;
public class Collocazione {

	private Long id;
	private String colCodice;
	private String colDescrizione; 
	private String colCodAzienda;
	private String colDescAzienda;
	private String flagAzienda;
	
	public Collocazione() {
	}

	public Collocazione(Long id, String colCodice, String colDescrizione, String colCodAzienda, String colDescAzienda,
			String flagAzienda) {
		this.id = id;
		this.colCodice = colCodice;
		this.colDescrizione = colDescrizione;
		this.colCodAzienda = colCodAzienda;
		this.colDescAzienda = colDescAzienda;
		this.flagAzienda = flagAzienda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColCodice() {
		return colCodice;
	}

	public void setColCodice(String colCodice) {
		this.colCodice = colCodice;
	}

	public String getColDescrizione() {
		return colDescrizione;
	}

	public void setColDescrizione(String colDescrizione) {
		this.colDescrizione = colDescrizione;
	}

	public String getColCodAzienda() {
		return colCodAzienda;
	}

	public void setColCodAzienda(String colCodAzienda) {
		this.colCodAzienda = colCodAzienda;
	}

	public String getColDescAzienda() {
		return colDescAzienda;
	}

	public void setColDescAzienda(String colDescAzienda) {
		this.colDescAzienda = colDescAzienda;
	}

	public String getFlagAzienda() {
		return flagAzienda;
	}

	public void setFlagAzienda(String flagAzienda) {
		this.flagAzienda = flagAzienda;
	}

}