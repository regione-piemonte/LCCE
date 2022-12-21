/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FaqRuparDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RichiestaCredenzialiDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.presentation.model.CredenzialiRuparModel;

public interface RichiestaCredenzialiLowDao extends EntityBaseLowDao<RichiestaCredenzialiDto, Long> {
	
	Collection<RichiestaCredenzialiDto> findRichiesteRupar(CredenzialiRuparModel ruparModel);

    Collection<RichiestaCredenzialiDto> findByIdUtente(UtenteDto utenteDto);
    
    FaqRuparDto findPfd(String descrizione);
}
