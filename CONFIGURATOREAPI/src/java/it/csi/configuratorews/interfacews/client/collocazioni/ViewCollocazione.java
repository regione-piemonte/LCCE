/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.collocazioni;

public class ViewCollocazione extends Collocazione {

	private String radioId;
	private String labelId;
	private String viewColCodice;
	private String viewColDescrizione;
	private String viewNome;
	private String viewIndirizzo;

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
