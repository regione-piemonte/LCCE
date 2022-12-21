/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ProfiloFunzionalitaBody implements Serializable {

	private static final long serialVersionUID = 1730450679762444667L;

	private String descrizione;
	private String tipo;
	
	@ApiModelProperty(value = "")
	@JsonProperty("descrizione") 
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@ApiModelProperty(value = "")
	@JsonProperty("tipo") 
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
