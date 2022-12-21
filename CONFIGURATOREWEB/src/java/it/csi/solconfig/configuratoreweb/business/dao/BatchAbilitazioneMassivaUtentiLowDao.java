/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaUtentiDto;

public interface BatchAbilitazioneMassivaUtentiLowDao extends EntityBaseLowDao<BatchAbilitazioneMassivaUtentiDto, Long> {

	BatchAbilitazioneMassivaUtentiDto salva(BatchAbilitazioneMassivaUtentiDto abilitazione);

    void modifica(BatchAbilitazioneMassivaUtentiDto abilitazione);
    
    String progressoBatch(Long idbatch);
    
    List<BatchAbilitazioneMassivaUtentiDto> findByIdBatch(Long idBatch);
    
   
    
}
