/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class Collocazione {

	private String colCodAzienda;
	private String colDescAzienda;
	private String colCodice;
	private String colDescrizione;
	private String radioId;
	private String labelId;
	private String viewColCodice;
	private String viewColDescrizione;
	private String viewNome;
	private String viewIndirizzo;

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

	public String getViewNome() {
		return viewNome;
	}

	public void setViewNome(String viewNome) {
		this.viewNome = viewNome;
	}

	public String getViewIndirizzo() {
		return viewIndirizzo;
	}

	public void setViewIndirizzo(String viewIndirizzo) {
		this.viewIndirizzo = viewIndirizzo;
	}

	public String getViewColCodice() {
		return viewColCodice;
	}

	public void setViewColCodice(String viewColCodice) {
		this.viewColCodice = viewColCodice;
	}

	public String getViewColDescrizione() {
		return viewColDescrizione;
	}

	public void setViewColDescrizione(String viewColDescrizione) {
		this.viewColDescrizione = viewColDescrizione;
	}

	public String getRadioId() {
		return radioId;
	}

	public void setRadioId(String radioId) {
		this.radioId = radioId;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

}
