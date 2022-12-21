/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.abilitazione;

import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlType(namespace="http://dmac.csi.it/")
public class Abilitazione {

	private Applicazione applicazione;

	public Applicazione getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(Applicazione applicazione) {
		this.applicazione = applicazione;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Abilitazione that = (Abilitazione) o;
		return getApplicazione().equals(that.getApplicazione());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getApplicazione());
	}
}
