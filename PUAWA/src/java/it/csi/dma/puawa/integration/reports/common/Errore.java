/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;

/**
 * L'Errore ha un codice, una descrizione e il contesto in cui si è verificato.
 * 
 * @author Alberto Lagna
 * 
 */
public class Errore extends Codifica {

	private static final long serialVersionUID = 3747860812360179038L;

	public Errore() {
		super();
	}

	public Errore(String codice, String descrizione) {
		super(codice, descrizione);
	}
}