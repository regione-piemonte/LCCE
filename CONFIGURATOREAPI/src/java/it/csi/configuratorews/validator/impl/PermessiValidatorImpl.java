/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.PermessoLowDao;
import it.csi.configuratorews.business.dao.TipologiaDatoLowDao;
import it.csi.configuratorews.business.dto.TipologiaDatoDto;
import it.csi.configuratorews.dto.configuratorews.ListaPermessi;
import it.csi.configuratorews.dto.configuratorews.Permesso;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.PermessiValidator;

@Component
public class PermessiValidatorImpl extends BaseValidatorImpl implements PermessiValidator{

	@Autowired
	TipologiaDatoLowDao tipologiaDatoLowDao;
	@Autowired
	PermessoLowDao permessoLowDao;
	
	@Override
	public ErroreBuilder validate(ListaPermessi listaPermessi) throws Exception {
		List<TipologiaDatoDto> tipologie = tipologiaDatoLowDao.findAllValide();
		if (tipologie.size() !=listaPermessi.getPermessi().size()) {
			return generateErrore(Constants.TIPOLOGIE_DATO_MANCANTI, Constants.CODICE_PERMESSO);
		}
		for (Permesso permesso : listaPermessi.getPermessi()) {
			if (!permessoLowDao.existsByCodice(permesso.getCodicePermesso())) {
				return generateErrore(Constants.PERMESSO_NON_VALIDO, Constants.CODICE_PERMESSO);
			}
			if(search(tipologie,permesso.getCodiceTipologiaDato())==null) {
				return generateErrore(Constants.TIPOLOGIA_DATO_NON_VALIDO, Constants.CODICE_TIPOLOGIA_DATO);
			}
		}
		return null;
	}

	private Object search(List<TipologiaDatoDto> tipologie, String codiceTipologiaDato) {
		for(TipologiaDatoDto tipologiaDatoDto:tipologie) {
			if(tipologiaDatoDto.getCodice().equals(codiceTipologiaDato)) {
				return tipologiaDatoDto;
			}
		}
		return null;
	}

}
