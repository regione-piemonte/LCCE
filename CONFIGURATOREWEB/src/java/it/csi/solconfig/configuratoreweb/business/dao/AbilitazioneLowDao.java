/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.Collection;
import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloUtenteDto;

public interface AbilitazioneLowDao extends EntityBaseLowDao<AbilitazioneDto, Long> {

	Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(AbilitazioneDto abilitazioneDto);
	
	Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(RuoloUtenteDto ruoloUtenteDto, ApplicazioneDto applicazioneDto);

	List<AbilitazioneDto> findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtente(Long idApplicazione,
																											   Long idCollocazione,
																											   Long idFunzione,
																											   String codiceFiscaleUtente);
	Collection<AbilitazioneDto> findByRuoloUtenteDto(RuoloUtenteDto ruoloUtenteDto);

	List<AbilitazioneDto> findAbilitazioniValideByCodiceFiscale(String codiceFiscale);

	List<AbilitazioneDto> findByCodiceFiscaleAndApplicazioneAndDateValidita(String cfUtente, String codiceApplicazione);
	
	List<AbilitazioneDto> findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtenteAndIdRuolo(Long idApplicazione,
																														 Long idCollocazione,
																														 Long idFunzione,
																														 String codiceFiscaleUtente,
																														 Long idRuolo);
	
	List<AbilitazioneDto> findAbilitazioneConfiguratoreByIdCollocazioneAndCodiceFiscale(Long idCollocazione,String codiceFiscale);
	
	public List<AbilitazioneDto> findAbilitazioniCaricamentoMassivo(Long idApplicazione, Long idCollocazione, Long idFunzione,
			String codiceFiscaleUtente, Long idRuolo);
	
	List<AbilitazioneDto> findAbilitazioniByIdUtenteAndIdAppAndIdCollAndIdRuolo(Long idUtente, Long idApplicazione, Long idCollocazione, Long idRuolo);

	Collection<AbilitazioneDto> findByIdFunzTree(Long idTreeFunzione);

}
