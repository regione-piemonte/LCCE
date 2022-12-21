/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;

public interface RuoloProfiloLowDao extends EntityBaseLowDao<RuoloProfilo, Long> {

    Collection<RuoloProfilo> findByIdRuolo(Long idRuolo);
    Collection<RuoloProfilo> findByIdProfilo(Long idRuolo);
    Collection<RuoloProfilo> findByIdFunz(Long idFunz);
	Collection<RuoloProfilo> findByIdFunzIdRuolo(Long idRuolo, Long idFunz);
}
