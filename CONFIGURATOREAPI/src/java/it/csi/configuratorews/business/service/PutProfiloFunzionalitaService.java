/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import java.util.Date;

import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalitaBody;

public interface PutProfiloFunzionalitaService {

    void updateProfiloFunzionalita(String codiceFunzionalita, String codiceApplicazione, ProfiloFunzionalitaBody body,String codiceAzienda,String codiceFiscale);
}
