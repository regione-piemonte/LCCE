/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.PermessoFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PermessoFunzionalitaDto;

@Component
public class PermessoFunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<PermessoFunzionalitaDto, Long> implements PermessoFunzionalitaLowDao {

	@Override
	public List<PermessoFunzionalitaDto> findPermessiByFunzId(Long funzId) {
		StringBuffer hql = new StringBuffer("select permessoFunzionalita from PermessoFunzionalitaDto as permessoFunzionalita ");
		hql.append(" inner join fetch permessoFunzionalita.permesso ");
		hql.append(" inner join fetch permessoFunzionalita.tipologiaDato ");
		hql.append( " where 1=1 ");
		hql.append(" and permessoFunzionalita.funzionalita.idFunzione=:idFunzione");
		hql.append(" and permessoFunzionalita.dataInizioValidita < now() ");
		hql.append(" and (permessoFunzionalita.dataFineValidita > now() or permessoFunzionalita.dataFineValidita is null) ");
		
		Query q = entityManager.createQuery(hql.toString());
		q.setParameter("idFunzione", funzId);
		return (List<PermessoFunzionalitaDto>)  q.getResultList();
	}

}
