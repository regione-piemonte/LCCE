/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.FunzionalitaGetValidator;

@Component
public class FunzionalitaGetValidatorImpl extends BaseValidatorImpl implements FunzionalitaGetValidator {

	protected LogUtil log = new LogUtil(this.getClass());
	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	
	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceFunzionalita) throws Exception {
		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}
		if(codiceFunzionalita == null || codiceFunzionalita.isEmpty()){
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_FUNZIONALITA);
		}else{
			FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
			funzionalitaDto = Utils.getFirstRecord(funzionalitaLowDao.findByCodiceFunzioneAndCodiceTipoFunzione(codiceFunzionalita,Constants.FUNZIONALITA_TIPO_FUNZ));
			if(funzionalitaDto == null){
				return generateErrore(Constants.FUNZIONALITA_ERRATA, Constants.CODICE_FUNZIONALITA);
			}
		}

		return null;
	}

}
