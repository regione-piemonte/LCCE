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
@Table(name = "auth_t_preferenza_salvata")
@SequenceGenerator(name = "preferenza_salvata_id_seq", sequenceName = "auth_t_preferenza_salvata_preferenza_salvata_id_seq", allocationSize = 1)
public class PreferenzaSalvataDto extends BaseDto{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preferenza_salvata_id_seq")
	@Column(name = "preferenza_salvata_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "preferenza_fruitore_id", referencedColumnName = "preferenza_fruitore_id")
	private PreferenzaFruitoreDto preferenzaFruitoreDto;
	
	@ManyToOne
	@JoinColumn(name = "preferenza_id", referencedColumnName = "preferenza_id")
	private PreferenzaDto preferenzaDto;
	
	@Column(name = "email")
	private Boolean email;
	
	@Column(name = "push")
	private Boolean push;
	
	@Column(name = "sms")
	private Boolean sms;
	
	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PreferenzaFruitoreDto getPreferenzaFruitoreDto() {
		return preferenzaFruitoreDto;
	}

	public void setPreferenzaFruitoreDto(PreferenzaFruitoreDto preferenzaFruitoreDto) {
		this.preferenzaFruitoreDto = preferenzaFruitoreDto;
	}

	public PreferenzaDto getPreferenzaDto() {
		return preferenzaDto;
	}

	public void setPreferenzaDto(PreferenzaDto preferenzaDto) {
		this.preferenzaDto = preferenzaDto;
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


}
