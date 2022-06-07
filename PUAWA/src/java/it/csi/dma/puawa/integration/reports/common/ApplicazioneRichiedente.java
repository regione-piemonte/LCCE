/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;

@XmlType(namespace = "http://dma.csi.it/")
public class ApplicazioneRichiedente extends Codifica {

	private static final long serialVersionUID = -3736633961380690890L;

	public ApplicazioneRichiedente() {
		super();
	}

	public ApplicazioneRichiedente(String codice, String descrizione) {
		super(codice, descrizione);
	}

	public ApplicazioneRichiedente(ApplicazioneRichiedenteCodice codice) {
		this(codice, null);
	}

	public ApplicazioneRichiedente(ApplicazioneRichiedenteCodice codice, String descrizione) {
		super(codice.getValue(), descrizione);
	}
}
