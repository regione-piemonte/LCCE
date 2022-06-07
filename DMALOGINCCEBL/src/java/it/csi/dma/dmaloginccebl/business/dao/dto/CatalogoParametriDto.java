/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_d_catalogo_parametri")
@SequenceGenerator(name="seq_auth_d_catalogo_parametri", sequenceName="seq_auth_d_catalogo_parametri",allocationSize=1)
public class CatalogoParametriDto extends CatalogoBaseDto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_d_catalogo_parametri")
    @Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_applicazione", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;

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

}
