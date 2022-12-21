/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneCollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.service.ApplicazioniService;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.ApplicazioneModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaApplicazioneModel;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Controller
@Scope("prototype")
public class GestioneApplicazioniController extends BaseController {

	private static final String REDIRECT = "redirect:";

	@Autowired
	private ApplicazioniService applicazioniService;

	@Autowired
	private CollocazioneService collocazioneService;

	// inserimentoNuovaApplicazione -- in get, la pagina di inserimento
	// searchAppliczione -- in post la ricerca della applicazione

	// model paginaSOL nella pagina di ricerca
	// oggetto nel form: applicazione
	@RequestMapping(value = "/searchApplicazione", method = RequestMethod.POST)
	public ModelAndView searchApplicazione(
			@ModelAttribute("applicazione") RicercaApplicazioneModel ricercaApplicazioneModel, ModelAndView mav)
			throws Exception {
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {
			log.info(" -- Ricerca SOL -- ");
			data = getData();

			PaginaDTO<ApplicazioneDto> paginaDTO = applicazioniService.ricercaApplicazioni(ricercaApplicazioneModel);

			if (paginaDTO.getElementiTotali() == 0) {
				log.error("WARNING: Nessuna applicazione trovata");
				messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.APPLICAZIONE_NON_TROVATA));
				mav.setViewName(ConstantsWebApp.GESTIONE_APPLICAZIONI);
				return mav;
			}

			mav.addObject("paginaSOL", paginaDTO);
			mav.addObject("sysdate", Utils.sysdate());
			// mav.addObject("flagConfiguratore", flagConfiguratore);

			/*
			 * LogAudit
			 */
			// setLogAuditSOL(OperazioneEnum.READ, "ELE_RUO");
			// Scrittura log Audit
			applicazioniService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_RICERCA_APPLICAZIONE, null, 
            		UUID.randomUUID().toString(), null, ConstantsWebApp.RICERCA_APP, getData());
			updateData(data);
			mav.setViewName(ConstantsWebApp.GESTIONE_APPLICAZIONI);
		} catch (Exception e) {
			log.error("ERROR: applicazioni - ", e);
			messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}
		return mav;
	}

	private void initCombo(ModelAndView mav) {
		Map<String, String> icons = applicazioniService.getIcons();
		mav.addObject("icons", icons);

		mav.addObject("sediOperative", collocazioneService.getAllAziende(getData()));
	}

	@RequestMapping(value = "/inserimentoNuovaApplicazione", method = RequestMethod.GET)
	public ModelAndView showInserisciApplicazione(ModelAndView mav) {

		ApplicazioneModel app = new ApplicazioneModel();
		try {
			app.setPathImmagine(applicazioniService.getDefaultPath());
			app.setRedirectUrl(REDIRECT);
			app.setOscurato("N");

		} catch (Exception e) {
			log.error("ERROR: showInserisciApplicazione - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return showInserisciApplicazione(app, mav);
	}

	public ModelAndView showInserisciApplicazione(ApplicazioneModel app, ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.INSERISCI_APPLICAZIONI);

			mav.addObject("applicazione", app);

			initCombo(mav);

			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: showInserisciApplicazione - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/inserimentoNuovaApplicazione", method = RequestMethod.POST)
	public ModelAndView inserimentoNuovaApplicazione(
			@ModelAttribute("applicazione") ApplicazioneModel applicazioneModel, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {

			Data data = getData();
			log.info(" -- inserimentoNuovaApplicazione  -- ");

			messaggiErrore.addAll(checkCampiObbligatori(applicazioneModel));

			if (messaggiErrore.size() > 0) {
				return showInserisciApplicazione(applicazioneModel, mav);
			} else {
				Collection<ApplicazioneDto> appsdb = applicazioniService
						.findApplicazioneByCodice(applicazioneModel.getCodice());
				if (appsdb != null && appsdb.size() > 0) {
					log.error("ERROR: Impossibile eseguire inserimento - " + ConstantsWebApp.ERRORE_SOL_GIA_PRESENTE);
					messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.ERRORE_SOL_GIA_PRESENTE));

					return showInserisciApplicazione(applicazioneModel, mav);
				}
//			

				// xxx
				log.info("[GestioneApplicazioniController::inserimentoNuovaApplicazione] ApplicazioneModel::"
						+ applicazioneModel.toString());
				ApplicazioneDto app = applicazioniService.inserisciApplicazione(applicazioneModel,
						getData().getUtente().getCodiceFiscale());

				String applicazioneCollocazione = "";
				if (!applicazioneModel.getCollocazioni().isEmpty()) {
					List<String> collocaz = applicazioneModel.getCollocazioni();
					
					for(String item:collocaz) 
						applicazioneCollocazione = applicazioneCollocazione+", "+item;
				}

				// Scrittura log Audit
				applicazioniService.setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_INSERISCI_APPLICAZIONE, null, 
	            		UUID.randomUUID().toString(), app.getId() + " - " + applicazioneCollocazione, ConstantsWebApp.INSERISCI_APP, getData());
				

				// visualizza messaggio di successo
				log.info("Salvataggio effettuato");

				// vai alla modifica

				return showUpdateApplicazione(app, applicazioneModel, mav, ConstantsWebApp.OPERAZIONE_RIUSCITA);

			}

		} catch (Exception e) {
			log.error("ERROR: Applicazioni - ", e);
			messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav = showInserisciApplicazione(applicazioneModel, mav);

		} finally {
			if (messaggiErrore != null && messaggiErrore.size() > 0) {
				mav.addObject("errori", messaggiErrore);
			}
		}

		return mav;
	}

	private Collection<? extends MessaggiUtenteDto> checkCampiObbligatori(ApplicazioneModel applicazioneModel) throws Exception {
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
		if ((applicazioneModel.getCodice() == null || applicazioneModel.getCodice().isEmpty()) //
				|| (applicazioneModel.getDescrizione() == null || applicazioneModel.getDescrizione().isEmpty()) //  
				|| (applicazioneModel.getDescrizioneWebapp() == null || applicazioneModel.getDescrizioneWebapp().isEmpty()) //
				) {
			messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.CAMPI_NON_VALORIZZATI));
		}

		if ((applicazioneModel.getRedirectUrl() == null || applicazioneModel.getCodice().isEmpty()) //
			|| applicazioneModel.getRedirectUrl().trim().equalsIgnoreCase(REDIRECT)	//
				) {
			messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.CAMPI_NON_VALORIZZATI));
		}
				

		
		
		
		return messaggiErrore;
	}

	public ModelAndView showUpdateApplicazione(ApplicazioneDto app, ApplicazioneModel model, ModelAndView mav,
			String... msgs) throws Exception {
		Map<String, String> icons = applicazioniService.getIcons();
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		Data data = getData();

		// recupero la app visualizzare
		if (app != null) {
			model.setId(app.getId());

			model.setCodice(app.getCodice());
			model.setDescrizione(app.getDescrizione());
			model.setPinRichiesto(app.getPinRichiesto());
			model.setUrlverifyloginconditions(app.getUrlVerifyLoginConditions());
			model.setDescrizioneWebapp(app.getDescrizioneWebapp());

			if (icons.containsKey(app.getPathImmagine())) {
				model.setPathImmagine(app.getPathImmagine());
			} else {
				model.setPathImmagine(applicazioniService.getDefaultPath());
			}

			model.setRedirectUrl(app.getRedirectUrl());
			model.setBottone(app.getBottone());

			model.setOscurato(app.getOscurato());

			// xxx
			model.setCollocazioni(applicazioniService.getCollocazioniByIdApp(app.getId()));
			// if (app.getApplicazioneCollocazioneList() != null &&
			// app.getApplicazioneCollocazioneList().size() > 0) {
			// model.getCollocazioni().addAll(applicazioniService.getCollocazioni(app));
			// }
		}
		initCombo(mav);

		updateData(data);
		mav.addObject("applicazione", model);
		initCombo(mav);
		mav.setViewName(ConstantsWebApp.EDIT_APPLICAZIONE);
		
		// modificaApplicazioni
		log.info("[GestioneApplicazioniController::showUpdateApplicazione] msgs.length "
				+ (msgs == null ? "null" : Integer.toString(msgs.length)));
		for (int i = 0; msgs != null && i < msgs.length; i++) {
			messaggiErrore.add(applicazioniService.aggiungiErrori(msgs[i]));
		}

		if (messaggiErrore.size() > 0) {
			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}
	
	
	

	@RequestMapping(value = "/updateApplicazione", method = RequestMethod.GET)
	public ModelAndView showUpdateApplicazione(@RequestParam(value = "id") long id,
			@ModelAttribute("applicazione") ApplicazioneModel applicazione, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {
			// recupero la app visualizzare

			ApplicazioneDto app = applicazioniService.findApplicazioneById(id);

			return showUpdateApplicazione(app, applicazione, mav);

		} catch (Exception e) {
			log.error("ERROR: update - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/modificaApplicazione", method = RequestMethod.POST)
	public ModelAndView modificaApplicazione(@ModelAttribute("applicazione") ApplicazioneModel applicazioneModel,
			ModelAndView mav) throws Exception {
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {

			Data data = getData();
			log.info(" -- modificaApplicazione  -- ");

			messaggiErrore.addAll(checkCampiObbligatori(applicazioneModel));
			
			if (messaggiErrore.size() == 0) {
				// xxx
				log.info("[GestioneApplicazioniController::modificaApplicazione] ApplicazioneModel::"
						+ applicazioneModel.toString());

				ApplicazioneDto app = applicazioniService.aggiornaApplicazione(applicazioneModel,
						getData().getUtente().getCodiceFiscale());

				// visualizza messaggio di successo
				log.info("Salvataggio effettuato");
				

				String applicazioneCollocazione = "";
				if (!applicazioneModel.getCollocazioni().isEmpty()){
					List<String> collocaz = applicazioneModel.getCollocazioni();
					for(String item:collocaz) 
						applicazioneCollocazione = applicazioneCollocazione+", "+item;
				}

				// Scrittura log Audit
				applicazioniService.setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_APPLICAZIONE, null, 
	            		UUID.randomUUID().toString(), app.getId() + " - " + applicazioneCollocazione, ConstantsWebApp.MODIFICA_APP, getData());


				// vai alla modifica
				mav = showUpdateApplicazione(app, applicazioneModel, mav, ConstantsWebApp.OPERAZIONE_RIUSCITA);

			} else {
				mav = showUpdateApplicazione(null, applicazioneModel, mav);
				mav.addObject("errori", messaggiErrore);
			}
			return mav ; 
			
			
		} catch (Exception e) {
			log.error("ERROR: Applicazioni - ", e);
			messaggiErrore.add(applicazioniService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}
}
