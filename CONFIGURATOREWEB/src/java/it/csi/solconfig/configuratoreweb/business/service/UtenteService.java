/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtentiConfiguratoreViewDto;
import it.csi.solconfig.configuratoreweb.business.exceptions.UserExistException;
import it.csi.solconfig.configuratoreweb.presentation.model.*;
import it.csi.solconfig.configuratoreweb.business.exceptions.WebServiceException;

import java.text.ParseException;
import java.util.List;

public interface UtenteService extends BaseService {

    PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtente(RicercaUtenteModel ricercaUtenteModel, Data data) throws WebServiceException;

    List<UtentiConfiguratoreViewDto> exportUtenti(Data data);

    SalvataggioUtenteModel salvaUtente(FormNuovoUtente form, String cfOperatore, String uuid, Data data) throws ParseException, UserExistException;

    SalvataggioUtenteModel modificaUtente(Long id, FormNuovoUtente form, List<String> listaProfiliSolACuiUtenteAbilitato, Data data, String uuid) throws ParseException;

    UtenteDto recuperaUtenteByCodiceFiscale(String codiceFiscale);

    UtenteDto recuperaUtenteById(Long id);

    FormNuovoUtente getUtenteFromAura(String cf) throws WebServiceException;

    void controllaDataFineValiditaUtenteSol(UtenteDto utenteDto, List<String> profiliSol,
                                            String dataFineValiditaUtente) throws WebServiceException, ParseException;

    List<MessaggiUtenteDto> invioMailAUtenteProfilato(String cf, Data data, SalvataggioUtenteModel utenteSalvato) throws Exception;
    
    List<MessaggiUtenteDto> invioMailConfAdAura(String cf, SalvataggioUtenteModel utenteSalvato) throws Exception;

    String disabilitaTutteConfigurazioni(Long id, List<String> listaProfiliSolACuiUtenteAbilitato, Data data, String uuid);

    List<MessaggiUtenteDto> richiestaCredenzialiRupar(String cf, Data data) throws Exception;

    FormNuovoUtente getUtenteFromAuraOrMEF(String cf, Data data) throws WebServiceException;
    
    FormNuovoUtente getRuoliCollocazioniFromOpessan(FormNuovoUtente form, Data data) throws WebServiceException;

    boolean checkAbilitazione(Long id) throws Exception;

    Boolean checkIsAbilitazioneConfiguratorePresente(Long idCollocazione,String codiceFiscale);
    
	PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> ricercaUtentiAbilitazioneMassiva(
			AbilitazioneMassivaModel abilitazioneMassivaModel, Data data);
	
	PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> ricercaUtentiDisabilitazioneMassiva(
			AbilitazioneMassivaModel abilitazioneMassivaModel, Data data);
	
	void abilitazioneMassiva(AbilitazioneMassivaModel abilitazioneMassivaModel, Data data);
	
	List<StoricoDTO> findStoricoByUtente(Data data,boolean abilitazione);
	
	boolean checkBatchInElaborazione(Data data, List<String> stati);
	
	byte[] downloadCsv(Long idBatch, boolean inseriti);

	String getMessaggioAura(String msg, String app, Boolean errore);

}
