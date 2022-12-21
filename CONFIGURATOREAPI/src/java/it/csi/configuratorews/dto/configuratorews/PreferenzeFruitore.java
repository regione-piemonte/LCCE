/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class PreferenzeFruitore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6594284335174481857L;

	private String nomeFruitore;
	private String descrizioneFruitore;
	private String codiceRuolo;
	private String codiceCollocazione;
	private String applicazione;
	private Preference preferenza;
	@ApiModelProperty(value = "")
	@JsonProperty("nome_fruitore") 
	public String getNomeFruitore() {
		return nomeFruitore;
	}
	public void setNomeFruitore(String nomeFruitore) {
		this.nomeFruitore = nomeFruitore;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("codice_ruolo") 
	public String getCodiceRuolo() {
		return codiceRuolo;
	}
	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("applicazione")
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("preferenza")
	public Preference getPreferenza() {
		return preferenza;
	}
	public void setPreferenza(Preference preferenza) {
		this.preferenza = preferenza;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("codice_collocazione")
	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}
	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("descrizione_fruitore")
	public String getDescrizioneFruitore() {
		return descrizioneFruitore;
	}
	public void setDescrizioneFruitore(String descrizioneFruitore) {
		this.descrizioneFruitore = descrizioneFruitore;
	}
}
