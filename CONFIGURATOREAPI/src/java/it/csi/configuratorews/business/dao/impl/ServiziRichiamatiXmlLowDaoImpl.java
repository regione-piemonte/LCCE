/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.ServiziRichiamatiXmlLowDao;
import it.csi.configuratorews.business.dto.ServiziRichiamatiXmlDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.math.BigInteger;

@Component
public class ServiziRichiamatiXmlLowDaoImpl extends EntityBaseLowDaoImpl<ServiziRichiamatiXmlDto, Long>
		implements ServiziRichiamatiXmlLowDao {

	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private String getTableName() {
		return "AUTH_L_XML_SERVIZI_RICHIAMATI";
	}

	@Override
	public ServiziRichiamatiXmlDto insert(ServiziRichiamatiXmlDto obj) {
		log.info("START - Inserimento override insert xml servizi richiamati dao");

		obj.setId(getNextValueSequence());

		Query query = entityManager.createNativeQuery("INSERT INTO " + getTableName()
				+ " (ID, ID_MESSAGGIO, ID_SERVIZIO, XML_IN, XML_OUT, DATA_CHIAMATA, DATA_RISPOSTA, ESITO, DATA_INSERIMENTO) VALUES "
				+ " (:id, :idMessaggio, :idServizio, pgp_sym_encrypt_bytea(:xmlIn, getGoodPwd('@encryption_key@')), pgp_sym_encrypt_bytea(:xmlOut, getGoodPwd('@encryption_key@')), :dataChiamata, :dataRisposta, :esito, :dataInserimento)");

		query.setParameter("id", obj.getId());
		query.setParameter("idServizio", obj.getServiziDto().getId());
		query.setParameter("idMessaggio", obj.getMessaggiDto().getId());
		query.setParameter("xmlIn", obj.getXmlIn());
		query.setParameter("xmlOut", obj.getXmlOut());
		query.setParameter("dataChiamata", Utils.sysdate());
		query.setParameter("dataRisposta", obj.getDataRisposta());
		query.setParameter("esito", obj.getEsito());
		query.setParameter("dataInserimento", Utils.sysdate());

		query.executeUpdate();

		log.info("END - Inserimento override insert xml servizi richiamati dao");
		return obj;
	}

	private Long getNextValueSequence() {

		Query query = entityManager.createNativeQuery("SELECT nextval('SEQ_AUTH_L_XML_SERVIZI_RICHIAMATI')");
		return ((BigInteger) Utils.getFirstRecord(query.getResultList())).longValue();
	}

	@Override
	public void update(ServiziRichiamatiXmlDto obj) {
		log.info("START - override update xml servizi richiamati dao");

		Query query = entityManager.createNativeQuery("UPDATE " + getTableName() + " SET"
				+ " XML_OUT = pgp_sym_encrypt_bytea(:xmlOut, getGoodPwd('@encryption_key@')),"
				+ " DATA_RISPOSTA = :dataRisposta, ESITO = :esito, DATA_AGGIORNAMENTO = :dataAggiornamento WHERE ID = :id");

		query.setParameter("xmlOut", obj.getXmlOut());
		query.setParameter("dataRisposta", Utils.sysdate());
		query.setParameter("esito", obj.getEsito());
		query.setParameter("dataAggiornamento", Utils.sysdate());
		query.setParameter("id", obj.getId());

		query.executeUpdate();

		log.info("END - override update xml servizi richiamati dao");
	}
}