/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.List;

import it.csi.configuratorews.business.dto.PreferenzaFruitoreDto;

public interface PreferenzaFruitoreLowDao extends EntityBaseLowDao<PreferenzaFruitoreDto, Long>{

	List<PreferenzaFruitoreDto> findByCode(String codiceFornitore, String codiceApplicazione, String codiceRuolo, String codiceCollocazione);

	List<PreferenzaFruitoreDto> findByName(String nomeFruitore, String applicazione, String codiceRuolo, String codiceCollocazione);
	
	List<PreferenzaFruitoreDto> findAllActive();
}
