/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaFunzionalitaModel;

public interface FunzionalitaService extends BaseService {

	PaginaDTO<FunzionalitaDto> ricercaFunzionalita(RicercaFunzionalitaModel ricercaFunzionalitaModel);

	long inserisciFunzionalita(FunzionalitaModel funzionalitaModel);

	void loadFunzionalitaById(long id, FunzionalitaModel funzionalita);

	boolean existsFunzionalita(String codice);
}
