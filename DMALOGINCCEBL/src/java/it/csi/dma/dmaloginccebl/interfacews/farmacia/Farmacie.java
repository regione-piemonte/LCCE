/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import java.util.List;

public class Farmacie {

	List<FarmaciaAderente> farmaciaAderente;
	
	public List<FarmaciaAderente> getFarmaciaAderente() {
		return farmaciaAderente;
	}

	public void setFarmaciaAderente(List<FarmaciaAderente> farmaciaAderente) {
		this.farmaciaAderente = farmaciaAderente;
	}
}
