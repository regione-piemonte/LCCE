/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "auth_t_utente")
@SequenceGenerator(name = "seq_auth_t_utente", sequenceName = "seq_auth_t_utente", allocationSize = 1)
public class UtenteDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_t_utente")
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cognome")
	private String cognome;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;

	@Column(name = "id_aura")
	private Long idAura;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@Column(name = "Flag_configuratore")
	private String flagConfiguratore;
	
	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;
	
	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "data_nascita")
	private Timestamp dataNascita;
	
	@Column(name = "comune_nascita")
	private String comuneNascita;
	
	@Column(name = "sesso")
	private String sesso;
	
	@ColumnTransformer(
		    read =  "pgp_sym_decrypt(" +
		            "    indirizzo_mail::bytea, " +
		            "    getGoodPwd('@encryption_key@')" +
		            ")",
		    write = "pgp_sym_encrypt( " +
		            "    ?, " +
		            "    getGoodPwd('@encryption_key@')" +
		            ") "
		)
	@Column(name = "indirizzo_mail")
	private String indirizzoMail;
	
	@ColumnTransformer(
		    read =  "pgp_sym_decrypt(" +
		            "    numero_telefono::bytea, " +
		            "    getGoodPwd('@encryption_key@')" +
		            ")",
		    write = "pgp_sym_encrypt( " +
		            "    ?, " +
		            "    getGoodPwd('@encryption_key@')" +
		            ") "
		)
	@Column(name = "numero_telefono")
	private String numeroTelefono;
	
	@ManyToOne
	@JoinColumn(name = "id_operatore", referencedColumnName = "id")
	private UtenteDto utenteDto;
	
	@ManyToOne
	@JoinColumn(name = "id_contratto", referencedColumnName = "id")
	private TipoContrattoDto tipoContrattoDto;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ute_id")
	private List<UtenteCollocazioneDto> utenteCollocazioneList;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utente")
	private List<RuoloUtenteDto> ruoloUtenteList;
//	@OneToMany(mappedBy = "utenteDto", cascade = CascadeType.ALL)
//	private Set<LogAuditDto> logAuditDtos;

	
	@ColumnTransformer(
		    read =  "pgp_sym_decrypt(" +
		            "    email_preferenze::bytea, " +
		            "    getGoodPwd('@encryption_key@')" +
		            ")",
		    write = "pgp_sym_encrypt( " +
		            "    ?, " +
		            "    getGoodPwd('@encryption_key@')" +
		            ") "
		)
	@Column(name = "email_preferenze")
	private String emailPreferenze;
	
	public String getEmailPreferenze() {
		return emailPreferenze;
	}

	public void setEmailPreferenze(String emailPreferenze) {
		this.emailPreferenze = emailPreferenze;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

	public Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getIndirizzoMail() {
		return indirizzoMail;
	}

	public void setIndirizzoMail(String indirizzoMail) {
		this.indirizzoMail = indirizzoMail;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public TipoContrattoDto getTipoContrattoDto() {
		return tipoContrattoDto;
	}

	public void setTipoContrattoDto(TipoContrattoDto tipoContrattoDto) {
		this.tipoContrattoDto = tipoContrattoDto;
	}

	public String getFlagConfiguratore() {
		return flagConfiguratore;
	}

	public void setFlagConfiguratore(String flagConfiguratore) {
		this.flagConfiguratore = flagConfiguratore;
	}

	public List<UtenteCollocazioneDto> getUtenteCollocazioneList() {
		return utenteCollocazioneList;
	}

	public void setUtenteCollocazioneList(List<UtenteCollocazioneDto> utenteCollocazioneList) {
		this.utenteCollocazioneList = utenteCollocazioneList;
	}

	public List<RuoloUtenteDto> getRuoloUtenteList() {
		return ruoloUtenteList;
	}

	public void setRuoloUtenteList(List<RuoloUtenteDto> ruoloUtenteList) {
		this.ruoloUtenteList = ruoloUtenteList;
	}

	public Long getIdAura() {
		return idAura;
	}

	public void setIdAura(Long idAura) {
		this.idAura = idAura;
	}
}
