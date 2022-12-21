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
import it.csi.solconfig.configuratoreweb.business.dao.dto.CredenzialiServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloCompatibilita;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloProfilo;

@Component
public class RuoloCompatibilitaLowDaoImpl extends EntityBaseLowDaoImpl<RuoloCompatibilita, Long> implements RuoloCompatibilitaLowDao {

	@Override
	public List<RuoloCompatibilita> findByIdRuolo(Long idRuolo) {
		Query query = entityManager.createNativeQuery("select id_ruocomp, id_ruolo, id_ruolo_compatibile, data_inizio_val, data_fine_val from auth_r_ruolo_compatibilita t WHERE t.id_ruolo = :idRuolo ");
		query.setParameter("idRuolo", idRuolo);
		return getResultList(query, RuoloCompatibilita.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public List<RuoloCompatibilita> findByIdRuoloCompatibile(Long idRuoloComp) {
		Query query = entityManager.createNativeQuery("select id_ruocomp, id_ruolo, id_ruolo_compatibile, data_inizio_val, data_fine_val from auth_r_ruolo_compatibilita t WHERE t.id_ruolo_compatibile = :idRuoloComp ");
		query.setParameter("idRuoloComp", idRuoloComp);
		return getResultList(query, RuoloCompatibilita.class);
		//return query.get  .getResultList();
	}
	
	@Override
	public List<RuoloCompatibilita> findByIdRuoli(Long idRuolo, Long idRuoloCompatibile) {
		Query query = entityManager.createNativeQuery("select id_ruocomp, id_ruolo, id_ruolo_compatibile, data_inizio_val, data_fine_val from auth_r_ruolo_compatibilita t WHERE t.id_ruolo = :idRuolo AND t.id_ruolo_compatibile = :idRuoloComp ");
		query.setParameter("idRuolo", idRuolo);
		query.setParameter("idRuoloComp", idRuoloCompatibile);
		return getResultList(query, RuoloCompatibilita.class);
		//return query.get  .getResultList();
	}
	
	 public String getTabName(Object className) {
        return className.getClass().getName();
    }

}