/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.impl;

import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.CatalogoLogAuditLowDao;
import it.csi.dma.puawa.business.dao.dto.CatalogoLogAuditDto;

@Component
public class CatalogoLogAuditLowDaoImpl extends CatalogoBaseLowDaoImpl<CatalogoLogAuditDto, Long>
		implements CatalogoLogAuditLowDao {

}
