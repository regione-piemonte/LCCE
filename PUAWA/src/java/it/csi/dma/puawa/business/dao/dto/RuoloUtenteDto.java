/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_r_ruolo_utente")
@SequenceGenerator(name = "seq_auth_r_ruolo_utente", sequenceName = "seq_auth_r_ruolo_utente", allocationSize = 1)
public class RuoloUtenteDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_r_ruolo_utente")
	@Column(name = "id")
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_utente", referencedColumnName = "id")
	private UtenteDto utenteDto;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruoloDto;

	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
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

}
