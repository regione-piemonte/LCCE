/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FonteDto;

import java.util.Collection;

public interface FonteLowDao extends EntityBaseLowDao<FonteDto, Long> {

    Collection<FonteDto> findByFonteId(Long id);
    
    Collection<FonteDto> findByFonteCodice(String codice);

}
