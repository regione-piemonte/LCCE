/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.model;

public class NotificaData {

	private String codiceAzienda;
	private String emailAzienda;
	private String numeroTelefonoAzienda;
	private String codiceFiscaleAssistito;
	private String titoloPush;
	private String testoPush;
	private String oggettoEmail;
	private String testoEmail;
	private String testoSito;

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getEmailAzienda() {
		return emailAzienda;
	}

	public void setEmailAzienda(String emailAzienda) {
		this.emailAzienda = emailAzienda;
	}

	public String getNumeroTelefonoAzienda() {
		return numeroTelefonoAzienda;
	}

	public void setNumeroTelefonoAzienda(String numeroTelefonoAzienda) {
		this.numeroTelefonoAzienda = numeroTelefonoAzienda;
	}

	public String getCodiceFiscaleAssistito() {
		return codiceFiscaleAssistito;
	}

	public void setCodiceFiscaleAssistito(String codiceFiscaleAssistito) {
		this.codiceFiscaleAssistito = codiceFiscaleAssistito;
	}

	public String getTitoloPush() {
		return titoloPush;
	}

	public void setTitoloPush(String titoloPush) {
		this.titoloPush = titoloPush;
	}

	public String getTestoPush() {
		return testoPush;
	}

	public void setTestoPush(String testoPush) {
		this.testoPush = testoPush;
	}

	public String getOggettoEmail() {
		return oggettoEmail;
	}

	public void setOggettoEmail(String oggettoEmail) {
		this.oggettoEmail = oggettoEmail;
	}

	public String getTestoEmail() {
		return testoEmail;
	}

	public void setTestoEmail(String testoEmail) {
		this.testoEmail = testoEmail;
	}

	public String getTestoSito() {
		return testoSito;
	}

	public void setTestoSito(String testoSito) {
		this.testoSito = testoSito;
	}
}
