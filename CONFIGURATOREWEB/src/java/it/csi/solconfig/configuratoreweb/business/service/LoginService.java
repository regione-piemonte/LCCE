/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.GetTokenInformation2Response;
import it.csi.solconfig.configuratoreweb.presentation.model.Utente;

public interface LoginService extends BaseService {


	Utente getUtenteLogin(GetTokenInformation2Response getTokenInformation2Response, String ipAddressClient);
}
