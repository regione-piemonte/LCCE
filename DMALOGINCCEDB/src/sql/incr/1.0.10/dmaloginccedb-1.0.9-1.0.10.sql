-- IUNCO
/* sostiuto con mail IUNCO del 6/6/2022
CREATE TABLE auth_t_preferenza (
preferenza_id BIGSERIAL NOT NULL,
id_utente BIGINT NOT NULL,
id_applicazione BIGINT,
id_ruolo BIGINT,
col_id BIGINT,
email BOOLEAN,
push BOOLEAN,
sms BOOLEAN,
data_inserimento TIMESTAMP DEFAULT now() NOT NULL,
data_aggiornamento TIMESTAMP,
data_cancellazione TIMESTAMP,
PRIMARY KEY (preferenza_id)
);

CREATE INDEX IDX_auth_t_preferenza_1 ON auth_t_preferenza (id_utente);
CREATE INDEX IDX_auth_t_preferenza_2 ON auth_t_preferenza (id_applicazione);
CREATE INDEX IDX_auth_t_preferenza_3 ON auth_t_preferenza (id_ruolo);

CREATE INDEX IDX_auth_t_preferenza_4 ON auth_t_preferenza (col_id);ALTER TABLE auth_t_preferenza ADD CONSTRAINT auth_t_utente_auth_t_preferenza
FOREIGN KEY (id_utente) REFERENCES auth_t_utente (id);

ALTER TABLE auth_t_preferenza ADD CONSTRAINT auth_d_applicazione_auth_t_preferenza
FOREIGN KEY (id_applicazione) REFERENCES auth_d_applicazione (id);

ALTER TABLE auth_t_preferenza ADD CONSTRAINT auth_d_ruolo_auth_t_preferenza
FOREIGN KEY (id_ruolo) REFERENCES auth_d_ruolo (id);

ALTER TABLE auth_t_preferenza ADD CONSTRAINT auth_t_collocazione_auth_t_preferenza
FOREIGN KEY (col_id) REFERENCES lcce.auth_t_collocazione (col_id);

COMMENT ON COLUMN auth_t_preferenza.id_utente IS 'utente';
COMMENT ON COLUMN auth_t_preferenza.id_applicazione IS 'applicazione';
COMMENT ON COLUMN auth_t_preferenza.id_ruolo IS 'ruolo';
COMMENT ON COLUMN auth_t_preferenza.col_id IS 'collocazione';

ALTER TABLE lcce.auth_t_utente ADD email text NULL;
ALTER TABLE lcce.auth_t_utente ADD telefono text NULL;
*/

CREATE TABLE lcce.auth_t_preferenza (
preferenza_id BIGSERIAL NOT NULL,
id_utente BIGINT NOT NULL,
id_applicazione BIGINT,
id_ruolo BIGINT,
col_id BIGINT,
data_inserimento TIMESTAMP DEFAULT now() NOT NULL,
data_aggiornamento TIMESTAMP,
data_cancellazione TIMESTAMP,
CONSTRAINT auth_t_preferenza_pkey PRIMARY KEY (preferenza_id)
);

ALTER TABLE lcce.auth_t_preferenza ADD CONSTRAINT auth_t_utente_auth_t_preferenza
FOREIGN KEY (id_utente) REFERENCES lcce.auth_t_utente (id);
ALTER TABLE lcce.auth_t_preferenza ADD CONSTRAINT auth_d_applicazione_auth_t_preferenza
FOREIGN KEY (id_applicazione) REFERENCES lcce.auth_d_applicazione (id);
ALTER TABLE lcce.auth_t_preferenza ADD CONSTRAINT auth_d_ruolo_auth_t_preferenza
FOREIGN KEY (id_ruolo) REFERENCES lcce.auth_d_ruolo (id);
ALTER TABLE lcce.auth_t_preferenza ADD CONSTRAINT auth_t_collocazione_auth_t_preferenza
FOREIGN KEY (col_id) REFERENCES lcce.auth_t_collocazione (col_id);
COMMENT ON COLUMN lcce.auth_t_preferenza.id_utente IS 'utente';
COMMENT ON COLUMN lcce.auth_t_preferenza.id_applicazione IS 'applicazione';
COMMENT ON COLUMN lcce.auth_t_preferenza.id_ruolo IS 'ruolo';
COMMENT ON COLUMN lcce.auth_t_preferenza.col_id IS 'collocazione';


CREATE TABLE lcce.auth_t_preferenza_fruitore (
preferenza_fruitore_id BIGSERIAL NOT NULL,
fruitore_nome TEXT NOT NULL,
fruitore_descrizione TEXT,
id_applicazione BIGINT,
id_ruolo BIGINT,
col_id BIGINT,
email BOOLEAN,
push BOOLEAN,
sms BOOLEAN,
data_inserimento TIMESTAMP DEFAULT now() NOT NULL,
data_aggiornamento TIMESTAMP,
data_cancellazione TIMESTAMP,
PRIMARY KEY (preferenza_fruitore_id)
);

CREATE INDEX IDX_auth_t_preferenza_fruitore_1 ON lcce.auth_t_preferenza_fruitore (id_applicazione);
CREATE INDEX IDX_auth_t_preferenza_fruitore_2 ON lcce.auth_t_preferenza_fruitore (id_ruolo);
CREATE INDEX IDX_auth_t_preferenza_fruitore_3 ON lcce.auth_t_preferenza_fruitore (col_id);

ALTER TABLE lcce.auth_t_preferenza_fruitore ADD CONSTRAINT auth_t_collocazione_auth_t_preferenza_fruitore
FOREIGN KEY (col_id) REFERENCES lcce.auth_t_collocazione (col_id);
ALTER TABLE lcce.auth_t_preferenza_fruitore ADD CONSTRAINT auth_d_applicazione_auth_t_preferenza_fruitore
FOREIGN KEY (id_applicazione) REFERENCES lcce.auth_d_applicazione (id);
ALTER TABLE lcce.auth_t_preferenza_fruitore ADD CONSTRAINT auth_d_ruolo_auth_t_preferenza_fruitore
FOREIGN KEY (id_ruolo) REFERENCES lcce.auth_d_ruolo (id);
COMMENT ON COLUMN lcce.auth_t_preferenza_fruitore.id_applicazione IS 'applicazione';
COMMENT ON COLUMN lcce.auth_t_preferenza_fruitore.id_ruolo IS 'ruolo';
COMMENT ON COLUMN lcce.auth_t_preferenza_fruitore.col_id IS 'collocazione';


CREATE TABLE auth_t_preferenza_salvata (
preferenza_salvata_id BIGSERIAL NOT NULL,
preferenza_fruitore_id BIGINT NOT NULL,
preferenza_id BIGINT NOT NULL,
email BOOLEAN,
push BOOLEAN,
sms BOOLEAN,
data_inserimento TIMESTAMP DEFAULT now() NOT NULL,
data_aggiornamento TIMESTAMP,
data_cancellazione TIMESTAMP,
CONSTRAINT PK_auth_t_preferenza_salvata PRIMARY KEY (preferenza_salvata_id)
);

CREATE INDEX IDX_auth_t_preferenza_salvata_1 ON auth_t_preferenza_salvata (preferenza_fruitore_id);
CREATE INDEX IDX_auth_t_preferenza_salvata_2 ON auth_t_preferenza_salvata (preferenza_id);

ALTER TABLE auth_t_preferenza_salvata ADD CONSTRAINT auth_t_preferenza_fruitore_auth_t_preferenza_salvata
FOREIGN KEY (preferenza_fruitore_id) REFERENCES lcce.auth_t_preferenza_fruitore (preferenza_fruitore_id);
ALTER TABLE auth_t_preferenza_salvata ADD CONSTRAINT auth_t_preferenza_auth_t_preferenza_salvata
FOREIGN KEY (preferenza_id) REFERENCES lcce.auth_t_preferenza (preferenza_id);


-- GROSSO
CREATE TABLE lcce.auth_d_azienda (
	id_azienda serial NOT NULL,
	cod_azienda text NOT NULL,
	desc_azienda text NULL,
	data_inizio_val timestamp NULL,
	data_fine_val timestamp NULL,
	CONSTRAINT auth_d_azienda_pk PRIMARY KEY (id_azienda)
);

-- drop table lcce.auth_r_utente_visibilita_azienda;
CREATE TABLE lcce.auth_r_utente_visibilita_azienda (
	id bigserial NOT NULL,
	id_utente int8 NOT NULL,
	id_azienda int4 NULL,
	data_inizio_val timestamp NULL,
	data_fine_val timestamp NULL,
	cf_operatore text NULL,
	data_operazione timestamp NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	CONSTRAINT auth_r_utente_visibilita_azienda_pk PRIMARY KEY (id)
);
CREATE INDEX auth_r_utente_visibilita_azienda_id_utente_idx ON lcce.auth_r_utente_visibilita_azienda (id_utente);

ALTER TABLE lcce.auth_r_utente_visibilita_azienda ADD CONSTRAINT auth_d_azienda_auth_r_utente_visibilita_azienda FOREIGN KEY (id_azienda) REFERENCES auth_d_azienda(id_azienda);

ALTER TABLE lcce.auth_r_utente_visibilita_azienda ADD CONSTRAINT auth_t_utente_auth_r_utente_visibilita_azienda FOREIGN KEY (id_utente) REFERENCES auth_t_utente(id);


/* -- usiamo quella esistente
CREATE TABLE lcce.auth_r_ruolo_profilo (
	id serial NOT NULL,
	id_ruolo int8 not null,
	id_profilo int8 not null,
	data_inizio_val timestamp NULL,
	data_fine_val timestamp NULL,
	CONSTRAINT auth_r_ruolo_profilo_pk PRIMARY KEY (id)
);

*/

ALTER TABLE lcce.auth_t_collocazione ADD flag_azienda char(1) NULL;

ALTER TABLE lcce.auth_r_utente_collocazione ADD col_fonte_id int8 NULL;
ALTER TABLE lcce.auth_r_utente_collocazione ADD CONSTRAINT auth_d_fonte_auth_r_utente_collocazione FOREIGN KEY (col_fonte_id) REFERENCES auth_d_fonte(fonte_id);

ALTER TABLE lcce.auth_r_ruolo_utente ADD flag_configuratore char(1) NULL;
ALTER TABLE lcce.auth_r_ruolo_utente ADD col_fonte_id int8 NULL;

ALTER TABLE lcce.auth_r_ruolo_utente ADD CONSTRAINT auth_d_fonte_auth_r_ruolo_utente FOREIGN KEY (col_fonte_id) REFERENCES auth_d_fonte(fonte_id);


--drop table lcce.auth_r_ruolo_ruoli;

CREATE TABLE lcce.auth_r_ruolo_ruoli (
	id bigserial NOT NULL,
	id_ruolo_operatore int8 NOT NULL,
	id_ruolo_selezionabile int8 NOT NULL,
	col_tipo_id int8 NULL,
	data_inizio_val  timestamp not NULL,
	data_fine_val timestamp NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	--cf_operatore text NULL,
	-- data_operazione timestamp NULL,
	constraint auth_r_ruolo_ruoli_pk PRIMARY KEY (id)
);
CREATE INDEX auth_r_ruolo_ruoli_id_ruolo_operatore_idx ON lcce.auth_r_ruolo_ruoli (id_ruolo_operatore);

ALTER TABLE lcce.auth_r_ruolo_ruoli ADD CONSTRAINT auth_d_ruolo_auth_r_ruolo_ruoli_id_ruolo_operatore FOREIGN KEY (id_ruolo_operatore) REFERENCES auth_d_ruolo(id);

ALTER TABLE lcce.auth_r_ruolo_ruoli ADD CONSTRAINT auth_d_ruolo_auth_r_ruolo_ruoli_id_ruolo_selezionabile FOREIGN KEY (id_ruolo_selezionabile) REFERENCES auth_d_ruolo(id);
ALTER TABLE lcce.auth_r_ruolo_ruoli ADD CONSTRAINT auth_d_ruolo_auth_r_ruolo_ruoli_col_tipo_id FOREIGN KEY (col_tipo_id) REFERENCES auth_d_collocazione_tipo(col_tipo_id);

--- 2022-04-28
ALTER TABLE lcce.auth_t_utente ADD ultimo_accesso_pua timestamp NULL;

CREATE TABLE lcce.auth_r_ruolo_compatibilita (
	id_ruocomp bigserial NOT NULL,
	id_ruolo int8 NOT NULL,
	id_ruolo_compatibile int8 NULL,
	data_inizio_val timestamp NULL,
	data_fine_val timestamp NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	constraint auth_r_ruolo_compatibilita_pk PRIMARY KEY (id_ruocomp)
);

ALTER TABLE lcce.auth_r_ruolo_compatibilita ADD CONSTRAINT auth_r_ruolo_compatibilita_id_ruolo FOREIGN KEY (id_ruolo) REFERENCES auth_d_ruolo(id);

ALTER TABLE lcce.auth_r_ruolo_compatibilita ADD CONSTRAINT auth_r_ruolo_compatibilita_id_ruolo_compatibile FOREIGN KEY (id_ruolo_compatibile) REFERENCES auth_d_ruolo(id);

CREATE TABLE lcce.auth_r_colazienda_profilo (
	id_colaz_prof bigserial NOT NULL,
	id_collocazione int8 NOT NULL,
	fnz_id int8 NULL,
	data_inizio_val timestamp NULL,
	data_fine_val timestamp NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	constraint auth_r_colazienda_profilo_pk PRIMARY KEY (id_colaz_prof)
);

ALTER TABLE lcce.auth_r_colazienda_profilo ADD CONSTRAINT auth_r_colazienda_profilo_id_collocazione FOREIGN KEY (id_collocazione) REFERENCES auth_t_collocazione(col_id);

ALTER TABLE lcce.auth_r_colazienda_profilo ADD CONSTRAINT auth_r_colazienda_profilo_fnz_id FOREIGN KEY (fnz_id) REFERENCES auth_t_funzionalita(fnz_id);
/*
ALTER TABLE lcce.auth_r_ruolo_ruoli ADD col_tipo_id int8 NULL;
ALTER TABLE lcce.auth_r_ruolo_ruoli ADD CONSTRAINT auth_r_ruolo_ruoli_col_tipo_id FOREIGN KEY (col_tipo_id) REFERENCES auth_d_collocazione_tipo(col_tipo_id);


ALTER TABLE lcce.auth_r_ruolo_ruoli drop column data_operazione;
ALTER TABLE lcce.auth_r_ruolo_ruoli drop column cf_operatore;
*/
ALTER TABLE lcce.auth_r_ruolo_profilo drop column data_aggiornamento;
ALTER TABLE lcce.auth_r_ruolo_profilo drop column data_cancellazione;
ALTER TABLE lcce.auth_r_ruolo_profilo drop column cf_operatore;

ALTER TABLE lcce.auth_r_ruolo_compatibilita ALTER COLUMN data_inizio_val SET NOT NULL;
ALTER TABLE lcce.auth_r_ruolo_compatibilita ALTER COLUMN id_ruolo_compatibile SET NOT NULL;

ALTER TABLE lcce.auth_r_colazienda_profilo ALTER COLUMN fnz_id SET NOT NULL;
ALTER TABLE lcce.auth_r_colazienda_profilo ALTER COLUMN data_inizio_val SET NOT NULL;

ALTER TABLE lcce.auth_r_utente_visibilita_azienda ALTER COLUMN id_azienda SET NOT NULL;
ALTER TABLE lcce.auth_r_utente_visibilita_azienda ALTER COLUMN data_inizio_val SET NOT NULL;

-- aggiunta richiesta ruggiero
ALTER TABLE lcce.auth_l_xml_messaggi ALTER COLUMN id_messaggio DROP NOT NULL;

--  aggiunta richiesta  Gaudenzi


CREATE TABLE lcce.auth_d_sistemi_richiedenti  (
	id bigserial NOT NULL,
	codice text NOT NULL,
	descrizione text NOT NULL,
	data_inizio_validita timestamp NULL,
	data_fine_validita timestamp NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	data_aggiornamento timestamp NULL,
	cf_operatore text,
	constraint auth_d_sistemi_richiedenti_pk PRIMARY KEY (id)
);

COMMENT ON TABLE lcce.auth_d_sistemi_richiedenti is 'La tabella conterrà i codici dei dipartimentali delle ASR che richiameranno le api del configuratore. I codici verranno assegnati da CSI ai fornitori delle ASR su richiesta.'

-- AGGIUNTE GROSSO
CREATE UNIQUE INDEX if not exists auth_d_messaggi_utente_codice_idx ON lcce.auth_d_messaggi_utente (codice);
-- aggiunta richiesta bontempi
/*
ALTER TABLE lcce.auth_t_collocazione ADD cod_struttura text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD denom_struttura text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD cod_uo text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD denom_uo text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD cod_multi_spec text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD denom_multi_spec text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD codice_elemento_organizzativo text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD desc_elemento text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD id_ambulatorio text NULL;
ALTER TABLE lcce.auth_t_collocazione ADD denom_ambulatorio text NULL;
*/

INSERT INTO lcce.auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval ('auth_t_funzionalita_fnz_id_seq'), 'OP-RicercaApplicazione', 'Abilita ricerca applicazione', (select fnz_tipo_id FROM lcce.auth_d_funzionalita_tipo where fnz_tipo_codice ='PROF'), (SELECT id from auth_d_applicazione WHERE codice='SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO lcce.auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval ('auth_t_funzionalita_fnz_id_seq'), 'OP-ModificaApplicazione', 'Abilita modifica applicazione', (select fnz_tipo_id FROM lcce.auth_d_funzionalita_tipo where fnz_tipo_codice ='PROF'), (SELECT id from auth_d_applicazione WHERE codice='SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO lcce.auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval ('auth_t_funzionalita_fnz_id_seq'), 'OP-InserisciApplicazione', 'Abilita inserimento nuova applicazione', (select fnz_tipo_id FROM lcce.auth_d_funzionalita_tipo where fnz_tipo_codice ='PROF'), (SELECT id from auth_d_applicazione WHERE codice='SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI');



INSERT INTO lcce.auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval ('auth_t_funzionalita_fnz_id_seq'), 'OP-RicercaFunzionalita', 'Abilita ricerca funzionalita', (select fnz_tipo_id FROM lcce.auth_d_funzionalita_tipo where fnz_tipo_codice ='PROF'), (SELECT id from auth_d_applicazione WHERE codice='SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO lcce.auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval ('auth_t_funzionalita_fnz_id_seq'), 'OP-ModificaFunzionalita', 'Abilita modifica funzionalita', (select fnz_tipo_id FROM lcce.auth_d_funzionalita_tipo where fnz_tipo_codice ='PROF'), (SELECT id from auth_d_applicazione WHERE codice='SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI');
INSERT INTO lcce.auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(nextval ('auth_t_funzionalita_fnz_id_seq'), 'OP-InserisciFunzionalita', 'Abilita inserimento nuova funzionalita', (select fnz_tipo_id FROM lcce.auth_d_funzionalita_tipo where fnz_tipo_codice ='PROF'), (SELECT id from auth_d_applicazione WHERE codice='SOLCONFIG'), now(), NULL, now(), now(), NULL, 'CSI');


INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-RicercaApplicazione'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-RicercaApplicazione'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'SUPERUSERCONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';



INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-ModificaApplicazione'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-ModificaApplicazione'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'SUPERUSERCONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';



INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-InserisciApplicazione'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-InserisciApplicazione'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'SUPERUSERCONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';



INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-RicercaFunzionalita'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-RicercaFunzionalita'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'SUPERUSERCONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';



INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-ModificaFunzionalita'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-ModificaFunzionalita'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'SUPERUSERCONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';


INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-InserisciFunzionalita'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

INSERT INTO lcce.auth_r_funzionalita_tree
(fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore) 
SELECT (SELECT fnz_id FROM lcce.auth_t_funzionalita where fnz_codice='OP-InserisciFunzionalita'),
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'SUPERUSERCONF'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL),
NOW(),NULL,NOW(),NOW(),NULL,'CSI';

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.10','Release 1.0.10', 'Incrementale', 'dmaloginccedb-1.0.9-1.0.10.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,10,'10',NOW(),'dmaloginccedb-1.0.9-1.0.10.sql');
		END IF;
	END IF;
END
$$;
