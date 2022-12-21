/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.util.Utils;

@Component
public class CollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<CollocazioneDto, Long> implements CollocazioneLowDao {

    @Override
    public List<Long> findAziendeSanitarie(List<Long> idCollocazioni) {
        List<String> colCodAziende = entityManager.createQuery("SELECT c.colCodAzienda " +
                "FROM CollocazioneDto c " +
                "WHERE c.colId IN :idCollocazioni", String.class)
                .setParameter("idCollocazioni", idCollocazioni)
                .getResultList();

        return entityManager.createQuery("SELECT c.colId " +
                "FROM CollocazioneDto c " +
                "WHERE c.colCodice = '-' AND c.colCodAzienda IN :colCodAziende", Long.class)
                .setParameter("colCodAziende", colCodAziende)
                .getResultList();
    }

    @Override
    public List<Long> findSediSanitarie(List<Long> idAziendeSanitarie) {
        List<String> colCodAziende = entityManager.createQuery("SELECT c.colCodAzienda " +
                "FROM CollocazioneDto c " +
                "WHERE c.colId IN :idCollocazioni", String.class)
                .setParameter("idCollocazioni", idAziendeSanitarie)
                .getResultList();

        return entityManager.createQuery("SELECT c.colId " +
                "FROM CollocazioneDto c " +
                "WHERE c.colCodice <> '-' AND c.colCodAzienda IN :idAziendeSanitarie", Long.class)
                .setParameter("idAziendeSanitarie", colCodAziende)
                .getResultList();
    }

    @Override
    public Collection<CollocazioneDto> findByAziendaAndDataValidazione(String codiceAzienda) {
        Query query = entityManager.createQuery("from " + new CollocazioneDto().getClass().getName()
                + " c WHERE c.colCodAzienda = :codiceAzienda "
                + " AND ((:data BETWEEN c.dataInizioValidita AND c.dataFineValidita) OR (:data >= c.dataInizioValidita AND c.dataFineValidita IS NULL)) "
                + " AND c.flagAzienda = 'S' ");
        query.setParameter("codiceAzienda", codiceAzienda);
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }

	@Override
	public Collection<CollocazioneDto> findByCodice(String codiceCollocazione) {
		Query query = entityManager.createQuery("from " + new CollocazioneDto().getClass().getName()
                + " c WHERE c.colCodice = :codiceCollocazione "
                + " AND ((:data BETWEEN c.dataInizioValidita AND c.dataFineValidita) OR (:data >= c.dataInizioValidita AND c.dataFineValidita IS NULL))"
                + " AND c.dataCancellazione is null ");
        query.setParameter("codiceCollocazione", codiceCollocazione);
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
	}

	@Override
	public List<CollocazioneDto> findByAziendaAndStruttura(String codiceAzienda, String codiceStruttura, Integer limit,
			Integer offset) {
		StringBuffer sb = new StringBuffer();
		sb.append("from CollocazioneDto c WHERE c.colCodAzienda = :codiceAzienda ")
		.append(" AND ((:data BETWEEN c.dataInizioValidita AND c.dataFineValidita) OR (:data >= c.dataInizioValidita AND c.dataFineValidita IS NULL)) ");
		if(StringUtils.isNotBlank(codiceStruttura)) {
			sb.append(" AND c.codStruttura = :codiceStruttura ");
		}
		
		String queryString= sb.toString();
		Query query = entityManager.createQuery(queryString);
		query.setParameter("codiceAzienda", codiceAzienda);
		if(StringUtils.isNotBlank(codiceStruttura)) {
			query.setParameter("codiceStruttura", codiceStruttura);
		}
        query.setParameter("data", Utils.sysdate());
        query.setFirstResult(offset);
		query.setMaxResults(limit);
        return query.getResultList();
	}

	@Override
	public Long countByAziendaAndStruttura(String codiceAzienda, String codiceStruttura) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) from CollocazioneDto c WHERE c.colCodAzienda = :codiceAzienda ")
		.append(" AND ((:data BETWEEN c.dataInizioValidita AND c.dataFineValidita) OR (:data >= c.dataInizioValidita AND c.dataFineValidita IS NULL)) ");
		if(StringUtils.isNotBlank(codiceStruttura)) {
			sb.append(" AND c.codStruttura = :codiceStruttura ");
		}
		
		String queryString= sb.toString();
		Query query = entityManager.createQuery(queryString);
		query.setParameter("codiceAzienda", codiceAzienda);
		if(StringUtils.isNotBlank(codiceStruttura)) {
			query.setParameter("codiceStruttura", codiceStruttura);
		}
		query.setParameter("data", Utils.sysdate());
		Long count = (Long) query.getSingleResult();
		return count;
	}
	@Override
	public List<CollocazioneDto> findByStrutturaAndDataValidazione(String codiceStruttura){
		  Query query = entityManager.createQuery("from " + new CollocazioneDto().getClass().getName()
	                + " c WHERE c.codStruttura = :codiceStruttura"
	                + " AND ((:data BETWEEN c.dataInizioValidita AND c.dataFineValidita) OR (:data >= c.dataInizioValidita AND c.dataFineValidita IS NULL)) ");
	        query.setParameter("codiceStruttura", codiceStruttura);
	        query.setParameter("data", Utils.sysdate());
	        return query.getResultList();
	}

	@Override
	public Collection<CollocazioneDto> findByCodiceAndAzienda(String codiceCollocazione, String codiceAzienda) {
		
		Query query = entityManager.createQuery("from " + new CollocazioneDto().getClass().getName()
				+ " c WHERE c.colCodice = :codiceCollocazione "
				+ " AND ((:data BETWEEN c.dataInizioValidita AND c.dataFineValidita) OR (:data >= c.dataInizioValidita AND c.dataFineValidita IS NULL))"
				+ " AND c.dataCancellazione is null "
				+ " AND c.colCodAzienda = :codiceAzienda ");
		
		query.setParameter("codiceCollocazione", codiceCollocazione);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("data", Utils.sysdate());
		
		return query.getResultList();
	}

	@Override
	public List<CollocazioneDto> findByRuoloAziendaAndUtente(String codiceAzienda, String ruolo, String cf, Integer limit, Integer offset) {

		Query query = entityManager.createQuery("SELECT distinct uc.collocazioneDto "
				+ " FROM UtenteCollocazioneDto uc, "
				+ " AbilitazioneDto ab, " 
				+ " RuoloUtenteDto ur "				
				+ " WHERE ur.utenteDto.id = uc.utenteDto.id "
				+ " AND ((now() BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) OR "
				+ " (now() >= uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL)) "
				+ " AND ((now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) OR "
				+ " (now() >= uc.dataInizioValidita AND uc.dataFineValidita IS NULL)) "
				+ " AND ((now() BETWEEN ur.ruoloDto.dataInizioValidita AND ur.ruoloDto.dataFineValidita) OR "
				+ " (now() >= ur.ruoloDto.dataInizioValidita AND ur.ruoloDto.dataFineValidita IS NULL)) "
				+ " AND ((now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita) OR "
				+ " (now() >= ab.dataInizioValidita AND ab.dataFineValidita IS NULL)) "
				+ " AND uc.collocazioneDto.dataCancellazione is null "
				+ " AND uc.collocazioneDto.colCodAzienda = :codiceAzienda "
				+ " AND ur.ruoloDto.codice = :ruolo "
				+ " AND uc.utenteDto.codiceFiscale = :cf "
				+ " AND ab.ruoloUtenteDto.id = ur.id "
				+ " AND ab.utenteCollocazioneDto.id = uc.id ");
		
		query.setParameter("ruolo", ruolo);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("cf", cf);
				
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		return query.getResultList();
		
	}

	@Override
	public Long countByRuoloAziendaAndUtente(String codiceFiscale, String ruoloCodice, String codiceAzienda) {

		Query query = entityManager.createQuery("SELECT COUNT(distinct uc.collocazioneDto) "
				+ " FROM UtenteCollocazioneDto uc, "
				+ " AbilitazioneDto ab, " 
				+ " RuoloUtenteDto ur "				
				+ " WHERE ur.utenteDto.id = uc.utenteDto.id "
				+ " AND ((now() BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) OR "
				+ " (now() >= uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL)) "
				+ " AND ((now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) OR "
				+ " (now() >= uc.dataInizioValidita AND uc.dataFineValidita IS NULL)) "
				+ " AND ((now() BETWEEN ur.ruoloDto.dataInizioValidita AND ur.ruoloDto.dataFineValidita) OR "
				+ " (now() >= ur.ruoloDto.dataInizioValidita AND ur.ruoloDto.dataFineValidita IS NULL)) "
				+ " AND ((now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita) OR "
				+ " (now() >= ab.dataInizioValidita AND ab.dataFineValidita IS NULL)) "
				+ " AND uc.collocazioneDto.dataCancellazione is null "
				+ " AND uc.collocazioneDto.colCodAzienda = :codiceAzienda "
				+ " AND ur.ruoloDto.codice = :ruolo "
				+ " AND uc.utenteDto.codiceFiscale = :cf "
				+ " AND ab.ruoloUtenteDto.id = ur.id "
				+ " AND ab.utenteCollocazioneDto.id = uc.id ");
		
		query.setParameter("ruolo", ruoloCodice);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("cf", codiceFiscale);
						
		Long count = (Long) query.getSingleResult();
		return count;
		
	}

	@Override
	public String getCodAziendaByRuoloCollAndCF(String codiceFiscale, String codiceCollocazione, String codiceRuolo,
			String codiceApplicazione) {
		
		try{
			Query query = entityManager.createQuery("SELECT distinct uc.collocazioneDto.colCodAzienda "
					+ " FROM UtenteCollocazioneDto uc, "
					+ " AbilitazioneDto ab, " 
					+ " RuoloUtenteDto ur "				
					+ " WHERE ur.utenteDto.id = uc.utenteDto.id "
					+ " AND ((now() BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) OR "
					+ " (now() >= uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL)) "
					+ " AND ((now() BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) OR "
					+ " (now() >= uc.dataInizioValidita AND uc.dataFineValidita IS NULL)) "
					+ " AND ((now() BETWEEN ur.ruoloDto.dataInizioValidita AND ur.ruoloDto.dataFineValidita) OR "
					+ " (now() >= ur.ruoloDto.dataInizioValidita AND ur.ruoloDto.dataFineValidita IS NULL)) "
					+ " AND ((now() BETWEEN ab.dataInizioValidita AND ab.dataFineValidita) OR "
					+ " (now() >= ab.dataInizioValidita AND ab.dataFineValidita IS NULL)) "
					+ " AND uc.collocazioneDto.dataCancellazione is null "
					+ " AND uc.collocazioneDto.colCodice = :codiceCollocazione "
					+ " AND ur.ruoloDto.codice = :codiceRuolo "
					+ " AND uc.utenteDto.codiceFiscale = :codiceFiscale "
					+ " AND ab.applicazioneDto.codice = :codiceApplicazione "
					+ " AND ab.ruoloUtenteDto.id = ur.id "				
					+ " AND ab.utenteCollocazioneDto.id = uc.id ");
			
			query.setParameter("codiceRuolo", codiceRuolo);
			query.setParameter("codiceCollocazione", codiceCollocazione);
			query.setParameter("codiceFiscale", codiceFiscale);
			query.setParameter("codiceApplicazione", codiceApplicazione);		
				 
			String az = (String) query.getSingleResult();

			return az;
		
		} catch (NoResultException nre) {
			return null;
		}		
		
	}
	
}
