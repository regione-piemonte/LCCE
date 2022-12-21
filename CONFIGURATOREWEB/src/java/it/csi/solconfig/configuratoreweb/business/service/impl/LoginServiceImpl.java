/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.LoginService;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.Funzionalita;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.GetTokenInformation2Response;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.Richiedente;
import it.csi.solconfig.configuratoreweb.presentation.model.Collocazione;
import it.csi.solconfig.configuratoreweb.presentation.model.Ruolo;
import it.csi.solconfig.configuratoreweb.presentation.model.Utente;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

	@Autowired
    RuoloLowDao ruoloLowDao;
	
	@Autowired
    UtenteLowDao utenteLowDao;

	@Override
	public Utente getUtenteLogin(GetTokenInformation2Response getTokenInformation2Response, String ipAddressClient){

		Richiedente richiedente = getTokenInformation2Response.getRichiedente();

		Utente utente = new Utente();
		utente.setNome(richiedente.getNome());
		utente.setCognome(richiedente.getCognome());
		utente.setCodiceFiscale(richiedente.getCodiceFiscale());
		utente.setIpAddress(ipAddressClient);
		//Ruolo
		Ruolo ruolo = new Ruolo();
		ruolo.setCodice(richiedente.getRuolo());
		RuoloDto ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodice(new RuoloDto(), richiedente.getRuolo()));
		if(ruoloDto != null){
			ruolo.setDescrizione(ruoloDto.getDescrizione());
		}
		utente.setRuolo(ruolo);

		//collocazione da visualizzare
		utente.setCollocazione(convertCollocazione(richiedente));

		//Recupero codici funzionalita' abilitati
		List<String> funzionalitaAbilitateList = new ArrayList<String>();
		for(Funzionalita funzionalita : getTokenInformation2Response.getFunzionalitaAbilitate().getFunzionalita()){
			if(funzionalita.getCodiceFunzionalita().equalsIgnoreCase(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue())){
				log.info("Setto SUPERUSER profilo :" + funzionalita.getCodiceFunzionalita());
				utente.setProfilo(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());
			}
			if(funzionalita.getCodiceFunzionalita().equalsIgnoreCase(FunzionalitaEnum.CONF_TITOLARE_PROF.getValue()) 
					&& !FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equals(utente.getProfilo())){
				log.info("Setto TITOLARE profilo :" + funzionalita.getCodiceFunzionalita());
				utente.setProfilo(FunzionalitaEnum.CONF_TITOLARE_PROF.getValue());
			}
			if(funzionalita.getCodiceFunzionalita().equalsIgnoreCase(FunzionalitaEnum.CONF_DELEGATO_PROF.getValue())
				&& !FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equals(utente.getProfilo())) {
				log.info("Setto DELEGATO profilo :" + funzionalita.getCodiceFunzionalita());
				utente.setProfilo(FunzionalitaEnum.CONF_DELEGATO_PROF.getValue());
			}
			funzionalitaAbilitateList.add(funzionalita.getCodiceFunzionalita());
		}
		log.info("Profilo login :" + utente.getProfilo());
		utente.setFunzionalitaAbilitate(funzionalitaAbilitateList);

		return utente;
	}

	private Collocazione convertCollocazione(Richiedente richiedente) {
		Collocazione collocazione = new Collocazione();
		collocazione.setColCodice(richiedente.getCodiceCollocazione());
		collocazione.setColCodAzienda(richiedente.getCodiceAzienda());
		collocazione.setColDescAzienda(richiedente.getDescrizioneAzienda());

		String[] colDescrizioni = richiedente.getDescrizioneCollocazione().split("@");
		collocazione.setViewIndirizzo("");
		for (int i = 0; i < colDescrizioni.length; ++i) {
			if (i == 0) {
				collocazione.setViewNome(colDescrizioni[i]);
			} else {
				if (i != colDescrizioni.length - 1) {
					collocazione.setViewIndirizzo(collocazione.getViewIndirizzo() + colDescrizioni[i] + ", ");
				} else {
					collocazione.setViewIndirizzo(collocazione.getViewIndirizzo() + colDescrizioni[i]);
				}
			}
		}

		return collocazione;
	}
}
