/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dao;

public class SQLStatements {
	
	public static final String FIND_RICHIESTE_MASSIVE = "SELECT bam.* FROM auth_t_batch_abilitazione_massiva bam "
														+ "JOIN auth_d_batch_stato bs ON bam.batch_stato_id = bs.batch_stato_id "
														+ "WHERE ((bs.batch_stato_codice = ? AND bam.data_inizio IS NULL) "
														+ "OR (bs.batch_stato_codice = ? AND bam.data_inizio IS NOT NULL)) AND bam.data_fine IS NULL "
														+ "order by bam.data_inserimento";
	
	public static final String FIND_UTENTI_RICHIESTA_MASSIVA = "SELECT buam.* FROM auth_t_batch_utenti_abilitazione_massiva buam "
																+ "WHERE buam.id_batch = ? AND buam.data_fine IS NULL "
																+ "order by buam.data_inserimento";
	
	public static final String START_RICHIESTA = "UPDATE auth_t_batch_abilitazione_massiva SET batch_stato_id = "
												+ "(SELECT batch_stato_id FROM auth_d_batch_stato WHERE batch_stato_codice = ?), "
												+ "data_inizio = ? WHERE id = ?";
	
	public static final String END_RICHIESTA = "UPDATE auth_t_batch_abilitazione_massiva SET batch_stato_id = "
												+ "(SELECT batch_stato_id FROM auth_d_batch_stato WHERE batch_stato_codice = ?), "
												+ "data_fine = ? WHERE id = ?";

	public static final String START_UTENTE_RICHIESTA = "UPDATE auth_t_batch_utenti_abilitazione_massiva SET data_inizio = ? WHERE id = ?";
	
	public static final String END_UTENTE_RICHIESTA = "UPDATE auth_t_batch_utenti_abilitazione_massiva SET data_fine = ?, errore_utente = ?, errore_interno = ? WHERE id = ?";
	
	public static final String FIND_ABILITAZIONE_ATTIVA = "SELECT ara.* FROM auth_r_abilitazione ara "
														+ "JOIN auth_r_ruolo_utente ru ON ara.id_ruolo_utente = ru.id "
														+ "JOIN auth_r_utente_collocazione cu ON ara.utecol_id = cu.utecol_id "
														+ "WHERE ara.id_applicazione = ? AND (ru.id_utente = ? AND ru.id_ruolo = ?) "
														+ "AND (cu.ute_id = ? AND cu.col_id = ?) AND fnztree_id = ? "
														+ "AND ((CURRENT_TIMESTAMP BETWEEN ara.data_inizio_validita AND ara.data_fine_validita) " 
								                        + "OR (CURRENT_TIMESTAMP > ara.data_inizio_validita AND ara.data_fine_validita IS NULL)  "
								                        + "OR (CURRENT_TIMESTAMP < ara.data_fine_validita AND ara.data_inizio_validita IS NULL) "
								                        + "OR (ara.data_inizio_validita IS NULL AND ara.data_fine_validita IS NULL)) "
								                        + "AND ((CURRENT_TIMESTAMP BETWEEN ru.data_inizio_validita AND ru.data_fine_validita) " 
								                        + "OR (CURRENT_TIMESTAMP > ru.data_inizio_validita AND ru.data_fine_validita IS NULL)  "
								                        + "OR (CURRENT_TIMESTAMP < ru.data_fine_validita AND ru.data_inizio_validita IS NULL) "
								                        + "OR (ru.data_inizio_validita IS NULL AND ru.data_fine_validita IS NULL)) "
								                        + "AND ((CURRENT_TIMESTAMP BETWEEN cu.data_inizio_validita AND cu.data_fine_validita) " 
								                        + "OR (CURRENT_TIMESTAMP > cu.data_inizio_validita AND cu.data_fine_validita IS NULL)  "
								                        + "OR (CURRENT_TIMESTAMP < cu.data_fine_validita AND cu.data_inizio_validita IS NULL) "
								                        + "OR (cu.data_inizio_validita IS NULL AND cu.data_fine_validita IS NULL))";
	
	public static final String FIND_ABILITAZIONE_NON_ATTIVA = "SELECT ara.* FROM auth_r_abilitazione ara "
														+ "JOIN auth_r_ruolo_utente ru ON ara.id_ruolo_utente = ru.id "
														+ "JOIN auth_r_utente_collocazione cu ON ara.utecol_id = cu.utecol_id "
														+ "WHERE ara.id_applicazione = ? AND (ru.id_utente = ? AND ru.id_ruolo = ?) "
														+ "AND (cu.ute_id = ? AND cu.col_id = ?) AND fnztree_id = ? "
														+ "AND ( ara.data_fine_validita IS NOT NULL AND CURRENT_TIMESTAMP >= ara.data_fine_validita " 
											            + "AND ara.data_cancellazione IS NOT NULL) "
											            + "AND ((CURRENT_TIMESTAMP BETWEEN ru.data_inizio_validita AND ru.data_fine_validita) " 
											            + "OR (CURRENT_TIMESTAMP > ru.data_inizio_validita AND ru.data_fine_validita IS NULL)  "
											            + "OR (CURRENT_TIMESTAMP < ru.data_fine_validita AND ru.data_inizio_validita IS NULL) "
											            + "OR (ru.data_inizio_validita IS NULL AND ru.data_fine_validita IS NULL)) "
											            + "AND ((CURRENT_TIMESTAMP BETWEEN cu.data_inizio_validita AND cu.data_fine_validita) " 
											            + "OR (CURRENT_TIMESTAMP > cu.data_inizio_validita AND cu.data_fine_validita IS NULL)  "
											            + "OR (CURRENT_TIMESTAMP < cu.data_fine_validita AND cu.data_inizio_validita IS NULL) "
											            + "OR (cu.data_inizio_validita IS NULL AND cu.data_fine_validita IS NULL))";
	
	public static final String FIND_RUOLO_UTENTE = "SELECT ru.id FROM auth_r_ruolo_utente ru WHERE ru.id_utente = ? AND ru.id_ruolo = ? "
													+ "AND ((CURRENT_TIMESTAMP BETWEEN ru.data_inizio_validita AND ru.data_fine_validita) " 
										            + "OR (CURRENT_TIMESTAMP > ru.data_inizio_validita AND ru.data_fine_validita IS NULL)  "
										            + "OR (CURRENT_TIMESTAMP < ru.data_fine_validita AND ru.data_inizio_validita IS NULL) "
										            + "OR (ru.data_inizio_validita IS NULL AND ru.data_fine_validita IS NULL)) ";
	
	public static final String FIND_COLLOCAZIONE_UTENTE = "SELECT cu.utecol_id FROM auth_r_utente_collocazione cu WHERE cu.ute_id = ? AND cu.col_id = ? "
														 + "AND ((CURRENT_TIMESTAMP BETWEEN cu.data_inizio_validita AND cu.data_fine_validita) " 
												         + "OR (CURRENT_TIMESTAMP > cu.data_inizio_validita AND cu.data_fine_validita IS NULL)  "
												         + "OR (CURRENT_TIMESTAMP < cu.data_fine_validita AND cu.data_inizio_validita IS NULL) "
												         + "OR (cu.data_inizio_validita IS NULL AND cu.data_fine_validita IS NULL))";
	
	public static final String FIND_FUNZ_TREE_ID = "SELECT fnztree_id from auth_r_funzionalita_tree WHERE fnz_id = ? "
													+ "AND ((CURRENT_TIMESTAMP BETWEEN data_inizio_validita AND data_fine_validita) "
													+ "OR (data_fine_validita IS NULL AND (CURRENT_TIMESTAMP BETWEEN data_inizio_validita AND '9999-12-31 00:00:00')))";
	
	public static final String UPDATE_ABILITAZIONE = "UPDATE auth_r_abilitazione SET data_fine_validita = ?, data_cancellazione = ?, "
													+ "data_aggiornamento = now() WHERE id = ?";
	
	public static final String INSERT_ABILITAZIONE = "INSERT INTO auth_r_abilitazione (id, id_ruolo_utente, id_applicazione, codice_abilitazione, "
													+ "data_inizio_validita, data_fine_validita, data_inserimento, fnztree_id, utecol_id, "
													+ "data_aggiornamento, data_cancellazione, cf_operatore) VALUES "
													+ "(nextval('seq_auth_r_abilitazione'), ?, ?, ?, ?, ?, now(), ?, ?, now(), null, 'Batch abilitazione/disabilitazione massiva')";

	public static final String FIND_PREFERENZA_RUOLO = "SELECT preferenza_id FROM auth_t_preferenza "
														+ "WHERE id_utente = ? AND id_ruolo = ? "
														+ "AND col_id IS NULL AND id_applicazione IS NULL";
	
	public static final String FIND_PREFERENZA_RUOLO_COLLOCAZIONE = "SELECT preferenza_id FROM auth_t_preferenza "
																	+ "WHERE id_utente = ? AND id_ruolo = ? "
																	+ "AND col_id = ? AND id_applicazione IS NULL";
	
	public static final String FIND_PREFERENZA_APPLICAZIONE = "SELECT preferenza_id FROM auth_t_preferenza "
															+ "WHERE id_utente = ? AND id_ruolo IS NULL "
															+ "AND col_id IS NULL AND id_applicazione = ? ";
	
	public static final String UPDATE_PREFERENZA = "UPDATE auth_t_preferenza SET data_inizio_validita = "
													+ "(CASE WHEN data_cancellazione IS NULL THEN data_inizio_validita ELSE now() END), "
													+ "data_cancellazione = ?, data_aggiornamento = now(), data_fine_validita = ? WHERE preferenza_id = ? ";
	
	public static final String INSERT_PREFERENZA = "INSERT INTO auth_t_preferenza (id_utente, id_applicazione, id_ruolo, "
													+ "col_id, data_inserimento, data_aggiornamento, data_cancellazione, data_inizio_validita) "
													+ "VALUES(?, ?, ?, ?, now(), now(), null, now())";
	
	public static final String FIND_ID_MESSAGGIO_UTENTE = "SELECT id FROM auth_d_messaggi_utente WHERE codice = ?";
	
	public static final String FIND_EMAIL_UTENTE = "SELECT pgp_sym_decrypt(indirizzo_mail::bytea, ?) FROM auth_t_utente WHERE id = ?";
	
	public static final String FIND_CONFIGURAZIONE = "SELECT valore FROM auth_t_configurazione WHERE chiave = ?";
	
	public static final String FIND_COLLOCAZIONI = "SELECT c.col_id, c.col_codice, c.col_descrizione, c.col_cod_azienda, c.col_desc_azienda, c.flag_azienda "
													+ "FROM auth_r_utente_collocazione uc JOIN auth_t_collocazione c ON uc.col_id = c.col_id "
													+ "WHERE uc.ute_id = ?"
													+ "AND ((CURRENT_TIMESTAMP BETWEEN c.data_inizio_validita AND c.data_fine_validita) "
													+ "OR (CURRENT_TIMESTAMP > c.data_inizio_validita AND c.data_fine_validita IS NULL) "
													+ "OR (CURRENT_TIMESTAMP < c.data_fine_validita AND c.data_inizio_validita IS NULL) "
													+ "OR (c.data_inizio_validita IS NULL AND c.data_fine_validita IS NULL)) "
													+ "AND ((CURRENT_TIMESTAMP BETWEEN uc.data_inizio_validita AND uc.data_fine_validita) "
													+ "OR (CURRENT_TIMESTAMP > uc.data_inizio_validita AND uc.data_fine_validita IS NULL) "
													+ "OR (CURRENT_TIMESTAMP < uc.data_fine_validita AND uc.data_inizio_validita IS NULL) "
													+ "OR (uc.data_inizio_validita IS NULL AND uc.data_fine_validita IS NULL)) ";

	public static final String FIND_RUOLI = "SELECT r.id_ruolo FROM auth_r_ruolo_utente r WHERE r.id_utente = ? "
											+ "AND ((CURRENT_TIMESTAMP BETWEEN r.data_inizio_validita AND r.data_fine_validita) "
											+ "OR (CURRENT_TIMESTAMP > r.data_inizio_validita AND r.data_fine_validita IS NULL) "
											+ "OR (CURRENT_TIMESTAMP < r.data_fine_validita AND r.data_inizio_validita IS NULL) "
											+ "OR (r.data_inizio_validita IS NULL AND r.data_fine_validita IS NULL)) ";
	
	public static final String FIND_ABILITAZIONI_STRING = "SELECT DISTINCT "
														+ "CASE WHEN (a.data_fine_validita IS NOT NULL) "
														+ "THEN CONCAT(ap.id, '|', uc.col_id, '|', f.fnz_id , '|', rp.id_ruolo, '|', a.data_fine_validita) "
														+ "ELSE CONCAT(ap.id, '|', uc.col_id, '|', f.fnz_id , '|', rp.id_ruolo) "
														+ "END "
														+ "FROM auth_t_utente u, auth_r_utente_collocazione uc, "
														+ "auth_r_abilitazione a, auth_d_applicazione ap, "
														+ "auth_r_funzionalita_tree tf, auth_t_funzionalita f, "
														+ "auth_r_ruolo_utente rp "
														+ "WHERE u.id = uc.ute_id "
														+ "and uc.utecol_id = a.utecol_id "
														+ "and a.id_applicazione = ap.id "
														+ "and a.fnztree_id = tf.fnztree_id "
														+ "and tf.fnz_id = f.fnz_id "
														+ "and a.id_ruolo_utente = rp.id "
														+ "and u.id = ? AND ap.flag_configuratore = 'S'"
														+ "AND ap.codice <> 'SOLCONFIG'"
														+ "AND ((CURRENT_TIMESTAMP BETWEEN rp.data_inizio_validita AND rp.data_fine_validita) "
														+ "OR (CURRENT_TIMESTAMP > rp.data_inizio_validita AND rp.data_fine_validita IS NULL) "
														+ "OR (CURRENT_TIMESTAMP < rp.data_fine_validita AND rp.data_inizio_validita IS NULL) "
														+ "OR (rp.data_inizio_validita IS NULL AND rp.data_fine_validita IS NULL)) "
														+ "AND ((CURRENT_TIMESTAMP BETWEEN uc.data_inizio_validita AND uc.data_fine_validita) "
														+ "OR (CURRENT_TIMESTAMP > uc.data_inizio_validita AND uc.data_fine_validita IS NULL) "
														+ "OR (CURRENT_TIMESTAMP < uc.data_fine_validita AND uc.data_inizio_validita IS NULL) "
														+ "OR (uc.data_inizio_validita IS NULL AND uc.data_fine_validita IS NULL)) "
														+ "AND ((CURRENT_TIMESTAMP BETWEEN a.data_inizio_validita AND a.data_fine_validita) "
														+ "OR (CURRENT_TIMESTAMP > a.data_inizio_validita AND a.data_fine_validita IS NULL) "
														+ "OR (CURRENT_TIMESTAMP < a.data_fine_validita AND a.data_inizio_validita IS NULL) "
														+ "OR (a.data_inizio_validita IS NULL AND a.data_fine_validita IS NULL)) "
														+ "AND ((CURRENT_TIMESTAMP BETWEEN tf.data_inizio_validita AND tf.data_fine_validita) "
														+ "OR (CURRENT_TIMESTAMP > tf.data_inizio_validita AND tf.data_fine_validita IS NULL) "
														+ "OR (CURRENT_TIMESTAMP < tf.data_fine_validita AND tf.data_inizio_validita IS NULL) "
														+ "OR (tf.data_inizio_validita IS NULL AND tf.data_fine_validita IS NULL)) "
														+ "AND ((CURRENT_TIMESTAMP BETWEEN f.data_inizio_validita AND f.data_fine_validita) "
														+ "OR (CURRENT_TIMESTAMP > f.data_inizio_validita AND f.data_fine_validita IS NULL) "
														+ "OR (CURRENT_TIMESTAMP < f.data_fine_validita AND f.data_inizio_validita IS NULL) "
														+ "OR (f.data_inizio_validita IS NULL AND f.data_fine_validita IS NULL)) ";
	
	public static final String FIND_APPLICAZIONE = "SELECT id, codice, descrizione FROM auth_d_applicazione WHERE id = ?";
	
	public static final String FIND_FUNZIONALITA = "SELECT fnz_id, fnz_codice, fnz_descrizione FROM auth_t_funzionalita WHERE fnz_id = ? "
													+ "AND ((CURRENT_TIMESTAMP BETWEEN data_inizio_validita AND data_fine_validita) "
													+ "OR (CURRENT_TIMESTAMP > data_inizio_validita AND data_fine_validita IS NULL) "
													+ "OR (CURRENT_TIMESTAMP < data_fine_validita AND data_inizio_validita IS NULL) "
													+ "OR (data_inizio_validita IS NULL AND data_fine_validita IS NULL)) ";
	
	public static final String FIND_UTENTE = "SELECT id, nome, cognome, codice_fiscale, pgp_sym_decrypt(numero_telefono::bytea, ?), pgp_sym_decrypt(indirizzo_mail::bytea, ?) FROM auth_t_utente WHERE id = ?";
	
	public static final String FIND_DESC_RUOLO = "SELECT CONCAT(codice, ' - ', descrizione) FROM auth_d_ruolo WHERE id = ?";
	
	public static final String FIND_DESC_COLLOCAZIONE = "SELECT CONCAT(col_codice, ' - ', col_descrizione) FROM auth_t_collocazione WHERE col_id = ?";
	
	public static final String FIND_DESC_AZIENDA = "SELECT CONCAT(col_cod_azienda, ' - ', col_desc_azienda) FROM auth_t_collocazione WHERE col_id = ?";
		
	public static final String FIND_DESC_ERRORE = "SELECT descrizione FROM auth_d_messaggi_utente WHERE id = ?";
	
	public static final String SAVE_CSV_SENZA_ERRORI = "UPDATE auth_t_batch_abilitazione_massiva SET csv_utenti_inseriti = ? WHERE id = ?";
	
	public static final String SAVE_CSV_CON_ERRORI = "UPDATE auth_t_batch_abilitazione_massiva SET csv_utenti_non_inseriti = ? WHERE id = ?";
	
	public static final String CHECK_APPLICAZIONE_CON_INVIO_MAIL_AURA = "SELECT invio_mail_aura FROM auth_d_applicazione WHERE id = ?";
	
	public static final String FIND_ABILITAZIONI_BY_RUOLO_UTENTE = "SELECT ara.* FROM auth_r_abilitazione ara "
																	+ "WHERE ara.id_ruolo_utente = ? "
																	+ "AND ((CURRENT_TIMESTAMP BETWEEN ara.data_inizio_validita AND ara.data_fine_validita) " 
														            + "OR (CURRENT_TIMESTAMP > ara.data_inizio_validita AND ara.data_fine_validita IS NULL)  "
														            + "OR (CURRENT_TIMESTAMP < ara.data_fine_validita AND ara.data_inizio_validita IS NULL) "
														            + "OR (ara.data_inizio_validita IS NULL AND ara.data_fine_validita IS NULL)) ";
	
	public static final String FIND_ABILITAZIONI_BY_USER_AND_APP = "SELECT ara.* FROM auth_r_abilitazione ara "
																	+ "JOIN auth_r_ruolo_utente ru ON ara.id_ruolo_utente = ru.id "
																	+ "WHERE ara.id_applicazione = ? AND ru.id_utente = ? "
																	+ "AND ((CURRENT_TIMESTAMP BETWEEN ara.data_inizio_validita AND ara.data_fine_validita) " 
														            + "OR (CURRENT_TIMESTAMP > ara.data_inizio_validita AND ara.data_fine_validita IS NULL)  "
														            + "OR (CURRENT_TIMESTAMP < ara.data_fine_validita AND ara.data_inizio_validita IS NULL) "
														            + "OR (ara.data_inizio_validita IS NULL AND ara.data_fine_validita IS NULL)) "
														            + "AND ((CURRENT_TIMESTAMP BETWEEN ru.data_inizio_validita AND ru.data_fine_validita) " 
														            + "OR (CURRENT_TIMESTAMP > ru.data_inizio_validita AND ru.data_fine_validita IS NULL)  "
														            + "OR (CURRENT_TIMESTAMP < ru.data_fine_validita AND ru.data_inizio_validita IS NULL) "
														            + "OR (ru.data_inizio_validita IS NULL AND ru.data_fine_validita IS NULL)) ";
}
