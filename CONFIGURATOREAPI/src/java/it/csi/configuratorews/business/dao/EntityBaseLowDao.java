/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.BaseDto;

import java.util.Collection;

public interface EntityBaseLowDao<T extends BaseDto, E> {
	
	public T insert(T obj);
	
	public T save(T obj);
	
	public void update(T obj);

	public void merge(T obj);
	
	public T findByPrimaryId(E id);
	
	public Collection<T> findAll(T obj);

	public Collection<T> findByFilter(T obj) throws Exception;

}
