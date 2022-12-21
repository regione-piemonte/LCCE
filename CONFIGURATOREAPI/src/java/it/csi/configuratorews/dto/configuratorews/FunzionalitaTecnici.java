/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class FunzionalitaTecnici extends DatiTecnici implements Comparable<FunzionalitaTecnici> {
	
	private String codice = null;
	private String descrizione = null;
	
	private List<PermessoValido> permessi;

	public List<PermessoValido> getPermessi() {
		return permessi;
	}

	public void setPermessi(List<PermessoValido> permessi) {
		this.permessi = permessi;
	}

	@ApiModelProperty(value = "")
	@JsonProperty("codice")

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 **/

	@ApiModelProperty(value = "")
	@JsonProperty("descrizione")

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "FunzionalitaTecnici [codice=" + codice + ", descrizione=" + descrizione + ", getId()=" + getId() + ", getInizioValidita()="
				+ getInizioValidita() + ", getFineValidita()=" + getFineValidita() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	@Override
	public int compareTo(FunzionalitaTecnici o) {
		return this.getCodice().compareTo(o.getCodice());
	}

}
