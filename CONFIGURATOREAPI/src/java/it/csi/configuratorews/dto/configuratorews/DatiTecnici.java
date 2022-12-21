/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Date;

public class DatiTecnici implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6959046831114051887L;
	private Long id;
	private Date inizioValidita;
	private Date fineValidita;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getInizioValidita() {
		return inizioValidita;
	}
	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}
	public Date getFineValidita() {
		return fineValidita;
	}
	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}
}
