/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.controller;

import it.csi.dma.puawa.business.dao.util.Constants;
import it.csi.dma.puawa.integration.collocazioni.client.*;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.integration.ruoliUtente.client.GetRuoliUtenteRequest;
import it.csi.dma.puawa.integration.ruoliUtente.client.GetRuoliUtenteResponse;
import it.csi.dma.puawa.integration.ruoliUtente.client.RuoliUtenteServiceClient;
import it.csi.dma.puawa.integration.ruoliUtente.client.Ruolo;
import it.csi.dma.puawa.presentation.constants.ConstantsWebApp;
import it.csi.dma.puawa.presentation.model.Data;
import it.csi.dma.puawa.presentation.model.MessaggioWarning;
import it.csi.dma.puawa.presentation.model.Utente;
import it.csi.iride2.policy.entity.Identita;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class LoginController extends BaseController {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	private RuoliUtenteServiceClient ruoliUtenteServiceClient;

	@Autowired
	private CollocazioniServiceClient collocazioniServiceClient;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		try {
			Data data = getData();

			log.info(" -- init Login con filtro -- ");

			/* Lettura identita' con popolamento oggetti necessari */
			Object identitaSession = session.getAttribute("edu.yale.its.tp.cas.client.filter.user");

			log.info(" -- identitaSession - primo tentativo =  -- " + identitaSession);

			if(identitaSession == null){
				identitaSession = session.getAttribute("Shib-Iride-IdentitaDigitale");
				log.info(" -- identitaSession - secondo tentativo =  -- " + identitaSession);
			}

			if (identitaSession != null) {

				Identita identita = new Identita(identitaSession.toString());
				Utente utente = new Utente();
				utente.setNome(identita.getNome());
				utente.setCognome(identita.getCognome());
				utente.setCodiceFiscale(identita.getCodFiscale());
				utente.setIpAddress(getIpAddressClient(request));
				data.setUtente(utente);
				updateData(data);
				/* Log Audit */
				LogAuditRichiedente logAuditRichiedente = new LogAuditRichiedente();
				logAuditRichiedente.setCodiceFiscaleRichiedente(identita.getCodFiscale());
				logAuditRichiedente.setIpChiamante(getIpAddressClient(request));
				setLogAudit(logAuditRichiedente, ConstantsWebApp.LOGIN_PUA_LOG, null, null, null, null, null);
			}
			mav = new ModelAndView(ConstantsWebApp.REDIRECT_SCELTA_RUOLI);
		} catch (Exception e) {
			log.error("ERROR: login - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/loginDemo", method = RequestMethod.GET)
	public ModelAndView loginDemo(HttpServletRequest request, @RequestParam(value = "cf", required = false) String cf) {

		ModelAndView mav = new ModelAndView();

		log.info(" -- init Login senza filtro -- ");

		try {
			Data data = getData();

			/* Creazione utente Demo */
			Utente utente = new Utente();
			utente.setNome("ALICE");
			utente.setCognome("ALICI");
			utente.setIpAddress(getIpAddressClient(request));
			if (cf != null) {
				utente.setCodiceFiscale(cf);
			} else {
				utente.setCodiceFiscale("LCALCA55E42G273G");
			}

			data.setUtente(utente);
			updateData(data);

			// LogAudit
			LogAuditRichiedente logAuditRichiedente = new LogAuditRichiedente();
			logAuditRichiedente.setCodiceFiscaleRichiedente(utente.getCodiceFiscale());
			logAuditRichiedente.setIpChiamante(getIpAddressClient(request));
			setLogAudit(logAuditRichiedente, ConstantsWebApp.LOGIN_PUA_LOG, null, null, null, null, null);

			mav.setViewName(ConstantsWebApp.REDIRECT_SCELTA_RUOLI);
		} catch (Exception e) {
			log.error("ERROR: loginDemo - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		return mav;
	}

	@RequestMapping(value = "/sceltaRuoli", method = RequestMethod.GET)
	public ModelAndView sceltaRuoli(ModelAndView mav) {

		try {
			Data data = getData();
			Utente utente = getData().getUtente();

			log.info(" -- init scelta ruoli -- " + utente);

			// Lista Ruoli
			GetRuoliUtenteRequest getRuoliUtenteRequest = new GetRuoliUtenteRequest();
			it.csi.dma.puawa.integration.ruoliUtente.client.Richiedente richiedenteRuoli = new it.csi.dma.puawa.integration.ruoliUtente.client.Richiedente();
			richiedenteRuoli.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
			richiedenteRuoli.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
			richiedenteRuoli.setIpChiamante(utente.getIpAddress());
			getRuoliUtenteRequest.setRichiedente(richiedenteRuoli);
			GetRuoliUtenteResponse ruoliUtenteResponse = ruoliUtenteServiceClient.call(getRuoliUtenteRequest);

			// Settaggio eventuali errori
			if (ruoliUtenteResponse != null && ruoliUtenteResponse.getListaRuoli() != null
					&& !ruoliUtenteResponse.getListaRuoli().isEmpty()) {
				log.error(" -- lista ruoli trovata -- ");
				// Se non ci sono errori
				List<Ruolo> listaRuoli = (List<Ruolo>) ruoliUtenteResponse.getListaRuoli();
				utente.setListaRuoli(listaRuoli);
				data.setUtente(utente);
				// Se c'e' un solo ruolo, lo setta come selezionato
				if (ruoliUtenteResponse.getListaRuoli().size() == 1) {
					log.error(" -- un solo ruolo trovato nella lista -- ");
					data.setCodiceRuoloSelezionato(listaRuoli.get(0).getCodice());
					utente.setRuolo(new Ruolo(listaRuoli.get(0).getCodice(), listaRuoli.get(0).getDescrizione()));
					data.setUtente(utente);
					updateData(data);
					mav = sceltaCollocazioni(mav, listaRuoli.get(0).getCodice(), listaRuoli.get(0).getDescrizione());
				}
			} else {
				mav.addObject("errore", new MessaggioWarning("Non esiste alcun ruolo associato all'utente connesso"));
			}
			updateData(data);
			mav.setViewName(ConstantsWebApp.SCELTA_RUOLI);
		} catch (Exception e) {
			log.error("ERROR: sceltaRuoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/sceltaCollocazioni", method = RequestMethod.GET)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ModelAndView sceltaCollocazioni(ModelAndView mav,
			@RequestParam(value = "codiceRuoloSelezionato") String codiceRuoloSelezionato,
			@RequestParam(value = "descrizioneRuoloSelezionata") String descrizioneRuoloSelezionata) {

		try {
			Data data = getData();
			data.setCodiceRuoloSelezionato(codiceRuoloSelezionato);
			Utente utente = getData().getUtente();
			utente.setViewCollocazione(null);
			data.setColCodiceCollocazioneSelezionata(null);
			utente.setRuolo(new Ruolo(codiceRuoloSelezionato, descrizioneRuoloSelezionata));
			data.setUtente(utente);
			updateData(data);

			// Configurazione LogAuditRichiedente per sceltaRuoli
			LogAuditRichiedente logAuditRichiedente = new LogAuditRichiedente();
			logAuditRichiedente.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
			logAuditRichiedente.setCodiceFiscaleRichiedente(utente.getCodiceFiscale());
			logAuditRichiedente.setCodiceRuoloRichiedente(codiceRuoloSelezionato);
			logAuditRichiedente.setIpChiamante(utente.getIpAddress());

			// LogAudit di sceltaRuoli, impostato una volta selezionato il ruolo
			setLogAudit(logAuditRichiedente, ConstantsWebApp.SCELTA_RUOLI_LOG,
					null, null, null, null, null, descrizioneRuoloSelezionata);

			// Chiamata al servizio collocazioniService
			GetCollocazioniRequest getCollocazioniRequest = new GetCollocazioniRequest();
			it.csi.dma.puawa.integration.collocazioni.client.Richiedente richiedenteCollocazioni = new it.csi.dma.puawa.integration.collocazioni.client.Richiedente();
			richiedenteCollocazioni.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
			richiedenteCollocazioni.setCodiceFiscaleRichiedente(utente.getCodiceFiscale());
			richiedenteCollocazioni.setCodiceRuoloRichiedente(codiceRuoloSelezionato);
			richiedenteCollocazioni.setIpChiamante(utente.getIpAddress());

			getCollocazioniRequest.setRichiedente(richiedenteCollocazioni);
			GetCollocazioniResponse collocazioniResponse = collocazioniServiceClient.call(getCollocazioniRequest);

			if (collocazioniResponse.getCollocazioni() != null && !collocazioniResponse.getCollocazioni().isEmpty()) {
				List<ViewCollocazione> listaViewCollocazioni = new ArrayList<ViewCollocazione>();
				for (Collocazione collocazione : collocazioniResponse.getCollocazioni()) {

					ViewCollocazione viewCollocazione = convertCollocazione(collocazione);

					if (!contains(listaViewCollocazioni, viewCollocazione)) {
						viewCollocazione.setRadioId("radio" + listaViewCollocazioni.size());
						viewCollocazione.setLabelId("label" + listaViewCollocazioni.size());
						listaViewCollocazioni.add(viewCollocazione);
					}
				}
				utente.setViewListaCollocazioni(listaViewCollocazioni);
			} else {
				mav.addObject("errore", new MessaggioWarning("Nessuna collocazione disponibile"));
				utente.setViewListaCollocazioni(new ArrayList<ViewCollocazione>());
			}

			if (utente.getViewListaCollocazioni().size() == 1) {
				mav = handleCollocazioni(mav,
						utente.getViewListaCollocazioni().get(0).getColCodice(),
						utente.getViewListaCollocazioni().get(0).getColCodAzienda());
			}

			data.setUtente(utente);
			updateData(data);
			mav.setViewName(ConstantsWebApp.SCELTA_RUOLI);
		} catch (Exception e) {
			log.error("ERROR: sceltaCollocazioni - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/handleCollocazioni", method = RequestMethod.GET)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ModelAndView handleCollocazioni(ModelAndView mav,
			@RequestParam(value = "codiceCollocazioneSelezionato") String codiceCollocazioneSelezionato,
		    @RequestParam(value = "codiceColAziendaSelezionata") String codiceColAziendaSelezionata) {

		try{
			Data data = getData();

			// Configurera' il ViewColCodice
			data.setColCodiceCollocazioneSelezionata(codiceCollocazioneSelezionato);
			data.setCodiceColAziendaSelezionata(codiceColAziendaSelezionata);
			updateData(data);
			mav.setViewName(ConstantsWebApp.SCELTA_RUOLI);
		}catch(Exception e){
			log.error("ERROR: handleCollocazioni - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/continue", method = RequestMethod.GET)
	public ModelAndView prosegui(ModelAndView mav) {
		try{
			Data data = getData();
			Utente utente = getData().getUtente();

			for (ViewCollocazione collocazione : data.getUtente().getViewListaCollocazioni()) {
				if (collocazione.getColCodice().equals(data.getColCodiceCollocazioneSelezionata()) &&
					collocazione.getColCodAzienda().equals(data.getCodiceColAziendaSelezionata())) {

					utente.setViewCollocazione(collocazione);
					data.setUtente(utente);

					// Configurazione logAudit per scelta Collocazioni
					LogAuditRichiedente richiedente = new LogAuditRichiedente();
					richiedente.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
					richiedente.setCodiceFiscaleRichiedente(utente.getCodiceFiscale());
					richiedente.setCodiceRuoloRichiedente(data.getCodiceRuoloSelezionato());
					richiedente.setIpChiamante(utente.getIpAddress());

					// LogAudit effettivo
					setLogAudit(richiedente, ConstantsWebApp.SCELTA_COLLOCAZIONI_LOG, collocazione.getColCodAzienda(),
							collocazione.getColCodice(), null, null, null, collocazione.getColDescrizione());
				}
			}

			// Da cambiare in seguito
			mav.setViewName(ConstantsWebApp.REDIRECT_HOME);
			updateData(data);
		}catch(Exception e){
			log.error("ERROR: prosegui - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	private String getIpAddressClient(HttpServletRequest request) {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	private Boolean contains(List<ViewCollocazione> lista, ViewCollocazione tempCollocazione) {

		Boolean check = false;
		for (ViewCollocazione collocazione : lista) {
			if ((collocazione.getColCodAzienda() == null && tempCollocazione.getColCodAzienda() == null)
					|| collocazione.getColCodAzienda().equals(tempCollocazione.getColCodAzienda())) {
				if ((collocazione.getColCodice() == null && tempCollocazione.getColCodice() == null)
						|| collocazione.getColCodice().equals(tempCollocazione.getColCodice())) {
					if ((collocazione.getColDescAzienda() == null && collocazione.getColDescAzienda() == null)
							|| collocazione.getColDescAzienda().equals(collocazione.getColDescAzienda())) {
						if ((collocazione.getColDescrizione() == null && collocazione.getColDescrizione() == null)
								|| collocazione.getColDescrizione().equals(collocazione.getColDescrizione())) {
							check = true;
						}
					}
				}
			}
		}
		return check;
	}

	private ViewCollocazione convertCollocazione(Collocazione collocazione) {
		ViewCollocazione viewCollocazione = new ViewCollocazione();
		viewCollocazione.setColDescrizione(collocazione.getColDescrizione());
		viewCollocazione.setColCodAzienda(collocazione.getColCodAzienda());
		viewCollocazione.setColCodice(collocazione.getColCodice());
		viewCollocazione.setColDescAzienda(collocazione.getColDescAzienda());

		viewCollocazione.setViewColCodice("");
		viewCollocazione.setViewColDescrizione("");

		String[] colDescrizioni = collocazione.getColDescrizione().toString().split("@");
		viewCollocazione.setViewIndirizzo("");
		for (int i = 0; i < colDescrizioni.length; ++i) {
			if (i == 0) {
				viewCollocazione.setViewNome(colDescrizioni[0]);
			} else {
				if (i != colDescrizioni.length - 1) {
					viewCollocazione.setViewIndirizzo(viewCollocazione.getViewIndirizzo() + colDescrizioni[i] + ", ");
				} else {
					viewCollocazione.setViewIndirizzo(viewCollocazione.getViewIndirizzo() + colDescrizioni[i]);
				}
			}
			if (i != colDescrizioni.length - 1) {
				viewCollocazione
						.setViewColDescrizione(viewCollocazione.getViewColDescrizione() + colDescrizioni[i] + ", ");
			} else {
				viewCollocazione.setViewColDescrizione(viewCollocazione.getViewColDescrizione() + colDescrizioni[i]);
			}
		}

		String[] colCodici = collocazione.getColCodice().split("@");
		for (int i = 0; i < colCodici.length; ++i) {
			if (i != colCodici.length - 1) {
				viewCollocazione.setViewColCodice(viewCollocazione.getViewColCodice() + colCodici[i] + ", ");
			} else {
				viewCollocazione.setViewColCodice(viewCollocazione.getViewColCodice() + colCodici[i]);
			}
		}

		return viewCollocazione;
	}
}
