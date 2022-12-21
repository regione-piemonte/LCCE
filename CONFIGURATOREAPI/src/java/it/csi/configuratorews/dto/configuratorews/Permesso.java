/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import org.codehaus.jackson.annotate.JsonProperty;

public class Permesso {
	@JsonProperty("codice_tipologia_dato")
	private String codiceTipologiaDato;
	@JsonProperty("codice_permesso")
	private String codicePermesso;

	public String getCodiceTipologiaDato() {
		return codiceTipologiaDato;
	}

	public void setCodiceTipologiaDato(String codiceTipologiaDato) {
		this.codiceTipologiaDato = codiceTipologiaDato;
	}

	public String getCodicePermesso() {
		return codicePermesso;
	}

	public void setCodicePermesso(String codicePermesso) {
		this.codicePermesso = codicePermesso;
	}

}
