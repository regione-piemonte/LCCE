/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.List;

import it.csi.configuratorews.business.dto.PreferenzaSalvataDto;

public interface PreferenzaSalvataLowDao extends EntityBaseLowDao<PreferenzaSalvataDto, Long>{

	List<PreferenzaSalvataDto> findByIdFruitore(Long idFruitore);

}
