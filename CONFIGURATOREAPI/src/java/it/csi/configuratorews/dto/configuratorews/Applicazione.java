/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class Applicazione implements Serializable {
	// verra' utilizzata la seguente strategia serializzazione degli attributi:
	// [explicit-as-modeled]

	private static final long serialVersionUID = 8445135292840150023L;
	private String codice = null;
	private String descrizione = null;

	/**
	 * codice dell&#39;applicazione
	 **/

	@ApiModelProperty(value = "codice dell'applicazione")
	@JsonProperty("codice")

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 * descrizione dell&#39;applicazione
	 **/

	@ApiModelProperty(value = "descrizione dell'applicazione")
	@JsonProperty("descrizione")

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Applicazione applicazione = (Applicazione) o;
		return Objects.equals(codice, applicazione.codice) && Objects.equals(descrizione, applicazione.descrizione);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codice, descrizione);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Applicazione {\n");

		sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
		sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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
}
