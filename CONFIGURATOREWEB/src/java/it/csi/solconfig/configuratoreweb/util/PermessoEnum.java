/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

public enum PermessoEnum {

    NA("N.A."),
    LETTURA_SCRITTURA("Lettura/Scrittura"),
    LETTURA("Lettura");

    private String value;

    private PermessoEnum(String value){

        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
