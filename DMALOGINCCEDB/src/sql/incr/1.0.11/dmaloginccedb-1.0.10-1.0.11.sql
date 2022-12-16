INSERT INTO auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('seq_auth_d_messaggi_utente'), 'MSGCONF032', 'L''''anagrafica per il codice fiscale {0} non e'''' stata trovata. Non e'''' possibile procedere con l''''inserimento', 'W', now())
on conflict do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AURA_SEPAC', 'AURA ricercaAssistitoAnag', 'E', now())on conflict do NOTHING;
INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'OPESSAN_DET_OP', 'OPESSAN GetDettaglioOperatore getInfoOperatoreConf', 'E', now())on conflict do NOTHING;
INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'OPESSAN_DET_OP_DIP', 'OPESSAN OperatoreDipendente getDettaglioConf', 'E', now())on conflict do NOTHING;

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AURA_SEPAC_ER_001', 'Codice fiscale non trovato', 'Codice fiscale non trovato', 'SOLCONFIG', '', now())on conflict do NOTHING;
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AURA_SEPAC_ER_002', 'AURA SOAP Fault', 'AURA SOAP Fault', 'SOLCONFIG', 'Bloccante', now())on conflict do NOTHING;
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AURA_SEPAC_ER_003', '400 Could not send Message', '400 Could not send Message', 'SOLCONFIG', 'Bloccante', now())on conflict do NOTHING;

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.11','Release 1.0.11', 'Incrementale', 'dmaloginccedb-1.0.10-1.0.11.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,11,'11',NOW(),'dmaloginccedb-1.0.10-1.0.11.sql');
		END IF;
	END IF;
END
$$;
