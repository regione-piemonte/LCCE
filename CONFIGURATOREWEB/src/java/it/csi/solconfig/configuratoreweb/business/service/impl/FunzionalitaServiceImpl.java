/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.PermessoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.service.FunzionalitaService;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaFunzionalitaModel;
import it.csi.solconfig.configuratoreweb.util.TipologiaDatoEnum;

@Service
@Transactional
public class FunzionalitaServiceImpl extends BaseServiceImpl implements FunzionalitaService {

	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;
	
	@Autowired
	private PermessoFunzionalitaLowDao permessoFunzionalitaLowDao;

	@Override
	public PaginaDTO<FunzionalitaDto> ricercaFunzionalita(RicercaFunzionalitaModel ricercaFunzionalitaModel) {
		
		FunzionalitaDto funzTosearch = new FunzionalitaDto();
		funzTosearch.setCodiceFunzione(ricercaFunzionalitaModel.getCodice());
		funzTosearch.setDescrizioneFunzione(ricercaFunzionalitaModel.getDescrizione());
		ApplicazioneDto appToFunz = new ApplicazioneDto();
		appToFunz.setId(ricercaFunzionalitaModel.getIdApplicazione());
		funzTosearch.setApplicazioneDto(appToFunz);
		List<FunzionalitaDto> result = funzionalitaLowDao.findFunzionalita(funzTosearch);

		PaginaDTO<FunzionalitaDto> paginaDTO = new PaginaDTO<>();
		paginaDTO.setElementi(result);
		paginaDTO.setElementiTotali(result.size());
		paginaDTO.setPagineTotali(
				(int) Math.ceil((float) paginaDTO.getElementiTotali() / ricercaFunzionalitaModel.getNumeroElementi()));

		return paginaDTO;
	}
	
	@Override
	public boolean existsFunzionalita(String codice) {
		FunzionalitaDTO result = funzionalitaLowDao.findFunzionalitaByCodice(codice);
		boolean rstl = (result!=null);
		return rstl;
	}

	@Override
	public long inserisciFunzionalita(FunzionalitaModel funzionalitaModel) {
		FunzionalitaDto funz = getFunz(funzionalitaModel);
		FunzionalitaDto i = funzionalitaLowDao.insert(funz);
		return i.getIdFunzione();
	}

	private static String getSN(String value) {
		return (value != null && "S".equalsIgnoreCase("S") ? "S" : "N");
	}

	private static FunzionalitaDto getFunz(FunzionalitaModel s) {
		FunzionalitaDto d = new FunzionalitaDto();
//		d.setCodice(s.getCodice());
//		d.setDescrizione(s.getDescrizione());
//
//		d.setPinRichiesto(getSN(s.getPinRichiesto()));
//		d.setBottone(getSN(s.getBottone()));
//
//		d.setUrlVerifyLoginConditions(s.getUrlverifyloginconditions());
//		d.setDescrizioneWebapp(s.getDescrizioneWebapp());
//		d.setPathImmagine(s.getPathImmagine());
//		d.setRedirectUrl(s.getRedirectUrl());
//		d.setFlagConfiguratore("S");

		return d;
	}

	@Override
	public void loadFunzionalitaById(long id, FunzionalitaModel model) {
		FunzionalitaDto funz = funzionalitaLowDao.findByPrimaryId(id);
		//List<PermessoFunzionalitaDto> permFunz = permessoFunzionalitaLowDao.findPermessiByFunzId(id);
		
		for(PermessoFunzionalitaDto permFunz: permessoFunzionalitaLowDao.findPermessiByFunzId(id)) {

			if (TipologiaDatoEnum.DATI_ANAGRAFICI.getValue().equals(permFunz.getTipologiaDato().getCodice())) {
				model.setDatiAnagrafici(permFunz.getPermesso().getId().toString());
			}
			if (TipologiaDatoEnum.DATI_PRESCRITTIVI.getValue().equals(permFunz.getTipologiaDato().getCodice())) {
				model.setDatiPrescrittivi(permFunz.getPermesso().getId().toString());
			}
			if (TipologiaDatoEnum.DATI_CLINICI.getValue().equals(permFunz.getTipologiaDato().getCodice())) {
				model.setDatiClinici(permFunz.getPermesso().getId().toString());
			}
			if (TipologiaDatoEnum.DATI_DI_CONSENSO.getValue().equals(permFunz.getTipologiaDato().getCodice())) {
				model.setDatiConsenso(permFunz.getPermesso().getId().toString());
			}
			if (TipologiaDatoEnum.DATI_AMMINISTRATIVI.getValue().equals(permFunz.getTipologiaDato().getCodice())) {
				model.setDatiAmministrativi(permFunz.getPermesso().getId().toString());
			}
		}
		
		model.setId(funz.getIdFunzione());
		model.setCodice(funz.getCodiceFunzione());
		model.setDescrizione(funz.getDescrizioneFunzione());
		model.setApplicazione(funz.getApplicazioneDto().getDescrizione());
		model.setIdApplicazione(funz.getApplicazioneDto().getId().toString());
		model.setFunzionalitaAttivo(false);
		if (funz.getDataFineValidita() == null)
			model.setFunzionalitaAttivo(true);
		
	}

}
