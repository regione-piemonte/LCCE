/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.UtenteCollocazioneLowDao;
import it.csi.configuratorews.business.dto.UtenteCollocazioneDto;
import it.csi.configuratorews.business.dto.UtenteDto;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class UtenteCollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<UtenteCollocazioneDto, Long> implements UtenteCollocazioneLowDao {

    @Override
    public List<UtenteCollocazioneDto> findByUtenteDto(UtenteDto utenteDto) {
        Query query = entityManager.createQuery("FROM UtenteCollocazioneDto u WHERE u.utenteDto = :utenteDto");
        query.setParameter("utenteDto", utenteDto);
        return query.getResultList();
    }

}