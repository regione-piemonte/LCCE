/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.CatalogoLogAuditLowDao;
import it.csi.configuratorews.business.dto.CatalogoLogAuditDto;
import org.springframework.stereotype.Component;

@Component
public class CatalogoLogAuditLowDaoImpl extends CatalogoBaseLowDaoImpl<CatalogoLogAuditDto, Long>
		implements CatalogoLogAuditLowDao {

}
