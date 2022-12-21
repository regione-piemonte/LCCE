/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.business.dto.UtenteDto;
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
public class GetUtentiSOLServiceImpl implements GetUtentiSOLService {

    @Autowired
    ApplicazioneLowDao applicazioneLowDao;

    @Autowired
    TreeFunzionalitaLowDao treeFunzionalitaLowDao;

    @Autowired
    AbilitazioneLowDao abilitazioneLowDao;

    @Override
    public Pagination<ModelUtente> getUtentiSol(String applicazione, String azienda,Integer limit,Integer offset){

        /*
            Recupero di tutti i profili attivi correlati all'applicazione in input
            Per ogni profilo recuperare le abilitzioni attive
            E per ogni abilitazione abbiamo bisogno dei dati dell'utente, ruolo e collocazione
         */
    	Pagination<ModelUtente> modelUtente = new Pagination<>();
        Map<UtenteDto, List<AbilitazioneDto>> outputMap = new HashMap<>();
        ApplicazioneDto applicazioneDto = getApplicazioneDto(applicazione);
        if(applicazioneDto != null){
            List<TreeFunzionalitaDto> listaProfiliApplicazione = recuperoListaProfili(applicazioneDto);

            if(listaProfiliApplicazione != null && !listaProfiliApplicazione.isEmpty()){
                for(TreeFunzionalitaDto profilo : listaProfiliApplicazione){
                    List<AbilitazioneDto> abilitazioniProfilo = recuperoListaAbilitazioniProfilo(azienda, profilo);
                    for(AbilitazioneDto abilitazione : abilitazioniProfilo){
                        popolaMappaUtenteAbilitazione(outputMap, abilitazione);
                    }
                }
                if(!outputMap.isEmpty()) {
                    List<ModelUtente> popolaOutput = popolaOutput(outputMap);
                    List<ModelUtente> result = new ArrayList<ModelUtente>();
                    for (int i = 0; i < popolaOutput.size(); i++) {
                    	ModelUtente element = popolaOutput.get(i);
						if(i<(limit+offset) && i>=offset) {
							result.add(element);
						}
					}
                    modelUtente.setListaRis(result);
                    modelUtente.setCount((long)popolaOutput.size());
					return modelUtente;
                }
            }
        }
        return null;
    }

    private List<ModelUtente> popolaOutput(Map<UtenteDto, List<AbilitazioneDto>> outputMap) {
        List<ModelUtente> modelUtenteList = new ArrayList<>();
        for(UtenteDto utente : outputMap.keySet()){
            List<AbilitazioneDto> abilitzioniUtente = outputMap.get(utente);
            List<Abilitazione> abilitazioniOutput = new ArrayList<>();
            for(AbilitazioneDto abilitazioneDto : abilitzioniUtente){
                popolaAbilitazioneOutput(abilitazioniOutput, abilitazioneDto);
            }
            ModelUtente modelUtente = new ModelUtente();
            modelUtente.setNome(utente.getNome());
            modelUtente.setCognome(utente.getCognome());
            modelUtente.setCodiceFiscale(utente.getCodiceFiscale());
            modelUtente.setAbilitazioni(abilitazioniOutput);
            modelUtenteList.add(modelUtente);
        }
        return modelUtenteList;
    }

    private void popolaAbilitazioneOutput(List<Abilitazione> abilitazioniOutput, AbilitazioneDto abilitazioneDto) {
        Abilitazione abilitazione = new Abilitazione();

        ModelRuolo modelRuolo = new ModelRuolo();
        modelRuolo.setCodice(abilitazioneDto.getRuoloUtenteDto().getRuoloDto().getCodice());
        modelRuolo.setDescrizione(abilitazioneDto.getRuoloUtenteDto().getRuoloDto().getDescrizione());

        ModelCollocazione modelCollocazione = new ModelCollocazione();
        modelCollocazione.setCodiceCollocazione(abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColCodice());
        modelCollocazione.setDescrizioneCollocazione(abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColDescrizione());
        modelCollocazione.setCodiceAzienda(abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda());
        modelCollocazione.setDescrizioneAzienda(abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColDescAzienda());

        Profilo profilo = new Profilo();
        profilo.setCodice(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione());
        profilo.setDescrizione(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDescrizioneFunzione());

        abilitazione.setRuolo(modelRuolo);
        abilitazione.setCollocazione(modelCollocazione);
        abilitazione.setProfilo(profilo);
        abilitazioniOutput.add(abilitazione);
    }

    private void popolaMappaUtenteAbilitazione(Map<UtenteDto, List<AbilitazioneDto>> outputMap, AbilitazioneDto abilitazione) {
        if(outputMap.get(abilitazione.getRuoloUtenteDto().getUtenteDto()) != null){
            outputMap.get(abilitazione.getRuoloUtenteDto().getUtenteDto()).add(abilitazione);
        }else{
            List<AbilitazioneDto> listaAbilitazioniUtente = new ArrayList<>();
            listaAbilitazioniUtente.add(abilitazione);
            outputMap.put(abilitazione.getRuoloUtenteDto().getUtenteDto(), listaAbilitazioniUtente);
        }
    }

    private List<AbilitazioneDto> recuperoListaAbilitazioniProfilo(String azienda, TreeFunzionalitaDto profilo) {
        return abilitazioneLowDao.findByIdFunzioneAndAzienda(profilo, azienda);
    }

    private List<TreeFunzionalitaDto> recuperoListaProfili(ApplicazioneDto applicazioneDto) {
        return treeFunzionalitaLowDao.findByIdApplicazione(applicazioneDto, FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO);
    }

    private ApplicazioneDto getApplicazioneDto(String applicazione) {
        ApplicazioneDto applicazioneDto = new ApplicazioneDto();
        applicazioneDto.setCodice(applicazione);
        applicazioneDto = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto));
        return applicazioneDto;
    }
}
