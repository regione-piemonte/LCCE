/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.util;

public enum CatalogoLogAudit {

	LOG_SUCCESSO_AUTHENTICATION("AUTH_LOG_010"),	//Token generato e Info di richiesta di autenticazione registrate
	LOG_SUCCESSO_TOKEN("AUTH_LOG_020");	//Token verificato e marcato come utilizzato
	
	private final String value;

	CatalogoLogAudit(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static CatalogoLogAudit fromValue(String v) {
        for (CatalogoLogAudit c: CatalogoLogAudit.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
