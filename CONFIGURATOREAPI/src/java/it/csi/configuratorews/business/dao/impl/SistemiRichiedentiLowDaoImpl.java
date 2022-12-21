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

import it.csi.configuratorews.business.dao.SistemiRichiedentiLowDao;
import it.csi.configuratorews.business.dto.SistemiRichiedentiDto;
import it.csi.configuratorews.util.Utils;

@Component
public class SistemiRichiedentiLowDaoImpl  extends CatalogoBaseLowDaoImpl<SistemiRichiedentiDto, Long> implements SistemiRichiedentiLowDao {
	public List<SistemiRichiedentiDto> findByValidCodice(String xCodiceServizio, String codiceAzienda) {
		Timestamp data = Utils.sysdate();
		
		StringBuilder queryBuilder = new StringBuilder("from SistemiRichiedentiDto func WHERE func.codice = :codice ");
		queryBuilder.append("and func.codiceAzienda = :codiceAzienda ");
		queryBuilder.append("and (( :data between func.dataInizioValidita and func.dataFineValidita) ");
		queryBuilder.append("or (func.dataFineValidita is null ");
		queryBuilder.append("and :data >= func.dataInizioValidita)) ");
		
		Query query = entityManager.createQuery(queryBuilder.toString());
		
		query.setParameter("codice", xCodiceServizio);		
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("data", data);
		
		return query.getResultList();
	}
}
