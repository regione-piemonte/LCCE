/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.dto.BaseDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoBaseDto;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Component
public class BaseLowDaoImpl<T> {
	
	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	protected Class<T> entityClass;

	@PersistenceContext
	protected EntityManager entityManager;


	public Collection<T> findByFilter(T obj) throws Exception {
		Class<?> thisClass = null;
		try {
			Map<String, Object> parameters = createParametersMap(obj);

			StringBuilder queryBuilder = new StringBuilder("from " + obj.getClass().getName() +" t WHERE 1 = 1 ");
			for(String fName : parameters.keySet()) {
				queryBuilder.append(" AND t."+fName +" = :"+fName);
			}
			Query query = entityManager.createQuery(queryBuilder.toString());
			for(String fName : parameters.keySet()) {
				query.setParameter(fName, parameters.get(fName));
			}
			
			return query.getResultList();
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		}
		
	}

	private Map<String, Object> createParametersMap(Object obj) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		Map<String, Object> parameters = new HashMap<>();
		Class<?> thisClass;
		thisClass = Class.forName(obj.getClass().getName());

		Field[] aClassFields = thisClass.getDeclaredFields();
		for (Field f : aClassFields) {
			String fName = f.getName();
			Object fieldValue = new PropertyDescriptor(fName, thisClass).getReadMethod().invoke(obj);
			if (fieldValue != null) {
				if(fieldValue instanceof BaseDto ||
						fieldValue instanceof CatalogoBaseDto){
					Map<String, Object> recursiveParameters = createParametersMap(fieldValue);
					for(String keyName : recursiveParameters.keySet()){
						parameters.put(fName + "." + keyName, recursiveParameters.get(keyName));
					}
				}
				parameters.put(fName, fieldValue);
			}
		}
		return parameters;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setEntityClass() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	public static <T> T map(Class<T> type, Object[] tuple){
		List<Class<?>> tupleTypes = new ArrayList<>();
		List<Object> tupleResults = new ArrayList<>();
		for(Object field : tuple){
			if(field != null){
				tupleTypes.add(field.getClass());
				tupleResults.add(field);
			}
		}
		try {
			Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tupleResults.size()]));
			return ctor.newInstance(tupleResults.toArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> map(Class<T> type, List<Object[]> records){
		List<T> result = new LinkedList<>();
		for(Object[] record : records){
			result.add(map(type, record));
		}
		return result;
	}

	public static <T> List<T> getResultList(Query query, Class<T> type){
		List<Object[]> records = query.getResultList();
		return map(type, records);
	}
}
