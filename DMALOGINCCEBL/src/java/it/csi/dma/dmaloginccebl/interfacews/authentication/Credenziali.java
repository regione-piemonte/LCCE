/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authentication;

import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author DXC
 * 
 */
@XmlType(namespace="http://dma.csi.it/")
public class Credenziali {
	
    protected String username;
    protected String password;
    protected String PIN;
    
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
    

}
