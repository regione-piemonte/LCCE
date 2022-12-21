/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PreferenzaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;


public interface PreferenzaLowDao extends EntityBaseLowDao<PreferenzaDto, Long> {
	
	PreferenzaDto getPreferenzaRuolo(UtenteDto utente, RuoloDto ruolo);
	PreferenzaDto getPreferenzaRuoloCollocazione(UtenteDto utente, RuoloDto ruolo, CollocazioneDto collocazione);
	PreferenzaDto getPreferenzaApplicazione(UtenteDto utente, ApplicazioneDto applicazione);
	List<PreferenzaDto> getPreferenzaByUtente(UtenteDto utente);

}
