
-- aggiusto la sequence se sballata
SELECT setval('seq_auth_d_servizi', COALESCE((SELECT MAX(id)+1 FROM auth_d_servizi), 1), false);

---

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETPREFERENCESOPER', 'Servizio REST GET lista delle preferenze di spedizione', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_167', 'E’ stato richiamato il servizio get preferenze operatore', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'PUTPREFERENCESOPER', 'Servizio REST PUT lista delle preferenze di spedizione', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_161', 'E’ stato richiamato il servizio put preferenze operatore', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'POSTGETUTENTI', 'Servizio REST POST restituisce lista degli utenti', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_162', 'E’ stato richiamato il servizio post lista utenti', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'PUTTOKEN', 'Servizio REST PUT fruitori del notificatore', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_163', 'E’ stato richiamato il servizio put fruitore', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETCONTATTI', 'Servizio REST GET contatti digitali', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_164', 'E’ stato richiamato il servizio get contatti', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'POSTDELETEPREF', 'Servizio REST POST cancella preferenze e contatti digitali di un utente', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_165', 'E’ stato richiamato il servizio delete contatti', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'PUTCONTATTI', 'Servizio REST PUT contatti', 'I', current_timestamp)
on conflict (codice) do NOTHING;

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'AUTH_LOG_166', 'E’ stato richiamato il servizio put contatti', NULL, NULL, current_timestamp)
on conflict (codice) do NOTHING;


DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.19','Release 1.0.19', 'Incrementale', 'dmaloginccedb-1.0.18-1.0.19.sql','CSI', 0, true);

	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,19,'19',NOW(),'dmaloginccedb-1.0.18-1.0.19.sql');
		END IF;
	END IF;
END
$$;
/*
-- version control
INSERT INTO version_control (release_version, description, release_type, script_name, installed_by, execution_time, success) VALUES
('1.0.19','Release 1.0.19', 'Incrementale', 'dmaloginccedb-1.0.18-1.0.19.sql','CSI', 0, true);
*/
