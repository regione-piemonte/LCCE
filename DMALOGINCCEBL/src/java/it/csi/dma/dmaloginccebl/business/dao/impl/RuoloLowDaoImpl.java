/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.RuoloLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloDto;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Collection;

@Component
public class RuoloLowDaoImpl extends CatalogoBaseLowDaoImpl<RuoloDto, Long> implements RuoloLowDao{

	@Override
	public Collection<RuoloDto> findByUtenteCodiceFiscale(String codiceFiscale) {
		Query query=entityManager.createQuery("SELECT r FROM RuoloDto r, RuoloUtenteDto ru, UtenteDto u " +
				" WHERE r.id=ru.ruoloDto.id AND u.id=ru.utenteDto.id AND u.codiceFiscale=:codiceFiscale " +
				"  AND ((:data > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (:data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) " +
				" AND (r.visibilitaConf is null OR r.visibilitaConf <> :visibileConf)");
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("data", Utils.sysdate());
		query.setParameter("visibileConf", "N");
		return query.getResultList();
	}
}
