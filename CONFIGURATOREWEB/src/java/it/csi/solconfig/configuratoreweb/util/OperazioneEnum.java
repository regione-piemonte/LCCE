/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

public enum OperazioneEnum {

	LOGIN("login"),
	LOGOUT("logout"), 
	READ("read"),
	INSERT("insert"), 
	UPDATE("update"), 
	DELETE("delete"), 
	MERGE("merge"),
	INVIO_MAIL("invio_mail"),
    CREDENZIALI_RUPAR("credenziali_rupar");

    private String value;

    private OperazioneEnum(String value){

        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
