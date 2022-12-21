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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "auth_r_opessan_categoria_qualifica")
@SequenceGenerator(name="auth_r_opessan_categoria_qualifica_opcq_id_seq", sequenceName="auth_r_opessan_categoria_qualifica_opcq_id_seq",allocationSize=1)
public class OpessanCategoriaQualificaDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_r_opessan_categoria_qualifica_opcq_id_seq")
    @Column(name = "opcq_id")
	private Long opcqId;
	
	@ManyToOne
	@JoinColumn(name = "opc_id", referencedColumnName = "opc_id")
	private OpessanCategoriaDto opessanCategoriaDto;
    
	@ManyToOne
	@JoinColumn(name = "opq_id", referencedColumnName = "opq_id")
	private OpessanQualificaDto opessanQualificaDto;
	
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

	public Long getOpcqId() {
		return opcqId;
	}

	public void setOpcqId(Long opcqId) {
		this.opcqId = opcqId;
	}

	public OpessanCategoriaDto getOpessanCategoriaDto() {
		return opessanCategoriaDto;
	}

	public void setOpessanCategoriaDto(OpessanCategoriaDto opessanCategoriaDto) {
		this.opessanCategoriaDto = opessanCategoriaDto;
	}

	public OpessanQualificaDto getOpessanQualificaDto() {
		return opessanQualificaDto;
	}

	public void setOpessanQualificaDto(OpessanQualificaDto opessanQualificaDto) {
		this.opessanQualificaDto = opessanQualificaDto;
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
