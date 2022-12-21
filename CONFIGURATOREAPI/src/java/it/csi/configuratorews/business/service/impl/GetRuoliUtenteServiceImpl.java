/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.RuoloUtenteLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.business.dto.RuoloUtenteDto;
import it.csi.configuratorews.business.service.GetRuoliUtenteService;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetRuoliUtenteServiceImpl implements GetRuoliUtenteService{

	@Autowired
	RuoloUtenteLowDao ruoloUtenteLowDao;
	
	private LogUtil log = new LogUtil(this.getClass());
	@Override
	public Pagination<Ruolo> getRuoliUtente(String codiceFiscale, Integer offset, Integer limit) {
		Pagination<Ruolo> result = new Pagination<Ruolo>();
		List<RuoloUtenteDto> ruoliUtenteList = ruoloUtenteLowDao.findByCodiceFiscale(codiceFiscale,offset,limit);
		log.info("getRuoliUtente", "estrazione ruoli per cf "+codiceFiscale );
		
		Long countTotal = ruoloUtenteLowDao.countByUtente(codiceFiscale);
		log.info("getRuoliUtente", "estrazione tot "+countTotal);
		List<Ruolo> listResult = fromRuoloUtenteDtoListToRuoloList(ruoliUtenteList);
		result.setCount(countTotal);
		result.setListaRis(listResult);
		return result;
	}

	
	private List<Ruolo> fromRuoloUtenteDtoListToRuoloList(List<RuoloUtenteDto> ruoliUtenteList) {
		List<Ruolo> result = new ArrayList<Ruolo>();
		if(ruoliUtenteList != null && ruoliUtenteList.size()>0) {
			Ruolo tmp;
			for (RuoloUtenteDto ruoloUtenteDto : ruoliUtenteList) {
				tmp = fromRuoloDtoToRuolo(ruoloUtenteDto.getRuoloDto());
				tmp.setDataInizioValidita(ruoloUtenteDto.getDataInizioValidita());
				tmp.setDataFineValidita(ruoloUtenteDto.getDataFineValidita());
				result.add(tmp);
			}
		}
		return result;
	}


	
	private Ruolo fromRuoloDtoToRuolo(RuoloDto ruoloDto) {
		Ruolo ruolo = new Ruolo();		
		ruolo.setCodice(ruoloDto.getCodice());
		ruolo.setDescrizione(ruoloDto.getDescrizione());
//		ruolo.setDataInizioValidita(ruoloDto.getDataInizioValidita());
//		ruolo.setDataFineValidita(ruoloDto.getDataFineValidita());
		
		return ruolo;
	}


	private CollocazioneUtente fromAbilitazioneDtoToCollocazioneUtente(AbilitazioneDto abilitazione) {
		CollocazioneUtente result = new CollocazioneUtente();
		CollocazioneDto col = abilitazione.getUtenteCollocazioneDto().getCollocazioneDto();
		result.setCollocazioneCodice(col.getColCodice());
		result.setCollocazioneDescrizione(col.getColDescrizione());
		result.setCollocazioneCodiceAzienda(col.getColCodAzienda());
		result.setCollocazioneDescrizioneAzienda(col.getColDescAzienda());
		result.setDataInizioValidita(col.getDataInizioValidita());
		result.setDataFineValidita(col.getDataFineValidita());
		result.setStrutturaCodice(col.getCodStruttura());
		result.setStrutturaDescrizione(col.getDenomStruttura());
		result.setUoCodice(col.getCodUo());
		result.setUoDescrizione(col.getDenomUo());
		result.setMultiSpecCodice(col.getCodMultiSpec());
		result.setMultiSpecDescrizione(col.getDenomMultiSpec());
		result.setElementoOrganizzativoCodice(col.getCodiceElementoOrganizzativo());
		result.setElementoOrganizzativoDescrizione(col.getDescElemento());
		result.setAmbulatorioID(col.getIdAmbulatorio());
		result.setAmbulatorioDescrizione(col.getDenomAmbulatorio());
		return result;
	}



}
