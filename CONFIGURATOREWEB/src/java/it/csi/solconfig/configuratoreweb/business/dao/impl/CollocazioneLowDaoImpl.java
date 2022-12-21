/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.CollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AziendaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollocazioneLowDaoImpl extends EntityBaseLowDaoImpl<CollocazioneDto, Long> implements CollocazioneLowDao {

    @Override
	public List<CollocazioneDTO> findAllAziendeFromSuperUser() {
//		   return entityManager.createQuery(
//	                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
//	                        "c.colId, c.colDescAzienda, c.colCodAzienda, CASE WHEN c.colCodice = '-' THEN true ELSE false END) " +
//	                        "FROM CollocazioneDto c " +
//	                        "WHERE c.colCodice = '-' AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
//	                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
//	                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
//	                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))", CollocazioneDTO.class)
//	                		.getResultList();

		
		
		return entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, a.descAzienda, c.colCodAzienda, CASE WHEN c.flagAzienda = 'S' THEN true ELSE false END) " +
                        "FROM CollocazioneDto c, AziendaDto a " +
                        " WHERE c.colCodAzienda = a.codAzienda "
//                        " WHERE c.colCodAzienda IN " 
//                        + "("
//                        + "SELECT  a.codAzienda "
//                        + "FROM AziendaDto a "
//                        + " WHERE "
                        + "AND ((CURRENT_TIMESTAMP BETWEEN a.dataInizioValidita AND a.dataFineValidita) " 
                        + "OR (CURRENT_TIMESTAMP > a.dataInizioValidita AND a.dataFineValidita IS NULL)  "
                        + "OR (CURRENT_TIMESTAMP < a.dataFineValidita AND a.dataInizioValidita IS NULL) "
                        + "OR (a.dataInizioValidita IS NULL AND a.dataFineValidita IS NULL))"
//                        + ") "
                        + "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) AND c.flagAzienda = 'S' " +
                        "order by c.colCodAzienda", CollocazioneDTO.class)
						.getResultList();



	}

    @Override
    @SuppressWarnings("unchecked")
    public List<CollocazioneDTO> findAllAziendeFromOperatore(String codiceFiscale,String codiceFiscaleOperatore) {
        return entityManager.createQuery(
        		 "SELECT  new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
	                        "c.colId, a.descAzienda, c.colCodAzienda, CASE WHEN c.flagAzienda = 'S' THEN true ELSE false END) " +
	                        "FROM CollocazioneDto c, AziendaDto a, VisibilitaAziendaDto v " +
	                        "WHERE c.flagAzienda = 'S' AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
	                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) AND " +
	                        "c.colCodAzienda = a.codAzienda AND " +
	                        "v.utenteDto.codiceFiscale = :codFiscOperatore AND " +
	                        "v.aziendaDto.idAzienda = a.idAzienda AND "
//                        + " c.colCodAzienda IN ("
//                        + "	SELECT a.codAzienda "
//                        + " From AziendaDto a, VisibilitaAziendaDto v"
//                        + " WHERE v.utenteDto.codiceFiscale=:codFiscOperatore "
//                        + " and v.aziendaDto.idAzienda=a.idAzienda AND "
                        + " ((CURRENT_TIMESTAMP BETWEEN a.dataInizioValidita AND a.dataFineValidita) " 
                        + "OR (CURRENT_TIMESTAMP > a.dataInizioValidita AND a.dataFineValidita IS NULL)  "
                        + "OR (CURRENT_TIMESTAMP < a.dataFineValidita AND a.dataInizioValidita IS NULL) "
                        + "OR (a.dataInizioValidita IS NULL AND a.dataFineValidita IS NULL)) AND "
                        + " ((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita) " 
                        + "OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL)  "
                        + "OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL) "
                        + "OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL)) " 
//                        + ")"
                        + "order by c.colCodAzienda"
                        , CollocazioneDTO.class)
                .unwrap(org.hibernate.Query.class)
                //.setString("codiceFiscale", codiceFiscale)
                //.setString("codice", ConstantsWebApp.APPL_CONF)
                .setString("codFiscOperatore", codiceFiscaleOperatore)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CollocazioneDTO> findAziendaByCodiceOrDescrizioneFromSuperUser(String codiceAzienda, String descrizioneAzienda, String codice, String descrizione) {
        return entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda))" +
                        "FROM CollocazioneDto c " +
                        "WHERE c.colCodAzienda=:codiceAzienda " + //AND c.colDescAzienda=:descrizioneAzienda  rimosso controllo desc azienda perche' risulta diversa per le strutture jira solconfig30
                        "AND c.flagAzienda IS NULL " +
                        "AND ((:codice is null or lower(c.colCodice) LIKE CONCAT('%', :codice, '%')) " +
                        "AND (:descrizione is null or lower(c.colDescrizione) LIKE CONCAT('%', :descrizione, '%'))) " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))", CollocazioneDTO.class)
                .unwrap(org.hibernate.Query.class)
                .setString("codiceAzienda", codiceAzienda)
                .setString("codice", Optional.ofNullable(codice).map(String::toLowerCase).orElse(null))
                .setString("descrizione", Optional.ofNullable(descrizione).map(String::toLowerCase).orElse(null))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CollocazioneDTO> findAziendaByCodiceOrDescrizioneFromOperatore(String codiceAzienda, String descrizioneAzienda, String codice, String descrizione, String codiceFiscale) {
        return entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda))" +
                        "FROM CollocazioneDto c " +
                        "WHERE c.colCodAzienda=:codiceAzienda " + //AND c.colDescAzienda=:descrizioneAzienda  rimosso controllo desc azienda perche' risulta diversa per le strutture jira solconfig30
                        "AND c.flagAzienda IS NULL " +
                        "AND ((:codice is null or lower(c.colCodice) LIKE CONCAT('%', :codice, '%')) " +
                        "AND (:descrizione is null or lower(c.colDescrizione) LIKE CONCAT('%', :descrizione, '%'))) " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) AND "
                        + " c.colCodAzienda IN ("
                        + "	SELECT a.codAzienda "
                        + " From AziendaDto a, VisibilitaAziendaDto v"
                        + " WHERE v.utenteDto.codiceFiscale=:codFiscOperatore "
                        + " and v.aziendaDto.idAzienda=a.idAzienda "
//                        + " ((CURRENT_TIMESTAMP BETWEEN a.dataInizioValidita AND a.dataFineValidita) " 
//                        + "OR (CURRENT_TIMESTAMP > a.dataInizioValidita AND a.dataFineValidita IS NULL)  "
//                        + "OR (CURRENT_TIMESTAMP < a.dataFineValidita AND a.dataInizioValidita IS NULL) "
//                        + "OR (a.dataInizioValidita IS NULL AND a.dataFineValidita IS NULL)) AND "
//                        + " ((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita) " 
//                        + "OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL)  "
//                        + "OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL) "
//                        + "OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL))" 
                        + ")"
                        , CollocazioneDTO.class)
                .unwrap(org.hibernate.Query.class)
                .setString("codiceAzienda", codiceAzienda)
                .setString("codFiscOperatore", codiceFiscale)
                .setString("codice", Optional.ofNullable(codice).map(String::toLowerCase).orElse(null))
                .setString("descrizione", Optional.ofNullable(descrizione).map(String::toLowerCase).orElse(null))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CollocazioneDTO> findAziendaByIdFromSuperUser(List<Long> ids) {
        return entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda)) " +
                        "FROM CollocazioneDto c " +
                        "WHERE c.colId IN :ids " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))", CollocazioneDTO.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CollocazioneDTO> findAziendaByIdFromOperatore(List<Long> ids, String codiceFiscale) {
        return entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda)) " +
                        "FROM UtenteDto u JOIN u.utenteCollocazioneList uc JOIN uc.collocazioneDto c " +
                        "WHERE u.codiceFiscale = :codiceFiscale AND c.colId IN :ids " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))", CollocazioneDTO.class)
                .setParameter("ids", ids)
                .setParameter("codiceFiscale", codiceFiscale)
                .getResultList();
    }

    @Override
    public List<CollocazioneDTO> findByIdUtenteFromSuperUser(Long idUtente) {
        return entityManager.createQuery(
                "SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda), " +
                        //"CASE WHEN (uc.flagConfiguratore = 'S' AND u.flagConfiguratore = 'S' AND uc.colFonteId IS NULL) THEN true ELSE false END) " +
                        "CASE WHEN (uc.flagConfiguratore = 'S' AND uc.colFonteId IS NULL) THEN true ELSE false END) " +
                        "FROM UtenteCollocazioneDto uc JOIN uc.collocazioneDto c JOIN uc.utenteDto u " +
                        "WHERE u.id = :idUtente " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                        "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) ", CollocazioneDTO.class)
                .setParameter("idUtente", idUtente)
                .getResultList();
    }

    @Override
    public List<CollocazioneDTO> findByIdUtenteFromOperatore(Long idUtente, String codiceFiscale) {
        List<Long> idCollocazioni = getCollocazioniAbilitate(codiceFiscale, Constants.APPLICATION_CODE);

        if(idCollocazioni.isEmpty()) return Collections.emptyList();

        return entityManager.createQuery(
                "SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda), " +
                        "CASE WHEN (uc.flagConfiguratore = 'S' AND uc.colFonteId IS NULL) THEN true ELSE false END) " +
                        "FROM UtenteCollocazioneDto uc JOIN uc.collocazioneDto c JOIN uc.utenteDto u " +
                        "WHERE u.id = :idUtente AND c.colId IN :idCollocazioni " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                        "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) ", CollocazioneDTO.class)
                .setParameter("idUtente", idUtente)
                .setParameter("idCollocazioni", idCollocazioni)
                .getResultList();
    }

    @Override
    public List<Long> findAziendeSanitarie(List<Long> idCollocazioni) {
        List<String> colCodAziende = entityManager.createQuery("SELECT c.colCodAzienda " +
                "FROM CollocazioneDto c " +
                "WHERE c.colId IN :idCollocazioni", String.class)
                .setParameter("idCollocazioni", idCollocazioni)
                .getResultList();

        return entityManager.createQuery("SELECT c.colId " +
                "FROM CollocazioneDto c " +
                "WHERE c.flagAzienda = 'S' AND c.colCodAzienda IN :colCodAziende", Long.class)
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
                "WHERE c.flagAzienda IS NULL AND c.colCodAzienda IN :idAziendeSanitarie", Long.class)
                .setParameter("idAziendeSanitarie", colCodAziende)
                .getResultList();
    }

    @Override
    public List<Long> getCollocazioniAbilitate(String codiceFiscale, String codiceApplicazione) {
    	
    	return entityManager.createQuery("Select c.colId FROM "
    			+ " CollocazioneDto c Where c.colCodAzienda IN "
    			 +" (SELECT DISTINCT a.codAzienda "
                 + " From VisibilitaAziendaDto v JOIN v.aziendaDto a JOIN v.utenteDto u "
                 + " WHERE u.codiceFiscale=:codiceFiscale AND "
                 + "((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita) "
                 + "OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL) "
                 + "OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL) "
                 + "OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL))) "
                 + " And "
                 + "((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
                 + "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
                 + "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
                 + "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) "	,Long.class)
            .setParameter("codiceFiscale", codiceFiscale)
            .getResultList();
    	}

    @Override
    public List<CollocazioneDto> getCollocazioniAbilitatePerExport(String codiceFiscale, String codiceApplicazione) {

        Query query = entityManager.createQuery("select distinct abi.utenteCollocazioneDto.collocazioneDto from " + AbilitazioneDto.class.getName() + " abi where " +
                " ((:data BETWEEN abi.dataInizioValidita AND abi.dataFineValidita) OR (abi.dataFineValidita IS NULL AND :data >= abi.dataInizioValidita)) " +
                " AND abi.applicazioneDto.codice = :codiceApplicazione " +
                " AND abi.ruoloUtenteDto.utenteDto.codiceFiscale = :codiceFiscale ");

        query.setParameter("data", Utils.sysdate());
        query.setParameter("codiceApplicazione", codiceApplicazione);
        query.setParameter("codiceFiscale", codiceFiscale);

        return query.getResultList();
    }
    

	@Override
	public List<Long> findAziendeSanitarieConVisiblita(List<Long> idCollocazioni, String codiceFiscale) {
        List<String> colCodAziende = entityManager.createQuery("SELECT c.colCodAzienda " +
                "FROM CollocazioneDto c " +
                "WHERE c.colId IN :idCollocazioni AND  "
                + " c.colCodAzienda IN ("
                + "	SELECT  a.codAzienda "
                + " From AziendaDto a, VisibilitaAziendaDto v "
                + " WHERE v.utenteDto.codiceFiscale=:codFiscOperatore "
                + " and v.aziendaDto.idAzienda=a.idAzienda AND " 
                + " ((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita)  "
                + " OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL)  "
                + " OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL)  "
                + " OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL)) AND "
                + " ((CURRENT_TIMESTAMP BETWEEN a.dataInizioValidita AND a.dataFineValidita)  "
                + " OR (CURRENT_TIMESTAMP > a.dataInizioValidita AND a.dataFineValidita IS NULL)  "
                + " OR (CURRENT_TIMESTAMP < a.dataFineValidita AND a.dataInizioValidita IS NULL)  "
                + " OR (a.dataInizioValidita IS NULL AND a.dataFineValidita IS NULL)) "
                + ")", String.class)
                .setParameter("idCollocazioni", idCollocazioni)
                .setParameter("codFiscOperatore", codiceFiscale)
                .getResultList();
        
        if(colCodAziende.isEmpty()) return Collections.emptyList();
        
        return entityManager.createQuery("SELECT c.colId " +
                "FROM CollocazioneDto c " +
                "WHERE c.flagAzienda = 'S' AND c.colCodAzienda IN :colCodAziende", Long.class)
                .setParameter("colCodAziende", colCodAziende)
                .getResultList();
    }

	@Override
	public List<String> trovaVisibilita(String codFiscale) {
        return entityManager.createQuery(
                "SELECT DISTINCT a.codAzienda "
                + " From VisibilitaAziendaDto v JOIN v.aziendaDto a JOIN v.utenteDto u "
                + " WHERE u.codiceFiscale=:codiceFiscale AND "
                + "((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita) "
                + "OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL) "
                + "OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL) "
                + "OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL))", String.class)
                .unwrap(org.hibernate.Query.class)
                .setString("codiceFiscale",codFiscale)
               .list();
    }

	@Override
	public List<CollocazioneDTO> findCollocazioniFromOperatore(Long idUtente,String codiceFiscale) {
		
		  return entityManager.createQuery(
	                "SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
	                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda), " +
	                        "CASE WHEN (uc.colFonteId is NOT NULL) THEN true ELSE false END) " +
	                        "FROM UtenteCollocazioneDto uc JOIN uc.collocazioneDto c JOIN uc.utenteDto u " +
	                        "WHERE u.id = :idUtente AND " +
	                        " ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
	                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
	                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
	                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) " +
	                        " AND c.colCodAzienda IN "
	                        + "(SELECT DISTINCT a.codAzienda  "
	                        + "From VisibilitaAziendaDto v JOIN v.aziendaDto a JOIN v.utenteDto u  "
	                        + " WHERE u.codiceFiscale=:codiceFiscale AND  "
	                        + " ((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita)  "
	                        + " OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL)  "
	                        + " OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL)  "
	                        + " OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL)) AND "
	                        + " ((CURRENT_TIMESTAMP BETWEEN a.dataInizioValidita AND a.dataFineValidita)  "
	                        + " OR (CURRENT_TIMESTAMP > a.dataInizioValidita AND a.dataFineValidita IS NULL)  "
	                        + " OR (CURRENT_TIMESTAMP < a.dataFineValidita AND a.dataInizioValidita IS NULL)  "
	                        + " OR (a.dataInizioValidita IS NULL AND a.dataFineValidita IS NULL))"
	                        + ")", CollocazioneDTO.class)
	                .setParameter("codiceFiscale", codiceFiscale)
	                .setParameter("idUtente", idUtente)
	                .getResultList();
	};
	
	@Override
	public List<CollocazioneDTO> findCollocazioniFromID(List<Long> ids,String codiceFiscale) {
        return entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO(" +
                        "c.colId, c.colDescrizione, c.colCodice, concat(c.colCodAzienda, ' - ', c.colDescAzienda)) " +
                        "FROM CollocazioneDto c " +
                        "WHERE c.colId IN :ids " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL)" +
                        "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))  "
                        +" AND c.colCodAzienda IN "
                        + "(SELECT DISTINCT a.codAzienda  "
                        + "From VisibilitaAziendaDto v JOIN v.aziendaDto a JOIN v.utenteDto u  "
                        + " WHERE u.codiceFiscale=:codiceFiscale AND  "
                        + " ((CURRENT_TIMESTAMP BETWEEN v.dataInizioValidita AND v.dataFineValidita)  "
                        + " OR (CURRENT_TIMESTAMP > v.dataInizioValidita AND v.dataFineValidita IS NULL)  "
                        + " OR (CURRENT_TIMESTAMP < v.dataFineValidita AND v.dataInizioValidita IS NULL)  "
                        + " OR (v.dataInizioValidita IS NULL AND v.dataFineValidita IS NULL))"
                       +")" , CollocazioneDTO.class)
                .setParameter("ids", ids)
                .setParameter("codiceFiscale", codiceFiscale)
                .getResultList();
    }
	
	

	@Override
	public CollocazioneDto findByCodUoAndCodMultiSpec(String codAzienda, String codUoEsteso, String codMultiSpec) {
		String query = "SELECT c FROM CollocazioneDto c WHERE " 
					 + "((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
					 + "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
					 + "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
					 + "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) "
					 + "AND c.colCodAzienda = :codAzienda ";
		if(codUoEsteso == null && codMultiSpec == null) 
			  query += "AND c.flagAzienda = 'S'";
		else {
			if(codUoEsteso != null)
			  query += "AND c.codUo = :codUoEsteso ";
			else
			  query += "AND c.codUo is NULL ";
			if(codMultiSpec != null)
			  query += "AND c.codMultiSpec = :codMultiSpec ";
			else
			  query += "AND c.codMultiSpec is NULL ";
		}
		
		try {
			TypedQuery<CollocazioneDto> tq = entityManager.createQuery(query, CollocazioneDto.class).setParameter("codAzienda", codAzienda);
			if(codUoEsteso != null) tq.setParameter("codUoEsteso", codUoEsteso);
			if(codMultiSpec != null) tq.setParameter("codMultiSpec", codMultiSpec);
			
			return tq.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public CollocazioneDto findByIdAmbulatorio(BigDecimal idAmbulatorio) {
		try {
			return entityManager.createQuery("SELECT c FROM CollocazioneDto c WHERE " 
						 + "((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
						 + "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
						 + "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
						 + "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) "
						 + "AND c.idAmbulatorio = :idAmbulatorio"
						 , CollocazioneDto.class)
						 .setParameter("idAmbulatorio", idAmbulatorio.toString()).getSingleResult();
		} catch (Exception e) {
			return null;
		} 
		
	}

	@Override
	public CollocazioneDto findByCodStrutAndCodUoAndCodMultiSpecAndColElemOrg(String azienda, String codStruttura,
			String codUoEsteso, String codMultiSpec, String codElemOrganiz) {
		String query = "SELECT c FROM CollocazioneDto c WHERE " 
				 + "((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
				 + "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
				 + "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
				 + "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) "
				 + "AND c.colCodAzienda = :codAzienda ";
	if(codStruttura == null && codUoEsteso == null && codMultiSpec == null && codElemOrganiz == null) 
		  query += "AND c.flagAzienda = 'S' ";
	else {
		if(codStruttura != null)
		  query += "AND c.codStruttura = :codStruttura ";
		else
		  query += "AND c.codStruttura is NULL ";
		if(codUoEsteso != null)
		  query += "AND c.codUo = :codUoEsteso ";
		else
		  query += "AND c.codUo is NULL ";
		if(codMultiSpec != null)
		  query += "AND c.codMultiSpec = :codMultiSpec ";
		else
		  query += "AND c.codMultiSpec is NULL ";
		if(codElemOrganiz != null)
		  query += "AND c.codiceElementoOrganizzativo = :codElemOrganiz ";
		else
		  query += "AND c.codiceElementoOrganizzativo is NULL ";
	}
	
	try {
		TypedQuery<CollocazioneDto> tq = entityManager.createQuery(query, CollocazioneDto.class).setParameter("codAzienda", azienda);
		if(codStruttura != null) tq.setParameter("codStruttura", codStruttura);
		if(codUoEsteso != null) tq.setParameter("codUoEsteso", codUoEsteso);
		if(codMultiSpec != null) tq.setParameter("codMultiSpec", codMultiSpec);
		if(codElemOrganiz != null) tq.setParameter("codElemOrganiz", codElemOrganiz);
		
		return tq.getSingleResult();
	} catch (Exception e) {
		return null;
	}
	}



}  
