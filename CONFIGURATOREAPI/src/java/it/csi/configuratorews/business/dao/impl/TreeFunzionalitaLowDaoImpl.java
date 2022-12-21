/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

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
	public List<TreeFunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto, String codiceTipoFunzione) {

		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
		queryBuilder.append("and funzionalitaDto.applicazioneDto.id = :id ");
		queryBuilder.append("and funzionalitaDto.tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
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
	public List<TreeFunzionalitaDto> findFunzionalitaByIdPadreProfilo(Long idPadreProfilo) {

		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("select fz from " + tabella + " fz ");
		queryBuilder.append(" inner join fz.funzionalitaDto as func");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append("and fnztree_id_parent = :id ");

		queryBuilder.append("and fz.dataCancellazione is null ");

		queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and :data >= fz.dataInizioValidita)) ");

		queryBuilder.append("and (( :data between func.dataInizioValidita and func.dataFineValidita) ");
		queryBuilder.append("or (func.dataFineValidita is null ");
		queryBuilder.append("and :data >= func.dataInizioValidita)) ");

		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", idPadreProfilo);
		query.setParameter("data", data);
		return query.getResultList();
	}
	@Override
	public List<TreeFunzionalitaDto> findFunzionalitaById(Long idProfilo) {
		
		String tabella = getTabName(new TreeFunzionalitaDto());
		Timestamp data = Utils.sysdate();
		StringBuilder queryBuilder = new StringBuilder("select fz from " + tabella + " fz ");
		queryBuilder.append(" inner join fz.funzionalitaDto as func");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append("and fnztree_id = :id ");
		
		queryBuilder.append("and fz.dataCancellazione is null ");
		
		queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
		
		queryBuilder.append("and (( :data between func.dataInizioValidita and func.dataFineValidita) ");
		queryBuilder.append("or (func.dataFineValidita is null ");
		queryBuilder.append("and :data >= func.dataInizioValidita)) ");
		
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", idProfilo);
		query.setParameter("data", data);
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
	public List<TreeFunzionalitaDto> findIdTreeByFnzId(Long idFunzionalita) {

		String tabella = getTabName(new TreeFunzionalitaDto());
		
		StringBuilder queryBuilder = new StringBuilder("select fz from " + tabella + " fz ");
		queryBuilder.append(" WHERE fnz_id = :id ");		
		queryBuilder.append("and fz.dataCancellazione is null ");		
		queryBuilder.append("and (( now() between fz.dataInizioValidita and fz.dataFineValidita) ");
		queryBuilder.append("or (fz.dataFineValidita is null ");
		queryBuilder.append("and now() >= fz.dataInizioValidita)) ");
		
		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
		query.setParameter("id", idFunzionalita);
		
		return query.getResultList();
		
	}
	
}
