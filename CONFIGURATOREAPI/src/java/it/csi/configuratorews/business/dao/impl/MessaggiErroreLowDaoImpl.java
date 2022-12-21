/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.MessaggiErroreLowDao;
import it.csi.configuratorews.business.dto.MessaggiDto;
import it.csi.configuratorews.business.dto.MessaggiErroreDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;

import java.math.BigInteger;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessaggiErroreLowDaoImpl extends EntityBaseLowDaoImpl<MessaggiErroreDto, Long>
		implements MessaggiErroreLowDao {

	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private String getTableName() {
		return "auth_l_messaggi_errore"; // TODO costante
	}

	private Long getNextValueSequence() {

		Query query = entityManager.createNativeQuery("SELECT nextval('seq_auth_l_messaggi_errore')"); // TODO costante
		return ((BigInteger) Utils.getFirstRecord(query.getResultList())).longValue();

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
	public MessaggiErroreDto insert(MessaggiErroreDto obj) {
		final String querystr = "INSERT INTO " + getTableName() //
				+ "(id, id_messaggio, id_catalogo_log, codice_errore, descrizione_errore, controllore, tipo_errore, data_inserimento) "//
				+ " VALUES " //
				+ " (:id, :id_messaggio, :id_catalogo_log, :codice_errore, :descrizione_errore, 'APICONF', :tipo_errore, now())";

		log.info("START - Inserimento override insert Messaggio dao");

		obj.setId(getNextValueSequence());

		Query query = entityManager.createNativeQuery(querystr);

		query.setParameter("id", obj.getId());
		query.setParameter("id_messaggio", obj.getMessaggiDto().getId());
		query.setParameter("id_catalogo_log", obj.getCatalogoLogDto().getId());
		query.setParameter("codice_errore", obj.getCodiceErrore());
		query.setParameter("descrizione_errore", obj.getDescrizioneErrore());
		query.setParameter("tipo_errore", obj.getCatalogoLogDto().getTipoErrore());

		query.executeUpdate();

		log.info("END - Inserimento override insert Messaggio dao");
		return obj;
	}

}