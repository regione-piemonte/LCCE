/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import it.csi.configuratorews.business.dao.*;
import it.csi.configuratorews.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.configuratorews.business.dto.*;
import it.csi.configuratorews.business.service.GetRuoloService;
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
public class GetRuoloServiceImpl implements GetRuoloService {

    @Autowired
    RuoloLowDao ruoloLowDao;

    @Override
    public Pagination<ModelRuolo> getRuolo(Integer limit, Integer offset){

        /*
            Recupero tutti i ruoli attivi e aventi la visibilita' ad "S"
            eseguo una query unica in modo tale da poter filtrare facilmente per tutte le tabelle
            Sono gia' sicuro che il ruolo e' visibile dal configuratore in quanto lo controlla il validator prima, quindi
            nella query non e' gestito
         */
    	Pagination<ModelRuolo> modelRuolo = new Pagination<ModelRuolo>();
        List<ModelRuolo> modelRuoloList = null;
        List<RuoloDto> ruoloUtenteDtoList = ruoloLowDao.findByStatoAttivoEVisibilita(limit, offset);
        Long countListaRis = ruoloLowDao.countByStatoAttivoEVisibilita();
        if(ruoloUtenteDtoList != null && !ruoloUtenteDtoList.isEmpty()){
            modelRuoloList = new ArrayList<>();
            for(RuoloDto ruoloUtenteDto : ruoloUtenteDtoList){
                popolaOutput(modelRuoloList, ruoloUtenteDto);
            }
        }
        modelRuolo.setListaRis(modelRuoloList);
        modelRuolo.setCount(countListaRis);
        return modelRuolo;
    }


    private void popolaOutput(List<ModelRuolo> modelRuoloList, RuoloDto ruoloUtenteDto) {
        ModelRuolo modelRuolo = new ModelRuolo();
        modelRuolo.setCodice(ruoloUtenteDto.getCodice());
        modelRuolo.setDescrizione(ruoloUtenteDto.getDescrizione());
        modelRuoloList.add(modelRuolo);
    }

}
