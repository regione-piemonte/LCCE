/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.collocazioni;

import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlType(namespace="http://dmac.csi.it/")
public class Collocazione {
	
	private String colCodAzienda;
	private String colDescAzienda;
	private String colCodice;
	private String colDescrizione;
	
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Collocazione that = (Collocazione) o;
		return getColCodAzienda().equals(that.getColCodAzienda()) &&
				Objects.equals(getColDescAzienda(), that.getColDescAzienda()) &&
				getColCodice().equals(that.getColCodice()) &&
				Objects.equals(getColDescrizione(), that.getColDescrizione());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getColCodAzienda(), getColDescAzienda(), getColCodice(), getColDescrizione());
	}
}
