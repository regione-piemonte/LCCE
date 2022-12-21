/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Date;

public class CollocazioneUtente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6431791314179571647L;

	private String collocazioneCodice;
	private String collocazioneDescrizione;
	private String collocazioneCodiceAzienda;
	private String collocazioneDescrizioneAzienda;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private String strutturaCodice;
	private String strutturaDescrizione;
	private String uoCodice;
	private String uoDescrizione;
	private String multiSpecCodice;
	private String multiSpecDescrizione;
	private String elementoOrganizzativoCodice;
	private String elementoOrganizzativoDescrizione;
	private String ambulatorioID;
	private String ambulatorioDescrizione;
	private String colTipoCodice;
	private String colTipoDescrizione;

	public String getCollocazioneCodice() {
		return collocazioneCodice;
	}

	public void setCollocazioneCodice(String collocazioneCodice) {
		this.collocazioneCodice = collocazioneCodice;
	}

	public String getCollocazioneDescrizione() {
		return collocazioneDescrizione;
	}

	public void setCollocazioneDescrizione(String collocazioneDescrizione) {
		this.collocazioneDescrizione = collocazioneDescrizione;
	}

	public String getCollocazioneCodiceAzienda() {
		return collocazioneCodiceAzienda;
	}

	public void setCollocazioneCodiceAzienda(String collocazioneCodiceAzienda) {
		this.collocazioneCodiceAzienda = collocazioneCodiceAzienda;
	}

	public String getCollocazioneDescrizioneAzienda() {
		return collocazioneDescrizioneAzienda;
	}

	public void setCollocazioneDescrizioneAzienda(String collocazioneDescrizioneAzienda) {
		this.collocazioneDescrizioneAzienda = collocazioneDescrizioneAzienda;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getStrutturaCodice() {
		return strutturaCodice;
	}

	public void setStrutturaCodice(String strutturaCodice) {
		this.strutturaCodice = strutturaCodice;
	}

	public String getStrutturaDescrizione() {
		return strutturaDescrizione;
	}

	public void setStrutturaDescrizione(String strutturaDescrizione) {
		this.strutturaDescrizione = strutturaDescrizione;
	}

	public String getUoCodice() {
		return uoCodice;
	}

	public void setUoCodice(String uoCodice) {
		this.uoCodice = uoCodice;
	}

	public String getUoDescrizione() {
		return uoDescrizione;
	}

	public void setUoDescrizione(String uoDescrizione) {
		this.uoDescrizione = uoDescrizione;
	}

	public String getMultiSpecCodice() {
		return multiSpecCodice;
	}

	public void setMultiSpecCodice(String multiSpecCodice) {
		this.multiSpecCodice = multiSpecCodice;
	}

	public String getMultiSpecDescrizione() {
		return multiSpecDescrizione;
	}

	public void setMultiSpecDescrizione(String multiSpecDescrizione) {
		this.multiSpecDescrizione = multiSpecDescrizione;
	}

	public String getElementoOrganizzativoCodice() {
		return elementoOrganizzativoCodice;
	}

	public void setElementoOrganizzativoCodice(String elementoOrganizzativoCodice) {
		this.elementoOrganizzativoCodice = elementoOrganizzativoCodice;
	}

	public String getElementoOrganizzativoDescrizione() {
		return elementoOrganizzativoDescrizione;
	}

	public void setElementoOrganizzativoDescrizione(String elementoOrganizzativoDescrizione) {
		this.elementoOrganizzativoDescrizione = elementoOrganizzativoDescrizione;
	}

	public String getAmbulatorioID() {
		return ambulatorioID;
	}

	public void setAmbulatorioID(String ambulatorioID) {
		this.ambulatorioID = ambulatorioID;
	}

	public String getAmbulatorioDescrizione() {
		return ambulatorioDescrizione;
	}

	public void setAmbulatorioDescrizione(String ambulatorioDescrizione) {
		this.ambulatorioDescrizione = ambulatorioDescrizione;
	}

	public String getColTipoCodice() {
		return colTipoCodice;
	}

	public void setColTipoCodice(String colTipoCodice) {
		this.colTipoCodice = colTipoCodice;
	}

	public String getColTipoDescrizione() {
		return colTipoDescrizione;
	}

	public void setColTipoDescrizione(String colTipoDescrizione) {
		this.colTipoDescrizione = colTipoDescrizione;
	}

}
