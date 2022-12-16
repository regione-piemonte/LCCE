/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;

public interface MessaggiXmlLowDao extends EntityBaseLowDao<MessaggiXmlDto, Long> {

	public void updateIdMessaggio(MessaggiXmlDto obj);
}
