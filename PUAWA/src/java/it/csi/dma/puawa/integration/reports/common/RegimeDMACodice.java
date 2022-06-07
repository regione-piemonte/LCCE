/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

public enum RegimeDMACodice {
	/** EM emergenza */
	EM("EM"),
	/** PS pronto soccorso */
	PS("PS"),
	/** AMB visita ambulatoriale */
	AMB("AMB"),
	/** RIC ricovero */
	RIC("RIC"),

	MC("MC"),
	/** PS pronto soccorso */
	ACS("ACS"),
	/** AMB visita ambulatoriale */
	EMC("EMC"),
	/** RIC ricovero */
	SAP("SAP"),

	SAC("SAC"),

	UNDEFINED_VALUE("-1");

	private final String value;

	RegimeDMACodice(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public static RegimeDMACodice fromValue(String v) {
		for (RegimeDMACodice c : RegimeDMACodice.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		return UNDEFINED_VALUE;
	}

	public static boolean isValid(String v) {
		boolean valid = false;
		for (RegimeDMACodice c : RegimeDMACodice.values()) {
			if (c.value.equals(v)) {
				valid = true;
			}
		}
		return valid;
	}

}
