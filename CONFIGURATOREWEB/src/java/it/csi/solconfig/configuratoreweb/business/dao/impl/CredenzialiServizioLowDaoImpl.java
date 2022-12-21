/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.CredenzialiServiziLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CredenzialiServiziDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class CredenzialiServizioLowDaoImpl extends EntityBaseLowDaoImpl<CredenzialiServiziDto, Long> implements CredenzialiServiziLowDao{

    @Override
    public Collection<CredenzialiServiziDto> findByFilterAndServizioAndData(CredenzialiServiziDto credenzialiServiziDto) {

        StringBuilder queryString = new StringBuilder("SELECT ID, ID_SERVIZIO, ID_APPLICAZIONE," +
                " USERNAME, pgp_sym_decrypt(cast(PASSWORD AS BYTEA), getGoodPwd('@encryption_key@')), DATA_INIZIO_VALIDITA, DATA_FINE_VALIDITA" +
                " from  AUTH_T_CREDENZIALI_SERVIZI WHERE ID_SERVIZIO = :idServizio " +
                " AND :data BETWEEN DATA_INIZIO_VALIDITA AND DATA_FINE_VALIDITA ");

        if(credenzialiServiziDto.getApplicazioneDto() != null){
            queryString.append(" AND ID_APPLICAZIONE = :idApplicazione");
        }

        Query query = entityManager.createNativeQuery(queryString.toString());

        query.setParameter("idServizio", credenzialiServiziDto.getServiziDto().getId());
        query.setParameter("data", Utils.sysdate());

        if(credenzialiServiziDto.getApplicazioneDto() != null){
            query.setParameter("idApplicazione", credenzialiServiziDto.getApplicazioneDto().getId());
        }


        return getResultList(query, CredenzialiServiziDto.class);
    }
}
