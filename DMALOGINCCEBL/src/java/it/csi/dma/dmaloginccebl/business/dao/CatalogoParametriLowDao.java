/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoParametriDto;
import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;

import java.util.Collection;

public interface CatalogoParametriLowDao extends CatalogoBaseLowDao<CatalogoParametriDto, Long>{


    Collection<ParametriLogin> findByCodiceAndApplicazione(CatalogoParametriDto catalogoParametriDto);
}
