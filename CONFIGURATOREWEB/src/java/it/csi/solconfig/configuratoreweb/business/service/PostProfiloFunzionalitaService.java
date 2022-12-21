/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.InserimentoProfiloFunzionalitaBody;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaModel;

public interface PostProfiloFunzionalitaService {

	TreeFunzionalitaDto insertNuovaFunzionalita(String codiceFunzionalita,
			String descrizioneFunzionalita, String codiceApplicazione, InserimentoProfiloFunzionalitaBody body,
			String codiceFiscale) throws Exception ;

	void insertNuovoProfilo(String codiceProfilo, String descrizioneProfilo, String codiceApplicazione,
			String shibIdentitaCodiceFiscale, InserimentoProfiloFunzionalitaBody body);

	FunzionalitaDto modificaFunzionalita(FunzionalitaModel funz, InserimentoProfiloFunzionalitaBody body,
			String shibIdentitaCodiceFiscale) throws Exception;
}
