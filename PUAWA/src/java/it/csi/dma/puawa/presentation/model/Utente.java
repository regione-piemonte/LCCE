/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.model;

import it.csi.dma.puawa.business.dao.dto.AbilitazioneDto;
import it.csi.dma.puawa.integration.abilitazioni.client.Abilitazione;
import it.csi.dma.puawa.integration.collocazioni.client.ViewCollocazione;
import it.csi.dma.puawa.integration.ruoliUtente.client.Ruolo;

import java.util.Collection;
import java.util.List;

public class Utente {

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String ipAddress;
	private Ruolo ruolo;
	private ViewCollocazione viewCollocazione;
	private Collection<Ruolo> listaRuoli;
	private List<ViewCollocazione> viewListaCollocazioni;
	private Collection<Abilitazione> listaAbilitazioni;
	private AbilitazioneDto abilitazioneUtilizzata;
	private List<ViewApplicazione> listaApplicazioni;

	public AbilitazioneDto getAbilitazioneUtilizzata() {
		return abilitazioneUtilizzata;
	}

	public void setAbilitazioneUtilizzata(AbilitazioneDto abilitazioneUtilizzata) {
		this.abilitazioneUtilizzata = abilitazioneUtilizzata;
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

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Collection<Ruolo> getListaRuoli() {
		return listaRuoli;
	}

	public void setListaRuoli(Collection<Ruolo> listaRuoli) {
		this.listaRuoli = listaRuoli;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public ViewCollocazione getViewCollocazione() {
		return viewCollocazione;
	}

	public void setViewCollocazione(ViewCollocazione viewCollocazione) {
		this.viewCollocazione = viewCollocazione;
	}

	public Collection<Abilitazione> getListaAbilitazioni() {
		return listaAbilitazioni;
	}

	public void setListaAbilitazioni(Collection<Abilitazione> listaAbilitazioni) {
		this.listaAbilitazioni = listaAbilitazioni;
	}

	public List<ViewCollocazione> getViewListaCollocazioni() {
		return viewListaCollocazioni;
	}

	public void setViewListaCollocazioni(List<ViewCollocazione> viewListaCollocazioni) {
		this.viewListaCollocazioni = viewListaCollocazioni;
	}

	public List<ViewApplicazione> getListaApplicazioni() {
		return listaApplicazioni;
	}

	public void setListaApplicazioni(List<ViewApplicazione> listaApplicazioni) {
		this.listaApplicazioni = listaApplicazioni;
	}
}
