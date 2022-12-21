/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.List;
import java.util.Set;

import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.dto.configuratorews.FilterPreferences;
import it.csi.configuratorews.dto.configuratorews.RuoloCollocazione;

public interface PreferenzaLowDao extends EntityBaseLowDao<PreferenzaDto, Long> {
	public List<PreferenzaDto> findByFilter(FilterPreferences filter);
	public List<PreferenzaDto> findLeftByFilter(FilterPreferences filter);

	public List<PreferenzaDto> findApplicazionePreferenzeByCFAndApplicazioni(String cf, Set<String> applicazioni,boolean validRecord);
	public List<PreferenzaDto> findApplicazionePreferenzeByCFAndApplicazioni(String cf,  Set<String> applicazioni);
	public List<PreferenzaDto> findRuoloPreferenzeByCFAndRuolo(String cf, Set<String> ruolo,boolean validRecord);
	public List<PreferenzaDto> findRuoloPreferenzeByCFAndRuolo(String cf, Set<String> ruolo);
	public List<PreferenzaDto> findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(String cf, Set<RuoloCollocazione> ruoloCollocazioni,boolean validRecord);
	public List<PreferenzaDto> findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(String cf, Set<RuoloCollocazione>  ruoloCollocazioni);
}
