/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.math.BigInteger;

import javax.persistence.Query;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ServiziRichiamatiXmlDto;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.ServiziRichiamatiXmlLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.util.Utils;

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

	@Override
	public ServiziRichiamatiXmlDto inserisciCompleto(ServiziRichiamatiXmlDto sRXmlDto) {
		log.info("START - Inserimento override insert xml servizi richiamati dao");

		sRXmlDto.setId(getNextValueSequence());
		
		Query query = entityManager.createNativeQuery("INSERT INTO " + getTableName()
				+ " (ID, ID_MESSAGGIO, ID_SERVIZIO, XML_IN, XML_OUT, DATA_CHIAMATA, DATA_RISPOSTA, ESITO, DATA_INSERIMENTO, DATA_AGGIORNAMENTO) VALUES "
				+ " (:id, :idMessaggio, :idServizio, pgp_sym_encrypt_bytea(:xmlIn , getGoodPwd('@encryption_key@')), pgp_sym_encrypt_bytea(:xmlOut , getGoodPwd('@encryption_key@') ), "
				+ ":dataChiamata, :dataRisposta, :esito, :dataInserimento, :dataAggiornamento)");

		query.setParameter("id", sRXmlDto.getId());
		query.setParameter("idServizio", sRXmlDto.getServiziDto().getId());
		query.setParameter("idMessaggio", sRXmlDto.getMessaggiDto().getId());
		query.setParameter("xmlIn", sRXmlDto.getXmlIn());
		query.setParameter("xmlOut", sRXmlDto.getXmlOut());
		query.setParameter("dataChiamata", sRXmlDto.getDataChiamata());
		query.setParameter("dataRisposta", sRXmlDto.getDataRisposta());
		query.setParameter("esito", sRXmlDto.getEsito());
		query.setParameter("dataInserimento", sRXmlDto.getDataInserimento());
		query.setParameter("dataAggiornamento", sRXmlDto.getDataAggiornamento());
		
		query.executeUpdate();

		log.info("END - Inserimento override insert xml servizi richiamati dao");
		return sRXmlDto;
	}
}