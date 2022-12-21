/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoBaseDto;

public interface CatalogoBaseLowDao<T extends CatalogoBaseDto, E>{

	public Collection<T> findByCodice(T obj);

	public T findByPrimaryId(E id);

	Collection<T> findByCodice(T obj, String codice);

	Collection<T> findByFilter(T obj) throws Exception;
	
	public Collection<T> findAll(T obj);
	
	public T insert(T obj);
	
	public void update(T obj); 
	
	
}
