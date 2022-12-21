/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.RuoloLowDao;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Component
public class RuoloLowDaoImpl extends CatalogoBaseLowDaoImpl<RuoloDto, Long> implements RuoloLowDao {

    private static final String VISIBILITA_CONF_NO = "N";

	@Override
    public Collection<RuoloDto> findByUtenteCodiceFiscale(String codiceFiscale) {
        Query query = entityManager.createQuery(
                "SELECT r FROM RuoloDto r, RuoloUtenteDto ru, UtenteDto u WHERE r.id=ru.ruoloDto.id AND u.id=ru.utenteDto.id AND u.codiceFiscale=:codiceFiscale AND :data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita");
        query.setParameter("codiceFiscale", codiceFiscale);
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }

    @Override
    public Collection<RuoloDto> findByCodiceAndDataValidita(String codiceRuolo) {
        Query query = entityManager.createQuery(
                "SELECT r FROM RuoloDto r " +
                        " WHERE r.codice = :codiceRuolo " +
                        "AND ((:data BETWEEN r.dataInizioValidita AND r.dataFineValidita) OR (:data >= r.dataInizioValidita and r.dataFineValidita is null))");
        query.setParameter("codiceRuolo", codiceRuolo);
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }

    @Override
    public List<RuoloDto> findRuoli(RuoloDto ruoloDto, String flagAttivo, String flagNonAttivo, Boolean flagConfiguratore) {

        StringBuilder queryBuilder = new StringBuilder("from " + ruoloDto.getClass().getName() + " t" + " WHERE 1=1");

        if (ruoloDto.getCodice() != null && !ruoloDto.getCodice().isEmpty()) {
            queryBuilder.append("AND UPPER(t.codice) like UPPER(:codice) ");
        }
        if (ruoloDto.getDescrizione() != null && !ruoloDto.getDescrizione().isEmpty()) {
            queryBuilder.append("AND UPPER(t.descrizione) like UPPER(:descrizione) ");
        }

        /*filtri
        	stato attivo*/
        if (flagNonAttivo == null && "true".equalsIgnoreCase(flagAttivo)) {
        	queryBuilder.append("and ((:data between t.dataInizioValidita and t.dataFineValidita) ");
        	queryBuilder.append("or (t.dataFineValidita is null ");
        	queryBuilder.append("and :data >= dataInizioValidita)) ");
        		//stato non attivo
        } if (flagAttivo == null &&  "true".equalsIgnoreCase(flagNonAttivo)) {
        	queryBuilder.append("and ((:data < t.dataInizioValidita or :data > t.dataFineValidita) ");
        	queryBuilder.append("or (t.dataInizioValidita is null ");
        	queryBuilder.append("and t.dataFineValidita is null)) ");
        	}
        
        //filtro inserito da configuratore
        if(flagConfiguratore!=null && flagConfiguratore == true) {
            queryBuilder.append("AND t.flagConfiguratore = :flagConfiguratore");
        }
        
        //ruoli visibili da configuratore
        	queryBuilder.append(" AND (t.visibilitaConf != :visibilitaConf");
        	queryBuilder.append(" OR t.visibilitaConf is null)");
        

        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);

        if (ruoloDto.getCodice() != null && !ruoloDto.getCodice().isEmpty()) {
            query.setParameter("codice", ruoloDto.getCodice() + '%');
        }
        if (ruoloDto.getDescrizione() != null && !ruoloDto.getDescrizione().isEmpty()) {
            query.setParameter("descrizione", ruoloDto.getDescrizione() + '%');
        }
        if (flagNonAttivo == null && "true".equalsIgnoreCase(flagAttivo)
        	|| (flagAttivo == null &&  "true".equalsIgnoreCase(flagNonAttivo))) {
            query.setParameter("data", Utils.sysdate());
        }     
        if(flagConfiguratore !=null && flagConfiguratore==true) {
            query.setParameter("flagConfiguratore", "S");
        }
        	query.setParameter("visibilitaConf", "N");
        	
       
        return query.getResultList();
    }

	@Override
	public List<RuoloDto> findByStatoAttivoEVisibilita(Integer limit, Integer offset) {
		
		StringBuilder queryBuilder = new StringBuilder("from " + RuoloDto.class.getName() + " t" + " WHERE 1=1");
		
		queryBuilder.append("AND ((:data between t.dataInizioValidita AND t.dataFineValidita) ");
    	queryBuilder.append("OR (t.dataFineValidita is null ");
    	queryBuilder.append("AND :data >= dataInizioValidita)) ");
    	queryBuilder.append(" AND (t.visibilitaConf <> :visibilita OR t.visibilitaConf is null)");
    	
		String queryResult = queryBuilder.toString();
    	Query query = entityManager.createQuery(queryResult);
    	
    	query.setParameter("data", Utils.sysdate());
    	query.setParameter("visibilita", VISIBILITA_CONF_NO);
		
        if(limit!=null) {
        	query.setFirstResult(offset);
    		query.setMaxResults(limit);
        }
		
		return query.getResultList();
	}
	@Override
	public Long countByStatoAttivoEVisibilita() {
		
		StringBuilder queryBuilder = new StringBuilder("select count(*) from " + RuoloDto.class.getName() + " t" + " WHERE 1=1");
		
		queryBuilder.append("AND ((:data between t.dataInizioValidita AND t.dataFineValidita) ");
		queryBuilder.append("OR (t.dataFineValidita is null ");
		queryBuilder.append("AND :data >= dataInizioValidita)) ");
		queryBuilder.append(" AND (t.visibilitaConf <> :visibilita OR t.visibilitaConf is null)");
		
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		
		query.setParameter("data", Utils.sysdate());
		query.setParameter("visibilita", VISIBILITA_CONF_NO);
		
		
		return (Long)query.getSingleResult();
	}

	@Override
	public List<RuoloDto> findByProfilo(String codiceFunzionalita) {

		Query query = entityManager.createQuery("SELECT rp.ruoloDto FROM RuoloProfiloDto rp " 
				+ " WHERE rp.funzionalitaDto.codiceFunzione = :codiceFunzionalita "
				+ " AND rp.dataInizioValidita < now() and (rp.dataFineValidita > now() or rp.dataFineValidita is null)");
		
		query.setParameter("codiceFunzionalita", codiceFunzionalita);

		return query.getResultList();
		
	}

	@Override
	public List<RuoloDto> findByProfiloAndApplicazione(String codiceProfilo, String codiceApplicazione) {

		Query query = entityManager.createQuery("SELECT rp.ruoloDto FROM RuoloProfiloDto rp " 
				+ " WHERE rp.funzionalitaDto.codiceFunzione = :codiceFunzionalita "
				+ " AND rp.funzionalitaDto.applicazioneDto.codice = :codiceApplicazione "
				+ " AND rp.dataInizioValidita < now() and (rp.dataFineValidita > now() or rp.dataFineValidita is null)");
		
		query.setParameter("codiceFunzionalita", codiceProfilo);
		query.setParameter("codiceApplicazione", codiceApplicazione);

		return query.getResultList();
		
	}
		
}


	

