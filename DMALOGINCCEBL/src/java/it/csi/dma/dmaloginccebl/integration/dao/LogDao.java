/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoLogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogDto;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

public interface LogDao {

    /*
     * Inserisce le informazioni contenute nell'oggetto in entrata tabella
     * auth_l_log Recupera dal codice inserito la descrizione del log
     *
     */
    CatalogoLogDto log(LogDto logDto, String codiceLog, Object... parametri);

    public Errore logErrore(LogDto logDto, String codiceErrore, Object... parametri);
	
	public CatalogoLogDto getCatalogoLogDto(String codiceErrore);

}
