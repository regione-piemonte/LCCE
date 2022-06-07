/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.util;

public enum CatalogoLogAudit {

	LOG_SUCCESSO_AUTHENTICATION("AUTH_LOG_010"),	//Token generato e Info di richiesta di autenticazione registrate
	LOG_SUCCESSO_TOKEN("AUTH_LOG_020"),	//Token verificato e marcato come utilizzato
	LOG_SUCCESSO_RUOLI_UTENTE("AUTH_LOG_030"),		//Lista ruoli recuperata
	LOG_SUCCESSO_COLLOCAZIONI("AUTH_LOG_040"),		//Lista Collocazioni recuperata
    LOG_SUCCESSO_ABILITAZIONE("AUTH_LOG_100"); //Lista abilitazioni recuperata
	
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
