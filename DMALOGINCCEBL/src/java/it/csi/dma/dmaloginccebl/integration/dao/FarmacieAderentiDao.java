/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao;

import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.GetFarmacieAderentiRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

public interface FarmacieAderentiDao {
	
	public List<FarmacieDto> findFarmacieAderenti(GetFarmacieAderentiRequest parameters, List<Errore> errori) throws Exception;

}
