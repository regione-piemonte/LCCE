/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.msg;

import javax.xml.bind.annotation.XmlType;

/**
 * Usato come risposta binaria a molte scelte fatte dal Paziente in termini di consenso
 * 
 * @author Alberto Lagna
 *
 */
@XmlType(namespace="http://dma.csi.it/")
public enum SiNo {
    
    SI("S"),
    NO("N"),
	NA("");
	
	private final String value;

    SiNo(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }
    
    public static SiNo fromValue(String v) {
        for (SiNo c: SiNo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }    
    
    public static boolean isValid(String v) {
    	boolean valid = false;
        for (SiNo c: SiNo.values()) {
            if (c.value.equals(v)) {
                valid = true;
            }
        }
        return valid;
    }

}
