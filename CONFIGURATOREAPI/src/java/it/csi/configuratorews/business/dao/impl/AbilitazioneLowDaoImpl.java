/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.RuoloUtenteDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.util.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Component
public class AbilitazioneLowDaoImpl extends EntityBaseLowDaoImpl<AbilitazioneDto, Long> implements AbilitazioneLowDao {

	@Override
	public Collection<AbilitazioneDto> findByRuoloUtenteAndApplicazione(RuoloUtenteDto ruoloUtenteDto, ApplicazioneDto applicazioneDto) {

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
		Query query = entityManager.createQuery("from " + abilitazioneDto.getClass().getName() + " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente "
				+ "AND t.applicazioneDto.id = :idApplicazione AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (t.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
		query.setParameter("idRuoloUtente", abilitazioneDto.getRuoloUtenteDto().getId());
		query.setParameter("idApplicazione", abilitazioneDto.getApplicazioneDto().getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtente(Long idApplicazione, Long idCollocazione,
			Long idFunzione, String codiceFiscaleUtente) {
		Query query = entityManager.createQuery("SELECT DISTINCT ara " + "FROM AbilitazioneDto ara "
				+ "JOIN ara.applicazioneDto ada2 JOIN ara.utenteCollocazioneDto aruc "
				+ "JOIN aruc.collocazioneDto atc JOIN ara.treeFunzionalitaDto arft JOIN arft.funzionalitaDto atf " + "WHERE ada2.id = :idApplicazione "
				+ "AND atc.colId = :idCollocazione " + "AND atf.idFunzione = :idFunzione " + "AND aruc.utenteDto.codiceFiscale = :codiceFiscaleUtente "
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
				+ " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (t.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00'))) ");
		query.setParameter("idRuoloUtente", ruoloUtenteDto.getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findByIdFunzioneAndAzienda(TreeFunzionalitaDto treeFunzionalitaDto, String codiceAzienda) {
		StringBuilder queryBuilder = new StringBuilder("from " + AbilitazioneDto.class.getName()
				+ " a WHERE a.treeFunzionalitaDto.idTreeFunzione = :idTreeFunzione AND "
				+ " ((:data BETWEEN a.dataInizioValidita AND a.dataFineValidita) OR (a.dataFineValidita IS NULL AND :data >= a.dataInizioValidita) "
				+ " AND  (:data BETWEEN a.ruoloUtenteDto.dataInizioValidita AND a.ruoloUtenteDto.dataFineValidita) OR (a.ruoloUtenteDto.dataFineValidita IS NULL AND :data >= a.ruoloUtenteDto.dataInizioValidita)"
				+ " AND (:data BETWEEN a.ruoloUtenteDto.utenteDto.dataInizioValidita AND a.ruoloUtenteDto.utenteDto.dataFineValidita) OR (a.ruoloUtenteDto.utenteDto.dataFineValidita IS NULL AND :data >= a.ruoloUtenteDto.utenteDto.dataInizioValidita)) ");
		if (codiceAzienda != null && !codiceAzienda.isEmpty()) {
			queryBuilder.append(" AND utenteCollocazioneDto.collocazioneDto.colCodAzienda = :codiceAzienda");
		}

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("idTreeFunzione", treeFunzionalitaDto.getIdTreeFunzione());
		query.setParameter("data", Utils.sysdate());

		if (codiceAzienda != null && !codiceAzienda.isEmpty()) {
			query.setParameter("codiceAzienda", codiceAzienda);
		}

		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniByApplicazioneAndIdTree(String codiceApplicazione, Long funzTreeId) {
		StringBuilder queryBuilder = new StringBuilder("from " + AbilitazioneDto.class.getName()
				+ " a WHERE a.treeFunzionalitaDto.idTreeFunzione = :idTreeFunzione AND "
				+ " ((:data BETWEEN a.dataInizioValidita AND a.dataFineValidita) OR (a.dataFineValidita IS NULL AND :data >= a.dataInizioValidita) "
				+ " AND  (:data BETWEEN a.ruoloUtenteDto.dataInizioValidita AND a.ruoloUtenteDto.dataFineValidita) OR (a.ruoloUtenteDto.dataFineValidita IS NULL AND :data >= a.ruoloUtenteDto.dataInizioValidita)"
				+ " AND (:data BETWEEN a.ruoloUtenteDto.utenteDto.dataInizioValidita AND a.ruoloUtenteDto.utenteDto.dataFineValidita) OR (a.ruoloUtenteDto.utenteDto.dataFineValidita IS NULL AND :data >= a.ruoloUtenteDto.utenteDto.dataInizioValidita)) ");
		queryBuilder.append(" and a.applicazioneDto.codice = :codiceApplicazione");

		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("idTreeFunzione", funzTreeId);
		query.setParameter("data", Utils.sysdate());
		query.setParameter("codiceApplicazione", codiceApplicazione);

		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniByRuoloByCollByAzByCF(String codiceRuolo, String codiceCollocazione, String codiceAzienda,
			String codiceFiscaleUtente) {
		Query query = entityManager.createQuery("SELECT ab " + "FROM AbilitazioneDto ab " + "JOIN ab.ruoloUtenteDto ru " + "JOIN ab.applicazioneDto app "
				+ "JOIN ab.utenteCollocazioneDto uc " + "JOIN ru.utenteDto u " + "JOIN ru.ruoloDto r " + "JOIN uc.collocazioneDto c "
				+ "WHERE u.codiceFiscale = :codiceFiscaleUtente " + "AND r.codice = :codiceRuolo " + "AND c.colCodice = :codiceCollocazione "
				+ "AND c.colCodAzienda = :codiceAzienda " + "AND u.id = ru.utenteDto " + "AND ru.ruoloDto = r.id " + "AND u.id = uc.utenteDto "
				+ "AND c.colId = uc.collocazioneDto " + "AND ab.ruoloUtenteDto = ru.id " + "AND ab.utenteCollocazioneDto = uc.id_utecol "
				+ "AND ab.applicazioneDto is not null " + "AND ab.applicazioneDto = app.id "
				+ "AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) "
				+ "AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) "
				+ "AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita))");
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("codiceCollocazione", codiceCollocazione);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("codiceFiscaleUtente", codiceFiscaleUtente);
		return query.getResultList();
	}

	@Override
	public ApplicazioneDto findApplicazioneByCodice(String codiceApplicazione) {
		Query query = entityManager.createQuery("SELECT a " + "FROM ApplicazioneDto a " + "where a.codice = :codiceApplicazione");
		query.setParameter("codiceApplicazione", codiceApplicazione);
		return (ApplicazioneDto) query.getSingleResult();
	}

	@Override
	public List<AbilitazioneDto> findApplicazioniByRuoloCollocazione(String codiceRuolo, String codiceCollocazione) {
		StringBuilder stringBuilder = new StringBuilder("SELECT ab FROM AbilitazioneDto ab " 
				+ "inner JOIN fetch ab.ruoloUtenteDto ru "
				+ "inner JOIN FETCH ab.applicazioneDto app " 
				+ "inner JOIN ru.ruoloDto r "
				+ "inner JOIN FETCH ru.utenteDto u ");
		if(codiceCollocazione!=null) {
			stringBuilder.append( 
				  "inner JOIN ab.utenteCollocazioneDto uc " 
				+ "inner JOIN uc.collocazioneDto c " );
		}
		stringBuilder.append( "WHERE r.codice = :codiceRuolo "
				+ "AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) "
				+ "AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita))");
		if(codiceCollocazione!=null) {
			stringBuilder.append( 
				  "AND c.colCodice = :codiceCollocazione "
				+ "AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		}
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", codiceRuolo);
		if(codiceCollocazione!=null) {
			query.setParameter("codiceCollocazione", codiceCollocazione);
		}
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findProfiliByRuoloCollocazione(String codiceRuolo, String codiceCollocazione) {
		StringBuilder stringBuilder = new StringBuilder(
				"SELECT ab FROM AbilitazioneDto ab " 
			  + "inner JOIN fetch ab.ruoloUtenteDto ru "
			  + "inner JOIN FETCH ab.treeFunzionalitaDto tree " 
			  + "inner JOIN FETCH tree.funzionalitaDto profilo " 
			  + "inner JOIN ru.ruoloDto r " 
			  + "inner JOIN FETCH ru.utenteDto u " 
			  + "inner JOIN FETCH ab.applicazioneDto applicazione " 
			  
				);
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	  
			    "inner JOIN ab.utenteCollocazioneDto uc "
			  + "inner JOIN uc.collocazioneDto c " 
			);
		} else {
			stringBuilder.append( 	  
				    "left JOIN fetch ab.utenteCollocazioneDto uc "
				  + "left JOIN fetch uc.collocazioneDto c " 
				);
		}
		stringBuilder.append( 	
				"WHERE r.codice = :codiceRuolo "
				+ "AND profilo.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice "
				+ "AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) "
				+ "AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita))");
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	 
				"AND c.colCodice = :codiceCollocazione " 
				+ "AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		}
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", codiceRuolo);
		if(codiceCollocazione!=null) {
			query.setParameter("codiceCollocazione", codiceCollocazione);
		}
		query.setParameter("fnz_tipo_codice", FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO);
		return query.getResultList();
	}
	
	
	@Override
	public List<AbilitazioneDto> findApplicazioniProfiliByUtenteRuoloCollocazione(String codiceFiscale, String codiceRuolo, String codiceCollocazione, String codiceAzienda,
			List<String> applicazioni) {
		StringBuilder stringBuilder = new StringBuilder(
				"SELECT ab FROM AbilitazioneDto ab " 
			  + "inner JOIN fetch ab.ruoloUtenteDto ru "
			  + "inner JOIN FETCH ab.treeFunzionalitaDto tree " 
			  + "inner JOIN FETCH tree.funzionalitaDto profilo " 
			  + "inner JOIN ru.ruoloDto r " 
			  + "inner JOIN FETCH ru.utenteDto u " 
			  + "inner JOIN FETCH ab.applicazioneDto applicazione " 
			  
				);
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	  
			    "inner JOIN ab.utenteCollocazioneDto uc "
			  + "inner JOIN uc.collocazioneDto c " 
			);
		} else {
			stringBuilder.append( 	  
				    "left JOIN fetch ab.utenteCollocazioneDto uc "
				  + "left JOIN fetch uc.collocazioneDto c " 
				);
		}
		stringBuilder.append( 	
				"WHERE r.codice = :codiceRuolo "
				+ "AND profilo.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice "
				+ "AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) "
				+ "AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita))");
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	 
				"AND c.colCodice = :codiceCollocazione " 
				+ "AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		}
		stringBuilder.append(" AND c.colCodAzienda =:codiceAzienda ");
		stringBuilder.append(" AND u.codiceFiscale =:codiceFiscale ");
		stringBuilder.append(" AND applicazione.codice in (:applicazioni) ");
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("applicazioni", applicazioni);
		if(codiceCollocazione!=null) {
			query.setParameter("codiceCollocazione", codiceCollocazione);
		}
		query.setParameter("fnz_tipo_codice", FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO);
		return query.getResultList();
	}
	@Override
	public List<String> findListaApplicazioni(String codiceFiscale, String codiceRuolo, String codiceCollocazione, String codiceAzienda) {
		StringBuilder stringBuilder = new StringBuilder(
				"SELECT distinct applicazione.codice FROM AbilitazioneDto ab " 
						+ "inner JOIN ab.ruoloUtenteDto ru "
						+ "inner JOIN ab.treeFunzionalitaDto tree " 
						+ "inner JOIN tree.funzionalitaDto profilo " 
						+ "inner JOIN ru.ruoloDto r " 
						+ "inner JOIN ru.utenteDto u " 
						+ "inner JOIN ab.applicazioneDto applicazione " 
						
				);
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	  
					"inner JOIN ab.utenteCollocazioneDto uc "
							+ "inner JOIN uc.collocazioneDto c " 
					);
		} else {
			stringBuilder.append( 	  
					"left JOIN ab.utenteCollocazioneDto uc "
							+ "left JOIN uc.collocazioneDto c " 
					);
		}
		stringBuilder.append( 	
				"WHERE r.codice = :codiceRuolo "
						+ "AND profilo.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice "
						+ "AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) "
						+ "AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita))");
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	 
					"AND c.colCodice = :codiceCollocazione " 
							+ "AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		}
		stringBuilder.append(" AND c.colCodAzienda =:codiceAzienda ");
		stringBuilder.append(" AND u.codiceFiscale =:codiceFiscale ");
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("codiceFiscale", codiceFiscale);
		if(codiceCollocazione!=null) {
			query.setParameter("codiceCollocazione", codiceCollocazione);
		}
		query.setParameter("fnz_tipo_codice", FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO);
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findByCodiceFiscaleUtenteAndCodiceRuolo(String codiceFiscale, String codiceRuolo, Integer offset, Integer limit,String codiceAzienda) {
		Query query = entityManager.createQuery(" SELECT distinct (ara) from " + new AbilitazioneDto().getClass().getName() + " as ara "
				+ " inner join ara.utenteCollocazioneDto as uc" 
				+ " inner join uc.collocazioneDto as col" 
				+ " inner join ara.ruoloUtenteDto as ru"
				+ " inner join ru.utenteDto as utente" 
				+ " inner join ru.ruoloDto as ruolo" 
				+ " WHERE " 
				+ " utente.codiceFiscale =:codiceFiscale "
				+ " AND col.colCodAzienda =:codiceAzienda "
		        //+ " AND col.flagAzienda = 'S' "
				+ " AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) OR (:data >= ara.dataInizioValidita AND ara.dataFineValidita IS NULL)) "
				+ " AND ara.dataCancellazione IS NULL "
				+ " AND ((:data BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) OR (:data >= uc.dataInizioValidita AND uc.dataFineValidita IS NULL)) "
				+ " AND uc.dataCancellazione IS NULL " + " AND ruolo.codice =:codiceRuolo "
				+ " AND ((:data BETWEEN col.dataInizioValidita AND col.dataFineValidita) OR (:data >= col.dataInizioValidita AND col.dataFineValidita IS NULL)) "
				+ " AND col.dataCancellazione IS NULL "
				+ " AND ((:data BETWEEN ruolo.dataInizioValidita AND ruolo.dataFineValidita) OR (:data >= ruolo.dataInizioValidita AND ruolo.dataFineValidita IS NULL)) "
				+ " AND ((:data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) OR (:data >= ru.dataInizioValidita AND ru.dataFineValidita IS NULL)) ");
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public Long countByCodiceFiscaleUtenteAndCodiceRuolo(String codiceFiscale, String codiceRuolo,String codiceAzienda) {
		Query query = entityManager.createQuery(" SELECT COUNT(distinct ara) from " + new AbilitazioneDto().getClass().getName() + " as ara "
				+ " inner join ara.utenteCollocazioneDto as uc" + " inner join uc.collocazioneDto as col" + " inner join ara.ruoloUtenteDto as ru"
				+ " inner join ru.utenteDto as utente" + " inner join ru.ruoloDto as ruolo" + " WHERE " + " utente.codiceFiscale =:codiceFiscale "
				+ " AND col.colCodAzienda =:codiceAzienda "
		        //+ " AND col.flagAzienda = 'S' "
				+ " AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) OR (:data >= ara.dataInizioValidita AND ara.dataFineValidita IS NULL)) "
				+ " AND ara.dataCancellazione IS NULL "
				+ " AND ((:data BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) OR (:data >= uc.dataInizioValidita AND uc.dataFineValidita IS NULL)) "
				+ " AND uc.dataCancellazione IS NULL " + " AND ruolo.codice =:codiceRuolo "
				+ " AND ((:data BETWEEN col.dataInizioValidita AND col.dataFineValidita) OR (:data >= col.dataInizioValidita AND col.dataFineValidita IS NULL)) "
				+ " AND col.dataCancellazione IS NULL "
				+ " AND ((:data BETWEEN ruolo.dataInizioValidita AND ruolo.dataFineValidita) OR (:data >= ruolo.dataInizioValidita AND ruolo.dataFineValidita IS NULL)) "
				+ " AND ((:data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) OR (:data >= ru.dataInizioValidita AND ru.dataFineValidita IS NULL)) ");
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("data", Utils.sysdate());
		Long count = (Long) query.getSingleResult();
		return count;
	}

	@Override
	public List<AbilitazioneDto> getAbilitazioniUtente(String codiceFiscale, 
			String ruoloCodice,
			String applicazioneCodice, 
			String collocazioneCodice,
			String profiloCodice, String funzionalitaCodice,String codiceAzienda) {
		StringBuilder queryBuilder = new StringBuilder(" SELECT ara from AbilitazioneDto as ara ")
				.append(" inner join ara.utenteCollocazioneDto as uc ")
				.append(" inner join uc.collocazioneDto as col ")
				.append(" inner join ara.ruoloUtenteDto as ru ")
				.append(" inner join ru.utenteDto as utente ")
				.append(" inner join ru.ruoloDto as ruolo ")
				.append(" inner join ara.applicazioneDto as app ");
		if (StringUtils.isNotBlank(profiloCodice)) {
			queryBuilder.append(" inner join ara.treeFunzionalitaDto as tree ")
			.append(" inner join tree.funzionalitaDto as proffunz ");
		}
		if (StringUtils.isNotBlank(funzionalitaCodice)) {
			queryBuilder.append(" inner join tree.funzionalitaTreePadreDto.funzionalitaDto as funz ");
		}
		queryBuilder.append(" WHERE ")
				.append(" utente.codiceFiscale =:codiceFiscale ")
				.append(" AND col.colCodAzienda =:codiceAzienda ")
				.append(" AND col.flagAzienda = 'S' ")
				.append(" AND ((:data BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) OR (:data >= ara.dataInizioValidita AND ara.dataFineValidita IS NULL)) ")
				.append(" AND ara.dataCancellazione IS NULL ")
				.append(" AND ((:data BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) OR (:data >= uc.dataInizioValidita AND uc.dataFineValidita IS NULL)) ")
				.append(" AND uc.dataCancellazione IS NULL ")
				.append(" AND ruolo.codice =:codiceRuolo ")
				.append(" AND col.colCodice=:collocazioneCodice ")
				.append(" AND ((:data BETWEEN col.dataInizioValidita AND col.dataFineValidita) OR (:data >= col.dataInizioValidita AND col.dataFineValidita IS NULL)) ")
				.append(" AND col.dataCancellazione IS NULL ")
				.append(" AND ((:data BETWEEN ruolo.dataInizioValidita AND ruolo.dataFineValidita) OR (:data >= ruolo.dataInizioValidita AND ruolo.dataFineValidita IS NULL)) ")
				.append(" AND ((:data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) OR (:data >= ru.dataInizioValidita AND ru.dataFineValidita IS NULL)) ")
				.append(" AND app.codice =:applicazioneCodice ");
		if (StringUtils.isNotBlank(profiloCodice)) {
			queryBuilder
					.append(" AND ((:data BETWEEN tree.dataInizioValidita AND tree.dataFineValidita) OR (:data >= tree.dataInizioValidita AND tree.dataFineValidita IS NULL)) ")
					.append(" AND tree.dataCancellazione IS NULL ")
					.append(" AND proffunz.codiceFunzione=:profiloCodice ")
					.append(" AND ((:data BETWEEN proffunz.dataInizioValidita AND proffunz.dataFineValidita) OR (:data >= proffunz.dataInizioValidita AND proffunz.dataFineValidita IS NULL)) ")
					.append(" AND proffunz.dataCancellazione IS NULL ")
					.append(" AND proffunz.applicazioneDto.codice=:applicazioneCodice ");
		}
		if (StringUtils.isNotBlank(funzionalitaCodice)) {
			queryBuilder
					.append(" AND ((:data BETWEEN tree.funzionalitaTreePadreDto.dataInizioValidita AND tree.funzionalitaTreePadreDto.dataFineValidita) "
							+ "OR (:data >= tree.funzionalitaTreePadreDto.dataInizioValidita AND tree.funzionalitaTreePadreDto.dataFineValidita IS NULL)) ")
					.append(" AND tree.funzionalitaTreePadreDto.dataCancellazione IS NULL ")
					.append(" AND funz.codiceFunzione=:funzionalitaCodice ")
					.append(" AND ((:data BETWEEN funz.dataInizioValidita AND funz.dataFineValidita) "
							+ "OR (:data >= funz.dataInizioValidita AND funz.dataFineValidita IS NULL)) ")
					.append(" AND funz.dataCancellazione IS NULL ")
					.append(" AND funz.applicazioneDto.codice=:applicazioneCodice ");
		}

		Query query = entityManager.createQuery(queryBuilder.toString());
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("applicazioneCodice", applicazioneCodice);
		query.setParameter("codiceRuolo", ruoloCodice);
		query.setParameter("collocazioneCodice", collocazioneCodice);
		query.setParameter("codiceAzienda", codiceAzienda);
		if (StringUtils.isNotBlank(profiloCodice)) {
			query.setParameter("profiloCodice", profiloCodice);
		}
		if (StringUtils.isNotBlank(funzionalitaCodice)) {
			query.setParameter("funzionalitaCodice", funzionalitaCodice);
		}
		query.setParameter("data", Utils.sysdate());

		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findAbilitazioniByUtente(Long idUtente) {
		StringBuilder queryBuilder = new StringBuilder(
				"SELECT ara from AbilitazioneDto as ara "
				+ "inner JOIN FETCH ara.ruoloUtenteDto as ruoloUtente "
				+ "inner JOIN FETCH ruoloUtente.ruoloDto as ruolo "
				+ "inner JOIN FETCH ruoloUtente.utenteDto as utente "
				+ "left JOIN FETCH ara.utenteCollocazioneDto as utenteCollocazione "
				+ "left JOIN FETCH utenteCollocazione.collocazioneDto as collocazione "
				+ "left JOIN FETCH ara.treeFunzionalitaDto as tree  "
				+ "left JOIN FETCH tree.funzionalitaDto as funzionalita  "
				+ "where  utente.id = :idUtente "
				+ "AND (funzionalita.idFunzione is null or funzionalita.tipoFunzionalitaDto.codiceTipoFunzione = 'PROF' ) "
				
				//date validita
				+ "and (funzionalita.idFunzione is null or "
				+" (funzionalita.dataInizioValidita < now() and (funzionalita.dataFineValidita > now() or funzionalita.dataFineValidita is null)) "
				+" )"
				+ "and (tree.idTreeFunzione is null or "
				+" (tree.dataInizioValidita < now() and (tree.dataFineValidita > now() or tree.dataFineValidita is null)) "
				+" )"
				+ "and (collocazione.colId is null or "
				+" (collocazione.dataInizioValidita < now() and (collocazione.dataFineValidita > now() or collocazione.dataFineValidita is null)) "
				+" )"
				+ "and (utenteCollocazione.id_utecol is null or "
				+" (utenteCollocazione.dataInizioValidita < now() and (utenteCollocazione.dataFineValidita > now() or utenteCollocazione.dataFineValidita is null)) "
				+" )"
				+ "and utente.dataInizioValidita < now() and (utente.dataFineValidita > now() or utente.dataFineValidita is null) "
				+ "and ruolo.dataInizioValidita < now() and (ruolo.dataFineValidita > now() or ruolo.dataFineValidita is null) "
				+ "and ruoloUtente.dataInizioValidita < now() and (ruoloUtente.dataFineValidita > now() or ruoloUtente.dataFineValidita is null) "
				+ "and ara.dataInizioValidita < now() and (ara.dataFineValidita > now() or ara.dataFineValidita is null) "
				);
		Query query = entityManager.createQuery(queryBuilder.toString());
		query.setParameter("idUtente", idUtente);
		return query.getResultList();

	}

	@Override
	public void cancellaAbilitazioniPreferenze(String codiceApplicazione, String codiceFunzionalita, String cfOperatore, Timestamp dataFineValidita) {
		Query query = entityManager.createNativeQuery("select from lcce.fnc_cancella_preferenze(:codiceApplicazione, :codiceFunzionalita, :cfOperatore, :dataFineValidita) ")
				.setParameter("codiceApplicazione", codiceApplicazione)
				.setParameter("codiceFunzionalita", codiceFunzionalita)
				.setParameter("cfOperatore", cfOperatore)
				.setParameter("dataFineValidita", dataFineValidita);
		query.getResultList();
         
	}

	@Override
	public List<AbilitazioneDto> findByCodiceProfiloAndApplicazione(String codiceProfilo, String codiceApplicazione) {
		
		StringBuilder queryBuilder = new StringBuilder(" SELECT ara from AbilitazioneDto as ara ");		
		queryBuilder.append(" inner join ara.treeFunzionalitaDto.funzionalitaDto as prof ");
		queryBuilder.append(" where prof.codiceFunzione = :codiceProfilo ");
		queryBuilder.append(" and prof.tipoFunzionalitaDto.codiceTipoFunzione = 'PROF' ");
		queryBuilder.append(" and prof.applicazioneDto.codice = :codiceApplicazione ");
		queryBuilder.append(" and prof.dataInizioValidita < now() and (prof.dataFineValidita > now() or prof.dataFineValidita is null) ");
		queryBuilder.append(" and ara.dataInizioValidita < now() and (ara.dataFineValidita > now() or ara.dataFineValidita is null) ");
		
		Query query = entityManager.createQuery(queryBuilder.toString());
		query.setParameter("codiceProfilo", codiceProfilo);
		query.setParameter("codiceApplicazione", codiceApplicazione);
		
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findApplicazioniByRuoloCollocazioneOrAzienda(String ruolo, String collocazione, String azienda) {
		
		StringBuilder stringBuilder = new StringBuilder("SELECT ab FROM AbilitazioneDto ab ");
		stringBuilder.append("inner JOIN fetch ab.ruoloUtenteDto ru ");
		stringBuilder.append("inner JOIN FETCH ab.applicazioneDto app ");
//		stringBuilder.append("inner JOIN ru.ruoloDto r ");
		stringBuilder.append("inner JOIN FETCH ru.utenteDto u ");
		stringBuilder.append("inner JOIN ab.utenteCollocazioneDto uc ");
		stringBuilder.append("inner JOIN uc.collocazioneDto c ");
		stringBuilder.append("WHERE ru.ruoloDto.codice = :codiceRuolo ");
		stringBuilder.append("AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) "
				+ "OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) ");
		stringBuilder.append("AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) "
				+ "OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita)) ");

		if (collocazione != null) {
			stringBuilder.append("AND c.colCodice = :codiceCollocazione ");
		} else {
			stringBuilder.append("AND c.colCodAzienda = :codiceAzienda ");
		}
		stringBuilder.append("AND ((now() > c.dataInizioValidita AND c.dataFineValidita is null) "
				+ "OR (now() BETWEEN c.dataInizioValidita AND c.dataFineValidita)) ");
		stringBuilder.append("AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) "
				+ "OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");

		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", ruolo);
		if (collocazione != null) {
			query.setParameter("codiceCollocazione", collocazione);
		} else {
			query.setParameter("codiceAzienda", azienda);
		}

		return query.getResultList();
	}
	
	@Override
	public List<AbilitazioneDto> findProfiliByRuoloCollocazioneOrAzienda(String codiceRuolo, String codiceCollocazione, String codiceAzienda) {
		
		StringBuilder stringBuilder = new StringBuilder("SELECT ab FROM AbilitazioneDto ab ");
		stringBuilder.append("inner JOIN fetch ab.ruoloUtenteDto ru ");
		stringBuilder.append("inner JOIN FETCH ab.treeFunzionalitaDto tree ");
		stringBuilder.append("inner JOIN FETCH tree.funzionalitaDto profilo ");
		stringBuilder.append("inner JOIN ru.ruoloDto r ");
		stringBuilder.append("inner JOIN FETCH ru.utenteDto u ");
		stringBuilder.append("inner JOIN FETCH ab.applicazioneDto applicazione ");
										
		if (codiceCollocazione != null) {
			stringBuilder.append("inner JOIN ab.utenteCollocazioneDto uc ");
			stringBuilder.append("inner JOIN uc.collocazioneDto c ");
		} else {
			stringBuilder.append("left JOIN fetch ab.utenteCollocazioneDto uc ");
			stringBuilder.append("left JOIN fetch uc.collocazioneDto c ");
		}
		
		stringBuilder.append("WHERE r.codice = :codiceRuolo ");
		stringBuilder.append("AND profilo.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
		stringBuilder.append("AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) "
				+ "OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) ");
		stringBuilder.append("AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) "
				+ "OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita)) ");
		
		if (codiceCollocazione != null) {
			stringBuilder.append("AND c.colCodice = :codiceCollocazione ");
		} else {
			stringBuilder.append("AND c.colCodAzienda =:codiceAzienda ");
		}
		stringBuilder.append("AND ((now() > c.dataInizioValidita AND c.dataFineValidita is null) "
				+ "OR (now() BETWEEN c.dataInizioValidita AND c.dataFineValidita)) ");
		stringBuilder.append("AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) "
				+ "OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", codiceRuolo);
		if (codiceCollocazione != null) {
			query.setParameter("codiceCollocazione", codiceCollocazione);
		} else {
			query.setParameter("codiceAzienda", codiceAzienda);
		}

		query.setParameter("fnz_tipo_codice", FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO);
		return query.getResultList();
	}

	@Override
	public List<AbilitazioneDto> findApplicazioniProfiliByUtenteRuoloCollocazioneOrAzienda(String codiceFiscale,
			String ruolo, String codiceCollocazione, String codiceAzienda, String codiceApplicazione) {

		StringBuilder stringBuilder = new StringBuilder(
				"SELECT ab FROM AbilitazioneDto ab " 
			  + "inner JOIN fetch ab.ruoloUtenteDto ru "
			  + "inner JOIN FETCH ab.treeFunzionalitaDto tree " 
			  + "inner JOIN FETCH tree.funzionalitaDto profilo " 
			  + "inner JOIN ru.ruoloDto r " 
			  + "inner JOIN FETCH ru.utenteDto u " 
			  + "inner JOIN FETCH ab.applicazioneDto applicazione ");
		
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	  
			    "inner JOIN ab.utenteCollocazioneDto uc "
			  + "inner JOIN uc.collocazioneDto c " 
			);
		} else {
			stringBuilder.append( 	  
			    "left JOIN fetch ab.utenteCollocazioneDto uc "
			  + "left JOIN fetch uc.collocazioneDto c " 
			);
		}
		stringBuilder.append( 	
			"WHERE r.codice = :codiceRuolo "
				+ "AND profilo.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice "
				+ "AND ((now() > ru.dataInizioValidita AND ru.dataFineValidita is null) OR (now() BETWEEN ru.dataInizioValidita AND ru.dataFineValidita)) "
				+ "AND ((now() > ab.dataInizioValidita AND ab.dataFineValidita is null) OR (now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita)) "
			);
		if(codiceCollocazione!=null) {
			stringBuilder.append( 	 
				"AND c.colCodice = :codiceCollocazione " 
				+ "AND ((now() > uc.dataInizioValidita AND uc.dataFineValidita is null) OR (now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita)) ");
		}
		
		stringBuilder.append(" AND c.colCodAzienda =:codiceAzienda ");
		stringBuilder.append(" AND u.codiceFiscale =:codiceFiscale ");
		stringBuilder.append(" AND applicazione.codice = :codiceApplicazione ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("codiceRuolo", ruolo);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("codiceApplicazione", codiceApplicazione);
		if(codiceCollocazione!=null) {
			query.setParameter("codiceCollocazione", codiceCollocazione);
		}
		query.setParameter("fnz_tipo_codice", FunzionalitaLowDaoImpl.CODICE_TIPO_PROFILO);
		
		return query.getResultList();
		
	}

}