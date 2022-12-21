/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.bean;

import java.io.Serializable;

public class ParametriTicketRemedy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String personId;
	private String tipologia;
	private String livello1;
	private String livello2;
	private String livello3;
	private String impatto;
	private String urgenza;
	
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getLivello1() {
		return livello1;
	}
	public void setLivello1(String livello1) {
		this.livello1 = livello1;
	}
	public String getLivello2() {
		return livello2;
	}
	public void setLivello2(String livello2) {
		this.livello2 = livello2;
	}
	public String getLivello3() {
		return livello3;
	}
	public void setLivello3(String livello3) {
		this.livello3 = livello3;
	}
	public String getImpatto() {
		return impatto;
	}
	public void setImpatto(String impatto) {
		this.impatto = impatto;
	}
	public String getUrgenza() {
		return urgenza;
	}
	public void setUrgenza(String urgenza) {
		this.urgenza = urgenza;
	}
	
	
}
