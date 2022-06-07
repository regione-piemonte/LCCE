/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;

@XmlType(namespace = "http://dma.csi.it/")
public class RegimeDMA extends Codifica {

	private static final long serialVersionUID = 6860853850452698610L;

	public RegimeDMA() {
		super();
	}

	public RegimeDMA(String codice, String descrizione) {
		super(codice, descrizione);
	}

	public RegimeDMA(RegimeDMACodice codice) {
		super(codice.getValue(), codice.getValue());
	}
}