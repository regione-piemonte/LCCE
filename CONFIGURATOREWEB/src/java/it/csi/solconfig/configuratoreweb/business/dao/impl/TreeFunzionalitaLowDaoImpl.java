/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import it.csi.solconfig.configuratoreweb.business.dao.TreeFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;
import it.csi.solconfig.configuratoreweb.util.Utils;

import java.util.Collection;

@Component
public class TreeFunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<TreeFunzionalitaDto, Long> implements TreeFunzionalitaLowDao {
	public static String CODICE_TIPO_FUNZIONALITA_PROF = "PROF";
	public static String CODICE_TIPO_FUNZIONALITA_FUNZ = "FUNZ";
	public static String CODICE_TIPO_FUNZIONALITA_APP = "APP";
	
	
	
	/**
	 * @param className
	 * @return
	 */
	public String getTabName(Object className) {
		return className.getClass().getName();
	}


	@Override
    public Collection<TreeFunzionalitaDto> findByFunzionalitaDto(FunzionalitaDto funzionalitaDto) {

        TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
        Query query = entityManager.createQuery("from " + treeFunzionalitaDto.getClass().getName()
                + " a WHERE a.funzionalitaDto.idFunzione =:idFunzione AND ((:data BETWEEN a.dataInizioValidita AND a.dataFineValidita) OR (a.dataFineValidita IS NULL AND (:data BETWEEN a.dataInizioValidita AND '9999-12-31 00:00:00')))");
        query.setParameter("idFunzione", funzionalitaDto.getIdFunzione());
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }

	@Override
	public List<TreeFunzionalitaDto> findFunzByIdApplicazione(ApplicazioneDto applicazioneDto) {
		
		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
		queryBuilder.append("and funzionalitaDto.applicazioneDto.id = :id and funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = :codiceApp ");
		queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", applicazioneDto.getId());
		query.setParameter("codiceApp", CODICE_TIPO_FUNZIONALITA_FUNZ);
		query.setParameter("data", data);
		
		return query.getResultList();
	}

	@Override
	public List<TreeFunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto) {
		
		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
		queryBuilder.append("and funzionalitaDto.applicazioneDto.id = :id and funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = :codiceApp ");
		queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", applicazioneDto.getId());
		query.setParameter("codiceApp", CODICE_TIPO_FUNZIONALITA_APP);
		query.setParameter("data", data);
		
		return query.getResultList();
	}



	@Override
	public List<TreeFunzionalitaDto> findByIdPadre(Nodo albero) {
		
		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
		queryBuilder.append(" and fnztree_id_parent = :id ");
		//queryBuilder.append(" and funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = :codiceApp ");
		queryBuilder.append(" and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append(" or (fz.dataFineValidita is null ");
		queryBuilder.append(" and :data >= fz.dataInizioValidita)) ");
		String queryResult = queryBuilder.toString();
		
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", albero.getTreeFunzionalitaDto().getIdTreeFunzione()/*applicazioneDto.getId()*/);
		query.setParameter("data", data);
		//query.setParameter("codiceApp", CODICE_TIPO_FUNZIONALITA_FUNZ);
		
		return query.getResultList();
	}

	@Override
	public List<TreeFunzionalitaDto> findByIdPadreFunzionalita(TreeFunzionalitaDto treeFunzionalitaDto) {
		
		String tabella = getTabName(new TreeFunzionalitaDto());
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
		queryBuilder.append("and fnztree_id_parent = :id ");
		queryBuilder.append("and data_cancellazione is null");
		String queryResult = queryBuilder.toString();
		
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", treeFunzionalitaDto.getIdTreeFunzione());
		
		return query.getResultList();
	}

	@Override
	public List<TreeFunzionalitaDto> findByCodiceApplicazioneAndFunzionalitaTipo(String codiceTipoFunzione,
			String codiceApplicazione) {
		
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("from TreeFunzionalitaDto fz inner join fetch fz.funzionalitaDto WHERE 1=1 ");
		queryBuilder.append("and fz.funzionalitaDto.applicazioneDto.codice = :codiceApplicazione ");
//		queryBuilder.append("and fz.funzionalitaDto.codiceFunzione = :codiceFunzione ");
		queryBuilder.append("and fz.funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
		queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		
		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("fnz_tipo_codice", codiceTipoFunzione);
		query.setParameter("data", data);

		return query.getResultList();
		
	}

	@Override
	public List<TreeFunzionalitaDto> findByCodiceFunzioneAndApplicazione(String codiceFunzione, String codiceTipoFunzione, String codiceApplicazione) {

		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz inner join fetch fz.funzionalitaDto WHERE 1=1 ");
		queryBuilder.append("and fz.funzionalitaDto.applicazioneDto.codice = :codiceApplicazione ");
		queryBuilder.append("and fz.funzionalitaDto.codiceFunzione = :codiceFunzione ");
		queryBuilder.append("and fz.funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
		queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
		queryBuilder.append("and fz.dataCancellazione is null");
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("codiceFunzione", codiceFunzione);
		query.setParameter("fnz_tipo_codice", codiceTipoFunzione);
		query.setParameter("data", data);

		return query.getResultList();
	}
	
	@Override
	public List<TreeFunzionalitaDto> findByIdFunzione(Long idFunzione) {
		 TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
	        Query query = entityManager.createQuery("from " + treeFunzionalitaDto.getClass().getName()
	                + " a WHERE a.funzionalitaDto.idFunzione =:idFunzione");
	        query.setParameter("idFunzione", idFunzione);
	        return query.getResultList();
	}
	
}
