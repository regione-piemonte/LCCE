/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.Collection;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.FonteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FonteDto;

@Component
public class FonteLowDaoImpl extends EntityBaseLowDaoImpl<FonteDto, Long> implements FonteLowDao {

	@Override
	public Collection<FonteDto> findByFonteId(Long id) {
		return entityManager.createQuery("FROM FonteDto WHERE fonteId = :id "
				+ "AND dataFineValidita IS NULL AND dataCancellazione IS NULL", FonteDto.class)
				.setParameter("id", id).getResultList();
	}

	@Override
	public Collection<FonteDto> findByFonteCodice(String codice) {
		return entityManager.createQuery("FROM FonteDto WHERE fonteCodice = :codice "
				+ "AND dataFineValidita IS NULL AND dataCancellazione IS NULL", FonteDto.class)
				.setParameter("codice", codice).getResultList();
	}


}


	

