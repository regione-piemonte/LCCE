/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util.mailsender;

public class MailAuraParams {
	
	private String emailAura;
	
	private String oggettoMailAura;
	
	private String testoPrimaParteMailAura;
	
	private String testoElencoMailAura;
	
	
	private String testoMailUserAbil;
	
	private String testoMailUserDisabil;

	public MailAuraParams() {
	}
	
	public String getEmailAura() {
		return emailAura;
	}

	public void setEmailAura(String emailAura) {
		this.emailAura = emailAura;
	}
	
	public String getOggettoMailAura() {
		return oggettoMailAura;
	}

	public void setOggettoMailAura(String oggettoMailAura) {
		this.oggettoMailAura = oggettoMailAura;
	}

	public String getTestoPrimaParteMailAura() {
		return testoPrimaParteMailAura;
	}

	public void setTestoPrimaParteMailAura(String testoPrimaParteMailAura) {
		this.testoPrimaParteMailAura = testoPrimaParteMailAura;
	}

	public String getTestoElencoMailAura() {
		return testoElencoMailAura;
	}

	public void setTestoElencoMailAura(String testoElencoMailAura) {
		this.testoElencoMailAura = testoElencoMailAura;
	}

	public String getTestoMailUserAbil() {
		return testoMailUserAbil;
	}

	public void setTestoMailUserAbil(String testoMailUserAbil) {
		this.testoMailUserAbil = testoMailUserAbil;
	}

	public String getTestoMailUserDisabil() {
		return testoMailUserDisabil;
	}

	public void setTestoMailUserDisabil(String testoMailUserDisabil) {
		this.testoMailUserDisabil = testoMailUserDisabil;
	}

}