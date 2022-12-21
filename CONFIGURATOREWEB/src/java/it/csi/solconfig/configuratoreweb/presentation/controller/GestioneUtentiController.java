/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtentiConfiguratoreViewDto;
import it.csi.solconfig.configuratoreweb.business.service.*;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.CodiceFiscale;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.exceptions.UserExistException;
import it.csi.solconfig.configuratoreweb.business.exceptions.WebServiceException;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.export.ExcelReportView;
import it.csi.solconfig.configuratoreweb.presentation.model.*;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Scope("prototype")
public class GestioneUtentiController extends BaseController {
//    @Autowired
//	private CodiceFiscale cfutils;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private TipoContrattoService tipoContrattoService;

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private CollocazioneService collocazioneService;

    @Autowired
    private ServiziOnLineService serviziOnLineService;

    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping("/nonautorizzato")
    public String visualizzaPaginaNonAutorizzato() {
        return "notAuthorized";
    }

    /* *** RICERCA UTENTE *** */

    @PostMapping("/cercaUtente")
    public String cercaUtente(Model model, @ModelAttribute("utente") RicercaUtenteModel ricercaUtenteModel, String error, HttpSession httpSession) throws Exception {
        if (isFunzioneValida(getData(), FunzionalitaEnum.OPRicercaUtente.getValue())) {
            List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
            
            // Scrittura log Audit
            utenteService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_RICERCA_CF, ricercaUtenteModel.getCodiceFiscale(), 
            		UUID.randomUUID().toString(), null, ConstantsWebApp.RICERCA_CF, getData());
            
            try {
                if (error == null) {
                    if (ricercaUtenteModel.getCodiceFiscale() == null || ricercaUtenteModel.getCodiceFiscale().isEmpty()) {
                        log.error("ERROR: Codice fiscale non specificato");
                        messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.CODICE_FISCALE_NON_PRESENTE));
                    } else {
                        if (CodiceFiscale.checkValiditaCodiceFiscale(ricercaUtenteModel.getCodiceFiscale())) {
                        	
                            PaginaDTO<RisultatiRicercaUtenteDTO> paginaDTO = utenteService.ricercaUtente(ricercaUtenteModel, getData());
                            if (paginaDTO.getElementiTotali() == 0) {
                            	try {
                            		FormNuovoUtente form = new FormNuovoUtente();
                                    getUserFromAuraOrMEF(ricercaUtenteModel.getCodiceFiscale(), httpSession, form);
                                    if(httpSession.getAttribute(ConstantsWebApp.DATI_ANAGRAFICI) != null)
                                    	return showInsertPage(model, form, false, false, null, httpSession, Collections.emptyList(), null, null);
                                    else {
                                      log.error("ERROR: Utente non presente");
                                      messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.ANAGRAFICA_NON_PRESENTE,
                                              ricercaUtenteModel.getCodiceFiscale()));
                                    }
                                } catch (WebServiceException wse) {
                                    return showErrorMessage(model, ricercaUtenteModel.getCodiceFiscale(), wse.getMessage());
                                }
                            	
                            	
                            } else {
                                model.addAttribute("paginaUtenti", paginaDTO);
                            }
                        } else {
                            log.error("ERROR: Codice fiscale errato");
                            messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.CODICE_FISCALE_ERRATO,
                                    ricercaUtenteModel.getCodiceFiscale()));
                        }
                    }
                } else {
                    model.addAttribute("utente", ricercaUtenteModel);
                    messaggiErrore.add(utenteService.aggiungiErrori(error));
                }
            } catch (Exception e) {
                log.error("ERROR: cercaUtente - ", e);
                messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
                return ConstantsWebApp.ERROR;
            } finally {
                model.addAttribute("errori", messaggiErrore);
            }

            return ConstantsWebApp.GESTIONE_UTENTI;
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }

    @PostMapping("/annullaRicerca")
    public String annullaRicerca() {
        return "redirect:/utenti";
    }

    @GetMapping("/esportaUtenti")
    public ModelAndView esportaUtenti() throws Exception {
        List<UtentiConfiguratoreViewDto> list = utenteService.exportUtenti(getData());
        ModelAndView exportList = new ModelAndView(new ExcelReportView(), "exportList", list);
        //setLogAuditSOL(OperazioneEnum.READ, "EXP_UTE");

        // Scrittura log Audit
        utenteService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_EXPORT_UTENTI, null, 
        		UUID.randomUUID().toString(), null, ConstantsWebApp.EXPORT_UTENTI, getData());

        return exportList;
    }

    /* *** INSERIMENTO UTENTE *** */

    @GetMapping("/inserisciUtente")
    public String visualizzaInserimentoUtente(Model model, @RequestParam String cf,
                                              @ModelAttribute FormNuovoUtente form, HttpSession httpSession) throws Exception {
        if (isFunzioneValida(getData(), FunzionalitaEnum.OPInserisciNuovoUtente.getValue())) {
            httpSession.setAttribute(ConstantsWebApp.FROM_AURA, null);

            try {
                //getUserFromAuraOrMEF(cf, httpSession, form);
                return showInsertPage(model, form, false, false, null, httpSession, Collections.emptyList(), null, null);
            } catch (WebServiceException wse) {
                return showErrorMessage(model, cf, wse.getMessage());
            }
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }

    @PostMapping("/inserisciUtente")
    public String salvaUtente(@Valid @ModelAttribute FormNuovoUtente form, BindingResult bindingResult, Model model, HttpSession httpSession)
            throws Exception {
        boolean dataSent = false;
        boolean profileComplete = false;
        List<MessaggiUtenteDto> messaggiErrore = Collections.emptyList();
        String params = "";
        Boolean esitoMailAura = false;

     //   try {
        	//getUserFromAuraOrMEF(form.getCf(), httpSession, form);

            if (!Optional.ofNullable(form.getProfiliSol()).map(List::isEmpty).orElse(true)
                    && Optional.ofNullable(form.getRuoli()).map(List::isEmpty).orElse(true)) {
                error("ruoli", "NotEmpty.ruoli", bindingResult, form);
            }

            if (!Optional.ofNullable(form.getProfiliSol()).map(List::isEmpty).orElse(true)
                    && Optional.ofNullable(form.getCollocazioni()).map(List::isEmpty).orElse(true)) {
                error("collocazioni", "NotEmpty.collocazioni", bindingResult, form);
            }
            
            if(Utils.listIsNullOrEmpty(form.getProfiliSol())) {
        		error("profiliSol", "Abilitazione", bindingResult, form);
            }

            try {
                if (!bindingResult.hasErrors()) {
                	if(session.getAttribute("uuidSalvaUtente") == null)
                		session.setAttribute("uuidSalvaUtente", (String) UUID.randomUUID().toString());
                	
                    SalvataggioUtenteModel utenteSalvato = utenteService.salvaUtente(form, getData().getUtente().getCodiceFiscale(), (String) session.getAttribute("uuidSalvaUtente"), getData());

                    profileComplete = !Optional.ofNullable(form.getRuoli()).map(List::isEmpty).orElse(true)
                            && !Optional.ofNullable(form.getCollocazioni()).map(List::isEmpty).orElse(true)
                            && !Optional.ofNullable(form.getProfiliSol()).map(List::isEmpty).orElse(true);

                    dataSent = true;
                    
                    if (isFunzioneValida(getData(), FunzionalitaEnum.OPInvioMailUtente.getValue())) {
//                    	List<MessaggiUtenteDto> messaggiErrore;
                    	UtenteDto utenteDto=null;
                    	
                    	if(utenteSalvato.checkMailAura()) {
                			List<MessaggiUtenteDto> messaggiErroreAura = utenteService.invioMailConfAdAura(form.getCf(), utenteSalvato);
                			params = utenteSalvato.getAbilitazioniAura().stream().map(a -> a.getApplicazioneDto().getCodice()).distinct().collect(Collectors.joining(", "));
                			esitoMailAura = !messaggiErroreAura.stream().anyMatch(m -> m.getCodice().equalsIgnoreCase(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA));
                		}
                    	
                        messaggiErrore = utenteService.invioMailAUtenteProfilato(form.getCf(), getData(), utenteSalvato);
                        utenteDto = utenteService.recuperaUtenteByCodiceFiscale(form.getCf());
                        FormNuovoUtente formNuovoUtente = new FormNuovoUtente();
                        mapUtenteDtoToFormNuovoUtente(utenteDto, formNuovoUtente);
//                        if (utenteDto.getIdAura() != null)
//                            httpSession.getAttribute(ConstantsWebApp.FROM_AURA);
//                        return tipoPagina.equals("modificaUtente")
//                                ? showEditPage(model, formNuovoUtente, utenteDto, false, null, messaggiErrore)
//                                : showInsertPage(model, formNuovoUtente, true, true, null, httpSession, messaggiErrore);
                    }
                    
                } else {
                	remapRuoliCollocazioniFromOpessan(form);
                }
            } catch (UserExistException uee) {
                error("cf", "CodiceFiscale.alreadyExists", bindingResult, form);
            }

            return showInsertPage(model, form, dataSent, profileComplete, bindingResult, httpSession, Collections.emptyList(), params, esitoMailAura);
        /*} finally {
            setLogAuditSOL(OperazioneEnum.INSERT, "INS_UTE");
        }*/
    }

	private void remapRuoliCollocazioniFromOpessan(FormNuovoUtente form) {
		if(form != null) {
			List<String> ruoliOpessan = !Utils.listIsNullOrEmpty(form.getRuoliFromOpessan()) ? 
					form.getRuoliFromOpessan().stream().map(ro -> ro.endsWith("ro") ? ro.substring(0, ro.length()-2) : ro).collect(Collectors.toList()) : Collections.emptyList();
			List<String> collocazioniOpessan = !Utils.listIsNullOrEmpty(form.getCollocazioniFromOpessan()) ? 
					form.getCollocazioniFromOpessan().stream().map(co -> co.endsWith("ro") ? co.substring(0, co.length()-2) : co).collect(Collectors.toList()) : Collections.emptyList();
			
			List<String> ruoliRemapped = !Utils.listIsNullOrEmpty(form.getRuoli()) ? 
					form.getRuoli().stream().map(r -> ruoliOpessan.contains(r) ? r.concat("ro") : r).collect(Collectors.toList()) : Collections.emptyList();
			List<String> collocazioniRemapped = !Utils.listIsNullOrEmpty(form.getCollocazioni()) ? 
					form.getCollocazioni().stream().map(c ->collocazioniOpessan.contains(c) ? c.concat("ro") : c).collect(Collectors.toList()) : Collections.emptyList();
			
			form.setRuoli(ruoliRemapped);
			form.setCollocazioni(collocazioniRemapped);
		}
	}

    private String showInsertPage(Model model, FormNuovoUtente form, boolean dataSent, boolean profileComplete,
                                  BindingResult bindingResult, HttpSession httpSession, List<MessaggiUtenteDto> messaggiErrore, String params, Boolean esitoMailAura)
            throws Exception {
    	List<RuoloDTO> tuttiRuoli = ruoloService.ricercaTuttiRuoli(getData());
 		List<RuoloDTO> ruoliNonConfiguratore = ruoloService.ricercaRuoliNonConfiguratore(getData());
 		List<RuoloDTO> ruoliSelezionabili = ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(),getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice());
 		List<RuoloDTO> ruoliSelezionabiliFiltered = filtraRuoliSelezionabili(model, ruoliSelezionabili, tuttiRuoli, getData());
 		List<RuoloDTO> ruoliNonConfSelezionabiliFiltered = filtraRuoliSelezionabili(model, ruoliSelezionabili, ruoliNonConfiguratore, getData());

        model.addAttribute("ruoliSelezionabili", ruoliSelezionabiliFiltered);
        model.addAttribute("ruoliNonConfiguratoreSelezionabili", ruoliNonConfSelezionabiliFiltered);
        model.addAttribute("ruoli", ruoloService.ricercaTuttiRuoliInserimento());
        model.addAttribute("ruoliNonConfiguratore", ruoloService.ricercaTuttiRuoliInserimento());
    	
        model.addAttribute("command", form);
        model.addAttribute("dataFromAura", httpSession.getAttribute(ConstantsWebApp.FROM_AURA));
        model.addAttribute("contratti", tipoContrattoService.ricercaTipoContratto());
        model.addAttribute("sediOperative", collocazioneService.getAllAziende(getData()));
        model.addAttribute("dataSent", dataSent);
        model.addAttribute("profileComplete", profileComplete);
        if (bindingResult != null) model.addAttribute(BindingResult.class.getName() + ".command", bindingResult);

        model.addAttribute("errori", messaggiErrore);

        if(!Utils.isEmpty(params)) {
        	String msg = "";
        	if(esitoMailAura) msg = utenteService.ricercaMessaggiErroreByCod(ConstantsWebApp.MSG_ABILITAZIONE_AURA); 
        	else msg = utenteService.ricercaMessaggiErroreByCod(ConstantsWebApp.MSG_DISABILITAZIONE_AURA);
        	msg = msg.replace("@APP@", params);
        	model.addAttribute("mailaura", msg);
        }

        return ConstantsWebApp.INSERT_UTENTE;
    }

    private String showErrorMessage(Model model, String cf, String errorMessage) throws Exception {
        RicercaUtenteModel ricercaUtenteModel = new RicercaUtenteModel();
        ricercaUtenteModel.setCodiceFiscale(cf);
        return cercaUtente(model, ricercaUtenteModel, errorMessage, null);
    }

    private void getUserFromAura(String cf, HttpSession httpSession, FormNuovoUtente form) throws WebServiceException {
        FormNuovoUtente formFromAura = (FormNuovoUtente) httpSession.getAttribute(ConstantsWebApp.DATI_ANAGRAFICI);
        if (httpSession.getAttribute(ConstantsWebApp.FROM_AURA) == null) {
            formFromAura = utenteService.getUtenteFromAura(cf);

            httpSession.setAttribute(ConstantsWebApp.FROM_AURA, formFromAura != null);
            httpSession.setAttribute(ConstantsWebApp.DATI_ANAGRAFICI, formFromAura);
        }

        if (formFromAura != null) {
            FormNuovoUtente newForm = (FormNuovoUtente) httpSession.getAttribute(ConstantsWebApp.DATI_ANAGRAFICI);
            form.setIdAura(newForm.getIdAura());
            form.setNome(newForm.getNome());
            form.setCognome(newForm.getCognome());
            form.setDataDiNascita(newForm.getDataDiNascita());
            form.setProvinciaDiNascita(newForm.getProvinciaDiNascita());
            form.setComuneDiNascita(newForm.getComuneDiNascita());
            form.setSesso(newForm.getSesso());
        }
    }
    
    private void getUserFromAuraOrMEF(String cf, HttpSession httpSession, FormNuovoUtente form) throws WebServiceException {
        FormNuovoUtente formFromAura = (FormNuovoUtente) httpSession.getAttribute(ConstantsWebApp.DATI_ANAGRAFICI);
        if (formFromAura == null || formFromAura.getCf() != cf) {
            formFromAura = utenteService.getUtenteFromAuraOrMEF(cf, getData());

            httpSession.setAttribute(ConstantsWebApp.FROM_AURA, formFromAura != null);
            httpSession.setAttribute(ConstantsWebApp.DATI_ANAGRAFICI, formFromAura);
        }

        if (formFromAura != null) {
        	if(formFromAura.getIdAura() != null) {        
	        	formFromAura = utenteService.getRuoliCollocazioniFromOpessan(formFromAura, getData());
	        	httpSession.setAttribute(ConstantsWebApp.FROM_AURA, formFromAura != null);
	            httpSession.setAttribute(ConstantsWebApp.DATI_ANAGRAFICI, formFromAura);
        	}
            FormNuovoUtente newForm = (FormNuovoUtente) httpSession.getAttribute(ConstantsWebApp.DATI_ANAGRAFICI);
            form.setCf(newForm.getCf());
            form.setIdAura(newForm.getIdAura());
            form.setNome(newForm.getNome());
            form.setCognome(newForm.getCognome());
            form.setDataDiNascita(newForm.getDataDiNascita());
            form.setProvinciaDiNascita(newForm.getProvinciaDiNascita());
            form.setComuneDiNascita(newForm.getComuneDiNascita());
            form.setSesso(newForm.getSesso());
            form.setEmail(newForm.getEmail());
            form.setTelefono(newForm.getTelefono());
            
            form.setRuoli(newForm.getRuoli());
            form.setRuoliFromOpessan(newForm.getRuoliFromOpessan());
            form.setCollocazioni(newForm.getCollocazioni());
            form.setCollocazioniFromOpessan(newForm.getCollocazioniFromOpessan());
        }
    }

    /* *** MODIFICA UTENTE *** */

    @GetMapping(value = "/modificaUtente")
    public String visualizzaModificaUtente(Model model, @RequestParam String cf,
                                           @RequestParam(defaultValue = "false") boolean saved,
                                           @RequestParam(defaultValue = "false") boolean mail,
                                           @RequestParam(defaultValue = "false") boolean nomail,
                                           @RequestParam(defaultValue = "", required = false) String mailaura,
                                           @RequestParam(defaultValue = "", required = false) String nomailaura) throws Exception {
        if (isFunzioneValida(getData(), FunzionalitaEnum.OPModificaConfigurazioneUtente.getValue())) {
            UtenteDto utenteDto = utenteService.recuperaUtenteByCodiceFiscale(cf);
            if (utenteDto == null) throw new IllegalArgumentException();
            Data data= getData();
            if(saved || mail || nomail || mailaura!=null || nomailaura!=null) {
            	session.getAttribute("uuidSalvaUtente");
            	   }else {
            			session.removeAttribute("uuidSalvaUtente");
                    	session.setAttribute("uuidSalvaUtente", (String) UUID.randomUUID().toString());
            	   }
            FormNuovoUtente form = new FormNuovoUtente();
            mapUtenteDtoToFormNuovoUtente(utenteDto, form);
            utenteService.setLogAuditSOLNew(OperazioneEnum.READ,ConstantsWebApp.KEY_OPER_MODIFICA_DATI_UTENTE, cf, (String) session.getAttribute("uuidSalvaUtente"), null,ConstantsWebApp.MODIFICA_DATI_UTENTE, data);
            return showEditPage(model, form, utenteDto, saved, null, Collections.emptyList(),mail,nomail, mailaura, nomailaura);
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }

    @PostMapping("/modificaUtente/{id}")
    public String modificaUtente(@PathVariable("id") Long id, @Valid @ModelAttribute FormNuovoUtente form,
                                 BindingResult bindingResult, Model model) throws Exception {
    	
    	List<MessaggiUtenteDto> messaggiErrore;
    	
    	
        try {
        	
            if (!Optional.ofNullable(form.getProfiliSol()).map(List::isEmpty).orElse(true)
                    && Optional.ofNullable(form.getRuoli()).map(List::isEmpty).orElse(true)) {
                error("ruoli", "NotEmpty.ruoli", bindingResult, form);
            }

            if (!Optional.ofNullable(form.getProfiliSol()).map(List::isEmpty).orElse(true)
                    && Optional.ofNullable(form.getCollocazioni()).map(List::isEmpty).orElse(true)) {
            	error("collocazioni", "NotEmpty.collocazioni", bindingResult, form);
            }
            
            if(!utenteService.checkAbilitazione(id) && Utils.listIsNullOrEmpty(form.getProfiliSol())) {
            	error("profiliSol", "Abilitazione", bindingResult, form);
            }

            UtenteDto utenteDto = utenteService.recuperaUtenteById(id);
            boolean fromAura = utenteDto.getIdAura() != null;
            boolean fromConfiguratore = "S".equals(utenteDto.getFlagConfiguratore());
            if (fromAura || !fromConfiguratore) {
                form.setIdAura(utenteDto.getIdAura());
                form.setNome(utenteDto.getNome());
                form.setCognome(utenteDto.getCognome());
                if (utenteDto.getDataNascita() != null)
                    form.setDataDiNascita(utenteDto.getDataNascita().toLocalDateTime().format(DATE_FORMATTER));
                form.setProvinciaDiNascita(utenteDto.getProvincia());
                form.setComuneDiNascita(utenteDto.getComuneNascita());
                form.setSesso(utenteDto.getSesso());
            }

            if (!fromConfiguratore && utenteDto.getDataFineValidita() != null) {
                form.setDataFineValidita(utenteDto.getDataFineValidita().toLocalDateTime().format(DATE_FORMATTER));
            }

            try {
                if (bindingResult.hasErrors())
                    return showEditPage(model, form, utenteDto, false, bindingResult, Collections.emptyList(),false,false, null, null);
                utenteService.controllaDataFineValiditaUtenteSol(utenteDto, form.getProfiliSol(), form.getDataFineValidita());
                List<String> listaCollocazioniACuiUtenteAbilitato = convertCollocations(collocazioneService.ricercaCollocazioniByIdUtente(id, getData()));
                List<String> ruoli = convertRoles(ruoloService.ricercaRuoliByIdUtente(id, getData()));
                List<String> listaProfiliSolACuiUtenteAbilitato = serviziOnLineService.ricercaServiziOnLineByIdUtenteAndData(id, getData(), listaCollocazioniACuiUtenteAbilitato, ruoli);
                SalvataggioUtenteModel utenteModificato = utenteService.modificaUtente(id, form, listaProfiliSolACuiUtenteAbilitato, getData(),(String) session.getAttribute("uuidSalvaUtente"));
                
                if(form.isMaildaInviare()) {
                	if (isFunzioneValida(getData(), FunzionalitaEnum.OPInvioMailUtente.getValue())) {
                		
                		String params = "";
                		if(utenteModificato.checkMailAura()) {
                			List<MessaggiUtenteDto> messaggiErroreAura = utenteService.invioMailConfAdAura(form.getCf(), utenteModificato);
                			params = prepareAuraParams(utenteModificato, messaggiErroreAura);
                		}
                		
                		messaggiErrore = utenteService.invioMailAUtenteProfilato(form.getCf(), getData(), utenteModificato);
                		utenteDto = utenteService.recuperaUtenteByCodiceFiscale(form.getCf());
                		FormNuovoUtente formNuovoUtente = new FormNuovoUtente();
                		mapUtenteDtoToFormNuovoUtente(utenteDto, formNuovoUtente);
//                    if (utenteDto.getIdAura() != null)
//                        httpSession.getAttribute(ConstantsWebApp.FROM_AURA);
//                    return tipoPagina.equals("modificaUtente")
//                            ? showEditPage(model, formNuovoUtente, utenteDto, false, null, messaggiErrore)
//                            : showInsertPage(model, formNuovoUtente, true, true, null, httpSession, messaggiErrore);
                		
                		
                		if(!messaggiErrore.get(0).getCodice().equalsIgnoreCase(ConstantsWebApp.OPERAZIONE_EFFETTUATA)) {
                			return "redirect:/modificaUtente?cf=" + form.getCf() + "&mail=true" + params;
                		} else {
                			return "redirect:/modificaUtente?cf=" + form.getCf() + "&saved=true" + params; 
                		}
                	}
                	
                }
                
                if(!form.isMaildaInviare()) {
                	
                	return "redirect:/modificaUtente?cf=" + form.getCf() + "&nomail=true";
                }else {
                	
                	return "redirect:/modificaUtente?cf=" + form.getCf() + "&saved=true";
                }
                
                
            } catch (WebServiceException wse) {
                String completeMessage = wse.getMessage();
                String field = completeMessage.substring(0, completeMessage.lastIndexOf('-') - 1);
                String message = completeMessage.substring(completeMessage.lastIndexOf('-') + 2);
                error(field, message, bindingResult, form);
                return showEditPage(model, form, utenteDto, false, bindingResult, Collections.emptyList(),false,false, null, null);
            }
        } finally {
            //setLogAuditSOL(OperazioneEnum.UPDATE, "MOD_UTE");
        }
    }

	private String prepareAuraParams(SalvataggioUtenteModel utenteModificato,
			List<MessaggiUtenteDto> messaggiErroreAura) {
		String params;
		List<String> abi = utenteModificato.getAbilitazioniAura().stream().map(a -> a.getApplicazioneDto().getCodice()).distinct().collect(Collectors.toList());
		List<String> disabi = utenteModificato.getDisabilitazioniAura().stream().map(a -> a.getApplicazioneDto().getCodice()).distinct().collect(Collectors.toList());
		List<String> mods = utenteModificato.getModificheDataFineValAura().stream().map(a -> a.getApplicazioneDto().getCodice()).distinct().collect(Collectors.toList());
		
		params = messaggiErroreAura.stream().anyMatch(m -> m.getCodice().equalsIgnoreCase(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA))
				? "&nomailaura=" : "&mailaura=";
		for(String s : abi) params += "1"+s+",";
		for(String s : disabi) params += "0"+s+",";
		for(String s : mods) params += "2"+s+",";
		return params.substring(0, params.length()-1);
	}

    @PostMapping("/modificaUtente/{id}/disabilitaConfig")
    public String disabilitaTutteConfigurazioni(@PathVariable Long id, Model model) {
        if (isFunzioneValida(getData(), FunzionalitaEnum.OPModificaConfigurazioneUtente.getValue())) {
            // recupero la lista dei SOL a cui l'utente con identificativo id è abilitato
            List<String> listaCollocazioniACuiUtenteAbilitato = convertCollocations(collocazioneService.ricercaCollocazioniByIdUtente(id, getData()));
            List<String> ruoli = convertRoles(ruoloService.ricercaRuoliByIdUtente(id, getData()));
            List<String> listaProfiliSolACuiUtenteAbilitato = serviziOnLineService.ricercaServiziOnLineByIdUtenteAndData(id, getData(), listaCollocazioniACuiUtenteAbilitato, ruoli);

            // disabilito tutte le configurazioni SOL a cui l'utente è abilitato
            String codiceFiscale = utenteService.disabilitaTutteConfigurazioni(id, listaProfiliSolACuiUtenteAbilitato, getData(), (String) session.getAttribute("uuidSalvaUtente"));
        	
            return "redirect:/modificaUtente?cf=" + codiceFiscale + "&saved=true";
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }

    private String showEditPage(Model model, FormNuovoUtente form, UtenteDto utenteDto, boolean saved,
                                BindingResult bindingResult, List<MessaggiUtenteDto> messaggiErrore,boolean mail,boolean nomail, String mailaura, String nomailaura) throws Exception {
        boolean fromConfiguratore = "S".equals(utenteDto.getFlagConfiguratore());
        boolean profileComplete = !Optional.ofNullable(form.getRuoli()).map(List::isEmpty).orElse(true)
                && !Optional.ofNullable(form.getCollocazioni()).map(List::isEmpty).orElse(true)
                && !Optional.ofNullable(form.getProfiliSol()).map(List::isEmpty).orElse(true);
        
        List<RuoloDTO> tuttiRuoli = ruoloService.ricercaTuttiRuoli(getData());
		List<RuoloDTO> ruoliNonConfiguratore = ruoloService.ricercaRuoliNonConfiguratore(getData());
		List<RuoloDTO> ruoliSelezionabili = ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(),getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice());
		List<RuoloDTO> ruoliSelezionabiliFiltered = filtraRuoliSelezionabili(model, ruoliSelezionabili, tuttiRuoli, getData());
		List<RuoloDTO> ruoliNonConfSelezionabiliFiltered = filtraRuoliSelezionabili(model, ruoliSelezionabili, ruoliNonConfiguratore, getData());
		List<RuoloDTO> ruoli = filtraRuoliDefault(form, tuttiRuoli, ruoliSelezionabiliFiltered); 
		List<RuoloDTO> ruoliNonConf = filtraRuoliDefault(form, ruoliNonConfiguratore, ruoliNonConfSelezionabiliFiltered);  
		List<RuoloDTO> mergeRuoli = Stream.concat(ruoli.stream(), ruoliNonConf.stream()).collect(Collectors.toList());
		
		
        model.addAttribute("ruoliSelezionabili", ruoliSelezionabiliFiltered);
        model.addAttribute("ruoliNonConfiguratoreSelezionabili", ruoliNonConfSelezionabiliFiltered);
        model.addAttribute("ruoli", ruoli);
        model.addAttribute("ruoliNonConfiguratore", mergeRuoli);
        model.addAttribute("contratti", tipoContrattoService.ricercaTipoContratto());
        model.addAttribute("sediOperative", collocazioneService.getAllAziende(getData()));
        model.addAttribute("profileComplete", profileComplete);
        model.addAttribute("fromConfiguratore", fromConfiguratore);
        model.addAttribute("dataFromAura", utenteDto.getIdAura() != null);
        model.addAttribute("saved", saved);
        model.addAttribute("mail", mail);
        model.addAttribute("nomail", nomail);
        model.addAttribute("mailaura", mailaura);
        model.addAttribute("nomailaura", nomailaura);
        if (bindingResult != null) model.addAttribute(BindingResult.class.getName() + ".command", bindingResult);
//        List<CollocazioneDTO> collocazioniSol=collocazioneService.getCollocazioniSolByCodiceOrDescrizione(form.getCollocazioni().stream().map(f->{
//        	if(f.endsWith("ro"))
//        		 return f.substring(0,f.length()-2);
//        	return f;        		
//        }).map(Long::parseLong).collect(Collectors.toList()), getData());        			
       model.addAttribute("collocazioniSol",collocazioneService.getAllAziende(getData()));
        model.addAttribute("command", form);
        model.addAttribute("ruoliSol", ruoloService.ricercaRuoliByIdUtente(utenteDto.getId(), getData()));

        model.addAttribute("errori", messaggiErrore);

        return ConstantsWebApp.EDIT_UTENTE;
    }

    private List<RuoloDTO> filtraRuoliDefault(FormNuovoUtente form, List<RuoloDTO> ruoli, List<RuoloDTO> toAdd) {
    	List<String> defaultRoles = new ArrayList<String>();
		form.getRuoli().forEach(r -> defaultRoles.add(r.endsWith("ro") ? r.substring(0, r.length()-2) : r));

		List<RuoloDTO> ruoliFiltered = ruoli.stream().filter(r -> 
				defaultRoles.stream().map(Long::valueOf).collect(Collectors.toList())
			   .contains(r.getId())).collect(Collectors.toList());
		
		Set<Long> idSet = ruoliFiltered.stream().map(RuoloDTO::getId).collect(Collectors.toSet());
		toAdd.forEach(r -> {if(!idSet.contains(r.getId())) ruoliFiltered.add(r);});

		return ruoliFiltered;
    }
    
	private List<RuoloDTO> filtraRuoliSelezionabili(Model model, List<RuoloDTO> ruoliSelezionabili, List<RuoloDTO> ruoli, Data data) {
		boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
		List<RuoloDTO> rl = ruoli.stream().collect(Collectors.toList());
		if(!superUser && !ruoliSelezionabili.isEmpty()) {
			Set<Long> idSet = ruoliSelezionabili.stream().map(RuoloDTO::getId).collect(Collectors.toSet());
			rl = rl.stream().filter(r -> idSet.contains(r.getId())).collect(Collectors.toList());
		}
		return rl;
	}

    /* *** INVIO EMAIL A UTENTE PROFILATO *** */

    @PostMapping("/{tipoPagina}/invioEmail/{cf}")
    public String invioMailAUtenteProfilato(@PathVariable("tipoPagina") String tipoPagina, @PathVariable("cf") String cf,
                                            Model model, HttpSession httpSession) throws Exception {
        List<MessaggiUtenteDto> messaggiErrore;
        
        try {
            if (isFunzioneValida(getData(), FunzionalitaEnum.OPInvioMailUtente.getValue())) {
                messaggiErrore = utenteService.invioMailAUtenteProfilato(cf, getData(), new SalvataggioUtenteModel());
                UtenteDto utenteDto = utenteService.recuperaUtenteByCodiceFiscale(cf);
                FormNuovoUtente formNuovoUtente = new FormNuovoUtente();
                mapUtenteDtoToFormNuovoUtente(utenteDto, formNuovoUtente);
                if (utenteDto.getIdAura() != null)
                    httpSession.getAttribute(ConstantsWebApp.FROM_AURA);
                return tipoPagina.equals("modificaUtente")
                        ? showEditPage(model, formNuovoUtente, utenteDto, false, null, messaggiErrore,false,false, null, null)
                        : showInsertPage(model, formNuovoUtente, true, true, null, httpSession, messaggiErrore, null, null);
            } else {
                return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
            }
        } catch (Exception e) {
            return ConstantsWebApp.ERROR;
        } finally {
          //  setLogAuditSOL(OperazioneEnum.INVIO_MAIL, "INV_MAIL");
        	// Scrittura log Audit
            try {
            	utenteService.setLogAuditSOLNew(OperazioneEnum.INVIO_MAIL, ConstantsWebApp.KEY_OPER_INVIO_MAIL, cf, 
    					UUID.randomUUID().toString(), null, ConstantsWebApp.INVIO_MAIL, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
    }

    /* *** RICHIESTA CREDENZIALI RUPAR *** */

    @PostMapping("/{tipoPagina}/richiestaCredenzialiRupar/{cf}")
    public String richiestaCredenzialiRupar(@PathVariable("tipoPagina") String tipoPagina, @PathVariable("cf") String cf,  @RequestParam("profileComplete") Boolean profileComplete, Model model, HttpSession httpSession) throws Exception {
        List<MessaggiUtenteDto> messaggiErrore;
        try {
            if (isFunzioneValida(getData(), FunzionalitaEnum.OPRichiestaCredenzialiRUPAR.getValue())) {
            	UtenteDto utenteDto = utenteService.recuperaUtenteByCodiceFiscale(cf);
            	if(utenteDto.getTipoContrattoDto() == null || StringUtils.isBlank(utenteDto.getNumeroTelefono()) ){
            		messaggiErrore = new ArrayList<MessaggiUtenteDto>();
            		if(utenteDto.getTipoContrattoDto() == null){
            			messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.PARAMETRO_NON_PRESENTE, "Tipo di contratto"));
            		}
            		if(StringUtils.isBlank(utenteDto.getNumeroTelefono())){
            			messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.PARAMETRO_NON_PRESENTE, "Numero di telefono"));
            		}
            		
            	}else{
                    messaggiErrore = utenteService.richiestaCredenzialiRupar(cf, getData());
            	}
                FormNuovoUtente formNuovoUtente = new FormNuovoUtente();
                mapUtenteDtoToFormNuovoUtente(utenteDto, formNuovoUtente);

                if (utenteDto.getIdAura() != null)
                    httpSession.getAttribute(ConstantsWebApp.FROM_AURA);

                return tipoPagina.equals("modificaUtente")
                        ? showEditPage(model, formNuovoUtente, utenteDto, false, null, messaggiErrore,false,false, null, null)
                		: showInsertPage(model, formNuovoUtente, true, profileComplete, null, httpSession, messaggiErrore, null, null);
            } else {
                return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
            }
        } catch (Exception e) {
            return ConstantsWebApp.ERROR;
        } finally {
         //   setLogAuditSOL(OperazioneEnum.INSERT, "INS_RIC_CRE");
        }
    }


   
    /* *** AJAX *** */

    @GetMapping(value = "/ajax/collocazione", produces = "application/json")
    @ResponseBody
    public String cercaCollocazione(@RequestParam String code,
                                    @RequestParam(required = false) String codeTerm,
                                    @RequestParam(required = false) String descTerm) throws IOException {
        return new ObjectMapper().writeValueAsString(collocazioneService.getCollocazioneByCodiceOrDescrizione(code, codeTerm, descTerm, getData()));
    }

    @PostMapping(value = "/ajax/collocazione/id", produces = "application/json")
    @ResponseBody
    public String cercaCollocazioneId(@RequestParam String id) throws IOException {
        List<Long> list = Arrays.stream(id.split(","))
                .map(String::trim)
                .map(e -> e.endsWith("ro") ? e.substring(0, e.length() - 2) : e)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        return new ObjectMapper().writeValueAsString(collocazioneService.getCollocazioneById(list, getData()));
    }

    @PostMapping(value = "/ajax/sol", produces = "application/json")
    @ResponseBody
    public String getSol(@RequestParam List<Long> collocazioni) throws IOException {
    	return new ObjectMapper().writeValueAsString(serviziOnLineService.recuperaServiziOnLineSelezionabili(collocazioni, getData()));
    }
    
    @PostMapping(value = "/ajax/collocazioniSol", produces = "application/json")
    @ResponseBody
    public String cercaCollocazioniSol(@RequestParam List<Long> collocazioni,Model model ) throws IOException {
    	List<CollocazioneDTO> collocazioniSolByCodiceOrDescrizione = collocazioneService.getCollocazioneById(collocazioni, getData());
    	model.addAttribute("collocazioniSol",collocazioniSolByCodiceOrDescrizione);
        return new ObjectMapper().writeValueAsString(collocazioniSolByCodiceOrDescrizione);
    }
    
    @PostMapping(value = "/ajax/solSelezionabili", produces = "application/json")
    @ResponseBody
    public String getSolSelezionabili(@RequestParam Long collocazione,@RequestParam Long ruolo) throws IOException {
    	return new ObjectMapper().writeValueAsString(serviziOnLineService.recuperaSolSelezionabili(collocazione,ruolo,getData()));
    }

    @PostMapping(value = "/ajax/ruoliCompatibili", produces = "application/json")
    @ResponseBody
    public String getRuoliCompatibili(@RequestParam String id) throws IOException {
    	return new ObjectMapper().writeValueAsString(ruoloService.getRuoliCompatibili(id));
    }
    
    @PostMapping(value = "/ajax/idSol", produces = "application/json")
    @ResponseBody
    public String getIdSol(@RequestParam Long id) throws IOException {
    	return new ObjectMapper().writeValueAsString(serviziOnLineService.getSolId(id));
    }
    
    @PostMapping(value = "/ajax/profileSolCongif", produces = "application/json")
    @ResponseBody
    public String getProfileSolConfig() throws IOException {
    	return new ObjectMapper().writeValueAsString(serviziOnLineService.getProfileTitolareoDelegatoSolConfig());
    }
    
    @PostMapping(value = "/ajax/solSelezionabiliPerAbilitazione", produces = "application/json")
    @ResponseBody
    public String getSolSelezionabiliAbilitazione(@RequestParam Long collocazione) throws IOException {
    	return new ObjectMapper().writeValueAsString(serviziOnLineService.recuperaSolSelezionabiliAbilitazione(collocazione,getData()));
    }
    
    @PostMapping(value = "/ajax/messaggioAura", produces = "application/json")
    @ResponseBody
    public String getMessaggioAura(@RequestParam String msg, @RequestParam String app, @RequestParam Boolean errore) throws IOException {
    	return new ObjectMapper().writeValueAsString(utenteService.getMessaggioAura(msg,app,errore));
    }
    
    @GetMapping(value = "/ajax/checkHasSolConfig", produces = "application/json")
    @ResponseBody
    public String checkHasSolConfig(@RequestParam String cf, @RequestParam Long idCollocazione) throws IOException {
    	return new ObjectMapper().writeValueAsString(utenteService.checkIsAbilitazioneConfiguratorePresente(idCollocazione, cf));
    }

    /* *** UTILITIES *** */

    private void mapUtenteDtoToFormNuovoUtente(UtenteDto utenteDto, FormNuovoUtente formNuovoUtente) {
    	
    	  	
    	formNuovoUtente.setId(utenteDto.getId());
        formNuovoUtente.setIdAura(utenteDto.getIdAura());
        formNuovoUtente.setNome(utenteDto.getNome());
        formNuovoUtente.setCognome(utenteDto.getCognome());
        formNuovoUtente.setDataDiNascita(Utils.timestampToString(utenteDto.getDataNascita()));
        formNuovoUtente.setCf(utenteDto.getCodiceFiscale());
        formNuovoUtente.setProvinciaDiNascita(utenteDto.getProvincia());
        formNuovoUtente.setComuneDiNascita(utenteDto.getComuneNascita());
        formNuovoUtente.setSesso(utenteDto.getSesso());
        formNuovoUtente.setEmail(utenteDto.getIndirizzoMail());
        formNuovoUtente.setTelefono(utenteDto.getNumeroTelefono());
        formNuovoUtente.setStato(checkStato(utenteDto.getDataInizioValidita(), utenteDto.getDataFineValidita()));
        formNuovoUtente.setDataFineValidita(Utils.timestampToString(utenteDto.getDataFineValidita()));
        formNuovoUtente.setContratto(utenteDto.getTipoContrattoDto() != null ? utenteDto.getTipoContrattoDto().getId() : null);
        formNuovoUtente.setRuoli(convertRoles(ruoloService.ricercaRuoliByIdUtente(utenteDto.getId(), getData())));
        formNuovoUtente.setCollocazioni(convertCollocations(collocazioneService.ricercaCollocazioniByIdUtente(utenteDto.getId(), getData())));
        formNuovoUtente.setProfiliSol(serviziOnLineService.ricercaServiziOnLineByIdUtenteAndData(utenteDto.getId(), getData(), formNuovoUtente.getCollocazioni(), formNuovoUtente.getRuoli()));
    }

    private static boolean checkStato(Timestamp dataInizioValidita, Timestamp dataFineValidita) {
        Timestamp dataCorrente = new Timestamp(System.currentTimeMillis());

        if (dataInizioValidita == null && dataFineValidita == null) {
            return true;
        } else if (dataInizioValidita != null && dataFineValidita == null) {
            return dataCorrente.after(dataInizioValidita);
        } else if (dataInizioValidita == null) {
            return dataCorrente.before(dataFineValidita);
        } else {
            return dataCorrente.after(dataInizioValidita) && dataCorrente.before(dataFineValidita);
        }
    }

    private static List<String> convertRoles(List<RuoloDTO> roles) {
        return roles.stream().map(r -> r.isModificabile() ? String.valueOf(r.getId()) : r.getId() + "ro")
                .collect(Collectors.toList());
    }

    private static List<String> convertCollocations(List<CollocazioneDTO> roles) {
        return roles.stream().map(c -> c.isModificabile() ? String.valueOf(c.getId()) : c.getId() + "ro")
                .collect(Collectors.toList());
    }

    private boolean isFunzioneValida(Data data, String tipoFunzione) {
        return data.getUtente() != null && !Utils.listIsNullOrEmpty(data.getUtente().getFunzionalitaAbilitate())
                && data.getUtente().getFunzionalitaAbilitate().stream().anyMatch(f -> f.equals(tipoFunzione));
    }
}