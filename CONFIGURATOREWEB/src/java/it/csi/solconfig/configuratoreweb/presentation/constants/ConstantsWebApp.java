/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.constants;

public abstract class ConstantsWebApp {

    /**
     * Generic Constants
     **/
    public static final String CONFIGURATORE = "CONFIGURATORE";
    public static final String APPL_CONF = "SOLCONFIG";
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String CODICE_FONTE_OPESSAN = "OPESSAN/ARPE";
    public static final String CODICE_PRODOTTO = "CONFIGURATORE";
    public static final String CODICE_LINEA_CLIENTE = "RP-01";
    /**
     * CodiceLog
     **/
    public static final String LOGIN_PUA_LOG = "AUTH_LOG_110";
    public static final String LOGOUT_PUA_LOG = "AUTH_LOG_120";

    /** Generic Constants **/
	public static String configuratore = "CONFIGURATORE";

    /**
     * Pages
     **/
    public static final String ERROR = "error";
    public static final String REDIRECT_UTENTI = "redirect:/utenti";
    public static final String REDIRECT_PROFILI = "redirect:/profili";
    public static final String REDIRECT_MODIFICA_PROFILO = "redirect:/updateProfilo";
    public static final String REDIRECT_RUOLI = "redirect:/ruoli";
    public static final String REDIRECT_NONAUTORIZZATO = "redirect:/nonautorizzato";

    public static final String GESTIONE_UTENTI = "gestioneUtenti";
    public static final String GESTIONE_PROFILI = "gestioneProfili";
    public static final String GESTIONE_RUOLI = "gestioneRuoli";
    public static final String GESTIONE_RUPAR = "gestioneRupar";
    public static final String SESSION_TIMEOUT = "sessionTimeout";
    public static final String INSERT_UTENTE = "insertUtente";
    public static final String EDIT_UTENTE = "editUtente";
    public static final String EDIT_RUOLO = "editRuolo";
    public static final String MODIFICA_PROFILO = "modificaProfili";
    public static final String INSERISCI_PROFILI = "inserisciProfili";
    public static final String INSERISCI_RUOLI = "inserisciRuoli";
    public static final String ABILITAZIONE_MASSIVA = "abilitazioneMassiva";
    public static final String DISABILITAZIONE_MASSIVA = "disabilitazioneMassiva";
    public static final String HELP = "help";
    
    public static final String GESTIONE_APPLICAZIONI = "gestioneApplicazioni";
    public static final String INSERISCI_APPLICAZIONI = "inserisciApplicazioni";
	public static final String EDIT_APPLICAZIONE = "modificaApplicazioni";
    public static final String GESTIONE_FUNZIONALITA = "gestioneFunzionalita";
    public static final String INSERISCI_FUNZIONALITA = "inserisciFunzionalita";
	public static final String EDIT_FUNZIONALITA = "modificaFunzionalita";
	
    /**
     * CodiciErrore
     **/
    public static final String ERRORE_INTERNO = "MSGCONF001";
    public static final String CODICE_FISCALE_ERRATO = "MSGCONF002";
    public static final String UTENTE_NON_PRESENTE = "MSGCONF003";
    public static final String EMAIL_NON_PRESENTE = "MSGCONF004";
    public static final String RUOLI_NON_TROVATI = "MSGCONF005";
    public static final String RUOLO_ESISTENTE = "MSGCONF006";
    public static final String OPERAZIONE_NON_RIUSCITA = "MSGCONF007";
    public static final String OPERAZIONE_RIUSCITA = "MSGCONF008";
    public static final String PROFILI_NON_TROVATI = "MSGCONF009";
    public static final String PROFILO_ESISTENTE = "MSGCONF010";
    public static final String PROFILO_NON_SELEZIONATO = "MSGCONF011";
    public static final String RICHIESTA_NON_TROVATA = "MSGCONF012";
    public static final String DATA_NON_VALIDA = "MSGCONF032";
    public static final String ERRORE_WS_AURA = "MSGCONF013";
    public static final String ERRORE_WS_OPESSAN = "MSGCONF014";
    public static final String ERRORE_ALBERO_NOFIGLI = "MASS_ERR_003";

    public static final String ERRORE_INVIO_EMAIL = "MSGCONF022";

    public static final String SOL_MANCANTE = "MSGCONF024";
	public static final String CODICE_FISCALE_NON_PRESENTE = "MSGCONF015";

    public static final String UTENTE_PRENSENTE_IN_AURA_E_OPESSAN = "MSGCONF027";

    public static final String ERRORE_COMPILAZIONE_TESTO_MAIL = "MSGCONF030";
    public static final String OPERAZIONE_EFFETTUATA = "MSGCONF031";
    public static final String OPERAZIONE_PRESA_IN_CARICO = "MSGCONF035";
   
    public static final String MSG_ABILITAZIONE_AURA = "MSGCONF036";
    public static final String MSG_DISABILITAZIONE_AURA = "MSGCONF037";
    public static final String ERRORE_COMPILAZIONE_TESTO_MAIL_AURA = "MSGCONF038";


    public static final String CAMPO_CODICE_NON_VALORIZZATI = "MSGCONF040";
    public static final String CAMPO_DESCR_NON_VALORIZZATI = "MSGCONF039";

    public static final String ERRORE_INSERIMENTO_TICKET = "MSGCONF017";
    public static final String ERRORE_INFO_DETTAGLI = "MSGCONF018";

    public static final String ERRORE_SALVATAGGIO_RICHIESTA_CREDENZIALI_RUPAR = "MSGCONF019";

    public static final String SUCCESSO_SALVATAGGIO_RICHIESTA_CREDENZIALI_RUPAR = "MSGCONF021";
    public static final String RICHIESTA_CREDENZIALI_RUPAR_GIA_EFFETTUATA = "MSGCONF034";
    
    public static final String ANAGRAFICA_NON_PRESENTE = "MSGCONF032";
    public static final String NESSUN_UTENTE_TROVATO = "MSGCONF033";
    
    public static final String CAMPI_NON_VALORIZZATI = "MSGCONF042";
	public static final String CAMPO_CODICE_OBBLIGATORIO = "MSGCONF039";
	public static final String CAMPO_DESCRIZIONE_OBBLIGATORIO = "MSGCONF040";
	
	public static final String APPLICAZIONE_NON_TROVATA = "MSGCONF043";
	public static final String FUNZIONALITA_NON_TROVATA = "MSGCONF041";
	public static final String FUNZIONALITA_GIA_PRESENTE = "MSGCONF045";

	public static final String ERRORE_SOL_GIA_PRESENTE = "MSGCONF044";

	
    public static final String ERRORE_AURA_SEPAC_CF_NON_TROVATO = "AURA_SEPAC_ER_001";
    public static final String ERRORE_AURA_SEPAC_SOAP_FAULT = "AURA_SEPAC_ER_001";
    public static final String ERRORE_AURA_SEPAC_WS_EXCEPTION = "AURA_SEPAC_ER_001";

    public static final String ERRORE_OPESSAN_ID_AURA_NON_TROVATO = "OPESSAN_DETOP_ER_001";
    public static final String ERRORE_OPESSAN_SOAP_FAULT = "OPESSAN_DETOP_ER_002";
    public static final String ERRORE_OPESSAN_WS_EXCEPTION = "OPESSAN_DETOP_ER_003";
    
    

	
    public static final String PARAMETRO_NON_PRESENTE = "CONF-ERR-001";
    
    /**
     * Paginazione
     **/
    public static final Integer PAGINAZIONE_PRIMA_PAGINA = 1;
    public static final Integer PAGINAZIONE_20_ELEMENTI = 20;
    public static final Integer PAGINAZIONE_50_ELEMENTI = 50;
    public static final Integer PAGINAZIONE_100_ELEMENTI = 100;

    /**
     * Session Attribute
     */
    public static final String FROM_AURA = "fromAura";
    public static final String DATI_ANAGRAFICI = "datiAnagrafici";

    /**
     * Codici Servizi Esterni
     */
    public static final String AURA_SEPAC_SERVICE_CODE = "AURA_SEPAC";
    public static final String OPESSAN_DETT_OP_SERVICE_CODE = "OPESSAN_DET_OP";
    public static final String OPESSAN_DETT_OP_DIP_SERVICE_CODE = "OPESSAN_DET_OP_DIP";
    
    /**
     * Stato Batch
     */
    public static final String INELAB = "INELAB";
    public static final String DAELAB = "DAELAB";
    
    public static final String APP_CODE_SCEREV = "SCEREV";
    
    /**
     * Ogg_Oper LogAuditCSI
     */
    
    public static final String 	RICERCA_CF = "RICERCA CF";
    public static final String  INSERIMENTO_UTENTE = "INSERIMENTO NUOVO UTENTE NELLA AUTH_T_UTENTE";
    public static final String 	RICHIESTA_CREDENZIALI_RUPAR = "RICHIESTA CREDENZIALI RUPAR NELLA AUTH_T_RICHIESTA_CREDENZIALI";
    public static final String  ASSEGNA_RUOLO = "ASSEGNA RUOLO NELLA AUTH_R_RUOLO_UTENTE ";
    public static final String  RIMUOVI_RUOLO = "RIMUOVI ASSEGNAZIONE RUOLO NELLA AUTH_R_RUOLO_UTENTE";
    public static final String  ASSEGNA_COLLOCAZIONE = "ASSEGNA COLLOCAZIONE NELLA AUTH_R_UTENTE_COLLOCAZIONE";
    public static final String  RIMUOVI_COLLOCAZIONE = "RIMUOVI ASS. COLL. NELLA AUTH_R_UTENTE_COLLOCAZIONE";
    public static final String  ASSEGNA_SOL = "ASSEGNA SOL/PROFILO NELLA AUTH_R_ABILITAZIONE e AUTH_T_PREFERENZA";
    public static final String  MODIFICA_SOL = "MOD. ASS. SOL/PROFILO NELLA AUTH_R_ABILITAZIONE e AUTH_T_PREFERENZA";
    public static final String	VISUALIZZA_PROFILO = "VISUALIZZA FUNZIONALITA' DEL PROFILO";
    public static final String	INVIO_MAIL = "INVIO MAIL";
    public static final String	MODIFICA_DATI_UTENTE = "MODIFICA DATI UTENTE";
    public static final String	MODIFICA_DATI_UTENTE_SALVA = "MODIFICA DATI UTENTE NELLA AUTH_T_UTENTE";
    public static final String	DISABILITA_CONFIGURAZIONE = "DISABILITA TUTTE LE CONFIGURAZIONI NELLA AUTH_R_ABILITAZIONE e AUTH_T_PREFERENZA";
    public static final String	EXPORT_UTENTI = "EXPORT UTENTI E ABILITAZIONI";
    public static final String	RICERCA_RUOLI = "RICERCA RUOLI";
    public static final String	INSERISCI_RUOLO = "INS. RUOLO NELLA AUTH_D_RUOLO, AUTH_R_RUOLO_RUOLI, AUTH_R_RUOLI_COMPATIBILITA";
    public static final String	MODIFICA_RUOLO = "MODIFICA RUOLO NELLA AUTH_D_RUOLO, AUTH_R_RUOLO_RUOLI, AUTH_R_RUOLI_COMPATIBILITA";
    public static final String	MODIFICA_RUOLO_RELAZ_UTENTE_RUOLO = "MODIFICA RUOLO NELLA AUTH_D_RUOLO E RELAZIONE NELLA AUTH_R_RUOLO_UTENTE";
    public static final String	MODIFICA_RUOLO_RELAZ_UTENTE_RUOLO_ABILITAZIONI = "MODIFICA RUOLO NELLA AUTH_D_RUOLO E RELAZIONI NELLA AUTH_R_RUOLO_UTENTE E AUTH_R_ABILITAZIONE";
    public static final String	RICERCA_PROFILO = "RICERCA PROFILO";
    public static final String	INSERISCI_PROFILO = "INSERISCI PROFILO NELLA AUTH_ T_FUNZIONALITA, AUTH_R_FUNZIONALITA_TREE e AUTH_R_RUOLI_PROFILO";
    public static final String	MODIFICA_PROFILO_AUDIT = "MODIFICA PROFILO NELLA AUTH_T_FUNZIONALITA, AUTH_R_FUNZIONALITA_TREE e AUTH_R_RUOLI_PROFILO";
    public static final String	MODIFICA_PROFILO_AUDIT_RELAZ_UTENTE = "MODIFICA PROFILO NELLA AUTH_T_FUNZIONALITA E AUTH_R_FUNZIONALITA_TREE E RELAZIONI NELLA AUTH_R_ABILITAZIONE";
    public static final String	RICERCA_RUPAR = "RICERCA RICHIESTE CREDENZIALI RUPAR";
    public static final String	RITORNO_PUA = "NULL";
    public static final String	HELP_AUDIT = "RICHIAMO HELP";
    public static final String	ABILITAZIONE_MASSIVA_RICERCA = "RICERCA ABIL MASSIVA";
    public static final String	ABILITAZIONE_MASSIVA_UTENTI = "ABIL. MASS. UTENTI NELLA AUTH_T_BATCH_ABILITAZIONE_MASSIVA";
    public static final String	DISABILITAZIONE_MASSIVA_RICERCA = "RICERCA DISABIL MASSIVA";
    public static final String  DISABILITAZIONE_MASSIVA_UTENTI = "DISABIL. MASS. UTENTI NELLA AUTH_T_BATCH_ABILITAZIONE_MASSIVA ";
    public static final String	STORICO = "STORICO MASSIVA";
    public static final String	LOGIN = "LOGIN";
    public static final String	RICERCA_APP = "RICERCA APPLICAZIONE";
    public static final String	INSERISCI_APP = "INS. APPLICAZIONE NELLA AUTH_D_APPLICAZIONE e AUTH_R_APPLICAZIONE_COLLOCAZIONE";
    public static final String	MODIFICA_APP = "MODIFICA APPLICAZIONE NELLA AUTH_D_APPLICAZIONE e AUTH_R_APPLICAZIONE_COLLOCAZIONE";
    public static final String	RICERCA_FUNZ = "RICERCA FUNZIONALITA";
    public static final String	INSERISCI_FUNZ = "INS. FUNZIONALITA NELLA AUTH_T_FUNZIONALITA E AUTH_R_FUNZIONALITA_TREE";
    public static final String	MODIFICA_FUNZ = "MODIFICA FUNZIONALITA NELLA AUTH_T_FUNZIONALITA E AUTH_R_FUNZIONALITA_TREE";
    
    /**
     * KEY_OPERAZIONE
     */
    
    public static final String KEY_OPER_RICERCA_CF = "CU001";
    public static final String KEY_OPER_INSERIMENTO_UTENTE = "CU002";
    public static final String KEY_OPER_RICHIESTA_CREDENZIALI_RUPAR = "CU003";
    public static final String KEY_OPER_ASSEGNA_RUOLO = "CU004";
    public static final String KEY_OPER_RIMUOVI_RUOLO = "CU005";
    public static final String KEY_OPER_ASSEGNA_COLLOCAZIONE = "CU006";
    public static final String KEY_OPER_RIMUOVI_COLLOCAZIONE = "CU007";
    public static final String KEY_OPER_ASSEGNA_SOL = "CU008";
    public static final String KEY_OPER_MODIFICA_SOL = "CU009";
    public static final String KEY_OPER_VISUALIZZA_PROFILO = "CU010";
    public static final String KEY_OPER_INVIO_MAIL = "CU011";
    public static final String KEY_OPER_MODIFICA_DATI_UTENTE = "CU012_1";
    public static final String KEY_OPER_MODIFICA_DATI_UTENTE_SALVA = "CU012_2";
    public static final String KEY_OPER_DISABILITA_CONFIGURAZIONE = "CU013";
    public static final String KEY_OPER_EXPORT_UTENTI = "CU014";
    public static final String KEY_OPER_RICERCA_RUOLI = "CU015";
    public static final String KEY_OPER_INSERISCI_RUOLO = "CU016";
    public static final String KEY_OPER_MODIFICA_RUOLO = "CU017";
    public static final String KEY_OPER_RICERCA_PROFILO = "CU018";
    public static final String KEY_OPER_INSERISCI_PROFILO = "CU019";
    public static final String KEY_OPER_MODIFICA_PROFILO_AUDIT = "CU020";
    public static final String KEY_OPER_RICERCA_RUPAR = "CU021";
    public static final String KEY_OPER_RITORNO_PUA = "CU023";
    public static final String KEY_OPER_HELP_AUDIT = "CU024";
    public static final String KEY_OPER_ABILITAZIONE_MASSIVA_RICERCA = "CU025";
    public static final String KEY_OPER_ABILITAZIONE_MASSIVA_UTENTI = "CU026";
    public static final String KEY_OPER_DISABILITAZIONE_MASSIVA_RICERCA = "CU027";
    public static final String KEY_OPER_DISABILITAZIONE_MASSIVA_UTENTI = "CU028";
    public static final String KEY_OPER_STORICO = "CU029";
    public static final String KEY_OPER_RICERCA_APPLICAZIONE = "CU030";
    public static final String KEY_OPER_INSERISCI_APPLICAZIONE = "CU031";
    public static final String KEY_OPER_MODIFICA_APPLICAZIONE = "CU032";
    public static final String KEY_OPER_RICERCA_FUNZ = "CU033";
    public static final String KEY_OPER_INSERISCI_FUNZ = "CU034";
    public static final String KEY_OPER_MODIFICA_FUNZ = "CU035";
    public static final String KEY_OPER_LOGIN = "CULogin";


}
