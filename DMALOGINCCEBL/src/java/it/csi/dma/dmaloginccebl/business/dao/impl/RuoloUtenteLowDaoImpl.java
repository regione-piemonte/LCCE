/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.RuoloUtenteLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloUtenteDto;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Collection;

@Component
public class RuoloUtenteLowDaoImpl extends EntityBaseLowDaoImpl<RuoloUtenteDto, Long> implements RuoloUtenteLowDao{

    @Override
    public Collection<RuoloUtenteDto> findByUtenteRuoloAndData(RuoloUtenteDto ruoloUtenteDto) {
        Query query = entityManager.createQuery("from " + ruoloUtenteDto.getClass().getName() + " t WHERE t.utenteDto.id = :idUtente " +
                "AND t.ruoloDto.id = :idRuolo " +
                " AND ((:data > t.dataInizioValidita AND t.dataFineValidita is null) OR (:data BETWEEN t.dataInizioValidita AND t.dataFineValidita)) ");
        query.setParameter("idUtente", ruoloUtenteDto.getUtenteDto().getId());
        query.setParameter("idRuolo", ruoloUtenteDto.getRuoloDto().getId());
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }

	@Override
	public Collection<RuoloUtenteDto> findByIdUtente(Long id) {
		
		Query query=entityManager.createQuery("FROM "+new RuoloUtenteDto().getClass().getName()+" ru WHERE ru.ruoloDto.id=:id");
		query.setParameter("id", id);
		return query.getResultList();
	}
}
