/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;

@Entity
@Table(name = "auth_l_messaggi_errore")
@SequenceGenerator(name = "seq_auth_l_messaggi_errore", sequenceName = "seq_auth_l_messaggi_errore", allocationSize = 1)
public class MessaggiErroreDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_l_messaggi_errore")
	@Column(name = "id")
	private Long id;

	@Column(name = "codice_errore")
	private String codiceErrore;

	@Column(name = "descrizione_errore")
	private String descrizioneErrore;

	@Column(name = "controllore")
	private String controllore;

	@Column(name = "tipo_errore")
	private String tipoErrore;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_messaggio", referencedColumnName = "id")
	private MessaggiDto messaggiDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_catalogo_log", referencedColumnName = "id")
	private CatalogoLogDto catalogoLogDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getControllore() {
		return controllore;
	}

	public String getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public void setControllore(String controllore) {
		this.controllore = controllore;
	}

	public String getTipoErrore() {
		return tipoErrore;
	}

	public void setTipoErrore(String tipoErrore) {
		this.tipoErrore = tipoErrore;
	}

	public MessaggiDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}

	public CatalogoLogDto getCatalogoLogDto() {
		return catalogoLogDto;
	}

	public void setCatalogoLogDto(CatalogoLogDto catalogoLogDto) {
		this.catalogoLogDto = catalogoLogDto;
	}

}
