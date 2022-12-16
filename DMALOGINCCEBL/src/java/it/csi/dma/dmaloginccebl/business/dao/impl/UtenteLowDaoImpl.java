/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.UtenteLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;

@Component
public class UtenteLowDaoImpl extends EntityBaseLowDaoImpl<UtenteDto, Long> implements UtenteLowDao{

	@Override
	public Collection<UtenteDto> findByCodiceFiscale(String codiceFiscale) {
		Query query=entityManager.createQuery("FROM UtenteDto u WHERE u.codiceFiscale=:codiceFiscale");
		query.setParameter("codiceFiscale", codiceFiscale);
		return query.getResultList();
	}
	

	public void updateUltimoAccessoPUAByCF(String codiceFiscale) {

		Query query = entityManager.createNativeQuery(
				"update auth_t_utente set ultimo_accesso_pua=Current_timestamp where codice_fiscale=:codice_fiscale");

		query.setParameter("codice_fiscale", codiceFiscale);

		query.executeUpdate();

	}
}
