/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;

@Component
public class BatchAbilitazioneMassivaLowDaoImpl extends EntityBaseLowDaoImpl<BatchAbilitazioneMassivaDto, Long> implements BatchAbilitazioneMassivaLowDao {

    @Override
    public BatchAbilitazioneMassivaDto salva(BatchAbilitazioneMassivaDto utenteDto) {
        return insert(utenteDto);
    }

    @Override
    public void modifica(BatchAbilitazioneMassivaDto utenteDto) {
        update(utenteDto);
    }

	@Override
	public List<BatchAbilitazioneMassivaDto> findByCfOperatore(String codiceFiscale,boolean abilitazione) {
		return entityManager.createQuery(
				"Select s "
				+ "From BatchAbilitazioneMassivaDto s "
				+ "WHERE s.codiceFiscaleOperatore = :codiceFiscale AND s.disabilitazione= :abilitazione "
				+ "ORDER BY s.dataInserimento desc",BatchAbilitazioneMassivaDto.class)
				.setParameter("codiceFiscale", codiceFiscale)
				.setParameter("abilitazione", abilitazione)
				.getResultList();
				
	}
	
	@Override
	public List<BatchAbilitazioneMassivaDto> findBatchByStatoAndByCfOperatore(String codiceFiscale,List<String> stati) {
		
		return entityManager.createQuery(
				"Select s "
				+ "From BatchAbilitazioneMassivaDto s "
				+ "WHERE s.codiceFiscaleOperatore = :codiceFiscale AND s.statoBatch.statoBatch IN ( :stati) "
				+ " AND s.dataFine IS NULL ",BatchAbilitazioneMassivaDto.class)
				.setParameter("codiceFiscale", codiceFiscale)
				.setParameter("stati", stati)
				.getResultList();
				
	}

	



  

	
}
