/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.sql.Timestamp;

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
@Table(name = "auth_l_login_data")
@SequenceGenerator(name = "seq_auth_l_login_data", sequenceName = "seq_auth_l_login_data", allocationSize = 1)
public class LoginDataDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_l_login_data")
	@Column(name = "id")
	private Long id;

	@Column(name = "cf_richiedente")
	private String cfRichiedente;

	@Column(name = "cf_assistito")
	private String cfAssistito;

	@Column(name = "client_ip")
	private String clientIp;

	@Column(name = "remote_ip")
	private String remoteIp;

	@Column(name = "token")
	private String token;

	@Column(name = "data_richiesta")
	private Timestamp dataRichiestaToken;

	@Column(name = "data_utilizzo")
	private Timestamp dataUtilizzoToken;

	@Column(name = "ruolo_richiedente")
	private String ruoloRichiedente;

	@Column(name = "coll_codice_azienda")
	private String collCodiceAzienda;

	@Column(name = "codice_collocazione")
	private String codiceCollocazione;

	@Column(name = "applicazione_richiesta")
	private String applicazioneRichiesta;

	@Column(name = "applicazione_chiamante")
	private String applicazioneChiamante;

	@ManyToOne
	@JoinColumn(name = "id_servizio_chiamante", referencedColumnName = "id")
	private ServiziDto serviziDto;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;

	@ManyToOne
	@JoinColumn(name = "id_abilitazione", referencedColumnName = "id")
	private AbilitazioneDto abilitazioneDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCfRichiedente() {
		return cfRichiedente;
	}

	public void setCfRichiedente(String cfRichiedente) {
		this.cfRichiedente = cfRichiedente;
	}

	public String getCfAssistito() {
		return cfAssistito;
	}

	public void setCfAssistito(String cfAssistito) {
		this.cfAssistito = cfAssistito;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getDataRichiestaToken() {
		return dataRichiestaToken;
	}

	public void setDataRichiestaToken(Timestamp dataRichiestaToken) {
		this.dataRichiestaToken = dataRichiestaToken;
	}

	public Timestamp getDataUtilizzoToken() {
		return dataUtilizzoToken;
	}

	public void setDataUtilizzoToken(Timestamp dataUtilizzoToken) {
		this.dataUtilizzoToken = dataUtilizzoToken;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public AbilitazioneDto getAbilitazioneDto() {
		return abilitazioneDto;
	}

	public void setAbilitazioneDto(AbilitazioneDto abilitazioneDto) {
		this.abilitazioneDto = abilitazioneDto;
	}

	public String getRuoloRichiedente() {
		return ruoloRichiedente;
	}

	public void setRuoloRichiedente(String ruoloRichiedente) {
		this.ruoloRichiedente = ruoloRichiedente;
	}

	public String getCollCodiceAzienda() {
		return collCodiceAzienda;
	}

	public void setCollCodiceAzienda(String collCodiceAzienda) {
		this.collCodiceAzienda = collCodiceAzienda;
	}

	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}

	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}

	public String getApplicazioneRichiesta() {
		return applicazioneRichiesta;
	}

	public void setApplicazioneRichiesta(String applicazioneRichiesta) {
		this.applicazioneRichiesta = applicazioneRichiesta;
	}

	public String getApplicazioneChiamante() {
		return applicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		this.applicazioneChiamante = applicazioneChiamante;
	}

	public ServiziDto getServiziDto() {
		return serviziDto;
	}

	public void setServiziDto(ServiziDto serviziDto) {
		this.serviziDto = serviziDto;
	}
}
