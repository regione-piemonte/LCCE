/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import it.csi.solconfig.configuratoreweb.presentation.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.impl.RuoloProfiloLowDaoImpl;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.InserisciProfiliService;
import it.csi.solconfig.configuratoreweb.business.service.ModificaProfiliService;
import it.csi.solconfig.configuratoreweb.business.service.RicercaProfiliService;
import it.csi.solconfig.configuratoreweb.business.service.RuoloService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Controller
@Scope("prototype")
public class GestioneProfiliController extends BaseController {

	@Autowired
	RicercaProfiliService ricercaProfiliService;

	@Autowired
	private RuoloService ruoloService;

	@Autowired
	InserisciProfiliService inserisciProfiliService;
	
	@Autowired
	ModificaProfiliService modificaProfiliService;
	
	@Autowired
	RuoloProfiloLowDaoImpl ruoloProfiloLowDaoImpl;

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@RequestMapping(value = "/cercaprofili", method = RequestMethod.POST)
	public ModelAndView searchProfili(HttpServletRequest request,
									  @ModelAttribute("profilo") RicercaProfiloModel ricercaProfiloModel, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
		FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
		ApplicazioneDto applicazioneDto = new ApplicazioneDto();

		try {
			log.log(Level.INFO, "Sei in Cerca Profili");
			data = getData();

			if (ricercaProfiloModel.getidApplicazione() == null
			) {
				log.error("ERROR: SOL non selezionato");
				messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.SOL_MANCANTE));
				mav.setViewName(ConstantsWebApp.GESTIONE_PROFILI);
			} else {
				applicazioneDto.setId(ricercaProfiloModel.getidApplicazione());
				Boolean flag = null;
				flag = controllaFlag(ricercaProfiloModel);

				PaginaDTO<FunzionalitaDto> paginaDTO = ricercaProfiliService.ricercaListaProfili(ricercaProfiloModel,
						flag, applicazioneDto);
				if (paginaDTO.getElementiTotali() == 0) {
					log.log(Level.WARN, "Profili non trovati");
					messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.PROFILI_NON_TROVATI));
					data.setFunzionalitaDtoList(null);
				} else {


					mav.addObject("paginaProfili", paginaDTO);
					mav.addObject("sysdate", Utils.sysdate());
					mav.addObject("stato", flag);
					updateData(data);
					/* Log Audit */
//					setLogAuditSOL(OperazioneEnum.READ, "ELE_PRO");
					
					// Scrittura log Audit
		            ricercaProfiliService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_RICERCA_PROFILO, null, 
		            		UUID.randomUUID().toString(), null, ConstantsWebApp.RICERCA_PROFILO, getData());
					
				}
				mav.setViewName(ConstantsWebApp.GESTIONE_PROFILI);
			}
		} catch (Exception e) {
			log.error("ERROR: searchProfili - ", e);
			messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);
		} finally {

			mav.addObject("errori", messaggiErrore);
		}

		return mav;

	}

	@RequestMapping(value = "/inserimentoProfili", method = RequestMethod.GET)
	public ModelAndView vaiInserisciProfili(ModelAndView mav) {

		try {
			Data data = getData();
			
			List<String> tuttiRuoli = initComboRuoli(mav);
			
			//Costruisco un albero per ogni applicazione
			//(se la struttura esiste, e' costrassegnato da un nodo avente un tipo speciale detto "APP")
			for(ApplicazioneDto dto : data.getApplicazioneDtoList()) {
				Nodo albero = new Nodo();
				TreeFunzionalitaDto tfd = Utils.getFirstRecord(inserisciProfiliService.findByApplicazioneTree(dto));
				albero.setTreeFunzionalitaDto(tfd);
				if(albero != null && albero.getTreeFunzionalitaDto() != null
						&& "APP".equals(albero.getTreeFunzionalitaDto()
						.getFunzionalitaDto()
						.getTipoFunzionalitaDto().getCodiceTipoFunzione())) {
					//Metodo che costruisce l'intero l'albero avendo come base il nodo padre (nodo con tipo "APP")
					inserisciProfiliService.organizzaAlberoFunzionalita(albero);
					//Setto in sessione ogni albero trovato, per essere poi mostrato in pagina quando selezionato il suo SOL
					session.setAttribute(dto.getId().toString(),albero);
				}
			}
			mav.setViewName(ConstantsWebApp.INSERISCI_PROFILI);
			mav.addObject("profilo", new InserisciProfiloModel());
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: inserisciProfili - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
	
	@RequestMapping(value = "/updateProfilo", method = RequestMethod.GET)
	public ModelAndView vaiModificaProfili(ModelAndView mav, @RequestParam(value = "idFunzione", required = true) Long idFunzione) {

		try {
			Data data = getData();
			//Prelevo la funzionalita' corrispondente al profilo dell'albero
			FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
			funzionalitaDto.setIdFunzione(idFunzione);
			funzionalitaDto = modificaProfiliService.prelevaFunzionalita(funzionalitaDto);
			
			List<String> tuttiRuoli = initComboRuoli(mav);
			
			ApplicazioneDto dto = funzionalitaDto.getApplicazioneDto();
			//Popolo il modello per mostrarne i dati in pagina di Modifica
			ModificaProfiloModel profilo = popolaModelModificaProfilo(funzionalitaDto, dto, idFunzione);
			Nodo albero = new Nodo();
			//Costruisco la struttura dell'albero da visualizzare in pagina in base all'applicazione
			//(se la struttura esiste, e' costrassegnato da un nodo avente un tipo speciale detto "APP")
			albero.setTreeFunzionalitaDto(Utils.getFirstRecord(modificaProfiliService.findByApplicazioneTree(dto)));
			if(albero != null && albero.getTreeFunzionalitaDto() != null 
					&& "APP".equals(albero.getTreeFunzionalitaDto()
							.getFunzionalitaDto()
								.getTipoFunzionalitaDto().getCodiceTipoFunzione())) {
				//Metodo che costruisce l'intero l'albero avendo come base il nodo padre (nodo con tipo "APP")
				modificaProfiliService.organizzaAlberoFunzionalita(albero);
				//Setto l'albero costruito sia in sessione che nel MAV per averne la disponibilita'
				//sia in pagina, che nel controller adetto alla modifica
				mav.addObject("albero",albero);
				session.setAttribute("alberoGenerale", albero);
				//Prelevo il treeFunzionalita specifico del profilo
				TreeFunzionalitaDto treeFunzionalitaDto = modificaProfiliService.prelevaTreeFunzionalita(funzionalitaDto);
				Nodo alberoFunzionalitaProfilo = new Nodo();
				alberoFunzionalitaProfilo.setTreeFunzionalitaDto(treeFunzionalitaDto);
				//Costruisco l'albero del profilo specifico, che verra' usato per indicare quali checkbox saranno pre-selezionate
				//in pagina (il profilo ha gia' dei figli)
				List<Long> checkAttivi = modificaProfiliService.organizzaAlberoFunzionalitaCheckate(treeFunzionalitaDto, new ArrayList<Long>(), alberoFunzionalitaProfilo);
				//Setto in sessione l'albero del Profilo specifico che verra' usato nel controller di modifica
				session.setAttribute("alberoFunzionalitaProfilo",alberoFunzionalitaProfilo);
				mav.addObject("checkAttivi", checkAttivi);
			}
			mav.setViewName(ConstantsWebApp.MODIFICA_PROFILO);
			mav.addObject("profilo", profilo);
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: modificaProfili - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		
		//Riprende gli errori e messaggio di riuscita di Modifica profilo
		//quando viene usato questo controller per il redirect (sempre al termine della modifica)
		data = getData();
		if(data.getMessaggiUtenteDto() != null) {
		mav.addObject("errori", data.getMessaggiUtenteDto());
		data.setMessaggiUtenteDto(null);
		}
		updateData(data);

		return mav;
}

	/**
	 * @param funzionalitaDto
	 * @param dto
	 * @param idFunzione 
	 * @return profilo
	 */
	public ModificaProfiloModel popolaModelModificaProfilo(FunzionalitaDto funzionalitaDto, ApplicazioneDto dto, Long idFunzione) {
		ModificaProfiloModel profilo = new ModificaProfiloModel();
		profilo.setidApplicazione(dto.getId());
		profilo.setCodice(funzionalitaDto.getCodiceFunzione());
		profilo.setDescrizione(funzionalitaDto.getDescrizioneFunzione());
		profilo.setIdFunzione(idFunzione);
		Collection<RuoloProfilo> ruoliProfilo = ruoloProfiloLowDaoImpl.findByIdFunz(idFunzione);
		List<RuoloProfilo> listaRuoliProfilo;
		if (ruoliProfilo instanceof List)
			listaRuoliProfilo = (List<RuoloProfilo>)ruoliProfilo;
		else
			listaRuoliProfilo = new ArrayList<RuoloProfilo>(ruoliProfilo);
		
		List<String> listaRuoli = new ArrayList<String>();
		listaRuoliProfilo.forEach(r -> insertElementInListaRuoli(r, listaRuoli));
		profilo.setRuoli(listaRuoli);
		
		if(funzionalitaDto.getDataFineValidita() == null || funzionalitaDto.getDataFineValidita().after(Utils.sysdate())) {
			profilo.setActive(true);
		}
		return profilo;
	}
	
	private void insertElementInListaRuoli(RuoloProfilo r, List<String> lista) {
		if (r.getDataFineValidita() == null)
			lista.add(r.getRuolo().getId().toString());
	}

	@RequestMapping(value = "/inserisciProfili", method = RequestMethod.POST)
	public ModelAndView inserisciProfili(@ModelAttribute("profilo") InserisciProfiloModel inserisciProfiloModel,
			ModelAndView mav) throws Exception {


		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
		Data data = getData();
		try {
			
			List<String> tuttiRuoli = initComboRuoli(mav);
			
			StringBuilder codiceErrore = new StringBuilder("");
			if (!controlliInserisciProfilo(inserisciProfiloModel, codiceErrore)) {
				List<Nodo> listaNodiSelezionati = new ArrayList<Nodo>();
				Nodo albero = (Nodo) session.getAttribute(inserisciProfiloModel.getidApplicazione().toString());
				if (albero.getFigli() == null) {
					messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.ERRORE_ALBERO_NOFIGLI));
				} else {
					
					prelevaFunzionalitaChecked(inserisciProfiloModel, listaNodiSelezionati, albero);
	
					mav.addObject("profilo", new RicercaProfiloModel());
	
					String oggOperInsProfilo = inserisciProfiliService.inserimentoProfilo(inserisciProfiloModel, listaNodiSelezionati, data.getUtente().getCodiceFiscale());
					messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_RIUSCITA));
	
					/* Log Audit */
	//			setLogAuditSOL(OperazioneEnum.INSERT, "INS_PRO");
					// Scrittura log Audit
		            ricercaProfiliService.setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_INSERISCI_PROFILO, null, 
		            		UUID.randomUUID().toString(), oggOperInsProfilo, ConstantsWebApp.INSERISCI_PROFILO, getData());
				}
			}
			else {
				//Salva il messaggio (gestito come errore)
				//Inserendo automaticamente il parametro quando necessario
				controllaMessaggioParametrizzato(inserisciProfiloModel.getCodice(), messaggiErrore, codiceErrore);

				if(inserisciProfiloModel.getidApplicazione() != null){
					mav.addObject("albero", session.getAttribute(inserisciProfiloModel.getidApplicazione().toString()));
				}
			}
			updateData(data);
			mav.setViewName(ConstantsWebApp.INSERISCI_PROFILI);
		} catch (Exception e) {
			log.error("ERROR: inserisciProfili - ", e);
			messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);
		} finally {
			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}
	
	@RequestMapping(value = "/modProfili", method = RequestMethod.POST)
	public ModelAndView modProfili(@ModelAttribute("profilo") ModificaProfiloModel modificaProfiloModel,
			ModelAndView mav) throws Exception {

		
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
		Data data = getData();
		Nodo alberoFunzionalitaProfilo = (Nodo) session.getAttribute("alberoFunzionalitaProfilo");
		TreeFunzionalitaDto treeFunzionalitaProfiloDto = alberoFunzionalitaProfilo.getTreeFunzionalitaDto();
		try {
			if (modificaProfiloModel.getListaIdFunzioniSelezionata() == null
					|| modificaProfiloModel.getListaIdFunzioniSelezionata().isEmpty()) {
				log.error("ERROR: funzionalita non selezionata");
				messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.PROFILO_NON_SELEZIONATO));
			} else {  // TODO: Vedere se i ruoli sono obbligatori
				StringBuilder codiceErrore = new StringBuilder("");
				if (controlliModificaProfilo(modificaProfiloModel, codiceErrore) == false) {
					List<Nodo> listaNodiSelezionati = new ArrayList<Nodo>();
					//Prelevo lo scheletro dell'albero generale (no quello specifico del profilo)
					Nodo alberoGenerale = (Nodo) session.getAttribute("alberoGenerale");
					//Costruisco la lista dei nodi selezionati
					listaNodiSelezionati = prelevaFunzionalitaChecked(modificaProfiloModel, listaNodiSelezionati, alberoGenerale);
					 
					List<Nodo> listaNodiFunzionalitaProfilo = new ArrayList<Nodo>();
					//Converto l'albero del profilo in lista per poterlo poi confrontare con la listaNodiSelezionati
					listaNodiFunzionalitaProfilo = convertiAlberoInListaNodi(alberoFunzionalitaProfilo, listaNodiFunzionalitaProfilo);
					
					modificaProfiliService.modificaProfilo(modificaProfiloModel, listaNodiSelezionati, listaNodiFunzionalitaProfilo,
							treeFunzionalitaProfiloDto ,data.getUtente().getCodiceFiscale(), getData());
					
					messaggiErrore.add(modificaProfiliService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_RIUSCITA));
				
					/* Log Audit */
//				setLogAuditSOL(OperazioneEnum.UPDATE, "MOD_PRO");
					
					
				} else {
					controllaMessaggioParametrizzato(modificaProfiloModel.getCodice(), messaggiErrore, codiceErrore);
				}
				updateData(data);
				mav.addObject("profilo", modificaProfiloModel);
			}
		} catch (Exception e) {
			log.error("ERROR: ModificaProfili - ", e);
			messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_NON_RIUSCITA));
			
		} finally {
			data = getData();
			data.setMessaggiUtenteDto(messaggiErrore);
			updateData(data);
			mav.setViewName(ConstantsWebApp.REDIRECT_MODIFICA_PROFILO + "?idFunzione=" + treeFunzionalitaProfiloDto.getFunzionalitaDto().getIdFunzione());
		}

		return mav;
	}

	/**
	 * @param codice
	 * @param messaggiErrore
	 * @param codErrore
	 * @throws Exception
	 */
	public void controllaMessaggioParametrizzato(String codice,
			List<MessaggiUtenteDto> messaggiErrore, StringBuilder codErrore) throws Exception {
		String codiceErrore = codErrore.toString();
		if(ConstantsWebApp.PROFILO_ESISTENTE.equals(codiceErrore)) {
			messaggiErrore.add(modificaProfiliService.aggiungiErrori(codiceErrore,codice));
		} else {
			messaggiErrore.add(modificaProfiliService.aggiungiErrori(codiceErrore));
		}
	}

	/**
	 * @param alberoFunzionalita
	 * @param listaNodiFunzionalitaProfilo
	 */
	public List<Nodo> convertiAlberoInListaNodi(Nodo alberoFunzionalita, List<Nodo> listaNodiFunzionalitaProfilo) {
		if(alberoFunzionalita.gethasChildren()) {
			for(Nodo figlio : alberoFunzionalita.getFigli()) {
				listaNodiFunzionalitaProfilo.add(figlio);
				convertiAlberoInListaNodi(figlio, listaNodiFunzionalitaProfilo);
			}
		}
		return listaNodiFunzionalitaProfilo;
	}
	
	private List<String> initComboRuoli(ModelAndView mav) {
		List<RuoloDTO> tuttiRuoli = ruoloService.ricercaTuttiRuoli(getData());
		mav.getModel().put("ruoli", tuttiRuoli);
		return tuttiRuoli.stream().map(e -> (e.getId() != null ? e.getId().toString() : "")).collect(Collectors.toList());
	}

	@RequestMapping(value = "/selezionaAlbero", method = RequestMethod.POST)
	public @ResponseBody ModelAndView selezionaAlbero(ModelAndView mav, @ModelAttribute("profilo") InserisciProfiloModel inserisciProfiloModel) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
		try {
			
			List<String> tuttiRuoli = initComboRuoli(mav);

			mav.setViewName(ConstantsWebApp.INSERISCI_PROFILI);
			if(inserisciProfiloModel.getidApplicazione() != null){
				mav.addObject("albero", session.getAttribute(inserisciProfiloModel.getidApplicazione().toString()));
			}
			mav.addObject("profilo", inserisciProfiloModel);

		} catch (Exception e) {
			log.error("ERROR: inserisciProfili - ", e);
			messaggiErrore.add(ricercaProfiliService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);
		} finally {
			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}

	/**
	 * @param inserisciProfiloModel
	 * @param funzionalitaDto
	 * @param data
	 * @param funzionalitaCheckedDto
	 * @param albero 
	 * @throws NumberFormatException
	 */
	public void prelevaFunzionalitaChecked(InserisciProfiloModel inserisciProfiloModel, List<Nodo> listaNodiSelezionati ,Nodo albero) throws NumberFormatException {
		
		for (Nodo figlio : albero.getFigli()) {
			if (figlio.gethasChildren()) {
				prelevaFunzionalitaChecked(inserisciProfiloModel, listaNodiSelezionati, figlio);
			} else {
				for (String idNodo : inserisciProfiloModel.getListaIdFunzioniSelezionata()) {
					Long idFunzionalita = Long.parseLong(idNodo);
					if(figlio.getTreeFunzionalitaDto().getIdTreeFunzione().equals(idFunzionalita)) {
						listaNodiSelezionati.add(figlio);
//						System.out.println(idFunzionalita + " Aggiunto!");
					}
				}
			}
		}
	}
	
	/**
	 * @param modificaProfiloModel
	 * @param funzionalitaDto
	 * @param data
	 * @param funzionalitaCheckedDto
	 * @param albero 
	 * @throws NumberFormatException
	 */
	private List<Nodo> prelevaFunzionalitaChecked(ModificaProfiloModel modificaProfiloModel, List<Nodo> listaNodiSelezionati,
			Nodo alberoFunzionalita) {
		for (Nodo figlio : alberoFunzionalita.getFigli()) {
			if (figlio.gethasChildren()) {
				prelevaFunzionalitaChecked(modificaProfiloModel, listaNodiSelezionati, figlio);
			} else {
				for (String idNodo : modificaProfiloModel.getListaIdFunzioniSelezionata()) {
					Long idFunzionalita = Long.parseLong(idNodo);
					if(figlio.getTreeFunzionalitaDto().getIdTreeFunzione().equals(idFunzionalita)) {
						listaNodiSelezionati.add(figlio);
//						System.out.println(idFunzionalita + " Aggiunto!");
					}
				}
			}
		}
	return listaNodiSelezionati;	
	}

	/**
	 * @param ricercaProfiloModel
	 * @param funzionalitaDto
	 * @param codiciErrore
	 * @throws Exception
	 */
	public boolean controlliModificaProfilo(ModificaProfiloModel modificaProfiloModel, StringBuilder codiceErrore) throws Exception {

		if (modificaProfiloModel.getidApplicazione() == null) {
			log.error("ERROR: SOL non selezionato");
			codiceErrore.append(ConstantsWebApp.PROFILO_NON_SELEZIONATO);
			return true;
		}

		if ((modificaProfiloModel.getCodice() == null || modificaProfiloModel.getCodice().isEmpty())
				|| (modificaProfiloModel.getDescrizione() == null
						|| modificaProfiloModel.getDescrizione().isEmpty())) {

			log.error("ERROR: Mancato inserimento di Codice e/o Descrizione");
			if (modificaProfiloModel.getCodice() == null || modificaProfiloModel.getCodice().isEmpty())
				codiceErrore.append(ConstantsWebApp.CAMPO_CODICE_NON_VALORIZZATI);
			else
				codiceErrore.append(ConstantsWebApp.CAMPO_DESCR_NON_VALORIZZATI);
			return true;
		}

		return false;
	}
	
	public boolean controlliInserisciProfilo(InserisciProfiloModel inserisciProfiloModel,StringBuilder codiceErrore) throws Exception {

		if (inserisciProfiloModel.getListaIdFunzioniSelezionata() == null
				|| inserisciProfiloModel.getListaIdFunzioniSelezionata().isEmpty()) {
			log.error("ERROR: funzionalita non selezionata");
			codiceErrore.append(ConstantsWebApp.PROFILO_NON_SELEZIONATO);
			return true;
		}

		if (inserisciProfiloModel.getidApplicazione() == null) {
			log.error("ERROR: SOL non selezionato");
			codiceErrore.append(ConstantsWebApp.PROFILO_NON_SELEZIONATO);
			return true;
		}

		if (inserisciProfiloModel.getRuoli() == null) {
			log.error("ERROR: Ruoli non selezionati");
			codiceErrore.append(ConstantsWebApp.RUOLI_NON_TROVATI);
			return true;
		}

		if ((!inserisciProfiloModel.getCodice().isEmpty())
				&& (!inserisciProfiloModel.getDescrizione().isEmpty())) {
			FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
			funzionalitaDto.setCodiceFunzione(inserisciProfiloModel.getCodice());

			if (inserisciProfiliService.checkCodicePresente(funzionalitaDto) == false) {
				log.error("ERROR: SOL gia' presente nel sistema");
				codiceErrore.append(ConstantsWebApp.PROFILO_ESISTENTE);
				return true;
			}
		} else {
			log.error("ERROR: Mancato inserimento di Codice e/o Descrizione");
			if (inserisciProfiloModel.getCodice().isEmpty())
				codiceErrore.append(ConstantsWebApp.CAMPO_CODICE_NON_VALORIZZATI);
			else
				codiceErrore.append(ConstantsWebApp.CAMPO_DESCR_NON_VALORIZZATI);
			return true;
		}

		return false;
	}

	/**
	 * @param ricercaProfiloModel
	 */
	public Boolean controllaFlag(RicercaProfiloModel ricercaProfiloModel) {
		if (((ricercaProfiloModel.getFlagAttivo() == null || ricercaProfiloModel.getFlagAttivo().isEmpty())
				&& (ricercaProfiloModel.getFlagNonAttivo() == null || ricercaProfiloModel.getFlagNonAttivo().isEmpty()))
				|| ("true".equalsIgnoreCase(ricercaProfiloModel.getFlagAttivo())
						&& "true".equalsIgnoreCase(ricercaProfiloModel.getFlagNonAttivo()))) {
			return null;
		}
		return "true".equalsIgnoreCase(ricercaProfiloModel.getFlagAttivo())
				&& (ricercaProfiloModel.getFlagNonAttivo() == null || ricercaProfiloModel.getFlagNonAttivo().isEmpty())
						? true
						: false;
	}

}