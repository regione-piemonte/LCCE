/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import it.csi.configuratorews.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneCollocazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.ApplicazioneSolValidator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicazioneSolValidatorImpl extends BaseValidatorImpl implements ApplicazioneSolValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	ApplicazioneCollocazioneLowDao applicazioneCollocazioneLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String applicazione) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		if (applicazione == null || applicazione.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.APPLICAZIONE);
		} else {
			ApplicazioneDto applicazioneDto = new ApplicazioneDto();
			applicazioneDto.setCodice(applicazione);
			applicazioneDto = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto));
			if (applicazioneDto == null) {
				return generateErrore(Constants.APPLICAZIONE_ERRATA, Constants.APPLICAZIONE);
			}
		}

		return null;

	}

	@Override
	public ErroreBuilder validateCoerenzaAzienda(String applicazione, String azienda) throws Exception {

		List<ApplicazioneCollocazioneDto> list = applicazioneCollocazioneLowDao
				.findByApplicazioneAndAzienda(applicazione, azienda);

		if (list == null || list.size() <= 0) {
			return generateErrore(Constants.ERRORE_LEGAME_AZIENDA_APPLICAZIONE, Constants.APPLICAZIONE);
		}

		return null;
	}

	@Override
	public ErroreBuilder validateFlagBloccoModifica(String applicazione) throws Exception {

		List<ApplicazioneDto> list = applicazioneLowDao.findByCodiceAndBloccoModificaS(applicazione);
		if (list != null && list.size() > 0) {
			return generateErrore(Constants.APPLICAZIONE_NON_DISPONIBILE, Constants.APPLICAZIONE);
		}
		
		return null;
		
	}
}
