/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.CatalogoParametriLowDao;
import it.csi.dma.puawa.business.dao.dto.CatalogoParametriDto;
import it.csi.dma.puawa.interfacews.msg.ParametriLogin;

@Component
public class CatalogoParametriLowDaoImpl extends CatalogoBaseLowDaoImpl<CatalogoParametriDto, Long>
		implements CatalogoParametriLowDao {

	@Override
	public Collection<ParametriLogin> findByCodiceAndApplicazione(CatalogoParametriDto catalogoParametriDto) {

		StringBuilder queryBuilder = new StringBuilder(
				"from " + catalogoParametriDto.getClass().getName() + " t WHERE ");
		queryBuilder.append(" t.codice = :codice AND t.applicazioneDto.id = :idApplicazione");

		Query query = getEntityManager().createQuery(queryBuilder.toString());
		query.setParameter("codice", catalogoParametriDto.getCodice());
		query.setParameter("idApplicazione", catalogoParametriDto.getApplicazioneDto().getId());

		return query.getResultList();
	}
}
