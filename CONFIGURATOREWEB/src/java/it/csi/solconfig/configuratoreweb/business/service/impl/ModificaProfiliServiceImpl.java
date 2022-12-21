/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.service.ModificaProfiliService;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BaseDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.impl.RuoloProfiloLowDaoImpl;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.ModificaProfiloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Service
@Transactional
public class ModificaProfiliServiceImpl extends BaseServiceImpl implements ModificaProfiliService {

	public static final String PROF = "PROF";

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	
	@Autowired
	RuoloProfiloLowDaoImpl ruoloProfiloLowDaoImpl;

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	TipoFunzionalitaLowDao tipoFunzionalitaLowDao;

	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	
	@Autowired
	RuoloLowDao ruoloLowDao;

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Override
	public FunzionalitaDto prelevaFunzionalita(FunzionalitaDto funzionalitaDto) throws Exception {
		return funzionalitaLowDao.findByPrimaryId(funzionalitaDto.getIdFunzione());
	}

	@Override
	public List<Long> organizzaAlberoFunzionalitaCheckate(TreeFunzionalitaDto dto, List<Long> idFunzionalitaAlbero,
			Nodo alberoFunzionalita) throws Exception {

		List<TreeFunzionalitaDto> idfigli = treeFunzionalitaLowDao.findByIdPadreFunzionalita(dto);
		List<Nodo> figli = new ArrayList<Nodo>();
		if (idfigli != null && !idfigli.isEmpty()) {

			for (TreeFunzionalitaDto idfiglio : idfigli) {
				idFunzionalitaAlbero.add(idfiglio.getFunzionalitaDto().getIdFunzione());

				Nodo figlio = new Nodo();
				figlio.setTreeFunzionalitaDto(idfiglio);
				figli.add(figlio);

			}
			alberoFunzionalita.setFigli(figli);
			for (Nodo figlio : alberoFunzionalita.getFigli()) {
				figlio.setParent(alberoFunzionalita);
				organizzaAlberoFunzionalitaCheckate(figlio.getTreeFunzionalitaDto(), idFunzionalitaAlbero,
						figlio);

			}
		}
		return idFunzionalitaAlbero;
	}

	@Override
	public List<TreeFunzionalitaDto> findByApplicazioneTree(ApplicazioneDto applicazioneDto) throws Exception {
		List<TreeFunzionalitaDto> treeFunzionalitaDtoList = new ArrayList<TreeFunzionalitaDto>();
		treeFunzionalitaDtoList = treeFunzionalitaLowDao.findByIdApplicazione(applicazioneDto);

		return treeFunzionalitaDtoList;

	}

	@Override
	public TreeFunzionalitaDto prelevaTreeFunzionalita(FunzionalitaDto funzionalitaDto) throws Exception {
//		TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
//		treeFunzionalitaDto.setFunzionalitaDto(funzionalitaDto);
//		return Utils.getFirstRecord(treeFunzionalitaLowDao.findByFilter(treeFunzionalitaDto));
		return Utils.getFirstRecord(treeFunzionalitaLowDao.findByIdFunzione(funzionalitaDto.getIdFunzione()));
	}
/*	
	@Override
	public TreeFunzionalitaDto prelevaTreeFunzionalita(FunzionalitaDto funzionalitaDto) throws Exception {
		TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
		treeFunzionalitaDto.setFunzionalitaDto(funzionalitaDto);
		Collection<TreeFunzionalitaDto> tree = treeFunzionalitaLowDao.findByFunzionalitaDto(funzionalitaDto);
		TreeFunzionalitaDto firstRecord = Utils.getFirstRecord(tree);
		return firstRecord;
		
	//	return Utils.getFirstRecord(treeFunzionalitaLowDao.findByFilter(treeFunzionalitaDto));
	}
*/

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void modificaProfilo(ModificaProfiloModel modificaProfiloModel, List<Nodo> listaNodiSelezionati,
			List<Nodo> listaNodiFunzionalitaProfilo, TreeFunzionalitaDto treeFunzionalitaProfiloDto,
			String codiceFiscale, Data data) throws Exception {

		Map<String, String> records = popolaInserisciFunzionalita(modificaProfiloModel, codiceFiscale,treeFunzionalitaProfiloDto.getFunzionalitaDto());
		
		String funzionalitaDto = records.get("funz");
		String treeFunzionalitaDto = records.get("treeFunz");
				
		// Lista di Long dove vengono salvati gli id delle funzionalita
		List<Long> idInseriti = new ArrayList<Long>();
		idInseriti.add(Long.valueOf(funzionalitaDto));
		List<TreeFunzionalitaDto> treeModificati = modificaAlberoProfilo(listaNodiSelezionati, listaNodiFunzionalitaProfilo, idInseriti,
				modificaProfiloModel.getActive(), treeFunzionalitaProfiloDto, codiceFiscale);
		
		String ruoloProf = modificaRuoliProfilo(treeFunzionalitaProfiloDto.getFunzionalitaDto().getIdFunzione(), modificaProfiloModel.getRuoli());
		
		String keyOperModProfilo = funzionalitaDto + "-" + treeFunzionalitaDto;
		if(!Utils.isEmptyList(treeModificati)) {
			keyOperModProfilo += ",";
			keyOperModProfilo += treeModificati.stream().map(TreeFunzionalitaDto::getIdTreeFunzione).map(String::valueOf).collect(Collectors.joining(","));
		}
		
		String oggOperModProfilo = ConstantsWebApp.MODIFICA_PROFILO_AUDIT;
		if(records.containsKey("abil")) {
			keyOperModProfilo += "-" + records.get("abil");
			oggOperModProfilo = ConstantsWebApp.MODIFICA_PROFILO_AUDIT_RELAZ_UTENTE;
		}
		
		oggOperModProfilo += "-" + ruoloProf;
		
		// Scrittura log Audit
        setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_PROFILO_AUDIT, null, 
        		UUID.randomUUID().toString(), keyOperModProfilo, oggOperModProfilo, data);
	}
	
	private String modificaRuoliProfilo(Long idFunz, List<String> ruoli) throws Exception {
		String rtnValue = "";
		log.log(Level.INFO, "Entro in modificaRuoliProfilo");
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
			
			if (listProfili.isEmpty()) {
				//Ok, faccio l'inserimento di ruoli dentro auth_r_ruolo_profilo
				
				ruoliDto.forEach(r -> insertRuoloProfiloEntity(r, idFunz));
				log.log(Level.INFO, "Ok, ruoli inseriti"); 
			} else {
				//Ok, faccio la modifica
				// 1. Cancello quelle vecchie che non ci sono piu'
				listProfili.forEach(rp -> unsetRuoloProfiloEntity(rp, ruoliDto));
				// 2. Inserisco quelle nuove che non ci sono ancora, 
				ruoliDto.forEach(r -> insertRuoloProfiloEntity(r, idFunz));
				log.log(Level.INFO, "Ok, ruoli modificati");
			}
			
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
	
	private void unsetRuoloProfiloEntity(RuoloProfilo rp, List<RuoloDto> ruoliDto) {
		if (rp.getDataFineValidita() == null) {
			boolean itemExists = ruoliDto.stream().anyMatch(cRuoloDTO -> cRuoloDTO.getId() ==  rp.getRuolo().getId()); //rp.getRuolo() e' presente in ruoliDto? cRuoloDTO e' di tipo RuoloDTO
			//se itemExists e' true, significa che il ruolo e' ancora presente, 
			//altrimenti significa che l'utente l'ha eliminato, e quindi modifico la data di finevalidita' e di fatto lo cancello.
			if (!itemExists) {
				Long tempIdRuoloProfilo = rp.getId();
				RuoloProfilo tmpRP = ruoloProfiloLowDaoImpl.findByPrimaryId(tempIdRuoloProfilo);
				tmpRP.setDataFineValidita(Utils.sysdate());
				ruoloProfiloLowDaoImpl.update(tmpRP);
			} else {
				//se esiste lo tiro via dalla lista di riferimento, perche' gia' presente, cosi' dopo inserisco su DB solo quelle rimaste! (punto 2.)
				java.util.Iterator<RuoloDto> itr = ruoliDto.iterator();
				  
		        while (itr.hasNext()) {
		        	RuoloDto rtd = itr.next();
		  
		            if (rp.getRuolo().getId() == rtd.getId())
		                itr.remove();
		        }
			}
		}
		log.log(Level.INFO, "Ruolo " + rp.getId() + " correttamente gestito.");
	}
	
	private void insertRuoloProfiloEntity(RuoloDto r, Long idFunz) {
		RuoloProfilo rpToInsert = new RuoloProfilo();
		rpToInsert.setDataInizioValidita(Utils.sysdate());
		rpToInsert.setDataFineValidita(null);
		rpToInsert.setRuolo(r);
		FunzionalitaDto funzDTO = new FunzionalitaDto();
		funzDTO.setIdFunzione(idFunz); //Non mi serve altro
		rpToInsert.setFunzionalita(funzDTO);
		ruoloProfiloLowDaoImpl.insert(rpToInsert);
	}

	private List<TreeFunzionalitaDto> modificaAlberoProfilo(List<Nodo> listaNodiSelezionati, List<Nodo> listaNodiFunzionalitaProfilo,
			List<Long> idInseriti, boolean active, TreeFunzionalitaDto treeFunzionalitaProfilo, String codiceFiscale) {

		List<TreeFunzionalitaDto> treeModificati = new ArrayList<TreeFunzionalitaDto>();
		
		//Parte dove viene controllato se i nodi selezionati sono ancora presenti 
		//altrimenti cancella logicamente i singoli nodi
		for (Nodo nodo : listaNodiSelezionati) {
			boolean foundVecchiaLista = false;

			for (Nodo nodoOld : listaNodiFunzionalitaProfilo) {
				if (nodo.equals(nodoOld)) {
					checkFoundNodoOld(nodoOld);
					foundVecchiaLista = true;
				}

			}
			//Parte dove il nodo e' un nodo appena selezionato e quindi viene direttamente inserito
			if (!foundVecchiaLista) {
				controlloModificaFigliProfilo(nodo, listaNodiFunzionalitaProfilo, idInseriti, active,
						treeFunzionalitaProfilo, codiceFiscale, treeModificati);
				//}
			}
		}
		//Gestione dei vecchi nodi del profilo
		//Se non sono stati trovati vuol dire che sono stati deselezionati dall'utente e vengono cancellati logicamente
		//Se invece sono stati trovati vuol dire che non hanno subito modifiche ma dobbiamo controllare che lo stato
		//del profilo non sia variato, in tal caso devo modificare di conseguenza il treeFunzionalita'
		gestioneNodiOldProfilo(listaNodiFunzionalitaProfilo, active, codiceFiscale, treeModificati); 
	
		
		return treeModificati;
	}

	private void gestioneNodiOldProfilo(List<Nodo> listaNodiFunzionalitaProfilo, boolean active, String codiceFiscale, List<TreeFunzionalitaDto> treeModificati) {
		for(Nodo nodoOld : listaNodiFunzionalitaProfilo) {
			TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
			treeFunzionalitaDto = treeFunzionalitaLowDao.findByPrimaryId(nodoOld.getTreeFunzionalitaDto().getIdTreeFunzione());
			Timestamp dataOdierna = Utils.sysdate();
			if(!nodoOld.isFound()) {
				treeFunzionalitaDto.setDataFineValidita(dataOdierna);
				treeFunzionalitaDto.setDataCancellazione(dataOdierna);
			}else{
				if(active && treeFunzionalitaDto.getDataFineValidita() != null &&
					treeFunzionalitaDto.getDataFineValidita().before(dataOdierna)){
					treeFunzionalitaDto.setDataFineValidita(null);
					treeFunzionalitaDto.setDataCancellazione(null);
				}else if(!active && (treeFunzionalitaDto.getDataFineValidita() == null ||
						treeFunzionalitaDto.getDataFineValidita().after(dataOdierna))){
					treeFunzionalitaDto.setDataFineValidita(dataOdierna);
				}
			}
			treeFunzionalitaDto.setDataAggiornamento(dataOdierna);
			treeFunzionalitaDto.setCfOperatore(codiceFiscale);
			treeFunzionalitaLowDao.update(treeFunzionalitaDto);
			treeModificati.add(treeFunzionalitaDto);
		}
	}

	/**
	 * @param nodoOld
	 */
	public void checkFoundNodoOld(Nodo nodoOld) {
		nodoOld.setFound(true);
		if(nodoOld.getHasParent()) {
			checkFoundNodoOld(nodoOld.getParent());
		}
	}

	/**
	 * @param listaNodiSelezionati
	 * @param idInseriti
	 * @param nodoSelezionato
	 * @param treeModificati 
	 */
	public void controlloModificaFigliProfilo(Nodo nodoSelezionato, List<Nodo> listaNodiFunzionalitaProfilo,
			List<Long> idInseriti, boolean attivo, TreeFunzionalitaDto treeFunzionalitaProfilo,
			String codiceFiscaleUtente, List<TreeFunzionalitaDto> treeModificati) {

		if (nodoSelezionato.getHasParent() && "FUNZ".equals(nodoSelezionato.getParent().getTreeFunzionalitaDto()
				.getFunzionalitaDto().getTipoFunzionalitaDto().getCodiceTipoFunzione())) {
			controlloModificaFigliProfilo(nodoSelezionato.getParent(), listaNodiFunzionalitaProfilo, idInseriti, attivo,
					treeFunzionalitaProfilo, codiceFiscaleUtente, treeModificati);
		}
		if (!idInseriti.contains(nodoSelezionato.getTreeFunzionalitaDto().getFunzionalitaDto().getIdFunzione())) {
			boolean foundVecchiaLista = false;
			for (Nodo nodoOld : listaNodiFunzionalitaProfilo) {
				if (nodoSelezionato.equals(nodoOld)) {
					nodoOld.setFound(true);
					foundVecchiaLista = true;
					nodoSelezionato.setTreeFunzionalitaDto(nodoOld.getTreeFunzionalitaDto());
				}

			}
			if (!foundVecchiaLista) {
				TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
				treeFunzionalitaDto.setDataInizioValidita(Utils.sysdate());
				treeFunzionalitaDto.setDataInserimento(Utils.sysdate());
				if (attivo) {
					treeFunzionalitaDto.setDataFineValidita(null);
				} else {
					treeFunzionalitaDto.setDataFineValidita(Utils.sysdate());
				}
				treeFunzionalitaDto.setDataAggiornamento(Utils.sysdate());
				treeFunzionalitaDto.setFunzionalitaDto(nodoSelezionato.getTreeFunzionalitaDto().getFunzionalitaDto());
				treeFunzionalitaDto.setCfOperatore(codiceFiscaleUtente);
				if (!"FUNZ".equals(nodoSelezionato.getParent().getTreeFunzionalitaDto().getFunzionalitaDto()
						.getTipoFunzionalitaDto().getCodiceTipoFunzione())) {

					treeFunzionalitaDto.setFunzionalitaTreePadreDto(treeFunzionalitaProfilo);

				} else {
					treeFunzionalitaDto
							.setFunzionalitaTreePadreDto(nodoSelezionato.getParent().getTreeFunzionalitaDto());
				}
				idInseriti.add(treeFunzionalitaDto.getFunzionalitaDto().getIdFunzione());
				nodoSelezionato.setTreeFunzionalitaDto(treeFunzionalitaDto);
				TreeFunzionalitaDto insert = treeFunzionalitaLowDao.insert(treeFunzionalitaDto);
				treeModificati.add(insert);
			}
		}

	}

	/**
	 * @param modificaProfiloModel
	 * @param funzionalitaProfiloDto
	 * @param active
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> popolaInserisciFunzionalita(ModificaProfiloModel modificaProfiloModel,
			String codiceFiscaleUtente, FunzionalitaDto funzionalitaProfiloDto) throws Exception {
		Map<String, String> returnRecord = new HashMap<String, String>();
		String abil = "";
		Timestamp dataOdierna = Utils.sysdate();
		FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
		funzionalitaDto = funzionalitaLowDao.findByPrimaryId(funzionalitaProfiloDto.getIdFunzione());
		funzionalitaDto.setCodiceFunzione(modificaProfiloModel.getCodice());

		//Recupero TreeFunzion del profilo per attivarlo/disattivarlo
		TreeFunzionalitaDto treeFunzProfilo = new TreeFunzionalitaDto();
//		treeFunzProfilo.setFunzionalitaDto(funzionalitaDto);
//		treeFunzProfilo = Utils.getFirstRecord(treeFunzionalitaLowDao.findByFilter(treeFunzProfilo));
		treeFunzProfilo = Utils.getFirstRecord(treeFunzionalitaLowDao.findByIdFunzione(funzionalitaDto.getIdFunzione()));

		if (!modificaProfiloModel.getActive()) {
			funzionalitaDto.setDataFineValidita(dataOdierna);
			treeFunzProfilo.setDataFineValidita(dataOdierna);

			//Avendo disabilitato il profilo devo anche disabilitare eventuali abilitazioni collegate al profilo
			Collection<AbilitazioneDto> disattivaAbilitazioniProfilo = disattivaAbilitazioniProfilo(codiceFiscaleUtente, treeFunzProfilo, dataOdierna);
			abil = disattivaAbilitazioniProfilo.stream().map(AbilitazioneDto::getId).map(String::valueOf).collect(Collectors.joining(","));
		} else {
			funzionalitaDto.setDataFineValidita(null);
			treeFunzProfilo.setDataFineValidita(null);
		}
		funzionalitaDto.setDataAggiornamento(dataOdierna);
		funzionalitaDto.setDescrizioneFunzione(modificaProfiloModel.getDescrizione());
		funzionalitaDto.setCfOperatore(codiceFiscaleUtente);
		funzionalitaLowDao.update(funzionalitaDto);
		returnRecord.put("funz", funzionalitaDto.getIdFunzione().toString());
		
		
		treeFunzProfilo.setDataAggiornamento(dataOdierna);
		treeFunzionalitaLowDao.update(treeFunzProfilo);
		returnRecord.put("treeFunz", treeFunzProfilo.getIdTreeFunzione().toString());
		
		if(!abil.isEmpty()) {
			returnRecord.put("abil", abil);
		}
		
		return returnRecord;
	}

	private Collection<AbilitazioneDto> disattivaAbilitazioniProfilo(String codiceFiscaleUtente, TreeFunzionalitaDto treeFunzProfilo, Timestamp dataOdierna) throws Exception {

//		AbilitazioneDto abilitazioneDto = new AbilitazioneDto();
//		abilitazioneDto.setTreeFunzionalitaDto(treeFunzProfilo);
//		Collection<AbilitazioneDto> listaAbilitazioniProfilo  = abilitazioneLowDao.findByFilter(abilitazioneDto);
		Collection<AbilitazioneDto> listaAbilitazioniProfilo  = abilitazioneLowDao.findByIdFunzTree(treeFunzProfilo.getIdTreeFunzione());
		if(listaAbilitazioniProfilo != null && !listaAbilitazioniProfilo.isEmpty()){
			for(AbilitazioneDto abilitazioneProfilo : listaAbilitazioniProfilo){
				abilitazioneProfilo.setDataFineValidita(dataOdierna);
				abilitazioneProfilo.setDataAggiornamento(dataOdierna);
				abilitazioneProfilo.setCfOperatore(codiceFiscaleUtente);
				abilitazioneLowDao.update(abilitazioneProfilo);
			}
		}
		return listaAbilitazioniProfilo;
	}


}
