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

import it.csi.configuratorews.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneCollocazioneDto;
import it.csi.configuratorews.business.service.GetCollocazioniSOLService;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.Pagination;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetCollocazioniSOLServiceImpl implements GetCollocazioniSOLService {

    @Autowired
    ApplicazioneCollocazioneLowDao applicazioneCollocazioneLowDao;

    @Override
    public Pagination<ModelCollocazione> getCollocazioniSol(String applicazione, Integer limit, Integer offset, String codiceAzienda){

        /*
            Recupero le collocazioni collegate al sol inviato in input tramite
            la tabella di relazione auth_t_applicazione_collocazione
         */
    	Pagination<ModelCollocazione> pagination = new Pagination<ModelCollocazione>();
        List<ModelCollocazione> modelCollocazioneOutput = null;
        List<ApplicazioneCollocazioneDto> applicazioneCollocazioneDtoList = applicazioneCollocazioneLowDao.findByCodiceApplicazione(applicazione, limit, offset, codiceAzienda);
        if(applicazioneCollocazioneDtoList != null && !applicazioneCollocazioneDtoList.isEmpty()){
            modelCollocazioneOutput = new ArrayList<>();
            for(ApplicazioneCollocazioneDto applicazioneCollocazioneDto : applicazioneCollocazioneDtoList){
                popolaOutput(modelCollocazioneOutput, applicazioneCollocazioneDto);
            }
        }
        Long countCollocazioni =  applicazioneCollocazioneLowDao.countByCodiceApplicazione(applicazione);
        pagination.setCount(countCollocazioni);
        pagination.setListaRis(modelCollocazioneOutput);
        return pagination;
    }


    private void popolaOutput(List<ModelCollocazione> modelCollocazioneOutput, ApplicazioneCollocazioneDto applicazioneCollocazioneDto) {
        ModelCollocazione modelCollocazione = new ModelCollocazione();
        modelCollocazione.setCodiceCollocazione(applicazioneCollocazioneDto.getCollocazioneDto().getColCodice());
        modelCollocazione.setDescrizioneCollocazione(applicazioneCollocazioneDto.getCollocazioneDto().getColDescrizione());
        modelCollocazione.setCodiceAzienda(applicazioneCollocazioneDto.getCollocazioneDto().getColCodAzienda());
        modelCollocazione.setDescrizioneAzienda(applicazioneCollocazioneDto.getCollocazioneDto().getColDescAzienda());
        modelCollocazioneOutput.add(modelCollocazione);
    }


}
