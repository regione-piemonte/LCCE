/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import it.csi.configuratorews.business.dto.CollocazioneProfiloDto;

public interface CollocazioneProfiloLowDao extends EntityBaseLowDao<CollocazioneProfiloDto, Long> {

	Boolean existsProfiloByAzienda(String codiceAzienda, String codiceProfilo);

	Boolean existsApplicazioneByAziendaAndFunzionalita(String codiceAzienda, String codiceApplicazione);

}
