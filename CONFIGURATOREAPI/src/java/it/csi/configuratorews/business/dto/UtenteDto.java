/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	
	@Column(name = "indirizzo_mail")
	private String indirizzoMail;
	
	@Column(name = "numero_telefono")
	private String numeroTelefono;
	
	@ManyToOne
	@JoinColumn(name = "id_operatore", referencedColumnName = "id")
	private UtenteDto utenteDto;
	
	@ManyToOne
	@JoinColumn(name = "id_contratto", referencedColumnName = "id")
	private TipoContrattoDto tipoContrattoDto;

	@OneToMany
	@JoinColumn(name = "ute_id")
	private List<UtenteCollocazioneDto> utenteCollocazioneList;

	@OneToMany
	@JoinColumn(name = "id_utente")
	private List<RuoloUtenteDto> ruoloUtenteList;
//	@OneToMany(mappedBy = "utenteDto", cascade = CascadeType.ALL)
//	private Set<LogAuditDto> logAuditDtos;

	@ColumnTransformer(
		    read =  "pgp_sym_decrypt(" +
		            "    email_preferenze::bytea, " +
		            "    '@encryption_key@'" +
		            ")",
		    write = "pgp_sym_encrypt( " +
		            "    ?, " +
		            "    '@encryption_key@'" +
		            ") "
		)
	@Column(name = "email_preferenze")
	private String emailPreferenze;
	
	
	@ColumnTransformer(
		    read =  "pgp_sym_decrypt(" +
		            "    telefono_preferenze::bytea, " +
		            "    '@encryption_key@'" +
		            ")",
		    write = "pgp_sym_encrypt( " +
		            "    ?, " +
		            "    '@encryption_key@'" +
		            ") "
		)
	@Column(name = "telefono_preferenze")
	private String telefonoPreferenze;
	
	@Column(name = "token_push")
	private String tokenPush;

	@Column(name = "ultimo_accesso_pua")
	private Timestamp ultimoAccessoPua;
	
	public String getEmailPreferenze() {
		return emailPreferenze;
	}

	public void setEmailPreferenze(String emailPreferenze) {
		this.emailPreferenze = emailPreferenze;
	}

	public String getTelefonoPreferenze() {
		return telefonoPreferenze;
	}

	public void setTelefonoPreferenze(String telefonoPreferenze) {
		this.telefonoPreferenze = telefonoPreferenze;
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
	
	
	public Timestamp getUltimoAccessoPua() {
		return ultimoAccessoPua;
	}

	public void setUltimoAccessoPua(Timestamp ultimoAccessoPua) {
		this.ultimoAccessoPua = ultimoAccessoPua;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UtenteDto utenteDto = (UtenteDto) o;
		return getId().equals(utenteDto.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	public String getTokenPush() {
		return tokenPush;
	}

	public void setTokenPush(String tokenPush) {
		this.tokenPush = tokenPush;
	}
}
