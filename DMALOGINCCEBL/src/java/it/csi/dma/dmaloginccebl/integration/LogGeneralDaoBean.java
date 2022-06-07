/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration;

import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.LogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiErroreDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;

/*
 * Classe contenente i Dto utili per l'inserimento dei log
 */
public class LogGeneralDaoBean {

	LogDto logDto;
	
	LogAuditDto logAuditDto;
	
	MessaggiDto messaggiDto;
	
	MessaggiXmlDto messaggiXmlDto;
	
	List<MessaggiErroreDto> messaggiErroreDto;
	
	public LogGeneralDaoBean() {}

	public LogGeneralDaoBean(LogDto logDto, LogAuditDto logAuditDto, MessaggiDto messaggiDto,
			MessaggiXmlDto messaggiXmlDto, List<MessaggiErroreDto> messaggiErroreDto) {
		super();
		this.logDto = logDto;
		this.logAuditDto = logAuditDto;
		this.messaggiDto = messaggiDto;
		this.messaggiXmlDto = messaggiXmlDto;
		this.messaggiErroreDto = messaggiErroreDto;
	}

	public LogDto getLogDto() {
		return logDto;
	}

	public void setLogDto(LogDto logDto) {
		this.logDto = logDto;
	}

	public LogAuditDto getLogAuditDto() {
		return logAuditDto;
	}

	public void setLogAuditDto(LogAuditDto logAuditDto) {
		this.logAuditDto = logAuditDto;
	}

	public MessaggiDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}

	public MessaggiXmlDto getMessaggiXmlDto() {
		return messaggiXmlDto;
	}

	public void setMessaggiXmlDto(MessaggiXmlDto messaggiXmlDto) {
		this.messaggiXmlDto = messaggiXmlDto;
	}

	public List<MessaggiErroreDto> getMessaggiErroreDto() {
		return messaggiErroreDto;
	}

	public void setMessaggiErroreDto(List<MessaggiErroreDto> messaggiErroreDto) {
		this.messaggiErroreDto = messaggiErroreDto;
	}
	
	

}
