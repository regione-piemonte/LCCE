/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.RuoloProfiloLowDao;
import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.PermessoFunzionalitaDto;
import it.csi.configuratorews.business.dto.RuoloProfiloDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.business.service.PutAbilitazioniProfiloPreferenzeService;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PutAbilitazioniProfiloPreferenzeServiceImpl implements PutAbilitazioniProfiloPreferenzeService {

	@Autowired
	TreeFunzionalitaLowDao treeFunzionalitaLowDao;
	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	@Autowired
	PermessoFunzionalitaLowDao permessoFunzionalitaLowDao;
	
	@Autowired
	RuoloProfiloLowDao ruoloProfiloLowDao;

	private LogUtil log = new LogUtil(this.getClass());

	@Override
	public void updateAbilitazioniProfiloPreferenze(String codiceFunzionalita, String codiceApplicazione, String cfOperatore, String codiceAzienda) {
		// aggiorno Profilo
		FunzionalitaDto funzionalita = funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita, codiceApplicazione,
				FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO, codiceAzienda);
		if (funzionalita != null) {
			Timestamp dataFineValidita = new Timestamp(new Date().getTime());
			funzionalita.setDataFineValidita(dataFineValidita);
			funzionalita.setDataAggiornamento(dataFineValidita);
			funzionalita.setDataCancellazione(dataFineValidita);
			funzionalita.setCfOperatore(cfOperatore);
			List<TreeFunzionalitaDto> profili = treeFunzionalitaLowDao.findByCodiceFunzioneAndApplicazione(codiceFunzionalita, FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO, codiceApplicazione);
			for(TreeFunzionalitaDto profilo:profili) {
				profilo.setCfOperatore(cfOperatore);
				profilo.setDataFineValidita(dataFineValidita);
				profilo.setDataCancellazione(dataFineValidita);
				// aggiorno collegamenti funzionalita 
				List<TreeFunzionalitaDto> funzionalitaTreeResult = treeFunzionalitaLowDao.findFunzionalitaById(profilo.getIdTreeFunzione());
				for (TreeFunzionalitaDto legameConFunzionalita : funzionalitaTreeResult) {
					legameConFunzionalita.setCfOperatore(cfOperatore);
					legameConFunzionalita.setDataFineValidita(dataFineValidita);
					legameConFunzionalita.setDataCancellazione(dataFineValidita);
					
				}
			}
			
			//call procedure per aggiornare abilitazioni e preferenze -> 25/08/2022 per il momento non deve essere usato
			//abilitazioneLowDao.cancellaAbilitazioniPreferenze(codiceApplicazione, codiceFunzionalita, cfOperatore, dataFineValidita);
			
			// 25/08/2022 aggiornare informazioni profilo-ruoli, chiudendo associazioni tra profilo e ruoli			
			List<RuoloProfiloDto> ruoli = ruoloProfiloLowDao.findByProfiloAndApplicazione(codiceFunzionalita, codiceApplicazione);
			for(RuoloProfiloDto ruolo: ruoli) {
				ruolo.setDataFineValidita(dataFineValidita);
			}
			
			
		} else {
			log.error("updateFunzionalita", "error deleting " + codiceFunzionalita);
		}

	}

	@Override
	public void updateFunzionalita(String codiceFunzionalita, String codiceApplicazione, String cfOperatore, String codiceAzienda) {
		// chiusura funzionalita
		FunzionalitaDto funzionalita = funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita, codiceApplicazione,
				FunzionalitaLowDaoImpl.CODICE_TIPO_FUNZIONALITA, codiceAzienda);
		if (funzionalita != null) {
			Timestamp dataFineValidita = new Timestamp(new Date().getTime());
			funzionalita.setDataFineValidita(dataFineValidita);
			funzionalita.setDataAggiornamento(dataFineValidita);
			funzionalita.setDataCancellazione(dataFineValidita);
			funzionalita.setCfOperatore(cfOperatore);
			// chiusura link tra funzionalita e profilo
			List<TreeFunzionalitaDto> funzionalitaTree = treeFunzionalitaLowDao.findByCodiceFunzioneAndApplicazione(codiceFunzionalita, FunzionalitaLowDaoImpl.CODICE_TIPO_FUNZIONALITA, codiceApplicazione);
			for(TreeFunzionalitaDto currFunzionalitaTree:funzionalitaTree) {
				currFunzionalitaTree.setCfOperatore(cfOperatore);
				currFunzionalitaTree.setDataFineValidita(dataFineValidita);
				currFunzionalitaTree.setDataCancellazione(dataFineValidita);
				List<TreeFunzionalitaDto> result = treeFunzionalitaLowDao.findFunzionalitaByIdPadreProfilo(currFunzionalitaTree.getIdTreeFunzione());
				for (TreeFunzionalitaDto legameConProfili : result) {
					legameConProfili.setCfOperatore(cfOperatore);
					legameConProfili.setDataFineValidita(dataFineValidita);
					legameConProfili.setDataCancellazione(dataFineValidita);
				}
			}
			// chiusura permessi
			List<PermessoFunzionalitaDto> permessi = permessoFunzionalitaLowDao.findPermessiByFunzId(funzionalita.getIdFunzione());
			for (PermessoFunzionalitaDto permesso : permessi) {
				permesso.setDataAggiornamento(dataFineValidita);
				permesso.setDataFineValidita(dataFineValidita);
				permesso.setDataCancellazione(dataFineValidita);
				permesso.setCfOperatore(cfOperatore);
			}
		} else {
			log.error("updateFunzionalita", "error deleting " + codiceFunzionalita);
		}

	}

	
}
