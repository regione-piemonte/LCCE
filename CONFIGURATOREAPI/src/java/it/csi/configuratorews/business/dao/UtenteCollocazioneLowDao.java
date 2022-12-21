/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.UtenteCollocazioneDto;
import it.csi.configuratorews.business.dto.UtenteDto;

import java.util.List;

public interface UtenteCollocazioneLowDao extends EntityBaseLowDao<UtenteCollocazioneDto, Long>{

    List<UtenteCollocazioneDto> findByUtenteDto(UtenteDto utenteDto);
}