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

@Entity
@Table(name = "auth_r_applicazione_collocazione")
@SequenceGenerator(name = "auth_r_applicazione_collocazione_id_seq", sequenceName = "auth_r_applicazione_collocazione_id_seq", allocationSize = 1)
public class ApplicazioneCollocazioneDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_applicazione_collocazione_id_seq")
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_coll", referencedColumnName = "col_id")
	private CollocazioneDto collocazioneDto;
	
	@ManyToOne
	@JoinColumn(name = "id_app", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CollocazioneDto getCollocazioneDto() {
		return collocazioneDto;
	}

	public void setCollocazioneDto(CollocazioneDto collocazioneDto) {
		this.collocazioneDto = collocazioneDto;
	}

	public ApplicazioneDto getApplicazioneDto() {
		return applicazioneDto;
	}

	public void setApplicazioneDto(ApplicazioneDto applicazioneDto) {
		this.applicazioneDto = applicazioneDto;
	}
	
	
}
