/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.model;

public class Report {

	private byte[] reportFile;
	private String filename;

	public Report() {
	}

	public Report(byte[] reportFile, String filename) {
		this.reportFile = reportFile;
		this.filename = filename;
	}

	public byte[] getReportFile() {
		return reportFile;
	}

	public void setReportFile(byte[] reportFile) {
		this.reportFile = reportFile;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
