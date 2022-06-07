/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

public class Farmacia {
	
	private String codice;

	private String partitaIVA;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codiceFarmacia) {
		this.codice = codiceFarmacia;
	}

	public String getPartitaIVA() {
		return partitaIVA;
	}

	public void setPartitaIVA(String ivaFarmacia) {
		this.partitaIVA = ivaFarmacia;
	}
}