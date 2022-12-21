/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

public enum TipologiaDatoEnum {

    DATI_ANAGRAFICI("Dati anagrafici"),
    DATI_PRESCRITTIVI("Dati prescrittivi"),
    DATI_CLINICI("Dati clinici"),
    DATI_DI_CONSENSO("Dati di consenso (privacy)"),
    DATI_AMMINISTRATIVI("Dati amministrativi");

    private String value;

    private TipologiaDatoEnum(String value){

        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
