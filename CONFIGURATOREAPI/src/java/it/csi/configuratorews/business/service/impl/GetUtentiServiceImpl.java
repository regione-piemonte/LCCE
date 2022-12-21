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

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.business.service.GetUtentiService;
import it.csi.configuratorews.dto.configuratorews.Abilitazione;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.ModelUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.Profilo;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetUtentiServiceImpl implements GetUtentiService{

	@Autowired
	UtenteLowDao utenteLowDao;
	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	
	@Override
	public Pagination<ModelUtente> getUtentiSol(String applicazione, String azienda, Integer limit, Integer offset) {
		Pagination<ModelUtente> pagination = new Pagination<>();
		
		List<UtenteDto> utenti= utenteLowDao.findUtentiByApplicazioneAndAzienda(applicazione,azienda,limit,offset);
		Long utentiCount= utenteLowDao.countUtentiByApplicazioneAndAzienda(applicazione,azienda);
		List<ModelUtente> utentiModel = createListModelUtenti(utenti);
		for(ModelUtente utente:utentiModel) {
			List<AbilitazioneDto> abilitazioni = abilitazioneLowDao.findAbilitazioniByUtente(utente.getId());
			populateUtente(utente,abilitazioni);
		}
		pagination.setListaRis(utentiModel);
		pagination.setCount(utentiCount);
		return pagination;
	}

	private void populateUtente(ModelUtente utente, List<AbilitazioneDto> abilitazioni) {
		List<Abilitazione> abilitazioniModel = new ArrayList<>();
		for(AbilitazioneDto abilitazione: abilitazioni) {
			Abilitazione abilitazioneModel = new Abilitazione();
			ModelCollocazione collocazione = null;
			if(abilitazione.getUtenteCollocazioneDto()!=null) {
				collocazione = new ModelCollocazione();
				collocazione.setCodiceCollocazione(abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColCodice());
				collocazione.setCodiceAzienda(abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda());
				collocazione.setDescrizioneAzienda(abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColDescAzienda());
				collocazione.setDescrizioneCollocazione(abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColDescAzienda());
			}
			abilitazioneModel.setCollocazione(collocazione );
			ModelRuolo ruoloModel = new ModelRuolo();
			ruoloModel.setCodice(abilitazione.getRuoloUtenteDto().getRuoloDto().getCodice());
			ruoloModel.setDescrizione(abilitazione.getRuoloUtenteDto().getRuoloDto().getDescrizione());
			abilitazioneModel.setRuolo(ruoloModel);
			Profilo profilo = null;
			profilo= new Profilo();
			profilo.setCodice(abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione());
			profilo.setDescrizione(abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto().getDescrizioneFunzione());
			abilitazioneModel.setProfilo(profilo);
			abilitazioniModel.add(abilitazioneModel );
		}
		utente.setAbilitazioni(abilitazioniModel );
		
	}

	private List<ModelUtente> createListModelUtenti(List<UtenteDto> utenti) {
		List<ModelUtente> listaUtenti = new ArrayList<ModelUtente>();
		for(UtenteDto utente:utenti) {
			ModelUtente modelUtente = new ModelUtente();
			modelUtente.setId(utente.getId());
			modelUtente.setNome(utente.getNome());
			modelUtente.setCodiceFiscale(utente.getCodiceFiscale());
			modelUtente.setCognome(utente.getCognome());
			listaUtenti.add(modelUtente);
		}
		return listaUtenti;
	}

}
