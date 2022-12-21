/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloCompatibilita;

public interface RuoloCompatibilitaLowDao extends EntityBaseLowDao<RuoloCompatibilita, Long> {

    List<RuoloCompatibilita> findByIdRuolo(Long idRuolo);
    List<RuoloCompatibilita> findByIdRuoloCompatibile(Long idRuoloCompatibile);
    List<RuoloCompatibilita> findByIdRuoli(Long idRuolo, Long idRuoloCompatibile);
}
