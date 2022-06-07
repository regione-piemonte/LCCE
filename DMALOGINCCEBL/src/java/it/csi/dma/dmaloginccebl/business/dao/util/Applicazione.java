/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.util;

public enum Applicazione {
	
	DMAWA("DMAWA"),
	CCE("CCE"),
	SANSOL("SANSOL");
	
	private final String value;

	Applicazione(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static Applicazione fromValue(String v) {
        for (Applicazione c: Applicazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
