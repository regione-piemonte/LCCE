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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_d_catalogo_log")
@SequenceGenerator(name = "seq_auth_d_catalogo_log", sequenceName = "seq_auth_d_catalogo_log", allocationSize = 1)
public class CatalogoLogDto extends CatalogoBaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_catalogo_log")
	@Column(name = "id")
	private Long id;

	@Column(name = "fonte")
	private String fonte;

	@Column(name = "tipo_errore")
	private String tipoErrore;

	@Column(name = "descrizione_log")
	private String descrizioneLog;

//	@OneToMany(mappedBy = "catalogoLogDto", cascade = CascadeType.ALL)
//	private Set<LogDto> logDtoList;
//	
//	@OneToMany(mappedBy = "catalogoLogDto", cascade = CascadeType.ALL)
//	private Set<MessaggiErroriDto> messaggiErroriDtoList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public String getTipoErrore() {
		return tipoErrore;
	}

	public void setTipoErrore(String tipoErrore) {
		this.tipoErrore = tipoErrore;
	}

	public String getDescrizioneLog() {
		return descrizioneLog;
	}

	public void setDescrizioneLog(String descrizioneLog) {
		this.descrizioneLog = descrizioneLog;
	}

//	public Set<LogDto> getLogDtoList() {
//		return logDtoList;
//	}
//
//	public void setLogDtoList(Set<LogDto> logDtoList) {
//		this.logDtoList = logDtoList;
//	}
//
//	public Set<MessaggiErroriDto> getMessaggiErroriDtoList() {
//		return messaggiErroriDtoList;
//	}
//
//	public void setMessaggiErroriDtoList(Set<MessaggiErroriDto> messaggiErroriDtoList) {
//		this.messaggiErroriDtoList = messaggiErroriDtoList;
//	}

}
