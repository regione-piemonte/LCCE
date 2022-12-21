/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.TipoContrattoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoContrattoDto;
import it.csi.solconfig.configuratoreweb.presentation.model.TipoContrattoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TipoContrattoLowDaoImpl extends CatalogoBaseLowDaoImpl<TipoContrattoDto, Long> implements TipoContrattoLowDao {


    @Override
    public List<TipoContrattoDTO> findAll() {
		return entityManager.createQuery(
				"SELECT new it.csi.solconfig.configuratoreweb.presentation.model.TipoContrattoDTO(" +
						"t.id, t.descrizione) FROM TipoContrattoDto t" ,TipoContrattoDTO.class)
				.getResultList();
    }
}
