/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_t_collocazione")
@SequenceGenerator(name = "auth_t_collocazione_col_id_seq", sequenceName = "auth_t_collocazione_col_id_seq", allocationSize = 1)
public class CollocazioneDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_t_collocazione_col_id_seq")
	@Column(name = "col_id")
	private Long colId;

	@Column(name = "col_codice")
	private String colCodice;

	@Column(name = "col_descrizione")
	private String colDescrizione;

	@Column(name = "col_cod_azienda")
	private String colCodAzienda;

	@Column(name = "col_desc_azienda")
	private String colDescAzienda;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "col_tipo_id", referencedColumnName = "col_tipo_id")
	private CollocazioneTipoDto collocazioneTipoDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "col_fonte_id", referencedColumnName = "col_fonte_id")
	private CollocazioneFonteDto collocazioneFonteDto;

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

	public Long getColId() {
		return colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
	}

	public String getColCodice() {
		return colCodice;
	}

	public void setColCodice(String colCodice) {
		this.colCodice = colCodice;
	}

	public String getColDescrizione() {
		return colDescrizione;
	}

	public void setColDescrizione(String colDescrizione) {
		this.colDescrizione = colDescrizione;
	}

	public String getColDescAzienda() {
		return colDescAzienda;
	}

	public void setColDescAzienda(String colDescAzienda) {
		this.colDescAzienda = colDescAzienda;
	}

	public CollocazioneTipoDto getCollocazioneTipoDto() {
		return collocazioneTipoDto;
	}

	public void setCollocazioneTipoDto(CollocazioneTipoDto collocazioneTipoDto) {
		this.collocazioneTipoDto = collocazioneTipoDto;
	}

	public CollocazioneFonteDto getCollocazioneFonteDto() {
		return collocazioneFonteDto;
	}

	public void setCollocazioneFonteDto(CollocazioneFonteDto collocazioneFonteDto) {
		this.collocazioneFonteDto = collocazioneFonteDto;
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

	public String getColCodAzienda() {
		return colCodAzienda;
	}

	public void setColCodAzienda(String colCodAzienda) {
		this.colCodAzienda = colCodAzienda;
	}
}