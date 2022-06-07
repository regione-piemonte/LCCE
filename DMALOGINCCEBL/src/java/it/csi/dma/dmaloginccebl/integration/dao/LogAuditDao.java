/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.LogAuditDto;

public interface LogAuditDao {

	void log(LogAuditDto auditDto, String codiceLog, Object... params);
}
