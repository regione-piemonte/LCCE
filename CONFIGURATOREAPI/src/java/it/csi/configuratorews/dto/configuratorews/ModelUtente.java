/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import io.swagger.annotations.ApiModelProperty;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelUtente {
	// verra' utilizzata la seguente strategia serializzazione degli attributi:
	// [explicit-as-modeled]
	private Long id;

	private String nome = null;
	private String cognome = null;
	private String codiceFiscale = null;
	private List<Abilitazione> abilitazioni = new ArrayList<Abilitazione>();

	/**
	 * nome dell&#39;utente
	 **/

	@ApiModelProperty(value = "nome dell'utente")
	@JsonProperty("nome")

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * cognome dell&#39;utente
	 **/

	@ApiModelProperty(value = "cognome dell'utente")
	@JsonProperty("cognome")

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * codice fiscale dell&#39;utente
	 **/

	@ApiModelProperty(value = "codice fiscale dell'utente")
	@JsonProperty("codice_fiscale")

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 **/

	@ApiModelProperty(value = "")
	@JsonProperty("abilitazioni")

	public List<Abilitazione> getAbilitazioni() {
		return abilitazioni;
	}

	public void setAbilitazioni(List<Abilitazione> abilitazioni) {
		this.abilitazioni = abilitazioni;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ModelUtente modelUtente = (ModelUtente) o;
		return Objects.equals(nome, modelUtente.nome) && Objects.equals(cognome, modelUtente.cognome)
				&& Objects.equals(codiceFiscale, modelUtente.codiceFiscale) && Objects.equals(abilitazioni, modelUtente.abilitazioni);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, cognome, codiceFiscale, abilitazioni);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ModelUtente {\n");

		sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
		sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
		sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
		sb.append("    abilitazioni: ").append(toIndentedString(abilitazioni)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
