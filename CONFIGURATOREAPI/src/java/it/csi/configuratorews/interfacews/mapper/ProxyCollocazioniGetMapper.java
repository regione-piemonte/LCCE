/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.mapper;

import it.csi.configuratorews.dto.configuratorews.ModelAbilitazione;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.interfacews.client.abilitazione.Abilitazione;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import it.csi.configuratorews.interfacews.client.collocazioni.Collocazione;
import it.csi.configuratorews.interfacews.client.collocazioni.GetCollocazioniResponse;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProxyCollocazioniGetMapper {

    public ModelCollocazione[] mapSoapResponseToRest(GetCollocazioniResponse getCollocazioniResponse){
        List<ModelCollocazione> modelCollocazioneList = new ArrayList<ModelCollocazione>();

        for(Collocazione collocazione : getCollocazioniResponse.getCollocazioni()){
            ModelCollocazione modelCollocazione = new ModelCollocazione();
            modelCollocazione.setCodiceAzienda(collocazione.getColCodAzienda());
            modelCollocazione.setCodiceCollocazione(collocazione.getColCodice());
            modelCollocazione.setDescrizioneAzienda(collocazione.getColDescAzienda());
            modelCollocazione.setDescrizioneCollocazione(collocazione.getColDescrizione());
            modelCollocazioneList.add(modelCollocazione);
        }
        ModelCollocazione[] arrayModelCollocazione = new ModelCollocazione[modelCollocazioneList.size()];
        modelCollocazioneList.toArray(arrayModelCollocazione); // fill the array

        return arrayModelCollocazione;
    }
}
