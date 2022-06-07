/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.model;

import java.util.List;

import it.csi.dma.puawa.business.dao.dto.ReportTipoDto;

public class Data {

	private Utente utente;
	private String codiceRuoloSelezionato;
	private String colCodiceCollocazioneSelezionata;
	private String codiceCollocazioneReportistica;
	private String descrizioneReportSelezionato;
	private List<ReportTipoDto> reportTipoDtoList;
	private Report report;
	private NotificaData notificaData;
	private String codiceColAziendaSelezionata;

	public String getCodiceRuoloSelezionato() {
		return codiceRuoloSelezionato;
	}

	public void setCodiceRuoloSelezionato(String codiceRuoloSelezionato) {
		this.codiceRuoloSelezionato = codiceRuoloSelezionato;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getCodiceCollocazioneReportistica() {
		return codiceCollocazioneReportistica;
	}

	public void setCodiceCollocazioneReportistica(String codiceCollocazioneReportistica) {
		this.codiceCollocazioneReportistica = codiceCollocazioneReportistica;
	}

	public String getDescrizioneReportSelezionato() {
		return descrizioneReportSelezionato;
	}

	public void setDescrizioneReportSelezionato(String descrizioneReportSelezionato) {
		this.descrizioneReportSelezionato = descrizioneReportSelezionato;
	}

	public String getColCodiceCollocazioneSelezionata() {
		return colCodiceCollocazioneSelezionata;
	}

	public void setColCodiceCollocazioneSelezionata(String colCodiceCollocazioneSelezionata) {
		this.colCodiceCollocazioneSelezionata = colCodiceCollocazioneSelezionata;
	}

	public List<ReportTipoDto> getReportTipoDtoList() {
		return reportTipoDtoList;
	}

	public void setReportTipoDtoList(List<ReportTipoDto> reportTipoDtoList) {
		this.reportTipoDtoList = reportTipoDtoList;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public NotificaData getNotificaData() {
		return notificaData;
	}

	public void setNotificaData(NotificaData notificaData) {
		this.notificaData = notificaData;
	}

	public String getCodiceColAziendaSelezionata() {
		return codiceColAziendaSelezionata;
	}

	public void setCodiceColAziendaSelezionata(String codiceColAziendaSelezionata) {
		this.codiceColAziendaSelezionata = codiceColAziendaSelezionata;
	}
}
