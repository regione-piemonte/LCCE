/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util.mailsender;

public class MailMessage {

    private String mittenteMail;

    private String oggettoMail;

    private String testoPrimaParteMail;

    private String footer;
    
    private MailAuraParams auraParams;

	public String getMittenteMail() {
        return mittenteMail;
    }

    public void setMittenteMail(String mittenteMail) {
        this.mittenteMail = mittenteMail;
    }

    public String getOggettoMail() {
        return oggettoMail;
    }

    public void setOggettoMail(String oggettoMail) {
        this.oggettoMail = oggettoMail;
    }

    public String getTestoPrimaParteMail() {
        return testoPrimaParteMail;
    }

    public void setTestoPrimaParteMail(String testoPrimaParteMail) {
        this.testoPrimaParteMail = testoPrimaParteMail;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

	public MailAuraParams getAuraParams() {
		return auraParams;
	}

	public void setAuraParams(MailAuraParams scerevParams) {
		this.auraParams = scerevParams;
	}
}
