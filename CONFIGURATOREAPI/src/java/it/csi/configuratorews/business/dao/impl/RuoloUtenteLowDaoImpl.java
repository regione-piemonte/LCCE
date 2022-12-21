/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.RuoloUtenteLowDao;
import it.csi.configuratorews.business.dto.RuoloUtenteDto;
import it.csi.configuratorews.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Component
public class RuoloUtenteLowDaoImpl extends EntityBaseLowDaoImpl<RuoloUtenteDto, Long> implements RuoloUtenteLowDao {

	@Override
	public Collection<RuoloUtenteDto> findByUtenteRuoloAndData(RuoloUtenteDto ruoloUtenteDto) {
		Query query = entityManager.createQuery("from " + ruoloUtenteDto.getClass().getName()
				+ " t WHERE t.utenteDto.id = :idUtente "
				+ "AND t.ruoloDto.id = :idRuolo AND :data BETWEEN t.dataInizioValidita AND t.dataFineValidita ");
		query.setParameter("idUtente", ruoloUtenteDto.getUtenteDto().getId());
		query.setParameter("idRuolo", ruoloUtenteDto.getRuoloDto().getId());
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}
	
	@Override
	public List<RuoloUtenteDto> findByCodiceRuoloAndDataValidita(String codiceRuolo) {
		String tabella = getTabName(new RuoloUtenteDto());
		Query query = entityManager.createQuery("from " + tabella + " t WHERE t.ruoloDto.codice = :codiceRuolo " +
				" AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (:data >= t.dataInizioValidita and t.dataFineValidita is null)) " +
				" AND ((:data BETWEEN t.ruoloDto.dataInizioValidita AND t.ruoloDto.dataFineValidita) OR (:data >= t.ruoloDto.dataInizioValidita and t.ruoloDto.dataFineValidita is null)) " +
				" AND ((:data BETWEEN t.utenteDto.dataInizioValidita AND t.utenteDto.dataFineValidita) OR (:data >= t.utenteDto.dataInizioValidita and t.utenteDto.dataFineValidita is null))");
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public Collection<RuoloUtenteDto> findByIdRuolo(Long idRuolo) {
		String tabella = getTabName(new RuoloUtenteDto());
		Query query = entityManager.createQuery("from " + tabella + " t WHERE t.ruoloDto.id = :idRuolo ");
		query.setParameter("idRuolo", idRuolo);
		return query.getResultList();
	}
	
	 public String getTabName(Object className) {
	        return className.getClass().getName();
	    }

	@Override
	public List<RuoloUtenteDto> findByCodiceFiscale(String codiceFiscale, Integer offset, Integer limit) {
		Query query = entityManager.createQuery("Select t from RuoloUtenteDto t "
				+ " inner join t.utenteDto as utente "
				+ " inner join t.ruoloDto as ruolo "
				+ " WHERE"
				+ " utente.codiceFiscale =:codiceFiscale " 
				+ " AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (:data >= t.dataInizioValidita and t.dataFineValidita is null)) " 
				+ " AND ((:data BETWEEN ruolo.dataInizioValidita AND ruolo.dataFineValidita) OR (:data >= ruolo.dataInizioValidita and ruolo.dataFineValidita is null)) " 
				+ " AND ((:data BETWEEN utente.dataInizioValidita AND utente.dataFineValidita) OR (:data >= utente.dataInizioValidita and utente.dataFineValidita is null))");
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("data", Utils.sysdate());
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public Long countByUtente(String codiceFiscale) {
		Query query = entityManager.createQuery("Select count(*) from RuoloUtenteDto t "
				+ " inner join t.utenteDto as utente "
				+ " inner join t.ruoloDto as ruolo "
				+ " WHERE"
				+ " utente.codiceFiscale =:codiceFiscale " 
				+ " AND ((:data BETWEEN t.dataInizioValidita AND t.dataFineValidita) OR (:data >= t.dataInizioValidita and t.dataFineValidita is null)) " 
				+ " AND ((:data BETWEEN ruolo.dataInizioValidita AND ruolo.dataFineValidita) OR (:data >= ruolo.dataInizioValidita and ruolo.dataFineValidita is null)) " 
				+ " AND ((:data BETWEEN utente.dataInizioValidita AND utente.dataFineValidita) OR (:data >= utente.dataInizioValidita and utente.dataFineValidita is null))");
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("data", Utils.sysdate());
		Long count = (Long) query.getSingleResult();
		return count;
	}
	
}