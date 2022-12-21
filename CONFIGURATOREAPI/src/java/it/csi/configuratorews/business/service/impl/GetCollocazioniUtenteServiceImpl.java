/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.service.GetCollocazioniUtenteService;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetCollocazioniUtenteServiceImpl implements GetCollocazioniUtenteService{

	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	
	@Autowired
	CollocazioneLowDao collocazioneLowDao;
	
	private LogUtil log = new LogUtil(this.getClass());
	
	@Override
	public Pagination<CollocazioneUtente> getCollocazioniUtente(String codiceFiscale,
			String ruoloCodice, Integer offset, Integer limit,String codiceAzienda) {
		Pagination<CollocazioneUtente> result = new Pagination<CollocazioneUtente>();
		
//		List<AbilitazioneDto> abilitazioni = abilitazioneLowDao.findByCodiceFiscaleUtenteAndCodiceRuolo(codiceFiscale, ruoloCodice, offset, limit,codiceAzienda);
//		log.info("getCollocazioniUtente", "estrazione collocazioni per cf "+codiceFiscale +" e ruolo: "+ ruoloCodice);
//		Long countTotal = abilitazioneLowDao.countByCodiceFiscaleUtenteAndCodiceRuolo(codiceFiscale, ruoloCodice,codiceAzienda);
//		log.info("getCollocazioniUtente", "estrazione tot "+countTotal);
//		List<CollocazioneUtente> listRes = fromListAbilitazioneDtoToListCollocazioneUtente(abilitazioni);
//		List<CollocazioneUtente> listRes2 = listRes.stream().sorted((collocazione1,collocazione2)->collocazione1.getCollocazioneCodice().compareTo(collocazione2.getCollocazioneCodice())).collect(Collectors.toList());;
//		result.setCount(countTotal);
//		result.setListaRis(listRes2);
		
		List<CollocazioneDto> collocazioniDto = collocazioneLowDao.findByRuoloAziendaAndUtente(codiceAzienda, ruoloCodice, codiceFiscale, limit, offset);
		List<CollocazioneUtente> collocazioniUtente = formCollocazioneDtoListToCollocazioneUtenteList(collocazioniDto);
		
		List<CollocazioneUtente> listRes2 = collocazioniUtente.stream().sorted((collocazione1,collocazione2)->collocazione1.getCollocazioneCodice().compareTo(collocazione2.getCollocazioneCodice())).collect(Collectors.toList());;
		result.setListaRis(listRes2);
			
		Long countTotal = collocazioneLowDao.countByRuoloAziendaAndUtente(codiceFiscale, ruoloCodice, codiceAzienda);
		result.setCount(countTotal);
		
		return result;
	}
	
	private List<CollocazioneUtente> formCollocazioneDtoListToCollocazioneUtenteList(
			List<CollocazioneDto> collocazioniDto) {

		List<CollocazioneUtente> result = new ArrayList<>();
		
		for(CollocazioneDto col: collocazioniDto) {
			CollocazioneUtente collocazioneUtente = new CollocazioneUtente();
			collocazioneUtente.setCollocazioneCodice(col.getColCodice());
			collocazioneUtente.setCollocazioneDescrizione(col.getColDescrizione());
			collocazioneUtente.setCollocazioneCodiceAzienda(col.getColCodAzienda());
			collocazioneUtente.setCollocazioneDescrizioneAzienda(col.getColDescAzienda());
			collocazioneUtente.setDataInizioValidita(col.getDataInizioValidita());
			collocazioneUtente.setDataFineValidita(col.getDataFineValidita());
			collocazioneUtente.setStrutturaCodice(col.getCodStruttura());
			collocazioneUtente.setStrutturaDescrizione(col.getDenomStruttura());
			collocazioneUtente.setUoCodice(col.getCodUo());
			collocazioneUtente.setUoDescrizione(col.getDenomUo());
			collocazioneUtente.setMultiSpecCodice(col.getCodMultiSpec());
			collocazioneUtente.setMultiSpecDescrizione(col.getDenomMultiSpec());
			collocazioneUtente.setElementoOrganizzativoCodice(col.getCodiceElementoOrganizzativo());
			collocazioneUtente.setElementoOrganizzativoDescrizione(col.getDescElemento());
			collocazioneUtente.setAmbulatorioID(col.getIdAmbulatorio());
			collocazioneUtente.setAmbulatorioDescrizione(col.getDenomAmbulatorio());
			collocazioneUtente.setColTipoCodice(col.getCollocazioneTipoDto().getColTipoCodice());
			collocazioneUtente.setColTipoDescrizione(col.getCollocazioneTipoDto().getColTipoDescrizione());
			result.add(collocazioneUtente);
		}

		return result;
		
	}

	private List<CollocazioneUtente> fromListAbilitazioneDtoToListCollocazioneUtente(
			List<AbilitazioneDto> abilitazioni) {
		List<CollocazioneUtente>result = new ArrayList<CollocazioneUtente>();
		if(abilitazioni!= null && !abilitazioni.isEmpty()) {
			CollocazioneUtente cU=null;
			for(AbilitazioneDto col :abilitazioni) {
				cU=fromAbilitazioneDtoToCollocazioneUtente(col);
				result.add(cU);
			}
		}
		return result;
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
