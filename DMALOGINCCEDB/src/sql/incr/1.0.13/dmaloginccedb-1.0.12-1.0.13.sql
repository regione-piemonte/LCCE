INSERT INTO auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('seq_auth_d_messaggi_utente'), 'MSGCONF033', 'Nessun utente trovato', 'W', now()) on conflict do NOTHING;

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'OPESSAN_DETOP_ER_001', 'Id AURA non trovato', 'Id AURA non trovato', 'SOLCONFIG', '', now()) on conflict do NOTHING;
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'OPESSAN_DETOP_ER_002', 'OPESSAN SOAP Fault', 'OPESSAN SOAP Fault', 'SOLCONFIG', 'Bloccante', now()) on conflict do NOTHING;
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'OPESSAN_DETOP_ER_003', '400 Could not send Message', '400 Could not send Message', 'SOLCONFIG', 'Bloccante', now()) on conflict do NOTHING;

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.13','Release 1.0.13', 'Incrementale', 'dmaloginccedb-1.0.12-1.0.13.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,13,'13',NOW(),'dmaloginccedb-1.0.12-1.0.13.sql');
		END IF;
	END IF;
END
$$;
