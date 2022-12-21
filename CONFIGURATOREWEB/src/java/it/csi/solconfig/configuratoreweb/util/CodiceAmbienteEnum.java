/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

public enum CodiceAmbienteEnum {

	TEST("test"),
	COLL("coll"),
	CERT("cert"),
	PROD("prod");

    private String value;

    private CodiceAmbienteEnum(String value){

        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
