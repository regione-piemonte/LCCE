/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.ApplicazioneSolGetUtentiSOLValidator;


@Component
public class ApplicazioneSolGetUtentiSOLValidatorImpl extends BaseValidatorImpl implements ApplicazioneSolGetUtentiSOLValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	CollocazioneLowDao collocazioneLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
						 String xForwardedFor, String xCodiceServizio, String applicazione, String azienda) throws Exception{

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}

		if(applicazione == null || applicazione.isEmpty()){
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.APPLICAZIONE);
		}else{
			ApplicazioneDto applicazioneDto = new ApplicazioneDto();
			applicazioneDto.setCodice(applicazione);
			applicazioneDto = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto));
			if(applicazioneDto == null){
				return generateErrore(Constants.PARAMETRO_NON_VALIDO, Constants.APPLICAZIONE);
			}
		}

		if(azienda != null && !azienda.isEmpty()){
			Collection<CollocazioneDto> collocazioneDtoCollection = collocazioneLowDao.findByAziendaAndDataValidazione(azienda);
			if(collocazioneDtoCollection == null || collocazioneDtoCollection.isEmpty()){
				return generateErrore(Constants.PARAMETRO_NON_VALIDO, Constants.AZIENDA);
			}
		}

		return null;

	}
}
