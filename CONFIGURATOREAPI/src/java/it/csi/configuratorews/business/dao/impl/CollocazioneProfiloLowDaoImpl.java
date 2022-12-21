/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.CollocazioneProfiloLowDao;
import it.csi.configuratorews.business.dto.CollocazioneProfiloDto;
import it.csi.configuratorews.util.Utils;

@Component
public class CollocazioneProfiloLowDaoImpl extends EntityBaseLowDaoImpl<CollocazioneProfiloDto, Long>
		implements CollocazioneProfiloLowDao {

	@Override
	public Boolean existsProfiloByAzienda(String codiceAzienda, String codiceProfilo) {

		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("select cp from CollocazioneProfiloDto cp ");
		queryBuilder.append("where cp.collocazioneDto.flagAzienda = 'S' ");
		queryBuilder.append("and cp.collocazioneDto.colCodAzienda = :codiceAzienda ");
		queryBuilder.append("and cp.collocazioneDto.dataInizioValidita <= :data ");
		queryBuilder.append(
				"and (cp.collocazioneDto.dataFineValidita is null or cp.collocazioneDto.dataFineValidita >= :data) ");
		queryBuilder.append("and cp.funzionalitaDto.codiceFunzione = :codiceProfilo ");
		queryBuilder.append("and cp.funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = 'PROF' ");
		queryBuilder.append("and cp.funzionalitaDto.dataInizioValidita <= :data ");
		queryBuilder.append(
				"and (cp.funzionalitaDto.dataFineValidita is null or cp.funzionalitaDto.dataFineValidita >= :data) ");

		Query query = entityManager.createQuery(queryBuilder.toString(), CollocazioneProfiloDto.class);

		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("codiceProfilo", codiceProfilo);
		query.setParameter("data", data);

		List<CollocazioneProfiloDto> apps = query.getResultList();
		if (apps != null && apps.size() > 0)
			return true;

		return false;
	}

	@Override
	public Boolean existsApplicazioneByAziendaAndFunzionalita(String codiceAzienda,
			String codiceApplicazione) {
				
		StringBuilder sql = new StringBuilder("select cp from CollocazioneProfiloDto cp ");
		sql.append("where cp.collocazioneDto.colCodAzienda = :codiceAzienda ");
		sql.append("and cp.collocazioneDto.flagAzienda = 'S' ");
		sql.append("and cp.collocazioneDto.dataInizioValidita <= now() ");
		sql.append("and (cp.collocazioneDto.dataFineValidita is null or cp.collocazioneDto.dataFineValidita >= now()) ");
		sql.append("and cp.funzionalitaDto.applicazioneDto.codice = :codiceApplicazione ");

		Query query = entityManager.createQuery(sql.toString(), CollocazioneProfiloDto.class);
				        
        query.setParameter("codiceAzienda", codiceAzienda);
        query.setParameter("codiceApplicazione", codiceApplicazione);
        
		List<CollocazioneProfiloDto> apps = query.getResultList();
				
		if(apps!=null && apps.size()>0) 
			return true;
		
		return false;
		
	}
	
}
