/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.PreferenzaFruitoreLowDao;
import it.csi.configuratorews.business.dto.PreferenzaFruitoreDto;

@Component
public class PreferenzaFruitoreLowDaoImpl extends EntityBaseLowDaoImpl<PreferenzaFruitoreDto, Long> implements PreferenzaFruitoreLowDao {

	@Override
	public List<PreferenzaFruitoreDto> findByCode(String codiceFornitore, String codiceApplicazione, String codiceRuolo, String codiceCollocazione) {
		StringBuffer hql = new StringBuffer("select preferenzaFruitore from PreferenzaFruitoreDto as preferenzaFruitore ");
		if(codiceApplicazione!=null) {
			hql.append(" inner join preferenzaFruitore.applicazioneDto as applicazione");
		}
		if(codiceRuolo!=null) {
			hql.append(" inner join preferenzaFruitore.ruoloDto as ruolo");
		}
		if(codiceCollocazione!=null) {
			hql.append(" inner join preferenzaFruitore.collocazioneDto as collocazione");
		}
		hql.append(" where preferenzaFruitore.dataCancellazione is null");
		hql.append(" and preferenzaFruitore.nomeFruitore = '"+codiceFornitore+"'");
		if(codiceApplicazione!=null) {
			hql.append(" and applicazione.codice = '"+codiceApplicazione+"'");
		}
		if(codiceRuolo!=null) {
			hql.append(" and ruolo.codice = '"+codiceRuolo+"'");
		}
		if(codiceCollocazione!=null) {
			hql.append(" and collocazione.colCodice = '"+codiceCollocazione+"'");
		}
		Query q = entityManager.createQuery(hql.toString());
		return (List<PreferenzaFruitoreDto>)  q.getResultList();
	}

	@Override
	public List<PreferenzaFruitoreDto> findByName(String nomeFruitore, String applicazione, String codiceRuolo, String codiceCollocazione) {
		StringBuffer hql = new StringBuffer(
				"select preferenzaFruitore from PreferenzaFruitoreDto as preferenzaFruitore where preferenzaFruitore.dataCancellazione is null");
		hql.append(" and preferenzaFruitore.nomeFruitore = '"+nomeFruitore+"'");
		if(applicazione!=null) {
			hql.append(" and preferenzaFruitore.applicazioneDto.codice = '"+applicazione+"'");
		}
		if(codiceRuolo!=null) {
			hql.append(" and preferenzaFruitore.ruoloDto.codice = '"+codiceRuolo+"'");
		}
		if(codiceCollocazione!=null) {
			hql.append(" and preferenzaFruitore.collocazioneDto.colCodice = '"+codiceCollocazione+"'");
		}
		Query q = entityManager.createQuery(hql.toString());
		return q.getResultList();
	}

	@Override
	public List<PreferenzaFruitoreDto> findAllActive() {
		StringBuffer hql = new StringBuffer(
				"select preferenzaFruitore from PreferenzaFruitoreDto as preferenzaFruitore "
				+ " left join fetch preferenzaFruitore.applicazioneDto"
				+ " left join fetch preferenzaFruitore.ruoloDto"	
				+ " left join fetch preferenzaFruitore.collocazioneDto"	
				+ " where preferenzaFruitore.dataCancellazione is null");
		Query q = entityManager.createQuery(hql.toString());
		return q.getResultList();
	}

}
