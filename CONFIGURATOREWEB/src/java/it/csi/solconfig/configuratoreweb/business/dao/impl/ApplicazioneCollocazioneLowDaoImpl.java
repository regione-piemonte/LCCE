/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneCollocazioneDto;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicazioneCollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<ApplicazioneCollocazioneDto, Long>
		implements ApplicazioneCollocazioneLowDao {

	@Override
	public List<Long> findIdCollocazioniByIdApplicazione(Long idApplicazione) {
		return entityManager
				.createQuery("SELECT ac.collocazioneDto.colId " + "FROM ApplicazioneCollocazioneDto ac "
						+ "WHERE ac.applicazioneDto.id = :idApplicazione", Long.class)
				.setParameter("idApplicazione", idApplicazione).getResultList();
	}

	@Override
	public List<ApplicazioneCollocazioneDto> findByIdCollocazioneAndDateValidita(Long colId) {
		Query query = entityManager.createQuery(" FROM ApplicazioneCollocazioneDto ac "
				+ "WHERE ac.collocazioneDto.colId = :colId "
				+ " AND ((:data BETWEEN ac.collocazioneDto.dataInizioValidita AND ac.collocazioneDto.dataFineValidita) "
				+ " OR (:data >= ac.collocazioneDto.dataInizioValidita and ac.collocazioneDto.dataFineValidita is null)) ");

		query.setParameter("colId", colId);
		query.setParameter("data", Utils.sysdate());

		return query.getResultList();
	}

	@Override
	public void insert(Long idApplicazione, long idCollocazione) {
		Query query = entityManager.createNativeQuery(
				"INSERT INTO auth_r_applicazione_collocazione (id, id_coll, id_app, data_inserimento)"
						+ " VALUES (nextval('auth_r_applicazione_collocazione_id_seq') , :id_coll, :id_app, now())");

		query.setParameter("id_coll", idCollocazione);
		query.setParameter("id_app", idApplicazione);

		query.executeUpdate();

	}

	@Override
	public void deleteById(long idapp, List<String> collocazioniToDel) {
		if (collocazioniToDel != null && collocazioniToDel.size() > 0) {
			List<Long> data = collocazioniToDel.stream().map(Long::parseLong).collect(Collectors.toList());
			Query query = entityManager.createNativeQuery(
					"delete from auth_r_applicazione_collocazione where id_app=:id_app and id_coll in (:id_coll)");
			query.setParameter("id_app", idapp);
			query.setParameter("id_coll", data);
			query.executeUpdate();
		}
	}

}
