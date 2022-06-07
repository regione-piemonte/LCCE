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
@Table(name = "auth_d_ruolo")
@SequenceGenerator(name = "seq_auth_d_ruolo", sequenceName = "seq_auth_d_ruolo", allocationSize = 1)
public class RuoloDto extends CatalogoBaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_ruolo")
	@Column(name = "id")
	private Long id;

//	@OneToMany(mappedBy = "ruoloDto", cascade = CascadeType.ALL)
//	private Set<LogAuditDto> logAuditDtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Set<LogAuditDto> getLogAuditDtos() {
//		return logAuditDtos;
//	}
//
//	public void setLogAuditDtos(Set<LogAuditDto> logAuditDtos) {
//		this.logAuditDtos = logAuditDtos;
//	}
}
