/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.AbilitazioneLowDao;
import it.csi.dma.puawa.business.dao.dto.AbilitazioneDto;
import it.csi.dma.puawa.business.dao.dto.ApplicazioneDto;
import it.csi.dma.puawa.business.dao.dto.RuoloUtenteDto;
import it.csi.dma.puawa.util.Utils;

@Component
public class AbilitazioneLowDaoImpl extends EntityBaseLowDaoImpl<AbilitazioneDto, Long> implements AbilitazioneLowDao {

	@Override
	public Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(RuoloUtenteDto ruoloUtenteDto,
			ApplicazioneDto applicazioneDto) {

		AbilitazioneDto abilitazioneDto = new AbilitazioneDto();
		Query query = entityManager.createQuery("from " + abilitazioneDto.getClass().getName()
				+ " a WHERE a.ruoloUtenteDto.id =:idRuoloUtente AND a.applicazioneDto.id = :idApplicazione AND ((:data BETWEEN a.dataInizioValidita AND a.dataFineValidita) OR (a.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00')))");
		query.setParameter("idRuoloUtente", ruoloUtenteDto.getId());
		query.setParameter("idApplicazione", applicazioneDto.getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(AbilitazioneDto abilitazioneDto) {
		Query query = entityManager.createQuery("from " + abilitazioneDto.getClass().getName()
				+ " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente "
				+ "AND t.applicazioneDto.id = :idApplicazione AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (t.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
		query.setParameter("idRuoloUtente", abilitazioneDto.getRuoloUtenteDto().getId());
		query.setParameter("idApplicazione", abilitazioneDto.getApplicazioneDto().getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}
}