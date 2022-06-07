/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.impl;

import it.csi.dma.puawa.business.dao.dto.RuoloDto;
import it.csi.dma.puawa.util.Utils;
import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.ReportTipoLowDao;
import it.csi.dma.puawa.business.dao.dto.ReportTipoDto;

import javax.persistence.Query;
import java.util.Collection;

@Component
public class ReportTipoDtoLowDaoImpl extends EntityBaseLowDaoImpl<ReportTipoDto, Long> implements ReportTipoLowDao {

    @Override
    public Collection<ReportTipoDto> findAllByDataValidazione() {
        Query query = entityManager.createQuery(
                "SELECT r FROM ReportTipoDto r WHERE (:data BETWEEN r.dataInizioValidita AND r.dataFineValidita) OR (:data >= r.dataInizioValidita AND r.dataFineValidita IS NULL)");
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }
}
