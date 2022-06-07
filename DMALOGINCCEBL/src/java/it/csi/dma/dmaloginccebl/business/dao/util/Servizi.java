/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.util;

public enum Servizi {
	
	AUTHENTICATION("GET_AUTH"),
	TOKEN("GET_TOK_INFO"),
	VERIFYLOGIN("VER_LOG_COND"),
	IRIDE_USER_PASSWORD("ID_US_PASS"),
	IRIDE_USER_PASSWORD_PIN("ID_US_PASS_PIN"),
	RUOLI_UTENTE("GET_RUOLI_UTENTE"),
	COLLOCAZIONI("GET_COLLOCAZIONI"),
    AUTHENTICATION2("GET_AUTH2"),
    VERIFYLOGIN2("VER_LOG_COND2"),
    TOKEN2("GET_TOK_INFO2"),
    ABILITAZIONE("GET_ABI"),
	FARMACIE_ADERENTI("FAR_ADE"), 
	VERIFICA_FARMACISTA("VER_FAR");
	
	private final String value;

	Servizi(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static Servizi fromValue(String v) {
        for (Servizi c: Servizi.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
