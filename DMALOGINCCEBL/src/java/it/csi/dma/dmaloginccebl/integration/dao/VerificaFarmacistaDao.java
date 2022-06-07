/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao;

import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.VerificaFarmacistaRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

public interface VerificaFarmacistaDao {

	public void ricercaFarmaciaAderente(VerificaFarmacistaRequest parameters, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) throws Exception;

	public void verificaFunzionalitaFarmaciaAbituale(VerificaFarmacistaRequest parameters, List<Errore> errori,
			LogGeneralDaoBean logGeneralDaoBean) throws Exception;
}
