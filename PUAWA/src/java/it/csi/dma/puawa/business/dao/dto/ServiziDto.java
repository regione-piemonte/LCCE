/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_d_servizi")
@SequenceGenerator(name = "seq_auth_d_servizi", sequenceName = "seq_auth_d_servizi", allocationSize = 1)
public class ServiziDto extends CatalogoBaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_servizi")
	@Column(name = "id")
	private Long id;

	@Column(name = "tipo_servizio")
	private String tipoServizio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}
}
