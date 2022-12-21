/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import it.csi.configuratorews.business.dto.UtenteDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface UtenteLowDao extends EntityBaseLowDao<UtenteDto, Long> {

	UtenteDto salva(UtenteDto utenteDto);

	void modifica(UtenteDto utenteDto);

	UtenteDto findByCodiceFiscale(String codiceFiscale);

	Collection<UtenteDto> findByCodiceFiscaleValido(String codiceFiscale);

	Collection<UtenteDto> findByDataFineValidita(Date dataFineValidita);

	List<UtenteDto> findByRuoloCollocazione(String ruolo, String collocazione, String codiceAzienda, Integer limit,
			Integer offset);

	List<UtenteDto> findByRuoloCollocazioneAzienda(String ruolo, String collocazione, String codiceAzienda,
			Integer limit, Integer offset);

	Long countByRuoloCollocazione(String ruolo, String collocazione, String codiceAzienda);

	List<UtenteDto> findUtentiByApplicazioneAndAzienda(String applicazione, String azienda, Integer limit,
			Integer offset);

	Long countUtentiByApplicazioneAndAzienda(String applicazione, String azienda);

	Long countByRuoloCollocazioneAzienda(String ruolo, String codiceCollocazione, String codiceAzienda);
}
