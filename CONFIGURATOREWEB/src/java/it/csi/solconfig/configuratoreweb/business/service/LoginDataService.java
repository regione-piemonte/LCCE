/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.iride2.policy.entity.Identita;

public interface LoginDataService {
	void insertLoginDataDemo(Identita id, String token, String ipAddressClient, String ruolo, String azienda,
			String collocazione);
}
