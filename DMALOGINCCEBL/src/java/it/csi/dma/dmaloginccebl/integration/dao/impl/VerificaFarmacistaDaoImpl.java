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

import it.csi.dma.dmaloginccebl.business.dao.AbilitazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.FarmacieLowDao;
import it.csi.dma.dmaloginccebl.integration.dao.VerificaFarmacistaDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.VerificaFarmacistaRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class VerificaFarmacistaDaoImpl implements VerificaFarmacistaDao {

	private static final String FARMAB = "FARMAB";

	@Autowired
	private FarmacieLowDao farmacieLowDao;

	@Autowired
	private AbilitazioneLowDao abilitazioneLowDao;
	
	@Autowired
	private LogGeneralDao logGeneralDao;
	
	@Override
	public void ricercaFarmaciaAderente(VerificaFarmacistaRequest parameters, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) throws Exception {
		FarmacieDto farmacieDto = new FarmacieDto();
		List<String> codiciFarmacia = new ArrayList<String>();
		codiciFarmacia.add(parameters.getFarmacia().getCodice());
		farmacieDto = Utils.getFirstRecord((List<FarmacieDto>) farmacieLowDao.findByDatiFarmacia(farmacieDto, codiciFarmacia));	
		if(farmacieDto == null) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.FARMACISTA_FARMACIA_NON_ABILITITATI.getValue()));
		}
	}

	@Override
	public void verificaFunzionalitaFarmaciaAbituale(VerificaFarmacistaRequest parameters, List<Errore> errori,
			LogGeneralDaoBean logGeneralDaoBean) throws Exception {
		
		List<AbilitazioneDto> abilitazioneDtoList = (ArrayList<AbilitazioneDto>)abilitazioneLowDao.findAbilitazioneFarmacista(
				parameters.getFarmacia().getPartitaIVA()+"@"+parameters.getFarmacia().getCodice(), parameters.getCodiceFiscaleFarmacista(), FARMAB);
		
		if(abilitazioneDtoList == null || abilitazioneDtoList.isEmpty()) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.FARMACISTA_FARMACIA_NON_ABILITITATI.getValue()));
		}
	}
}
