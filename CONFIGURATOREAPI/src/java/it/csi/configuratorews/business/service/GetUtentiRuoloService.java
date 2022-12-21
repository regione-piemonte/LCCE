/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ModelUtente;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;

import java.util.List;

public interface GetUtentiRuoloService {

    List<ModelUtenteBase> getUtentiRuolo(String codiceRuolo);
}
