/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.service.PutProfiloFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalitaBody;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PutProfiloFunzionalitaServiceImpl implements PutProfiloFunzionalitaService{

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	protected LogUtil log = new LogUtil(this.getClass());
	@Override
	public void updateProfiloFunzionalita(String codiceFunzionalita, String codiceApplicazione, ProfiloFunzionalitaBody body,String codiceAzienda, String codiceFiscale) {
		FunzionalitaDto funzionalita =  funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita,codiceApplicazione,body.getTipo(), codiceAzienda);
		if(funzionalita!=null) {
			if(body.getDescrizione()!=null) {
				funzionalita.setDescrizioneFunzione(body.getDescrizione());
			}
			funzionalita.setDataAggiornamento(new Timestamp(new Date().getTime()));
			funzionalita.setCfOperatore(codiceFiscale);
		} else {
			log.error("updateProfiloFunzionalita", "funzionalita non travata forse codice azienda non valido "+codiceFunzionalita+codiceAzienda);
		}
	}

	
}
