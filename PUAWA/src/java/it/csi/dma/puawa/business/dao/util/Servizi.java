/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.util;

public enum Servizi {
	
	AUTHENTICATION("GET_AUTH"),
	TOKEN("GET_TOK_INFO"),
	VERIFYLOGIN("VER_LOG_COND"),
	IRIDE_USER_PASSWORD("ID_US_PASS"),
	IRIDE_USER_PASSWORD_PIN("ID_US_PASS_PIN"),
	RUOLI_UTENTE("GET_RUOLI_UTENTE"),
	COLLOCAZIONI("GET_COLLOCAZIONI"),
	AUTHENTICATION_2("GET_AUTH2"),
	ABILITAZIONI("GET_ABI"),
	REPORT_REFERTI_SCARTATI("REP_REF_SCA"),
	REPORT_OPERAZIONI_CONSENSI("REP_OP_CONS"),
	NOTIFICA_CITTADINO("NOTIFICA_CITTADINO");
	
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
