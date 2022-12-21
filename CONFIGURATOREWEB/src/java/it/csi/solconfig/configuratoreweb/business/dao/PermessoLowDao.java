/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoDto;

public interface PermessoLowDao extends EntityBaseLowDao<PermessoDto, Long> {

	boolean existsByCodice(String codicePermesso);

	PermessoDto findByCodice(String codicePermesso);
	
	List<PermessoDto> findValid();

}
