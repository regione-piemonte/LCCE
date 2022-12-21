/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.util.ApisanFseStatus;

/**
 * Build dell'oggetto {@link Errore}
 * 
 */
public class ErroreBuilder {
	private Errore errore;
	
	public static ErroreBuilder from(ApisanFseStatus aas, Object... args) {
		return new ErroreBuilder()
				.status(aas.getStatusCode())
				.codice(aas.name())
				.descrizione(aas.getMessage(args));
	}
	

	public static ErroreBuilder from(Status s) {
		return new ErroreBuilder()
				.status(s.getStatusCode())
				.codice(s.name());
	}
	
	public static ErroreBuilder from(int status, String code) {
		return new ErroreBuilder()
				.status(status)
				.codice(code);
	}
	
	public static ErroreBuilder from(int status, String code, String descrizione) {
		return new ErroreBuilder()
				.status(status)
				.codice(code)
				.descrizione(descrizione);
	}
	
	private ErroreBuilder() {
		this.errore = new Errore();
	}
	
	public ErroreBuilder status(int status) {
		errore.setStatus(status);
		return this;
	}
	
	public ErroreBuilder codice(String codice) {
		errore.setCodice(codice);
		return this;
	}
	
	public ErroreBuilder descrizione(String descrizione) {
		errore.setDescrizione(descrizione);
		return this;
	}
	
	
	public Errore build() {
		return this.errore;
	}
	
	public ErroreRESTException exception() {
		return new ErroreRESTException(this.errore);
	}
	
	public ErroreRESTException exception(String message) {
		return new ErroreRESTException(this.errore, message);
	}


	public Errore getErrore() {
		return errore;
	}
	
	public void setErrore(Errore errore) {
		this.errore = errore;
	}


	public Response response() {
		return new ErroreRESTException(this.errore).getResponse();
	}
	
	
}
