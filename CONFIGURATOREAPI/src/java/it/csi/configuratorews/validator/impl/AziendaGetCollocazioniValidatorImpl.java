/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.AziendaGetCollocazioniValidator;

@Component
public class AziendaGetCollocazioniValidatorImpl extends BaseValidatorImpl implements AziendaGetCollocazioniValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	CollocazioneLowDao collocazioneLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceAzienda) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		if (codiceAzienda == null || codiceAzienda.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_AZIENDA);
		} else {
			CollocazioneDto collocazioneDto = Utils
					.getFirstRecord(collocazioneLowDao.findByAziendaAndDataValidazione(codiceAzienda));
			if (collocazioneDto == null || StringUtils.isBlank(collocazioneDto.getColCodAzienda())) {
				return generateErrore(Constants.CODICE_AZIENDA_ERRATO, Constants.CODICE_AZIENDA);
			}
		}

		return null;

	}
}
