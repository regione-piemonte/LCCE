/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.collocazioni;

import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloUtenteDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

public class ValidateCollocazioniResponse {
	private List<Errore> listaErrori;
	private ApplicazioneDto applicazioneDto;
	private UtenteDto utenteDto;
	private RuoloDto ruoloDto;
	private RuoloUtenteDto ruoloUtenteDto;

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

	public RuoloUtenteDto getRuoloUtenteDto() {
		return ruoloUtenteDto;
	}

	public void setRuoloUtenteDto(RuoloUtenteDto ruoloUtenteDto) {
		this.ruoloUtenteDto = ruoloUtenteDto;
	}
}
