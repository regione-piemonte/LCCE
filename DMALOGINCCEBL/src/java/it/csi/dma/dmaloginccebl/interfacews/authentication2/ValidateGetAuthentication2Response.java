/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authentication2;

import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

import java.util.ArrayList;
import java.util.List;

public class ValidateGetAuthentication2Response {

	private List<Errore> listaErrori = new ArrayList<Errore>();
	private ApplicazioneDto applicazioneRichiestaDto;
	private ApplicazioneDto applicazioneChiamanteDto;
	private UtenteDto utenteDto;
	private RuoloDto ruoloDto;
	private AbilitazioneDto abilitazioneDto;

	public List<Errore> getListaErrori() {
		return listaErrori;
	}

	public void setListaErrori(List<Errore> listaErrori) {
		this.listaErrori = listaErrori;
	}

	public ApplicazioneDto getApplicazioneRichiestaDto() {
		return applicazioneRichiestaDto;
	}

	public void setApplicazioneRichiestaDto(ApplicazioneDto applicazioneRichiestaDto) {
		this.applicazioneRichiestaDto = applicazioneRichiestaDto;
	}

	public ApplicazioneDto getApplicazioneChiamanteDto() {
		return applicazioneChiamanteDto;
	}

	public void setApplicazioneChiamanteDto(ApplicazioneDto applicazioneChiamanteDto) {
		this.applicazioneChiamanteDto = applicazioneChiamanteDto;
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

	public AbilitazioneDto getAbilitazioneDto() {
		return abilitazioneDto;
	}

	public void setAbilitazioneDto(AbilitazioneDto abilitazioneDto) {
		this.abilitazioneDto = abilitazioneDto;
	}
}
