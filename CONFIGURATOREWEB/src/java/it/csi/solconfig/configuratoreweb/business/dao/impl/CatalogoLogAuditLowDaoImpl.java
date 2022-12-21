/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.CatalogoLogAuditLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoLogAuditDto;

@Component
public class CatalogoLogAuditLowDaoImpl extends CatalogoBaseLowDaoImpl<CatalogoLogAuditDto, Long>
		implements CatalogoLogAuditLowDao {

}
