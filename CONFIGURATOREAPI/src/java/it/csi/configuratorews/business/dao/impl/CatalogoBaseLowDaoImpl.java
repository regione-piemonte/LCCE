/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.CatalogoBaseLowDao;
import it.csi.configuratorews.business.dto.CatalogoBaseDto;
import it.csi.configuratorews.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Collection;

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
	
	@Override
	public Collection<T> findAll(T obj) {
		return entityManager.createQuery("from " + obj.getClass().getName()).getResultList();
	}
	
	@Override
	public T insert(T obj) {
		log.info("START - Inserimento base dao");
		obj.setDataInserimento(Utils.sysdate());
		entityManager.persist(obj);
		log.info("END - Inserimento base dao");
		entityManager.flush();
		return obj;
	}
	
	@Override
	public void update(T obj) {
		entityManager.persist(obj);
		entityManager.flush();
	}

}
