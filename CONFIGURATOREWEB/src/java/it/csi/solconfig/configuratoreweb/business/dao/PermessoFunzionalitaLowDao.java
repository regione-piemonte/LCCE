/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoFunzionalitaDto;

public interface PermessoFunzionalitaLowDao extends EntityBaseLowDao<PermessoFunzionalitaDto, Long> {

	List<PermessoFunzionalitaDto> findPermessiByFunzId(Long funzId);
}
