/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

public class UtenteProfiloFunzionalita extends UtenteApplicazioni
		implements Serializable, Comparable<UtenteProfiloFunzionalita> {

	private static final long serialVersionUID = 5546787137442305703L;

	private Set<ApplicazioneProfili> applicazioni;

	public Set<ApplicazioneProfili> getApplicazioni() {
		return applicazioni;
	}

	public void setApplicazioni(Set<ApplicazioneProfili> applicazioni) {
		this.applicazioni = applicazioni;
	}

	@Override
	public int compareTo(UtenteProfiloFunzionalita o) {
		return this.getCodiceFiscale().compareTo(o.getCodiceFiscale());
	}

}
