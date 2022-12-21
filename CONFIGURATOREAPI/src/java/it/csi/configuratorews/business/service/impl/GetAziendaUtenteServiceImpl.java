/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.service.GetAziendaUtenteService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetAziendaUtenteServiceImpl implements GetAziendaUtenteService {

	@Autowired
	CollocazioneLowDao collocazioneLowDao;

	@Override
	public String getAzienda(String codiceFiscale, String codiceCollocazione, String codiceRuolo,
			String codiceApplicazione) {

		String codiceAzienda = collocazioneLowDao.getCodAziendaByRuoloCollAndCF(codiceFiscale, codiceCollocazione,
				codiceRuolo, codiceApplicazione);

		return codiceAzienda;

	}

}
