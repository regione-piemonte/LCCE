/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.Collection;
import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RichiestaCredenzialiDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;

public class Data {

	private Utente utente;
	private String codiceRuoloSelezionato;
	private String colCodiceCollocazioneSelezionata;
	private Collection<ApplicazioneDto> applicazioneDtoList; 
	private List<FunzionalitaDto> funzionalitaDtoList;
	private Collection<RuoloDto> ruoloDtoList;
	private Collection<Nodo> alberoList;
	private List<MessaggiUtenteDto> messaggiUtenteDto;
	
	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getCodiceRuoloSelezionato() {
		return codiceRuoloSelezionato;
	}

	public void setCodiceRuoloSelezionato(String codiceRuoloSelezionato) {
		this.codiceRuoloSelezionato = codiceRuoloSelezionato;
	}

	public String getColCodiceCollocazioneSelezionata() {
		return colCodiceCollocazioneSelezionata;
	}

	public void setColCodiceCollocazioneSelezionata(String colCodiceCollocazioneSelezionata) {
		this.colCodiceCollocazioneSelezionata = colCodiceCollocazioneSelezionata;
	}

	public Collection<ApplicazioneDto> getApplicazioneDtoList() {
		return applicazioneDtoList;
	}

	public void setApplicazioneDtoList(Collection<ApplicazioneDto> applicazioneDtoList) {
		this.applicazioneDtoList = applicazioneDtoList;
	}

	public List<FunzionalitaDto> getFunzionalitaDtoList() {
		return funzionalitaDtoList;
	}

	public void setFunzionalitaDtoList(List<FunzionalitaDto> funzionalitaDtoList) {
		this.funzionalitaDtoList = funzionalitaDtoList;
	}

	public Collection<RuoloDto> getRuoloDtoList() {
		return ruoloDtoList;
	}

	public void setRuoloDtoList(Collection<RuoloDto> ruoloDtoList) {
		this.ruoloDtoList = ruoloDtoList;
	}

	public Collection<Nodo> getAlberoList() {
		return alberoList;
	}

	public void setAlberoList(Collection<Nodo> alberoList) {
		this.alberoList = alberoList;
	}

	public List<MessaggiUtenteDto> getMessaggiUtenteDto() {
		return messaggiUtenteDto;
	}

	public void setMessaggiUtenteDto(List<MessaggiUtenteDto> messaggiUtenteDto) {
		this.messaggiUtenteDto = messaggiUtenteDto;
	}
	
}
