insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'CONF-ERR-001', 'Il parametro {0} non risulta valorizzato', 'Il parametro {0} non risulta valorizzato', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'CONF-ERR-002', 'Il parametro {0} non risulta presente o non e'' valido', 'Il parametro {0} non risulta presente o non e'' valido', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'CONF-ERR-004', 'Il ruolo non risulta visibile al Configuratore', 'Il ruolo non risulta visibile al Configuratore', 'LCCE', 'Bloccante', now());

insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_635', 'Sistema richiedente non presente', 'Sistema richiedente non presente', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_636', 'Profilo o funzionalità non presente nell''applicativo', 'Profilo o funzionalità non presente nell''applicativo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_637', 'Il campo tipo deve essere valorizzato con FUNZ oppure PROF', 'Il campo tipo deve essere valorizzato con FUNZ oppure PROF', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_638', 'Il campo data_fine_validita deve contenere la data corrente', 'Il campo data_fine_validita deve contenere la data corrente', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_639', 'Collocazione non valida', 'Collocazione non valida', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_640', 'Profilo non valido', 'Profilo non valido', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_641', 'Funzionalità non valida', 'Funzionalità non valida', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_642', 'Se è valorizzato il campo Funzionalità deve essere valorizzato anche il campo profilo', 'Se è valorizzato il campo Funzionalità deve essere valorizzato anche il campo profilo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_643', 'Profilo già presente per l''applicativo', 'Profilo già presente per l''applicativo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_644', 'Funzionalità già presente per l''applicativo', 'Funzionalità già presente per l''applicativo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_645', 'Profilo non presente per l''applicativo', 'Profilo non presente per l''applicativo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_646', 'Il campo azione deve essere valorizzato con uno dei seguenti valori: CANC_PROF, CANC_FUNZ, CANC_PROF_FUNZ', 'Il campo azione deve essere valorizzato con uno dei seguenti valori: CANC_PROF, CANC_FUNZ, CANC_PROF_FUNZ', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_647', 'Se il campo azione=CANC_PROF deve essere valorizzato il campo codice profilo e non il campo codice funzionalità', 'Se il campo azione=CANC_PROF deve essere valorizzato il campo codice profilo e non il campo codice funzionalità', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_648', 'Se il campo azione=CANC_FUNZ deve essere valorizzato il campo codice funzionalità e non il campo codice profilo', 'Se il campo azione=CANC_FUNZ deve essere valorizzato il campo codice funzionalità e non il campo codice profilo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_649', 'Se il campo azione=CANC_PROF_FUNZ deve essere valorizzato sia il campo codice profilo che il campo codice funzionalità', 'Se il campo azione=CANC_PROF_FUNZ deve essere valorizzato sia il campo codice profilo che il campo codice funzionalità', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_656', 'Tipologia dato non valido', 'Tipologia dato non valido', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_657', 'Permesso non valido', 'Permesso non valido', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_658', 'Occorre fornire tutte le tipologie di dato con i relativi permessi', 'Occorre fornire tutte le tipologie di dato con i relativi permessi', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_659', 'Valorizzare la lista dei ruoli da associare al profilo', 'Valorizzare la lista dei ruoli da associare al profilo', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_660', 'Uno o più codici ruolo non sono validi', 'Uno o più codici ruolo non sono validi', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_661', 'Non è possibile procedere con la cancellazione del profilo perché sono presenti abilitazioni attive', 'Non è possibile procedere con la cancellazione del profilo perché sono presenti abilitazioni attive', 'LCCE', 'Bloccante', now());
insert into auth_d_catalogo_log (id, codice, descrizione, descrizione_log,fonte, tipo_errore, data_inserimento)
values (nextval('lcce.seq_auth_d_catalogo_log'), 'AUTH_ER_663', 'Sistema richiedente non corrispondente alla configurazione restituita dall''API-Manager (token non corrispondente al richiedente)', 'Sistema richiedente non corrispondente alla configurazione restituita dall''API-Manager (token non corrispondente al richiedente)', 'LCCE', 'Bloccante', now());

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.18','Release 1.0.18', 'Incrementale', 'dmaloginccedb-1.0.17-1.0.18.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,18,'18',NOW(),'dmaloginccedb-1.0.17-1.0.18.sql');
		END IF;
	END IF;
END
$$;
