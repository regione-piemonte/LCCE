/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.log.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.dao.CatalogoLogAuditConfLowDao;
import it.csi.configuratorews.business.dao.CatalogoLogAuditLowDao;
import it.csi.configuratorews.business.dao.LogAuditLowDao;
import it.csi.configuratorews.business.dao.MessaggiLowDao;
import it.csi.configuratorews.business.dao.ServiziLowDao;
import it.csi.configuratorews.business.dao.SistemiRichiedentiLowDao;
import it.csi.configuratorews.business.dto.CatalogoLogAuditDto;
import it.csi.configuratorews.business.dto.LogAuditDto;
import it.csi.configuratorews.business.dto.MessaggiDto;
import it.csi.configuratorews.business.dto.ServiziDto;
import it.csi.configuratorews.business.dto.SistemiRichiedentiDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
public class LogAuditServiceImpl implements LogAuditService {

	Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private static final String CODICE_PRODOTTO = "CONFIGURATORE";
	private static final String CODICE_LINEA_CLIENTE = "RP-01";

	@Autowired
	protected LogAuditLowDao logAuditLowDao;

	@Autowired
	protected CatalogoLogAuditLowDao catalogoLogAuditLowDao;

	@Autowired
	protected ServiziLowDao serviziLowDao;

	@Autowired
	protected MessaggiLowDao messaggiLowDao;

	@Autowired
	protected SistemiRichiedentiLowDao sistemiRichiedentiLowDao;

	@Value("${codiceAmbiente:AmbienteDefault}")
	String codiceAmbiente;
	@Value("${codiceUnitaInstallazione:UnitaDefault}")
	String codiceUnitaInstallazione;

	@Override
	public boolean insertLogAudit(HttpServletRequest httpRequest, String codiceLog, String shibIdentitaCodiceFiscale, String codSer,
			String xForwardedFor, String xRequestID, String xCodiceServizio) {

		LogAuditDto logAuditDto = new LogAuditDto();

		try {

			logAuditDto.setDataInserimento(Utils.sysdate());
			if (codiceLog != null && !codiceLog.isEmpty()) {
				CatalogoLogAuditDto catalogoLogAuditLowDto = new CatalogoLogAuditDto();
				catalogoLogAuditLowDto.setCodice(codiceLog);
				catalogoLogAuditLowDto = Utils
						.getFirstRecord(catalogoLogAuditLowDao.findByCodice(catalogoLogAuditLowDto));

				if (catalogoLogAuditLowDto != null) {
					logAuditDto.setCodiceLog(codiceLog);
					logAuditDto.setInformazioniTracciate(catalogoLogAuditLowDto.getDescrizione());
					logAuditDto.setCatalogoLogAuditDto(catalogoLogAuditLowDto);
				} else {
					log.error("Errore codiceLog non trovato in anagrafica.");
					return false;
				}
			} else {
				log.error("Errore codiceLog non passato correttamente.");
				return false;
			}
			logAuditDto.setCfRichiedente(shibIdentitaCodiceFiscale);
			logAuditDto.setIpRichiedente(xForwardedFor);
			if (codSer != null && !codSer.isEmpty()) {
				ServiziDto serviziDto = new ServiziDto();
				serviziDto.setCodice(codSer);
				serviziDto = Utils.getFirstRecord(serviziLowDao.findByCodice(serviziDto));

				if (serviziDto != null) {
					logAuditDto.setServiziDto(serviziDto);
				} else {
					log.error("Errore codiceServizio non trovato in anagrafica.");
					return false;
				}
			} else {
				log.error("Errore codiceServizio non passato correttamente.");
				return false;
			}
			Long idMsg = (Long) httpRequest.getAttribute(Constants.IDMSG);
			if (idMsg != null) {

				MessaggiDto msgDto = new MessaggiDto();
				msgDto.setId(idMsg);
				msgDto = Utils.getFirstRecord(messaggiLowDao.findByFilter(msgDto));
				logAuditDto.setMessaggiDto(msgDto);
			} else {
				log.error("Errore codiceMessaggio non trovato in sessione.");
				return false;
			}
			logAuditDto.setIdRichiesta(xRequestID);
			if (xCodiceServizio != null && !xCodiceServizio.isEmpty()) {
				SistemiRichiedentiDto sistemiRichiedentiDto = new SistemiRichiedentiDto();
				sistemiRichiedentiDto.setCodice(xCodiceServizio);
				sistemiRichiedentiDto = Utils.getFirstRecord(sistemiRichiedentiLowDao.findByFilter(sistemiRichiedentiDto));

				if (sistemiRichiedentiDto != null) {
					logAuditDto.setIdSistemaRichiedente(sistemiRichiedentiDto.getId());
				} else {
					log.error("Errore xCodiceServizio Sistema Richiedente non trovato in anagrafica.");
					return false;
				}
			} else {
				log.error("Errore xCodiceServizio Sistema Richiedente non passato correttamente.");
				return false;
			}
			logAuditDto = logAuditLowDao.insert(logAuditDto);
		} catch (Exception e) {
			log.error("Errore durante la scrittura dei log di Audit: ", e);
		}

		return true;
	}

	/**
	 * @return idApplicazione
	 */
	public String generaIdApp(String codiceAmbiente, String codiceUnitaInstallazione) {
		return CODICE_PRODOTTO + "_" + CODICE_LINEA_CLIENTE + "_" + codiceAmbiente + "_" + codiceUnitaInstallazione;
	}
}
