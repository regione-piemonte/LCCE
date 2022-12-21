/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiErroreDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;

public interface MessaggiErroreLowDao extends EntityBaseLowDao<MessaggiErroreDto, Long> {

	 public MessaggiUtenteDto ricercaMessaggiErroreByCod(String messaggio);
}
