/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.controller;

import it.csi.dma.puawa.business.dao.*;
import it.csi.dma.puawa.business.dao.dto.ApplicazioneDto;
import it.csi.dma.puawa.business.dao.util.Constants;
import it.csi.dma.puawa.integration.abilitazioni.client.*;
import it.csi.dma.puawa.integration.authentication2.client.Authentication2ServiceClient;
import it.csi.dma.puawa.integration.authentication2.client.GetAuthentication2Request;
import it.csi.dma.puawa.integration.authentication2.client.GetAuthentication2Response;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.presentation.constants.ConstantsWebApp;
import it.csi.dma.puawa.presentation.model.Data;
import it.csi.dma.puawa.presentation.model.MessaggioWarning;
import it.csi.dma.puawa.presentation.model.Utente;
import it.csi.dma.puawa.presentation.model.ViewApplicazione;
import it.csi.dma.puawa.util.UrlUtils;
import it.csi.dma.puawa.util.Utils;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class HomePageController extends BaseController {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	private ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	private AbilitazioneLowDao abilitazioneLowDao;

	@Autowired
	private RuoloUtenteLowDao ruoloUtenteLowDao;

	@Autowired
	private RuoloLowDao ruoloLowDao;

	@Autowired
	private UtenteLowDao utenteLowDao;

	@Autowired
	private Authentication2ServiceClient authentication2ServiceClient;

	@Autowired
	private AbilitazioniServiceClient abilitazioniServiceClient;

	@Autowired
	private UrlUtils urlUtils;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView homePage(ModelAndView mav) {

		try {
			Data data = getData();

			GetAbilitazioniResponse getAbilitazioniResponse = callAbilitazioneService(data);

			if ((getAbilitazioniResponse.getErrori() == null || getAbilitazioniResponse.getErrori().isEmpty())
					&& getAbilitazioniResponse.getListaAbilitazioni() != null
					&& !getAbilitazioniResponse.getListaAbilitazioni().isEmpty()) {

				Utente utente = data.getUtente();
				utente.setListaAbilitazioni(filterAbilitazione(getAbilitazioniResponse.getListaAbilitazioni()));

				//Recupero per ogni applicazione le informazioni dinamiche per visualizzarla in jsp
				getListViewApplicazioni(utente);

				data.setUtente(utente);
				mav.setViewName(ConstantsWebApp.HOME_PAGE);
			} else {
				mav.setViewName(ConstantsWebApp.SCELTA_RUOLI);
				mav.addObject("errore", new MessaggioWarning("Nessuna abilitazione presente"));
			}
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: homePage - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ModelAndView redirectSol(ModelAndView mav,
			@RequestParam(value = "codiceApplicazione") String codiceApplicazione) {

		try {
			mav.setViewName(ConstantsWebApp.HOME_PAGE);
			Data data = getData();

			GetAuthentication2Response getAuthentication2Response = callGetAuthentication2(codiceApplicazione, data);

			String codiceLog = ConstantsWebApp.SCELTA_APPLICATIVO_LOG;

			if (getAuthentication2Response.getErrori() != null && !getAuthentication2Response.getErrori().isEmpty()) {
				mav.addObject("errori", getAuthentication2Response.getErrori());
			} else {
				ApplicazioneDto applicazione =
						Utils.getFirstRecord(applicazioneLowDao.findByCodice(new ApplicazioneDto(), codiceApplicazione));
				String redirectUrl = applicazione.getRedirectUrl();
				if (redirectUrl != null && redirectUrl.endsWith("=")) {
					mav = new ModelAndView(
							redirectUrl + getAuthentication2Response.getAuthenticationToken());
				} else {
					mav = new ModelAndView(redirectUrl);
				}
			}

			// Configurazione logAudit per sceltaSOL
			LogAuditRichiedente richiedente = popolaLogAudit(codiceApplicazione, data);

			setLogAudit(richiedente, codiceLog, data.getUtente().getViewCollocazione().getColCodAzienda(),
					data.getColCodiceCollocazioneSelezionata(), null,
					getAuthentication2Response.getAuthenticationToken(), null, codiceApplicazione);
		} catch (Exception e) {
			log.error("ERROR: redirectSol - " , e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	private LogAuditRichiedente popolaLogAudit(@RequestParam("codiceApplicazione") String codiceApplicazione, Data data) {
		LogAuditRichiedente richiedente = new LogAuditRichiedente();
		richiedente.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
		richiedente.setApplicazioneRichiesta(codiceApplicazione);
		richiedente.setCodiceCollocazioneRichiedente(data.getColCodiceCollocazioneSelezionata());
		richiedente.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
		richiedente.setCodiceRuoloRichiedente(data.getCodiceRuoloSelezionato());
		richiedente.setCollCodiceAziendaRichiedente(data.getUtente().getViewCollocazione().getColCodAzienda());
		richiedente.setIpChiamante(data.getUtente().getIpAddress());
		return richiedente;
	}

	private GetAuthentication2Response callGetAuthentication2(@RequestParam("codiceApplicazione") String codiceApplicazione, Data data) {
		GetAuthentication2Request getAuthentication2Request = new GetAuthentication2Request();
		it.csi.dma.puawa.integration.authentication2.client.Richiedente richiedenteAuthentication2 = new it.csi.dma.puawa.integration.authentication2.client.Richiedente();
		richiedenteAuthentication2.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
		richiedenteAuthentication2.setApplicazioneRichiesta(codiceApplicazione);
		richiedenteAuthentication2.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
		richiedenteAuthentication2.setCodiceCollocazione(data.getColCodiceCollocazioneSelezionata());
		richiedenteAuthentication2.setCollCodiceAzienda(data.getUtente().getViewCollocazione().getColCodAzienda());
		richiedenteAuthentication2.setIpClient(data.getUtente().getIpAddress());
		richiedenteAuthentication2.setRuolo(data.getUtente().getRuolo().getCodice());

		getAuthentication2Request.setRichiedente(richiedenteAuthentication2);

		// Chiamata GetAuthentication2
		return authentication2ServiceClient
				.call(getAuthentication2Request);
	}

	private List<Abilitazione> filterAbilitazione(List<Abilitazione> listaAbilitazione) {

		List<Abilitazione> lista = new ArrayList<Abilitazione>();

		for (Abilitazione abilitazione : listaAbilitazione) {
			if (!lista.contains(abilitazione)) {
				lista.add(abilitazione);
			}
		}

		return lista;
	}



	private void getListViewApplicazioni(Utente utente) {
		List<ViewApplicazione> listaApplicazioni = new ArrayList<ViewApplicazione>();
		for(Abilitazione abilitazione : utente.getListaAbilitazioni()){
			ViewApplicazione viewApplicazione = new ViewApplicazione();
			ApplicazioneDto applicazioneDto =
					Utils.getFirstRecord(applicazioneLowDao.findByCodice(new ApplicazioneDto(),
							abilitazione.getApplicazione().getCodiceApplicazione()));
			viewApplicazione.setCodice(applicazioneDto.getCodice());
			viewApplicazione.setDescrizione(applicazioneDto.getDescrizioneWebapp());
			viewApplicazione.setPathImmagine(applicazioneDto.getPathImmagine());
			viewApplicazione.setBottone(applicazioneDto.getBottone());
			listaApplicazioni.add(viewApplicazione);
		}
		utente.setListaApplicazioni(listaApplicazioni);
	}

	private GetAbilitazioniResponse callAbilitazioneService(Data data) {
		GetAbilitazioniRequest getAbilitazioniRequest = new GetAbilitazioniRequest();

		Richiedente richiedente = new Richiedente();
		richiedente.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
		richiedente.setCodiceCollocazioneRichiedente(data.getColCodiceCollocazioneSelezionata());
		richiedente.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
		richiedente.setCodiceRuoloRichiedente(data.getCodiceRuoloSelezionato());
		richiedente.setCollCodiceAziendaRichiedente(data.getUtente().getViewCollocazione().getColCodAzienda());
		richiedente.setIpChiamante(data.getUtente().getIpAddress());
		getAbilitazioniRequest.setRichiedente(richiedente);

		return abilitazioniServiceClient.call(getAbilitazioniRequest);
	}
}