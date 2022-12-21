/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.bean;

import java.io.Serializable;

public class InfoTicketRemedy implements Serializable{

	public static final String STATO_IN_CORSO = "IN CORSO";
	public static final String STATO_RISOLTO = "RISOLTO";
	public static final String STATO_PENDING = "PENDING";
	public static final String STATO_ANNULLATO = "ANNULLATO";

	public static final String MOTIVO_STATO_INOLTRATO = "INOLTRATO";
	public static final String MOTIVO_STATO_RICHIESTA_INFORMAZIONI = "RICHIESTA INFORMAZIONI";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ticketId;
	private String stato;
	private String statoPrecedente;
	private String motivoStato;
	private String testoRisoluzione;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStatoPrecedente() {
		return statoPrecedente;
	}

	public void setStatoPrecedente(String statoPrecedente) {
		this.statoPrecedente = statoPrecedente;
	}

	public String getMotivoStato() {
		return motivoStato;
	}

	public void setMotivoStato(String motivoStato) {
		this.motivoStato = motivoStato;
	}

	public String getTestoRisoluzione() {
		return testoRisoluzione;
	}

	public void setTestoRisoluzione(String testoRisoluzione) {
		this.testoRisoluzione = testoRisoluzione;
	}
}
