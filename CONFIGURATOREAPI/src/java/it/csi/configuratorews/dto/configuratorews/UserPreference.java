/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class UserPreference implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codiceFiscale;
	private String email;
	private String numeroDiTelefono;
	private String push;
	
	@ApiModelProperty(value = "codice fiscale dell'utente che deve mandare notifica")
	@JsonProperty("codiceFiscale") 
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	@ApiModelProperty(value = "mail a cui mandare la notifica")
	@JsonProperty("email") 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ApiModelProperty(value = "numero di telefono a cui mandare la notifica")
	@JsonProperty("numeroDiTelefono") 
	public String getNumeroDiTelefono() {
		return numeroDiTelefono;
	}
	public void setNumeroDiTelefono(String numeroDiTelefono) {
		this.numeroDiTelefono = numeroDiTelefono;
	}
	
	@ApiModelProperty(value = "se mandare la notifica push")
	@JsonProperty("push") 
	public String getPush() {
		return push;
	}
	public void setPush(String push) {
		this.push = push;
	}
}
