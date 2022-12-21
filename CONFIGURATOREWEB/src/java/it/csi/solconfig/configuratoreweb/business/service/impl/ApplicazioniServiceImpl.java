/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TreeFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneCollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.impl.FunzionalitaLowDaoImpl;
import it.csi.solconfig.configuratoreweb.business.service.ApplicazioniService;
import it.csi.solconfig.configuratoreweb.presentation.model.ApplicazioneModel;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaApplicazioneModel;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Service
@Transactional
public class ApplicazioniServiceImpl extends BaseServiceImpl implements ApplicazioniService {

	@Autowired
	private ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;

	@Autowired
	private TreeFunzionalitaLowDao treeFunzionalitaLowDao;

	@Autowired
	private TipoFunzionalitaLowDao tipoFunzionalitaLowDao;

	@Autowired
	private ApplicazioneCollocazioneLowDao applicazioneCollocazioneLowDao;

	@Override
	public PaginaDTO<ApplicazioneDto> ricercaApplicazioni(RicercaApplicazioneModel ricercaApplicazioneModel) {
		List<ApplicazioneDto> result = applicazioneLowDao.findApplicazione(ricercaApplicazioneModel.getCodice(),
				ricercaApplicazioneModel.getDescrizione());

		PaginaDTO<ApplicazioneDto> paginaDTO = new PaginaDTO<>();
		paginaDTO.setElementi(result);
		paginaDTO.setElementiTotali(result.size());
		paginaDTO.setPagineTotali(
				(int) Math.ceil((float) paginaDTO.getElementiTotali() / ricercaApplicazioneModel.getNumeroElementi()));

		return paginaDTO;
	}

	@Override
	public ApplicazioneDto inserisciApplicazione(ApplicazioneModel applicazioneModel, String cfOperatore) {
		ApplicazioneDto app = toDto(new ApplicazioneDto(), applicazioneModel);
		ApplicazioneDto i = applicazioneLowDao.insert(app);

		checkAndInserisciFunzionalitaFor(i, cfOperatore);

		List<String> collocazioni = applicazioneModel.getCollocazioni();

		// inserisce le collocazioni
		collocazioni.forEach(c -> insertCollocazione(app.getId(), c));

		return findApplicazioneById(i.getId());
	}

	private void checkAndInserisciFunzionalitaFor(ApplicazioneDto i, String cfOperatore) {
		List<FunzionalitaDto> funz = funzionalitaLowDao.findFunzionalitaApplicazione(i);
		if (funz == null || funz.size() == 0) {

			FunzionalitaDto f = new FunzionalitaDto();
			f.setApplicazioneDto(i);
			f.setCodiceFunzione(i.getCodice());
			f.setDescrizioneFunzione(i.getDescrizione());
			f.setDataInizioValidita(Utils.sysdate());
			f.setTipoFunzionalitaDto(tipoFunzionalitaLowDao
					.findByCodiceTipoFunzione(FunzionalitaLowDaoImpl.CODICE_TIPO_FUNZIONALITA_APP));
			f.setDataInizioValidita(Utils.sysdate());
			f.setDataAggiornamento(Utils.sysdate());
			f.setCfOperatore(cfOperatore);
			f = funzionalitaLowDao.insert(f);

			TreeFunzionalitaDto tree = new TreeFunzionalitaDto();
			tree.setFunzionalitaDto(f);
			tree.setCfOperatore(cfOperatore);
			tree.setDataAggiornamento(Utils.sysdate());
			tree.setDataInizioValidita(Utils.sysdate());
			treeFunzionalitaLowDao.insert(tree);
		}
	}

	private static String getSN(String value) {
		return (value != null && "S".equalsIgnoreCase("S") ? "S" : "N");
	}

	private static ApplicazioneDto toDto(ApplicazioneDto dest, ApplicazioneModel source) {
		if (dest.getCodice() == null || dest.getCodice().trim().length() > 0) {
			dest.setCodice(source.getCodice().toUpperCase());
		}

		dest.setDescrizione(source.getDescrizione());

		dest.setPinRichiesto(getSN(source.getPinRichiesto()));
		dest.setBottone(getSN(source.getBottone()));

		if (source.getUrlverifyloginconditions() != null && source.getUrlverifyloginconditions().trim().length() == 0) {
			dest.setUrlVerifyLoginConditions(null);
		} else {
			dest.setUrlVerifyLoginConditions(source.getUrlverifyloginconditions());
		}
		dest.setDescrizioneWebapp(source.getDescrizioneWebapp());
		dest.setPathImmagine(source.getPathImmagine());
		dest.setRedirectUrl(source.getRedirectUrl());
		dest.setOscurato(source.getOscurato());

		dest.setFlagConfiguratore("S");

		return dest;
	}

	@Override
	public ApplicazioneDto findApplicazioneById(long id) {
		ApplicazioneDto app = applicazioneLowDao.findByPrimaryId(id);
		return app;

	}

	@Override
	public Collection<ApplicazioneDto> findApplicazioneByCodice(String codice) {
		return applicazioneLowDao.findByCodice(new ApplicazioneDto(), codice);
	}

	@Override
	public ApplicazioneDto aggiornaApplicazione(ApplicazioneModel applicazioneModel, String cfOperatore) {
		long idapp = applicazioneModel.getId();
		ApplicazioneDto app = toDto(findApplicazioneById(idapp), applicazioneModel);
		List<String> collocazioniOnDb = getCollocazioni(app);
		List<String> collocazioniOnForm = new ArrayList<>(applicazioneModel.getCollocazioni());

		List<String> collocazioniNew = new ArrayList<>(applicazioneModel.getCollocazioni());
		List<String> collocazioniToDel = new ArrayList<>(collocazioniOnDb);

		collocazioniNew.removeAll(collocazioniOnDb);
		collocazioniToDel.removeAll(collocazioniOnForm);
		//
		applicazioneLowDao.update(app);

		checkAndInserisciFunzionalitaFor(app, cfOperatore);

		// inserisce le collocazioni
		collocazioniNew.forEach(c -> insertCollocazione(idapp, c));

		//
		applicazioneCollocazioneLowDao.deleteById(idapp, collocazioniToDel);

		return app; // findApplicazioneById(idapp);
	}

	private void insertCollocazione(Long idApplicazione, String c) {
		if (c != null && c.trim().length() > 0 && Utils.isValidNumber(c)) {
			applicazioneCollocazioneLowDao.insert(idApplicazione, Long.parseLong(c));
		}
	}

	@Override
	public Map<String, String> getIcons() {
		Map<String, String> icons = new LinkedHashMap<>();

		icons.put(getDefaultPath(), "Generica");

		icons.put("im/icone/ruoli/ico_cup.svg", "CUP");
		icons.put("im/icone/ruoli/ico_fascicolo_sanitario.svg", "Fascicolo Sanitario");
		icons.put("im/icone/ruoli/ico_pagamento_ticket.svg", "Pagamento Ticket");
		icons.put("im/icone/ruoli/ico_prenotazioni_esami.svg", "Prenotazioni Esami");
		icons.put("im/icone/ruoli/ico_professionista_sociale.svg", "Professionista Sociale");

		icons.put("im/icone/ruoli/ico_ritiro_referti.svg", "Ritiro referti");

		return icons;
	}

	@Override
	public String getDefaultPath() {
		return "im/icone/ruoli/ico_generica.svg";
	}

	@Override
	public List<String> getCollocazioni(ApplicazioneDto app) {
		return app.getApplicazioneCollocazioneList().stream().map(e -> getIdCollocazione(e.getCollocazioneDto()))
				.collect(Collectors.toList());
	}

	private String getIdCollocazione(CollocazioneDto collocazioneDto) {
		return (collocazioneDto != null && collocazioneDto.getColId() != null ? collocazioneDto.getColId().toString()
				: "");
	}

	@Override
	public List<String> getCollocazioniByIdApp(Long id) {
		return applicazioneCollocazioneLowDao.findIdCollocazioniByIdApplicazione(id).stream().map(l -> l.toString())
				.collect(Collectors.toList());
	}

}
