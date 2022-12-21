/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ModelFunzionalitaProfilo;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalita;

public interface GetProfiliFunzionalitaService {

	public Pagination<ProfiloFunzionalita> getProfiliFunzionalita(String codiceApplicazione, Integer limit,
			Integer offset, String codiceAzienda);

	public Pagination<ModelFunzionalitaProfilo> getFunzionalitaProfiliByApp(String codiceApplicazione,
			String codiceAzienda, Integer limit, Integer offset);

}
