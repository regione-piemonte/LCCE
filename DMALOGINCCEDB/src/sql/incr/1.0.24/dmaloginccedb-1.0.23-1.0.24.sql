-- aggiornamento configurazione per la nuova configurazione di timeout

INSERT INTO auth_t_configurazione (id,chiave,valore,descrizione,data_inserimento) VALUES 
(nextval('seq_auth_t_configurazione'),'SESSION_TIME_TOK','108000','Durata temporale del token - sessione totale', now())
;

-- controllo
select * from auth_t_configurazione
where chiave = 'SESSION_TIME_TOK';

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.24','Release 1.0.24', 'Incrementale', 'dmaloginccedb-1.0.23-1.0.24.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,23,'23',NOW(),'dmaloginccedb-1.0.23-1.0.24.sql');
		END IF;
	END IF;
END
$$;
