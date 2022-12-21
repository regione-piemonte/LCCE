/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_d_report_tipo")
@SequenceGenerator(name = "auth_d_report_tipo_rep_tipo_id_seq", sequenceName = "auth_d_report_tipo_rep_tipo_id_seq", allocationSize = 1)
public class ReportTipoDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_d_report_tipo_rep_tipo_id_seq")
	@Column(name = "rep_tipo_id")
	private Long repTipoId;

	@Column(name = "rep_tipo_codice")
	private String repTipoCodice;

	@Column(name = "rep_tipo_descrizione")
	private String repTipoDescrizione;

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

	public Long getRepTipoId() {
		return repTipoId;
	}

	public void setRepTipoId(Long repTipoId) {
		this.repTipoId = repTipoId;
	}

	public String getRepTipoCodice() {
		return repTipoCodice;
	}

	public void setRepTipoCodice(String repTipoCodice) {
		this.repTipoCodice = repTipoCodice;
	}

	public String getRepTipoDescrizione() {
		return repTipoDescrizione;
	}

	public void setRepTipoDescrizione(String repTipoDescrizione) {
		this.repTipoDescrizione = repTipoDescrizione;
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
