/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoParametriDto;
import it.csi.solconfig.configuratoreweb.interfacews.msg.ParametriLogin;

public interface CatalogoParametriLowDao extends CatalogoBaseLowDao<CatalogoParametriDto, Long>{


    Collection<ParametriLogin> findByCodiceAndApplicazione(CatalogoParametriDto catalogoParametriDto);
}
