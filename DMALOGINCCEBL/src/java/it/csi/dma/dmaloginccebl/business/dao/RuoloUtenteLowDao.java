/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloUtenteDto;

import java.util.Collection;

public interface RuoloUtenteLowDao extends EntityBaseLowDao<RuoloUtenteDto, Long> {

    Collection<RuoloUtenteDto> findByUtenteRuoloAndData(RuoloUtenteDto ruoloUtenteDto);
    
    Collection<RuoloUtenteDto> findByIdUtente(Long id);
}
