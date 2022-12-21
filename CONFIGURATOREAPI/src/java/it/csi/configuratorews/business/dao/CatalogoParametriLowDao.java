/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.CatalogoParametriDto;
import it.csi.configuratorews.interfacews.client.base.ParametriLogin;

import java.util.Collection;

public interface CatalogoParametriLowDao extends CatalogoBaseLowDao<CatalogoParametriDto, Long>{


    Collection<ParametriLogin> findByCodiceAndApplicazione(CatalogoParametriDto catalogoParametriDto);
}
