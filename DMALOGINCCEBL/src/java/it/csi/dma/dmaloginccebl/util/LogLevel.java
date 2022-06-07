/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.util;


public enum LogLevel {
	DEBUG(0), INFO(1), ERROR(2), WARN(3);
	private int codificaLog;

	private LogLevel(int codificaLog) {
		this.codificaLog = codificaLog;
	}

	public int getCodificaLog() {
		return codificaLog;
	}
	
	
}
