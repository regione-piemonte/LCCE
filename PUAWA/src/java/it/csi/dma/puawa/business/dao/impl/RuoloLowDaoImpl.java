/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.RuoloLowDao;
import it.csi.dma.puawa.business.dao.dto.RuoloDto;
import it.csi.dma.puawa.util.Utils;

@Component
public class RuoloLowDaoImpl extends CatalogoBaseLowDaoImpl<RuoloDto, Long> implements RuoloLowDao {

	@Override
	public Collection<RuoloDto> findByUtenteCodiceFiscale(String codiceFiscale) {
		Query query = entityManager.createQuery(
				"SELECT r FROM RuoloDto r, RuoloUtenteDto ru, UtenteDto u WHERE r.id=ru.ruoloDto.id AND u.id=ru.utenteDto.id AND u.codiceFiscale=:codiceFiscale AND :data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita");
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}
}