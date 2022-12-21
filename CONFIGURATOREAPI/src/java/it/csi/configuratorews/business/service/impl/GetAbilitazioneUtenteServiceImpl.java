/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.service.GetAbilitazioniService;
import it.csi.configuratorews.dto.configuratorews.AutorizzazioneUtente;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetAbilitazioneUtenteServiceImpl implements GetAbilitazioniService {

	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	

	@Override
	public AutorizzazioneUtente getAbilitazioneUtente(String codiceFiscale, String ruoloCodice,
			String applicazioneCodice, String collocazioneCodice, String profiloCodice, String funzionalitaCodice,String codiceAzienda) {
		
		AutorizzazioneUtente result = new AutorizzazioneUtente();
		result.setAbilitato(false);
		
		List<AbilitazioneDto> queryResult = abilitazioneLowDao.getAbilitazioniUtente( codiceFiscale,  ruoloCodice,
			 applicazioneCodice,  collocazioneCodice,  profiloCodice,  funzionalitaCodice,codiceAzienda);
		
		if(queryResult!= null && queryResult.size()>0) {
			result.setAbilitato(true);
		}
		
		return result;
	}

}
