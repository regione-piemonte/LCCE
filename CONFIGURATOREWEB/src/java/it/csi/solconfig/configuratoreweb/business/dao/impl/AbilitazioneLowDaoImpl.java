/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloUtenteDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class AbilitazioneLowDaoImpl extends EntityBaseLowDaoImpl<AbilitazioneDto, Long> implements AbilitazioneLowDao {

	@Override
	public Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(RuoloUtenteDto ruoloUtenteDto,
			ApplicazioneDto applicazioneDto) {

		AbilitazioneDto abilitazioneDto = new AbilitazioneDto();
		Query query = entityManager.createQuery("from " + abilitazioneDto.getClass().getName()
				+ " a WHERE a.ruoloUtenteDto.id =:idRuoloUtente AND a.applicazioneDto.id = :idApplicazione AND ((:data BETWEEN a.dataInizioValidita AND a.dataFineValidita) OR (a.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00')))");
		query.setParameter("idRuoloUtente", ruoloUtenteDto.getId());
		query.setParameter("idApplicazione", applicazioneDto.getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(AbilitazioneDto abilitazioneDto) {
		Query query = entityManager.createQuery("from " + abilitazioneDto.getClass().getName()
				+ " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente "
				+ "AND t.applicazioneDto.id = :idApplicazione AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (t.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
		query.setParameter("idRuoloUtente", abilitazioneDto.getRuoloUtenteDto().getId());
		query.setParameter("idApplicazione", abilitazioneDto.getApplicazioneDto().getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtente(Long idApplicazione,
																													  Long idCollocazione,
																													  Long idFunzione,
																													  String codiceFiscaleUtente) {
		Query query = entityManager.createQuery("SELECT DISTINCT ara FROM AbilitazioneDto ara "
				+ "JOIN ara.applicazioneDto ada2 JOIN ara.utenteCollocazioneDto aruc "
				+ "JOIN aruc.collocazioneDto atc JOIN ara.treeFunzionalitaDto arft JOIN arft.funzionalitaDto atf "
				+ "WHERE ada2.id = :idApplicazione "
				+ "AND atc.colId = :idCollocazione "
				+ "AND atf.idFunzione = :idFunzione "
				+ "AND aruc.utenteDto.codiceFiscale = :codiceFiscaleUtente "
				+ "AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) OR (ara.dataFineValidita IS NULL AND (:data BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
		query.setParameter("idApplicazione", idApplicazione);
		query.setParameter("idCollocazione", idCollocazione);
		query.setParameter("idFunzione", idFunzione);
		query.setParameter("codiceFiscaleUtente", codiceFiscaleUtente);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}
	
	@Override
	public Collection<AbilitazioneDto> findByRuoloUtenteDto(RuoloUtenteDto ruoloUtenteDto) {
	   Query query = entityManager.createQuery("from " + AbilitazioneDto.class.getName()
	         + " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (t.dataFineValidita IS NULL AND :data >= t.dataInizioValidita )) ");
	   query.setParameter("idRuoloUtente", ruoloUtenteDto.getId());
	   query.setParameter("data", Utils.sysdate());
	   return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniValideByCodiceFiscale(String codiceFiscale) {
		Query query = entityManager.createQuery("SELECT DISTINCT a from AbilitazioneDto a "
				+ "INNER JOIN a.ruoloUtenteDto ru "
				+ "INNER JOIN ru.utenteDto u "
				+ "WHERE u.codiceFiscale = :codiceFiscale "
				+ "AND CURRENT_TIMESTAMP BETWEEN coalesce(a.dataInizioValidita, "
				+ "TO_TIMESTAMP('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) "
				+ "AND coalesce(a.dataFineValidita, TO_TIMESTAMP('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) "
				+ "AND "
				+ "CURRENT_TIMESTAMP BETWEEN coalesce(ru.dataInizioValidita, "
				+ "TO_TIMESTAMP('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) AND coalesce( "
				+ "ru.dataFineValidita, TO_TIMESTAMP('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS'))");
		query.setParameter("codiceFiscale", codiceFiscale);
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findByCodiceFiscaleAndApplicazioneAndDateValidita(String cfUtente, String codiceApplicazione) {
		Query query = entityManager.createQuery("from " + AbilitazioneDto.class.getName()
				+ " t WHERE t.ruoloUtenteDto.utenteDto.codiceFiscale = :cfUtente AND t.applicazioneDto.codice = :codiceApplicazione " +
				" AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (t.dataFineValidita IS NULL AND :data >= t.dataInizioValidita)) ");
		query.setParameter("cfUtente", cfUtente);
		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}
	
	@Override
	public List<AbilitazioneDto> findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtenteAndIdRuolo(Long idApplicazione,
																													  Long idCollocazione,
																													  Long idFunzione,
																													  String codiceFiscaleUtente,
																													  Long idRuolo) {
		Query query = entityManager.createQuery("SELECT DISTINCT ara FROM AbilitazioneDto ara "
				+ "JOIN ara.applicazioneDto ada2 JOIN ara.utenteCollocazioneDto aruc JOIN ara.ruoloUtenteDto ru "
				+ "JOIN aruc.collocazioneDto atc JOIN ru.ruoloDto r "
				+ "JOIN ara.treeFunzionalitaDto arft JOIN arft.funzionalitaDto atf "
				+ "WHERE ada2.id = :idApplicazione "
				+ "AND atc.colId = :idCollocazione "
				+ "AND atf.idFunzione = :idFunzione "
				+ "AND aruc.utenteDto.codiceFiscale = :codiceFiscaleUtente "
				+ "AND r.id = :idRuolo "
				+ "AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) OR (ara.dataFineValidita IS NULL AND (:data BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
		query.setParameter("idApplicazione", idApplicazione);
		query.setParameter("idCollocazione", idCollocazione);
		query.setParameter("idFunzione", idFunzione);
		query.setParameter("codiceFiscaleUtente", codiceFiscaleUtente);
		query.setParameter("idRuolo", idRuolo);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioneConfiguratoreByIdCollocazioneAndCodiceFiscale(Long idCollocazione,String codiceFiscaleUtente) {
		
		Query query = entityManager.createQuery("SELECT DISTINCT ara FROM AbilitazioneDto ara "
			+ "JOIN ara.applicazioneDto ada2 JOIN ara.utenteCollocazioneDto aruc JOIN ara.ruoloUtenteDto ru "
			+ "JOIN aruc.collocazioneDto atc  "
			+ "JOIN ara.treeFunzionalitaDto arft JOIN arft.funzionalitaDto atf "
			+ "WHERE ada2.codice = 'SOLCONFIG' "
			+ "AND atc.colCodAzienda IN ( "
			+ "SELECT coll.colCodAzienda FROM CollocazioneDto coll WHERE coll.colId = :idCollocazione ) "
			//+ "AND atc.colId = :idCollocazione AND atc.flagAzienda='S' "
			+ "AND aruc.utenteDto.codiceFiscale = :codiceFiscaleUtente "
			+ "AND atf.codiceFunzione IN ('CONF_TITOLARE','CONF_DELEGATO') "
			+ "AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) OR (ara.dataFineValidita IS NULL AND (:data BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
	query.setParameter("idCollocazione", idCollocazione);
	query.setParameter("codiceFiscaleUtente", codiceFiscaleUtente);
	query.setParameter("data", Utils.sysdate());
	return query.getResultList();}

	@Override
	public List<AbilitazioneDto> findAbilitazioniCaricamentoMassivo(Long idApplicazione, Long idCollocazione, Long idFunzione,
			String codiceFiscaleUtente, Long idRuolo) {
		return entityManager.createQuery("SELECT DISTINCT ara FROM AbilitazioneDto ara "
				//+ "JOIN ara.applicazioneDto ada2 JOIN ara.utenteCollocazioneDto aruc JOIN ara.ruoloUtenteDto ru "
				//+ "JOIN aruc.collocazioneDto atc JOIN ru.ruoloDto r "
				//+ "JOIN ara.treeFunzionalitaDto arft JOIN arft.funzionalitaDto atf "
				+ "WHERE ara.applicazioneDto.id = :idApplicazione "
				+ "AND ara.utenteCollocazioneDto.collocazioneDto.colId = :idCollocazione "
				+ "AND ara.treeFunzionalitaDto.funzionalitaDto.idFunzione = :idFunzione "
				+ "AND ara.utenteCollocazioneDto.utenteDto.codiceFiscale = :codiceFiscaleUtente "
				+ "AND ara.ruoloUtenteDto.ruoloDto.id = :idRuolo "
				+ "AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
				+ "OR (ara.dataFineValidita IS NULL "
				+ "AND (:data BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) "
				,AbilitazioneDto.class)
				.setParameter("idApplicazione", idApplicazione)
				.setParameter("idCollocazione", idCollocazione)
				.setParameter("idFunzione", idFunzione)
				.setParameter("codiceFiscaleUtente", codiceFiscaleUtente)
				.setParameter("idRuolo", idRuolo)
				.setParameter("data", Utils.sysdate())
				.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniByIdUtenteAndIdAppAndIdCollAndIdRuolo(Long idUtente, Long idApplicazione, Long idCollocazione, Long idRuolo) {
		return entityManager.createQuery("SELECT DISTINCT ara FROM AbilitazioneDto ara "
				//+ "JOIN ara.applicazioneDto ada2 JOIN ara.utenteCollocazioneDto aruc JOIN ara.ruoloUtenteDto ru "
				+ "WHERE ara.applicazioneDto.id = :idApplicazione "
				+ "AND ara.utenteCollocazioneDto.collocazioneDto.colId = :idCollocazione "
				+ "AND ara.ruoloUtenteDto.ruoloDto.id = :idRuolo "
				+ "AND ara.utenteCollocazioneDto.utenteDto.id = :idUtente "
				+ "AND ara.ruoloUtenteDto.utenteDto.id = :idUtente "
				+ "AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
				+ "OR (ara.dataFineValidita IS NULL AND (:data BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ", AbilitazioneDto.class)
			.setParameter("idApplicazione", idApplicazione)
			.setParameter("idUtente", idUtente)
			.setParameter("idCollocazione", idCollocazione)
			.setParameter("idRuolo", idRuolo)
			.setParameter("data", Utils.sysdate())
			.getResultList();
	}

	@Override
	public Collection<AbilitazioneDto> findByIdFunzTree(Long idTreeFunzione) {
		return entityManager.createQuery("SELECT ara FROM AbilitazioneDto ara "
				+ "WHERE ara.treeFunzionalitaDto.idTreeFunzione = :idTreeFunzione "
				, AbilitazioneDto.class)
			.setParameter("idTreeFunzione", idTreeFunzione)
			.getResultList();
	}
	
}
