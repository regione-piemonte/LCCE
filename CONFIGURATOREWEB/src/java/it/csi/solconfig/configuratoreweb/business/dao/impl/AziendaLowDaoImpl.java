/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AziendaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.AziendaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipoFunzionalitaLowDao;


@Component
public class AziendaLowDaoImpl  implements AziendaLowDao{
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public AziendaDto findAziendaByCodice(String codAzienda) {   
		
		Query query = entityManager.createQuery("FROM AziendaDto ac " +
            "WHERE ac.codAzienda = :codAzienda " +
            " AND ((:data BETWEEN ac.dataInizioValidita AND ac.dataFineValidita) " +
            " OR (:data >= ac.dataInizioValidita and ac.dataFineValidita is null)) ",AziendaDto.class);

    query.setParameter("codAzienda", codAzienda);
    query.setParameter("data", Utils.sysdate());

    return (AziendaDto) query.getSingleResult();
    };

}
