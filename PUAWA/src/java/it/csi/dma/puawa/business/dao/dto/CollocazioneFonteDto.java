/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_d_collocazione_fonte")
@SequenceGenerator(name = "auth_d_collocazione_fonte_col_fonte_id_seq", sequenceName = "auth_d_collocazione_fonte_col_fonte_id_seq", allocationSize = 1)
public class CollocazioneFonteDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_d_collocazione_fonte_col_fonte_id_seq")
	@Column(name = "col_fonte_id")
	private Long idColFonte;

	@Column(name = "col_fonte_codice")
	private String colFonteCodice;

	@Column(name = "col_fonte_descrizione")
	private String colFonteDescrizione;

	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;

	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;

	@Column(name = "cf_operatore")
	private String cfOperatore;

	public Long getIdColFonte() {
		return idColFonte;
	}

	public void setIdColFonte(Long idColFonte) {
		this.idColFonte = idColFonte;
	}

	public String getColFonteCodice() {
		return colFonteCodice;
	}

	public void setColFonteCodice(String colFonteCodice) {
		this.colFonteCodice = colFonteCodice;
	}

	public String getColFonteDescrizione() {
		return colFonteDescrizione;
	}

	public void setColFonteDescrizione(String colFonteDescrizione) {
		this.colFonteDescrizione = colFonteDescrizione;
	}

	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}
}
