/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.RuoloDto;

import java.util.Collection;
import java.util.List;

public interface RuoloLowDao extends CatalogoBaseLowDao<RuoloDto, Long> {

    Collection<RuoloDto> findByUtenteCodiceFiscale(String codiceFiscale);

    Collection<RuoloDto> findByCodiceAndDataValidita(String codiceRuolo);

    List<RuoloDto> findRuoli(RuoloDto ruoloDto, String flagAttivo, String flagNonAttivo, Boolean flagConfiguratore);

	List<RuoloDto> findByStatoAttivoEVisibilita(Integer limit, Integer offset);

	Long countByStatoAttivoEVisibilita();

	List<RuoloDto> findByProfilo(String codiceFunzionalita);

	List<RuoloDto> findByProfiloAndApplicazione(String codiceProfilo, String codiceApplicazione);
	
}
