/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.model;

public class ReportisticaData {

	// E' il viewColCodice
	private String codiceCollocazione;
	private String codiceOptionReport;
	private String dataDa;
	private String dataA;

	public String getDataDa() {
		return dataDa;
	}

	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	public String getDataA() {
		return dataA;
	}

	public void setDataA(String dataA) {
		this.dataA = dataA;
	}

	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}

	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}

	public String getCodiceOptionReport() {
		return codiceOptionReport;
	}

	public void setCodiceOptionReport(String codiceOptionReport) {
		this.codiceOptionReport = codiceOptionReport;
	}
}
