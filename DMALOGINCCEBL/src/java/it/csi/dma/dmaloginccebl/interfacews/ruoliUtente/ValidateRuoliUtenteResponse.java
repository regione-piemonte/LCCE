/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.ruoliUtente;

import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

public class ValidateRuoliUtenteResponse {
	private List<Errore> listaErrori;
	private ApplicazioneDto applicazioneDto;
	private UtenteDto utenteDto;
	
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
}
