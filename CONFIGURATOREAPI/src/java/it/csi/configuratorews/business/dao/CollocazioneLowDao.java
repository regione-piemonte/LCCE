/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.Collection;
import java.util.List;

import it.csi.configuratorews.business.dto.CollocazioneDto;

public interface CollocazioneLowDao extends EntityBaseLowDao<CollocazioneDto, Long> {

	List<Long> findAziendeSanitarie(List<Long> idCollocazioni);

	List<Long> findSediSanitarie(List<Long> idAziendeSanitarie);

	Collection<CollocazioneDto> findByAziendaAndDataValidazione(String codiceAzienda);

	Collection<CollocazioneDto> findByCodice(String codiceCollocazione);

	// 02/08
	List<CollocazioneDto> findByAziendaAndStruttura(String codiceAzienda, String codiceStruttura, Integer limit,
			Integer offset);

	Long countByAziendaAndStruttura(String codiceAzienda, String codiceStruttura);

	List<CollocazioneDto> findByStrutturaAndDataValidazione(String codiceStruttura);

	Collection<CollocazioneDto> findByCodiceAndAzienda(String codiceCollocazione, String codiceAzienda);

	List<CollocazioneDto> findByRuoloAziendaAndUtente(String azienda, String ruolo, String cf, Integer limit,
			Integer offset);

	Long countByRuoloAziendaAndUtente(String codiceFiscale, String ruoloCodice, String codiceAzienda);
	
	String getCodAziendaByRuoloCollAndCF(String codiceFiscale, String codiceCollocazione, String codiceRuolo,
			String codiceApplicazione);

}
