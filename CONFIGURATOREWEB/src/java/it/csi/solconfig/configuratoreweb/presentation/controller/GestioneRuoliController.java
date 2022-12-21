/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import it.csi.solconfig.configuratoreweb.presentation.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.business.service.RuoliService;
import it.csi.solconfig.configuratoreweb.business.service.RuoloService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Controller
@Scope("prototype")
public class GestioneRuoliController extends BaseController {

	@Autowired
	RuoliService ruoliService;

	@Autowired
	private RuoloService ruoloService;

	@Autowired
	private CollocazioneService collocazioneService;

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@RequestMapping(value = "/searchRuoli", method = RequestMethod.POST)
	public ModelAndView searchRuoli(@ModelAttribute("ruolo") RicercaRuoloModel ricercaRuoloModel, ModelAndView mav)
			throws Exception {
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {
			log.info(" -- Ricerca Ruoli -- ");
			data = getData();

			// filtri opzionali

			Boolean flagConfiguratore = null;
			flagConfiguratore = checkInsertByConfiguratore(ricercaRuoloModel);

			PaginaDTO<RuoloDto> paginaDTO = ruoliService.ricercaListaRuoli(ricercaRuoloModel, flagConfiguratore);

			if (paginaDTO.getElementiTotali() == 0) {
				log.error("WARNING: Nessun ruolo trovato");
				messaggiErrore.add(ruoliService.aggiungiErrori(ConstantsWebApp.RUOLI_NON_TROVATI));
				mav.setViewName(ConstantsWebApp.GESTIONE_RUOLI);
				return mav;
			}

			mav.addObject("paginaRuoli", paginaDTO);
//			session.setAttribute("ricercaRuolo", ricercaRuoloModel);
//			session.setAttribute("listaRicerca", paginaDTO);
			mav.addObject("sysdate", Utils.sysdate());
			mav.addObject("flagConfiguratore", flagConfiguratore);

			/*
			 * LogAudit
			 */
//			setLogAuditSOL(OperazioneEnum.READ, "ELE_RUO");
			// Scrittura log Audit
            ruoliService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_RICERCA_RUOLI, null, 
            		UUID.randomUUID().toString(), null, ConstantsWebApp.RICERCA_RUOLI, getData());
			updateData(data);
			mav.setViewName(ConstantsWebApp.GESTIONE_RUOLI);

		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			messaggiErrore.add(ruoliService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}
		return mav;
	}

	@RequestMapping(value = "/annullaRicerca", method = RequestMethod.GET)
	public ModelAndView ruoli(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.REDIRECT_RUOLI);
			mav.addObject("ruolo", new RicercaRuoloModel());
			data.setRuoloDtoList(null);
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/inserimentoRuoli", method = RequestMethod.GET)
	public ModelAndView vaiInserisciRuoli(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.INSERISCI_RUOLI);
			mav.addObject("ruolo", new InserisciRuoloModel());
			
			initComboRuoli(mav);
			
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: inserisciRuoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	// controller che visualizza pagina con i dati ruolo da modificare
	@RequestMapping(value = "/updateRuolo", method = RequestMethod.GET)
	public ModelAndView updateRuolo(HttpServletRequest request, @RequestParam(value = "id") long id,
			@ModelAttribute("ruolo") RuoloModel modificaRuoloModel, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {
			Data data = getData();

			List<String> tuttiRuoli = initComboRuoli(mav);
			// recupero il ruolo da visualizzare
			RuoloDto ruoloDaModificare = ruoliService.recuperaRuoloById(id);
			modificaRuoloModel.setId(ruoloDaModificare.getId());

			// popolo il model con i dati del ruolo recuperato
			if (ruoloDaModificare != null) {
				modificaRuoloModel.setCodice(ruoloDaModificare.getCodice().toUpperCase());
				modificaRuoloModel.setDescrizione(ruoloDaModificare.getDescrizione());

				// recupero lo stato
				modificaRuoloModel.setFlagAttivo(recuperaFlag(ruoloDaModificare));

				{
					List<RuoloSelezionabileDto> druoli = ruoliService
							.ricercaRuoliSelezionabiliBy(ruoloDaModificare.getId());
					for (RuoloSelezionabileDto d : druoli) {
						if (tuttiRuoli.contains(d.getRuoloSelezionabile().getId().toString())) {
							RuoloSelCollTipo item = new RuoloSelCollTipo(d.getRuoloSelezionabile(),
									d.getCollocazioneTipo());

							log.info("[GestioneRuoliController::updateRuolo] item:=" + item);
							modificaRuoloModel.getRuoliSel().add(item.toString());

						}
					}
				}

				log.info("[GestioneRuoliController::updateRuolo] modificaRuoloModel.getRuoliSel():="
						+ modificaRuoloModel.getRuoliSel());
				modificaRuoloModel.getRuoliCompatibili()
						.addAll(ruoloService.getRuoliCompatibili(ruoloDaModificare.getId().toString()));
				List<String> compditroppo = new ArrayList<>(modificaRuoloModel.getRuoliCompatibili());
				compditroppo.removeAll(tuttiRuoli);
				modificaRuoloModel.getRuoliCompatibili().removeAll(compditroppo);

				// Scrittura log Audit
	            // Nel metodo RuoliServiceImpl.modificaRuolo
				updateData(data);
				mav.addObject("ruolo", modificaRuoloModel);

				mav.setViewName(ConstantsWebApp.EDIT_RUOLO);
				return mav;
			}

		} catch (Exception e) {
			log.error("ERROR: update - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}

	private List<String> initComboRuoli(ModelAndView mav) {
		List<RuoloDTO> tuttiRuoli = ruoloService.ricercaTuttiRuoli(getData());
		mav.getModel().put("ruoli", tuttiRuoli);
		mav.getModel().put("collocazioniTipo", collocazioneService.getCollocazioniTipo());
		return tuttiRuoli.stream().map(e -> (e.getId() != null ? e.getId().toString() : ""))
				.collect(Collectors.toList());
	}

	@RequestMapping(value = "/modificaRuolo", method = RequestMethod.POST)
	public ModelAndView modificaRuoli(@ModelAttribute("ruolo") RuoloModel modificaRuoloModel, ModelAndView mav)
			throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {

			Data data = getData();
			log.info(" -- Aggiornamento ruolo -- ");

			initComboRuoli(mav);

			if ((modificaRuoloModel.getCodice() == null || modificaRuoloModel.getCodice().isEmpty())
					|| (modificaRuoloModel.getDescrizione() == null || modificaRuoloModel.getDescrizione().isEmpty())) {
				log.error("ERROR: Impossibile effettuare le modifiche");
				messaggiErrore.add(ruoliService.aggiungiErrori(ConstantsWebApp.CAMPI_NON_VALORIZZATI));
				mav.setViewName(ConstantsWebApp.EDIT_RUOLO);
				return mav;
			}
				
			String cfUtente= data.getUtente().getCodiceFiscale();
				
			ruoliService.modificaRuolo(modificaRuoloModel, cfUtente, getData());
			//visualizza messaggio di successo
			log.error("Salvataggio effettuato");
			messaggiErrore.add(ruoliService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_RIUSCITA));
			mav.setViewName(ConstantsWebApp.EDIT_RUOLO);

			updateData(data);
//			setLogAuditSOL(OperazioneEnum.UPDATE, "MOD_RUO");
			
			
			mav.addObject("ruolo", modificaRuoloModel);
			
			// spostato in ruoliService.modificaRuolo
			//String ruoliSelCollTipoForLogAudit = "";
			//for(RuoloSelCollTipo item:modificaRuoloModel.getRuoliSelCollTipo()) 
			//	ruoliSelCollTipoForLogAudit = ruoliSelCollTipoForLogAudit+", "+item.getIdRuolo();
			//String ruoliCompatibiliForLogAudit = "";
			//for(String item:modificaRuoloModel.getRuoliCompatibili()) 
			//	ruoliCompatibiliForLogAudit = ruoliCompatibiliForLogAudit+", "+item;
			
			// Scrittura log Audit
            //ruoliService.setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_RUOLO, null, 
            //		UUID.randomUUID().toString(), modificaRuoloModel.getId()+"- "+ruoliSelCollTipoForLogAudit+"- "+ruoliCompatibiliForLogAudit, ConstantsWebApp.MODIFICA_RUOLO, getData());

			return mav;

		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			messaggiErrore.add(ruoliService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}

	public boolean recuperaFlag(RuoloDto ruoloRecuperato) {
		Timestamp dataCorrente = Utils.sysdate();

		if ((ruoloRecuperato.getDataInizioValidita() == null && ruoloRecuperato.getDataFineValidita() == null)
				|| (ruoloRecuperato.getDataFineValidita() != null
						&& dataCorrente.after(ruoloRecuperato.getDataFineValidita())
						|| (ruoloRecuperato.getDataInizioValidita() != null
								&& dataCorrente.before(ruoloRecuperato.getDataInizioValidita())))) {
			return false;
		}
		if ((ruoloRecuperato.getDataInizioValidita() != null && ruoloRecuperato.getDataFineValidita() == null)
				|| (dataCorrente.before(ruoloRecuperato.getDataFineValidita())
						&& dataCorrente.after(ruoloRecuperato.getDataInizioValidita()))) {
			return true;
		}
		return false;
	}

	public Boolean checkInsertByConfiguratore(RicercaRuoloModel ricercaRuoloModel) {
		if (ricercaRuoloModel.getFlagConfiguratore() == null || ricercaRuoloModel.getFlagConfiguratore().isEmpty()) {
			return null;
		}
		return "true".equalsIgnoreCase(ricercaRuoloModel.getFlagConfiguratore()) ? true : false;
	}

}