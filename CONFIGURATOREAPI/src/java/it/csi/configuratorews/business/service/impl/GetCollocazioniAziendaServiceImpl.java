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

import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.service.GetCollocazioniAziendaService;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetCollocazioniAziendaServiceImpl implements GetCollocazioniAziendaService{

	@Autowired
	CollocazioneLowDao collocazioneLowDao;
	
	private LogUtil log = new LogUtil(this.getClass());
	
	@Override
	public Pagination<CollocazioneUtente> getCollocazioniAzienda(String codiceAzienda, String codiceStruttura, Integer limit,
			Integer offset) {
		Pagination<CollocazioneUtente> result = new Pagination<CollocazioneUtente>();
		List<CollocazioneDto> listaCollocazioni = collocazioneLowDao.findByAziendaAndStruttura( codiceAzienda,  codiceStruttura,  limit,
				 offset);
		log.info("getCollocazioniAzienda", "estrazione collocazioni per codiceAzienda "+codiceAzienda +" e codiceStruttura: "+ codiceStruttura);
		Long countTotal = collocazioneLowDao.countByAziendaAndStruttura(codiceAzienda, codiceStruttura);
		log.info("getCollocazioniAzienda", "estrazione tot "+countTotal);
		List<CollocazioneUtente> listRes = fromListCollocazioneDtoToListCollocazione(listaCollocazioni);
		result.setCount(countTotal);
		result.setListaRis(listRes);
		return result;
	}
	
	
	private List<CollocazioneUtente> fromListCollocazioneDtoToListCollocazione(
			List<CollocazioneDto> colList) {
		List<CollocazioneUtente>result = new ArrayList<CollocazioneUtente>();
		if(colList!= null && !colList.isEmpty()) {
			CollocazioneUtente cU=null;
			for(CollocazioneDto col :colList) {
				cU=fromCollocazioneDtoToCollocazione(col);
				result.add(cU);
			}
		}
		return result;
	}
	
	private CollocazioneUtente fromCollocazioneDtoToCollocazione(CollocazioneDto col) {		
		CollocazioneUtente result = new CollocazioneUtente();		
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
		result.setColTipoCodice(col.getCollocazioneTipoDto().getColTipoCodice());
		result.setColTipoDescrizione(col.getCollocazioneTipoDto().getColTipoDescrizione());
		
		return result;
	}


}
