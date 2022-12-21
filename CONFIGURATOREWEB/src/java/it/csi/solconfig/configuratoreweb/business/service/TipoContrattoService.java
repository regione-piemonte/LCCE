/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.presentation.model.TipoContrattoDTO;

import java.util.List;

public interface TipoContrattoService extends BaseService {

    List<TipoContrattoDTO> ricercaTipoContratto() throws Exception;
}
