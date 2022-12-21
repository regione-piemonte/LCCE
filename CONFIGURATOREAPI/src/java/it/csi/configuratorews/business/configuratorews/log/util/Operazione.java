/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.log.util;

public enum Operazione {


    SOAP("ChiamataSoap"),
    READ("READ");

    private final String value;

    Operazione(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static Operazione fromValue(String v) {
        for (Operazione c: Operazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static boolean isValid(String v) {
        boolean valid = false;
        for (Operazione c: Operazione.values()) {
            if (c.value.equals(v)) {
                valid = true;
            }
        }
        return valid;
    }
}
