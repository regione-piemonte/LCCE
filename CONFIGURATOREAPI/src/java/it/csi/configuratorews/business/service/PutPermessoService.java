/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ListaPermessi;

public interface PutPermessoService {

	void updatePermesso(String codiceFunzionalita, String codiceApplicazione, String shibIdentitaCodiceFiscale, String codiceAzienda, ListaPermessi permessi);

}
