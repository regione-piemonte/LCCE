/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.controller;

import javax.servlet.http.HttpSession;

import it.csi.dma.puawa.business.dao.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.puawa.business.dao.ApplicazioneLowDao;
import it.csi.dma.puawa.business.dao.RuoloLowDao;
import it.csi.dma.puawa.business.dao.UtenteLowDao;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.integration.log.LogDao;
import it.csi.dma.puawa.presentation.constants.ConstantsWebApp;
import it.csi.dma.puawa.presentation.model.Data;
import it.csi.dma.puawa.presentation.model.Utente;
import it.csi.dma.puawa.util.Utils;

@Controller
@Scope("prototype")
public class BaseController {

	@Autowired
	protected HttpSession session;

	@Autowired
	private UtenteLowDao utenteLowDao;

	@Autowired
	private ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	private RuoloLowDao ruoloLowDao;

	@Autowired
	private LogDao logDao;

	protected Data data;

	public Data getData() {
		if (data == null) {
			if (session.getAttribute("data") == null) {
				data = new Data();
				session.setAttribute("data", new Data());
			} else {
				data = (Data) session.getAttribute("data");
			}
		}
		return data;
	}

	public Data initializeData() {
		this.getData();
		Utente utente = null;
		if (this.data.getUtente() != null) {
			utente = new Utente();
			utente = data.getUtente();
			utente.setAbilitazioneUtilizzata(null);
			utente.setViewCollocazione(null);
			utente.setListaAbilitazioni(null);
			utente.setListaRuoli(null);
			utente.setRuolo(null);
			utente.setViewListaCollocazioni(null);
		}
		this.data = new Data();
		this.data.setUtente(utente);
		updateData(this.data);
		session.setAttribute("data", (Data) this.data);
		return this.data;
	}

	public void updateData(Data data) {
		if (data != null) {
			session.setAttribute("data", (Data) data);
		} else {
			session.removeAttribute("data");
		}
	}

	public void setData(Data data) {
		this.data = data;
		session.setAttribute("data", (Data) data);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	protected LogAuditDto setLogAudit(LogAuditRichiedente richiedente,
									  String codiceLog, String colCodAzienda, String codiceCollocazione, AbilitazioneDto abilitazioneDto,
									  String token, MessaggiDto messaggiDto, Object... params) {
		LogAuditDto logAudit = new LogAuditDto();

		Utente utente = data.getUtente();

		// Utente
		UtenteDto utenteDto = Utils
				.getFirstRecord(utenteLowDao.findByCodiceFiscale(richiedente.getCodiceFiscaleRichiedente()));
		logAudit.setUtenteDto(utenteDto);

		// Ruolo
		RuoloDto ruoloDto = new RuoloDto();
		ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodice(ruoloDto, data.getCodiceRuoloSelezionato()));
		logAudit.setRuoloDto(ruoloDto);

		// Applicazione
		ApplicazioneDto applicazioneDto = new ApplicazioneDto();
		applicazioneDto = Utils.getFirstRecord(
				applicazioneLowDao.findByCodice(applicazioneDto, richiedente.getApplicazioneRichiesta()));
		logAudit.setApplicazioneDto(applicazioneDto);

		if (applicazioneDto != null && ruoloDto != null && abilitazioneDto != null) {
			logAudit.setAbilitazioneDto(abilitazioneDto);
			logAudit.setAbilitazioneDto(utente.getAbilitazioneUtilizzata());
		}

		logAudit.setCfRichiedente(richiedente.getCodiceFiscaleRichiedente());
		logAudit.setIpRichiedente(richiedente.getIpChiamante());
		logAudit.setCodiceCollocazione(codiceCollocazione);
		logAudit.setColCodAzienda(colCodAzienda);
		logAudit.setCodiceLog(codiceLog);
		logAudit.setToken(token);
		logAudit.setMessaggiDto(messaggiDto);
		return logDao.saveLogAudit(logAudit, params);
	}
}
