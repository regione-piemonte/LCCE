/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import java.util.Collection;

import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;

public interface UtenteLowDao extends EntityBaseLowDao<UtenteDto, Long> {

	public Collection<UtenteDto> findByCodiceFiscale(String codiceFiscale);

	public void updateUltimoAccessoPUAByCF(String codiceFiscale);
}
