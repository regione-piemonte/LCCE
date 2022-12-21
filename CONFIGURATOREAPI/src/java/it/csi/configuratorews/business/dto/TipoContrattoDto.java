/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;


@Entity
@Table(name = "auth_d_tipo_contratto")
@SequenceGenerator(name = "seq_auth_d_tipo_contratto", sequenceName = "seq_auth_d_tipo_contratto", allocationSize = 1)
public class TipoContrattoDto extends CatalogoBaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_tipo_contratto")
	@Column(name = "id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
