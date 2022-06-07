/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.dto;

import java.sql.Timestamp;

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
@Table(name = "auth_l_messaggi")
@SequenceGenerator(name = "seq_auth_l_messaggi", sequenceName = "seq_auth_l_messaggi", allocationSize = 1)
public class MessaggiDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_l_messaggi")
	@Column(name = "id")
	private Long id;

	@Column(name = "applicazione")
	private String applicazione;

	@Column(name = "codice_abilitazione")
	private String codiceAbilitazione;

	@Column(name = "certificato")
	private String certificato;

	@Column(name = "cf_richiedente")
	private String cfRichiedente;

	@Column(name = "ruolo_richiedente")
	private String ruoloRichiedente;

	@Column(name = "cf_assistito")
	private String cfAssistito;

	@Column(name = "client_ip")
	private String clientIp;

	@Column(name = "token")
	private String token;

	@Column(name = "data_ricezione")
	private Timestamp dataRicezione;

	@Column(name = "data_risposta")
	private Timestamp dataRisposta;

	@Column(name = "esito")
	private String esito;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_abilitazione", referencedColumnName = "id")
	private AbilitazioneDto abilitazioneDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servizio", referencedColumnName = "id")
	private ServiziDto serviziDto;

//	@OneToMany(mappedBy = "messaggiDto", cascade = CascadeType.ALL)
//	private Set<LogDto> logDtos;
//	
//	@OneToMany(mappedBy = "messaggiDto", cascade = CascadeType.ALL)
//	private Set<MessaggiErroriDto> messaggiErroriDtos;
//	
//	@OneToMany(mappedBy = "messaggiDto", cascade = CascadeType.ALL)
//	private Set<ServiziRichiamatiXmlDto> serviziRichiamatiXmlDtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public String getCfRichiedente() {
		return cfRichiedente;
	}

	public void setCfRichiedente(String cfRichiedente) {
		this.cfRichiedente = cfRichiedente;
	}

	public String getRuoloRichiedente() {
		return ruoloRichiedente;
	}

	public void setRuoloRichiedente(String ruoloRichiedente) {
		this.ruoloRichiedente = ruoloRichiedente;
	}

	public String getCfAssistito() {
		return cfAssistito;
	}

	public void setCfAssistito(String cfAssistito) {
		this.cfAssistito = cfAssistito;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Timestamp dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public Timestamp getDataRisposta() {
		return dataRisposta;
	}

	public void setDataRisposta(Timestamp dataRisposta) {
		this.dataRisposta = dataRisposta;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public ServiziDto getServiziDto() {
		return serviziDto;
	}

	public void setServiziDto(ServiziDto serviziDto) {
		this.serviziDto = serviziDto;
	}

	public AbilitazioneDto getAbilitazioneDto() {
		return abilitazioneDto;
	}

	public void setAbilitazioneDto(AbilitazioneDto abilitazioneDto) {
		this.abilitazioneDto = abilitazioneDto;
	}

	public String getCodiceAbilitazione() {
		return codiceAbilitazione;
	}

	public void setCodiceAbilitazione(String codiceAbilitazione) {
		this.codiceAbilitazione = codiceAbilitazione;
	}

	public String getCertificato() {
		return certificato;
	}

	public void setCertificato(String certificato) {
		this.certificato = certificato;
	}

//	public Set<LogDto> getLogDtos() {
//		return logDtos;
//	}
//
//	public void setLogDtos(Set<LogDto> logDtos) {
//		this.logDtos = logDtos;
//	}
//
//	public Set<MessaggiErroriDto> getMessaggiErroriDtos() {
//		return messaggiErroriDtos;
//	}
//
//	public void setMessaggiErroriDtos(Set<MessaggiErroriDto> messaggiErroriDtos) {
//		this.messaggiErroriDtos = messaggiErroriDtos;
//	}
//
//	public Set<ServiziRichiamatiXmlDto> getServiziRichiamatiXmlDtos() {
//		return serviziRichiamatiXmlDtos;
//	}
//
//	public void setServiziRichiamatiXmlDtos(Set<ServiziRichiamatiXmlDto> serviziRichiamatiXmlDtos) {
//		this.serviziRichiamatiXmlDtos = serviziRichiamatiXmlDtos;
//	}

}
