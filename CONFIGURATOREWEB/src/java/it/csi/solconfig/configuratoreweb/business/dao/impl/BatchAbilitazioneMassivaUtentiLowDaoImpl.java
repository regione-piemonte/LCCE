/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaUtentiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaUtentiDto;

@Component
public class BatchAbilitazioneMassivaUtentiLowDaoImpl extends EntityBaseLowDaoImpl<BatchAbilitazioneMassivaUtentiDto, Long> implements BatchAbilitazioneMassivaUtentiLowDao {

    @Override
    public BatchAbilitazioneMassivaUtentiDto salva(BatchAbilitazioneMassivaUtentiDto utenteDto) {
        return insert(utenteDto);
    }

    @Override
    public void modifica(BatchAbilitazioneMassivaUtentiDto utenteDto) {
        update(utenteDto);
    }

	@Override
	public String progressoBatch(Long idbatch) {
		
		Long completi= entityManager.createQuery(
				"Select count(s) "
				+ " From BatchAbilitazioneMassivaUtentiDto s JOIN s.idBatch a " 
				+ " WHERE  "
				+ "  a.id = :idBatch AND  "
				+ " s.dataFine IS NOT NULL ",Long.class)
				.setParameter("idBatch", idbatch)
				.getSingleResult();
		
		Long totale= entityManager.createQuery(
				"Select count(s) "
				+ " From BatchAbilitazioneMassivaUtentiDto s JOIN s.idBatch a " 
				+ " WHERE "
				+ "  a.id = :idBatch   ",Long.class)
				.setParameter("idBatch", idbatch)
				.getSingleResult();
		
		return completi.toString() +"/"+ totale.toString();
				
	}

	@Override
	public List<BatchAbilitazioneMassivaUtentiDto> findByIdBatch(Long idBatch) {
		return  entityManager.createQuery(
				"Select s "
				+ " From BatchAbilitazioneMassivaUtentiDto s JOIN s.idBatch a " 
				+ " WHERE  "
				+ "  a.id = :idBatch "	,BatchAbilitazioneMassivaUtentiDto.class)
				.setParameter("idBatch", idBatch)
				.getResultList();
	}

	
}
