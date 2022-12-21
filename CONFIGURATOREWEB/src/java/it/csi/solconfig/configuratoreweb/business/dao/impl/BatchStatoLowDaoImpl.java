/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaUtentiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.BatchStatoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaUtentiDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchStatoDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class BatchStatoLowDaoImpl extends EntityBaseLowDaoImpl<BatchStatoDto, Long> implements BatchStatoLowDao {

	@Override
	public BatchStatoDto findByStato(String stato) {
		
		return  entityManager.createQuery(
                "SELECT r FROM BatchStatoDto r WHERE r.statoBatch=:stato",BatchStatoDto.class)
				.setParameter("stato", stato).getSingleResult();
       
    }

   
  

	
}
