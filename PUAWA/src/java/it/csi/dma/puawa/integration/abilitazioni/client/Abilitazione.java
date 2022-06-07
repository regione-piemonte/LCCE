/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.abilitazioni.client;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dmac.csi.it/")
public class Abilitazione {

	private Applicazione applicazione;

	public Abilitazione() {
	}

	public Abilitazione(Applicazione applicazione) {
		this.applicazione = applicazione;
	}

	public Applicazione getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(Applicazione applicazione) {
		this.applicazione = applicazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicazione == null) ? 0 : applicazione.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abilitazione other = (Abilitazione) obj;
		if (applicazione == null) {
			if (other.applicazione != null)
				return false;
		} else if (!applicazione.equals(other.applicazione))
			return false;
		return true;
	}
}
