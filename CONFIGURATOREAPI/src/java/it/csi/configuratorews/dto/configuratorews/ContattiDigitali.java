/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;

public class ContattiDigitali implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6605103388743819038L;
	private String email;
	private String telefono;
	private List<String> tokenPush;
	public List<String> getTokenPush() {
		return tokenPush;
	}
	public void setTokenPush(List<String> tokenPush) {
		this.tokenPush = tokenPush;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	

}
