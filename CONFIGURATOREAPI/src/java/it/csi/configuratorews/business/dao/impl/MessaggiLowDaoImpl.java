/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.MessaggiLowDao;
import it.csi.configuratorews.business.dto.MessaggiDto;
import it.csi.configuratorews.business.dto.MessaggiXmlDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;

import javax.persistence.Query;
import java.math.BigInteger;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessaggiLowDaoImpl extends EntityBaseLowDaoImpl<MessaggiDto, Long> implements MessaggiLowDao {

	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private String getTableName() {
		return "AUTH_L_MESSAGGI"; // TODO costante
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
	public MessaggiDto insert(MessaggiDto obj) {
		final String querystr = "INSERT INTO " + getTableName() + " ("
				+ " ID,  ID_SERVIZIO, CF_RICHIEDENTE, CLIENT_IP, DATA_RICEZIONE, DATA_INSERIMENTO, request_uri) "//
				+ " VALUES " //
				+ " (:id, :idServizio, :cfRichiedente, :clientIp, :dataRicezione, :dataInserimento, :request_uri)";

		log.info("START - Inserimento override insert Messaggio dao");

		obj.setId(getNextValueSequence());

		Query query = entityManager.createNativeQuery(querystr);

		query.setParameter("id", obj.getId());
		query.setParameter("idServizio", obj.getServiziDto());
		query.setParameter("cfRichiedente", obj.getCfRichiedente());
		query.setParameter("clientIp", obj.getClientIp());
		query.setParameter("dataRicezione", obj.getDataRicezione());
		query.setParameter("dataInserimento", Utils.sysdate());
		query.setParameter("request_uri", obj.getRequestUri());

		query.executeUpdate();

		log.info("END - Inserimento override insert Messaggio dao");
		return obj;
	}

	private Long getNextValueSequence() {

		Query query = entityManager.createNativeQuery("SELECT nextval('SEQ_AUTH_L_MESSAGGI')"); // TODO costante
		return ((BigInteger) Utils.getFirstRecord(query.getResultList())).longValue();

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
	public void update(MessaggiDto obj) {
		log.info("START - override update xml dao");

		Query query = entityManager.createNativeQuery("UPDATE " + getTableName() + " SET"
				+ " esito=:esito, DATA_AGGIORNAMENTO = :dataAggiornamento, data_risposta=:dataRisposta WHERE ID = :id");

		query.setParameter("esito", obj.getEsito());
		query.setParameter("dataAggiornamento", Utils.sysdate());
		query.setParameter("dataRisposta", Utils.sysdate());
		query.setParameter("id", obj.getId());

		query.executeUpdate();

		log.info("END - override update xml dao");
	}

}