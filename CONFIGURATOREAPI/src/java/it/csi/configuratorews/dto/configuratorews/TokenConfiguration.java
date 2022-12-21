/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class TokenConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4397179545044874093L;
	
	private List<PreferenzeFruitore> preferenzeFruitoreDaCancellare;
	private List<PreferenzeFruitore> preferenzeFruitoreDaAggiungere;
	@ApiModelProperty(value = "")
	@JsonProperty("preferenze_fruitore_da_cancellare") 
	public List<PreferenzeFruitore> getPreferenzeFruitoreDaCancellare() {
		return preferenzeFruitoreDaCancellare;
	}
	public void setPreferenzeFruitoreDaCancellare(List<PreferenzeFruitore> preferenzeFruitoreDaCancellare) {
		this.preferenzeFruitoreDaCancellare = preferenzeFruitoreDaCancellare;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("preferenze_fruitore_da_aggiungere")
	public List<PreferenzeFruitore> getPreferenzeFruitoreDaAggiungere() {
		return preferenzeFruitoreDaAggiungere;
	}
	public void setPreferenzeFruitoreDaAggiungere(List<PreferenzeFruitore> preferenzeFruitoreDaAggiungere) {
		this.preferenzeFruitoreDaAggiungere = preferenzeFruitoreDaAggiungere;
	}
}
