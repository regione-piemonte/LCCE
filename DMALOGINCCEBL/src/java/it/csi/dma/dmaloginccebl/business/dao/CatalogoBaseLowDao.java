/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import java.util.Collection;

import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoBaseDto;

public interface CatalogoBaseLowDao<T extends CatalogoBaseDto, E>{

	public Collection<T> findByCodice(T obj);

	public T findByPrimaryId(E id);

	Collection<T> findByCodice(T obj, String codice);
	
}
