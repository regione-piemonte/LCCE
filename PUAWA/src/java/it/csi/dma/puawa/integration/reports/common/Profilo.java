/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;

@XmlType(namespace = "http://dma.csi.it/")
public class Profilo extends Codifica {

	private static final long serialVersionUID = -1374891578642040683L;

	public Profilo() {
		super();
	}

	public Profilo(String codice, String descrizione) {
		super(codice, descrizione);
	}
}