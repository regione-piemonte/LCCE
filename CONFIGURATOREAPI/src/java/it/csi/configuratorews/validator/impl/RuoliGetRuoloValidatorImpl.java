/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dao.RuoloLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.ApplicazioneSolGetUtentiSOLValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloUtentiValidator;
import it.csi.configuratorews.validator.RuoliGetRuoloValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class RuoliGetRuoloValidatorImpl extends BaseValidatorImpl implements RuoliGetRuoloValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	RuoloLowDao ruoloLowDao;


	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
						 String xForwardedFor, String xCodiceServizio) throws Exception{

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}

		return null;

	}
}
