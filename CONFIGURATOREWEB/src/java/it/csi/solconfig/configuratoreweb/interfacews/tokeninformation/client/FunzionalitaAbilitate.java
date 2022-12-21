/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * 
 * @author DXC
 * 
 */
@XmlType(namespace="http://dma.csi.it/")
public class FunzionalitaAbilitate {
	
	protected List<Funzionalita> funzionalita;

	public List<Funzionalita> getFunzionalita() {
		return funzionalita;
	}

	public void setFunzionalita(List<Funzionalita> funzionalita) {
		this.funzionalita = funzionalita;
	}
}
