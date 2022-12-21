/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.business.service.UtenteService;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;
import it.csi.configuratorews.exception.ErroreBuilder;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    UtenteLowDao utenteLowDao;

    @Override
    public ModelUtenteBase getUtenteInfo(String cfUtente){

        /*
            Recupero le informazioni base dell'utente
         */

        ModelUtenteBase modelUtenteBase = null;
        List<UtenteDto> utenteDtoList = (List<UtenteDto>) utenteLowDao.findByCodiceFiscaleValido(cfUtente);
        if(utenteDtoList != null && !utenteDtoList.isEmpty()){
        	UtenteDto utenteDto = utenteDtoList.get(0);
        	modelUtenteBase = new ModelUtenteBase();
        	modelUtenteBase.setCognome(utenteDto.getCognome());
        	modelUtenteBase.setNome(utenteDto.getNome());
        	modelUtenteBase.setCodiceFiscale(cfUtente);
        	modelUtenteBase.setSesso(utenteDto.getSesso());
        } else {
        	throw ErroreBuilder.from(Response.Status.NOT_FOUND).descrizione("Utente non trovato").exception();
        }
        return modelUtenteBase;
    }

	@Override
	public void aggiornaAccessoPua(String cfUtente) {
		UtenteDto utente=utenteLowDao.findByCodiceFiscale(cfUtente);
		if(utente!=null) {
			utente.setUltimoAccessoPua(new Timestamp(System.currentTimeMillis()));
			utenteLowDao.modifica(utente);
			
		}
	}


}
