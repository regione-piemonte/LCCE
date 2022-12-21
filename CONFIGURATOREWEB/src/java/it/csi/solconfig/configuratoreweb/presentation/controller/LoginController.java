/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import it.csi.iride2.policy.entity.Identita;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.LoginDataService;
import it.csi.solconfig.configuratoreweb.business.service.LoginService;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.GetTokenInformation2Request;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.GetTokenInformation2Response;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.TokenInformationServiceClient;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.Ruolo;
import it.csi.solconfig.configuratoreweb.presentation.model.Utente;
import it.csi.solconfig.configuratoreweb.presentation.model.Collocazione;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.RequestUtils;
import it.csi.solconfig.configuratoreweb.util.ShibboletFilter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Scope("prototype")
public class LoginController extends BaseController {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	TokenInformationServiceClient tokenInformationServiceClient;

	@Autowired
	LoginDataService loginDataService;

	@Autowired
	LoginService loginService;

	@Value("${callLcceDevMode:false}")
	private boolean callLcceDevMode;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, @RequestParam(value = "token", required = true) String token) {

		ModelAndView mav = new ModelAndView();

		try {
			Data data = getData();


			log.info(" -- init Login con filtro -- ");

			String ipAddressClient = getIpAddressClient(request);

			GetTokenInformation2Request getTokenInformation2Request = new GetTokenInformation2Request();
			getTokenInformation2Request.setApplicazione(Constants.APPLICATION_CODE);
			getTokenInformation2Request.setIpBrowser(ipAddressClient);
			getTokenInformation2Request.setToken(token);

			GetTokenInformation2Response getTokenInformation2Response = tokenInformationServiceClient.call(getTokenInformation2Request);

			
			if(getTokenInformation2Response == null || (getTokenInformation2Response.getErrori() != null && !getTokenInformation2Response.getErrori().isEmpty())){
				if(getTokenInformation2Response != null){
					log.error("ERROR: login tokenInformation - " +
									getTokenInformation2Response.getErrori().get(0).getCodice()
									+ getTokenInformation2Response.getErrori().get(0).getDescrizione());
				}
				mav.setViewName(ConstantsWebApp.ERROR);
				return mav;
			}

			data.setUtente(loginService.getUtenteLogin(getTokenInformation2Response, ipAddressClient));
			data.setCodiceRuoloSelezionato(data.getUtente().getRuolo().getCodice());
			updateData(data);
			
			/* Log Audit */
			  // Scrittura log Audit
			loginService.setLogAuditSOLNew(OperazioneEnum.LOGIN, ConstantsWebApp.KEY_OPER_LOGIN, null, 
	        		null, null, null, getData());

			mav = new ModelAndView(ConstantsWebApp.REDIRECT_UTENTI);
		} catch (Exception e) {
			log.error("ERROR: login - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/loginDemo", method = RequestMethod.GET)
	public ModelAndView loginDemo(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		/*
		 * Forzo la sessione a valida in quanto siamo in demo
		 */
		try {
			if (!ShibboletFilter.isDevMode())
				throw new Exception("URL NON VALIDA!!");

			request.setAttribute("it.csi.dma.isRequestedSessionIdValid", true);
			data = getData();

			log.info(" -- init Login senza filtro -- ");
			String token = UUID.randomUUID().toString();

			String ipAddressClient = getIpAddressClient(request);

			/*
			 * Per login demo inseriamo un valore fittizio nella login data per poi poter
			 * chiamare il servizio tokenInformation per recuperarlo
			 */
			final String marker = ShibboletFilter.getTokenDevMode(request);

			String ruolo = request.getParameter("ruolo");
			String collocazione = request.getParameter("collocazione");
			String azienda = request.getParameter("azienda");

			if (request.getParameter("ruolo") == null) {
				ruolo = "OAM";
				collocazione = "010301";
				azienda = "010301";
			}

			
			if (!callLcceDevMode) {
				log.info("[LoginController::loginDemo] caricmento dati per chiamata a lcce");
				if (marker != null) {
					loginDataService.insertLoginDataDemo(new Identita(marker), token, ipAddressClient, ruolo, azienda,
							collocazione);
				}

				mav = login(request, token);
			} else {
				log.info("[LoginController::loginDemo] caricmento dati login con /getTokenInformation2Response-demo.xml");
				GetTokenInformation2Response getTokenInformation2Response = RequestUtils.load(GetTokenInformation2Response.class, "/getTokenInformation2Response-demo.xml");
				data.setUtente(loginService.getUtenteLogin(getTokenInformation2Response, ipAddressClient));
				data.setCodiceRuoloSelezionato(data.getUtente().getRuolo().getCodice());
				updateData(data);

				/* Log Audit */
				setLogAuditSOL(OperazioneEnum.LOGIN, null);

				mav = new ModelAndView(ConstantsWebApp.REDIRECT_UTENTI);
				
			}
		} catch (Exception e) {
			log.error("ERROR: loginDemo - ", e);
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

	private List<String> getTokenInformation2BeanLoginDemo() {
		List<String> funzionalitaAbilitatePUA = new ArrayList<String>();
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRicercaUtente.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPInserisciNuovoUtente.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRichiestaCredenzialiRUPAR.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPInvioMailUtente.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPModificaConfigurazioneUtente.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPExportUtentiAbilitazioni.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRicercaRuolo.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPInserisciNuovoRuolo.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPModificaRuolo.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRicercaProfilo.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPInserisciNuovoProfilo.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPModificaProfilo.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRicercaCredenzialiRUPAR.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPListaSOLconConfiguratore.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPListaProfiliCompleta.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRicercaAbilitazioneMassiva.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPAbilitazioneMassiva.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPRicercaDisabilitazioneMassiva.getValue());
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPDisabilitazioneMassiva.getValue());

		//Profilo di demo SUPER USER
		funzionalitaAbilitatePUA.add(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());

		//Profilo operatore per testing
//		funzionalitaAbilitatePUA.add(FunzionalitaEnum.OPERATORE_PROF.getValue());


		return funzionalitaAbilitatePUA;
	}


}
