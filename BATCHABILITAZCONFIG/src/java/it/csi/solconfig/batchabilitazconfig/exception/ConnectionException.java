/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.exception;

public class ConnectionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2844893307515788872L;

	public ConnectionException(String s){
		super(s);
	}
	
	public ConnectionException(){
		super();
	}
}
