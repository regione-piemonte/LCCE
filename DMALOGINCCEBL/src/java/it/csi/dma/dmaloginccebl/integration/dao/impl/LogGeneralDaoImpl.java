/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.CatalogoLogAuditLowDao;
import it.csi.dma.dmaloginccebl.business.dao.CatalogoLogLowDao;
import it.csi.dma.dmaloginccebl.business.dao.MessaggiErroreLowDao;
import it.csi.dma.dmaloginccebl.business.dao.MessaggiLowDao;
import it.csi.dma.dmaloginccebl.business.dao.MessaggiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziRichiamatiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoLogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoLogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiErroreDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziRichiamatiXmlDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogAuditDao;
import it.csi.dma.dmaloginccebl.integration.dao.LogDao;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class LogGeneralDaoImpl implements LogGeneralDao{
	
	private static final String LCCE = "LCCE";

	private static final String BLOCCANTE = "Bloccante";

	@Autowired
	ServiziLowDao serviziLowDao;
	
	@Autowired
	LogDao logDao;
	
	@Autowired
	LogAuditDao logAuditDao;
	
	@Autowired
	MessaggiLowDao messaggiLowDao;
	
	@Autowired
	MessaggiXmlLowDao messaggiXmlLowDao;
	
	@Autowired
	CatalogoLogAuditLowDao catalogoLogAuditLowDao;

	@Autowired
	CatalogoLogLowDao catalogoLogLowDao;

	@Autowired
	MessaggiErroreLowDao messaggiErroreLowDao;

	@Autowired
	ServiziRichiamatiXmlLowDao serviziRichiamatiXmlLowDao;

	/*
	 * (non-Javadoc)
	 * @see it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao#logStart(it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean, java.lang.Object[])
	 * Inserisco nelle tabelle di Log le informazioni passate in input
	 * I codici utilizzati per ritrovare le informazioni da tracciare sono predefiniti
	 */
	@Override
	public void logStart(LogGeneralDaoBean logGeneralDaoBean, Object... params) {

		logGeneralDaoBean.setMessaggiDto(messaggiLowDao.insert(logGeneralDaoBean.getMessaggiDto()));

		LogDto logDto = getLogDto(logGeneralDaoBean, logGeneralDaoBean.getMessaggiDto());
		logDao.log(logDto, CatalogoLog.LOG_START.getValue(), params);

		logGeneralDaoBean.getLogAuditDto().setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
		
		MessaggiXmlDto messaggiXmlDto = logGeneralDaoBean.getMessaggiXmlDto();
		messaggiXmlDto.setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
		logGeneralDaoBean.setMessaggiXmlDto(messaggiXmlLowDao.insert(messaggiXmlDto)); //TODO creare nuova insert/update per cifrare gli xml
	}

	@Override
	public void logEnd(LogGeneralDaoBean logGeneralDaoBean, AbilitazioneDto abilitazioneDto,
					   ServiceResponse response, String token, String xmlOut, Object... params) {

		MessaggiDto messaggiDto = getMessaggiDtoLogEnd(logGeneralDaoBean, abilitazioneDto, response.getEsito().getValue(), token);

		messaggiLowDao.update(messaggiDto);


		LogDto logDto = getLogDto(logGeneralDaoBean, messaggiDto);
		logDao.log(logDto, CatalogoLog.LOG_END.getValue(), params);

		MessaggiXmlDto messaggiXmlDto = getMessaggiXmlDtoLogEnd(logGeneralDaoBean, xmlOut);

		messaggiXmlLowDao.update(messaggiXmlDto);

		if (response.getErrori() != null) {
			for (Errore errore : response.getErrori()) {

				if (Constants.ERRORE_SERIVIZIO_ESTERNO.equals(errore.getRiferimento())) {
					errore.setCodice(CatalogoLog.ERRORE_SERVIZIO_ESTERNO.getValue());
				}

				MessaggiErroreDto messaggiErroreDto = getMessaggiErroreDto(messaggiDto, errore);
				messaggiErroreLowDao.insert(messaggiErroreDto);
			}
		}
	}

	private MessaggiErroreDto getMessaggiErroreDto(MessaggiDto messaggiDto, Errore errore) {
		MessaggiErroreDto messaggiErroreDto = new MessaggiErroreDto();
		CatalogoLogDto catalogoLogDto = new CatalogoLogDto();
		catalogoLogDto = Utils.getFirstRecord(catalogoLogLowDao.findByCodice(catalogoLogDto, errore.getCodice()));
		messaggiErroreDto.setCatalogoLogDto(catalogoLogDto);
		messaggiErroreDto.setCodiceErrore(errore.getCodice());
		messaggiErroreDto.setControllore(LCCE);
		messaggiErroreDto.setDescrizioneErrore(errore.getDescrizione());
		messaggiErroreDto.setMessaggiDto(messaggiDto);
		messaggiErroreDto.setTipoErrore(BLOCCANTE);
		return messaggiErroreDto;
	}

	@Override
	public Errore logErrore(LogDto logDto, String codiceErrore, Object... parametri){
		return logDao.logErrore(logDto,codiceErrore,parametri);
	}

	@Override
	public void logAudit(LogAuditDto auditDto, ApplicazioneDto applicazioneDto,
						 AbilitazioneDto abilitazioneDto, UtenteDto utenteDto,
						 RuoloDto ruoloDto, String token, String codiceLog, Object... params){
		auditDto.setCfRichiedente(utenteDto.getCodiceFiscale());
		auditDto.setApplicazioneDto(applicazioneDto);
		auditDto.setAbilitazioneDto(abilitazioneDto);
		auditDto.setUtenteDto(utenteDto);
		auditDto.setRuoloDto(ruoloDto);
		auditDto.setToken(token);
		logAuditDao.log(auditDto, codiceLog, params);
	}
	
	@Override
	public void logXml(MessaggiDto messaggiDto, String xmlIn, String xmlOut){
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		messaggiXmlDto.setMessaggiDto(messaggiDto);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.getBytes() : null);
		messaggiXmlDto.setXmlOut(xmlOut != null ? xmlOut.getBytes() : null);

		messaggiXmlLowDao.insert(messaggiXmlDto);
	}

	@Override
	public void registraXmlServiziRichiamati(LogGeneralDaoBean logGeneralDaoBean, String xmlIn, String xmlOut,
											 ServiziDto serviziDto, String esito) {
		ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = getServiziRichiamatiXmlDto(logGeneralDaoBean, xmlIn, xmlOut,
				serviziDto, esito);

		serviziRichiamatiXmlLowDao.insert(serviziRichiamatiXmlDto);
	}

	private ServiziRichiamatiXmlDto getServiziRichiamatiXmlDto(LogGeneralDaoBean logGeneralDaoBean, String xmlIn, String xmlOut,
															  	 ServiziDto serviziDto, String esito) {
		ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = new ServiziRichiamatiXmlDto();
		serviziRichiamatiXmlDto.setDataChiamata(Utils.sysdate());
		serviziRichiamatiXmlDto.setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
		serviziRichiamatiXmlDto.setServiziDto(serviziDto);
		serviziRichiamatiXmlDto.setXmlIn(xmlIn.getBytes());
		serviziRichiamatiXmlDto.setDataRisposta(Utils.sysdate());
		serviziRichiamatiXmlDto.setEsito(esito);
		serviziRichiamatiXmlDto.setXmlOut(xmlOut != null ? xmlOut.getBytes() : null);
		return serviziRichiamatiXmlDto;
	}

	private LogDto getLogDto(LogGeneralDaoBean logGeneralDaoBean, MessaggiDto messaggiDto) {
		LogDto logDto = logGeneralDaoBean.getLogDto();
		logDto.setMessaggiDto(messaggiDto);
		return logDto;
	}

	private MessaggiXmlDto getMessaggiXmlDtoLogEnd(LogGeneralDaoBean logGeneralDaoBean, String xmlOut) {
		MessaggiXmlDto messaggiXmlDto = logGeneralDaoBean.getMessaggiXmlDto();
		messaggiXmlDto.setXmlOut(xmlOut != null ? xmlOut.getBytes() : null);
		messaggiXmlDto.setDataAggiornamento(Utils.sysdate());
		return messaggiXmlDto;
	}

	private MessaggiDto getMessaggiDtoLogEnd(LogGeneralDaoBean logGeneralDaoBean, AbilitazioneDto abilitazioneDto, String esito, String token) {
		MessaggiDto messaggiDto = logGeneralDaoBean.getMessaggiDto();
		messaggiDto.setAbilitazioneDto(abilitazioneDto);
		messaggiDto.setCodiceAbilitazione(abilitazioneDto != null ? abilitazioneDto.getCodiceAbilitazione() : null);
		messaggiDto.setDataRisposta(Utils.sysdate());
		messaggiDto.setEsito(esito);
		messaggiDto.setToken(token);
		messaggiDto.setDataAggiornamento(Utils.sysdate());
		return messaggiDto;
	}

	public CatalogoLogAuditDto getCatalogoLogAudit(String codice) {
		CatalogoLogAuditDto catalogoLogAuditDto = new CatalogoLogAuditDto();
		catalogoLogAuditDto.setCodice(codice);
		catalogoLogAuditDto = Utils.getFirstRecord(catalogoLogAuditLowDao.findByCodice(catalogoLogAuditDto));
		return catalogoLogAuditDto;
	}
}
