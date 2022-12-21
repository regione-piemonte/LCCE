/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.exception;

public class BatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 953993072016874712L;
	
	private String msg;

	public String getMsg() {
		return msg;
	}

	public BatchException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	
	

}
