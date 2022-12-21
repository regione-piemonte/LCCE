/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.TipoFunzionalitaLowDao;
import it.csi.configuratorews.business.dto.TipoFunzionalitaDto;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;


@Component
public class TipoFunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<TipoFunzionalitaDto, Long> implements TipoFunzionalitaLowDao {

	@Override
	public List<TipoFunzionalitaDto> findByCodice(String codice) {

		Query query = entityManager.createQuery("from TipoFunzionalitaDto t WHERE t.codiceTipoFunzione = :codice");
		query.setParameter("codice", codice);
		return query.getResultList();
		
	}
	
}
