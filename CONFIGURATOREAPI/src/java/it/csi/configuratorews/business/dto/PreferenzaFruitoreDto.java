/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_t_preferenza_fruitore")
@SequenceGenerator(name = "preferenza_fruitore_id_seq", sequenceName = "auth_t_preferenza_fruitore_preferenza_fruitore_id_seq", allocationSize = 1)
public class PreferenzaFruitoreDto  extends BaseDto{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preferenza_fruitore_id_seq")
	@Column(name = "preferenza_fruitore_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_applicazione", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruoloDto;
	
	@ManyToOne
	@JoinColumn(name = "col_id", referencedColumnName = "col_id")
	private CollocazioneDto collocazioneDto;
	
	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;
	
	@Column(name = "fruitore_nome")
	private String nomeFruitore;
	
	@Column(name = "fruitore_descrizione")
	private String descrizioneFruitore;
	
	@Column(name = "email")
	private Boolean email;
	
	@Column(name = "push")
	private Boolean push;
	
	@Column(name = "sms")
	private Boolean sms;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicazioneDto getApplicazioneDto() {
		return applicazioneDto;
	}

	public void setApplicazioneDto(ApplicazioneDto applicazioneDto) {
		this.applicazioneDto = applicazioneDto;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
	}

	public CollocazioneDto getCollocazioneDto() {
		return collocazioneDto;
	}

	public void setCollocazioneDto(CollocazioneDto collocazioneDto) {
		this.collocazioneDto = collocazioneDto;
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

	public String getNomeFruitore() {
		return nomeFruitore;
	}

	public void setNomeFruitore(String nomeFruitore) {
		this.nomeFruitore = nomeFruitore;
	}

	public Boolean getEmail() {
		return email;
	}

	public void setEmail(Boolean email) {
		this.email = email;
	}

	public Boolean getPush() {
		return push;
	}

	public void setPush(Boolean push) {
		this.push = push;
	}

	public Boolean getSms() {
		return sms;
	}

	public void setSms(Boolean sms) {
		this.sms = sms;
	}
	public String getDescrizioneFruitore() {
		return descrizioneFruitore;
	}

	public void setDescrizioneFruitore(String descrizioneFruitore) {
		this.descrizioneFruitore = descrizioneFruitore;
	}
}
