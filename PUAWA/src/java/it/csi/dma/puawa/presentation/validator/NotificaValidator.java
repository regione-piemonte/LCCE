/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.dma.puawa.business.dao.util.CatalogoLog;
import it.csi.dma.puawa.integration.log.LogDao;
import it.csi.dma.puawa.integration.reports.common.Errore;
import it.csi.dma.puawa.presentation.model.NotificaData;

@Component
public class NotificaValidator {

//	public static final String CODICE_FISCALE_REGEX = "^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$";
	private static final String CODICE_FISCALE_REGEX = "^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})$|([0-9]{11})$";
	public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	@Autowired
	private LogDao logDao;

	public List<Errore> validateCampi(NotificaData notificaData) {
		List<Errore> errori = new ArrayList<Errore>();

		errori = validateCodiceFiscale(notificaData, errori);

		errori = validateEmailAzienda(notificaData, errori);

		errori = validateNumeroTelefonoAzienda(notificaData, errori);

		errori = validateTitoloPush(notificaData, errori);

		errori = validateTestoPush(notificaData, errori);

		errori = validateOggettoMail(notificaData, errori);

		errori = validateTestoEmail(notificaData, errori);

		errori = validateTestoSito(notificaData, errori);

		return errori;
	}

	private List<Errore> validateTestoSito(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getTestoSito() == null || notificaData.getTestoSito().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Testo Sito"));
		}
		return errori;
	}

	private List<Errore> validateTestoEmail(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getTestoEmail() == null || notificaData.getTestoEmail().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Testo Email"));
		}
		return errori;
	}

	private List<Errore> validateOggettoMail(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getOggettoEmail() == null || notificaData.getOggettoEmail().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Oggetto Email"));
		}
		return errori;
	}

	private List<Errore> validateTestoPush(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getTestoPush() == null || notificaData.getTestoPush().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Testo Push"));
		}
		return errori;
	}

	private List<Errore> validateTitoloPush(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getTitoloPush() == null || notificaData.getTitoloPush().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Titolo Push"));
		}
		return errori;
	}

	private List<Errore> validateNumeroTelefonoAzienda(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getNumeroTelefonoAzienda() == null || notificaData.getNumeroTelefonoAzienda().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Numero Telefono Azienda"));
		}else if(!StringUtils.isNumeric(notificaData.getNumeroTelefonoAzienda())){
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALIDO.getValue(), "Numero Telefono Azienda"));
		}
		return errori;
	}

	private List<Errore> validateEmailAzienda(NotificaData notificaData, List<Errore> errori) {
		Pattern pattern;
		Matcher matcher;
		if (notificaData.getEmailAzienda() == null || notificaData.getEmailAzienda().length()==0) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Email Azienda"));
		} else {
			pattern = Pattern.compile(EMAIL_REGEX);
			matcher = pattern.matcher(notificaData.getEmailAzienda());
			if (!matcher.matches()) {
				errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALIDO.getValue(), "Email Azienda"));
			}
		}
		return errori;
	}

	private List<Errore> validateCodiceFiscale(NotificaData notificaData, List<Errore> errori) {
		if (notificaData.getCodiceFiscaleAssistito() == null || notificaData.getCodiceFiscaleAssistito().isEmpty()) {
			errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALORIZZATO.getValue(), "Codice Fiscale Assistito"));
		} else {
			Pattern pattern = Pattern.compile(CODICE_FISCALE_REGEX);
			Matcher matcher = pattern.matcher(notificaData.getCodiceFiscaleAssistito());
			if (!matcher.matches()) {
				errori.add(logDao.getErrore(CatalogoLog.CAMPO_NON_VALIDO.getValue(), "Codice Fiscale Assistito"));
			}
		}
		return errori;
	}
}
