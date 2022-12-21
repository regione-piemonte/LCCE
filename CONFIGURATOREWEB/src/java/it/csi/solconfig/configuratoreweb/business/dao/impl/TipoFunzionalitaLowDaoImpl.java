/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

import java.sql.Timestamp;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.TipoFunzionalitaLowDao;

@Component
public class TipoFunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<TipoFunzionalitaDto, Long>
		implements TipoFunzionalitaLowDao {

	@Override
	public TipoFunzionalitaDto findByCodiceTipoFunzione(String codTipoFunz) {

		StringBuilder queryBuilder = new StringBuilder(
				"from " + TipoFunzionalitaDto.class.getName() + " WHERE 1=1 ");
		queryBuilder.append("and codiceTipoFunzione = :fnz_tipo_codice ");
		queryBuilder.append("and (( :data between dataInizioValidita and dataFineValidita) ");
		queryBuilder.append("or (dataFineValidita is null ");
		queryBuilder.append("and :data >= dataInizioValidita)) ");

		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("fnz_tipo_codice", codTipoFunz);
		query.setParameter("data", Utils.sysdate());

		return (TipoFunzionalitaDto) Utils.getFirstRecord(query.getResultList());

	}

}
