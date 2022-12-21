/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

import java.sql.Timestamp;

public class RichiestaCredenzialiView {

	private Long id;
	private String dataRichiesta;
	private String ticketRemedy;
	private UtenteDto utenteDto;

	private UtenteDto operatoreDto;

	public UtenteDto getOperatoreDto() {
		return operatoreDto;
	}

	public void setOperatoreDto(UtenteDto operatoreDto) {
		this.operatoreDto = operatoreDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Timestamp dataRichiesta) {
		this.dataRichiesta = Utils.timestampFormatDDMMYYYY(dataRichiesta);
	}

	public String getTicketRemedy() {
		return ticketRemedy;
	}

	public void setTicketRemedy(String ticketRemedy) {
		this.ticketRemedy = ticketRemedy;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

}
