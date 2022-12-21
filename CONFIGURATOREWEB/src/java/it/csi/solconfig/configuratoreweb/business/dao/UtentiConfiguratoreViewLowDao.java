/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtentiConfiguratoreViewDto;

import java.util.List;

public interface UtentiConfiguratoreViewLowDao {
    List<UtentiConfiguratoreViewDto> findForExcelExport(boolean isOperatore, List<String> collocazioneFilter,String codice);
}
