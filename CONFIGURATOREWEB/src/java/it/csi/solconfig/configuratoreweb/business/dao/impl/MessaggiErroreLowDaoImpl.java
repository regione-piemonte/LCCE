/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.MessaggiErroreLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiErroreDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;

@Component
public class MessaggiErroreLowDaoImpl extends EntityBaseLowDaoImpl<MessaggiErroreDto, Long> implements MessaggiErroreLowDao{

	@Override
	public MessaggiUtenteDto ricercaMessaggiErroreByCod(String messaggio) {
		return entityManager.createQuery("Select m FROM MessaggiUtenteDto m "
				+ " WHERE m.codice=:codice",MessaggiUtenteDto.class)
				.setParameter("codice", messaggio)
				.getSingleResult();
			
	}
	
	
}