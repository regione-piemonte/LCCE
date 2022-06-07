/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao;

import it.csi.dma.puawa.business.dao.dto.ReportTipoDto;

import java.util.Collection;

public interface ReportTipoLowDao extends EntityBaseLowDao<ReportTipoDto, Long> {

    Collection<ReportTipoDto> findAllByDataValidazione();
}
