/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;

@Entity
@Table(name = "auth_l_log_audit")
@SequenceGenerator(name = "seq_auth_l_log_audit", sequenceName = "seq_auth_l_log_audit", allocationSize = 1)
public class LogAuditDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_l_log_audit")
	@Column(name = "id")
	private Long id;

	@Column(name = "codice_log")
	private String codiceLog;

	@Column(name = "informazioni_tracciate")
	private String informazioniTracciate;

	@Column(name = "token")
	private String token;

	@Column(name = "cf_assistito")
	private String cfAssistito;

	@Column(name = "codice_fiscale_richiedente")
	private String cfRichiedente;

	@Column(name = "ip_richiedente")
	private String ipRichiedente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_catalogo_log_audit", referencedColumnName = "id")
	private CatalogoLogAuditDto catalogoLogAuditDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_applicazione", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utente_richiedente", referencedColumnName = "id")
	private UtenteDto utenteDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruoloDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servizio", referencedColumnName = "id")
	private ServiziDto serviziDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_abilitazione", referencedColumnName = "id")
	private AbilitazioneDto abilitazioneDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_messaggio", referencedColumnName = "id")
	private MessaggiDto messaggiDto;

	@Column(name = "col_cod_azienda")
	private String colCodAzienda;

	@Column(name = "codice_collocazione")
	private String codiceCollocazione;
	
	@Column(name = "id_richiesta")
	private String idRichiesta;
	
	@Column(name = "id_sistema_richiedente")
	private Long idSistemaRichiedente;

	public String getColCodAzienda() {
		return colCodAzienda;
	}

	public void setColCodAzienda(String colCodAzienda) {
		this.colCodAzienda = colCodAzienda;
	}

	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}

	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCfAssistito() {
		return cfAssistito;
	}

	public void setCfAssistito(String cfAssistito) {
		this.cfAssistito = cfAssistito;
	}

	public String getCfRichiedente() {
		return cfRichiedente;
	}

	public void setCfRichiedente(String cfRichiedente) {
		this.cfRichiedente = cfRichiedente;
	}

	public String getIpRichiedente() {
		return ipRichiedente;
	}

	public void setIpRichiedente(String ipRichiedente) {
		this.ipRichiedente = ipRichiedente;
	}

	public CatalogoLogAuditDto getCatalogoLogAuditDto() {
		return catalogoLogAuditDto;
	}

	public void setCatalogoLogAuditDto(CatalogoLogAuditDto catalogoLogAuditDto) {
		this.catalogoLogAuditDto = catalogoLogAuditDto;
	}

	public ApplicazioneDto getApplicazioneDto() {
		return applicazioneDto;
	}

	public void setApplicazioneDto(ApplicazioneDto applicazioneDto) {
		this.applicazioneDto = applicazioneDto;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
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

	public MessaggiDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public Long getIdSistemaRichiedente() {
		return idSistemaRichiedente;
	}

	public void setIdSistemaRichiedente(Long idSistemaRichiedente) {
		this.idSistemaRichiedente = idSistemaRichiedente;
	}
}
