/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.util.Utils;


@Component
public class FunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<FunzionalitaDto, Long> implements FunzionalitaLowDao {

    public static String CODICE_TIPO_PROFILO = "PROF";
    public static String CODICE_TIPO_FUNZIONALITA = "FUNZ";
    public static String CODICE_TIPO_FUNZIONALITA_APP = "APP";

    @Override
    public List<FunzionalitaDto> findProfili(FunzionalitaDto funzionalitaDto, Boolean flag) {
        String tabella = getTabName(funzionalitaDto);
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
        if (funzionalitaDto.getCodiceFunzione() != null && !funzionalitaDto.getCodiceFunzione().isEmpty()) {
            queryBuilder.append("and UPPER(fnz_codice) like UPPER(:codice) ");
        }
        if (funzionalitaDto.getDescrizioneFunzione() != null && !funzionalitaDto.getDescrizioneFunzione().isEmpty()) {
            queryBuilder.append("and UPPER(fnz_descrizione) like UPPER(:descrizione) ");

        }
        if (funzionalitaDto.getApplicazioneDto().getId() != null) {
            queryBuilder.append("and applicazione_id = :applicazione_id ");

        }
        queryBuilder.append("and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        if (flag != null) {
            if (flag == true) {

                queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
                queryBuilder.append("or (fz.dataFineValidita is null ");
                queryBuilder.append("and :data >= fz.dataInizioValidita)) ");

            }
            if (flag == false) {

                queryBuilder.append("and (( :data < fz.dataInizioValidita or :data > fz.dataFineValidita) ");
                queryBuilder.append("or (fz.dataFineValidita is null ");
                queryBuilder.append("and fz.dataInizioValidita is null)) ");
            }
        }
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        if (funzionalitaDto.getCodiceFunzione() != null && !funzionalitaDto.getCodiceFunzione().isEmpty()) {
            query.setParameter("codice", funzionalitaDto.getCodiceFunzione() + '%');
        }
        if (funzionalitaDto.getDescrizioneFunzione() != null && !funzionalitaDto.getDescrizioneFunzione().isEmpty()) {
            query.setParameter("descrizione", funzionalitaDto.getDescrizioneFunzione() + '%');
        }
        if (funzionalitaDto.getApplicazioneDto().getId() != null) {
            query.setParameter("applicazione_id", funzionalitaDto.getApplicazioneDto().getId());
        }
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_PROFILO);
        if (flag != null && (flag == true || flag == false)) {
            query.setParameter("data", data);
        }
        return query.getResultList();

    }

    @Override
    public List<FunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto) {

        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
        queryBuilder.append("and applicazione_id = :id ");
        queryBuilder.append("and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append("or (fz.dataFineValidita is null ");
        queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        query.setParameter("id", applicazioneDto.getId());
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_FUNZIONALITA);
        query.setParameter("data", data);

        return query.getResultList();
    }



    @Override
    public List<FunzionalitaDto> findFunzionalitaApplicazione(ApplicazioneDto applicazioneDto, String codiceTipoFunzione) {

        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE ");
        queryBuilder.append("and applicazione_id = :id ");
        queryBuilder.append("and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append("or (fz.dataFineValidita is null ");
        queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        query.setParameter("id", applicazioneDto.getId());
        query.setParameter("fnz_tipo_codice", codiceTipoFunzione);
        query.setParameter("data", data);

        return query.getResultList();
    }

    /**
     * @param className
     * @return
     */
    public String getTabName(Object className) {
        return className.getClass().getName();
    }

    
    @Override
    public Long countFunzionalitaByCodiceApplicazione(String codiceApplicazione,String codiceAzienda) {
//aggiungere auth_r_colazienda_profilo
        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("select count(*) from " + tabella + " fz ");
        queryBuilder.append(" inner join fz.applicazioneDto app ");
        queryBuilder.append(" inner join app.applicazioneCollocazioneDtoList appColl ");
        queryBuilder.append(" WHERE 1=1 ");
        queryBuilder.append(" and appColl.collocazioneDto.colCodAzienda =:codiceAzienda ");
        queryBuilder.append(" and (app.flagOscurato != 'S' or app.flagOscurato is null) ");
        queryBuilder.append(" and appColl.collocazioneDto.flagAzienda = 'S' ");
        queryBuilder.append(" and app.codice = :codiceApplicazione ");
        queryBuilder.append(" and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append(" and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append(" or (fz.dataFineValidita is null ");
        queryBuilder.append(" and :data >= fz.dataInizioValidita)) ");
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        query.setParameter("codiceApplicazione", codiceApplicazione);
        query.setParameter("codiceAzienda", codiceAzienda);
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_PROFILO);
        query.setParameter("data", data);

        Long count = (Long)query.getSingleResult();
        return count;
    }
    
    @Override
    public List<FunzionalitaDto> findFunzionalitaByCodiceApplicazione(String codiceApplicazione, Integer limit, Integer offset,String codiceAzienda) {
        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("select fz from " + tabella + " fz ");
        queryBuilder.append(" inner join fz.applicazioneDto app ");
        queryBuilder.append(" inner join app.applicazioneCollocazioneDtoList appColl ");
        queryBuilder.append(" WHERE 1=1 ");
        queryBuilder.append(" and appColl.collocazioneDto.colCodAzienda =:codiceAzienda ");
        queryBuilder.append(" and (app.flagOscurato != 'S' or app.flagOscurato is null) ");
        queryBuilder.append(" and appColl.collocazioneDto.flagAzienda = 'S' ");
        queryBuilder.append(" and app.codice = :codiceApplicazione ");
        queryBuilder.append(" and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append(" and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append(" or (fz.dataFineValidita is null ");
        queryBuilder.append(" and :data >= fz.dataInizioValidita)) ");        
        
        queryBuilder.append(" order by fz.codiceFunzione");
        
        String queryResult = queryBuilder.toString();
        
        Query query = entityManager.createQuery(queryResult);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        query.setParameter("codiceApplicazione", codiceApplicazione);
        query.setParameter("codiceAzienda", codiceAzienda);
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_PROFILO);
        query.setParameter("data", data);

        return query.getResultList();
    }

    public FunzionalitaDto findByFunzionalitaApplicazioneAndTipo(String codiceFunzionalita,String codiceApplicazione, String tipoFunzionalita) {
    	return findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita,codiceApplicazione,tipoFunzionalita,null);
    }
	@Override
	public FunzionalitaDto findByFunzionalitaApplicazioneAndTipo(String codiceFunzionalita,String codiceApplicazione, String tipoFunzionalita, String codiceAzienda) {
		String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("select fz from " + tabella + " fz ");
        if(codiceAzienda!=null) {
	    	queryBuilder.append(" inner join fz.applicazioneDto app ");
	    	queryBuilder.append(" inner join app.applicazioneCollocazioneDtoList appColl ");
        }
    	queryBuilder.append( " WHERE 1=1 ");
    	if(codiceAzienda!=null) {
    		queryBuilder.append(" and appColl.collocazioneDto.colCodAzienda =:codiceAzienda ");
    	}
        if(tipoFunzionalita!=null) {
        	queryBuilder.append("and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        }
        queryBuilder.append("and fz.codiceFunzione = :codiceFunzionalita ");
        if(codiceApplicazione!=null) {
        	queryBuilder.append("and fz.applicazioneDto.codice = :codiceApplicazione ");
        }
        queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita ) ");
        queryBuilder.append("or (fz.dataFineValidita is null ");
        queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
        
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        if(tipoFunzionalita!=null) {
        	query.setParameter("fnz_tipo_codice", tipoFunzionalita);
        }
        if(codiceApplicazione!=null) {
        	query.setParameter("codiceApplicazione", codiceApplicazione);
        }
        query.setParameter("codiceFunzionalita", codiceFunzionalita);
        if(codiceAzienda!=null) {
        	query.setParameter("codiceAzienda", codiceAzienda);
        }
        query.setParameter("data", data);
        try {
        	return (FunzionalitaDto) query.getSingleResult();
        }catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<FunzionalitaDto> findByCodiceFunzioneAndCodiceTipoFunzione(String codiceFunzione,String codiceTipoFunzione) {
		Query query = entityManager.createQuery("from FunzionalitaDto f WHERE f.codiceFunzione = :codiceFunzione  "
		 + " AND f.tipoFunzionalitaDto.codiceTipoFunzione = :codiceTipoFunzione "
	     + " AND ((:data BETWEEN f.dataInizioValidita AND f.dataFineValidita) OR (:data >= f.dataInizioValidita AND f.dataFineValidita IS NULL)) "
	     +  " AND f.dataCancellazione IS NULL ");
		query.setParameter("data", Utils.sysdate());
		query.setParameter("codiceFunzione", codiceFunzione);
		query.setParameter("codiceTipoFunzione", codiceTipoFunzione);
		return query.getResultList();
	}

	@Override
	public List<FunzionalitaDto> findByCodiceAndCodiceTipoAndApplicazioneCodice(String codiceFunzionalita, String codiceTipo,
			String codiceApplicazione) {
		Query query = entityManager.createQuery("from FunzionalitaDto f WHERE f.codiceFunzione = :codiceFunzione  "
				 + " AND f.tipoFunzionalitaDto.codiceTipoFunzione = :codiceTipoFunzione "
				 + " AND f.applicazioneDto.codice = :codiceApplicazione "
			     + " AND ((:data BETWEEN f.dataInizioValidita AND f.dataFineValidita) OR (:data >= f.dataInizioValidita AND f.dataFineValidita IS NULL)) "
			     + " AND f.dataCancellazione IS NULL ");
				query.setParameter("data", Utils.sysdate());
				query.setParameter("codiceFunzione", codiceFunzionalita);
				query.setParameter("codiceTipoFunzione", codiceTipo);
				query.setParameter("codiceApplicazione", codiceApplicazione);
				return query.getResultList();
	}

	@Override
	public Boolean existsProfiloByApplicazione(String codiceProfilo, String codiceApplicazione) {
		
		Timestamp data = Utils.sysdate();
		StringBuilder sql = new StringBuilder("select f from FunzionalitaDto f ");
		sql.append("where f.codiceFunzione = :codiceProfilo ");
		sql.append("and f.tipoFunzionalitaDto.codiceTipoFunzione = 'PROF' ");
		sql.append("and f.applicazioneDto.codice = :codiceApplicazione ");
		sql.append("and f.dataInizioValidita <= :data and (f.dataFineValidita is null or f.dataFineValidita >= :data) ");
		Query query = entityManager.createQuery(sql.toString(), FunzionalitaDto.class);
		
        query.setParameter("codiceApplicazione", codiceApplicazione);
        query.setParameter("codiceProfilo", codiceProfilo);
        query.setParameter("data", data);
        
		List<FunzionalitaDto> apps = query.getResultList();		
		
		if(apps!=null && apps.size()>0) 
			return true;
		
		return false;
	}
	
	@Override
	public List<FunzionalitaDto> findFunzionalitaByCodiceApp(String codiceApp, String codiceAzienda, String codiceTipoFunzione,
			Integer limit, Integer offset) {

		StringBuilder queryBuilder = new StringBuilder("select fz from FunzionalitaDto fz  ");		
		queryBuilder.append(" inner join fz.applicazioneDto app ");
	    queryBuilder.append(" inner join app.applicazioneCollocazioneDtoList appColl ");	  
	    queryBuilder.append("WHERE");
		queryBuilder.append(" fz.applicazioneDto.codice = :codiceApp ");
		queryBuilder.append("and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
		queryBuilder.append("and (( now() between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and now() >= fz.dataInizioValidita)) ");
		queryBuilder.append("and appColl.collocazioneDto.colCodAzienda =:codiceAzienda ");
		queryBuilder.append("order by fz.codiceFunzione");
		
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);

		query.setParameter("codiceApp", codiceApp);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("fnz_tipo_codice", codiceTipoFunzione);
		
		query.setFirstResult(offset);
        query.setMaxResults(limit);

		return query.getResultList();
	}

	@Override
	public Long countFunzionalitaByCodiceApp(String codiceApp, String codiceAzienda,
			String codiceTipoFunzione) {

		StringBuilder queryBuilder = new StringBuilder("select count(*) from FunzionalitaDto fz  ");		
		queryBuilder.append(" inner join fz.applicazioneDto app ");
	    queryBuilder.append(" inner join app.applicazioneCollocazioneDtoList appColl ");	  
	    queryBuilder.append("WHERE");
		queryBuilder.append(" fz.applicazioneDto.codice = :codiceApp ");
		queryBuilder.append("and fz.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
		queryBuilder.append("and (( now() between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and now() >= fz.dataInizioValidita)) ");
		queryBuilder.append("and appColl.collocazioneDto.colCodAzienda =:codiceAzienda ");
		//queryBuilder.append("order by fz.codiceFunzione");

		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);

		query.setParameter("codiceApp", codiceApp);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("fnz_tipo_codice", codiceTipoFunzione);

        Long count = (Long)query.getSingleResult();
        return count;
        
	}

}
