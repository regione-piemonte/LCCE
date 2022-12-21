/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import it.csi.solconfig.configuratoreweb.business.service.TipoContrattoService;
import it.csi.solconfig.configuratoreweb.business.dao.TipoContrattoLowDao;
import it.csi.solconfig.configuratoreweb.presentation.model.TipoContrattoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoContrattoServiceImpl extends BaseServiceImpl implements TipoContrattoService {

    @Autowired
    private TipoContrattoLowDao tipoContrattoLowDao;

    @Override
    public List<TipoContrattoDTO> ricercaTipoContratto() {
        return tipoContrattoLowDao.findAll();
    }
}
