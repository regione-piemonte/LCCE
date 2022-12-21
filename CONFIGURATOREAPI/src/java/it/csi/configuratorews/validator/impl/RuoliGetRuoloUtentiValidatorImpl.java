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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class RuoliGetRuoloUtentiValidatorImpl extends BaseValidatorImpl implements RuoliGetRuoloUtentiValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	RuoloLowDao ruoloLowDao;


	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
						 String xForwardedFor, String xCodiceServizio, String codiceRuolo) throws Exception{

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}

		if(codiceRuolo == null || codiceRuolo.isEmpty()){
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_RUOLO);
		}else{
			RuoloDto ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodiceAndDataValidita(codiceRuolo));
			if(ruoloDto == null){
				return generateErrore(Constants.RUOLO_ERRATO, Constants.CODICE_RUOLO);
			}else if(!"S".equalsIgnoreCase(ruoloDto.getVisibilitaConf())){
				return generateErrore(Constants.RUOLO_NON_VISIB_DA_CONF, Constants.CODICE_RUOLO);
			}
		}

		return null;

	}
}
