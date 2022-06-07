/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reportRefertiScaricati.client;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reports.common.Errore;
import it.csi.dma.puawa.integration.reports.common.ServiceResponse;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;

/**
 * Risposta del metodo SupportoService.listaRuoli()
 * 
 * @author Alberto Lagna
 * 
 */
@XmlRootElement(name = "ReportRefertiScaricatiResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportRefertiScaricatiResponse")
public class ReportRefertiScaricatiResponse extends ServiceResponse {

	protected byte[] reportFile;

	public ReportRefertiScaricatiResponse() {
		super();
	}

	public ReportRefertiScaricatiResponse(ServiceResponse res) {
		super(res.getErrori(), res.getEsito());
	}

	public ReportRefertiScaricatiResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public ReportRefertiScaricatiResponse(Errore errore) {
		super(errore);
	}

	public byte[] getReportFile() {
		return reportFile;
	}

	public void setReportFile(byte[] reportFile) {
		this.reportFile = reportFile;
	}

}
