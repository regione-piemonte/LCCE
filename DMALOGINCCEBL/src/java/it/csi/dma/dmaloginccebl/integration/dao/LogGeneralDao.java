/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao;

import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoLogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziRichiamatiXmlDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;

public interface LogGeneralDao {

	void logStart(LogGeneralDaoBean logGeneralDaoBean, Object... params);

    void logEnd(LogGeneralDaoBean logGeneralDaoBean, AbilitazioneDto abilitazioneDto,
                ServiceResponse response, String token, String xmlOut, Object... params);

    Errore logErrore(LogDto logDto, String codiceErrore, Object... parametri);

    void logAudit(LogAuditDto auditDto, ApplicazioneDto applicazioneDto,
                  AbilitazioneDto abilitazioneDto, UtenteDto utenteDto,
                  RuoloDto ruoloDto, String token, String codiceLog, Object... params);
    
    void registraXmlServiziRichiamati(LogGeneralDaoBean logGeneralDaoBean, String xmlIn, String xmlOut,
									  ServiziDto serviziDto, String esito);

	void logXml(MessaggiDto messaggiDto, String xmlIn, String xmlOut);
	
	CatalogoLogAuditDto getCatalogoLogAudit(String codice);

	ServiziRichiamatiXmlDto getServiziRichiamatiXmlDto(LogGeneralDaoBean logGeneralDaoBean, String xmlIn, String xmlOut,
			ServiziDto serviziDto, String esito);

	void registraXmlServiziRichiamati(ServiziRichiamatiXmlDto serviziRichiamatiXmlDto);
	
}