/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.RuoloUtenteDto;

import java.util.Collection;
import java.util.List;

public interface RuoloUtenteLowDao extends EntityBaseLowDao<RuoloUtenteDto, Long> {


    Collection<RuoloUtenteDto> findByUtenteRuoloAndData(RuoloUtenteDto ruoloUtenteDto);

    List<RuoloUtenteDto> findByCodiceRuoloAndDataValidita(String codiceRuolo);

    Collection<RuoloUtenteDto> findByIdRuolo(Long idRuolo);
    //27 07 2022
    List<RuoloUtenteDto> findByCodiceFiscale(String codiceFiscale, Integer offset,Integer limit);
    Long countByUtente(String codiceFiscale);

}
