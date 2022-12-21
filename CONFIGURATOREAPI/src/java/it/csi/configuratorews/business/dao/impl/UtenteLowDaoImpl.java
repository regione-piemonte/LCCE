/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.UtenteDto;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class UtenteLowDaoImpl extends EntityBaseLowDaoImpl<UtenteDto, Long> implements UtenteLowDao {
	Logger log = Logger.getLogger(UtenteLowDaoImpl.class);

	@Override
	public UtenteDto salva(UtenteDto utenteDto) {
		return insert(utenteDto);
	}

	@Override
	public void modifica(UtenteDto utenteDto) {
		update(utenteDto);
	}

	@Override
	public Collection<UtenteDto> findByDataFineValidita(Date dataFineValitida) {
		Query query = entityManager.createQuery("FROM UtenteDto u WHERE u.dataInizioValidita=:dataInizioValidita");
		query.setParameter("dataInizioValidita", dataFineValitida);
		return query.getResultList();
	}

	@Override
	public Collection<UtenteDto> findByCodiceFiscaleValido(String codiceFiscale) {
		Query query = entityManager.createQuery(
				"FROM UtenteDto u WHERE u.codiceFiscale=:codiceFiscale and u.dataInizioValidita < now() and (u.dataFineValidita > now() or u.dataFineValidita is null)");
		query.setParameter("codiceFiscale", codiceFiscale);
		return query.getResultList();
	}
	
	@Override
	public List<UtenteDto> findByRuoloCollocazioneAzienda(String ruolo, String collocazione, String codiceAzienda,
			Integer limit, Integer offset) {
		
		StringBuilder stringBuilder = new StringBuilder("select distinct a.ruoloUtenteDto.utenteDto ");
		stringBuilder.append("from AbilitazioneDto a ");
		
		stringBuilder.append(" where a.ruoloUtenteDto.ruoloDto.codice = :ruolo ");				
		if (collocazione != null) {
			stringBuilder.append("and a.utenteCollocazioneDto.collocazioneDto.colCodice = :collocazione ");
		} else {
			stringBuilder.append("and a.utenteCollocazioneDto.collocazioneDto.colCodAzienda = :codiceAzienda ");
		}
		stringBuilder.append("and a.applicazioneDto != null ");
		
		stringBuilder.append("and a.ruoloUtenteDto.dataInizioValidita < now() "
				+ "and (a.ruoloUtenteDto.dataFineValidita > now() or a.ruoloUtenteDto.dataFineValidita is null) ");
		stringBuilder.append("and a.ruoloUtenteDto.utenteDto.dataInizioValidita < now() "
				+ "and (a.ruoloUtenteDto.utenteDto.dataFineValidita > now() or a.ruoloUtenteDto.utenteDto.dataFineValidita is null) ");
		stringBuilder.append("and a.utenteCollocazioneDto.dataInizioValidita < now() "
				+ "and (a.utenteCollocazioneDto.dataFineValidita > now() or a.utenteCollocazioneDto.dataFineValidita is null) ");
		stringBuilder.append("and a.dataInizioValidita < now() "
				+ "and (a.dataFineValidita > now() or a.dataFineValidita is null) ");
		
		stringBuilder.append("and a.dataCancellazione is null ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		
		query.setParameter("ruolo", ruolo);
		if (collocazione != null) {
			query.setParameter("collocazione", collocazione);
		} else {
			query.setParameter("codiceAzienda", codiceAzienda);
		}
		
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}
	
	@Override
	public Long countByRuoloCollocazioneAzienda(String ruolo, String collocazione, String codiceAzienda) {
		
		StringBuilder stringBuilder = new StringBuilder("select count(distinct a.ruoloUtenteDto.utenteDto) ");
		stringBuilder.append("from AbilitazioneDto a ");
		
		stringBuilder.append(" where a.ruoloUtenteDto.ruoloDto.codice = :ruolo ");				
		if (collocazione != null) {
			stringBuilder.append("and a.utenteCollocazioneDto.collocazioneDto.colCodice = :collocazione ");
		} else {
			stringBuilder.append("and a.utenteCollocazioneDto.collocazioneDto.colCodAzienda = :codiceAzienda ");
		}
		stringBuilder.append("and a.applicazioneDto != null ");
		
		stringBuilder.append("and a.ruoloUtenteDto.dataInizioValidita < now() "
				+ "and (a.ruoloUtenteDto.dataFineValidita > now() or a.ruoloUtenteDto.dataFineValidita is null) ");
		stringBuilder.append("and a.ruoloUtenteDto.utenteDto.dataInizioValidita < now() "
				+ "and (a.ruoloUtenteDto.utenteDto.dataFineValidita > now() or a.ruoloUtenteDto.utenteDto.dataFineValidita is null) ");
		stringBuilder.append("and a.utenteCollocazioneDto.dataInizioValidita < now() "
				+ "and (a.utenteCollocazioneDto.dataFineValidita > now() or a.utenteCollocazioneDto.dataFineValidita is null) ");
		stringBuilder.append("and a.dataInizioValidita < now() "
				+ "and (a.dataFineValidita > now() or a.dataFineValidita is null) ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		
		query.setParameter("ruolo", ruolo);
		if (collocazione != null) {
			query.setParameter("collocazione", collocazione);
		} else {
			query.setParameter("codiceAzienda", codiceAzienda);
		}
				
		Long count = (Long) query.getSingleResult();
		return count;
		
	}

	@Override
	public List<UtenteDto> findByRuoloCollocazione(String ruolo, String collocazione, String codiceAzienda,
			Integer limit, Integer offset) {
		
		StringBuilder stringBuilder = new StringBuilder("select distinct u FROM UtenteDto as u ");
		stringBuilder.append("inner join u.ruoloUtenteList as ruoloUtente ");
		stringBuilder.append("inner join u.utenteCollocazioneList as collocazioneDb ");
		stringBuilder.append("inner join ruoloUtente.abilitazioneList as abilitazioniDb ");
		stringBuilder.append("WHERE u.dataInizioValidita < now() "
				+ "and (u.dataFineValidita > now() or u.dataFineValidita is null) ");
		stringBuilder.append("AND ruoloUtente.ruoloDto.codice = :ruolo ");
		stringBuilder.append("and ruoloUtente.dataInizioValidita < now() "
				+ "and (ruoloUtente.dataFineValidita > now() or ruoloUtente.dataFineValidita is null) ");

		if (collocazione != null) {
			stringBuilder.append("and collocazioneDb.collocazioneDto.colCodice = :collocazione ");
		} else {
			stringBuilder.append("and collocazioneDb.collocazioneDto.colCodAzienda = :codiceAzienda ");
		}
		stringBuilder.append(" and collocazioneDb.collocazioneDto.dataInizioValidita < now() "
				+ "and (collocazioneDb.collocazioneDto.dataFineValidita > now() or collocazioneDb.collocazioneDto.dataFineValidita is null) ");
		
		stringBuilder.append(" and collocazioneDb.collocazioneDto.dataInizioValidita < now() "
				+ "and (collocazioneDb.collocazioneDto.dataFineValidita > now() or collocazioneDb.collocazioneDto.dataFineValidita is null) ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("ruolo", ruolo);
		if (collocazione != null) {
			query.setParameter("collocazione", collocazione);
		} else {
			query.setParameter("codiceAzienda", codiceAzienda);
		}
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		return query.getResultList();
	}

	@Override
	public Long countByRuoloCollocazione(String ruolo, String collocazione, String codiceAzienda) {
		StringBuilder stringBuilder = new StringBuilder(
				"select count(distinct u) FROM UtenteDto as u inner join u.ruoloUtenteList as ruoloUtente ");
		stringBuilder.append("inner join u.utenteCollocazioneList as collocazioneDb ");
		stringBuilder.append(
				"WHERE u.dataInizioValidita < now() and (u.dataFineValidita > now() or u.dataFineValidita is null) "
						+ "AND ruoloUtente.ruoloDto.codice= :ruolo "
						+ "and ruoloUtente.dataInizioValidita < now() and (ruoloUtente.dataFineValidita > now() or ruoloUtente.dataFineValidita is null) ");

		if (collocazione != null) {
			stringBuilder.append("and collocazioneDb.collocazioneDto.colCodice = :collocazione ");
		} else {
			stringBuilder.append("and collocazioneDb.collocazioneDto.colCodAzienda = :codiceAzienda ");
		}
		
		stringBuilder.append(" and collocazioneDb.collocazioneDto.dataInizioValidita < now() "
				+ "and (collocazioneDb.collocazioneDto.dataFineValidita > now() or collocazioneDb.collocazioneDto.dataFineValidita is null) ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		query.setParameter("ruolo", ruolo);
		if (collocazione != null) {
			query.setParameter("collocazione", collocazione);
		} else {
			query.setParameter("codiceAzienda", codiceAzienda);
		}
		
		Long count = (Long) query.getSingleResult();
		return count;
	}

	public UtenteDto findByCodiceFiscale(String codiceFiscale) {
		try {
			return entityManager.createQuery(
					"FROM UtenteDto u WHERE u.codiceFiscale=:codiceFiscale and u.dataInizioValidita < now() and (u.dataFineValidita > now() or u.dataFineValidita is null)",
					UtenteDto.class).setParameter("codiceFiscale", codiceFiscale).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<UtenteDto> findUtentiByApplicazioneAndAzienda(String applicazione, String azienda, Integer limit,
			Integer offset) {

		StringBuffer stringBuffer = new StringBuffer("select u FROM UtenteDto as u "
				+ "WHERE u.dataInizioValidita < now() and (u.dataFineValidita > now() or u.dataFineValidita is null) "
				+ "AND " + " (u.id in (  " + "		select u1.id from UtenteDto as u1 "
				+ "		inner join u1.ruoloUtenteList as ruoloUtente "
				+ "		inner join ruoloUtente.abilitazioneList as abilitazioni "
				+ "		inner join abilitazioni.applicazioneDto as applicazione ");
		if (azienda != null) {
			stringBuffer.append("		inner join u1.utenteCollocazioneList as utenteCollocazioni "
					+ "		inner join utenteCollocazioni.collocazioneDto as collocazione ");
		}
		stringBuffer.append("		where applicazione.codice = :applicazione ");
		stringBuffer.append(
				"		and ruoloUtente.dataInizioValidita < now() and (ruoloUtente.dataFineValidita > now() or ruoloUtente.dataFineValidita is null) ");
		if (azienda != null) {
			stringBuffer.append("	and collocazione.colCodAzienda = :azienda ");
			stringBuffer.append(
					"  and utenteCollocazioni.dataInizioValidita < now() and (utenteCollocazioni.dataFineValidita > now() or utenteCollocazioni.dataFineValidita is null) ");
		}
		stringBuffer.append(
				"  and abilitazioni.dataInizioValidita < now() and (abilitazioni.dataFineValidita > now() or abilitazioni.dataFineValidita is null) ");
		stringBuffer.append(")");// fine prima inner query
		stringBuffer.append(" or u.id in (" + "		select u2.id from UtenteDto as u2 "
				+ "		inner join u2.utenteCollocazioneList as utenteCollocazioni2 "
				+ "		inner join utenteCollocazioni2.abilitazioneList as abilitazioni2 "
				+ "		inner join abilitazioni2.applicazioneDto as applicazione2 "
				+ "		inner join utenteCollocazioni2.collocazioneDto as collocazione2 ");
		stringBuffer.append("		where applicazione2.codice = :applicazione ");
		stringBuffer.append(
				"  and utenteCollocazioni2.dataInizioValidita < now() and (utenteCollocazioni2.dataFineValidita > now() or utenteCollocazioni2.dataFineValidita is null) ");
		if (azienda != null) {
			stringBuffer.append("	and collocazione2.colCodAzienda = :azienda ");
		}
		stringBuffer.append(
				"  and abilitazioni2.dataInizioValidita < now() and (abilitazioni2.dataFineValidita > now() or abilitazioni2.dataFineValidita is null) ");
		stringBuffer.append(" ) ");// fine seconda inner query
		stringBuffer.append(" ) ");// fine external query
		log.debug("query complicata: " + stringBuffer.toString());
		Query query = entityManager.createQuery(stringBuffer.toString());
		query.setParameter("applicazione", applicazione);
		if (azienda != null) {
			query.setParameter("azienda", azienda);
		}

		if (offset != null)
			query.setFirstResult(offset);

		if (limit != null)
			query.setMaxResults(limit);

		return query.getResultList();
	}

	@Override
	public Long countUtentiByApplicazioneAndAzienda(String applicazione, String azienda) {
		StringBuffer stringBuffer = new StringBuffer("select count(*)  FROM UtenteDto as u "
				+ "WHERE u.dataInizioValidita < now() and (u.dataFineValidita > now() or u.dataFineValidita is null) "
				+ "AND " + " (u.id in (  " + "		select u1.id from UtenteDto as u1 "
				+ "		inner join u1.ruoloUtenteList as ruoloUtente "
				+ "		inner join ruoloUtente.abilitazioneList as abilitazioni "
				+ "		inner join abilitazioni.applicazioneDto as applicazione ");
		if (azienda != null) {
			stringBuffer.append("		inner join u1.utenteCollocazioneList as utenteCollocazioni "
					+ "		inner join utenteCollocazioni.collocazioneDto as collocazione ");
		}
		stringBuffer.append("		where applicazione.codice = :applicazione ");
		stringBuffer.append(
				"		and ruoloUtente.dataInizioValidita < now() and (ruoloUtente.dataFineValidita > now() or ruoloUtente.dataFineValidita is null) ");
		if (azienda != null) {
			stringBuffer.append("	and collocazione.colCodAzienda = :azienda ");
			stringBuffer.append(
					"  and utenteCollocazioni.dataInizioValidita < now() and (utenteCollocazioni.dataFineValidita > now() or utenteCollocazioni.dataFineValidita is null) ");
		}
		stringBuffer.append(")"// fine prima inner query
				+ " or u.id in (" + "		select u2.id from UtenteDto as u2 "
				+ "		inner join u2.utenteCollocazioneList as utenteCollocazioni2 "
				+ "		inner join utenteCollocazioni2.abilitazioneList as abilitazioni2 "
				+ "		inner join abilitazioni2.applicazioneDto as applicazione2 "
				+ "		inner join utenteCollocazioni2.collocazioneDto as collocazione2 ");
		stringBuffer.append("		where applicazione2.codice = :applicazione ");
		stringBuffer.append(
				"  and utenteCollocazioni2.dataInizioValidita < now() and (utenteCollocazioni2.dataFineValidita > now() or utenteCollocazioni2.dataFineValidita is null) ");
		if (azienda != null) {
			stringBuffer.append("	and collocazione2.colCodAzienda = :azienda ");
		}
		stringBuffer.append(" ) ");// fine seconda inner query
		stringBuffer.append(" ) ");// fine external query

		Query query = entityManager.createQuery(stringBuffer.toString());
		query.setParameter("applicazione", applicazione);
		if (azienda != null) {
			query.setParameter("azienda", azienda);
		}
		Long count = (Long) query.getSingleResult();
		return count;
	}

}
