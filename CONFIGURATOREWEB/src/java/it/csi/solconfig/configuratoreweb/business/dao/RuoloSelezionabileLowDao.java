/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloCompatibilita;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;

public interface RuoloSelezionabileLowDao extends EntityBaseLowDao<RuoloSelezionabileDto, Long> {

    List<RuoloSelezionabileDto> findByIdRuolo(Long idRuolo);
    List<RuoloSelezionabileDto> findByIdRuoloSelezionabile(Long idRuoloSelezionabile);
    List<RuoloSelezionabileDto> findByIdRuoli(Long idRuolo, Long idRuoloSelezionabile);
}
