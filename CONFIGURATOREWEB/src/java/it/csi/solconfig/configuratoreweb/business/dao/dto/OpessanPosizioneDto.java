/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "auth_d_opessan_posizione")
@SequenceGenerator(name="auth_d_opessan_posizione_oppf_id_seq", sequenceName="auth_d_opessan_posizione_oppf_id_seq",allocationSize=1)
public class OpessanPosizioneDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_d_opessan_posizione_oppf_id_seq")
    @Column(name = "oppf_id")
	private Long oppfId;
	
	@Column(name = "oppf_codice")
	private String oppfCodice;
	
	@Column(name = "oppf_descrizione")
	private String oppfDescrizione;
	
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

	public Long getOppfId() {
		return oppfId;
	}

	public void setOppfId(Long oppfId) {
		this.oppfId = oppfId;
	}

	public String getOppfCodice() {
		return oppfCodice;
	}

	public void setOppfCodice(String oppfCodice) {
		this.oppfCodice = oppfCodice;
	}

	public String getOppfDescrizione() {
		return oppfDescrizione;
	}

	public void setOppfDescrizione(String oppfDescrizione) {
		this.oppfDescrizione = oppfDescrizione;
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
