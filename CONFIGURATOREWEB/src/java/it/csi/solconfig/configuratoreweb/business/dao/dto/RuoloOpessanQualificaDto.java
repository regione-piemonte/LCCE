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
@Table(name = "auth_r_ruolo_opessan_qualifica")
@SequenceGenerator(name="seq_auth_r_ruolo_opessan_qualifica", sequenceName="seq_auth_r_ruolo_opessan_qualifica",allocationSize=1)
public class RuoloOpessanQualificaDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_r_ruolo_opessan_qualifica")
    @Column(name = "ruoloopq_id")
	private Long ruoloOpqId;
	
	@ManyToOne
	@JoinColumn(name = "ruolo_id", referencedColumnName = "id")
	private RuoloDto ruoloDto;
    
	@ManyToOne
	@JoinColumn(name = "opcq_id", referencedColumnName = "opcq_id")
	private OpessanCategoriaQualificaDto opessanCategoriaQualificaDto;
	
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
	
	public Long getRuoloOpqId() {
		return ruoloOpqId;
	}

	public void setRuoloOpqId(Long ruoloOpqId) {
		this.ruoloOpqId = ruoloOpqId;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
	}

	public OpessanCategoriaQualificaDto getOpessanCategoriaQualificaDto() {
		return opessanCategoriaQualificaDto;
	}

	public void setOpessanCategoriaQualificaDto(OpessanCategoriaQualificaDto opessanCategoriaQualificaDto) {
		this.opessanCategoriaQualificaDto = opessanCategoriaQualificaDto;
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
