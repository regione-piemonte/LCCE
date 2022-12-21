/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.EntityBaseLowDao;
import it.csi.configuratorews.business.dto.BaseDto;
import it.csi.configuratorews.business.dto.CatalogoBaseDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class EntityBaseLowDaoImpl<T extends BaseDto, E> extends BaseLowDaoImpl<T> implements EntityBaseLowDao<T, E> {

	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor=Exception.class)
	public T insert(T obj) {
		log.info("START - Inserimento base dao");
		obj.setDataInserimento(Utils.sysdate());
		entityManager.persist(obj);
		log.info("END - Inserimento base dao");
		entityManager.flush();
		return obj;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor=Exception.class)
	public void update(T obj) {
		entityManager.persist(obj);
		entityManager.flush();
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
	public Collection<T> findByFilter(T obj) throws Exception {
		Class<?> thisClass = null;
		try {
			Map<String, Object> parameters = createParametersMap(obj);

			StringBuilder queryBuilder = new StringBuilder("from " + obj.getClass().getName() + " t WHERE 1 = 1 ");
			for (String fName : parameters.keySet()) {

				String parametro = fName;
				if (parametro.contains(".")) {
					parametro = parametro.replaceAll("\\.", "");
				}
				queryBuilder.append(" AND t." + fName + " = :" + parametro);
			}
			Query query = entityManager.createQuery(queryBuilder.toString());
			for (String fName : parameters.keySet()) {

				String parametro = fName;
				if (parametro.contains(".")) {
					parametro = parametro.replaceAll("\\.", "");
				}
				query.setParameter(parametro, parameters.get(fName));
			}

			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		}

	}

	private Map<String, Object> createParametersMap(Object obj)
			throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		Map<String, Object> parameters = new HashMap<>();
		Class<?> thisClass;
		thisClass = Class.forName(obj.getClass().getName());

		Field[] aClassFields = thisClass.getDeclaredFields();
		for (Field f : aClassFields) {
			String fName = f.getName();
			Object fieldValue = new PropertyDescriptor(fName, thisClass).getReadMethod().invoke(obj);
			if (fieldValue != null) {
				if (fieldValue instanceof BaseDto || fieldValue instanceof CatalogoBaseDto) {
					Map<String, Object> recursiveParameters = createParametersMap(fieldValue);
					for (String keyName : recursiveParameters.keySet()) {
						parameters.put(fName + "." + keyName, recursiveParameters.get(keyName));
					}
				} else {
					parameters.put(fName, fieldValue);
				}
			}
		}
		return parameters;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor=Exception.class)
	public T save(T obj) {
		obj.setDataInserimento(Utils.sysdate());
		entityManager.persist(obj);
		return obj;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor=Exception.class)
	public void merge(T obj) {
		entityManager.merge(obj);
	}
}
