/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.CollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.CollocazioneTipoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneTipoDto;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.Utente;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;

@Service
@Transactional
public class CollocazioneServiceImpl extends BaseServiceImpl implements CollocazioneService {

    @Autowired
    private CollocazioneLowDao collocazioneLowDao;

    @Autowired
    private CollocazioneTipoLowDao collocazioneTipoLowDao;

    @Override
    public List<CollocazioneDTO> getAllAziende(Data data) {
        boolean superUser = data.getUtente() != null &&
                data.getUtente().getProfilo() != null &&
                data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());
       // superUser=false;

        if (superUser) {
            //ricerca tutte le collocazioni
            return collocazioneLowDao.findAllAziendeFromSuperUser();
        } else {
            //ricerca solo le collocazioni su cui è abilitato
            List<CollocazioneDTO> allAziendeFromOperatore = collocazioneLowDao.findAllAziendeFromOperatore(Optional.ofNullable(data.getUtente()).map(Utente::getCodiceFiscale).orElse(null),data.getUtente().getCodiceFiscale());
            Map<String, List<CollocazioneDTO>> collocazioniGroupByColColAzienda = allAziendeFromOperatore.stream().collect(Collectors.groupingBy(CollocazioneDTO::getCodice));
            return collocazioniGroupByColColAzienda.values().stream().map(list -> {
                List<CollocazioneDTO> filterList = list.stream().filter(CollocazioneDTO::getAziendaSanitaria).collect(Collectors.toList());
                if (filterList.size() > 0)
                    return filterList.get(0);
                else if (list.size() > 0)
                    return list.get(0);
                else
                    return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    @Override
    public List<CollocazioneDTO> getCollocazioneByCodiceOrDescrizione(String azienda, String codice, String descrizione, Data data) {
        String codiceAzienda = "";
        String descrizioneAzienda = "";

        if (azienda != null && !azienda.isEmpty()) {
            try {
                codiceAzienda = azienda.substring(0, azienda.indexOf('-')).trim();
                descrizioneAzienda = azienda.substring(azienda.indexOf('-') + 2).trim();
            } catch (Exception e) {
                log.error("ERROR: getCollocazioneByCodiceOrDescrizione - ", e);
            }
        }
        boolean superUser = data.getUtente() != null &&
                data.getUtente().getProfilo() != null &&
                data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());

        List<CollocazioneDTO> collocazioneDTO = superUser ? collocazioneLowDao.findAziendaByCodiceOrDescrizioneFromSuperUser(codiceAzienda, descrizioneAzienda, codice, descrizione) :
                collocazioneLowDao.findAziendaByCodiceOrDescrizioneFromOperatore(codiceAzienda, descrizioneAzienda, codice, descrizione, Optional.ofNullable(data.getUtente()).map(Utente::getCodiceFiscale).orElse(null));
        replaceStringCollocazione(collocazioneDTO);

        return collocazioneDTO;
    }

    @Override
    public List<CollocazioneDTO> getCollocazioneById(List<Long> ids, Data data) {
        boolean superUser = data.getUtente() != null &&
                data.getUtente().getProfilo() != null &&
                data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());

        List<CollocazioneDTO> dtoList = superUser
                ? collocazioneLowDao.findAziendaByIdFromSuperUser(ids)
               // : collocazioneLowDao.findAziendaByIdFromOperatore(ids, Optional.ofNullable(data.getUtente()).map(Utente::getCodiceFiscale).orElse(null));
                : collocazioneLowDao.findCollocazioniFromID(ids, Optional.ofNullable(data.getUtente()).map(Utente::getCodiceFiscale).orElse(null));	
        replaceStringCollocazione(dtoList);

        return dtoList;
    }

    @Override
    public List<CollocazioneDTO> ricercaCollocazioniByIdUtente(Long idUtente, Data data) {
        boolean superUser = data.getUtente() != null &&
                data.getUtente().getProfilo() != null &&
                data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());

        return superUser ? collocazioneLowDao.findByIdUtenteFromSuperUser(idUtente) :
                collocazioneLowDao.findByIdUtenteFromOperatore(idUtente,Optional.ofNullable(data.getUtente()).map(Utente::getCodiceFiscale).orElse(null));
    }

    private static void replaceStringCollocazione(List<CollocazioneDTO> list) {
        if (list == null) return;

        list.forEach(dto -> {
            if (dto.getCodice() != null && !dto.getCodice().isEmpty())
                dto.setCodice(dto.getCodice().replace('@', ' '));

            if (dto.getDescrizione() != null && !dto.getDescrizione().isEmpty())
                dto.setDescrizione(dto.getDescrizione().replace('@', ' '));
        });
    }

	@Override
	public List<CollocazioneDTO> getCollocazioniSolByCodiceOrDescrizione(List<Long> collocazioni,Data data) {
			List<CollocazioneDTO> allAziende = getAllAziende(data);
			allAziende=allAziende.stream().filter(f->collocazioni.contains(f.getId())).collect(Collectors.toList());
			
		return allAziende;
	}

	@Override
	public Collection<CollocazioneTipoDto> getCollocazioniTipo() {
		return collocazioneTipoLowDao.findAll(new CollocazioneTipoDto());
	}

	
}
