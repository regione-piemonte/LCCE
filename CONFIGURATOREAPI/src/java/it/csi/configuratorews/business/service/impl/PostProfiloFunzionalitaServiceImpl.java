/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoLowDao;
import it.csi.configuratorews.business.dao.RuoloLowDao;
import it.csi.configuratorews.business.dao.RuoloProfiloLowDao;
import it.csi.configuratorews.business.dao.TipoFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.TipologiaDatoLowDao;
import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.PermessoFunzionalitaDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.business.dto.RuoloProfiloDto;
import it.csi.configuratorews.business.dto.TipoFunzionalitaDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.business.service.PostProfiloFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.Permesso;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;

@Service
@Transactional
public class PostProfiloFunzionalitaServiceImpl implements PostProfiloFunzionalitaService {

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	TipoFunzionalitaLowDao tipoFunzionalitaLowDao;

	@Autowired
	TreeFunzionalitaLowDao treeFunzionalitaLowDao;

	@Autowired
	PermessoFunzionalitaLowDao permessoFunzionalitaLowDao;

	@Autowired
	TipologiaDatoLowDao tipologiaDatoLowDao;

	@Autowired
	PermessoLowDao permessoLowDao;
	
	@Autowired
	RuoloProfiloLowDao ruoloProfiloLowDao;

	@Autowired
	RuoloLowDao ruoloLowDao;
	
	protected LogUtil log = new LogUtil(this.getClass());

	@Override
	public void insertNuovaFunzionalita(String codiceFunzionalita, String descrizioneFunzionalita,
			String codiceApplicazione, InserimentoProfiloFunzionalitaBody body, String codiceAzienda,
			String shibIdentitaCodiceFiscale) {

		Timestamp data = Utils.sysdate();

		// inserimento record in tabella AUTH_T_FUNZIONALITA
		FunzionalitaDto funzionalita = new FunzionalitaDto();
		funzionalita.setCodiceFunzione(codiceFunzionalita);
		funzionalita.setDescrizioneFunzione(descrizioneFunzionalita);
		funzionalita.setCfOperatore(shibIdentitaCodiceFiscale);
		funzionalita.setDataInizioValidita(data);
		funzionalita.setDataInserimento(data);
		funzionalita.setDataAggiornamento(data);
		ApplicazioneDto appicazioneDto = Utils
				.getFirstRecord(applicazioneLowDao.findByCodice(new ApplicazioneDto(), codiceApplicazione));
		funzionalita.setApplicazioneDto(appicazioneDto);
		TipoFunzionalitaDto funzionalitaTipo = Utils
				.getFirstRecord(tipoFunzionalitaLowDao.findByCodice(Constants.FUNZIONALITA_TIPO_FUNZ));
		funzionalita.setTipoFunzionalitaDto(funzionalitaTipo);

		funzionalitaLowDao.save(funzionalita);

		// inserimento record in tabella AUTH_R_FUNZIONALITA_TREE
		TreeFunzionalitaDto funzionalitaTree = new TreeFunzionalitaDto();
		funzionalitaTree.setFunzionalitaDto(funzionalita);
		funzionalitaTree.setDataInizioValidita(data);
		funzionalitaTree.setDataInserimento(data);
		funzionalitaTree.setDataAggiornamento(data);
		funzionalitaTree.setCfOperatore(shibIdentitaCodiceFiscale);

		TreeFunzionalitaDto funzionalitaTreePadre = new TreeFunzionalitaDto();
		funzionalitaTreePadre = Utils.getFirstRecord(treeFunzionalitaLowDao.findByCodiceApplicazioneAndFunzionalitaTipo(
				Constants.CODICE_TIPO_FUNZIONALITA_APP, codiceApplicazione));

		funzionalitaTree.setFunzionalitaTreePadreDto(funzionalitaTreePadre);

		treeFunzionalitaLowDao.save(funzionalitaTree);

		// inserimento in tabella AUTH_R_FUNZIONALITA_TREE per ogni codice profilo
		for (String profilo : body.getListaProfili()) {
			TreeFunzionalitaDto profiloTree = new TreeFunzionalitaDto();
			profiloTree.setFunzionalitaDto(funzionalita);
			profiloTree.setCfOperatore(shibIdentitaCodiceFiscale);
			profiloTree.setDataInizioValidita(data);
			profiloTree.setDataInserimento(data);
			profiloTree.setDataAggiornamento(data);

			TreeFunzionalitaDto profiloTreePadre = new TreeFunzionalitaDto();
			profiloTreePadre = Utils.getFirstRecord(treeFunzionalitaLowDao.findByCodiceFunzioneAndApplicazione(profilo,
					Constants.FUNZIONALITA_TIPO_PROFILO, codiceApplicazione));
			profiloTree.setFunzionalitaTreePadreDto(profiloTreePadre);

			treeFunzionalitaLowDao.save(profiloTree);
		}

		// inserimento record in AUTH_R_FUNZIONALITA_TIPOLOGIA_DATO_PERMESSO
		for (Permesso permesso : body.getTipologiaDatiPermessi()) {
			PermessoFunzionalitaDto permessoFunz = new PermessoFunzionalitaDto();
			permessoFunz.setCfOperatore(shibIdentitaCodiceFiscale);
			permessoFunz.setDataInizioValidita(data);
			permessoFunz.setDataInserimento(data);
			permessoFunz.setDataAggiornamento(data);
			permessoFunz.setFunzionalita(funzionalita);
			permessoFunz.setTipologiaDato(tipologiaDatoLowDao.findByCodice(permesso.getCodiceTipologiaDato()));
			permessoFunz.setPermesso(permessoLowDao.findByCodice(permesso.getCodicePermesso()));

			permessoFunzionalitaLowDao.save(permessoFunz);
		}
	}

	@Override
	public void insertNuovoProfilo(String codiceProfilo, String descrizioneProfilo, String codiceApplicazione,
			String shibIdentitaCodiceFiscale, InserimentoProfiloFunzionalitaBody body) {

		Timestamp data = Utils.sysdate();

		FunzionalitaDto profilo = new FunzionalitaDto();
		profilo.setCodiceFunzione(codiceProfilo);
		profilo.setDescrizioneFunzione(descrizioneProfilo);
		profilo.setCfOperatore(shibIdentitaCodiceFiscale);
		profilo.setDataInizioValidita(data);
		profilo.setDataInserimento(data);
		profilo.setDataAggiornamento(data);

		ApplicazioneDto appicazioneDto = Utils
				.getFirstRecord(applicazioneLowDao.findByCodice(new ApplicazioneDto(), codiceApplicazione));
		profilo.setApplicazioneDto(appicazioneDto);

		TipoFunzionalitaDto funzionalitaTipo = Utils
				.getFirstRecord(tipoFunzionalitaLowDao.findByCodice(Constants.FUNZIONALITA_TIPO_PROFILO));
		profilo.setTipoFunzionalitaDto(funzionalitaTipo);

		funzionalitaLowDao.save(profilo);

		// inserimento profilo appena creato in tabella AUTH_R_FUNZIONALITA_TREE
		TreeFunzionalitaDto profiloTree = new TreeFunzionalitaDto();
		profiloTree.setFunzionalitaDto(profilo);
		profiloTree.setDataInizioValidita(data);
		profiloTree.setDataInserimento(data);
		profiloTree.setDataAggiornamento(data);
		profiloTree.setCfOperatore(shibIdentitaCodiceFiscale);

		profiloTree.setFunzionalitaTreePadreDto(null);

		treeFunzionalitaLowDao.save(profiloTree);
		
		// inserimento di ogni ruolo passato in input
		for(String codiceRuolo: body.getListaRuoli()) {
			RuoloProfiloDto ruoloProfilo = new RuoloProfiloDto();
			
			RuoloDto ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodiceAndDataValidita(codiceRuolo));		
			
			ruoloProfilo.setRuoloDto(ruoloDto);
			ruoloProfilo.setFunzionalitaDto(profilo);
			ruoloProfilo.setDataInizioValidita(data);
			
			ruoloProfiloLowDao.save(ruoloProfilo);
		}
		
	}

}
