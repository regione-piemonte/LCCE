/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloUtenteDto;

public interface RuoloUtenteLowDao extends EntityBaseLowDao<RuoloUtenteDto, Long> {


    Collection<RuoloUtenteDto> findByUtenteRuoloAndData(RuoloUtenteDto ruoloUtenteDto);
    
    Collection<RuoloUtenteDto> findByIdRuolo(Long idRuolo);
}
