/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.Pagination;

public interface GetRuoloService {

	Pagination<ModelRuolo> getRuolo(Integer limit, Integer offset);
}
