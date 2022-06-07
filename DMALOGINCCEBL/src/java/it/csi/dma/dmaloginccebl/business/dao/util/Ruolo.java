/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.util;

public enum Ruolo {
	
	MMG("MMG"),
	PLS("PLS"),
	INF("INF"),
	AAS("AAS"),
	DSA("DSA"),
	MEDOSP("MEDOSP"),
	MEDRSA("MEDRSA"),
	MEDRP("MEDRP");
	
	private final String value;
	
	private Ruolo(String v) {
		value = v;
	}
	
	public String getValue() {
        return value;
    }
	
	public static Ruolo fromValue(String v) {
        for (Ruolo c: Ruolo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
