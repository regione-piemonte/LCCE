/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.service.ServiziOnLineService;
import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.CollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.*;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiziOnLineImpl extends BaseServiceImpl implements ServiziOnLineService {

    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    @Autowired
    private ApplicazioneLowDao applicazioneLowDao;

    @Autowired
    private CollocazioneLowDao collocazioneLowDao;

    @Autowired
    private ApplicazioneCollocazioneLowDao applicazioneCollocazioneLowDao;

    @Autowired
    private FunzionalitaLowDao funzionalitaLowDao;
    
    @Autowired
    private RuoloLowDao ruoloLowDao;

    @Override
    public List<ServizioOnLineDTO> recuperaServiziOnLineSelezionabili(List<Long> idCollocazioni, Data data) {
        if (idCollocazioni == null || idCollocazioni.isEmpty())
            return Collections.emptyList();

        boolean isOPListaSOLconConfiguratore = checkSOLConfiguratore(data);

        boolean isOPListaProfiliCompleta = checkListaProfiliCompleta(data);
        
        boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
        
        List<Long> idAziendeSanitarie = null;
        
        if(superUser) {
        	
        	idAziendeSanitarie = collocazioneLowDao.findAziendeSanitarie(idCollocazioni);
        	
        }else {
        	
        	idAziendeSanitarie = collocazioneLowDao.findAziendeSanitarieConVisiblita(idCollocazioni,data.getUtente().getCodiceFiscale());
        }


        if (idAziendeSanitarie == null || idAziendeSanitarie.isEmpty())
            return Collections.emptyList();

        //Acquisisco i SOL
        List<ServizioOnLineDTO> serviziOnLineDTO = applicazioneLowDao.findServiziByIdCollocazioni(idAziendeSanitarie, isOPListaSOLconConfiguratore);

        if (serviziOnLineDTO != null && !serviziOnLineDTO.isEmpty()) {
            serviziOnLineDTO.forEach(servizoOnLineDTO -> {

                //Acquisisco le collocazioni
                List<Long> allCollocazioni = applicazioneCollocazioneLowDao.findIdCollocazioniByIdApplicazione(servizoOnLineDTO.getId());
                allCollocazioni.addAll(collocazioneLowDao.findSediSanitarie(allCollocazioni));

                if (!allCollocazioni.isEmpty()) {
                    List<Long> collocazioni = new ArrayList<>();
                    allCollocazioni.forEach(id -> {
                        if (idCollocazioni.contains(id) && !collocazioni.contains(id)) {
                            collocazioni.add(id);
                        }
                    });
                    servizoOnLineDTO.setCollocazioni(collocazioni);
                }

                //Acquisisco i profili
                List<ProfiloDTO> profili = funzionalitaLowDao.findProfiliByIdApplicazione(servizoOnLineDTO.getId(), isOPListaProfiliCompleta, superUser);
                if (profili != null && !profili.isEmpty()) {
                    profili.forEach(profiloDTO -> {

                        //Acquisisco le funzionalità
                        List<FunzionalitaTreeDTO> idFunzionalita = funzionalitaLowDao.findAllFunzionalitaByIdProfilo(profiloDTO.getId());

                        //Assegno le informazioni alle funzionalità
                        if (idFunzionalita != null && !idFunzionalita.isEmpty()) {
                            List<FunzionalitaDTO> funzionalitaList = new ArrayList<>();
                            idFunzionalita.forEach(funzionalitaTreeDTO -> {
                                FunzionalitaDTO funzionalitaDTO = funzionalitaLowDao.findFunzionalitaById(funzionalitaTreeDTO.getId());
                                if (funzionalitaDTO != null) {
                                    funzionalitaDTO.setParent(funzionalitaLowDao.findFunzionalitaById(funzionalitaTreeDTO.getParent()));
                                    funzionalitaList.add(funzionalitaDTO);
                                } else {
                                    log.error("ERROR (in recuperaServiziOnLineSelezionabili): Funzionalita non presente per id " + funzionalitaTreeDTO.getId() );
                                }
                            });
                            profiloDTO.setFunzionalita(funzionalitaList);
                        }
                        
                        //Acquisisco i ruoli
                        List<RuoloDTO> ruoli = ruoloLowDao.findRuoliByIdProfilo(profiloDTO.getId());
                        profiloDTO.setRuoli(ruoli);
                        
                    });
                }
                servizoOnLineDTO.setProfili(profili);
            });
        }
        return serviziOnLineDTO;
    }

    @Override
    public List<String> ricercaServiziOnLineByIdUtenteAndData(Long idUtente, Data data, List<String> collocazioni, List<String> ruoli) {
        boolean isOPListaSOLconConfiguratore = checkSOLConfiguratore(data);

        List<String> listaSOL = applicazioneLowDao.findServiziByIdUtente(idUtente, isOPListaSOLconConfiguratore);
        String ruoloTitConfig=applicazioneLowDao.getProfiloTitolareSolConfig().toString();

        List<String> newSol = new ArrayList<>();
        if (listaSOL != null && !listaSOL.isEmpty()) {
            for (String sol : listaSOL) {
				try {
					String[] parts = sol.split("\\|");

					String applicazione = parts[0];

					boolean isTitolare = data.getUtente() != null && data.getUtente().getProfilo() != null
							&& data.getUtente().getProfilo().equals(FunzionalitaEnum.CONF_TITOLARE_PROF.getValue());

					String collocazione = parts[1];
					
					String profilo=parts[2];

					String ruolo = parts[3];

					if (collocazioni.stream().anyMatch(c -> c.equals(collocazione) || c.equals(collocazione + "ro"))
							&& ruoli.stream().anyMatch(r -> r.equals(ruolo) || r.equals(ruolo + "ro"))) {

						String solNuovo = parts[0] + '|' + parts[1] + '|' + parts[2] + '|' + parts[3];

						if (parts.length == 5 && !parts[4].isEmpty()) {
							String dataFineValidita = parts[4];
							String newDate = dataFineValidita.substring(8, dataFineValidita.indexOf(' ')) + "/"
									+ dataFineValidita.substring(5, 7) + "/" + dataFineValidita.substring(0, 4);
							solNuovo = solNuovo + '|' + newDate;
						}
						
						String codiceApp=applicazioneLowDao.getSolId(Long.parseLong(applicazione));
						
						if(!((codiceApp.equalsIgnoreCase(ConstantsWebApp.APPL_CONF)) && isTitolare && (ruoloTitConfig.equalsIgnoreCase(profilo)))) {
							newSol.add(solNuovo);
							
						}
						
						
					}
				} catch (Exception e) {
                    log.error("ERROR: Ricerca ServiziOnLine By IdUtente And Data - ", e);
                }
            }
        }
        return newSol;
    }

    private boolean checkSOLConfiguratore(Data data) {
        boolean superUser = data.getUtente() != null &&
                data.getUtente().getProfilo() != null &&
                data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());

        if (superUser) return true;

        boolean isOPListaSOLconConfiguratore = false;
        if (data.getUtente() != null && data.getUtente().getFunzionalitaAbilitate() != null && !data.getUtente().getFunzionalitaAbilitate().isEmpty())
            isOPListaSOLconConfiguratore = data.getUtente().getFunzionalitaAbilitate().stream().anyMatch(f -> f.equals(FunzionalitaEnum.OPListaSOLconConfiguratore.getValue()));

        return isOPListaSOLconConfiguratore;
    }

    private boolean checkListaProfiliCompleta(Data data) {
        boolean superUser = data.getUtente() != null &&
                data.getUtente().getProfilo() != null &&
                data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());

        if (superUser) return true;

        boolean isOPListaProfiliCompleta = false;
        if (data.getUtente() != null && data.getUtente().getFunzionalitaAbilitate() != null && !data.getUtente().getFunzionalitaAbilitate().isEmpty())
            isOPListaProfiliCompleta = data.getUtente().getFunzionalitaAbilitate().stream().anyMatch(f -> f.equals(FunzionalitaEnum.OPListaProfiliCompleta.getValue()));

        return isOPListaProfiliCompleta;
    }

	@Override
	public List<ServizioOnLineDTO> recuperaSolSelezionabili(Long idCollocazione, Long ruolo,Data data) {

		boolean superUser = data.getUtente() != null &&
	            data.getUtente().getProfilo() != null &&
	            data.getUtente().getProfilo().equals(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());
		
		boolean isDelegato = data.getUtente() != null &&
				data.getUtente().getProfilo() != null &&
				data.getUtente().getProfilo().equals(FunzionalitaEnum.CONF_DELEGATO_PROF.getValue());
		
		List<ServizioOnLineDTO> sol =  applicazioneLowDao.findSolSelezionabili(idCollocazione);
		if(isDelegato) sol.removeIf(s -> s.getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE));
		
		if(sol.isEmpty())
			return Collections.emptyList();
		List<Long> idSol=sol.stream().map(ServizioOnLineDTO::getId).collect(Collectors.toList());
		List<ServizioOnLineDTO> solSelezionabili=applicazioneLowDao.findSolFiltrati(idSol,ruolo);
		boolean isOPListaProfiliCompleta = checkListaProfiliCompleta(data);
		for(ServizioOnLineDTO s: solSelezionabili) {
			List<ProfiloDTO> profili = funzionalitaLowDao.findProfiliByIdApplicazione(s.getId(), isOPListaProfiliCompleta, superUser);
			List<ProfiloDTO> profiliByRuolo = funzionalitaLowDao.findProfiliByIdApplicazioneAndRuolo(s.getId(), isOPListaProfiliCompleta,ruolo, superUser);
			List<ProfiloDTO> profiliByCollocazione = funzionalitaLowDao.findProfiliByIdApplicazioneAndCollocazione(s.getId(), isOPListaProfiliCompleta,idCollocazione, superUser);
			
		     if (profili != null && !profili.isEmpty()) {
	    		if (profiliByRuolo != null && !profiliByRuolo.isEmpty()) {
	    			Set<Long> idProfiliRuolo = profiliByRuolo.stream().map(ProfiloDTO::getId).collect(Collectors.toSet());
	    			profili.removeIf(p -> !idProfiliRuolo.contains(p.getId()));
//						profiliByRuolo.forEach(prof->{
//							Set<Long> idProfili = profili.stream().map(ProfiloDTO::getId).collect(Collectors.toSet());
//							if(!idProfili.contains(prof.getId())) {
//								profili.add(prof);
//							}
//						});
				}
	    		if (profiliByCollocazione != null && !profiliByCollocazione.isEmpty()) {
	    			Set<Long> idProfiliCollocazione = profiliByCollocazione.stream().map(ProfiloDTO::getId).collect(Collectors.toSet());
	    			profili.removeIf(p -> !idProfiliCollocazione.contains(p.getId()));
//		    			profiliByCollocazione.forEach(prof->{
//							Set<Long> idProfili = profili.stream().map(ProfiloDTO::getId).collect(Collectors.toSet());
//							if(!idProfili.contains(prof.getId())) {
//								profili.add(prof);
//							}
//						});
				}
		     }
		     
		     if (profili != null && !profili.isEmpty()) {
                 profili.forEach(profiloDTO -> {

                     //Acquisisco le funzionalità
                     List<FunzionalitaTreeDTO> idFunzionalita = funzionalitaLowDao.findAllFunzionalitaByIdProfilo(profiloDTO.getId());

                     //Assegno le informazioni alle funzionalità
                     if (idFunzionalita != null && !idFunzionalita.isEmpty()) {
                         List<FunzionalitaDTO> funzionalitaList = new ArrayList<>();
                         idFunzionalita.forEach(funzionalitaTreeDTO -> {
                             FunzionalitaDTO funzionalitaDTO = funzionalitaLowDao.findFunzionalitaById(funzionalitaTreeDTO.getId());
                             if (funzionalitaDTO != null) {
                                 funzionalitaDTO.setParent(funzionalitaLowDao.findFunzionalitaById(funzionalitaTreeDTO.getParent()));
                                 funzionalitaList.add(funzionalitaDTO);
                             } else {
                                 log.error("ERROR (in recuperaSolSelezionabili): Funzionalita non presente per id " + funzionalitaTreeDTO.getId() );
                             }
                         });
                         profiloDTO.setFunzionalita(funzionalitaList);
                     }
                 });
             }
			
		     s.setProfili(profili);
		}
		
		return solSelezionabili;
	}

	@Override
	public boolean getSolId(Long id) {
		
		return applicazioneLowDao.getSolId(id).equalsIgnoreCase("SOLCONFIG") ? true : false;
	}

	@Override
	public List<Long> getProfileTitolareoDelegatoSolConfig() {

		return applicazioneLowDao.getProfileTitolareoDelegatoSolConfig();
	}

	@Override
	public List<ServizioOnLineDTO> recuperaSolSelezionabiliAbilitazione(Long collocazione, Data data) {

		List<ServizioOnLineDTO> sol =  applicazioneLowDao.findSolSelezionabili(collocazione);
		if(sol.isEmpty())
			return Collections.emptyList();
		sol.removeIf(e->e.getCodice().equalsIgnoreCase("SOLCONFIG"));
		List<Long> idSol=sol.stream().map(ServizioOnLineDTO::getId).collect(Collectors.toList());
		boolean isOPListaProfiliCompleta = checkListaProfiliCompleta(data);
		for(ServizioOnLineDTO s: sol) {
			List<ProfiloDTO> profili = funzionalitaLowDao.findProfiliByIdApplicazione(s.getId(), isOPListaProfiliCompleta, false);
			//List<ProfiloDTO> profiliByRuolo = funzionalitaLowDao.findProfiliByIdApplicazioneAndRuolo(s.getId(), isOPListaProfiliCompleta,ruolo);
			//List<ProfiloDTO> profiliByCollocazione = funzionalitaLowDao.findProfiliByIdApplicazioneAndCollocazione(s.getId(), isOPListaProfiliCompleta,idCollocazione);
			
		     if (profili != null && !profili.isEmpty()) {
		    	 profili.forEach(profiloDTO -> {

                     //Acquisisco le funzionalità
                     List<FunzionalitaTreeDTO> idFunzionalita = funzionalitaLowDao.findAllFunzionalitaByIdProfilo(profiloDTO.getId());

                     //Assegno le informazioni alle funzionalità
                     if (idFunzionalita != null && !idFunzionalita.isEmpty()) {
                         List<FunzionalitaDTO> funzionalitaList = new ArrayList<>();
                         idFunzionalita.forEach(funzionalitaTreeDTO -> {
                             FunzionalitaDTO funzionalitaDTO = funzionalitaLowDao.findFunzionalitaById(funzionalitaTreeDTO.getId());
                             if (funzionalitaDTO != null) {
                                 funzionalitaDTO.setParent(funzionalitaLowDao.findFunzionalitaById(funzionalitaTreeDTO.getParent()));
                                 funzionalitaList.add(funzionalitaDTO);
                             } else {
                                 log.error("ERROR (in recuperaSolSelezionabiliAbilitazione): Funzionalita non presente per id " + funzionalitaTreeDTO.getId() );
                             }
                         });
                         profiloDTO.setFunzionalita(funzionalitaList);
                     }
                 });
             }
			
		     s.setProfili(profili);
		}
		
		return sol;
	}

}
