---- Add new column for auth_l_log_audit for SRV-04
ALTER TABLE AUTH_L_LOG_AUDIT ADD COLUMN ID_RICHIESTA VARCHAR;
-- ALTER TABLE AUTH_L_LOG_AUDIT ALTER COLUMN ID_RICHIESTA SET NOT NULL;


ALTER TABLE AUTH_L_LOG_AUDIT ADD COLUMN ID_SISTEMA_RICHIEDENTE int8;
-- ALTER TABLE AUTH_L_LOG_AUDIT ALTER COLUMN ID_SISTEMA_RICHIEDENTE SET NOT NULL;

select setval('lcce.seq_auth_d_servizi',(select max(id) from auth_d_servizi));

INSERT INTO auth_d_servizi (id, codice, descrizione, tipo_servizio, data_inserimento) 
values(nextval('lcce.seq_auth_d_servizi'), 'CONF_API', 'Api del Configuratore', 'E', now() );


ALTER TABLE auth_l_messaggi ADD request_uri varchar;

--
CREATE SEQUENCE auth_d_messaggi_utente_seq ;
select setval('auth_d_messaggi_utente_seq',  (SELECT MAX(id) FROM auth_d_messaggi_utente));

ALTER TABLE auth_d_messaggi_utente ALTER COLUMN id SET DEFAULT nextval('auth_d_messaggi_utente_seq');
ALTER TABLE auth_d_messaggi_utente ALTER COLUMN data_inserimento SET DEFAULT now();

insert into auth_d_messaggi_utente (codice,descrizione,tipo_messaggio)
values ('AUTH_ER_635','Sistema richiedente non presente','E'),
('AUTH_ER_502','Il ruolo fornito da parte del sistema non è riconosciuto','E'),
('AUTH_ER_640','Profilo non valido','E'),
('AUTH_ER_639','Collocazione non valida','E'),
('AUTH_ER_641','Funzionalità non valida','E'),
('AUTH_ER_642','Se è valorizzato il campo Funzionalità deve essere valorizzato anche il campo profilo','E')
on conflict (codice) do NOTHING;

--

insert into auth_d_messaggi_utente (codice,descrizione,tipo_messaggio)
values 
('AUTH_ER_507','Applicazione richiesta non disponibile ','E'),
('AUTH_ER_636','Profilo o funzionalità non presente nell''applicativo' ,'E'),
('AUTH_ER_637','Il campo tipo deve essere valorizzato con FUNZ oppure PROF','E'),
('AUTH_ER_638',' Il campo data_fine_validita deve contenere la data corrente ','E'),
('AUTH_ER_643',' Profilo già presente per l’applicativo ','E'),
('AUTH_ER_645',' Profilo non presente per l’applicativo ','E'),
('AUTH_ER_644',' Funzionalità già presente per l’applicativo ','E'),
('AUTH_ER_646',' Il campo azione deve essere valorizzato con uno dei seguenti valori: CANC_PROF, CANC_FUNZ, CANC_PROF_FUNZ ','E'),
('AUTH_ER_647',' Se il campo azione=CANC_PROF deve essere valorizzato il campo codice profilo e non il campo codice funzionalità ','E'),
('AUTH_ER_648',' Se il campo azione=CANC_FUNZ deve essere valorizzato il campo codice funzionalità e non il campo codice profilo ','E'),
('AUTH_ER_649',' Se il campo azione=CANC_PROF_FUNZ deve essere valorizzato sia il campo codice profilo che il campo codice funzionalità ','E'),
('AUTH_ER_650',' Se il codice funzionalità indicato non è associato al profilo indicato ','E')
on conflict (codice) do NOTHING;

-- fix eventuali errori
update auth_d_messaggi_utente set codice=trim(codice);

---

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.15','Release 1.0.15', 'Incrementale', 'dmaloginccedb-1.0.14-1.0.15.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,15,'15',NOW(),'dmaloginccedb-1.0.14-1.0.15.sql');
		END IF;
	END IF;
END
$$;
