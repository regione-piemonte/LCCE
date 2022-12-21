/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.ServizioOnLineDTO;

import java.util.List;

public interface ServiziOnLineService extends BaseService {

    List<ServizioOnLineDTO> recuperaServiziOnLineSelezionabili(List<Long> idCollocazioni, Data data);
    
    List<ServizioOnLineDTO> recuperaSolSelezionabili(Long collocazione,Long ruolo,Data data);

    List<String> ricercaServiziOnLineByIdUtenteAndData(Long idUtente, Data data, List<String> collocazioni, List<String> ruoli);
    
    boolean getSolId(Long id);

	List<Long> getProfileTitolareoDelegatoSolConfig();
	
	List<ServizioOnLineDTO> recuperaSolSelezionabiliAbilitazione(Long collocazione,Data data);
    
    

}
