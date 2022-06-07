/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;

import java.util.Collection;
import java.util.List;

public interface AbilitazioneLowDao extends EntityBaseLowDao<AbilitazioneDto, Long> {

    Collection<AbilitazioneDto> findByUtenteRuoloAppAndData(AbilitazioneDto abilitazioneDto);

    Collection<AbilitazioneDto> findAbilitazioneGetAbilitazioni(AbilitazioneDto abilitazioneDto);

	Collection<AbilitazioneDto> findByUtenteRuolo(AbilitazioneDto abilitazioneDto);

	Collection<AbilitazioneDto> findAbilitazioneFarmacista(String codiceCollocazione, String codiceFarmacia, String codiceApplicazione);
}
