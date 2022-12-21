/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.LoginDataLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.LoginDataDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class LoginDataLowDaoImpl extends EntityBaseLowDaoImpl<LoginDataDto, Long> implements LoginDataLowDao{
	
	@Override
	public List<LoginDataDto> findByToken(LoginDataDto loginDataDto) {
		
		StringBuilder queryBuilder = new StringBuilder("from " + loginDataDto.getClass().getName() +" t WHERE t.token = :token ");
        Query query = getEntityManager().createQuery(queryBuilder.toString());
        query.setParameter("token", loginDataDto.getToken());

        return query.getResultList();
	}
	
	@Override
	public void update(LoginDataDto loginDataDto) {
		log.info("START - Update loginDataDto");
		loginDataDto.setDataAggiornamento(Utils.sysdate());
		entityManager.persist(loginDataDto);
		log.info("END - Update loginDataDto");
	}
}