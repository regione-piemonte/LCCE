/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneCollocazioneDto;
import it.csi.configuratorews.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class ApplicazioneCollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<ApplicazioneCollocazioneDto, Long> implements ApplicazioneCollocazioneLowDao {

	@Override
	public List<Long> findIdCollocazioniByIdApplicazione(Long idApplicazione) {
		return entityManager
				.createQuery("SELECT ac.collocazioneDto.colId " + "FROM ApplicazioneCollocazioneDto ac " + "WHERE ac.applicazioneDto.id = :idApplicazione",
						Long.class)
				.setParameter("idApplicazione", idApplicazione).getResultList();
	}

	@Override
	public List<ApplicazioneCollocazioneDto> findByCodiceApplicazione(String codiceApplicazione, Integer limit, Integer offset, String codiceAzienda) {
		StringBuilder stringBuilder = new StringBuilder(
				" FROM ApplicazioneCollocazioneDto ac WHERE ac.applicazioneDto.codice = :codiceApplicazione "
				+ " AND ((:data BETWEEN ac.collocazioneDto.dataInizioValidita AND ac.collocazioneDto.dataFineValidita) "
				+ " OR (:data >= ac.collocazioneDto.dataInizioValidita and ac.collocazioneDto.dataFineValidita is null)) ");
		if(codiceAzienda!=null) {
			stringBuilder.append(" and ac.collocazioneDto.colCodAzienda=:codiceAzienda");
		}
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("data", Utils.sysdate());
		if(codiceAzienda != null) {
			query.setParameter("codiceAzienda", codiceAzienda);			
		}
		if (limit != null) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		return query.getResultList();
	}

	@Override
	public Long countByCodiceApplicazione(String codiceApplicazione) {
		Query query = entityManager.createQuery("SELECT COUNT(*) FROM ApplicazioneCollocazioneDto ac " + "WHERE ac.applicazioneDto.codice = :codiceApplicazione "
				+ " AND ((:data BETWEEN ac.collocazioneDto.dataInizioValidita AND ac.collocazioneDto.dataFineValidita) "
				+ " OR (:data >= ac.collocazioneDto.dataInizioValidita and ac.collocazioneDto.dataFineValidita is null)) ");

		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("data", Utils.sysdate());
		Long count = (Long) query.getSingleResult();
		return count;
	}

	@Override
	public List<ApplicazioneCollocazioneDto> findByApplicazioneAndAzienda(String codiceApplicazione,
			String codiceAzienda) {

		StringBuilder stringBuilder = new StringBuilder(
				" FROM ApplicazioneCollocazioneDto ac "
				+ " WHERE ac.applicazioneDto.codice = :codiceApplicazione "
				+ " AND ac.collocazioneDto.flagAzienda = 'S' "
				+ " AND ((now() BETWEEN ac.collocazioneDto.dataInizioValidita AND ac.collocazioneDto.dataFineValidita) "
				+ " OR (now() >= ac.collocazioneDto.dataInizioValidita and ac.collocazioneDto.dataFineValidita is null)) ");
		
		stringBuilder.append(" and ac.collocazioneDto.colCodAzienda=:codiceAzienda");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("codiceAzienda", codiceAzienda);				
			
		return query.getResultList();
		
	}
}
