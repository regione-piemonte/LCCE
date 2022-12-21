/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.LogAuditLowDao;
import it.csi.configuratorews.business.dto.LogAuditDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;

import javax.persistence.Query;
import java.math.BigInteger;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;

@Component
public class LogAuditLowDaoImpl extends EntityBaseLowDaoImpl<LogAuditDto, Long> implements LogAuditLowDao {

	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private String getTableName() {
		return "AUTH_L_LOG_AUDIT"; // TODO costante
		
	}

	@Override
	public LogAuditDto insert(LogAuditDto obj) {
		log.info("START - Inserimento override insert Messaggio dao");

		obj.setId(getNextValueSequence());

		Query query = entityManager.createNativeQuery(
				"INSERT INTO " + getTableName() + " ("
						+ " ID, CODICE_LOG, INFORMAZIONI_TRACCIATE, ID_CATALOGO_LOG_AUDIT, "
						+ " CODICE_FISCALE_RICHIEDENTE, IP_RICHIEDENTE, ID_SERVIZIO, "
						+ " ID_MESSAGGIO, DATA_INSERIMENTO, "
						+ " ID_RICHIESTA, ID_SISTEMA_RICHIEDENTE"
						+ " ) VALUES ( "
						+ " :id, :codiceLog, :infoTracciate, :idCatalogLogAudit, "
						+ " :cfRichiedente, :ipRichiedente, :idServizio, "
						+ " :idMessaggio, :dataInserimento, "
						+ " :idRichiesta, :idSistemaRichiedente"
						+ " )");

		query.setParameter("id", obj.getId());
		query.setParameter("codiceLog", obj.getCodiceLog());
		query.setParameter("infoTracciate", obj.getCatalogoLogAuditDto().getDescrizione());
		query.setParameter("idCatalogLogAudit", obj.getCatalogoLogAuditDto().getId());
		query.setParameter("cfRichiedente", obj.getCfRichiedente());
		query.setParameter("ipRichiedente", obj.getIpRichiedente());
		query.setParameter("idServizio", obj.getServiziDto().getId());
		query.setParameter("idMessaggio", obj.getMessaggiDto().getId());
		query.setParameter("dataInserimento", Utils.sysdate());
		query.setParameter("idRichiesta", obj.getIdRichiesta());
		query.setParameter("idSistemaRichiedente", obj.getIdSistemaRichiedente());
		query.executeUpdate();

		log.info("END - Inserimento override insert Messaggio dao");
		return obj;
	}

	private Long getNextValueSequence() {

		Query query = entityManager.createNativeQuery("SELECT nextval('SEQ_AUTH_L_LOG_AUDIT')"); // TODO costante
		return ((BigInteger) Utils.getFirstRecord(query.getResultList())).longValue();

	}
}
