/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import java.util.List;

import it.csi.configuratorews.business.dto.TipoFunzionalitaDto;

public interface TipoFunzionalitaLowDao extends EntityBaseLowDao<TipoFunzionalitaDto, Long> {
	
	List<TipoFunzionalitaDto> findByCodice(String codice);
	
}
