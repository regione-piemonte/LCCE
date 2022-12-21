/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.PreferenzaSalvataLowDao;
import it.csi.configuratorews.business.dto.PreferenzaSalvataDto;

@Component
public class PreferenzaSalvataLowDaoImpl extends EntityBaseLowDaoImpl<PreferenzaSalvataDto, Long> implements PreferenzaSalvataLowDao {

	@Override
	public List<PreferenzaSalvataDto> findByIdFruitore(Long idFruitore) {
		StringBuffer hql = new StringBuffer("select preferenzaSalvata from PreferenzaSalvataDto as preferenzaSalvata ");
		hql.append(" inner join preferenzaSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenzaSalvata.dataCancellazione is  null");
		hql.append(" and preferenzaFruitore.id = "+idFruitore);
		Query q = entityManager.createQuery(hql.toString());
		return q.getResultList();
	}

}
