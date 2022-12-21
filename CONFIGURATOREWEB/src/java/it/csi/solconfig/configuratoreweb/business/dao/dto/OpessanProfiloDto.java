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
@Table(name = "auth_d_opessan_profilo")
@SequenceGenerator(name="auth_d_opessan_profilo_oppp_id_seq", sequenceName="auth_d_opessan_profilo_oppp_id_seq",allocationSize=1)
public class OpessanProfiloDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_d_opessan_profilo_oppp_id_seq")
    @Column(name = "oppp_id")
	private Long opppId;
	
	@Column(name = "oppp_codice")
	private String opppCodice;
	
	@Column(name = "oppp_descrizione")
	private String opppDescrizione;
	
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
	
	public Long getOpppId() {
		return opppId;
	}

	public void setOpppId(Long opppId) {
		this.opppId = opppId;
	}

	public String getOpppCodice() {
		return opppCodice;
	}

	public void setOpppCodice(String opppCodice) {
		this.opppCodice = opppCodice;
	}

	public String getOpppDescrizione() {
		return opppDescrizione;
	}

	public void setOpppDescrizione(String opppDescrizione) {
		this.opppDescrizione = opppDescrizione;
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
