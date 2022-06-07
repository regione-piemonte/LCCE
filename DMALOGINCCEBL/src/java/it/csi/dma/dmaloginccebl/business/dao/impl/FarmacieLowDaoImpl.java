/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.FarmacieLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class FarmacieLowDaoImpl extends BaseLowDaoImpl<FarmacieDto> implements FarmacieLowDao {
	
	@Override
	public Collection<FarmacieDto> findByDatiFarmacia(FarmacieDto farmacieDto, List<String> codiciFarmacia) throws Exception{
		try {

		StringBuilder queryBuilder = new StringBuilder("from " + farmacieDto.getClass().getName() +" t WHERE 1 = 1 ");

		if(farmacieDto.getComune() != null && !farmacieDto.getComune().isEmpty()) {
			queryBuilder.append(" AND UPPER(t.comune) LIKE UPPER(:comune)");
		}
		if(farmacieDto.getIndirizzo() != null && !farmacieDto.getIndirizzo().isEmpty()) {
			queryBuilder.append(" AND UPPER(t.indirizzo) LIKE UPPER(:indirizzo)");
		}
		if(farmacieDto.getDenominazioneFarmacia() != null && !farmacieDto.getDenominazioneFarmacia().isEmpty()) {
			queryBuilder.append(" AND UPPER(t.denominazioneFarmacia) LIKE UPPER(:denominazioneFarmacia)");
		}
		if(codiciFarmacia != null && !codiciFarmacia.isEmpty() && !Utils.getFirstRecord(codiciFarmacia).isEmpty()) {
			queryBuilder.append(" AND t.codiceFarmacia IN (:codiciFarmacia)");
		}
		queryBuilder.append(" AND (( t.dataInizioValiditaFarmacia is not null AND t.dataInizioValiditaFarmacia <= :data) "
				+ "AND (t.dataFineValiditaFarmacia is null OR t.dataFineValiditaFarmacia > :data))");
		
		queryBuilder.append(" AND (( t.dataInizioFarmaciaAbituale is not null AND t.dataInizioFarmaciaAbituale <= :data) "
				+ "AND (t.dataFineFarmaciaAbituale is null OR t.dataFineFarmaciaAbituale > :data))");
			
		Query query = entityManager.createQuery(queryBuilder.toString());
		if(farmacieDto.getComune() != null && !farmacieDto.getComune().isEmpty()) {
		query.setParameter("comune", "%"+farmacieDto.getComune()+"%"); 
		}
		if(farmacieDto.getIndirizzo() != null && !farmacieDto.getIndirizzo().isEmpty()) {
		query.setParameter("indirizzo", "%"+farmacieDto.getIndirizzo()+"%");
		}
		if(farmacieDto.getDenominazioneFarmacia() != null && !farmacieDto.getDenominazioneFarmacia().isEmpty()) {
		query.setParameter("denominazioneFarmacia", "%"+farmacieDto.getDenominazioneFarmacia()+"%");
		}
		if(codiciFarmacia != null && !codiciFarmacia.isEmpty() && !Utils.getFirstRecord(codiciFarmacia).isEmpty()) {
		query.setParameter("codiciFarmacia", codiciFarmacia);
		}
		query.setParameter("data", Utils.sysdate());
				
		return query.getResultList();
	
	} catch (Exception e) {
		e.printStackTrace();
		log.error(e);
		throw e;
	}
	}

}
