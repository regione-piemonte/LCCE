/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.CredenzialiServiziDto;

public interface CredenzialiServiziLowDao extends EntityBaseLowDao<CredenzialiServiziDto, Long> {

    Collection<CredenzialiServiziDto> findByFilterAndServizioAndData(CredenzialiServiziDto credenzialiServiziDto);
}
