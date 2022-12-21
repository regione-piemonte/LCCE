/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.integration.log.impl;

import it.csi.solconfig.configuratoreweb.business.dao.*;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoLogAuditDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.LogAuditDto;
import it.csi.solconfig.configuratoreweb.integration.log.LogDao;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LogDaoImpl implements LogDao {

	private static final String LCCE = "LCCE";

	private static final String BLOCCANTE = "Bloccante";

	@Autowired
	private CatalogoLogAuditLowDao catalogoLogAuditLowDao;

	@Autowired
	private LogAuditLowDao logAuditLowDao;

	@Autowired
	private MessaggiLowDao messaggiLowDao;

	@Autowired
	private MessaggiErroreLowDao messaggiErroreLowDao;

	@Autowired
	private CatalogoLogLowDao catalogoLogLowDao;

	@Autowired
	private ServiziRichiamatiXmlLowDao serviziRichiamatiXmlLowDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public LogAuditDto saveLogAudit(LogAuditDto logAuditDto, Object... params) {
		LogAuditDto logAudit = new LogAuditDto();
		if (logAuditDto != null) {
			logAudit = copyPropertiesLog(logAuditDto, logAudit);
			// Recupero descrizione log
			CatalogoLogAuditDto catalogoLogAuditDto = Utils.getFirstRecord(
					catalogoLogAuditLowDao.findByCodice(new CatalogoLogAuditDto(), logAuditDto.getCodiceLog()));

			logAudit.setCatalogoLogAuditDto(catalogoLogAuditDto);

			// Se ho trovato il codice nel catalogo prendo la descrizione
			// Se non dovesse esserci metto una descrizione di default e vado avanti
			String descrizioneLog = null;
			if (logAudit.getCatalogoLogAuditDto() != null
					&& logAudit.getCatalogoLogAuditDto().getDescrizione() != null) {
				descrizioneLog = logAudit.getCatalogoLogAuditDto().getDescrizione();
			} else {
				descrizioneLog = "...";
			}
			// Insersco la descrizione trovata e sostituisco eventuali parametri
			logAudit.setInformazioniTracciate(String.format(descrizioneLog, params));

			// Inserisco Log completo di descrizione
			logAudit = logAuditLowDao.insert(logAudit);
			return logAudit;
		}
		return null;
	}

//	@Override
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//	public LogGeneralDaoBean logStart(LogGeneralDaoBean logGeneralDaoBean, String serviceName, Object... params) {
//
//		logGeneralDaoBean.setMessaggiDto(messaggiLowDao.insert(logGeneralDaoBean.getMessaggiDto()));
//		if (logGeneralDaoBean.getServiziRichiamatiXmlDto() != null) {
//			ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = new ServiziRichiamatiXmlDto();
//			serviziRichiamatiXmlDto = logGeneralDaoBean.getServiziRichiamatiXmlDto();
//			serviziRichiamatiXmlDto.setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
//			serviziRichiamatiXmlDto.setDataInserimento(Timestamp.valueOf(LocalDateTime.now()));
//			serviziRichiamatiXmlDto.setDataChiamata(Timestamp.valueOf(LocalDateTime.now()));
//			serviziRichiamatiXmlDto.setDataRisposta(Timestamp.valueOf(LocalDateTime.now()));
//			logGeneralDaoBean.setServiziRichiamatiXmlDto(serviziRichiamatiXmlDto);
//			logGeneralDaoBean.setServiziRichiamatiXmlDto(
//					serviziRichiamatiXmlLowDao.insert(logGeneralDaoBean.getServiziRichiamatiXmlDto()));
//		}
//
//		return logGeneralDaoBean;
//	}
//
//	@Override
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//	public void logEnd(LogGeneralDaoBean logGeneralDaoBean, AbilitazioneDto abilitazioneDto, ServiceResponse response,
//			String xmlOut, Object... params) {
//
//		MessaggiDto messaggiDto = new MessaggiDto();
//		messaggiDto = logGeneralDaoBean.getMessaggiDto();
//		messaggiDto.setDataRisposta(Utils.sysdate());
//		messaggiDto.setDataAggiornamento(Utils.sysdate());
//		messaggiDto.setDataRicezione(Utils.sysdate());
//		if (response != null) {
//			if (response.getEsito() != null) {
//				messaggiDto.setEsito(response.getEsito().getValue());
//			}
//		} else {
//			messaggiDto.setEsito(RisultatoCodice.FALLIMENTO.getValue());
//		}
//
//		messaggiLowDao.update(messaggiDto);
//
//		ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = null;
//		if (logGeneralDaoBean.getServiziRichiamatiXmlDto() != null) {
//			serviziRichiamatiXmlDto = new ServiziRichiamatiXmlDto();
//			serviziRichiamatiXmlDto = logGeneralDaoBean.getServiziRichiamatiXmlDto();
//			serviziRichiamatiXmlDto.setMessaggiDto(messaggiDto);
//			serviziRichiamatiXmlDto.setDataAggiornamento(Utils.sysdate());
//			if (response != null) {
//				if (response.getEsito() != null) {
//					serviziRichiamatiXmlDto.setEsito(response.getEsito().getValue());
//				}
//			}
//			serviziRichiamatiXmlDto.setXmlOut(xmlOut.getBytes());
//			serviziRichiamatiXmlDto.setDataRisposta(Utils.sysdate());
//			serviziRichiamatiXmlLowDao.update(logGeneralDaoBean.getServiziRichiamatiXmlDto());
//		}
//
//		MessaggiErroreDto messaggiErroreDto = null;
//		if (response.getErrori() != null && !response.getErrori().isEmpty()) {
//			for (Errore errore : response.getErrori()) {
//				if (errore != null) {
//					messaggiErroreDto = new MessaggiErroreDto();
//					if (errore.getRiferimento() != null && !errore.getRiferimento().isEmpty()) {
//						if (errore.getRiferimento().equals("ERRORE_SERIVIZIO_ESTERNO")) {
//							errore.setCodice(CatalogoLog.ERRORE_SERVIZIO_ESTERNO.getValue());
//						}
//					}
//					if (errore.getDescrizione() != null && !errore.getDescrizione().isEmpty()) {
//						messaggiErroreDto.setDescrizioneErrore(errore.getDescrizione());
//					}
//					messaggiErroreDto.setCatalogoLogDto(Utils
//							.getFirstRecord(catalogoLogLowDao.findByCodice(new CatalogoLogDto(), errore.getCodice())));
//					messaggiErroreDto.setCodiceErrore(errore.getCodice());
//					messaggiErroreDto.setControllore(LCCE);
//					messaggiErroreDto.setMessaggiDto(messaggiDto);
//					messaggiErroreDto.setTipoErrore(BLOCCANTE);
//					messaggiErroreLowDao.insert(messaggiErroreDto);
//				}
//			}
//		}
//	}
//
//	@Override
//	public Errore getErrore(String codiceErrore, Object... params){
//		CatalogoLogDto catalogoLogDto =
//				Utils.getFirstRecord(catalogoLogLowDao.findByCodice(new CatalogoLogDto(), codiceErrore));
//		Errore errore = new Errore();
//		errore.setCodice(codiceErrore);
//		if(catalogoLogDto.getDescrizioneLog() != null){
//			errore.setDescrizione(String.format(catalogoLogDto.getDescrizioneLog(), params));
//		}else{
//			errore.setDescrizione("...");
//		}
//		return errore;
//	}

	private LogAuditDto copyPropertiesLog(LogAuditDto auditDto, LogAuditDto logAudit) {
		logAudit.setId(auditDto.getId());
		logAudit.setCodiceCollocazione(auditDto.getCodiceCollocazione());
		logAudit.setColCodAzienda(auditDto.getColCodAzienda());
		logAudit.setCodiceLog(auditDto.getCodiceLog());
		logAudit.setDataInserimento(auditDto.getDataInserimento());
		logAudit.setInformazioniTracciate(auditDto.getInformazioniTracciate());
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
		logAudit.setCatalogoLogAuditDto(auditDto.getCatalogoLogAuditDto());
		return logAudit;
	}
}
