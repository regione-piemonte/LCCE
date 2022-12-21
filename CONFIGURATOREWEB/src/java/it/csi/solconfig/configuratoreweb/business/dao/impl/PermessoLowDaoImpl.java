/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.PermessoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class PermessoLowDaoImpl extends EntityBaseLowDaoImpl<PermessoDto, Long> implements PermessoLowDao {

	@Override
	public boolean existsByCodice(String codicePermesso) {

		StringBuilder queryBuilder = new StringBuilder("select t from PermessoDto t ");
		queryBuilder.append("WHERE t.codice = :codicePermesso ");
		queryBuilder.append("and t.dataInizioValidita <= now() ");
		queryBuilder.append("and (t.dataFineValidita is null or t.dataFineValidita >= now() ) ");

		Query query = entityManager.createQuery(queryBuilder.toString());
		query.setParameter("codicePermesso", codicePermesso);

		List<PermessoDto> permessi = query.getResultList();

		if (permessi != null && permessi.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PermessoDto findByCodice(String codicePermesso) {
		Timestamp data = Utils.sysdate();

		StringBuilder queryBuilder = new StringBuilder("from PermessoDto t WHERE t.codice = :codicePermesso ");
		queryBuilder.append(
				"and t.dataInizioValidita <= :data and (t.dataFineValidita is null or t.dataFineValidita >= :data) ");

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("codicePermesso", codicePermesso);
		query.setParameter("data", data);

		return (PermessoDto) query.getSingleResult();
	}

	@Override
	public List<PermessoDto> findValid() {
		Timestamp data = Utils.sysdate();

		StringBuilder queryBuilder = new StringBuilder("from PermessoDto t WHERE 1=1 ");
		queryBuilder.append(
				"and t.dataInizioValidita <= :data and (t.dataFineValidita is null or t.dataFineValidita >= :data) ");

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("data", data);

		return (List<PermessoDto>) query.getResultList();
	}

}
