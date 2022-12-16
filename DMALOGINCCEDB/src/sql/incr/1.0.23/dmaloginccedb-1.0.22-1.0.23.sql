alter table auth_l_csi_log_audit add column ruolo int8,
add column cf_oggetto_operazione text,
add column ogg_oper text,
add column key_oper text;

ALTER TABLE lcce.auth_l_csi_log_audit ADD CONSTRAINT auth_l_csi_log_audit_id_auth_d_ruolo_fkey FOREIGN KEY (ruolo) REFERENCES lcce.auth_d_ruolo(id) ;


INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU001', 'Ricerca Utente', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU002', 'Inserimento nuovo utente', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU003', 'Richiesta credenziali RUPAR', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU004', 'Assegna Ruolo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU005', 'Rimuovi assegnazione Ruolo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU006', 'Assegna collocazione', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU007', 'Rimuovi Assegazione collocazione', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU008', 'Assegna SOL/Profilo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU009', 'Modifica assegnazione SOL/Profilo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU010', 'Visualizza funzionalità del profilo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU011', 'Invio Mail all utente profilato', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU012_1', 'Modifica dati utente(tasto matita)', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU012_2', 'Modifica dati utente(tasto Salva)', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU013', 'Disabilita tutte le configurazioni', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU014', 'Export Utenti e Abilitazioni', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU015', 'Ricerca Ruolo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU016', 'Inserisci Ruolo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU017', 'Modifica Ruolo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU018', 'Ricerca Profilo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU019', 'Inserisci Profilo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU020', 'Modifica Profilo', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU021', 'Ricerca credenziali RUPAR', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU023', 'Ritorno al PUA', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU024', 'HELP', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU025', 'Ricerca abilitazione massiva utenti', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU026', 'Abilitazione massiva utenti', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU027', 'Ricerca disabilitazione massiva utenti', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU028', 'Disabilitazione massiva utenti', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CU029', 'Storico Abilitazione/Disabilitazione massiva utenti', now());

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit_conf'), 'CULogin', 'Login', now());

ALTER TABLE lcce.auth_t_batch_utenti_abilitazione_massiva ADD column if not exists data_fine_abilitazione timestamp NULL;

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.23','Release 1.0.23', 'Incrementale', 'dmaloginccedb-1.0.22-1.0.23.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,23,'23',NOW(),'dmaloginccedb-1.0.22-1.0.23.sql');
		END IF;
	END IF;
END
$$;
