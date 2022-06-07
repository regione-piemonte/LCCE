/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement()
@XmlType(namespace = "http://dma.csi.it/")
public class FiltriReport implements Serializable {

	private static final long serialVersionUID = -6700892272228791627L;

	private CollocazioneReport collocazione;
	private String dataRicercaDA;
	private String dataRicercaA;

	public FiltriReport() {
	}

	public FiltriReport(CollocazioneReport collocazione) {
		super();
		this.collocazione = collocazione;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public CollocazioneReport getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(CollocazioneReport collocazione) {
		this.collocazione = collocazione;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getDataRicercaDA() {
		return dataRicercaDA;
	}

	public void setDataRicercaDA(String dataRicercaDA) {
		this.dataRicercaDA = dataRicercaDA;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getDataRicercaA() {
		return dataRicercaA;
	}

	public void setDataRicercaA(String dataRicercaA) {
		this.dataRicercaA = dataRicercaA;
	}
}