/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneProfiloDto;

@Component
public class ApplicazioneLowDaoImpl extends CatalogoBaseLowDaoImpl<ApplicazioneDto, Long> implements ApplicazioneLowDao {


	@Override
	public List<ApplicazioneDto> findApplicazioniAbilitate(
															String codiceRuolo, 
															String codiceCollocazione, 
															String codiceAzienda, 
															String cfUtente) {
		
		StringBuilder sql = new StringBuilder(" with qry as (            ");
		sql.append(" select distinct                                     ");
		//sql.append(" auth_t_utente.id,                                   ");
		sql.append(" auth_t_utente.codice_fiscale Codice_Fiscale,        ");
		sql.append(" auth_d_ruolo.codice Codice_Ruolo,                   ");
		
		sql.append(" auth_t_collocazione.col_cod_azienda ,               ");
		sql.append(" auth_t_collocazione.col_desc_azienda ,              ");
		sql.append(" auth_t_collocazione.col_codice ,                    ");
		
		sql.append(" auth_d_applicazione.*                               ");
		//sql.append(" auth_d_applicazione.descrizione,                    ");
		//sql.append(" auth_d_applicazione.redirect_url                    ");
		
		sql.append(" from                                                                                   ");
		sql.append(" auth_t_utente,                                                                         ");
		sql.append(" auth_r_ruolo_utente,                                                                   ");
		sql.append(" auth_d_ruolo,                                                                          ");
		sql.append(" auth_r_utente_collocazione,                                                            ");
		sql.append(" auth_t_collocazione,                                                                   ");
		sql.append(" auth_r_abilitazione,                                                                   ");
		sql.append(" auth_r_funzionalita_tree,                                                              ");
		sql.append(" auth_t_funzionalita,                                                                   ");
		sql.append(" auth_d_applicazione                                                                    ");
		sql.append(" where                                                                                  ");
		sql.append(" auth_r_ruolo_utente.id_utente = auth_t_utente.id and                                   ");
		sql.append(" auth_r_ruolo_utente.id_ruolo = auth_d_ruolo.id and                                     ");
		sql.append(" auth_r_utente_collocazione.ute_id = auth_t_utente.id and                               ");
		sql.append(" auth_r_utente_collocazione.col_id = auth_t_collocazione.col_id and                     ");
		sql.append(" auth_r_abilitazione.id_ruolo_utente =auth_r_ruolo_utente.id and                        ");
		sql.append(" auth_r_abilitazione.utecol_id =auth_r_utente_collocazione.utecol_id AND                ");
		sql.append(" auth_r_funzionalita_tree.fnztree_id = auth_r_abilitazione.fnztree_id and               ");
		sql.append(" auth_t_funzionalita.fnz_id =auth_r_funzionalita_tree.fnz_id and                        ");
		sql.append(" auth_d_applicazione.id = auth_t_funzionalita.applicazione_id and                       ");
		sql.append(" auth_t_funzionalita.fnz_tipo_id = (SELECT fnz_tipo_id FROM auth_d_funzionalita_tipo WHERE auth_d_funzionalita_tipo.fnz_tipo_codice = 'PROF') and     ");
		sql.append(" now() between coalesce(auth_t_utente.data_inizio_validita,now()) and coalesce(auth_t_utente .data_fine_validita,now()) and                           ");
		sql.append(" now() between coalesce(auth_r_ruolo_utente.data_inizio_validita,now()) and coalesce(auth_r_ruolo_utente .data_fine_validita,now()) and               ");
		sql.append(" now() between coalesce(auth_d_ruolo.data_inizio_validita,now()) and coalesce(auth_d_ruolo .data_fine_validita,now()) and                             ");
		sql.append(" now() between coalesce(auth_r_utente_collocazione.data_inizio_validita,now()) and coalesce(auth_r_utente_collocazione .data_fine_validita,now()) and ");
		sql.append(" auth_r_utente_collocazione.data_cancellazione is null and                                                                                            ");
		sql.append(" now() between coalesce(auth_t_collocazione.data_inizio_validita,now()) and coalesce(auth_t_collocazione .data_fine_validita,now()) and               ");
		sql.append(" auth_t_collocazione.data_cancellazione is null and                                                                                                   ");
		sql.append(" now() between coalesce(auth_r_abilitazione.data_inizio_validita,now()) and coalesce(auth_r_abilitazione .data_fine_validita,now()) and               ");
		sql.append(" auth_r_abilitazione.data_cancellazione is null and                                                                                                   ");
		sql.append(" now() between coalesce(auth_r_funzionalita_tree.data_inizio_validita,now()) and coalesce(auth_r_funzionalita_tree .data_fine_validita,now()) and     ");
		sql.append(" auth_r_funzionalita_tree.data_cancellazione is null and                                                                                              ");
		sql.append(" now() between coalesce(auth_t_funzionalita.data_inizio_validita,now()) and coalesce(auth_t_funzionalita .data_fine_validita,now()) and               ");
		sql.append(" auth_t_funzionalita.data_cancellazione is null                                                                                                       ");
		sql.append(" order by Codice_Fiscale,codice_ruolo                                                                                                                 ");
		sql.append(" )                                       ");
		sql.append(" select                                  ");
		sql.append(" *                                       ");
		sql.append(" from                                    ");
		sql.append(" qry                                     ");
		sql.append(" where                                   ");
		sql.append(" Codice_Fiscale= :cfUtente and           ");
		sql.append(" codice_ruolo= :codiceRuolo and          ");
		sql.append(" col_cod_azienda= :codiceAzienda and     ");
		sql.append(" col_codice= :codiceCollocazione and     ");
		sql.append(" coalesce(oscurato,'N') !='S'       ");

		Query query = entityManager.createNativeQuery(sql.toString(), ApplicazioneDto.class);	
       		
		query.setParameter("codiceRuolo", codiceRuolo);
		query.setParameter("codiceCollocazione", codiceCollocazione);
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setParameter("cfUtente", cfUtente);
		
		List<ApplicazioneDto> apps = query.getResultList();
		 
		for (ApplicazioneDto app : apps) {
		    System.out.println("APPLICAZIONE "
		            + app.getCodice()
		            + " "
		            + app.getDescrizione()
		            + " "
		            + app.getRedirectUrl());
		}
		
		//return getResultList(query, ApplicazioneDto.class);
		return apps;
		
	}

	@Override
	public List<ApplicazioneDto> findAll(Integer limit, Integer offset, String codiceAzienda) {
		StringBuilder sql = new StringBuilder("select a from ApplicazioneDto a ");
		sql.append(" , ApplicazioneCollocazioneDto ac ");
		sql.append(" , CollocazioneDto c");
		sql.append(" where ac.applicazioneDto.id = a.id and ac.collocazioneDto.colId = c.colId ");
		sql.append(" and (a.flagOscurato != 'S' or a.flagOscurato is null) ");
		sql.append(" and c.flagAzienda = 'S' ");
		if(codiceAzienda != null) sql.append(" and c.colCodAzienda =:codiceAzienda");
		sql.append(" order by a.codice");
		
		Query query = entityManager.createQuery(sql.toString());	
		query.setParameter("codiceAzienda", codiceAzienda);
		query.setFirstResult(offset);
		query.setMaxResults(limit);	
		
		List<ApplicazioneDto> apps = query.getResultList();
		
		return apps;	
	}

	@Override
	public Long countApplicazioni() {
		StringBuilder sql = new StringBuilder("select count(*) from ApplicazioneDto a where 1=1");
		sql.append(" and (a.flagOscurato != 'S' or a.flagOscurato is null)");
		Query query = entityManager.createQuery(sql.toString());	
		Long count = (Long)query.getSingleResult();
        return count;
	}

	@Override
	public Long countApplicazioniByCodAzienda(String codiceAzienda) {        
		StringBuilder sql = new StringBuilder("select count(*) from ApplicazioneDto a ");
		sql.append(" , ApplicazioneCollocazioneDto ac ");
		sql.append(" , CollocazioneDto c");
		sql.append(" where ac.applicazioneDto.id = a.id and ac.collocazioneDto.colId = c.colId ");
		sql.append(" and (a.flagOscurato != 'S' or a.flagOscurato is null) ");
		sql.append(" and c.flagAzienda = 'S' ");
		if(codiceAzienda != null) sql.append(" and c.colCodAzienda =:codiceAzienda");
		
		Query query = entityManager.createQuery(sql.toString());	
		query.setParameter("codiceAzienda", codiceAzienda);
		Long count = (Long) query.getSingleResult();
		return count;        
	}
	
	@Override 
	public List<ApplicazioneDto> findByCodiceValidator(ApplicazioneDto applicazionedto) {
			Query query = entityManager.createQuery("from ApplicazioneDto a WHERE a.codice = :codice  ");
			query.setParameter("codice", applicazionedto.getCodice());
			return query.getResultList();
	}

	@Override
	public List<ApplicazioneDto> findByUtenteAndCollocazioneOrAzienda(Long idUtente, String collocazione,
			String codiceAzienda) {

		StringBuilder stringBuilder = new StringBuilder("select distinct a.applicazioneDto from AbilitazioneDto a ");
		stringBuilder.append(" where a.utenteCollocazioneDto.utenteDto.id = :idUtente ");				
		
		if (collocazione != null) {
			stringBuilder.append("and a.utenteCollocazioneDto.collocazioneDto.colCodice = :collocazione ");
		} else {
			stringBuilder.append("and a.utenteCollocazioneDto.collocazioneDto.colCodAzienda = :codiceAzienda ");
		}
		
		stringBuilder.append("and a.ruoloUtenteDto.dataInizioValidita < now() "
				+ "and (a.ruoloUtenteDto.dataFineValidita > now() or a.ruoloUtenteDto.dataFineValidita is null) ");
		stringBuilder.append("and a.ruoloUtenteDto.utenteDto.dataInizioValidita < now() "
				+ "and (a.ruoloUtenteDto.utenteDto.dataFineValidita > now() or a.ruoloUtenteDto.utenteDto.dataFineValidita is null) ");
		stringBuilder.append("and a.utenteCollocazioneDto.dataInizioValidita < now() "
				+ "and (a.utenteCollocazioneDto.dataFineValidita > now() or a.utenteCollocazioneDto.dataFineValidita is null) ");
		stringBuilder.append("and a.dataInizioValidita < now() "
				+ "and (a.dataFineValidita > now() or a.dataFineValidita is null) ");
		
		Query query = entityManager.createQuery(stringBuilder.toString());
		
		query.setParameter("idUtente", idUtente);
		if (collocazione != null) {
			query.setParameter("collocazione", collocazione);
		} else {
			query.setParameter("codiceAzienda", codiceAzienda);
		}

		return query.getResultList();
		
	}

	@Override
	public List<ApplicazioneDto> findByCodiceAndBloccoModificaS(String codiceApp) {
		
		Query query = entityManager.createQuery("from ApplicazioneDto a "
				+ "WHERE a.codice = :codice "
				+ "and a.flagApiBloccoModifica = 'S' ");
		
		query.setParameter("codice", codiceApp);
		
		return query.getResultList();
	}

}
