/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


public class IdentitaCallDto extends BaseDto {
	
	private it.csi.dma.dmaloginccebl.iride.data.Identita identita;
	
	private String xmlIn;

	private String xmlOut;
	
	private org.apache.axis.AxisFault axisFaultException;
	
	private Exception genericException;
	
	

	public Exception getGenericException() {
		return genericException;
	}

	public void setGenericException(Exception genericException) {
		this.genericException = genericException;
	}

	public org.apache.axis.AxisFault getAxisFaultException() {
		return axisFaultException;
	}

	public void setAxisFaultException(org.apache.axis.AxisFault axisFaultException) {
		this.axisFaultException = axisFaultException;
	}

	public it.csi.dma.dmaloginccebl.iride.data.Identita getIdentita() {
		return identita;
	}

	public void setIdentita(it.csi.dma.dmaloginccebl.iride.data.Identita identita) {
		this.identita = identita;
	}

	public String getXmlIn() {
		return xmlIn;
	}

	public void setXmlIn(String xmlIn) {
		this.xmlIn = xmlIn;
	}

	public String getXmlOut() {
		return xmlOut;
	}

	public void setXmlOut(String xmlOut) {
		this.xmlOut = xmlOut;
	}


	

}
