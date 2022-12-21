/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.collocazioni;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace="http://dmac.csi.it/")
public class Collocazione {
	
	private String colCodAzienda;
	private String colDescAzienda;
	private String colCodice;
	private String colDescrizione;
	
	public Collocazione() {}
	
	public Collocazione(String colCodAzienda, String colDescAzienda, String colCodice, String colDescrizione) {
		this.colCodAzienda=colCodAzienda;
		this.colDescAzienda=colDescAzienda;
		this.colCodice=colCodice;
		this.colDescrizione=colDescrizione;
	}
	
	public String getColCodAzienda() {
		return colCodAzienda;
	}
	public void setColCodAzienda(String colCodAzienda) {
		this.colCodAzienda = colCodAzienda;
	}
	public String getColDescAzienda() {
		return colDescAzienda;
	}
	public void setColDescAzienda(String colDescAzienda) {
		this.colDescAzienda = colDescAzienda;
	}
	public String getColCodice() {
		return colCodice;
	}
	public void setColCodice(String colCodice) {
		this.colCodice = colCodice;
	}
	public String getColDescrizione() {
		return colDescrizione;
	}
	public void setColDescrizione(String colDescrizione) {
		this.colDescrizione = colDescrizione;
	}
}
