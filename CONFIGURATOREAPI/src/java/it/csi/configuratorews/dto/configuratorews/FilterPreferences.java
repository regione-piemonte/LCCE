/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class FilterPreferences implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8840284152031892990L;

	private String codiceRuolo;
	private String codiceCollocazione;
	private String codiceApplicazione;
	private String codiceAzienda;
	private String nomeFruitore;
	private List<String> codiciFiscale;
	
	@ApiModelProperty(value = "")
	@JsonProperty("codice_ruolo") 
	public String getCodiceRuolo() {
		return codiceRuolo;
	}
	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
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
	@JsonProperty("codice_applicazione") 
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
	
	@ApiModelProperty(value = "")
	@JsonProperty("codici_fiscale")
	public List<String> getCodiciFiscale() {
		return codiciFiscale;
	}
	public void setCodiciFiscale(List<String> codiciFiscale) {
		this.codiciFiscale = codiciFiscale;
	}
	
	@ApiModelProperty(value = "")
	@JsonProperty("codice_azienda") 
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("nome_fruitore") 
	public String getNomeFruitore() {
		return nomeFruitore;
	}
	public void setNomeFruitore(String nomeFruitore) {
		this.nomeFruitore = nomeFruitore;
	}
	
}
