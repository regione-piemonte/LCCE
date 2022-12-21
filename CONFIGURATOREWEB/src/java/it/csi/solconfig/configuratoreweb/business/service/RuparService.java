/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FaqRuparDto;
import it.csi.solconfig.configuratoreweb.presentation.model.CredenzialiRuparModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RichiestaCredenzialiView;

public interface RuparService extends BaseService  {
	
	public PaginaDTO<RichiestaCredenzialiView> ricercaRichiesteRupar(CredenzialiRuparModel credenzialiRuparModel, Data data) throws Exception;
	
	public FaqRuparDto downloadFaq() throws Exception;

}
