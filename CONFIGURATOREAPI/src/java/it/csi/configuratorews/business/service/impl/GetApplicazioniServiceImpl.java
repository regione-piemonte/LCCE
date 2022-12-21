/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.service.GetApplicazioniService;
import it.csi.configuratorews.dto.configuratorews.Applicazione;
import it.csi.configuratorews.dto.configuratorews.Pagination;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetApplicazioniServiceImpl implements GetApplicazioniService {

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;

	@Override
	public Pagination<Applicazione> getApplicazioni(Integer limit, Integer offset, String codiceAzienda) {
		Pagination<Applicazione> applicazioniOutput = new Pagination<Applicazione>();
		List<Applicazione> applicazioniList = null;
		List<ApplicazioneDto> applicazioneDtoList = recuperaApplicazioni(limit, offset, codiceAzienda);
		if (applicazioneDtoList != null && !applicazioneDtoList.isEmpty()) {
			applicazioniList = new ArrayList<>();
			for (ApplicazioneDto applicazioneDto : applicazioneDtoList) {
				popolaOutput(applicazioniList, applicazioneDto);
			}
		}
		applicazioniOutput.setListaRis(applicazioniList);
		Long countApplicazioni = applicazioneLowDao.countApplicazioniByCodAzienda(codiceAzienda);
		applicazioniOutput.setCount(countApplicazioni);
		return applicazioniOutput;
	}

	private List<ApplicazioneDto> recuperaApplicazioni(Integer limit, Integer offset, String codiceAzienda) {
		return applicazioneLowDao.findAll(limit, offset, codiceAzienda);
	}

	private void popolaOutput(List<Applicazione> applicazioniOutput, ApplicazioneDto applicazioneDto) {
		Applicazione app = new Applicazione();
		app.setCodice(applicazioneDto.getCodice());
		app.setDescrizione(applicazioneDto.getDescrizione());
		applicazioniOutput.add(app);
	}

}
