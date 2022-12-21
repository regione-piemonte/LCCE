/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.logger;

import org.apache.log4j.Logger;

public class BatchLoggerFactory {

	public static Logger getLogger(Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz.getName());

		return logger;
	}

	public static Logger getLogger(Object obj) {
		Logger logger = Logger.getLogger(obj.getClass().getName());
		return logger;
	}
}
