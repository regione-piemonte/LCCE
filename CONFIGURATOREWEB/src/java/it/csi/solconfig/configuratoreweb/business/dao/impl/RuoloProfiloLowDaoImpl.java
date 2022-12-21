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

import it.csi.solconfig.configuratoreweb.business.dao.RuoloProfiloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CredenzialiServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;

@Component
public class RuoloProfiloLowDaoImpl extends EntityBaseLowDaoImpl<RuoloProfilo, Long> implements RuoloProfiloLowDao {
	
	@Override
	public Collection<RuoloProfilo> findByIdRuolo(Long idRuolo) {
		Query query = entityManager.createNativeQuery("select rp_id, id_ruolo, fnz_id, data_inizio_validita, data_fine_validita from auth_r_ruolo_profilo t WHERE t.id_ruolo = :idRuolo ");
		query.setParameter("idRuolo", idRuolo);
		return getResultList(query, RuoloProfilo.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public Collection<RuoloProfilo> findByIdProfilo(Long idProfilo) {
		Query query = entityManager.createNativeQuery("select rp_id, id_ruolo, fnz_id, data_inizio_validita, data_fine_validita from auth_r_ruolo_profilo t WHERE t.rp_id = :idProfilo ");
		query.setParameter("idProfilo", idProfilo);
		return getResultList(query, RuoloProfilo.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public Collection<RuoloProfilo> findByIdFunz(Long idFunz) {
		Query query = entityManager.createNativeQuery("select rp_id, id_ruolo, fnz_id, data_inizio_validita, data_fine_validita from auth_r_ruolo_profilo t WHERE t.fnz_id = :idFunz ");
		query.setParameter("idFunz", idFunz);
		return getResultList(query, RuoloProfilo.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public List<RuoloProfilo> findByIdFunzIdRuolo(Long idRuolo, Long idFunz) {
		Query query = entityManager.createNativeQuery("select rp_id, id_ruolo, fnz_id, data_inizio_validita, data_fine_validita from auth_r_ruolo_profilo t WHERE t.fnz_id = :idFunz and t.id_ruolo = :idRuolo ");
		query.setParameter("idFunz", idFunz);
		query.setParameter("idRuolo", idRuolo);
		return getResultList(query, RuoloProfilo.class);
		//return query.get  .getResultList();
	}
	
	 public String getTabName(Object className) {
        return className.getClass().getName();
    }

}