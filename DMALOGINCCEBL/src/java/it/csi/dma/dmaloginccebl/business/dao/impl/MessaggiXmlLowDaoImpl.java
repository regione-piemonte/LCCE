/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import java.math.BigInteger;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import it.csi.dma.dmaloginccebl.business.dao.MessaggiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component("messaggiXmlLowDaoImpl")
public class MessaggiXmlLowDaoImpl extends EntityBaseLowDaoImpl<MessaggiXmlDto, Long> implements MessaggiXmlLowDao{


    Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    private String getTableName(){
        return "AUTH_L_XML_MESSAGGI"; //TODO costante
    }

    @Override
    public MessaggiXmlDto insert(MessaggiXmlDto obj) {
        log.info("START - Inserimento override insert xml dao");

        obj.setId(getNextValueSequence());

        Query query = entityManager.createNativeQuery("INSERT INTO "+getTableName()+
                " (ID, XML_IN, DATA_INSERIMENTO) VALUES " +
                " (:id, pgp_sym_encrypt_bytea(:xmlIn, '@encryption_key@'), NOW())");

        query.setParameter("id", obj.getId());
        query.setParameter("xmlIn", obj.getXmlIn());

        query.executeUpdate();

        log.info("END - Inserimento override insert xml dao");
        return obj;
    }

    private Long getNextValueSequence(){

        Query query = entityManager.createNativeQuery("SELECT nextval('SEQ_AUTH_L_XML_MESSAGGI')"); //TODO costante
        return ((BigInteger) Utils.getFirstRecord(query.getResultList())).longValue();

    }

    @Override
    public void update(MessaggiXmlDto obj) {
        log.info("START - override update xml dao");


        Query query = entityManager.createNativeQuery("UPDATE "+getTableName()+" SET" +
                " XML_OUT = pgp_sym_encrypt_bytea(:xmlOut, '@encryption_key@'), DATA_AGGIORNAMENTO = NOW() WHERE ID = :id");

        query.setParameter("xmlOut", obj.getXmlOut());
        query.setParameter("id", obj.getId());

        query.executeUpdate();

        log.info("END - override update xml dao");
    }

	@Override
	public void updateIdMessaggio(MessaggiXmlDto obj) {
        log.info("START - override updateIdMessaggio xml dao");


        Query query = entityManager.createNativeQuery("UPDATE "+getTableName()+" SET" +
                " ID_MESSAGGIO = :idMessaggio, DATA_AGGIORNAMENTO = NOW() WHERE ID = :id");

        query.setParameter("idMessaggio", obj.getMessaggiDto().getId());
        query.setParameter("id", obj.getId());

        query.executeUpdate();

        log.info("END - override updateIdMessaggio xml dao");
		
	}

}
