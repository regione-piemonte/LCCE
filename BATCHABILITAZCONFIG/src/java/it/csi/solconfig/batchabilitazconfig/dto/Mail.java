/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dto;

public class Mail {
	
	private String subject;
	private String body;
	private String footer;
	
	private String emailAura;
	private String oggettoMailAura;
	private String testoPrimaParteMailAura;
	private String testoElencoMailAura;
	private String testoMailUserAbil;
	private String testoMailUserDisabil;
	
	public Mail() {
		super();
	}

	public Mail(String subject, String body, String footer) {
		super();
		this.subject = subject;
		this.body = body;
		this.footer = footer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
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
