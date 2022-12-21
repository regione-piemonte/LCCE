/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.mapper;

import it.csi.configuratorews.dto.configuratorews.ModelAbilitazione;
import it.csi.configuratorews.interfacews.client.abilitazione.Abilitazione;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbilitazioniMapper {

    public ModelAbilitazione[] mapSoapResponseToRest(GetAbilitazioniResponse getAbilitazioniResponse){
        List<ModelAbilitazione> modelAbilitazioneList = new ArrayList<ModelAbilitazione>();

        for(Abilitazione abilitazione : getAbilitazioniResponse.getListaAbilitazioni()){
            ModelAbilitazione modelAbilitazione = new ModelAbilitazione();
            modelAbilitazione.setApplicazione(abilitazione.getApplicazione().getCodiceApplicazione());
            modelAbilitazioneList.add(modelAbilitazione);
        }
        ModelAbilitazione[] arrayModelAbilitazione = new ModelAbilitazione[modelAbilitazioneList.size()];
        modelAbilitazioneList.toArray(arrayModelAbilitazione); // fill the array

        return arrayModelAbilitazione;
    }
}
