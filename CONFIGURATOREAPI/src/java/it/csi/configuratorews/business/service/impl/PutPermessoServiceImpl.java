/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoLowDao;
import it.csi.configuratorews.business.dao.TipologiaDatoLowDao;
import it.csi.configuratorews.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.PermessoDto;
import it.csi.configuratorews.business.dto.PermessoFunzionalitaDto;
import it.csi.configuratorews.business.dto.TipologiaDatoDto;
import it.csi.configuratorews.business.service.PutPermessoService;
import it.csi.configuratorews.dto.configuratorews.ListaPermessi;
import it.csi.configuratorews.dto.configuratorews.Permesso;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PutPermessoServiceImpl implements PutPermessoService {

	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;
	
	@Autowired
	private PermessoFunzionalitaLowDao permessoFunzionalitaLowDao;
	@Autowired
	private PermessoLowDao permessoLowDao;
	@Autowired
	private TipologiaDatoLowDao tipologiaLowDao;
	private LogUtil log = new LogUtil(this.getClass());
	
	@Override
	public void updatePermesso(String codiceFunzionalita, String codiceApplicazione, String shibIdentitaCodiceFiscale, String codiceAzienda,
			ListaPermessi permessi) {
		FunzionalitaDto funzionalita = funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita,  codiceApplicazione,FunzionalitaLowDaoImpl.CODICE_TIPO_FUNZIONALITA , codiceAzienda);
		if(funzionalita!=null) {
			List<PermessoFunzionalitaDto> permessiFunzionalita = permessoFunzionalitaLowDao.findPermessiByFunzId(funzionalita.getIdFunzione());
			Timestamp ts = Utils.sysdate();
			for (PermessoFunzionalitaDto permessoFunzionalitaDto : permessiFunzionalita) {
				permessoFunzionalitaDto.setDataFineValidita(ts);
				permessoFunzionalitaDto.setDataCancellazione(ts);
				permessoFunzionalitaDto.setDataAggiornamento(ts);
				permessoFunzionalitaDto.setCfOperatore(shibIdentitaCodiceFiscale);
			}
			for(Permesso permesso:permessi.getPermessi()) {
				PermessoFunzionalitaDto permessoFunzionalita = new PermessoFunzionalitaDto();
				permessoFunzionalita.setCfOperatore(shibIdentitaCodiceFiscale);
				permessoFunzionalita.setDataAggiornamento(ts);
				permessoFunzionalita.setDataInizioValidita(ts);
				permessoFunzionalita.setDataInserimento(ts);
				permessoFunzionalita.setFunzionalita(funzionalita);
				PermessoDto permesso1 = permessoLowDao.findByCodice(permesso.getCodicePermesso());
				permessoFunzionalita.setPermesso(permesso1 );
				TipologiaDatoDto tipologiaDato=tipologiaLowDao.findByCodice(permesso.getCodiceTipologiaDato());
				permessoFunzionalita.setTipologiaDato(tipologiaDato);
				permessoFunzionalitaLowDao.save(permessoFunzionalita);
			}
		}else {
			log.error("updatePermesso", "error updating permessi " + codiceFunzionalita);
		}
	}

}
