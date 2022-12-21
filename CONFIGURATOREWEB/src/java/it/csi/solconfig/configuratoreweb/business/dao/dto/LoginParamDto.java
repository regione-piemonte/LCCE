/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

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
@Table(name = "auth_l_login_param")
@SequenceGenerator(name = "seq_auth_l_login_param", sequenceName = "seq_auth_l_login_param", allocationSize = 1)
public class LoginParamDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_l_login_param")
	@Column(name = "id")
	private Long id;

	@Column(name = "codice")
	private String codice;

	@Column(name = "valore")
	private String valore;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_login", referencedColumnName = "id")
	private LoginDataDto loginDataDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public LoginDataDto getLoginDataDto() {
		return loginDataDto;
	}

	public void setLoginDataDto(LoginDataDto loginDataDto) {
		this.loginDataDto = loginDataDto;
	}

}
