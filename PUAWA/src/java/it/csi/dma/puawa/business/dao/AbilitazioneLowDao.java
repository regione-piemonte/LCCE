/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao;

import java.util.Collection;

import it.csi.dma.puawa.business.dao.dto.AbilitazioneDto;
import it.csi.dma.puawa.business.dao.dto.ApplicazioneDto;
import it.csi.dma.puawa.business.dao.dto.RuoloUtenteDto;

public interface AbilitazioneLowDao extends it.csi.dma.puawa.business.dao.EntityBaseLowDao<AbilitazioneDto, Long> {

	Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(AbilitazioneDto abilitazioneDto);
	
	Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(RuoloUtenteDto ruoloUtenteDto, ApplicazioneDto applicazioneDto);
}
