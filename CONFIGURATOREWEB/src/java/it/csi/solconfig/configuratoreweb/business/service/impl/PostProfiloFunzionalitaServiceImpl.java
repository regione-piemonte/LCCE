/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.PermessoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.PermessoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloProfiloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipologiaDatoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TreeFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.InserimentoProfiloFunzionalitaBody;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.FunzionalitaService;
import it.csi.solconfig.configuratoreweb.business.service.PostProfiloFunzionalitaService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Permesso;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Service
@Transactional
public class PostProfiloFunzionalitaServiceImpl implements PostProfiloFunzionalitaService {

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	@Autowired
	private FunzionalitaService funzionalitaService;

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
	
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Override
	public TreeFunzionalitaDto insertNuovaFunzionalita(String codiceFunzionalita, String descrizioneFunzionalita,
			String codiceApplicazione, InserimentoProfiloFunzionalitaBody body, String shibIdentitaCodiceFiscale) throws Exception {

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
		TipoFunzionalitaDto funzionalitaTipo = tipoFunzionalitaLowDao.findByCodiceTipoFunzione(Constants.FUNZIONALITA_TIPO_FUNZ);
		funzionalita.setTipoFunzionalitaDto(funzionalitaTipo);

		funzionalita = funzionalitaLowDao.insert(funzionalita); //.save(funzionalita);

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

		treeFunzionalitaLowDao.insert(funzionalitaTree);   //.save(funzionalitaTree);

		// inserimento in tabella AUTH_R_FUNZIONALITA_TREE per ogni codice profilo
		/*for (String profilo : body.getListaProfili()) {
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

			treeFunzionalitaLowDao.insert(profiloTree); //.save(profiloTree);
		}*/

		// inserimento record in AUTH_R_FUNZIONALITA_TIPOLOGIA_DATO_PERMESSO
		for (Permesso permesso : body.getTipologiaDatiPermessi()) {
			PermessoFunzionalitaDto permessoFunz = new PermessoFunzionalitaDto();
			permessoFunz.setCfOperatore(shibIdentitaCodiceFiscale);
			permessoFunz.setDataInizioValidita(data);
			permessoFunz.setDataInserimento(data);
			permessoFunz.setDataAggiornamento(data);
			permessoFunz.setFunzionalita(funzionalita);
			permessoFunz.setTipologiaDato(tipologiaDatoLowDao.findByCodice(permesso.getCodiceTipologiaDato()));
			permessoFunz.setPermesso(permessoLowDao.findByPrimaryId(Long.parseLong(permesso.getCodicePermesso())));
			
			permessoFunzionalitaLowDao.insert(permessoFunz);  //.save(permessoFunz);
		}

		return funzionalitaTree;
	}

	@Override
	public FunzionalitaDto modificaFunzionalita(FunzionalitaModel funz, InserimentoProfiloFunzionalitaBody body, String shibIdentitaCodiceFiscale) throws Exception {

		Timestamp data = Utils.sysdate();

		// inserimento record in tabella AUTH_T_FUNZIONALITA
		FunzionalitaDto funzionalita = funzionalitaLowDao.findByPrimaryId(funz.getId());
		funzionalita.setDescrizioneFunzione(funz.getDescrizione());
		if (!funz.isFunzionalitaAttivo()) {
			funzionalita.setDataFineValidita(data);
			funzionalita.setDataCancellazione(data);
		}

		log.info("Funzionality modificata!");
		funzionalitaLowDao.update(funzionalita); //.save(funzionalita);

		// inserimento record in AUTH_R_FUNZIONALITA_TIPOLOGIA_DATO_PERMESSO

		for (PermessoFunzionalitaDto permessoFunz : permessoFunzionalitaLowDao.findPermessiByFunzId(funz.getId())) {
			permessoFunz.setDataFineValidita(data);
			permessoFunz.setDataCancellazione(data);
			log.info("CodiceTipologiaDato " + permessoFunz.getTipologiaDato().getCodice() + " con Permesso " + permessoFunz.getPermesso().getCodice() + " eliminato. ");
			permessoFunzionalitaLowDao.update(permessoFunz);
		}
		
		for (Permesso permesso : body.getTipologiaDatiPermessi()) {
			PermessoFunzionalitaDto permessoFunz = new PermessoFunzionalitaDto();
			permessoFunz.setCfOperatore(shibIdentitaCodiceFiscale);
			permessoFunz.setDataInizioValidita(data);
			permessoFunz.setDataInserimento(data);
			permessoFunz.setDataAggiornamento(data);
			permessoFunz.setFunzionalita(funzionalita);
			permessoFunz.setTipologiaDato(tipologiaDatoLowDao.findByCodice(permesso.getCodiceTipologiaDato()));
			permessoFunz.setPermesso(permessoLowDao.findByPrimaryId(Long.parseLong(permesso.getCodicePermesso())));
			permessoFunzionalitaLowDao.insert(permessoFunz);  //.save(permessoFunz);
		}
		
		return funzionalita;
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

		TipoFunzionalitaDto funzionalitaTipo = tipoFunzionalitaLowDao.findByCodiceTipoFunzione(Constants.FUNZIONALITA_TIPO_PROFILO);
		profilo.setTipoFunzionalitaDto(funzionalitaTipo);

		funzionalitaLowDao.insert(profilo);  //.save(profilo);

		// inserimento profilo appena creato in tabella AUTH_R_FUNZIONALITA_TREE
		TreeFunzionalitaDto profiloTree = new TreeFunzionalitaDto();
		profiloTree.setFunzionalitaDto(profilo);
		profiloTree.setDataInizioValidita(data);
		profiloTree.setDataInserimento(data);
		profiloTree.setDataAggiornamento(data);
		profiloTree.setCfOperatore(shibIdentitaCodiceFiscale);

		profiloTree.setFunzionalitaTreePadreDto(null);

		treeFunzionalitaLowDao.insert(profiloTree);   //.save(profiloTree);
		
		// inserimento di ogni ruolo passato in input
		for(String codiceRuolo: body.getListaRuoli()) {
			RuoloProfilo ruoloProfilo = new RuoloProfilo();
			
			RuoloDto ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodiceAndDataValidita(codiceRuolo));		
			
			ruoloProfilo.setRuolo(ruoloDto);
			ruoloProfilo.setFunzionalita(profilo);
			ruoloProfilo.setDataInizioValidita(data);
			
			ruoloProfiloLowDao.insert(ruoloProfilo);   //.save(ruoloProfilo);
		}
		
	}

}
