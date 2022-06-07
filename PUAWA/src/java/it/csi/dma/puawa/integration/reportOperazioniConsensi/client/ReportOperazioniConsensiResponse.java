/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reportOperazioniConsensi.client;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reports.common.Errore;
import it.csi.dma.puawa.integration.reports.common.ServiceResponse;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;

@XmlRootElement(name = "reportOperazioniConsensiResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reportOperazioniConsensiResponse")
public class ReportOperazioniConsensiResponse extends ServiceResponse {

	protected byte[] reportFile;

	public ReportOperazioniConsensiResponse() {
		super();
	}

	public ReportOperazioniConsensiResponse(ServiceResponse res) {
		super(res.getErrori(), res.getEsito());
	}

	public ReportOperazioniConsensiResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public ReportOperazioniConsensiResponse(Errore errore) {
		super(errore);
	}

	public byte[] getReportFile() {
		return reportFile;
	}

	public void setReportFile(byte[] reportFile) {
		this.reportFile = reportFile;
	}
}