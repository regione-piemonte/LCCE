/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteCollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteCollocazioneLowDao;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

@Component
public class UtenteCollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<UtenteCollocazioneDto, Long> implements UtenteCollocazioneLowDao {

    @Override
    public List<UtenteCollocazioneDto> findByUtenteDto(UtenteDto utenteDto) {
        Query query = entityManager.createQuery("FROM UtenteCollocazioneDto u WHERE u.utenteDto = :utenteDto");
        query.setParameter("utenteDto", utenteDto);
        return query.getResultList();
    }

	@Override
	public List<UtenteCollocazioneDto> findByIdRuoloAndIdAzienda(Long idRuolo, Long idAzienda) {
		return entityManager.createQuery(
				"Select uc from UtenteCollocazioneDto uc join uc.utenteDto u join u.ruoloUtenteList ru " +
				"where ru.ruoloDto.id = :idRuolo " +
				"and uc.collocazioneDto.colCodAzienda IN " +
				"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
				"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) " +
				"AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ", 
                UtenteCollocazioneDto.class)
				.setParameter("idRuolo", idRuolo)
				.setParameter("idAzienda", idAzienda)
				.getResultList();
				
	}

	@Override
	public List<UtenteCollocazioneDto> findByIdRuoloAndIdCollocazione(Long idRuolo, Long idCollocazione) {
		return entityManager.createQuery(
				"Select uc from UtenteCollocazioneDto uc join uc.utenteDto u join u.ruoloUtenteList ru " +
				"where ru.ruoloDto.id = :idRuolo " +
				"and uc.collocazioneDto.colId = :idCollocazione " +
				"AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ", 
                UtenteCollocazioneDto.class)
				.setParameter("idRuolo", idRuolo)
				.setParameter("idCollocazione", idCollocazione)
				.getResultList();
	}

	@Override
	public List<UtenteCollocazioneDto> ricercaUtenteCollocazioneAbilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol, Integer numeroPagina, Integer numeroElementi) {
		String query = "Select distinct uc from UtenteCollocazioneDto uc join uc.utenteDto u join u.ruoloUtenteList ru " +
					   "left join uc.abilitazioneList ara " +
					   "where ru.ruoloDto.id = :idRuolo " ;
		if(idCollocazione == null)
			query += "and uc.collocazioneDto.colCodAzienda IN " +
					"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
					"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
	                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) ";
		else
			query += "and uc.collocazioneDto.colId = :idCollocazione ";
		
		if(idSol != null) {
			query += "and ara.applicazioneDto.id = :idApplicazione "
				   + "and ((CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
				   + "or (ara.dataFineValidita IS NULL AND (CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ";
		}
		query += "AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ";
		
		TypedQuery<UtenteCollocazioneDto> createQuery = entityManager.createQuery(query, UtenteCollocazioneDto.class);
		createQuery.setParameter("idRuolo", idRuolo);
		if(idCollocazione == null) 
			createQuery.setParameter("idAzienda", idAzienda);
		else 
			createQuery.setParameter("idCollocazione", idCollocazione);
		if(idSol != null) 
			createQuery.setParameter("idApplicazione", idSol);
		
		createQuery.setFirstResult((numeroPagina - 1) * numeroElementi);
		createQuery.setMaxResults(numeroElementi);
		
		return createQuery.getResultList();
	}
	
	@Override
	public Long countRicercaUtenteCollocazioneAbilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol) {
		String query = "Select count(distinct uc) from UtenteCollocazioneDto uc join uc.utenteDto u join u.ruoloUtenteList ru " +
					   "left join uc.abilitazioneList ara " +
					   "where ru.ruoloDto.id = :idRuolo " ;
		if(idCollocazione == null)
			query += "and uc.collocazioneDto.colCodAzienda IN " +
					"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
					"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
	                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) ";
		else
			query += "and uc.collocazioneDto.colId = :idCollocazione ";
		
		if(idSol != null) {
			query += "and ara.applicazioneDto.id = :idApplicazione "
				   + "and ((CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
				   + "or (ara.dataFineValidita IS NULL AND (CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ";
		}
		query += "AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ";
		
		TypedQuery<Long> createQuery = entityManager.createQuery(query, Long.class);
		createQuery.setParameter("idRuolo", idRuolo);
		if(idCollocazione == null) 
			createQuery.setParameter("idAzienda", idAzienda);
		else 
			createQuery.setParameter("idCollocazione", idCollocazione);
		if(idSol != null) 
			createQuery.setParameter("idApplicazione", idSol);
				
		return createQuery.getSingleResult();
	}
	
	@Override
	public List<AbilitazioneDto> ricercaUtenteCollocazioneDisabilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol, Integer numeroPagina, Integer numeroElementi) {
		String query = "Select distinct ara from AbilitazioneDto ara join ara.ruoloUtenteDto ru  join ru.utenteDto u " +
					   " join ara.utenteCollocazioneDto uc "
					   + " WHERE "+
					   " ara.applicazioneDto.id = :idApplicazione "
					   + "and ((CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
					   + "or (ara.dataFineValidita IS NULL AND (CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ";
		
		if(idCollocazione == null)
			query += " and uc.collocazioneDto.colCodAzienda IN " +
					"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
					"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
	                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) ";
		else
			query += "and uc.collocazioneDto.colId = :idCollocazione ";
		
		if(idRuolo != null) {
			query += "AND ru.ruoloDto.id = :idRuolo "+
					"AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
	                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ";
		}
		query += "AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) ";
                
		
		TypedQuery<AbilitazioneDto> createQuery = entityManager.createQuery(query, AbilitazioneDto.class);
		if(idRuolo!=null) {
			createQuery.setParameter("idRuolo", idRuolo);
			
		}
		if(idCollocazione == null) {
			
			createQuery.setParameter("idAzienda", idAzienda);
		}
		else {
			
			createQuery.setParameter("idCollocazione", idCollocazione);
		}
		
			createQuery.setParameter("idApplicazione", idSol);
		
		createQuery.setFirstResult((numeroPagina - 1) * numeroElementi);
		createQuery.setMaxResults(numeroElementi);
		
		return createQuery.getResultList();
	}
	
	@Override
	public Long countRicercaUtenteCollocazioneDisabilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol) {
		String query = "Select count(distinct ara)  from AbilitazioneDto ara join ara.ruoloUtenteDto ru  join ru.utenteDto u " +
				   " join ara.utenteCollocazioneDto uc "
				   + " WHERE "+
				   " ara.applicazioneDto.id = :idApplicazione "
				   + "and ((CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
				   + "or (ara.dataFineValidita IS NULL AND (CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ";
		
		if(idCollocazione == null)
			query += "and uc.collocazioneDto.colCodAzienda IN " +
					"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
					"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
	                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) ";
		else
			query += "and uc.collocazioneDto.colId = :idCollocazione ";
		
		if(idRuolo != null) {
			query += " AND ru.ruoloDto.id = :idRuolo "+
					"AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
	                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ";
		}
		query += "AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) ";
		
		TypedQuery<Long> createQuery = entityManager.createQuery(query, Long.class);
		if(idRuolo!=null) {
			createQuery.setParameter("idRuolo", idRuolo);
			
		}
		if(idCollocazione == null) {
			
			createQuery.setParameter("idAzienda", idAzienda);
		}
		else {
			
			createQuery.setParameter("idCollocazione", idCollocazione);
		}
		
			createQuery.setParameter("idApplicazione", idSol);
				
		return createQuery.getSingleResult();
	}

	@Override
	public List<Long> ricercaIDUtenteCollocazioneAbilitazioneMassiva(Long idRuolo, Long idAzienda, Long idCollocazione,
			Long idSol) {
		String query = "Select distinct uc.id_utecol from UtenteCollocazioneDto uc join uc.utenteDto u join u.ruoloUtenteList ru " +
				   "left join uc.abilitazioneList ara " +
				   "where ru.ruoloDto.id = :idRuolo " ;
	if(idCollocazione == null)
		query += "and uc.collocazioneDto.colCodAzienda IN " +
				"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
				"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
             "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
             "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
             "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) ";
	else
		query += "and uc.collocazioneDto.colId = :idCollocazione ";
	
	if(idSol != null) {
		query += "and ara.applicazioneDto.id = :idApplicazione "
			   + "and ((CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
			   + "or (ara.dataFineValidita IS NULL AND (CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ";
	}
	query += "AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
         "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
         "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
         "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
         "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
         "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
         "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
         "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
         "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
         "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
         "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
         "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
         "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
         "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
         "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
         "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
         "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
         "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
         "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
         "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ";
	
	TypedQuery<Long> createQuery = entityManager.createQuery(query, Long.class);
	createQuery.setParameter("idRuolo", idRuolo);
	if(idCollocazione == null) 
		createQuery.setParameter("idAzienda", idAzienda);
	else 
		createQuery.setParameter("idCollocazione", idCollocazione);
	if(idSol != null) 
		createQuery.setParameter("idApplicazione", idSol);
	
		
	return createQuery.getResultList();
}

	@Override
	public List<Long> ricercaIDUtenteCollocazioneDisabilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol) {

		String query = "Select distinct ara.id from AbilitazioneDto ara join ara.ruoloUtenteDto ru  join ru.utenteDto u " +
					   " join ara.utenteCollocazioneDto uc "
					   + " WHERE "+
					   " ara.applicazioneDto.id = :idApplicazione "
					   + "and ((CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND ara.dataFineValidita) "
					   + "or (ara.dataFineValidita IS NULL AND (CURRENT_TIMESTAMP BETWEEN ara.dataInizioValidita AND '9999-12-31 00:00:00'))) ";
		
		if(idCollocazione == null)
			query += " and uc.collocazioneDto.colCodAzienda IN " +
					"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
					"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
	                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) ";
		else
			query += "and uc.collocazioneDto.colId = :idCollocazione ";
		
		if(idRuolo != null) {
			query += "AND ru.ruoloDto.id = :idRuolo "+
					"AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
	                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
	                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
	                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ";
		}
		query += "AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) ";
                
		
		TypedQuery<Long> createQuery = entityManager.createQuery(query, Long.class);
		if(idRuolo!=null) {
			createQuery.setParameter("idRuolo", idRuolo);
			
		}
		if(idCollocazione == null) {
			
			createQuery.setParameter("idAzienda", idAzienda);
		}
		else {
			
			createQuery.setParameter("idCollocazione", idCollocazione);
		}
		
			createQuery.setParameter("idApplicazione", idSol);
		
		return createQuery.getResultList();
	
}

}