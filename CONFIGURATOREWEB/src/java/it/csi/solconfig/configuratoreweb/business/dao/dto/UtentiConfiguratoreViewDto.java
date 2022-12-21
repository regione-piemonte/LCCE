/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;


@Entity
@Table(name = "v_utenti_configuratore_2")
public class UtentiConfiguratoreViewDto {

	@Id
	@Column(name = "row_number")
	private Long row_number;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;

	@Column(name = "cognome")
	private String cognome;

	@Column(name = "nome")
	private String nome;

	@Column(name = "utente_inserito_dal_configuratore")
	private String utenteInseritoConfiguratore;

	@Column(name = "codice_ruolo")
	private String codiceRuolo;

	@Column(name = "ruolo")
	private String ruolo;

	@Column(name = "codice_azienda_sanitaria")
	private String codiceAziendaSanitaria;

	@Column(name = "descrizione_azienda_sanitaria")
	private String descrizioneAziendaSanitaria;

	@Column(name = "codice_struttura_sanitaria")
	private String codiceStrutturaSanitaria;

	@Column(name = "descrizione_struttura_sanitaria")
	private String descrizioneStrutturaSanitaria;

	@Column(name = "sol")
	private String sol;

	@Column(name = "gestito_da_configuratore")
	private String gestitoConfiguratore;

	@Column(name = "profili")
	private String profili;

	@Column(name = "data_inizio_abilitazione")
	private Timestamp dataInizioAbilitazione;

	@Column(name = "data_fine_abilitazione")
	private Timestamp dataFineAbilitazione;
	
	@Column(name = "cf_operatore")
	private String cfOperatore;

	public Timestamp getDataInizioAbilitazione() {
		return dataInizioAbilitazione;
	}

	public void setDataInizioAbilitazione(Timestamp dataInizioAbilitazione) {
		this.dataInizioAbilitazione = dataInizioAbilitazione;
	}

	public Timestamp getDataFineAbilitazione() {
		return dataFineAbilitazione;
	}

	public void setDataFineAbilitazione(Timestamp dataFineAbilitazione) {
		this.dataFineAbilitazione = dataFineAbilitazione;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public Long getRow_number() {
		return row_number;
	}

	public void setRow_number(Long row_number) {
		this.row_number = row_number;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUtenteInseritoConfiguratore() {
		return utenteInseritoConfiguratore;
	}

	public void setUtenteInseritoConfiguratore(String utenteInseritoConfiguratore) {
		this.utenteInseritoConfiguratore = utenteInseritoConfiguratore;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getCodiceAziendaSanitaria() {
		return codiceAziendaSanitaria;
	}

	public void setCodiceAziendaSanitaria(String codiceAziendaSanitaria) {
		this.codiceAziendaSanitaria = codiceAziendaSanitaria;
	}

	public String getDescrizioneAziendaSanitaria() {
		return descrizioneAziendaSanitaria;
	}

	public void setDescrizioneAziendaSanitaria(String descrizioneAziendaSanitaria) {
		this.descrizioneAziendaSanitaria = descrizioneAziendaSanitaria;
	}

	public String getCodiceStrutturaSanitaria() {
		return codiceStrutturaSanitaria;
	}

	public void setCodiceStrutturaSanitaria(String codiceStrutturaSanitaria) {
		this.codiceStrutturaSanitaria = codiceStrutturaSanitaria;
	}

	public String getDescrizioneStrutturaSanitaria() {
		return descrizioneStrutturaSanitaria;
	}

	public void setDescrizioneStrutturaSanitaria(String descrizioneStrutturaSanitaria) {
		this.descrizioneStrutturaSanitaria = descrizioneStrutturaSanitaria;
	}

	public String getSol() {
		return sol;
	}

	public void setSol(String sol) {
		this.sol = sol;
	}

	public String getGestitoConfiguratore() {
		return gestitoConfiguratore;
	}

	public void setGestitoConfiguratore(String gestitoConfiguratore) {
		this.gestitoConfiguratore = gestitoConfiguratore;
	}

	public String getProfili() {
		return profili;
	}

	public void setProfili(String profili) {
		this.profili = profili;
	}

	public UtentiConfiguratoreViewDto(Object[] o) {
		BigInteger i= (BigInteger) o[0];
		this.row_number= i.longValue();
		this.codiceFiscale = (String) o[1];
		this.cognome = (String) o[2];
		this.nome = (String) o[3];
		this.utenteInseritoConfiguratore = (String) o[4];
		this.codiceRuolo = (String) o[5];
		this.ruolo = (String) o[6];
		this.codiceAziendaSanitaria = (String) o[7];
		this.descrizioneAziendaSanitaria = (String) o[8];
		this.codiceStrutturaSanitaria = (String) o[9];
		this.descrizioneStrutturaSanitaria = (String) o[10];
		this.sol = (String) o[11];
		this.gestitoConfiguratore = (String) o[12];
		this.profili = (String) o[13];
		this.dataInizioAbilitazione = (Timestamp) o[14];
		this.dataFineAbilitazione = (Timestamp) o[15];
		this.cfOperatore = (String) o[16];
	}
	
	
}
