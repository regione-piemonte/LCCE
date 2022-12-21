/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.base;

/**
 * Risultato di un'operazione
 * 
 * @author Alberto Lagna
 *
 */

public enum RisultatoCodice {

    SUCCESSO("0000"),
    FALLIMENTO("9999");
   
    private final String value;

    RisultatoCodice(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static RisultatoCodice fromValue(String v) {
        for (RisultatoCodice c: RisultatoCodice.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static boolean isValid(String v) {
    	boolean valid = false;
        for (RisultatoCodice c: RisultatoCodice.values()) {
            if (c.value.equals(v)) {
                valid = true;
            }
        }
        return valid;
    }
    
}
