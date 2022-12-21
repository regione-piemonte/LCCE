/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.VisibilitaAziendaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.VisibilitaAziendaDto;
import it.csi.solconfig.configuratoreweb.util.Utils;


@Component
public class VisibilitaAziendaLowDaoImpl extends EntityBaseLowDaoImpl<VisibilitaAziendaDto, Long> implements VisibilitaAziendaLowDao{

	@Override
	public VisibilitaAziendaDto findVisibilitaByIdUtenteAndIdAzienda(Integer idAzienda,Long idUtente) {
		
		try {
			Query query = entityManager.createQuery("select distinct abi from " + VisibilitaAziendaDto.class.getName() + " abi where " +
					" ((:data BETWEEN abi.dataInizioValidita AND abi.dataFineValidita) OR (abi.dataFineValidita IS NULL AND :data >= abi.dataInizioValidita)) " +
					" AND abi.utenteDto.id = :idUtente " +
					" AND abi.aziendaDto.idAzienda = :idAzienda ",VisibilitaAziendaDto.class);
			
			query.setParameter("data", Utils.sysdate());
			query.setParameter("idAzienda", idAzienda);
			query.setParameter("idUtente", idUtente);
			
			return (VisibilitaAziendaDto) query.getSingleResult();
			
		}catch(NoResultException e) {
			return null;
		}

    }
	
	@Override
    public VisibilitaAziendaDto salva(VisibilitaAziendaDto utenteDto) {
        return insert(utenteDto);
    }

    @Override
    public void modifica(VisibilitaAziendaDto utenteDto) {
        update(utenteDto);
    }
	

}
