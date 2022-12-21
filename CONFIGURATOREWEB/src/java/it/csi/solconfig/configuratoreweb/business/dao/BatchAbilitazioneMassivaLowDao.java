/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaDto;

public interface BatchAbilitazioneMassivaLowDao extends EntityBaseLowDao<BatchAbilitazioneMassivaDto, Long> {

	
	BatchAbilitazioneMassivaDto salva(BatchAbilitazioneMassivaDto abilitazione);

    void modifica(BatchAbilitazioneMassivaDto abilitazione);
    
    List<BatchAbilitazioneMassivaDto> findByCfOperatore(String codiceFiscale,boolean abilitazione);
    
    List<BatchAbilitazioneMassivaDto> findBatchByStatoAndByCfOperatore(String codiceFiscale,List<String> stati);
    
   
   
}
