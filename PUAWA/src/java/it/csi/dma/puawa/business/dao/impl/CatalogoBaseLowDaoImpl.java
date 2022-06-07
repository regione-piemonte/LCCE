/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.CatalogoBaseLowDao;
import it.csi.dma.puawa.business.dao.dto.CatalogoBaseDto;

@Component
public class CatalogoBaseLowDaoImpl<T extends CatalogoBaseDto, E> extends BaseLowDaoImpl<T>
		implements CatalogoBaseLowDao<T, E> {

	@Override
	public Collection<T> findByCodice(T obj) {
		Query query = entityManager.createQuery("from " + obj.getClass().getName() + " t WHERE t.codice = :codice");
		query.setParameter("codice", obj.getCodice());
		return query.getResultList();
	}

	@Override
	public Collection<T> findByCodice(T obj, String codice) {
		obj.setCodice(codice);
		Query query = entityManager.createQuery("from " + obj.getClass().getName() + " t WHERE t.codice = :codice");
		query.setParameter("codice", obj.getCodice());
		return query.getResultList();
	}

	@Override
	public T findByPrimaryId(E id) {
		setEntityClass();
		return entityManager.find(entityClass, id);
	}
}
