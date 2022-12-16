INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_140', 'E’ stato richiamato il servizio get applicazioni', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_141', 'E’ stato richiamato il servizio get elenco profili e funzionalità di una applicazione', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_142', 'E’ stato richiamato il servizio put profili e funzionalità su una applicazione', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_143', 'E’ stato richiamato il servizio get utenti profili e funzionalità per ruolo e collocazione', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_144', 'E’ stato richiamato il servizio get utente abilitato ad un applicativo per collocazione, ruolo, profilo e funzionalità', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_145', 'E’ stato richiamato il servizio get collocazioni di un utente per ruolo', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_146', 'E’ stato richiamato il servizio post profili - funzionalità', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_147', 'E’ stato richiamato il servizio delete profilo', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_148', 'E’ stato richiamato il servizio get utenti per ruolo e collocazione', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_149', 'E’ stato richiamato il servizio delete funzionalità', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;
INSERT INTO lcce.auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_150', 'E’ stato richiamato il servizio put funzionalità tipologia dati e permessi', NULL, NULL, '2022-08-09 16:32:52.693') on conflict do NOTHING;


INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'DELPRO', 'Servizio REST DELETE profilo', 'I', '2022-07-26 12:22:53.882') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETUTEPROFUN-RUOCOL', 'Servizio REST GET lista utenti che hanno una abilitazione attiva per ruolo e collocazione', 'I', '2022-07-26 12:22:53.882') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETCOL_UTE_RUO', 'Servizio REST GET collocazioni di un utente', 'I', '2022-07-26 12:22:53.882') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETAPP', 'Servizio REST Get applicazioni', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETPROFUN', 'Servizio REST Get elenco profili e funzionalità di una applicazione', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'PUTPROFUN', 'Servizio REST Put profili/funzionalità', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETUTEABILIT-RUOCOL', 'Servizio REST GET Utenti abilitazioni per ruolo e collocazione', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETUTEABIL_RUO_COL', 'Servizio REST GET Utente abilitato ad applicativo dato un ruolo, collocazione, e profilo, funzionalità', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'POSTPROFUN', 'Servizio REST POST profili e funzionalità', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'DELFUN', 'Servizio REST DELETE funzionalità', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETUTE-RUOCOL', 'Servizio REST GET lista utenti che hanno una abilitazione attiva per ruolo e collocazione', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GETPROFUNUTE-RUOCOL', 'Servizio REST GET profili e funzionalità di un utente che hanno una abilitazione attiva per ruolo e collocazione', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;
INSERT INTO lcce.auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'PUTFUN_DATIPER', 'Servizio REST che aggiorna la tipologia di dati e permessi di una funzionalità', 'I', '2019-12-04 09:26:28.093') on conflict do NOTHING;


DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.17','Release 1.0.17', 'Incrementale', 'dmaloginccedb-1.0.16-1.0.17.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,17,'17',NOW(),'dmaloginccedb-1.0.16-1.0.17.sql');
		END IF;
	END IF;
END
$$;
