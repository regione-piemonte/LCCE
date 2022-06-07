/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.util;

public enum CatalogoParametri {
	
	TIPO_DOCUMENTO("TIPO_DOCUMENTO");
	
	private final String value;
	
	private CatalogoParametri(String v) {
		value = v;
	}
	
	public String getValue() {
        return value;
    }
	
	public static CatalogoParametri fromValue(String v) {
        for (CatalogoParametri c: CatalogoParametri.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }

}
