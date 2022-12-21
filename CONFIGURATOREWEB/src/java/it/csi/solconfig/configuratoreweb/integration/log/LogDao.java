/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.integration.log;

import it.csi.solconfig.configuratoreweb.business.dao.dto.LogAuditDto;

public interface LogDao {

	public LogAuditDto saveLogAudit(LogAuditDto logAuditDto, Object... params);

}
