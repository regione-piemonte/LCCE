/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.UtenteCollocazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteCollocazioneDto;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class UtenteCollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<UtenteCollocazioneDto, Long> implements UtenteCollocazioneLowDao{

	@Override
	public Collection<UtenteCollocazioneDto> findByUtenteAndValidita(UtenteCollocazioneDto obj) {
		
		Query query=entityManager.createQuery("FROM UtenteCollocazioneDto uc WHERE uc.utenteDto.id=:id " +
				" ((:data > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (:data BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		query.setParameter("id", obj.getUtenteDto().getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public Collection<UtenteCollocazioneDto> findByUtenteAndCollocazioneAndValidita(UtenteCollocazioneDto obj) {

		Query query=entityManager.createQuery("FROM UtenteCollocazioneDto uc WHERE uc.utenteDto.id=:id AND uc.collocazioneDto.colId = :idCollocazione " +
				" AND ((:data > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (:data BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		query.setParameter("id", obj.getUtenteDto().getId());
		query.setParameter("idCollocazione", obj.getCollocazioneDto().getColId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public Collection<UtenteCollocazioneDto> findByListId(List<Long> idList) {
		Query query=entityManager.createQuery("FROM UtenteCollocazioneDto uc WHERE uc.id IN :idList");
		query.setParameter("idList", idList);
		return query.getResultList();
	}
}