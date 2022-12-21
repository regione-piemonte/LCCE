/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.CsiLogAuditLowDao;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.util.Utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CsiLogAuditLowDaoImpl extends EntityBaseLowDaoImpl<CsiLogAuditDto, Long> implements CsiLogAuditLowDao {
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public CsiLogAuditDto insert(CsiLogAuditDto obj) {
		log.info("START - Inserimento base dao");
		obj.setDataInserimento(Utils.sysdate());
		entityManager.persist(obj);
		log.info("END - Inserimento base dao");
		entityManager.flush();
		return obj;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor=Exception.class)
	public void update(CsiLogAuditDto obj) {
		entityManager.flush();
	}

}
