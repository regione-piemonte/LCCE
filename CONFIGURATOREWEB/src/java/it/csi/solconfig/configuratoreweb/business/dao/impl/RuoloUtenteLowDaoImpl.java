/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.RuoloUtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloUtenteDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class RuoloUtenteLowDaoImpl extends EntityBaseLowDaoImpl<RuoloUtenteDto, Long> implements RuoloUtenteLowDao {

	@Override
	public Collection<RuoloUtenteDto> findByUtenteRuoloAndData(RuoloUtenteDto ruoloUtenteDto) {
		Query query = entityManager.createQuery("from " + ruoloUtenteDto.getClass().getName()
				+ " t WHERE t.utenteDto.id = :idUtente "
				+ "AND t.ruoloDto.id = :idRuolo AND :data BETWEEN t.dataInizioValidita AND t.dataFineValidita ");
		query.setParameter("idUtente", ruoloUtenteDto.getUtenteDto().getId());
		query.setParameter("idRuolo", ruoloUtenteDto.getRuoloDto().getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}
	
	@Override
	public Collection<RuoloUtenteDto> findByIdRuolo(Long idRuolo) {
		String tabella = getTabName(new RuoloUtenteDto());
		Query query = entityManager.createQuery("from " + tabella + " t WHERE t.ruoloDto.id = :idRuolo ");
		query.setParameter("idRuolo", idRuolo);
		return query.getResultList();
	}
	
	 public String getTabName(Object className) {
	        return className.getClass().getName();
	    }

}