/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;

public class SalvataggioUtenteModel {
	
	private UtenteDto utenteDto;
	
	private List<AbilitazioneDto> abilitazioniAura;
	private List<AbilitazioneDto> disabilitazioniAura;
	private List<AbilitazioneDto> modificheDataFineValAura;

	public SalvataggioUtenteModel() {
		abilitazioniAura = new ArrayList<AbilitazioneDto>();
		disabilitazioniAura = new ArrayList<AbilitazioneDto>();
		modificheDataFineValAura = new ArrayList<AbilitazioneDto>();
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

	public List<AbilitazioneDto> getAbilitazioniAura() {
		return abilitazioniAura;
	}

	public void setAbilitazioniAura(List<AbilitazioneDto> abilitazioniAura) {
		this.abilitazioniAura = abilitazioniAura;
	}

	public List<AbilitazioneDto> getDisabilitazioniAura() {
		return disabilitazioniAura;
	}

	public void setDisabilitazioniAura(List<AbilitazioneDto> disabilitazioniAura) {
		this.disabilitazioniAura = disabilitazioniAura;
	}

	public List<AbilitazioneDto> getModificheDataFineValAura() {
		return modificheDataFineValAura;
	}
	
	public void setModificheDataFineValAura(List<AbilitazioneDto> modificheDataFineValAura) {
		this.modificheDataFineValAura = modificheDataFineValAura;
	}
	
	public void addAbilitazioneAura(AbilitazioneDto abilitazioneDto) {
		this.abilitazioniAura.add(abilitazioneDto);
	}
	
	public void addDisabilitazioneAura(AbilitazioneDto abilitazioneDto) {
		this.disabilitazioniAura.add(abilitazioneDto);
	}

	public void addModificheDataFineValeAura(AbilitazioneDto abilitazioneDto) {
		this.modificheDataFineValAura.add(abilitazioneDto);
	}
	
	public boolean checkMailAura() {
		return 	!this.abilitazioniAura.isEmpty() || 
				!this.disabilitazioniAura.isEmpty() || 
				!this.modificheDataFineValAura.isEmpty();
	}
}
