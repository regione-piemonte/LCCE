/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.RuoloCompatibilitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloProfiloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloSelezionabileLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CredenzialiServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloCompatibilita;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;

@Component
public class RuoloSelezionabileLowDaoImpl extends EntityBaseLowDaoImpl<RuoloSelezionabileDto, Long> implements RuoloSelezionabileLowDao {

	@Override
	public List<RuoloSelezionabileDto> findByIdRuolo(Long idRuolo) {
		Query query = entityManager.createNativeQuery("select id, id_ruolo_operatore, id_ruolo_selezionabile, col_tipo_id, data_inizio_val, data_fine_val from auth_r_ruolo_ruoli t WHERE t.id_ruolo_operatore = :idRuolo ");
		query.setParameter("idRuolo", idRuolo);
		return getResultList(query, RuoloSelezionabileDto.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public List<RuoloSelezionabileDto> findByIdRuoloSelezionabile(Long idRuoloSelez) {
		Query query = entityManager.createNativeQuery("select id, id_ruolo_operatore, id_ruolo_selezionabile, col_tipo_id, data_inizio_val, data_fine_val from auth_r_ruolo_ruoli t WHERE t.id_ruolo_selezionabile = :idRuoloSelez ");
		query.setParameter("idRuoloSelez", idRuoloSelez);
		return getResultList(query, RuoloSelezionabileDto.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public List<RuoloSelezionabileDto> findByIdRuoli(Long idRuolo, Long idRuoloSelez) {
		Query query = entityManager.createNativeQuery("select id, id_ruolo_operatore, id_ruolo_selezionabile, col_tipo_id, data_inizio_val, data_fine_val from auth_r_ruolo_ruoli t WHERE t.id_ruolo_operatore = :idRuolo AND t.id_ruolo_selezionabile = :idRuoloSelez ");
		query.setParameter("idRuolo", idRuolo);
		query.setParameter("idRuoloSelez", idRuoloSelez);
		List<RuoloSelezionabileDto> rtn = getResultList(query, RuoloSelezionabileDto.class);
		return rtn;
		//return query.get  .getResultList();
	}
	
	 public String getTabName(Object className) {
        return className.getClass().getName();
    }

}