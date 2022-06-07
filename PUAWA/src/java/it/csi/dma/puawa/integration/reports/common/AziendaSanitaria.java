/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;

@XmlType(namespace = "http://dma.csi.it/")
public class AziendaSanitaria extends Codifica {

	private static final long serialVersionUID = 8591060165775738766L;

	public AziendaSanitaria() {
		super();
		this.riferito = true;
	}

	public AziendaSanitaria(String codice, String descrizione) {
		super(codice, descrizione);
		this.riferito = true;
	}
}
