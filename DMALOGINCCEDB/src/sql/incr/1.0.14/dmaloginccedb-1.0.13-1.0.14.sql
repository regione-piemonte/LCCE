INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_t_funzionalita_fnz_id_seq'), 'OP-RicercaAbilitazioneMassiva', 'Abilita ricerca abilitazione massiva', 
	(select d.fnz_tipo_id from auth_d_funzionalita_tipo d where d.fnz_tipo_codice = 'FUNZ' and d.data_fine_validita is null and d.data_cancellazione is null),
	(select a.id from auth_d_applicazione a where a.codice = 'SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI-import');
INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_t_funzionalita_fnz_id_seq'), 'OP-AbilitazioneMassiva', 'Abilita abilitazione massiva', 
	(select d.fnz_tipo_id from auth_d_funzionalita_tipo d where d.fnz_tipo_codice = 'FUNZ' and d.data_fine_validita is null and d.data_cancellazione is null),
	(select a.id from auth_d_applicazione a where a.codice = 'SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI-import');
INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_t_funzionalita_fnz_id_seq'), 'OP-RicercaDisabilitazioneMassiva', 'Abilita ricerca disabilitazione massiva', 
	(select d.fnz_tipo_id from auth_d_funzionalita_tipo d where d.fnz_tipo_codice = 'FUNZ' and d.data_fine_validita is null and d.data_cancellazione is null),
	(select a.id from auth_d_applicazione a where a.codice = 'SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI-import');
INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_t_funzionalita_fnz_id_seq'), 'OP-DisabilitazioneMassiva', 'Abilita disabilitazione massiva', 
	(select d.fnz_tipo_id from auth_d_funzionalita_tipo d where d.fnz_tipo_codice = 'FUNZ' and d.data_fine_validita is null and d.data_cancellazione is null),
	(select a.id from auth_d_applicazione a where a.codice = 'SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI-import');
INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_t_funzionalita_fnz_id_seq'), 'OP-VisualizzaStoricoMassiva', 'Abilita visualizzazione storico', 
	(select d.fnz_tipo_id from auth_d_funzionalita_tipo d where d.fnz_tipo_codice = 'FUNZ' and d.data_fine_validita is null and d.data_cancellazione is null),
	(select a.id from auth_d_applicazione a where a.codice = 'SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI-import');



INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-RicercaAbilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_TITOLARE' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-AbilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_TITOLARE' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-RicercaDisabilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_TITOLARE' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-DisabilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_TITOLARE' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-VisualizzaStoricoMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_TITOLARE' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');

INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-RicercaAbilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_DELEGATO' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-AbilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_DELEGATO' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-RicercaDisabilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_DELEGATO' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-DisabilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_DELEGATO' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-VisualizzaStoricoMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='CONF_DELEGATO' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');

INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-RicercaAbilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='SUPERUSERCONF' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-AbilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='SUPERUSERCONF' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-RicercaDisabilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='SUPERUSERCONF' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-DisabilitazioneMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='SUPERUSERCONF' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_r_funzionalita_tree_fnztree_id_seq'), 
	(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='OP-VisualizzaStoricoMassiva' and f.data_fine_validita is null and f.data_cancellazione is null),
	(select r.fnztree_id from auth_r_funzionalita_tree r where r.fnz_id in(
		(select f.fnz_id from auth_t_funzionalita f where f.fnz_codice='SUPERUSERCONF' and f.data_fine_validita is null and f.data_cancellazione is null)
	) and r.data_fine_validita is null and r.data_cancellazione is null),
	now(), NULL, now(), now(), NULL, 'CSI');


CREATE TABLE lcce.auth_t_batch_abilitazione_massiva (
id bigserial NOT NULL,
codice_fiscale_operatore varchar(250) NOT NULL,
disabilitazione boolean not NULL,
batch_stato_id int8 NOT NULL,
data_inserimento timestamp NOT NULL,
data_inizio timestamp,
data_fine timestamp,
csv_utenti_inseriti bytea,
csv_utenti_non_inseriti bytea,
CONSTRAINT pk_auth_t_batch_abilitazione_massiva  PRIMARY KEY (id),
CONSTRAINT auth_d_batch_stato_auth_t_batch_abilitazione_massiva FOREIGN KEY (batch_stato_id) REFERENCES lcce.auth_d_batch_stato(batch_stato_id)
);

CREATE TABLE lcce.auth_t_batch_utenti_abilitazione_massiva (
id bigserial NOT NULL,
id_batch int8 NOT NULL,
utente int8 NOT NULL,
ruolo int8, 
collocazione int8 NOT NULL,
abilitazione int8 ,
profilo int8 ,
data_inserimento timestamp NOT NULL,
errore_utente int8,
errore_interno varchar(250),
data_inizio timestamp,
data_fine timestamp,
CONSTRAINT pk_auth_t_batch_utenti_abilitazione_massiva  PRIMARY KEY (id),
CONSTRAINT auth_t_batch_abilitazione_massiva_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (id_batch) REFERENCES lcce.auth_t_batch_abilitazione_massiva(id),
CONSTRAINT auth_t_batch_utente_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (utente) REFERENCES lcce.auth_t_utente(id),
CONSTRAINT auth_d_ruolo_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (ruolo) REFERENCES lcce.auth_d_ruolo(id),
CONSTRAINT auth_t_collocazione_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (collocazione) REFERENCES lcce.auth_t_collocazione(col_id),
CONSTRAINT auth_d_applicazione_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (abilitazione) REFERENCES lcce.auth_d_applicazione(id),
CONSTRAINT auth_t_funzionalita_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (profilo) REFERENCES lcce.auth_t_funzionalita(fnz_id),
CONSTRAINT auth_l_messaggi_errore_auth_t_batch_utenti_abilitazione_massiva FOREIGN KEY (errore_utente) REFERENCES lcce.auth_d_messaggi_utente(id)

);


INSERT INTO lcce.auth_d_batch_stato
(batch_stato_id, batch_stato_codice, batch_stato_descrizione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_d_batch_stato_batch_stato_id_seq'), 'DAELAB', 'Da eleaborare', now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO lcce.auth_d_batch_stato
(batch_stato_id, batch_stato_codice, batch_stato_descrizione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_d_batch_stato_batch_stato_id_seq'), 'INELAB', 'In elaborazione', now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO lcce.auth_d_batch_stato
(batch_stato_id, batch_stato_codice, batch_stato_descrizione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval('auth_d_batch_stato_batch_stato_id_seq'), 'COMPLERR', 'Compleatato con errori', now(), NULL, now(), now(), NULL, 'CSI');

INSERT INTO lcce.auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('seq_auth_d_messaggi_utente'), 'MSGCONF035', 'Operazione presa in carico.', 'S', now());
INSERT INTO auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('seq_auth_d_messaggi_utente'), 'MASS_ERR_001', 'L''utente risulta gia'' abilitato al SOL selezionato', 'E', now());
INSERT INTO auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('seq_auth_d_messaggi_utente'), 'MASS_ERR_002', 'L''utente non risulta abilitato al SOL selezionato', 'E', now());
INSERT INTO auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('seq_auth_d_messaggi_utente'), 'MASS_ERR_003', 'Errore durante l''abilitazione al SOL selezionato', 'E', now());


DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.14','Release 1.0.14', 'Incrementale', 'dmaloginccedb-1.0.13-1.0.14.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,14,'14',NOW(),'dmaloginccedb-1.0.13-1.0.14.sql');
		END IF;
	END IF;
END
$$;
