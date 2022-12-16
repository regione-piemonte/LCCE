

INSERT INTO auth_d_messaggi_utente
(codice, descrizione, tipo_messaggio, data_inserimento)
VALUES('MSGCONF042', 'Valorizzare i campi obbligatori', 'W', now());

INSERT INTO auth_d_messaggi_utente
(codice, descrizione, tipo_messaggio, data_inserimento)
VALUES('MSGCONF043', 'Nessuna applicazione trovata', 'W', now());

INSERT INTO auth_d_messaggi_utente
(codice, descrizione, tipo_messaggio, data_inserimento)
VALUES('MSGCONF044', 'Applicazione già presente', 'W', now());

INSERT INTO auth_d_messaggi_utente
(codice, descrizione, tipo_messaggio, data_inserimento)
VALUES('MSGCONF045', 'Funzionalità già presente', 'W', now());

  
DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.25','Release 1.0.25', 'Incrementale', 'dmaloginccedb-1.0.24-1.0.25.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,25,'25',NOW(),'dmaloginccedb-1.0.24-1.0.25.sql');
		END IF;
	END IF;
END
$$;
