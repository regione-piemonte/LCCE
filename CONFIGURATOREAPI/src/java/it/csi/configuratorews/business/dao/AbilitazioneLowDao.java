/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.RuoloUtenteDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;

public interface AbilitazioneLowDao extends EntityBaseLowDao<AbilitazioneDto, Long> {

	Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(AbilitazioneDto abilitazioneDto);

	Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(RuoloUtenteDto ruoloUtenteDto,
			ApplicazioneDto applicazioneDto);

	List<AbilitazioneDto> findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtente(
			Long idApplicazione, Long idCollocazione, Long idFunzione, String codiceFiscaleUtente);

	Collection<AbilitazioneDto> findByRuoloUtenteDto(RuoloUtenteDto ruoloUtenteDto);

	List<AbilitazioneDto> findByIdFunzioneAndAzienda(TreeFunzionalitaDto treeFunzionalitaDto, String codiceAzienda);

	List<AbilitazioneDto> findAbilitazioniByRuoloByCollByAzByCF(String codiceRuolo, String codiceCollocazione,
			String codiceAzienda, String codiceFiscaleUtente);

	ApplicazioneDto findApplicazioneByCodice(String codiceApplicazione);

	List<AbilitazioneDto> findApplicazioniByRuoloCollocazione(String ruolo, String collocazione);

	List<AbilitazioneDto> findProfiliByRuoloCollocazione(String ruolo, String collocazione);

	List<AbilitazioneDto> findApplicazioniByRuoloCollocazioneOrAzienda(String ruolo, String collocazione,
			String azienda);

	// 21/07/2022
	List<AbilitazioneDto> findByCodiceFiscaleUtenteAndCodiceRuolo(String codiceFiscale, String codiceRuolo,
			Integer offset, Integer limit, String codiceAzienda);

	Long countByCodiceFiscaleUtenteAndCodiceRuolo(String codiceFiscale, String codiceRuolo, String codiceAzienda);

	List<AbilitazioneDto> findAbilitazioniByApplicazioneAndIdTree(String codiceApplicazione, Long funzTreeId);

	// 25/07/2022
	List<AbilitazioneDto> getAbilitazioniUtente(String codiceFiscale, String ruoloCodice, String applicazioneCodice,
			String collocazioneCodice, String profiloCodice, String funzionalitaCodice, String codiceAzienda);

	List<AbilitazioneDto> findAbilitazioniByUtente(Long id);

	List<AbilitazioneDto> findApplicazioniProfiliByUtenteRuoloCollocazione(String utente, String ruolo,
			String collocazione, String codiceAzienda, List<String> applicazioni);

	List<String> findListaApplicazioni(String utente, String ruolo, String collocazione, String codiceAzienda);

	void cancellaAbilitazioniPreferenze(String codiceApplicazione, String codiceFunzionalita, String cfOperatore,
			Timestamp dataFineValidita);

	List<AbilitazioneDto> findByCodiceProfiloAndApplicazione(String codiceProfilo, String codiceApplicazione);

	List<AbilitazioneDto> findProfiliByRuoloCollocazioneOrAzienda(String ruolo, String collocazione,
			String codiceAzienda);

	List<AbilitazioneDto> findApplicazioniProfiliByUtenteRuoloCollocazioneOrAzienda(String codiceFiscale, String ruolo,
			String codiceCollocazione, String codiceAzienda, String codiceApplicazione);

}
