/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.List;

import it.csi.configuratorews.business.dto.SistemiRichiedentiDto;

public interface SistemiRichiedentiLowDao extends CatalogoBaseLowDao<SistemiRichiedentiDto, Long>{

	List<SistemiRichiedentiDto> findByValidCodice(String xCodiceServizio, String codiceAzienda);

}
