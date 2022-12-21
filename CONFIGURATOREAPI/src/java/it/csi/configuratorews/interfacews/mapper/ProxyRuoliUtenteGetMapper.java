/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.mapper;

import it.csi.configuratorews.dto.configuratorews.ModelAbilitazione;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.interfacews.client.abilitazione.Abilitazione;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import it.csi.configuratorews.interfacews.client.collocazioni.Collocazione;
import it.csi.configuratorews.interfacews.client.collocazioni.GetCollocazioniResponse;
import it.csi.configuratorews.interfacews.client.ruoliUtente.GetRuoliUtenteResponse;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProxyRuoliUtenteGetMapper { 

    public ModelRuolo[] mapSoapResponseToRest(GetRuoliUtenteResponse getRuoliUtenteResponse){
        List<ModelRuolo> modelRuoloList = new ArrayList<ModelRuolo>();

        for(Ruolo ruoloUtente : getRuoliUtenteResponse.getListaRuoli()){
            ModelRuolo modelRuolo = new ModelRuolo();
            modelRuolo.setCodice(ruoloUtente.getCodice());
            modelRuolo.setDescrizione(ruoloUtente.getDescrizione());
            modelRuoloList.add(modelRuolo);
        }
        ModelRuolo[] arrayModelRuolo = new ModelRuolo[modelRuoloList.size()];
        modelRuoloList.toArray(arrayModelRuolo); // fill the array

        return arrayModelRuolo;
    }
}
