/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.RuoloProfiloLowDao;
import it.csi.configuratorews.business.dto.RuoloProfiloDto;

@Component
public class RuoloProfiloLowDaoImpl extends EntityBaseLowDaoImpl<RuoloProfiloDto, Long> implements RuoloProfiloLowDao {

	@Override
	public List<RuoloProfiloDto> findByProfiloAndApplicazione(String codiceFunzionalita, String codiceApplicazione) {

		Query query = entityManager.createQuery("SELECT rp FROM RuoloProfiloDto rp " 
				+ " WHERE rp.funzionalitaDto.codiceFunzione = :codiceFunzionalita "
				+ " AND rp.funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = 'PROF' "
				+ " AND rp.funzionalitaDto.applicazioneDto.codice = :codiceApplicazione "
				+ " AND rp.dataInizioValidita < now() and (rp.dataFineValidita > now() or rp.dataFineValidita is null)");
		
		query.setParameter("codiceFunzionalita", codiceFunzionalita);
		query.setParameter("codiceApplicazione", codiceApplicazione);
		
		return query.getResultList();

	}

}
