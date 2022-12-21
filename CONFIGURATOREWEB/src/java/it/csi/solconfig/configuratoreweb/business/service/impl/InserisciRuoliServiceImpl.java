/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import it.csi.solconfig.configuratoreweb.business.service.InserisciRuoliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Service
@Transactional
public class InserisciRuoliServiceImpl extends BaseServiceImpl implements InserisciRuoliService {
	
	@Autowired
	RuoloLowDao ruoloLowDao;
	
	@Autowired
	UtenteLowDao utenteLowDao;
	
	
	@Override
	public List<RuoloDto> findByCodice(RuoloDto ruolo){
		List<RuoloDto> ruoloDtoList = new ArrayList<RuoloDto>();
		
		
		ruoloLowDao.findByCodice(ruolo);
			
		return ruoloDtoList;	
	}
	
	@Override
	public boolean checkCodicePresente(RuoloDto ruoloDto) {
		Collection<RuoloDto> checkList = new ArrayList<RuoloDto>();
		checkList = ruoloLowDao.findByCodice(ruoloDto);
		if(checkList==null || checkList.isEmpty()) {
			return false;
		} 
			return true;
	}
	
	@Override
	public RuoloDto insertRuolo(RuoloDto ruolo, String cfUtente, Boolean flag, List<String> newRuoliComp ,List<RuoloSelCollTipo> newRuoliSel) {
		
		ruolo.setDataInizioValidita(Utils.sysdate());
		
		//verifico lo stato in base alla scelta dell operatore	
		if(flag==false || flag==null) {
			ruolo.setDataFineValidita(Utils.sysdate());	
		} else {
			ruolo.setDataFineValidita(null);
		}
		
		ruolo.setFlagConfiguratore("S");
		
		//operatore che effettua l'inserimento
		ruolo.setUtenteDto(Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cfUtente)));	
		
		ruolo = ruoloLowDao.insert(ruolo);	
		
		log.info("[InserisciRuoliServiceImpl::insertRuolo] inserimento eseguito; ruolo.getId()::" + ruolo.getId());
		insertRuoliCompatibiliSelezionabili(ruolo.getId(), newRuoliComp, newRuoliSel);

		
		return ruolo;	
	}
	
	private void insertRuoliCompatibiliSelezionabili(Long id, List<String> newRuoliComp ,List<RuoloSelCollTipo> newRuoliSel) {
		newRuoliComp.stream().distinct().collect(Collectors.toList())
				.forEach(e -> ruoloLowDao.insertRuoloCompatibile(id, e));

		newRuoliSel.stream().distinct().collect(Collectors.toList())
				.forEach(e -> ruoloLowDao.insertRuoliSelezionabile(id, e.getIdRuolo(), e.getIdCollTipo(), e.getId()));
	}
}


