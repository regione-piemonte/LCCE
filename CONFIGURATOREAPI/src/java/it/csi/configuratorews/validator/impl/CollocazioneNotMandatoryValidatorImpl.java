/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.CollocazioneNotMandatoryValidator;

@Component
public class CollocazioneNotMandatoryValidatorImpl extends BaseValidatorImpl
		implements CollocazioneNotMandatoryValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	CollocazioneLowDao collocazioneLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceCollocazione) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		if (codiceCollocazione != null) {
			CollocazioneDto collocazioneDto = Utils.getFirstRecord(collocazioneLowDao.findByCodice(codiceCollocazione));
			if (collocazioneDto == null) {
				return generateErrore(Constants.COLLOCAZIONE_ERRATA, Constants.CODICE_COLLOCAZIONE);
			}
		}

		return null;

	}

	@Override
	public ErroreBuilder validateCoerenzaAzienda(String shibIdentitaCodiceFiscale, String xRequestID,
			String xForwardedFor, String xCodiceServizio, String codiceCollocazione, String codiceAzienda)
			throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		if (codiceCollocazione != null) {
			CollocazioneDto collocazioneDto = Utils
					.getFirstRecord(collocazioneLowDao.findByCodiceAndAzienda(codiceCollocazione, codiceAzienda));
			if (collocazioneDto == null) {
				return generateErrore(Constants.COLLOCAZIONE_NON_VALIDA_PER_AZIENDA, Constants.CODICE_COLLOCAZIONE);
			}
		}

		return null;

	}
}
