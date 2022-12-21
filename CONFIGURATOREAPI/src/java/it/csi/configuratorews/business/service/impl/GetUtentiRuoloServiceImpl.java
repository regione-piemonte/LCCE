/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import it.csi.configuratorews.business.dao.*;
import it.csi.configuratorews.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.configuratorews.business.dto.*;
import it.csi.configuratorews.business.service.GetUtentiRuoloService;
import it.csi.configuratorews.business.service.GetUtentiSOLService;
import it.csi.configuratorews.dto.configuratorews.*;
import it.csi.configuratorews.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetUtentiRuoloServiceImpl implements GetUtentiRuoloService {

    @Autowired
    RuoloUtenteLowDao ruoloUtenteLowDao;

    @Override
    public List<ModelUtenteBase> getUtentiRuolo(String codiceRuolo){

        /*
            Recupero tutti gli utenti partendo dal codice ruolo
            eseguo una query unica sulla RuoloUtente in modo tale da poter filtrare facilmente per tutte e 3 le tabelle
            (ruolo, utente e ruolo_utente)
            Sono gia' sicuro che il ruolo e' visibile dal configuratore in quanto lo controlla il validator prima, quindi
            nella query non e' gestito
         */

        List<ModelUtenteBase> modelUtenteBaseList = null;
        List<RuoloUtenteDto> ruoloUtenteDtoList = recuperaUtentiRuolo(codiceRuolo);
        if(ruoloUtenteDtoList != null && !ruoloUtenteDtoList.isEmpty()){
            modelUtenteBaseList = new ArrayList<>();
            for(RuoloUtenteDto ruoloUtenteDto : ruoloUtenteDtoList){
                popolaOutput(modelUtenteBaseList, ruoloUtenteDto);
            }
        }
        return modelUtenteBaseList;
    }

    private List<RuoloUtenteDto> recuperaUtentiRuolo(String codiceRuolo) {
        return ruoloUtenteLowDao.findByCodiceRuoloAndDataValidita(codiceRuolo);
    }

    private void popolaOutput(List<ModelUtenteBase> modelUtenteBaseList, RuoloUtenteDto ruoloUtenteDto) {
        ModelUtenteBase modelUtenteBase = new ModelUtenteBase();
        modelUtenteBase.setNome(ruoloUtenteDto.getUtenteDto().getNome());
        modelUtenteBase.setCognome(ruoloUtenteDto.getUtenteDto().getCognome());
        modelUtenteBase.setCodiceFiscale(ruoloUtenteDto.getUtenteDto().getCodiceFiscale());
        modelUtenteBaseList.add(modelUtenteBase);
    }

}
