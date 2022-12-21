/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.util;

public class Constants {
	// Types
	public final static String STRING_TYPE = "STRING";
	public final static String NUMERIC_TYPE = "NUMERIC";		

	//operations
	public final static String OPERAZIONE_GET	= "READ";
	public final static String OPERAZIONE_POST 	= "INSERT/UPDATE";
	public final static String OPERAZIONE_DELETE= "UPDATE";
	public final static String KEY_OPER_OK = "OK";
	public final static String KEY_OPER_KO = "KO";
	
	public static final String ERROR_CODE_INTERNO_SERVER = null;
	public static final String BATCH_STATO_DA_ELABORARE = "DAELAB";
	public static final String BATCH_STATO_IN_ELABORAZIONE = "INELAB";
	public static final String BATCH_STATO_COMPLETATO = "COMPL";
	public static final String BATCH_STATO_COMPLETATO_CON_ERRORI = "COMPLERR";
	
	public static final String MAIL_SUBJECT = "CONF_MAIL_OGGETTO";
	public static final String MAIL_BODY = "CONF_MAIL_CORPO";
	public static final String MAIL_FOOTER = "CONF_MAIL_FOOTER";
	
	public static final String CONF_MAIL_EMAIL_AURA = "EMAIL_SERV_AURA";
	public static final String CONF_MAIL_OGGETTO_AURA = "OGG_MAIL_SERV_AURA";
	public static final String CONF_MAIL_CORPO_AURA = "TESTO_MAIL_SERV_AURA";
	public static final String CONF_MAIL_ELENCO_AURA = "TESTO_ELENCO_SERV_AURA";
	public static final String CONF_MAIL_USER_ABI = "CONF_MAIL_CORPO_ABI";
	public static final String CONF_MAIL_USER_DISABI = "CONF_MAIL_CORPO_DISABI";
	
	public static final String ERR_MASS_UTENTE_ABILITATO = "MASS_ERR_001";
	public static final String ERR_MASS_UTENTE_NON_ABILITATO = "MASS_ERR_002";
	public static final String ERR_MASS_GENERICO = "MASS_ERR_003";
	
	public static final String STRING_SEPARATOR = " - ";
	public static final String CSV_SEPARATOR = ";";
	public static final String CSV_NEW_LINE = System.getProperty("line.separator");

}
