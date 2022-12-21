/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteCollocazioneDto;

import java.util.List;

public class Utente {

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String ipAddress;
	private Ruolo ruolo;
	private Collocazione collocazione;

	private List<UtenteCollocazioneDto> utenteCollocazioneList;

	private List<String> funzionalitaAbilitate;
	private String profilo;

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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Collocazione getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(Collocazione collocazione) {
		this.collocazione = collocazione;
	}

	public List<String> getFunzionalitaAbilitate() {
		return funzionalitaAbilitate;
	}

	public void setFunzionalitaAbilitate(List<String> funzionalitaAbilitate) {
		this.funzionalitaAbilitate = funzionalitaAbilitate;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}

	public String getProfilo() {
		return profilo;
	}

	public List<UtenteCollocazioneDto> getUtenteCollocazioneList() {
		return utenteCollocazioneList;
	}

	public void setUtenteCollocazioneList(List<UtenteCollocazioneDto> utenteCollocazioneList) {
		this.utenteCollocazioneList = utenteCollocazioneList;
	}
}
