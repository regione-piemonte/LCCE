/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.integration.dao.FarmacieAderentiDao;
import it.csi.dma.dmaloginccebl.business.dao.FarmacieLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.DatiFarmacia;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.GetFarmacieAderentiRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

@Component
public class FarmacieAderentiDaoImpl implements FarmacieAderentiDao {
	
	@Autowired
	private FarmacieLowDao farmacieLowDao;

	@Override
	public List<FarmacieDto> findFarmacieAderenti(GetFarmacieAderentiRequest parameters, List<Errore> errori) throws Exception {
		
		FarmacieDto farmacieDto = new FarmacieDto();
		List<FarmacieDto> farmacieDtoList = new ArrayList<FarmacieDto>();
		List<String> codiceFarmaciaList = null;
		if(parameters.getDatiFarmacia() != null) {
			DatiFarmacia datiFarmacia =	parameters.getDatiFarmacia();
			farmacieDto.setComune(datiFarmacia.getComune());
			farmacieDto.setIndirizzo(datiFarmacia.getIndirizzo());
			farmacieDto.setDenominazioneFarmacia(datiFarmacia.getNome());
			codiceFarmaciaList = datiFarmacia.getCodiceFarmacia();
		}
		farmacieDtoList = (List<FarmacieDto>) farmacieLowDao.findByDatiFarmacia(farmacieDto, codiceFarmaciaList);
				
		return farmacieDtoList;
	}

}
