/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoContrattoDto;
import it.csi.solconfig.configuratoreweb.presentation.model.TipoContrattoDTO;

import java.util.List;

public interface TipoContrattoLowDao extends CatalogoBaseLowDao<TipoContrattoDto, Long> {

    List<TipoContrattoDTO> findAll();
}
