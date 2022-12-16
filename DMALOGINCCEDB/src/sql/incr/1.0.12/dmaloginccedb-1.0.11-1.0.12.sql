create table lcce.auth_t_faq_credenziali ( id int8 not null,
valore bytea not null,
descrizione varchar(250) not null,
data_inserimento timestamp not null,
data_chiusura timestamp ,
constraint pk_auth_t_faq_credenziali primary key (id) );

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.12','Release 1.0.12', 'Incrementale', 'dmaloginccedb-1.0.11-1.0.12.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,12,'12',NOW(),'dmaloginccedb-1.0.11-1.0.12.sql');
		END IF;
	END IF;
END
$$;