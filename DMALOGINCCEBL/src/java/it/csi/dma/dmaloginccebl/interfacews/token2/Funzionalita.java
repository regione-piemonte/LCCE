/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token2;

import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author DXC
 * 
 */
@XmlType(namespace="http://dma.csi.it/")
public class Funzionalita {
	
	protected String codiceFunzionalita;
	protected String descrizioneFunzionalita;
	protected String codiceFunzionalitaPadre;
	protected String descrizioneFunzionalitaPadre;

	public String getCodiceFunzionalita() {
		return codiceFunzionalita;
	}

	public void setCodiceFunzionalita(String codiceFunzionalita) {
		this.codiceFunzionalita = codiceFunzionalita;
	}

	public String getDescrizioneFunzionalita() {
		return descrizioneFunzionalita;
	}

	public void setDescrizioneFunzionalita(String descrizioneFunzionalita) {
		this.descrizioneFunzionalita = descrizioneFunzionalita;
	}

	public String getCodiceFunzionalitaPadre() {
		return codiceFunzionalitaPadre;
	}

	public void setCodiceFunzionalitaPadre(String codiceFunzionalitaPadre) {
		this.codiceFunzionalitaPadre = codiceFunzionalitaPadre;
	}

	public String getDescrizioneFunzionalitaPadre() {
		return descrizioneFunzionalitaPadre;
	}

	public void setDescrizioneFunzionalitaPadre(String descrizioneFunzionalitaPadre) {
		this.descrizioneFunzionalitaPadre = descrizioneFunzionalitaPadre;
	}
}
