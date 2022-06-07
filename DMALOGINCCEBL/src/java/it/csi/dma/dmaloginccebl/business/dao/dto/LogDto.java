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
@Table(name = "auth_l_log")
@SequenceGenerator(name="seq_auth_l_log", sequenceName="seq_auth_l_log",allocationSize=1)
public class LogDto extends BaseDto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_l_log")
    @Column(name = "id")
	private Long id;

	@Column(name = "codice_log")
	private String codiceLog;

	@Column(name = "informazioni_tracciate")
	private String informazioniTracciate;
	
	@Column(name = "info_aggiuntive_errore")
	private String infoAggiuntiveErrore;
	
	@Column(name = "cf_assistito")
	private String cfAssistito;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servizio", referencedColumnName = "id")
	private ServiziDto serviziDto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_catalogo_log", referencedColumnName = "id")
	private CatalogoLogDto catalogoLogDto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_messaggi", referencedColumnName = "id")
	private MessaggiDto messaggiDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceLog() {
		return codiceLog;
	}

	public void setCodiceLog(String codiceLog) {
		this.codiceLog = codiceLog;
	}

	public String getInformazioniTracciate() {
		return informazioniTracciate;
	}

	public void setInformazioniTracciate(String informazioniTracciate) {
		this.informazioniTracciate = informazioniTracciate;
	}

	public String getInfoAggiuntiveErrore() {
		return infoAggiuntiveErrore;
	}

	public void setInfoAggiuntiveErrore(String infoAggiuntiveErrore) {
		this.infoAggiuntiveErrore = infoAggiuntiveErrore;
	}

	public String getCfAssistito() {
		return cfAssistito;
	}

	public void setCfAssistito(String cfAssistito) {
		this.cfAssistito = cfAssistito;
	}

	public ServiziDto getServiziDto() {
		return serviziDto;
	}

	public void setServiziDto(ServiziDto serviziDto) {
		this.serviziDto = serviziDto;
	}

	public CatalogoLogDto getCatalogoLogDto() {
		return catalogoLogDto;
	}

	public void setCatalogoLogDto(CatalogoLogDto catalogoLogDto) {
		this.catalogoLogDto = catalogoLogDto;
	}

	public MessaggiDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}
	
}
