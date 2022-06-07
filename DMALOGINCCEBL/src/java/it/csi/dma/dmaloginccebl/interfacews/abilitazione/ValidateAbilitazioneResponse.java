/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.abilitazione;

import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

import java.util.Collection;
import java.util.List;

public class ValidateAbilitazioneResponse {
	private List<Errore> listaErrori;
	private ApplicazioneDto applicazioneDto;
	private UtenteDto utenteDto;
	private RuoloDto ruoloDto;
	private CollocazioneDto collocazioneDto;
	private RuoloUtenteDto ruoloUtenteDto;
	private UtenteCollocazioneDto utenteCollocazioneDto;
	private Collection<AbilitazioneDto> abilitazioneDtoList;
	
	public List<Errore> getListaErrori() {
		return listaErrori;
	}
	public void setListaErrori(List<Errore> listaErrori) {
		this.listaErrori = listaErrori;
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

	public CollocazioneDto getCollocazioneDto() {
		return collocazioneDto;
	}

	public void setCollocazioneDto(CollocazioneDto collocazioneDto) {
		this.collocazioneDto = collocazioneDto;
	}

	public RuoloUtenteDto getRuoloUtenteDto() {
		return ruoloUtenteDto;
	}

	public void setRuoloUtenteDto(RuoloUtenteDto ruoloUtenteDto) {
		this.ruoloUtenteDto = ruoloUtenteDto;
	}

	public UtenteCollocazioneDto getUtenteCollocazioneDto() {
		return utenteCollocazioneDto;
	}

	public void setUtenteCollocazioneDto(UtenteCollocazioneDto utenteCollocazioneDto) {
		this.utenteCollocazioneDto = utenteCollocazioneDto;
	}

	public Collection<AbilitazioneDto> getAbilitazioneDtoList() {
		return abilitazioneDtoList;
	}

	public void setAbilitazioneDtoList(Collection<AbilitazioneDto> abilitazioneDtoList) {
		this.abilitazioneDtoList = abilitazioneDtoList;
	}
}
