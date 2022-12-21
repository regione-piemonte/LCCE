/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.impl.RuoloProfiloLowDaoImpl;
import it.csi.solconfig.configuratoreweb.business.service.InserisciProfiliService;
import it.csi.solconfig.configuratoreweb.presentation.model.InserisciProfiloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Service
@Transactional
public class InserisciProfiliServiceImpl extends BaseServiceImpl implements InserisciProfiliService {

	public static final String PROF = "PROF";

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;
	
	@Autowired
	RuoloProfiloLowDaoImpl ruoloProfiloLowDaoImpl;
	
	@Autowired
	RuoloLowDao ruoloLowDao;

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;

	@Autowired
	TipoFunzionalitaLowDao tipoFunzionalitaLowDao;

	public static final Long ID_RADICE_PROFILO = 3L;

	@Override
	public List<TreeFunzionalitaDto> findByApplicazioneTree(ApplicazioneDto applicazioneDto) throws Exception {
		List<TreeFunzionalitaDto> treeFunzionalitaDtoList = new ArrayList<TreeFunzionalitaDto>();
		treeFunzionalitaDtoList = treeFunzionalitaLowDao.findByIdApplicazione(applicazioneDto);

		return treeFunzionalitaDtoList;

	}

	@Override
	public boolean checkCodicePresente(FunzionalitaDto funzionalitaDto) throws Exception {
		FunzionalitaDto check = new FunzionalitaDto();
		check = (FunzionalitaDto) Utils.getFirstRecord(funzionalitaLowDao.findByFilter(funzionalitaDto));
		if (check == null) {
			return true;
		}
		return false;
	}

	@Override
	public String inserimentoProfilo(InserisciProfiloModel inserisciProfiloModel, List<Nodo> listaNodiSelezionati,
			String codiceFiscaleUtente) throws Exception {
		FunzionalitaDto funzionalitaDto = popolaInserisciFunzionalita(inserisciProfiloModel, codiceFiscaleUtente);
		TreeFunzionalitaDto treeFunzionalitaProfilo = popolaTreeFunzionalita(inserisciProfiloModel, funzionalitaDto, codiceFiscaleUtente);
		// Lista di Long dove vengono salvati gli id delle funzionalita
		List<Long> idInseriti = new ArrayList<Long>();
		idInseriti.add(funzionalitaDto.getIdFunzione());
		//Inserisco i figli controllando cosa ha selezionato l'utente
		List<TreeFunzionalitaDto> treeInseriti = popolaFigliProfilo(listaNodiSelezionati, idInseriti, inserisciProfiloModel.getActive(),
				treeFunzionalitaProfilo, codiceFiscaleUtente);
		String ruoloProf = inserisciRuoliProfilo(funzionalitaDto.getIdFunzione(), inserisciProfiloModel.getRuoli());
		
		String oggOperInsProfilo = funzionalitaDto.getIdFunzione().toString()+"-"+treeFunzionalitaProfilo.getIdTreeFunzione().toString();
		if(!Utils.isEmptyList(treeInseriti))
			oggOperInsProfilo += "," + treeInseriti.stream().map(TreeFunzionalitaDto::getIdTreeFunzione).map(String::valueOf).collect(Collectors.joining(","));
		
		oggOperInsProfilo += "-" + ruoloProf;
		
		return oggOperInsProfilo;
	}

	private String inserisciRuoliProfilo(Long idFunz, List<String> ruoli) throws Exception {
		String rtnValue = "";
		log.log(Level.INFO, "Entro in inserisciRuoliProfilo");
		List<RuoloDto> ruoliDto = new ArrayList<RuoloDto>();
		int ruoloCounter = 0;
		//Estraggo gli id dei ruoli
		for (String r : ruoli){
			if (r != null && !r.isEmpty()) {
				Long idRuolo = Long.parseLong(r);
				RuoloDto ruolo = ruoloLowDao.findByPrimaryId(idRuolo);
				ruoliDto.add(ruolo);
				ruoloCounter++;
			}
		}
		if (ruoloCounter == 0) {
			log.log(Level.ERROR, "Nessun ruolo da inserire.");
			throw new Exception("Nessun ruolo da inserire.");
		} else {
			//Tiro fuori i ruoli presenti.
			Collection<RuoloProfilo> profili = ruoloProfiloLowDaoImpl.findByIdFunz(idFunz);
			List<RuoloProfilo> listProfili;
			if (profili instanceof List)
				listProfili = (List<RuoloProfilo>)profili;
			else
				listProfili = new ArrayList<RuoloProfilo>(profili);
			
			ruoliDto.forEach(r -> insertRuoloProfiloEntity(r, idFunz));
			log.log(Level.INFO, "Ok, ruoli inseriti"); 
			
			for (RuoloDto r:ruoliDto) {
				List<RuoloProfilo> listRuoloProfilo = ruoloProfiloLowDaoImpl.findByIdFunzIdRuolo(r.getId(), idFunz);
				for (RuoloProfilo rpl:listRuoloProfilo) {
					
					rtnValue += ","+rpl.getId();
				}
			}

			log.log(Level.INFO, "Ok, ruoli correttamente gestiti");
		}
		return rtnValue;
	}
	
	private void insertRuoloProfiloEntity(RuoloDto r, Long idFunz) {
		RuoloProfilo rpToInsert = new RuoloProfilo();
		rpToInsert.setDataInizioValidita(Utils.sysdate());
		rpToInsert.setDataFineValidita(null);
		rpToInsert.setDataInserimento(Utils.sysdate());
		rpToInsert.setRuolo(r);
		FunzionalitaDto funzDTO = new FunzionalitaDto();
		funzDTO.setIdFunzione(idFunz); //Non mi serve altro
		rpToInsert.setFunzionalita(funzDTO);
		ruoloProfiloLowDaoImpl.insert(rpToInsert);
	}

	/**
	 * @param listaNodiSelezionati
	 */
	public List<TreeFunzionalitaDto> popolaFigliProfilo(List<Nodo> listaNodiSelezionati, List<Long> idInseriti, boolean attivo,
			TreeFunzionalitaDto treeFunzionalitaProfilo, String codiceFiscaleUtente) {
		List<TreeFunzionalitaDto> treeInseriti = new ArrayList<TreeFunzionalitaDto>();
		for (Nodo nodoSelezionato : listaNodiSelezionati) {
			//Gestisce e controlla ogni nodo dell'albero selezionato e lo inserisce 
			controlloPopolaFigliProfilo(nodoSelezionato, idInseriti, attivo, treeFunzionalitaProfilo, codiceFiscaleUtente, treeInseriti);

		}
		return treeInseriti;
	}

	/**
	 * @param listaNodiSelezionati
	 * @param idInseriti
	 * @param nodoSelezionato
	 * @param treeInseriti 
	 */
	public void controlloPopolaFigliProfilo(Nodo nodoSelezionato, List<Long> idInseriti, boolean attivo,
			TreeFunzionalitaDto treeFunzionalitaProfilo, String codiceFiscaleUtente, List<TreeFunzionalitaDto> treeInseriti) {
		TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
		//Controlla che il nodo corrente sia una funzionalita' del profilo 
		//Iterando l'ascesa dei livelli dell'albero fino al nodo del profilo (il primo livello, avente nodo con codice "APP")
		if (nodoSelezionato.getHasParent() && "FUNZ".equals(nodoSelezionato.getParent().getTreeFunzionalitaDto()
				.getFunzionalitaDto().getTipoFunzionalitaDto().getCodiceTipoFunzione())) {
			controlloPopolaFigliProfilo(nodoSelezionato.getParent(), idInseriti, attivo, treeFunzionalitaProfilo,
					codiceFiscaleUtente, treeInseriti);
		}
		//Inserisce il nodo corrente quando questo non e' stato gia' inserito e non e' il nodo del profilo
		if (!idInseriti.contains(nodoSelezionato.getTreeFunzionalitaDto().getFunzionalitaDto().getIdFunzione())) {
			treeFunzionalitaDto.setDataInizioValidita(Utils.sysdate());
			treeFunzionalitaDto.setDataInserimento(Utils.sysdate());
			treeFunzionalitaDto.setDataAggiornamento(Utils.sysdate());
			if (attivo) {
				treeFunzionalitaDto.setDataFineValidita(null);
			} else {
				treeFunzionalitaDto.setDataFineValidita(Utils.sysdate());
			}
			treeFunzionalitaDto.setFunzionalitaDto(nodoSelezionato.getTreeFunzionalitaDto().getFunzionalitaDto());
			treeFunzionalitaDto.setCfOperatore(codiceFiscaleUtente);
			if (!"FUNZ".equals(nodoSelezionato.getParent().getTreeFunzionalitaDto().getFunzionalitaDto()
					.getTipoFunzionalitaDto().getCodiceTipoFunzione())) {

				treeFunzionalitaDto.setFunzionalitaTreePadreDto(treeFunzionalitaProfilo);

			} else {
				treeFunzionalitaDto.setFunzionalitaTreePadreDto(nodoSelezionato.getParent().getTreeFunzionalitaDto());
			}
			idInseriti.add(treeFunzionalitaDto.getFunzionalitaDto().getIdFunzione());
			nodoSelezionato.setTreeFunzionalitaDto(treeFunzionalitaDto);
			TreeFunzionalitaDto insert = treeFunzionalitaLowDao.insert(treeFunzionalitaDto);
			treeInseriti.add(insert);
		}

	}

	/**
	 * @param inserisciProfiloModel
	 * @param funzionalitaDto
	 * @throws Exception
	 */
	public TreeFunzionalitaDto popolaTreeFunzionalita(InserisciProfiloModel inserisciProfiloModel,
			FunzionalitaDto funzionalitaDto, String codiceFiscaleUtente) throws Exception {
		TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
		treeFunzionalitaDto.setFunzionalitaDto(funzionalitaDto);
		treeFunzionalitaDto.setDataInizioValidita(Utils.sysdate());
		treeFunzionalitaDto.setDataInserimento(Utils.sysdate());
		treeFunzionalitaDto.setDataAggiornamento(Utils.sysdate());
		if (inserisciProfiloModel.getActive()) {
			treeFunzionalitaDto.setDataFineValidita(null);
		} else {
			treeFunzionalitaDto.setDataFineValidita(Utils.sysdate());
		}
		treeFunzionalitaDto.setCfOperatore(codiceFiscaleUtente);
		return treeFunzionalitaLowDao.insert(treeFunzionalitaDto);
	}

	/**
	 * @param inserisciProfiloModel
	 * @return
	 * @throws Exception
	 */
	public FunzionalitaDto popolaInserisciFunzionalita(InserisciProfiloModel inserisciProfiloModel,
			String codiceFiscaleUtente) throws Exception {
		FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
		ApplicazioneDto applicazioneDto = new ApplicazioneDto();
		applicazioneDto.setId(inserisciProfiloModel.getidApplicazione());
		funzionalitaDto.setApplicazioneDto(applicazioneDto);
		funzionalitaDto.setCodiceFunzione(inserisciProfiloModel.getCodice());
		funzionalitaDto.setDataInserimento(Utils.sysdate());
		funzionalitaDto.setDataInizioValidita(Utils.sysdate());
		funzionalitaDto.setDataAggiornamento(Utils.sysdate());
		if (!inserisciProfiloModel.getActive()) {
			funzionalitaDto.setDataFineValidita(Utils.sysdate());
		}
		funzionalitaDto.setDescrizioneFunzione(inserisciProfiloModel.getDescrizione());
		TipoFunzionalitaDto tipoFunzionalitaDto = new TipoFunzionalitaDto();
		tipoFunzionalitaDto.setCodiceTipoFunzione(PROF);
		tipoFunzionalitaDto = Utils.getFirstRecord(tipoFunzionalitaLowDao.findByFilter(tipoFunzionalitaDto));
		funzionalitaDto.setTipoFunzionalitaDto(tipoFunzionalitaDto);
		funzionalitaDto.setCfOperatore(codiceFiscaleUtente);
		funzionalitaDto = funzionalitaLowDao.insert(funzionalitaDto);
		return funzionalitaDto;
	}

	@Override
	public List<FunzionalitaDto> prelevaFunzionalitaApplicazione(List<ApplicazioneDto> applicazioneDtoList)
			throws Exception {
		List<FunzionalitaDto> funzionalitaDtoList = new ArrayList<FunzionalitaDto>();
		for (ApplicazioneDto dto : applicazioneDtoList) {
			//funzionalitaDtoList.addAll(funzionalitaLowDao.findFunzionalitaApplicazione(dto));
		}
		return funzionalitaDtoList;
	}

	@Override
	public List<TreeFunzionalitaDto> prelevaFunzionalitaTreePadre(List<FunzionalitaDto> funzionalitaDtoList)
			throws Exception {

		List<TreeFunzionalitaDto> treeFunzionalitaDtoList = new ArrayList<TreeFunzionalitaDto>();
		for (FunzionalitaDto dto : funzionalitaDtoList) {

		}

		return treeFunzionalitaDtoList;
	}

	@Override
	public Long getIdRadiceProfilo() {
		// TODO Auto-generated method stub
		return ID_RADICE_PROFILO;
	}
}
