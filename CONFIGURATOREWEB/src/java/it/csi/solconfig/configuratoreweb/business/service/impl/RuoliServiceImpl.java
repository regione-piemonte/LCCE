/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaRuoloModel;
import it.csi.solconfig.configuratoreweb.business.service.RuoliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloCompatibilitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloSelezionabileLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloUtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloUtenteDto;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloModel;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Service
@Transactional 
public class RuoliServiceImpl extends BaseServiceImpl implements RuoliService {

	@Autowired
	RuoloLowDao ruoloLowDao;

	@Autowired
	UtenteLowDao utenteLowDao;

	@Autowired
	RuoloUtenteLowDao ruoloUtenteLowDao;

	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	
	@Autowired
	private RuoloCompatibilitaLowDao ruoloCompatibilitaLowDao;
	
	@Autowired
	private RuoloSelezionabileLowDao ruoloSelezionabileLowDao;

	@Override
	public List<RuoloSelezionabileDto> ricercaRuoliSelezionabiliBy(Long idRuoloOperatore) {
		return ruoloLowDao.getRuoliSelezionabiliByRuolo(idRuoloOperatore);
	}

	@Override
	public PaginaDTO<RuoloDto> ricercaListaRuoli(RicercaRuoloModel ricercaRuoloModel, Boolean flagConfiguratore)
			throws Exception {

		List<RuoloDto> ruoloDtoList = new ArrayList<RuoloDto>();
		if (ricercaRuoloModel.getNumeroPagina() < 1 || ricercaRuoloModel.getNumeroElementi() < 1) {
			log.error("ERROR: NumeroElementi e/o numeroPagina non corretti");
			return new PaginaDTO<>();
		}

		PaginaDTO<RuoloDto> paginaDTO = new PaginaDTO<>();
		paginaDTO.setElementiTotali(0);
		RuoloDto ruoloDto = new RuoloDto();

		ruoloDto.setCodice(ricercaRuoloModel.getCodice());
		ruoloDto.setDescrizione(ricercaRuoloModel.getDescrizione());
		String statoAttivo = ricercaRuoloModel.getFlagAttivo();
		String statoNonAttivo = ricercaRuoloModel.getFlagNonAttivo();

		ruoloDtoList = ruoloLowDao.findRuoli(ruoloDto, statoAttivo, statoNonAttivo, flagConfiguratore);

		if (ruoloDtoList != null && !ruoloDtoList.isEmpty()) {
			/*
			 * Salvo numero elementi totali e mi ritaglio dalla lista solo gli elementi che
			 * corrispondono alla pagina ed al numero massimo di elementi da visualizzare
			 * Parto dal numero pagina - 1 * il numero elementi (offset) e arrivo fino al
			 * numero elementi visualizzabili (offset + limit)
			 */

			int numTotElementi = ruoloDtoList.size();
			int limit = ricercaRuoloModel.getNumeroElementi();
			int offset = (ricercaRuoloModel.getNumeroPagina() - 1) * limit;

			if (limit > ruoloDtoList.size() || (offset + limit) > ruoloDtoList.size()) {
				ruoloDtoList = ruoloDtoList.subList(offset, ruoloDtoList.size());
			} else {
				ruoloDtoList = ruoloDtoList.subList(offset, offset + limit);
			}

			paginaDTO.setElementi(ruoloDtoList);
			paginaDTO.setElementiTotali(numTotElementi);
			paginaDTO.setPagineTotali(
					(int) Math.ceil((float) paginaDTO.getElementiTotali() / ricercaRuoloModel.getNumeroElementi()));
		}

		return paginaDTO;
	}

	@Override
	public RuoloDto recuperaRuoloById(Long id) throws Exception {
		return ruoloLowDao.findByPrimaryId(id);
	}

	@Override
	public void modificaRuolo(RuoloModel ruoloModel, String cfOperatore, Data data) throws Exception{
		//Il ruolo da modificare e' stato inserito da Configuratore 
		
		RuoloDto ruoloDaModificare = recuperaRuoloById(ruoloModel.getId());
		if(ruoloDaModificare!=null) {
			RuoloDto editRuolo = ruoloDaModificare;
			Map<String, String> updatesRuoloUtenteAndAbilitazioni = new HashMap<String, String>();
		
			editRuolo.setCodice(ruoloModel.getCodice());
			editRuolo.setDescrizione(ruoloModel.getDescrizione());

			// operatore che effettua la modifica
			editRuolo.setUtenteDto(Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cfOperatore)));

			// dataAggiornamento: data corrente
			editRuolo.setDataAggiornamento(Utils.sysdate());

			// Attivazione ruolo
			if (ruoloModel.getFlagAttivo() == true) {
				editRuolo.setDataFineValidita(null);

			} else {
				// l'utente disattiva il ruolo e tutte le relative funzioni
				editRuolo.setDataFineValidita(Utils.sysdate());

				// aggiornamento tabella RuoloUtente e Abilitazioni
				updatesRuoloUtenteAndAbilitazioni = updateRuoloUtenteAndAbilitazioni(ruoloModel.getId(), cfOperatore);

			}

			ruoloLowDao.update(editRuolo);

			// ---
			// ruoli compatibili
			List<String> comp = ruoloLowDao.getRuoliCompatibili(ruoloModel.getId());

			List<String> newRuoliComp = new ArrayList<>(ruoloModel.getRuoliCompatibili());
			List<String> delRuoliComp = new ArrayList<>(comp);
			newRuoliComp.removeAll(comp); // tutti i nuovi
			delRuoliComp.removeAll(ruoloModel.getRuoliCompatibili()); // non ci sono piu
			
			newRuoliComp.forEach(e->ruoloLowDao.insertRuoloCompatibile(ruoloModel.getId(), e));
			ruoloLowDao.updateDataFineRuoliCompatibili(ruoloModel.getId(), delRuoliComp);

			// ruoli selezionabili
			List<RuoloSelezionabileDto> sel = ruoloLowDao.getRuoliSelezionabiliByRuolo(ruoloModel.getId());
			List<RuoloSelCollTipo> ruoloSelCollTipoM = ruoloModel.getRuoliSelCollTipo();
					
					//RuoloSelCollTipo.asListOf(ruoloModel.getRuoliSel(),
					//ruoloModel.getCollTipoSel()); 
					// ruoli da selezione web
			List<RuoloSelCollTipo> ruoloSelCollTipoDB = RuoloSelCollTipo.asListOf(sel); //ruoli da db

			List<RuoloSelCollTipo> newRuoliSel = new ArrayList<>(ruoloSelCollTipoM);
			List<RuoloSelCollTipo> delRuoliSel = new ArrayList<>(ruoloSelCollTipoDB);
			List<RuoloSelCollTipo> updRuoliSel = new ArrayList<>(ruoloSelCollTipoM);
			newRuoliSel.removeAll(ruoloSelCollTipoDB);  // i nuovi son quelli in piu dal dv
			delRuoliSel.removeAll(ruoloSelCollTipoM); // da rimuovere son quelli che ci son sul db ma non ci sono della selezione
			updRuoliSel.removeAll(newRuoliSel); // da aggiornare son quelli in selezione meno i nuovi
			
			newRuoliSel.stream().distinct().collect(Collectors.toList()).forEach(e->ruoloLowDao.insertRuoliSelezionabile(ruoloModel.getId(), e.getIdRuolo(), e.getIdCollTipo(), e.getId()));
			delRuoliSel.forEach(e->ruoloLowDao.updateDataFineRuoliSelezionabile(ruoloModel.getId(), e.getIdRuolo(), e.getId()));
			updRuoliSel.stream().distinct().collect(Collectors.toList()).forEach(e->ruoloLowDao.updateRuoliSelezionabile(ruoloModel.getId(), e.getIdRuolo(), e.getIdCollTipo(), e.getId()));

			//---
			ruoloLowDao.cleanRuoli(ruoloModel.getId());
			
//			
			String keyOper = editRuolo.getId().toString();
			String oggOper = ConstantsWebApp.MODIFICA_RUOLO;
			String ruoliUtente = updatesRuoloUtenteAndAbilitazioni.get("ruoliUtente");
			String abilitazioni = updatesRuoloUtenteAndAbilitazioni.get("abilitazioni");
			

			String ruoliSelCollTipoForLogAudit = "";
			for (RuoloSelCollTipo item:newRuoliSel) {
				List<RuoloSelezionabileDto> ruoliSelez = ruoloSelezionabileLowDao.findByIdRuoli(ruoloModel.getId(), Long.parseLong(item.getIdRuolo()));
				for(RuoloSelezionabileDto i:ruoliSelez)
					if (ruoliSelCollTipoForLogAudit == "")
						keyOper += ","+item.getId();
					else
						ruoliSelCollTipoForLogAudit = ruoliSelCollTipoForLogAudit+","+i.getId();
			}
			for (RuoloSelCollTipo item:delRuoliSel) {
				List<RuoloSelezionabileDto> ruoliSelez = ruoloSelezionabileLowDao.findByIdRuoli(ruoloModel.getId(), Long.parseLong(item.getIdRuolo()));
				for(RuoloSelezionabileDto i:ruoliSelez)
					if (ruoliSelCollTipoForLogAudit == "")
						keyOper += ","+item.getId();
					else
						ruoliSelCollTipoForLogAudit = ruoliSelCollTipoForLogAudit+","+i.getId();
			}
			for (RuoloSelCollTipo item:updRuoliSel) {
				List<RuoloSelezionabileDto> ruoliSelez = ruoloSelezionabileLowDao.findByIdRuoli(ruoloModel.getId(), Long.parseLong(item.getIdRuolo()));
				for(RuoloSelezionabileDto i:ruoliSelez)
					if (ruoliSelCollTipoForLogAudit == "")
						keyOper += ","+item.getId();
					else
						ruoliSelCollTipoForLogAudit = ruoliSelCollTipoForLogAudit+","+i.getId();
			}
			
			if(ruoliUtente != null && !ruoliUtente.isEmpty()) {
				oggOper = ConstantsWebApp.MODIFICA_RUOLO_RELAZ_UTENTE_RUOLO;
				keyOper += "-"+ruoliUtente;
				if(abilitazioni != null && !abilitazioni.isEmpty()) {
					oggOper = ConstantsWebApp.MODIFICA_RUOLO_RELAZ_UTENTE_RUOLO_ABILITAZIONI;
					keyOper += "-"+abilitazioni;
				}
			}
			
			// Scrittura log Audit
            setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_RUOLO, null, 
            		UUID.randomUUID().toString(), keyOper, oggOper, data);
			
		} 

	}

	private List<AbilitazioneDto> updateAbilitazioni(String cfOperatore, RuoloUtenteDto ruoloUtente) {
		List<AbilitazioneDto> abilitazioneList = new ArrayList<AbilitazioneDto>();
		abilitazioneList = (List<AbilitazioneDto>) abilitazioneLowDao.findByRuoloUtenteDto(ruoloUtente);
		if (abilitazioneList != null && !abilitazioneList.isEmpty()) {
			for (AbilitazioneDto abilitazioneDto : abilitazioneList) {
				abilitazioneDto.setDataFineValidita(Utils.sysdate());
				abilitazioneDto.setDataAggiornamento(Utils.sysdate());
				abilitazioneDto.setCfOperatore(cfOperatore);
				abilitazioneLowDao.update(abilitazioneDto);
			}
		}
		return abilitazioneList;
	}

	private Map<String, String> updateRuoloUtenteAndAbilitazioni(Long idRuolo, String cfOperatore) throws Exception {
		Map<String, String> updates = new HashMap<String, String>();
		List<RuoloUtenteDto> listaUtentiAssociati = new ArrayList<RuoloUtenteDto>();
		List<AbilitazioneDto> abilitazioniUpdates = new ArrayList<AbilitazioneDto>();
		
		listaUtentiAssociati = (List<RuoloUtenteDto>)ruoloUtenteLowDao.findByIdRuolo(idRuolo);
		
		if(listaUtentiAssociati!=null && !listaUtentiAssociati.isEmpty()) {
			for(RuoloUtenteDto ruoloUtente : listaUtentiAssociati) {
				ruoloUtente.setDataFineValidita(Utils.sysdate());
				ruoloUtente.setDataAggiornamento(Utils.sysdate());
				ruoloUtente.setUtenteDto(Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cfOperatore)));
				ruoloUtenteLowDao.update(ruoloUtente);
				List<AbilitazioneDto> abilitazioniList = updateAbilitazioni(cfOperatore, ruoloUtente);
				abilitazioniUpdates.addAll(abilitazioniList);
			}
			String keyOperRuoliUtente = listaUtentiAssociati.stream().map(RuoloUtenteDto::getId).map(String::valueOf).collect(Collectors.joining(","));
			updates.put("ruoliUtente", keyOperRuoliUtente);
			if(!abilitazioniUpdates.isEmpty()) {
				String keyOperAbilitazioni = abilitazioniUpdates.stream().map(AbilitazioneDto::getId).map(String::valueOf).collect(Collectors.joining(","));
				updates.put("abilitazioni", keyOperAbilitazioni);
			}
		}
		
		
		return updates;
	}

}
