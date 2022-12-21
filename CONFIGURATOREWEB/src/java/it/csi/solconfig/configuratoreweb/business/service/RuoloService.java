/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;

import java.util.List;

public interface RuoloService extends BaseService {

    List<RuoloDTO> ricercaTuttiRuoli(Data data);

    List<RuoloDTO> ricercaRuoliNonConfiguratore(Data data);

    List<RuoloDTO> ricercaRuoliByIdUtente(Long idUtente, Data data);
    
    List<RuoloDTO> getRuoliSelezionabili(String cfOperatore, String codiceCollocazione, String codiceRuolo);

    List<String> getRuoliCompatibili(String id);
	
	 List<RuoloDTO> ricercaTuttiRuoliInserimento();

	List<RuoloDTO> ricercaTuttiRuoliNoFilter();
}
