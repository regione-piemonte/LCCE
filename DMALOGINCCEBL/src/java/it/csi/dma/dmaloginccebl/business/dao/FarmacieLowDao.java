/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import java.util.Collection;
import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;

public interface FarmacieLowDao{
	
	public Collection<FarmacieDto> findByDatiFarmacia(FarmacieDto farmacieDto, List<String> codiciFarmacia) throws Exception;
}
