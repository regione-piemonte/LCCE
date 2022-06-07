/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reportRefertiScaricati.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reports.common.CollocazioneReport;
import it.csi.dma.puawa.integration.reports.common.FiltriReport;
import it.csi.dma.puawa.integration.reports.common.Richiedente;

@XmlRootElement(name = "ReportRefertiScaricati")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportRefertiScaricatiRequest", propOrder = { "collocazioneRichiedente", "filtriReport",
		"idMessaggio", "richiedente" })
public class ReportRefertiScaricatiRequest {

	private Richiedente richiedente;
	private CollocazioneReport collocazioneRichiedente;
	private FiltriReport filtriReport;
	private String idMessaggio;

	public String getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}

	public FiltriReport getFiltriReport() {
		return filtriReport;
	}

	public void setFiltriReport(FiltriReport filtriReport) {
		this.filtriReport = filtriReport;
	}

	public CollocazioneReport getCollocazioneRichiedente() {
		return collocazioneRichiedente;
	}

	public void setCollocazioneRichiedente(CollocazioneReport collocazioneRichiedente) {
		this.collocazioneRichiedente = collocazioneRichiedente;
	}

	public ReportRefertiScaricatiRequest() {
		super();
	}
}
