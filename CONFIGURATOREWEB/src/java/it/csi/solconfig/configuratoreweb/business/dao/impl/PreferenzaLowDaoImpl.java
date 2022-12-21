/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.PreferenzaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PreferenzaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;

@Component
public class PreferenzaLowDaoImpl extends EntityBaseLowDaoImpl<PreferenzaDto, Long> implements PreferenzaLowDao {

	@Override
	public PreferenzaDto getPreferenzaRuolo(UtenteDto utente, RuoloDto ruolo) {
		try {
			return entityManager.createQuery(
					"SELECT p FROM PreferenzaDto p "
					+ "WHERE p.utente = :utente AND p.ruoloDto = :ruolo "
					+ "AND p.collocazioneDto IS NULL AND p.applicazioneDto IS NULL", PreferenzaDto.class)
						 .setParameter("utente", utente)
						 .setParameter("ruolo", ruolo)
						 .getSingleResult();
		} catch (NoResultException e) {			
			return null;
		}
	}

	@Override
	public PreferenzaDto getPreferenzaRuoloCollocazione(UtenteDto utente, RuoloDto ruolo, CollocazioneDto collocazione) {
		try {
			return entityManager.createQuery(
					"SELECT p FROM PreferenzaDto p "
					+ "WHERE p.utente = :utente AND p.ruoloDto = :ruolo AND p.collocazioneDto = :collocazione "
					+ "AND p.applicazioneDto IS NULL", PreferenzaDto.class)
						 .setParameter("utente", utente)
						 .setParameter("ruolo", ruolo)
						 .setParameter("collocazione", collocazione)
						 .getSingleResult();
		} catch (NoResultException e) {			
			return null;
		}
	}

	@Override
	public PreferenzaDto getPreferenzaApplicazione(UtenteDto utente, ApplicazioneDto applicazione) {
		try {
			return entityManager.createQuery(
					"SELECT p FROM PreferenzaDto p "
					+ "WHERE p.utente = :utente AND p.applicazioneDto = :applicazione "
					+ "AND p.ruoloDto IS NULL AND p.collocazioneDto IS NULL", PreferenzaDto.class)
						 .setParameter("utente", utente)
						 .setParameter("applicazione", applicazione)
						 .getSingleResult();
		} catch (NoResultException e) {			
			return null;
		}
	}

	@Override
	public List<PreferenzaDto> getPreferenzaByUtente(UtenteDto utente) {
		
		return entityManager.createQuery(
				"SELECT p FROM PreferenzaDto p "
				+ "WHERE p.utente = :utente ", PreferenzaDto.class)
					 .setParameter("utente", utente)
					 .getResultList();
	}

}


	

