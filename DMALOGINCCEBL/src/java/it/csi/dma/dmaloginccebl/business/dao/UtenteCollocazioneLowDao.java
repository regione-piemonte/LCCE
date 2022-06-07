/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import java.util.Collection;
import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteCollocazioneDto;

public interface UtenteCollocazioneLowDao extends EntityBaseLowDao<UtenteCollocazioneDto, Long>{

	public Collection<UtenteCollocazioneDto> findByUtenteAndValidita(UtenteCollocazioneDto obj);

    Collection<UtenteCollocazioneDto> findByUtenteAndCollocazioneAndValidita(UtenteCollocazioneDto obj);

    public Collection<UtenteCollocazioneDto> findByListId(List<Long> idList);
}
