/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.FunzionalitaPerApplicazioneValidator;

@Component
public class FunzionalitaPerApplicazioneValidatorImpl extends BaseValidatorImpl  implements FunzionalitaPerApplicazioneValidator{
	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	@Override
	public ErroreBuilder validate(String codiceFunzionalita, String codiceApplicazione) throws Exception {
		if (codiceFunzionalita != null && !codiceFunzionalita.isEmpty()) {
			// controllo che sia valida la funzionalita' richiesta
			FunzionalitaDto funz = funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita,
					codiceApplicazione, Constants.FUNZIONALITA_TIPO_FUNZ);
			if (funz == null) {
				return generateErrore(Constants.PROFILO_O_FUNZIONALITA_ASSENTE,
						Constants.CODICE_FUNZIONALITA);
			}
		}
		return null;
	}
	
	@Override
	public ErroreBuilder validateCodiceProfilo(String codiceFunzionalita, String codiceApplicazione) throws Exception {
		if (codiceFunzionalita != null && !codiceFunzionalita.isEmpty()) {
			// controllo che sia valida la funzionalita' richiesta
			FunzionalitaDto funz = funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita,
					codiceApplicazione, Constants.FUNZIONALITA_TIPO_PROFILO);
			if (funz == null) {
				return generateErrore(Constants.PROFILO_O_FUNZIONALITA_ASSENTE,
						Constants.CODICE_FUNZIONALITA);
			}
		}
		return null;
	}

}
