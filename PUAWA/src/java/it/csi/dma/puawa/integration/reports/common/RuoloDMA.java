/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;

@XmlRootElement()
@XmlType(namespace = "http://dma.csi.it/")
public class RuoloDMA extends Codifica {

	private static final long serialVersionUID = -7737073108475761125L;

	private AziendaSanitaria aziendaSanitaria;
	private Profilo profilo;

	@XmlElement(namespace = "http://dma.csi.it/")
	private String flagVisibilePerConsenso;

	public RuoloDMA() {
		super();
	}

	public RuoloDMA(String codice, String descrizione) {
		super(codice, descrizione);
	}

	public AziendaSanitaria getAziendaSanitaria() {
		return aziendaSanitaria;
	}

	public void setAziendaSanitaria(AziendaSanitaria aziendaSanitaria) {
		this.aziendaSanitaria = aziendaSanitaria;
	}

	public Profilo getProfilo() {
		return profilo;
	}

	public void setProfilo(Profilo profilo) {
		this.profilo = profilo;
	}

	public String getFlagVisibilePerConsenso() {
		return flagVisibilePerConsenso;
	}

	public void setFlagVisibilePerConsenso(String flagVisibilePerConsenso) {
		this.flagVisibilePerConsenso = flagVisibilePerConsenso;
	}
}
