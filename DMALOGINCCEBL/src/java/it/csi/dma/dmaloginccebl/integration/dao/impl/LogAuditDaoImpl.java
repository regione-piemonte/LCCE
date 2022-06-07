/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.CatalogoLogAuditLowDao;
import it.csi.dma.dmaloginccebl.business.dao.LogAuditLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoLogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogAuditDto;
import it.csi.dma.dmaloginccebl.integration.dao.LogAuditDao;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogAuditDaoImpl implements LogAuditDao{
	
	@Autowired
	CatalogoLogAuditLowDao catalogoLogAuditLowDao;
	
	@Autowired
	LogAuditLowDao logAuditLowDao;
	
	/*
	 * Inserisce le informazioni contenute nell'oggetto in entrata
	 * tabella auth_l_log_audit
	 * Recupera dal codice inserito la descrizione del log
	 * 
	 */
	@Override
	public void log(LogAuditDto auditDto, String codiceLog, Object... params) {
		LogAuditDto logAudit = new LogAuditDto();
		if(auditDto != null) {
			copyPropertiesLog(auditDto, logAudit);
			logAudit.setCodiceLog(codiceLog);
			//Recupero descrizione log
			logAudit.setCatalogoLogAuditDto(
					Utils.getFirstRecord(catalogoLogAuditLowDao.findByCodice(new CatalogoLogAuditDto(), codiceLog)));
			
			//Se ho trovato il codice nel catalogo prendo la descrizione
			//Se non dovesse esserci metto una descrizione di default e vado avanti
			String descrizioneLog = null;
			if(logAudit.getCatalogoLogAuditDto() != null &&
					logAudit.getCatalogoLogAuditDto().getDescrizione() != null) {
				descrizioneLog = logAudit.getCatalogoLogAuditDto().getDescrizione();
			}else {
				descrizioneLog = "...";
			}
			//Insersco la descrizione trovata e sostituisco eventuali parametri
			logAudit.setInformazioniTracciate(String.format(descrizioneLog, params));
			
			//Inserisco Log completo di descrizione
			logAuditLowDao.insert(logAudit);
		}
	}

	private void copyPropertiesLog(LogAuditDto auditDto, LogAuditDto logAudit) {
		logAudit.setIpRichiedente(auditDto.getIpRichiedente());
		logAudit.setCfAssistito(auditDto.getCfAssistito());
		logAudit.setCfRichiedente(auditDto.getCfRichiedente());
		logAudit.setMessaggiDto(auditDto.getMessaggiDto());
		logAudit.setServiziDto(auditDto.getServiziDto());
		logAudit.setToken(auditDto.getToken());
		logAudit.setAbilitazioneDto(auditDto.getAbilitazioneDto());
		logAudit.setApplicazioneDto(auditDto.getApplicazioneDto());
		logAudit.setRuoloDto(auditDto.getRuoloDto());
		logAudit.setUtenteDto(auditDto.getUtenteDto());
	}
}
