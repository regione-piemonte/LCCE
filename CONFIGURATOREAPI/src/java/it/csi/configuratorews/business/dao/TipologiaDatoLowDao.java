/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.List;

import it.csi.configuratorews.business.dto.TipologiaDatoDto;

public interface TipologiaDatoLowDao  extends EntityBaseLowDao<TipologiaDatoDto, Long> {

	boolean existsByCodice(String codiceTipologiaDato);

	List<TipologiaDatoDto> findAllValide();
	
	TipologiaDatoDto findByCodice(String codiceTipologiaDato);
	
}
