/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import it.csi.solconfig.configuratoreweb.business.service.RuoloService;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RuoloServiceImpl extends BaseServiceImpl implements RuoloService {

    @Autowired
    private RuoloLowDao ruoloLowDao;

    @Override
    public List<RuoloDTO> ricercaTuttiRuoli(Data data) {
    	boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
        return ruoloLowDao.findAll(superUser);
    }

    @Override
    public List<RuoloDTO> ricercaTuttiRuoliNoFilter() {
        return ruoloLowDao.findAllNoFilter();
    }

    @Override
    public List<RuoloDTO> ricercaRuoliNonConfiguratore(Data data) {
    	boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
        return ruoloLowDao.ricercaRuoliNonConfiguratore(superUser);
    }

    @Override
    public List<RuoloDTO> ricercaRuoliByIdUtente(Long idUtente, Data data) {
        return ruoloLowDao.findByIdUtente(idUtente);
    }

	@Override
	public List<RuoloDTO> getRuoliSelezionabili(String cfOperatore,String codCollocazione, String codRuolo) {
		List<RuoloDTO> ruoliSelezionabili = ruoloLowDao.getRuoliSelezionabili(cfOperatore,codCollocazione, codRuolo);
		if(ruoliSelezionabili.stream().anyMatch( r -> r.getDescrizione() != null)) {
        	ruoliSelezionabili = ruoliSelezionabili.stream()
        	.filter(r -> r.getDescrizione() != null)
        	.collect(Collectors.toList());
        }
		return ruoliSelezionabili;
	}
	
	@Override
	public List<String> getRuoliCompatibili(String idRuolo) {
		if(idRuolo.endsWith("ro")) idRuolo = idRuolo.substring(0, idRuolo.length()-2);
		return ruoloLowDao.getRuoliCompatibili(Long.valueOf(idRuolo));
	}

	@Override
	public List<RuoloDTO> ricercaTuttiRuoliInserimento() {
		return ruoloLowDao.ricercaRuoliNonConfiguratore(true);
	}
}
