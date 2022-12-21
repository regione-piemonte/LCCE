/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dto.TipologiaDatoDto;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.business.dao.TipologiaDatoLowDao;

@Component
public class TipologiaDatoLowDaoImpl extends EntityBaseLowDaoImpl<TipologiaDatoDto, Long>
		implements TipologiaDatoLowDao {

	@Override
	public boolean existsByCodice(String codiceTipologiaDato) {

		Timestamp data = Utils.sysdate();

		StringBuilder queryBuilder = new StringBuilder(
				"from TipologiaDatoDto t WHERE t.codice = :codiceTipologiaDato ");
		queryBuilder.append(
				"and t.dataInizioValidita <= :data and (t.dataFineValidita is null or t.dataFineValidita >= :data) ");

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("codiceTipologiaDato", codiceTipologiaDato);
		query.setParameter("data", data);

		if (query.getResultList() != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<TipologiaDatoDto> findAllValide() {
		Timestamp data = Utils.sysdate();

		StringBuilder queryBuilder = new StringBuilder("from TipologiaDatoDto t");
		queryBuilder.append(
				" WHERE t.dataInizioValidita <= :data and (t.dataFineValidita is null or t.dataFineValidita >= :data) ");

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("data", data);

		return query.getResultList();
	}

	@Override
	public TipologiaDatoDto findByCodice(String codiceTipologiaDato) {
		Timestamp data = Utils.sysdate();

		StringBuilder queryBuilder = new StringBuilder(
				"from TipologiaDatoDto t WHERE t.codice = :codiceTipologiaDato ");
		queryBuilder.append(
				"and t.dataInizioValidita <= :data and (t.dataFineValidita is null or t.dataFineValidita >= :data) ");

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("codiceTipologiaDato", codiceTipologiaDato);
		query.setParameter("data", data);

		return (TipologiaDatoDto) query.getSingleResult();
		
	}

}
