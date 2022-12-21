/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class CredenzialiRuparModel extends RicercaModel {
	
	private String cfUtente;
	private String cfOperatore;
	private String dataRichiestaDa;
	private String dataRichiestaA;
	private String ticketRemedy;

	public String getCfUtente() {
		return cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getDataRichiestaDa() {
		return dataRichiestaDa;
	}

	public void setDataRichiestaDa(String dataRichiestaDa) {
		this.dataRichiestaDa = dataRichiestaDa;
	}

	public String getDataRichiestaA() {
		return dataRichiestaA;
	}

	public void setDataRichiestaA(String dataRichiestaA) {
		this.dataRichiestaA = dataRichiestaA;
	}

	public String getTicketRemedy() {
		return ticketRemedy;
	}

	public void setTicketRemedy(String ticketRemedy) {
		this.ticketRemedy = ticketRemedy;
	}
	
	
}
