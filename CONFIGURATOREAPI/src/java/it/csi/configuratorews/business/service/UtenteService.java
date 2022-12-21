/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;

public interface UtenteService {

    ModelUtenteBase getUtenteInfo(String cfUtente);
    
    public void aggiornaAccessoPua(String cfUtente);
}
