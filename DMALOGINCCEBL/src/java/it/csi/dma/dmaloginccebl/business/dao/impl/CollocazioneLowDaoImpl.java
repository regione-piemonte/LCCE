/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.CollocazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.CollocazioneDto;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class CollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<CollocazioneDto, Long> implements CollocazioneLowDao {

	@Override
	public List<CollocazioneDto> findByFilterAndDF(String codiceCollocazione, String codiceAzienda) {
		Query query = entityManager.createQuery("from CollocazioneDto t " + //
				" WHERE t.colCodice = :codiceCollocazione" + //
				" and t.colCodAzienda = :codiceAzienda " + //
				" and dataCancellazione is null " + //
				" and (t.dataFineValidita is null or (:data BETWEEN t.dataInizioValidita AND t.dataFineValidita)) ");
		query.setParameter("codiceCollocazione", codiceCollocazione);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

}
