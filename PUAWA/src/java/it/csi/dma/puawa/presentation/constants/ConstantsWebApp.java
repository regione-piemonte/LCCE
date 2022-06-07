/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.constants;

public class ConstantsWebApp {

	/** Generic Constants **/
	public static String PUAWA = "PUA";

	/** CodiceLog **/
	public static String SCELTA_RUOLI_LOG = "AUTH_LOG_070"; // E' stato selezionato il Ruolo
	public static String SCELTA_COLLOCAZIONI_LOG = "AUTH_LOG_080"; // E' stata selezionata la collocazione
	public static String SCELTA_APPLICATIVO_LOG = "AUTH_LOG_050"; // E' stato richiesto l'applicativo
	public static String REPORTISTICA_LOG = "AUTH_LOG_060"; // E' stata attivata la funzione di Reportistica
	public static String GENERA_REPORT_LOG = "AUTH_LOG_090"; // E' stato richiesto il Report %s, Da: %s A: %s per la
																// collocazione %s
	public static String LOGIN_PUA_LOG = "AUTH_LOG_110";
	public static String LOGOUT_PUA_LOG = "AUTH_LOG_120";
	public static String NOTIFICA_CITTADINO_LOG = "AUTH_LOG_130";

	/** Pages **/
	public static String ERROR = "error";
	public static String HOME_PAGE = "homePage";
	public static String SCELTA_RUOLI = "sceltaRuoli";
	public static String REPORTISTICA = "reportistica";
	public static String REDIRECT_SCELTA_RUOLI = "redirect:/sceltaRuoli";
	public static String REDIRECT_HOME = "redirect:/home";
	public static String REDIRECT_REPORTISTICA = "redirect:/reportistica";
	public static String NOTIFICA = "notifica";
	public static String SESSION_TIMEOUT = "sessionTimeout";
}
