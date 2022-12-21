/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.CredenzialiServiziDto;

import java.util.Collection;

public interface CredenzialiServiziLowDao extends EntityBaseLowDao<CredenzialiServiziDto, Long> {

    Collection<CredenzialiServiziDto> findByFilterAndServizioAndData(CredenzialiServiziDto credenzialiServiziDto);
}
