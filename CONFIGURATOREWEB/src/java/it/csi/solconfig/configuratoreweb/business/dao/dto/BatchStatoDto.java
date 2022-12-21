/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "auth_d_batch_stato")
@SequenceGenerator(name = "auth_d_batch_stato_batch_stato_id_seq", sequenceName = "auth_d_batch_stato_batch_stato_id_seq", allocationSize = 1)
public class BatchStatoDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_d_batch_stato_batch_stato_id_seq")
	@Column(name = "batch_stato_id")
	private Long id;

	@Column(name = "batch_stato_codice")
	private String statoBatch;

	@Column(name = "batch_stato_descrizione")
	private String descrizione;
	
	@Column(name = "data_inizio_validita")
	private Timestamp dataInizio;

	@Column(name = "data_fine_validita")
	private Timestamp dataFine;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;
	
	@Column(name = "cf_operatore")
	private String cfOperatore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatoBatch() {
		return statoBatch;
	}

	public void setStatoBatch(String statoBatch) {
		this.statoBatch = statoBatch;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Timestamp getDataFine() {
		return dataFine;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
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
