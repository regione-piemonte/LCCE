/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.UtenteValidator;

@Component
public class UtenteValidatorImpl extends BaseValidatorImpl implements UtenteValidator {

	protected LogUtil log = new LogUtil(this.getClass());
	
	@Autowired
	UtenteLowDao utenteLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		return null;

	}

	@Override
	public ErroreBuilder validateCodiceFiscale(String codiceFiscale) throws Exception {

		UtenteDto utenteDto = utenteLowDao.findByCodiceFiscale(codiceFiscale);
		if(utenteDto == null){
			return generateErrore(Constants.CODICE_FISCALE_NON_VALIDO, Constants.CODICE_FISCALE);
		}
		
		return null;
	}
}
