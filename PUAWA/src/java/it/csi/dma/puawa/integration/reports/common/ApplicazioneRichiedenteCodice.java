/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dma.csi.it/")
public enum ApplicazioneRichiedenteCodice {

	WEBAPP_DMA("WEBAPP_DMA"),
	WEBAPP_CM("WEBAPP_CM"),
	WEBAPP_UC("WEBAPP_UC"),
	WEBAPP_SR("WEBAPP_SR"),
	FSE("FSE"),
	IMR("IMR"),
	ROPVA("ROPVA"),
	INI("INI"),
	UNDEFINED_VALUE("-1");

	private final String value;

	ApplicazioneRichiedenteCodice(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public static ApplicazioneRichiedenteCodice fromValue(String v) {
		for (ApplicazioneRichiedenteCodice c : ApplicazioneRichiedenteCodice.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		return UNDEFINED_VALUE;
	}

	public static boolean isValid(String v) {
		boolean valid = false;
		for (ApplicazioneRichiedenteCodice c : ApplicazioneRichiedenteCodice.values()) {
			if (c.value.equals(v)) {
				valid = true;
			}
		}
		return valid;
	}
}