/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.TreeFunzionalitaDto;

import java.util.List;

public interface TreeFunzionalitaLowDao extends EntityBaseLowDao<TreeFunzionalitaDto, Long> {
    List<TreeFunzionalitaDto> findFunzionalitaForTokenInfo2(String codiceFiscaleRichiedente, String ruoloRichiedente,
                                                            String codiceApplicazione, String codiceCollocazione, String codiceAzienda);

    List<TreeFunzionalitaDto> findFunzionalitaSons(TreeFunzionalitaDto treeFunzionalitaDto);

    List<TreeFunzionalitaDto> findFunzionalitaParents(TreeFunzionalitaDto treeFunzionalitaDto);
}
