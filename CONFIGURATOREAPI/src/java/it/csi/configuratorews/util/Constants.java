/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.util;

public class Constants {
	public static final String COMPONENT_NAME = "configuratoreapi";
	public static final String RUOLO_MONITORAGGIO = "monitoring";
	public static final String APPLICATION_CODE = "CONF_API";

	public static final String PATTERN_DATE_yyyyMMddHHmmssSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_DATE_yyyyMMddHHmmssSSS2 = "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_DATE_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_yyyyMMddHHmmss2 = "yyyy/MM/dd HH:mm:ss";
	public static final String PATTERN_DATE_yyyyMMddHH = "yyyy-MM-dd";
	public static final String PATTERN_DATE_yyyyMMddHH2 = "yyyy/MM/dd";

	public static final String SHIB_IDENTITA_DIGITALE = "ShibIdentitaDigitale";
	public static final String X_CODICE_SERVIZIO = "xCodiceServizio";
	public static final String X_REQUEST_ID = "xRequestId";
	public static final String X_FORWARED_FOR = "xForwarderFor";

	/*
	 * QUERY SUPPORT
	 * 
	 */
	public static final String FUNZIONALITA_TIPO_FUNZ = "FUNZ";
	public static final String FUNZIONALITA_TIPO_PROFILO = "PROF";
	public static final String CODICE_TIPO_FUNZIONALITA_APP = "APP";
	/*
	 * Key Operation per log audit
	 */

	public static final String PROXY_ABI = "PROXY_ABI";
	public static final String GET_ABI = "GET_ABI";
	public static final String PROXY_AUTHENTICATION = "PROXY_AUTH";
	public static final String PROXY_RUOLO_UTENTE = "PROXY_RUO";
	public static final String PROXY_COLLOCAZIONE = "PROXY_COLL";
	public static final String PROXY_INFORMATION = "PROXY_TOK";
	public static final String GET_UTE_SOL = "GET_UTE_SOL";
	public static final String GET_RUO_UTE = "GET_RUO_UTE";
	public static final String GET_COLL_SOL = "GET_COLL_SOL";
	public static final String GET_RUO = "GET_RUO";

	/*
	 * codice errore
	 */

	/**
	 * Errore Genertico:{0}
	 */
	public static final String AUTH_ER_000 = "AUTH_ER_000";
	public static final String ERR_999 = "ERR-999";

	
	/*
	 * CODICI ERRORE SPECIFICI
	 */

	public static final String PARAMETRO_NON_VALORIZZATO = "CONF-ERR-001";
	public static final String PARAMETRO_NON_VALIDO = "CONF-ERR-002";
	public static final String RUOLO_NON_VISIB_DA_CONF = "CONF_ERR_004";
	public static final String ABILITAZIONE_NON_TROVATA = "CONF-ERR-005";

	public static final String CODICE_FISCALE_OBBLIGATORIO = "AUTH_ER_610";
	public static final String CODICE_FISCALE_ERRATO = "AUTH_ER_612";
	public static final String RUOLO_OBBLIGATORIO = "AUTH_ER_511";
	public static final String RUOLO_ERRATO = "AUTH_ER_502";
	// CAMBIARE IL CODICE ERRORE
	public static final String COLLOCAZIONE_ERRATA = "AUTH_ER_639";
	public static final String PROFILO_ERRATO = "AUTH_ER_640";
	public static final String FUNZIONALITA_ERRATA = "AUTH_ER_641";
	public static final String FUNZIONALITA_SENZA_PROFILO = "AUTH_ER_642";
	public static final String SISTEMA_NON_PRESENTE = "AUTH_ER_635";
	public static final String APPLICAZIONE_ERRATA = "AUTH_ER_507";
	public static final String PROFILO_O_FUNZIONALITA_ASSENTE = "AUTH_ER_636";
	public static final String FUNZIONALITA_TIPO_ERRORE = "AUTH_ER_637";
	public static final String DATA_FINE_VALIDITA_ERRORE = "AUTH_ER_638";
	public static final String PROFILO_GIA_PRESENTE_PER_APPLICATIVO = "AUTH_ER_643";
	public static final String PROFILO_NON_PRESENTE_PER_APPLICATIVO = "AUTH_ER_645";
	public static final String FUNZIONALITA_GIA_PRESENTE_PER_APPLICATIVO = "AUTH_ER_644";
	public static final String CAMPO_AZIONE_POSSIBILI = "AUTH_ER_646";
	public static final String CAMPO_AZIONE_MANCA_CODICE_PROFILO = "AUTH_ER_647";
	public static final String CAMPO_AZIONE_MANCA_CODICE_FUNZIONALITA = "AUTH_ER_648";
	public static final String CAMPO_AZIONE_SIA_PROFILO_SIA_FUNZIONALITA = "AUTH_ER_649";
	public static final String CODICE_FUNZIONALITA_SENZA_PROFILO = "AUTH_ER_650";
	public static final String CODICE_AZIENDA_ERRATO="AUTH_ER_651";
	public static final String CODICE_STRUTTURA_ERRATO="AUTH_ER_652";
	public static final String LISTA_CODICI_PROFILO_ASSENTE = "AUTH_ER_655";
	public static final String VALORIZZAZIONE_PROFILO_O_FUNZIONALITA = "AUTH_ER_654";
	public static final String TIPOLOGIA_DATO_NON_VALIDO = "AUTH_ER_656";
	public static final String PERMESSO_NON_VALIDO = "AUTH_ER_657";
	public static final String TIPOLOGIE_DATO_MANCANTI = "AUTH_ER_658";	 
	public static final String CODICE_FISCALE_NON_VALIDO = "AUTH_ER_653";	 
	public static final String LISTA_CODICI_RUOLI_ASSENTE = "AUTH_ER_659";	 
	public static final String CODICI_RUOLO_NON_VALIDI = "AUTH_ER_660";	
	public static final String ABILITAZIONI_ATTIVE_PER_PROFILO = "AUTH_ER_661";	
	public static final String SISTEMA_NON_VALIDO = "AUTH_ER_663";
	public static final String COLLOCAZIONE_NON_VALIDA_PER_AZIENDA = "AUTH_ER_664";
	public static final String LISTA_TIPOLOGIA_DATI_PERMESSI_ASSENTE = "AUTH_ER_665";
	public static final String ERRORE_LEGAME_AZIENDA_APPLICAZIONE = "AUTH_ER_666";
	public static final String APPLICAZIONE_NON_DISPONIBILE = "AUTH_ER_667";
	public static final String ABILITAZIONE_NON_ATTIVA = "AUTH_ER_668";
	
	
	/*
	 * Parametri codice errore
	 */

	public static final String CODICE_RUOLO = "Codice Ruolo";
	public static final String CODICE_COLLOCAZIONE = "Codice Collocazione";
	public static final String CODICE_AZIENDA = "Codice Azienda";
	public static final String APPLICAZIONE = "Applicazione";
	public static final String CODICE_PROFILO = "Codice Profilo";
	public static final String CODICE_FUNZIONALITA = "Codice Funzionalita'";
	public static final String CODICE_PROF_OR_FUNZ="Codice Profilo o Funzionalita'";
	public static final String FUNZIONALITA_TIPO="Tipo Funzionalita'";
	public static final String CODICE_APPLICAZIONE="Codice Applicazione";
	public static final String CODICE_TIPOLOGIA_DATO="Codice Tipologia Dato";
	public static final String CODICE_PERMESSO="Codice Permesso";
	public static final String AZIENDA = "Azienda";
	public static final Object PARAMETRI_LOGIN = "Parametri Login";
	public static final Object PARAMETRI_AUTENTICAZIONE = "Parametri Autentificazione";
	public static final Object TOKEN = "Token";
	public static final String CODICE_STRUTTURA="Codice Struttura";
	public static final String LIMIT="limit";
	public static final String OFFSET="offset";
	public static final String CODICE_FISCALE = "Codice Fiscale";
	
	//
	public static final String IDMSG = "idmsg";

	private Constants() {
	}

}
