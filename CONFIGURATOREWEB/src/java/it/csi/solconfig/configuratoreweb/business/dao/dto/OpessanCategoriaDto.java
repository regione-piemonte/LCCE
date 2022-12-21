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
@Table(name = "auth_d_opessan_categoria")
@SequenceGenerator(name="auth_d_opessan_categoria_opc_id_seq", sequenceName="auth_d_opessan_categoria_opc_id_seq",allocationSize=1)
public class OpessanCategoriaDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_d_opessan_categoria_opc_id_seq")
    @Column(name = "opc_id")
	private Long opcId;
	
	@Column(name = "opc_codice")
	private String opcCodice;
	
	@Column(name = "opc_descrizione")
	private String opcDescrizione;
	
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

	public Long getOpcId() {
		return opcId;
	}

	public void setOpcId(Long opcId) {
		this.opcId = opcId;
	}

	public String getOpcCodice() {
		return opcCodice;
	}

	public void setOpcCodice(String opcCodice) {
		this.opcCodice = opcCodice;
	}

	public String getOpcDescrizione() {
		return opcDescrizione;
	}

	public void setOpcDescrizione(String opcDescrizione) {
		this.opcDescrizione = opcDescrizione;
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
