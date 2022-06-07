/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;
import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.CatalogoParametriLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoParametriDto;

import javax.persistence.Query;
import java.util.Collection;

@Component
public class CatalogoParametriLowDaoImpl extends CatalogoBaseLowDaoImpl<CatalogoParametriDto, Long> implements CatalogoParametriLowDao{

    @Override
	public Collection<ParametriLogin> findByCodiceAndApplicazione(CatalogoParametriDto catalogoParametriDto){

        StringBuilder queryBuilder = new StringBuilder("from " + catalogoParametriDto.getClass().getName() +" t WHERE ");
        queryBuilder.append(" t.codice = :codice AND t.applicazioneDto.id = :idApplicazione");

        Query query = getEntityManager().createQuery(queryBuilder.toString());
        query.setParameter("codice", catalogoParametriDto.getCodice());
        query.setParameter("idApplicazione", catalogoParametriDto.getApplicazioneDto().getId());

        return query.getResultList();
    }

}
