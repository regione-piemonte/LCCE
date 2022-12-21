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
@Table(name = "auth_r_opessan_profilo_posizione")
@SequenceGenerator(name="auth_r_opessan_profilo_posizione_oppppf_id_seq", sequenceName="auth_r_opessan_profilo_posizione_oppppf_id_seq",allocationSize=1)
public class OpessanProfiloPosizioneDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_r_opessan_profilo_posizione_oppppf_id_seq")
    @Column(name = "oppppf_id")
	private Long oppppfId;
	
	@ManyToOne
	@JoinColumn(name = "oppp_id", referencedColumnName = "oppp_id")
	private OpessanProfiloDto opessanProfiloDto;
    
	@ManyToOne
	@JoinColumn(name = "oppf_id", referencedColumnName = "oppf_id")
	private OpessanPosizioneDto opessanPosizioneDto;
	
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

	public Long getOppppfId() {
		return oppppfId;
	}

	public void setOppppfId(Long oppppfId) {
		this.oppppfId = oppppfId;
	}

	public OpessanProfiloDto getOpessanProfiloDto() {
		return opessanProfiloDto;
	}

	public void setOpessanProfiloDto(OpessanProfiloDto opessanProfiloDto) {
		this.opessanProfiloDto = opessanProfiloDto;
	}

	public OpessanPosizioneDto getOpessanPosizioneDto() {
		return opessanPosizioneDto;
	}

	public void setOpessanPosizioneDto(OpessanPosizioneDto opessanPosizioneDto) {
		this.opessanPosizioneDto = opessanPosizioneDto;
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
