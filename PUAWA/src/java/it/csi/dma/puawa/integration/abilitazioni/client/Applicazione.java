/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.abilitazioni.client;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dmac.csi.it/")
public class Applicazione {

	private String codiceApplicazione;

	public Applicazione() {
	}

	public Applicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}

	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Applicazione other = (Applicazione) obj;
		if (codiceApplicazione == null) {
			if (other.codiceApplicazione != null)
				return false;
		} else if (!codiceApplicazione.equals(other.codiceApplicazione))
			return false;
		return true;
	}
}
