/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FaqRuparDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.business.service.RuoloService;
import it.csi.solconfig.configuratoreweb.business.service.UtenteService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.AbilitazioneMassivaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RisultatiRicercaAbilitazioneMassivaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Controller
@Scope("prototype")
public class GestioneAbilitazioniController extends BaseController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private CollocazioneService collocazioneService;


    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu");

   
    @PostMapping("/cercaUtentiAbilitazioneMassiva")
    public String cercaUtentiAbilitazioneMassiva(Model model, @ModelAttribute("abilitazione") AbilitazioneMassivaModel abilitazioneMassivaModel, String error, HttpSession httpSession) throws Exception {
    	if (isFunzioneValida(getData(), FunzionalitaEnum.OPRicercaAbilitazioneMassiva.getValue())) {
            List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
            try {
                if (error == null) {
                        	
                    PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> paginaDTO = utenteService.ricercaUtentiAbilitazioneMassiva(abilitazioneMassivaModel, getData());
                    utenteService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_ABILITAZIONE_MASSIVA_RICERCA, null, 
        	        		UUID.randomUUID().toString(), null, ConstantsWebApp.ABILITAZIONE_MASSIVA_RICERCA, getData());
                    if (paginaDTO.getElementiTotali() == 0) {
                    	
                    	messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.NESSUN_UTENTE_TROVATO));
                    }
                    model.addAttribute("paginaUtenti", paginaDTO);
                    //model.addAttribute("abilitazione", abilitazioneMassivaModel);                   

                } else {
                    
                    messaggiErrore.add(utenteService.aggiungiErrori(error));
                }
            } catch (Exception e) {
                log.error("ERROR: cercaUtente - ", e);
                messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
                return ConstantsWebApp.ERROR;
            } finally {
            	model.addAttribute("abilitazione", abilitazioneMassivaModel);
            	model.addAttribute("errori", messaggiErrore);
            	model.addAttribute("aziende", collocazioneService.getAllAziende(getData()));
                model.addAttribute("ruoli", filtraRuoliSelezionabili(
    					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
    					ruoloService.ricercaTuttiRuoli(getData()), getData()));
                List<String> stati= new ArrayList<String>();
    			stati.add(ConstantsWebApp.INELAB);
    			stati.add(ConstantsWebApp.DAELAB);
                model.addAttribute("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
            }

            return ConstantsWebApp.ABILITAZIONE_MASSIVA;
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }
    
    @RequestMapping(value = "/annullaRicercaAbilitazione", method = RequestMethod.POST)
	public ModelAndView annullaRicerca(ModelAndView mav) {

		try {
			Data data = getData();
			mav.setViewName(ConstantsWebApp.ABILITAZIONE_MASSIVA);
			mav.addObject("abilitazione", new AbilitazioneMassivaModel());
			mav.addObject("aziende", collocazioneService.getAllAziende(getData()));
			mav.addObject("ruoli", filtraRuoliSelezionabili(
					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
					ruoloService.ricercaTuttiRuoli(getData()), getData()));
			List<String> stati= new ArrayList<String>();
   			stati.add(ConstantsWebApp.INELAB);
   			stati.add(ConstantsWebApp.DAELAB);
   			mav.addObject("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
			updateData(data);
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		return mav;
	}
    
    @PostMapping("/cercaUtentiDisabilitazioneMassiva")
    public String cercaUtentiDisabilitazioneMassiva(Model model, @ModelAttribute("abilitazione") AbilitazioneMassivaModel abilitazioneMassivaModel, String error, HttpSession httpSession) throws Exception {
    	if (isFunzioneValida(getData(), FunzionalitaEnum.OPRicercaDisabilitazioneMassiva.getValue())) {
            List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
            try {
                if (error == null) {
                        	
                    PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> paginaDTO = utenteService.ricercaUtentiDisabilitazioneMassiva(abilitazioneMassivaModel, getData());
                    utenteService.setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_DISABILITAZIONE_MASSIVA_RICERCA, null, 
        	        		UUID.randomUUID().toString(), null, ConstantsWebApp.DISABILITAZIONE_MASSIVA_RICERCA, getData());
                    if (paginaDTO.getElementiTotali() == 0) {
                    	
                    	messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.NESSUN_UTENTE_TROVATO));
                    } 
                    model.addAttribute("paginaUtenti", paginaDTO);
                    //model.addAttribute("abilitazione", abilitazioneMassivaModel);

                } else {
                    
                    messaggiErrore.add(utenteService.aggiungiErrori(error));
                }
            } catch (Exception e) {
                log.error("ERROR: cercaUtente - ", e);
                messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
                return ConstantsWebApp.ERROR;
            } finally {
            	model.addAttribute("abilitazione", abilitazioneMassivaModel);
            	model.addAttribute("errori", messaggiErrore);
            	model.addAttribute("aziende", collocazioneService.getAllAziende(getData()));
                model.addAttribute("ruoli", filtraRuoliSelezionabili(
    					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
    					ruoloService.ricercaTuttiRuoli(getData()), getData()));
                List<String> stati= new ArrayList<String>();
    			stati.add(ConstantsWebApp.INELAB);
    			stati.add(ConstantsWebApp.DAELAB);
                model.addAttribute("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
                
            }

            return ConstantsWebApp.DISABILITAZIONE_MASSIVA;
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }
    
    @RequestMapping(value = "/annullaRicercaDisabilitazione", method = RequestMethod.POST)
   	public ModelAndView annullaRicercaDisabilitazione(ModelAndView mav) {

   		try {
   			Data data = getData();
   			mav.setViewName(ConstantsWebApp.DISABILITAZIONE_MASSIVA);
   			mav.addObject("abilitazione", new AbilitazioneMassivaModel());
   			mav.addObject("aziende", collocazioneService.getAllAziende(getData()));
   			mav.addObject("ruoli", filtraRuoliSelezionabili(
   					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
   					ruoloService.ricercaTuttiRuoli(getData()), getData()));
   			List<String> stati= new ArrayList<String>();
			stati.add(ConstantsWebApp.INELAB);
			stati.add(ConstantsWebApp.DAELAB);
			mav.addObject("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
   			updateData(data);
   		} catch (Exception e) {
   			log.error("ERROR: ruoli - ", e);
   			mav.setViewName(ConstantsWebApp.ERROR);
   		}
   		return mav;
   	}
    
    @PostMapping("/abilitazioneMassiva")
    public String abilitazioneMassiva(Model model, @ModelAttribute("abilitazione") AbilitazioneMassivaModel abilitazioneMassivaModel, String error, HttpSession httpSession) throws Exception {
    	if (isFunzioneValida(getData(), FunzionalitaEnum.OPAbilitazioneMassiva.getValue())) {
            List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
            try {
                if (error == null) {
                        	
                   utenteService.abilitazioneMassiva(abilitazioneMassivaModel, data);
                   messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_PRESA_IN_CARICO));

                } else {
                    
                    messaggiErrore.add(utenteService.aggiungiErrori(error));
                }
            } catch (Exception e) {
                log.error("ERROR: cercaUtente - ", e);
                messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
                return ConstantsWebApp.ERROR;
            } finally {
            	model.addAttribute("abilitazione", new AbilitazioneMassivaModel());
            	model.addAttribute("errori", messaggiErrore);
            	model.addAttribute("aziende", collocazioneService.getAllAziende(getData()));
                model.addAttribute("ruoli", filtraRuoliSelezionabili(
    					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
    					ruoloService.ricercaTuttiRuoli(getData()), getData()));
                List<String> stati= new ArrayList<String>();
    			stati.add(ConstantsWebApp.INELAB);
    			stati.add(ConstantsWebApp.DAELAB);
                model.addAttribute("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
            }

            return ConstantsWebApp.ABILITAZIONE_MASSIVA;
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }
    
    @PostMapping("/disabilitazioneMassiva")
    public String disabilitazioneMassiva(Model model, @ModelAttribute("abilitazione") AbilitazioneMassivaModel abilitazioneMassivaModel, String error, HttpSession httpSession) throws Exception {
    	if (isFunzioneValida(getData(), FunzionalitaEnum.OPDisabilitazioneMassiva.getValue())) {
            List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
            try {
                if (error == null) {
                        	
                   utenteService.abilitazioneMassiva(abilitazioneMassivaModel, data);
                   messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.OPERAZIONE_PRESA_IN_CARICO));

                } else {
                    
                    messaggiErrore.add(utenteService.aggiungiErrori(error));
                }
            } catch (Exception e) {
                log.error("ERROR: cercaUtente - ", e);
                messaggiErrore.add(utenteService.aggiungiErrori(ConstantsWebApp.ERRORE_INTERNO));
                return ConstantsWebApp.ERROR;
            } finally {
            	model.addAttribute("abilitazione", new AbilitazioneMassivaModel());
            	model.addAttribute("errori", messaggiErrore);
            	model.addAttribute("aziende", collocazioneService.getAllAziende(getData()));
                model.addAttribute("ruoli", filtraRuoliSelezionabili(
    					ruoloService.getRuoliSelezionabili(getData().getUtente().getCodiceFiscale(), getData().getUtente().getCollocazione().getColCodice(), getData().getUtente().getRuolo().getCodice()), 
    					ruoloService.ricercaTuttiRuoli(getData()), getData()));
                List<String> stati= new ArrayList<String>();
    			stati.add(ConstantsWebApp.INELAB);
    			stati.add(ConstantsWebApp.DAELAB);
                model.addAttribute("checkElaborazione", utenteService.checkBatchInElaborazione(getData(), stati));
            }

            return ConstantsWebApp.DISABILITAZIONE_MASSIVA;
        } else {
            return ConstantsWebApp.REDIRECT_NONAUTORIZZATO;
        }
    }
    
    
    @RequestMapping(value = "/downloadCsvAbilitati", method = RequestMethod.GET)
	public void downloadCsvAbilitati(ModelAndView mav,HttpServletResponse response, @RequestParam("id") String id,@RequestParam("abilita") boolean abilitazione) {
		
		

		try {
			byte[] csv=utenteService.downloadCsv(Long.valueOf(id), true);
			File reportOut = null;
			//String fileName =  "Utenti_Abilitati.csv";
			reportOut = File.createTempFile("Utenti_abilitati_Richiesta_"+id,".csv");
			FileUtils.writeByteArrayToFile(reportOut, csv);
			FileUtils.copyFile(reportOut, response.getOutputStream());
			response.setContentType("text/csv");
			if(abilitazione) {
				response.setHeader("Content-Disposition", "attachment;filename=\"Utenti_abilitati_Richiesta_"+id+".csv\"");
			}else {
				response.setHeader("Content-Disposition", "attachment;filename=\"Utenti_disabilitati_Richiesta_"+id+".csv\"");
				
			}
			response.flushBuffer();
			
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		
		
	}
    
    @RequestMapping(value = "/downloadCsvNonAbilitati", method = RequestMethod.GET)
	public void downloadCsvNonAbilitati(ModelAndView mav,HttpServletResponse response, @RequestParam("id") String id,@RequestParam("abilita") boolean abilitazione) {
		
		

		try {
			byte[] csv=utenteService.downloadCsv(Long.valueOf(id), false);
			File reportOut = null;
			//String fileName =  "Utenti_Abilitati.csv";
			reportOut = File.createTempFile("Utenti_Non_abilitati_Richiesta_"+id,".csv");
			FileUtils.writeByteArrayToFile(reportOut, csv);
			FileUtils.copyFile(reportOut, response.getOutputStream());
			response.setContentType("text/csv");
			if(abilitazione) {
				response.setHeader("Content-Disposition", "attachment;filename=\"Utenti_Non_abilitati_Richiesta_"+id+".csv\"");
				
			}else {
				response.setHeader("Content-Disposition", "attachment;filename=\"Utenti_Non_disabilitati_Richiesta_"+id+".csv\"");

			}
			response.flushBuffer();
			
		} catch (Exception e) {
			log.error("ERROR: ruoli - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}
		
		
	}
    
    //AJAX
    
    @PostMapping(value = "/ajax/storico", produces = "application/json")
    @ResponseBody
    public String findStorico(@RequestParam boolean abilitazione) throws IOException {
    	    return new ObjectMapper().writeValueAsString(utenteService.findStoricoByUtente(getData(), abilitazione));
    }
    
    

    /* *** UTILITIES *** */
    
    private List<RuoloDTO> filtraRuoliSelezionabili(List<RuoloDTO> ruoliSelezionabili, List<RuoloDTO> ruoli, Data data) {
		boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
		List<RuoloDTO> rl = ruoli.stream().collect(Collectors.toList());
		if(!superUser && !ruoliSelezionabili.isEmpty()) {
			Set<Long> idSet = ruoliSelezionabili.stream().map(RuoloDTO::getId).collect(Collectors.toSet());
			rl = rl.stream().filter(r -> idSet.contains(r.getId())).collect(Collectors.toList());
		}
		return rl;
	}
    
    private boolean isFunzioneValida(Data data, String tipoFunzione) {
        return data.getUtente() != null && !Utils.listIsNullOrEmpty(data.getUtente().getFunzionalitaAbilitate())
                && data.getUtente().getFunzionalitaAbilitate().stream().anyMatch(f -> f.equals(tipoFunzione));
    }
    
}