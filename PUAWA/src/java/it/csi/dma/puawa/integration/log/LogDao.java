/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.log;

import it.csi.dma.puawa.business.dao.dto.AbilitazioneDto;
import it.csi.dma.puawa.business.dao.dto.LogAuditDto;
import it.csi.dma.puawa.integration.reports.common.Errore;
import it.csi.dma.puawa.integration.reports.common.ServiceResponse;

public interface LogDao {

	public LogAuditDto saveLogAudit(LogAuditDto logAuditDto, Object... params);

	public LogGeneralDaoBean logStart(LogGeneralDaoBean logGeneralDaoBean, String serviceName, Object... params);

	public void logEnd(LogGeneralDaoBean logGeneralDaoBean, AbilitazioneDto abilitazioneDto, ServiceResponse response,
			String xmlOut, Object... params);

	Errore getErrore(String codiceErrore, Object... params);
}
