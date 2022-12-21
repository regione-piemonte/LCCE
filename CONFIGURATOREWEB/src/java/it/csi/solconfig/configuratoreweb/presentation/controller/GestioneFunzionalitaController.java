/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.PermessoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.InserimentoProfiloFunzionalitaBody;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.service.ApplicazioniService;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.business.service.FunzionalitaService;
import it.csi.solconfig.configuratoreweb.business.service.PostProfiloFunzionalitaService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.Permesso;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaFunzionalitaModel;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.TipologiaDatoEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Controller
@Scope("prototype")
public class GestioneFunzionalitaController extends BaseController {

	@Autowired
	private FunzionalitaService funzionalitaService;
	
	@Autowired
	private ApplicazioniService applicazioneService;
	
	@Autowired
	private PermessoLowDao permessoService;

    @Autowired
    private CollocazioneService collocazioneService;
	
	@Autowired
	private PostProfiloFunzionalitaService postProfiloFunzionalitaService;

	@RequestMapping(value = "/searchFunzionalita", method = RequestMethod.POST)
	public ModelAndView searchFunzionalita(
			@ModelAttribute("funzionalita") RicercaFunzionalitaModel ricercaFunzionalitaModel, ModelAndView mav)
			throws Exception {
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {
			log.info(" -- Ricerca FUNZ -- ");
			data = getData();

			PaginaDTO<FunzionalitaDto> paginaDTO = funzionalitaService.ricercaFunzionalita(ricercaFunzionalitaModel);

			if (paginaDTO.getElementiTotali() == 0) {
				log.error("WARNING: Nessuna funzionalita trovata");
				messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.FUNZIONALITA_NON_TROVATA));
				mav.setViewName(ConstantsWebApp.GESTIONE_FUNZIONALITA);
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
			funzionalitaService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_RICERCA_FUNZ, null, 
            		UUID.randomUUID().toString(), null, ConstantsWebApp.RICERCA_FUNZ, getData());
            
			updateData(data);
			mav.setViewName(ConstantsWebApp.GESTIONE_FUNZIONALITA);
		} catch (Exception e) {
			log.error("ERROR: applicazioni - ", e);
			messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}
		return mav;
	}

	private void initCombo(ModelAndView mav) {
		// TODO Auto-generated method stub

	}

	@RequestMapping(value = "/inserimentoNuovaFunzionalita", method = RequestMethod.GET)
	public ModelAndView showInserisciFunzionalita(ModelAndView mav) {
		FunzionalitaModel f=new FunzionalitaModel();
		f.setFunzionalitaAttivo(true);
		return showInserisciFunzionalita(mav, f, permessoService.findValid());
	}

	public ModelAndView showInserisciFunzionalita(ModelAndView mav, FunzionalitaModel funzionalitaModel,
			List<PermessoDto> permessi) {
		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.INSERISCI_FUNZIONALITA);
			mav.addObject("funzionalita", funzionalitaModel);
			mav.addObject("permessi", permessi);

			initCombo(mav);

			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: showInserisciFunzionalita - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	public List<MessaggiUtenteDto> checkCampiObbligatori(FunzionalitaModel funzionalitaModel) throws Exception {
		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		if ((funzionalitaModel.getCodice() == null || funzionalitaModel.getCodice().isEmpty()) || //
				(funzionalitaModel.getDescrizione() == null || funzionalitaModel.getDescrizione().isEmpty()) || //
				(funzionalitaModel.getIdApplicazione() == null || funzionalitaModel.getIdApplicazione().isEmpty()) || // ---
				(funzionalitaModel.getDatiAmministrativi() == null
						|| funzionalitaModel.getDatiAmministrativi().isEmpty())
				|| //
				(funzionalitaModel.getDatiAnagrafici() == null || funzionalitaModel.getDatiAmministrativi().isEmpty())
				|| //
				(funzionalitaModel.getDatiClinici() == null || funzionalitaModel.getDatiClinici().isEmpty()) || //
				(funzionalitaModel.getDatiConsenso() == null || funzionalitaModel.getDatiConsenso().isEmpty()) || //
				(funzionalitaModel.getDatiPrescrittivi() == null || funzionalitaModel.getDatiPrescrittivi().isEmpty()) //
		) {
			messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.CAMPI_NON_VALORIZZATI));

		}
		return messaggiErrore;
	}

	@RequestMapping(value = "/inserimentoNuovaFunzionalita", method = RequestMethod.POST)
	public ModelAndView inserimentoNuovaFunzionalita(
			@ModelAttribute("funzionalita") FunzionalitaModel funzionalitaModel, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try {

			Data data = getData();
			log.info(" -- inserimentoNuovaFunzionalita  -- ");

			messaggiErrore.addAll(checkCampiObbligatori(funzionalitaModel));
			if (messaggiErrore.size() > 0) {
				// return showInserisciFunzionalita(mav, funzionalitaModel, perm);
				return showInserisciFunzionalita(mav, funzionalitaModel, permessoService.findValid());
			} else {

				
				if (funzionalitaService.existsFunzionalita(funzionalitaModel.getCodice()) ) {
					messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.FUNZIONALITA_GIA_PRESENTE));
					log.info("elemento trovato: codice="+funzionalitaModel.getCodice());
					return showInserisciFunzionalita(mav, funzionalitaModel, permessoService.findValid());
				} else {
					log.info("elemento nuovo");
				}
				
				// log.info("[GestioneApplicazioniController::inserimentoNuovaApplicazione]
				// ApplicazioneModel::"
				// + applicazioneModel.toString());
				InserimentoProfiloFunzionalitaBody body = new InserimentoProfiloFunzionalitaBody();
				List<Permesso> perm = createPermessiList(funzionalitaModel);
				body.setTipologiaDatiPermessi(perm);

				ApplicazioneDto appl = applicazioneService
						.findApplicazioneById(Long.parseLong(funzionalitaModel.getIdApplicazione()));
				funzionalitaModel.setApplicazione(appl.getCodice());

				TreeFunzionalitaDto funzionalitaTree = postProfiloFunzionalitaService.insertNuovaFunzionalita(funzionalitaModel.getCodice(),
						funzionalitaModel.getDescrizione(), funzionalitaModel.getApplicazione(), body,
						data.getUtente().getCodiceFiscale()); // .modificaFunzionalita(funzionalitaModel, body, codiceAmbiente);

				
				// Scrittura log Audit
				funzionalitaService.setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_INSERISCI_FUNZ, null, 
		        		UUID.randomUUID().toString(), funzionalitaTree.getFunzionalitaDto().getIdFunzione() + " - " + funzionalitaTree.getIdTreeFunzione(), ConstantsWebApp.INSERISCI_FUNZ, data);
				
				// Long idNuovaFunz = funzionalitaService.inserisciFunzionalita(funzionalitaModel);
				// visualizza messaggio di successo
				log.info("Salvataggio effettuato");

				// vai alla modifica
				messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_EFFETTUATA));
				return showUpdateFunzionalita(funzionalitaTree.getFunzionalitaDto().getIdFunzione(), funzionalitaModel, mav);
			}

		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}

	// controller che visualizza pagina con i dati ruolo da modificare
	@RequestMapping(value = "/updateFunzionalita", method = RequestMethod.GET)
	public ModelAndView showUpdateFunzionalita(@RequestParam(value = "id") long id,
			@ModelAttribute("funzionalita") FunzionalitaModel funzionalita, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();

		try { 
			Data data = getData();
			

			// recupero il ruolo da visualizzare
			funzionalitaService.loadFunzionalitaById(id, funzionalita);

			updateData(data);
			mav.addObject("funzionalita", funzionalita);
			
			List<PermessoDto>permessi = permessoService.findValid();
			mav.addObject("permessi", permessi);
			
			initCombo(mav);
			mav.setViewName(ConstantsWebApp.EDIT_FUNZIONALITA);
			// modificaApplicazioni
			return mav;

		} catch (Exception e) {
			log.error("ERROR: update - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}


	@RequestMapping(value = "/modificaFunzionalita", method = RequestMethod.POST)
	public ModelAndView modificaFunzionalita(
			@ModelAttribute("funzionalita") FunzionalitaModel funzionalitaModel, ModelAndView mav) throws Exception {

		List<MessaggiUtenteDto> messaggiErrore = new ArrayList<MessaggiUtenteDto>();
Long idFunz =null;
		try {

			Data data = getData();
			log.info(" -- modificaFunzionalita  -- ");

			messaggiErrore.addAll(checkCampiObbligatori(funzionalitaModel));
			if (messaggiErrore.size() > 0) {
				idFunz = funzionalitaModel.getId();
			} else {
				InserimentoProfiloFunzionalitaBody body = new InserimentoProfiloFunzionalitaBody();
				List<Permesso> perm = createPermessiList(funzionalitaModel);
				body.setTipologiaDatiPermessi(perm);

				FunzionalitaDto funzionalita = postProfiloFunzionalitaService.modificaFunzionalita(funzionalitaModel, body,
						data.getUtente().getCodiceFiscale());
				
				idFunz = funzionalita.getIdFunzione();
				
				// Scrittura log Audit
				funzionalitaService.setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_FUNZ, null, 
						UUID.randomUUID().toString(), idFunz + " - ", ConstantsWebApp.MODIFICA_FUNZ, data);

				// visualizza messaggio di successo
				log.info("Modifica effettuato");

				// vai alla modifica
				messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_EFFETTUATA));
			}
			
			return showUpdateFunzionalita(idFunz, funzionalitaModel, mav);
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			messaggiErrore.add(funzionalitaService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
			mav.setViewName(ConstantsWebApp.ERROR);

		} finally {

			mav.addObject("errori", messaggiErrore);
		}

		return mav;
	}

	private List<Permesso> createPermessiList(FunzionalitaModel funzionalitaModel) {
		List<Permesso> permessi = new ArrayList<>();
		Permesso permDatiAnagrafici = new Permesso();
		permDatiAnagrafici.setCodiceTipologiaDato(TipologiaDatoEnum.DATI_ANAGRAFICI.getValue());
		permDatiAnagrafici.setCodicePermesso(funzionalitaModel.getDatiAnagrafici());
		permessi.add(permDatiAnagrafici);
		Permesso permDatiPrescrittivi = new Permesso();
		permDatiPrescrittivi.setCodiceTipologiaDato(TipologiaDatoEnum.DATI_PRESCRITTIVI.getValue());
		permDatiPrescrittivi.setCodicePermesso(funzionalitaModel.getDatiPrescrittivi());
		permessi.add(permDatiPrescrittivi);
		Permesso permDatiClinici = new Permesso();
		permDatiClinici.setCodiceTipologiaDato(TipologiaDatoEnum.DATI_CLINICI.getValue());
		permDatiClinici.setCodicePermesso(funzionalitaModel.getDatiClinici());
		permessi.add(permDatiClinici);
		Permesso permDatiConsenso = new Permesso();
		permDatiConsenso.setCodiceTipologiaDato(TipologiaDatoEnum.DATI_DI_CONSENSO.getValue());
		permDatiConsenso.setCodicePermesso(funzionalitaModel.getDatiConsenso());
		permessi.add(permDatiConsenso);
		Permesso permDatiAmministrativi = new Permesso();
		permDatiAmministrativi.setCodiceTipologiaDato(TipologiaDatoEnum.DATI_AMMINISTRATIVI.getValue());
		permDatiAmministrativi.setCodicePermesso(funzionalitaModel.getDatiAmministrativi());
		permessi.add(permDatiAmministrativi);
		
		return permessi;
	}


}
