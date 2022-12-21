/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;


import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchStatoDto;

public interface BatchStatoLowDao extends EntityBaseLowDao<BatchStatoDto, Long> {

		BatchStatoDto findByStato(String stato);

}
