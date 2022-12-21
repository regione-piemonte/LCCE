/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.base;

import javax.xml.bind.annotation.XmlType;

/**
 * L'Errore ha un codice, una descrizione e il contesto in cui si è verificato.
 * 
 * @author Alberto Lagna
 * 
 */
@XmlType(namespace = "http://dma.csi.it/")
public class Errore extends Codifica {

	private static final long serialVersionUID = 3747860845860179038L;

	public Errore() {
		super();
	}

	public Errore(String codice, String descrizione) {
		super(codice, descrizione);
	}
}