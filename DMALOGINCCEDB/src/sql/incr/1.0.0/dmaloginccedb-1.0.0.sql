--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --


--	SCRIPT INIZIALIZZAZIONE LOGINCCE
--	VERSIONE: 01C
--	SCHEMA DATI: LCCE
--	CREATE DATE: 13/11/2019
--	MODIFY DATE: 03/12/2019
--	NOTA:	ADEGUAMENTO SEARCH_PATH
--			SELECT pg_catalog.set_config('search_path', 'lcce,lcce_rw,public', false);
/*
	ALTER ROLE lcce 	IN DATABASE "DBD1LCCE"	SET search_path TO lcce,public;
	ALTER ROLE lcce_rw	IN DATABASE "DBD1LCCE"	SET search_path TO lcce,public;
*/

CREATE TABLE auth_d_applicazione(
id bigint not null,
codice character varying(20) not null,
descrizione character varying(150) not null,
pin_richiesto character varying(1) not null DEFAULT 'N',
urlverifyloginconditions character varying(250),
data_inserimento timestamp not null,
CONSTRAINT pk_auth_d_applicazione PRIMARY KEY (id),
CONSTRAINT uk_auth_d_applicazione_01 UNIQUE  (codice)
) WITH (
    OIDS = FALSE
);


create table auth_t_configurazione(
id bigint not null,
chiave character varying(50),
valore character varying(250),
descrizione character varying(250),
data_inserimento timestamp not null,
CONSTRAINT pk_auth_t_configurazione PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);


create table auth_t_utente(
id bigint not null,
nome character varying(20) not null,
cognome character varying(20) not null,
codice_fiscale character varying(16) not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_t_utente PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);


create table auth_d_ruolo(
id bigint not null,
codice character varying(20) not null,
descrizione character varying(150) not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_d_ruolo PRIMARY KEY (id),
CONSTRAINT uk_auth_d_o_01 UNIQUE (codice)
) WITH (
    OIDS = FALSE
);


create table auth_r_ruolo_utente(
id bigint not null,
id_utente bigint not null,
id_ruolo bigint not null,
data_inizio_validita timestamp not null,
data_fine_validita timestamp,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_r_ruolo_utente PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_r_ruolo_utente ADD CONSTRAINT fk_auth_t_utente_01
	FOREIGN KEY (id_utente) REFERENCES auth_t_utente(id);
ALTER TABLE auth_r_ruolo_utente ADD CONSTRAINT fk_auth_auth_d_ruolo_02
	FOREIGN KEY (id_ruolo) REFERENCES auth_d_ruolo(id);


create table auth_r_abilitazione(
id bigint not null,
id_ruolo_utente bigint not null,
id_applicazione bigint not null,
codice_abilitazione text not null,
data_inizio_validita timestamp not null,
data_fine_validita timestamp,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_r_abilitazione PRIMARY KEY (id),
CONSTRAINT uk_auth_r_abilitazione_01 UNIQUE (codice_abilitazione)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_r_abilitazione ADD CONSTRAINT fk_auth_r_ruolo_utente_01
	FOREIGN KEY (id_ruolo_utente) REFERENCES auth_r_ruolo_utente(id);
ALTER TABLE auth_r_abilitazione ADD CONSTRAINT fk_auth_d_applicazione_02
	FOREIGN KEY (id_applicazione) REFERENCES auth_d_applicazione(id);	

	
create table auth_d_servizi(
id bigint not null,
codice character varying(20) not null,
descrizione character varying(512) not null,
tipo_servizio character varying(1) not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_d_servizi PRIMARY KEY (id),
CONSTRAINT uk_auth_d_servizi_01 UNIQUE (codice)
) WITH (
    OIDS = FALSE
);
COMMENT ON COLUMN auth_d_servizi.tipo_servizio IS '''I'' per i servizi Interni a LCCE, ''E'' per i servizi Esterni a LCCE';


create table auth_d_catalogo_log(
id bigint not null,
codice character varying(20) not null,
descrizione character varying(512) not null,
descrizione_log character varying(512) not null,
fonte character varying(20),
tipo_errore character varying(20),
data_inserimento timestamp not null,
CONSTRAINT pk_auth_d_catalogo_log PRIMARY KEY (id),
CONSTRAINT uk_auth_d_catalogo_log_01 UNIQUE (codice)
) WITH (
    OIDS = FALSE
);
COMMENT ON COLUMN auth_d_catalogo_log.tipo_errore IS 'Bloccante o Warning (NULL per gli eventi positivi)';


create table auth_d_catalogo_log_audit(
id bigint not null,
codice character varying(20) not null,
descrizione character varying(512) not null,
descrizione_codice character varying(20),
tipo character varying(1),
data_inserimento timestamp not null,
CONSTRAINT pk_auth_d_catalogo_log_audit PRIMARY KEY (id),
CONSTRAINT uk_auth_d_catalogo_log_audit_01 UNIQUE (codice)
) WITH (
    OIDS = FALSE
);


create table auth_d_catalogo_parametri(
id bigint not null,
id_applicazione bigint not null,
codice character varying(20) not null,
descrizione character varying(512) not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_d_catalogo_parametri PRIMARY KEY (id),
CONSTRAINT uk_auth_d_catalogo_parametri_01 UNIQUE (codice)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_d_catalogo_parametri ADD CONSTRAINT fk_auth_d_applicazione_01 
FOREIGN KEY (id_applicazione) REFERENCES auth_d_applicazione(id);


create table auth_l_messaggi(
id bigint not null,
id_abilitazione bigint,
codice_abilitazione text,
id_servizio bigint not null,
certificato character varying(50),
applicazione character varying(20),
cf_richiedente character varying(16),
ruolo_richiedente character varying(20),
cf_assistito character varying(20),
client_ip character varying(50),
token character varying(100),
data_ricezione timestamp not null,
data_risposta timestamp,
esito character varying(60),
data_inserimento timestamp not null,
data_aggiornamento timestamp,
CONSTRAINT pk_auth_l_messaggi PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_messaggi ADD CONSTRAINT fk_auth_r_abilitazione_01
	FOREIGN KEY (id_abilitazione) REFERENCES auth_r_abilitazione(id);
ALTER TABLE auth_l_messaggi ADD CONSTRAINT fk_auth_d_servizi_02
	FOREIGN KEY (id_servizio) REFERENCES auth_d_servizi(id);

	
create table auth_l_log(
id bigint not null,
id_servizio bigint not null,
cf_assistito character varying(16),
id_catalogo_log bigint,
codice_log varchar(20),
informazioni_tracciate text not null,
info_aggiuntive_errore text,
id_messaggi bigint not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_l_log PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_log ADD CONSTRAINT fk_auth_d_servizi_01
    FOREIGN KEY (id_servizio) REFERENCES auth_d_servizi(id);
ALTER TABLE auth_l_log ADD CONSTRAINT fk_auth_d_catalogo_log_02
    FOREIGN KEY (id_catalogo_log) REFERENCES auth_d_catalogo_log(id);
ALTER TABLE auth_l_log ADD CONSTRAINT fk_auth_l_messaggi_03
    FOREIGN KEY (id_messaggi) REFERENCES auth_l_messaggi(id);


create table auth_l_log_audit(
id bigint not null,
token varchar(100),
codice_log varchar(20),
informazioni_tracciate text not null,
cf_assistito character varying(16),
id_catalogo_log_audit bigint not null,
id_applicazione bigint,
id_abilitazione bigint,
id_utente_richiedente bigint,
id_ruolo bigint,
codice_fiscale_richiedente character varying(16),
ip_richiedente character varying(20),
id_servizio bigint,
id_messaggio bigint not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_l_log_audit PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_d_catalogo_log_audit_01
    FOREIGN KEY (id_catalogo_log_audit) REFERENCES auth_d_catalogo_log_audit(id);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_d_applicazione_02
    FOREIGN KEY (id_applicazione) REFERENCES auth_d_applicazione(id);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_t_utente_03
    FOREIGN KEY (id_utente_richiedente) REFERENCES auth_t_utente(id);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_d_ruolo_04
    FOREIGN KEY (id_ruolo) REFERENCES auth_d_ruolo(id);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_d_servizi_05
    FOREIGN KEY (id_servizio) REFERENCES auth_d_servizi(id);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_r_abilitazione_06
    FOREIGN KEY (id_abilitazione) REFERENCES auth_r_abilitazione(id);
ALTER TABLE auth_l_log_audit ADD CONSTRAINT fk_auth_l_messaggi_07
    FOREIGN KEY (id_messaggio) REFERENCES auth_l_messaggi(id);	


create table auth_l_login_data(
id bigint not null,
id_abilitazione bigint not null,
cf_richiedente character varying(16) not null,
cf_assistito character varying(16) not null,
client_ip character varying(50) not null,
remote_ip character varying(50) not null,
token character varying(100),
data_richiesta timestamp,
data_utilizzo timestamp,
data_inserimento timestamp not null,
data_aggiornamento timestamp,
CONSTRAINT pk_auth_l_login_data PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_login_data ADD CONSTRAINT fk_auth_r_abilitazione_01
	FOREIGN KEY (id_abilitazione) REFERENCES auth_r_abilitazione(id);

	
create table auth_l_login_param(
id bigint not null,
id_login bigint not null,
codice character varying(50) not null,
valore character varying(250) not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_l_login_param PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_login_param ADD CONSTRAINT fk_auth_l_login_data_01
FOREIGN KEY (id_login) REFERENCES auth_l_login_data(id);


create table auth_l_messaggi_errore(
id bigint not null,
id_messaggio bigint not null,
id_catalogo_log bigint,
codice_errore character varying(20) not null,
descrizione_errore character varying(512) not null,
controllore character varying(25),
tipo_errore character varying(20) not null,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_l_messaggi_errore PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_messaggi_errore ADD CONSTRAINT fk_auth_l_messaggi_01
    FOREIGN KEY (id_messaggio) REFERENCES auth_l_messaggi(id);
ALTER TABLE auth_l_messaggi_errore ADD CONSTRAINT fk_auth_d_catalogo_log_02
    FOREIGN KEY (id_catalogo_log) REFERENCES auth_d_catalogo_log(id);
	

create table auth_l_xml_messaggi(
id bigint not null,
id_messaggio bigint not null,
xml_in bytea not null,
xml_out bytea,
data_inserimento timestamp not null,
data_aggiornamento timestamp,
CONSTRAINT pk_auth_l_xml_messaggi PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_l_xml_messaggi ADD CONSTRAINT fk_auth_l_messaggi_01
	FOREIGN KEY (id_messaggio) REFERENCES auth_l_messaggi(id);
	

create table auth_xml_servizi_richiamati(
id bigint not null,
id_messaggio bigint,
id_servizio bigint not null,
xml_in bytea not null,
xml_out bytea,
data_chiamata timestamp not null,
data_risposta timestamp,
esito character varying(60),
data_inserimento timestamp not null,
data_aggiornamento timestamp,
CONSTRAINT pk_auth_l_xml_servizi_richiamati PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
);
ALTER TABLE auth_xml_servizi_richiamati ADD CONSTRAINT fk_auth_l_messaggi_01
	FOREIGN KEY (id_messaggio) REFERENCES auth_l_messaggi(id);
ALTER TABLE auth_xml_servizi_richiamati ADD CONSTRAINT fk_auth_d_servizi_02
	FOREIGN KEY (id_servizio) REFERENCES auth_d_servizi(id);

	
create table auth_t_credenziali_servizi(
id bigint not null,
id_servizio bigint not null,
id_applicazione bigint,
username character varying(50) not null,
password text not null,
data_inizio_validita timestamp not null,
data_fine_validita timestamp,
data_inserimento timestamp not null,
CONSTRAINT pk_auth_t_credenziali_servizi PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE auth_t_credenziali_servizi ADD CONSTRAINT fk_auth_d_servizi_01
	FOREIGN KEY (id_servizio) REFERENCES auth_d_servizi(id);
ALTER TABLE auth_t_credenziali_servizi ADD CONSTRAINT fk_auth_d_applicazione_02
	FOREIGN KEY (id_applicazione) REFERENCES auth_d_applicazione(id);
	
/*
DROP sequence seq_auth_d_applicazione;
DROP sequence seq_auth_d_catalogo_log;
DROP sequence seq_auth_d_catalogo_log_audit;
DROP sequence seq_auth_d_catalogo_parametri;
DROP sequence seq_auth_d_servizi;
DROP sequence seq_auth_l_log;
DROP sequence seq_auth_l_log_audit;
DROP sequence seq_auth_l_login_data;
DROP sequence seq_auth_l_login_param;
DROP sequence seq_auth_l_messaggi;
DROP sequence seq_auth_l_messaggi_errore;
DROP sequence seq_auth_l_xml_messaggi;
DROP sequence seq_auth_r_abilitazione;
DROP sequence seq_auth_r_ruolo_utente;
DROP sequence seq_auth_t_configurazione;
DROP sequence seq_auth_t_utente;
DROP sequence seq_auth_xml_servizi_richiamati;
DROP sequence seq_auth_d_ruolo;
DROP sequence seq_auth_t_credenziali_servizi;
*/
	
create sequence seq_auth_d_applicazione START WITH 1 INCREMENT BY 1;
create sequence seq_auth_d_catalogo_log START WITH 1 INCREMENT BY 1;
create sequence seq_auth_d_catalogo_log_audit START WITH 1 INCREMENT BY 1;
create sequence seq_auth_d_catalogo_parametri START WITH 1 INCREMENT BY 1;
create sequence seq_auth_d_servizi START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_log START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_log_audit START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_login_data START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_login_param START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_messaggi START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_messaggi_errore START WITH 1 INCREMENT BY 1;
create sequence seq_auth_l_xml_messaggi START WITH 1 INCREMENT BY 1;
create sequence seq_auth_r_abilitazione START WITH 1 INCREMENT BY 1;
create sequence seq_auth_r_ruolo_utente START WITH 1 INCREMENT BY 1;
create sequence seq_auth_t_configurazione START WITH 1 INCREMENT BY 1;
create sequence seq_auth_t_utente START WITH 1 INCREMENT BY 1;
create sequence seq_auth_xml_servizi_richiamati START WITH 1 INCREMENT BY 1;
create sequence seq_auth_d_ruolo START WITH 1 INCREMENT BY 1;
create sequence seq_auth_t_credenziali_servizi START WITH 1 INCREMENT BY 1;

--	INIZIALIZZAZIONE TABELLE
INSERT INTO auth_d_applicazione
(id, codice, descrizione, pin_richiesto, urlverifyloginconditions, data_inserimento)
VALUES
(nextval('seq_auth_d_applicazione'), 'DMAWA', 'WebApp DMA', 'N', null, now());
INSERT INTO auth_d_applicazione
(id, codice, descrizione, pin_richiesto, urlverifyloginconditions, data_inserimento)
VALUES
(nextval('seq_auth_d_applicazione'), 'CCE', 'Cartelle Cliniche Elettroniche', 'N', null, now());


INSERT INTO auth_d_catalogo_parametri
(id, id_applicazione, codice, descrizione, data_inserimento) 
SELECT nextval('seq_auth_d_catalogo_parametri'), applicazione.id, 'TIPO_DOCUMENTO', 'Tipo di documento', now()
FROM auth_d_applicazione applicazione
WHERE applicazione.codice = 'DMAWA';


INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'MMG', 'Medico di base', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'PLS', 'Pediatra di Libera scelta', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'INF', 'Infermiere', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'AAS', 'Ruolo medico di INI', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'DSA', 'Direttore Sanitario di INI', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'MEDOSP', 'Medico Ospedaliero', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'MEDRSA', 'Medico di RSA (Residenza Sanitaria Assistenziale)', now());
INSERT INTO auth_d_ruolo (id, codice, descrizione, data_inserimento)
VALUES
(nextval('seq_auth_d_ruolo'), 'MEDRP', 'Medico di Rete di Patologia', now());


INSERT INTO auth_d_servizi (id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES
(nextval('seq_auth_d_servizi'), 'GET_AUTH', 'GetAuthentication', 'I', now());
INSERT INTO auth_d_servizi (id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES
(nextval('seq_auth_d_servizi'), 'GET_TOK_INFO', 'GetTokenInformation', 'I', now());
INSERT INTO auth_d_servizi (id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES
(nextval('seq_auth_d_servizi'), 'VER_LOG_COND', 'VerfyLoginConditions', 'E', now());
INSERT INTO auth_d_servizi (id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES
(nextval('seq_auth_d_servizi'), 'ID_US_PASS', 'Servizio di IRIDE per identificazione mediante Username e Password', 'E', now());
INSERT INTO auth_d_servizi (id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES
(nextval('seq_auth_d_servizi'), 'ID_US_PASS_PIN', 'Servizio di IRIDE per identificazione mediante Username, Password e Pin', 'E', now());



INSERT INTO auth_d_catalogo_log_audit (id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_010', 'Token generato e Info di richiesta di autenticazione registrate', null, null, now());
INSERT INTO auth_d_catalogo_log_audit (id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_020', 'Token verificato e marcato come utilizzato', null, null, now());


INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_LOG_000', 'Chiamata al Servizio con parametri: Lista parametri di input con: Nome Parametro %s', 'Chiamata al Servizio con parametri: Lista parametri di input con: Nome Parametro %s', null, null, now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_000', 'Errore interno del servizio non riconducibile ad altri errori codificati', 'Errore interno del servizio non riconducibile ad altri errori codificati', null, 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_501', 'Errore di autenticazione', 'Errore di autenticazione', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_502', 'Il ruolo fornito da parte del sistema non è riconosciuto', 'Il ruolo fornito da parte del sistema non è riconosciuto', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_506', 'Il sistema non è stato configurato per consentire al medico di accedere dalla CCE all''applicazione richiesta', 'Il sistema non è stato configurato per consentire al medico di accedere dalla CCE all''applicazione richiesta', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_507', 'Applicazione richiesta non disponibile', 'Applicazione richiesta non disponibile', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_508', 'Il parametro Username del richiedente deve essere valorizzato', 'Il parametro Username del richiedente deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_509', 'Il parametro Password del richiedente deve essere valorizzato', 'Il parametro Password del richiedente deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_510', 'Il parametro Pin del richiedente deve essere valorizzato', 'Il parametro Pin del richiedente deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_511', 'Il parametro Ruolo Richiedente deve essere valorizzato', 'Il parametro Ruolo Richiedente deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_512', 'Il parametro Ip Client del Richiedente deve essere valorizzato', 'Il parametro Ip Client del Richiedente deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_513', 'Il parametro Applicazione deve essere valorizzato', 'Il parametro Applicazione deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_514', 'Il parametro cf Assistito deve essere valorizzato', 'Il parametro cf Assistito deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_517', 'I parametri %s non sono previsti per l''applicazione %s', 'I parametri %s non sono previsti per l''applicazione %s', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_518', 'Utente richiedente non censito per il servizio di Login CCE', 'Utente richiedente non censito per il servizio di Login CCE', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_601', 'Token non valido o scaduto', 'Token non valido o scaduto', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_603', 'Il parametro token deve essere valorizzato', 'Il parametro token deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_604', 'Il parametro applicazione deve essere valorizzato', 'Il parametro applicazione deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_605', 'Il parametro ip browser deve essere valorizzato', 'Il parametro ip browser deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_606', 'L''applicazione richiesta non coincide con quella richiesta in fase di autenticazione', 'L''applicazione richiesta non coincide con quella richiesta in fase di autenticazione', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_609', 'Applicazione non abilitata ad accedere alla base dati LoginCCE', 'Applicazione non abilitata ad accedere alla base dati LoginCCE', 'LCCE', 'Bloccante', now());
--	INCR vers.01C	(INIZIO)
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_602', 'Ip Address non congruente con quello fornito al momento della generazione del token', 'Ip Address non congruente con quello fornito al momento della generazione del token', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_515', 'Il Richiedente deve essere valorizzato', 'Il Richiedente deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_516', 'Le credenziali devono essere valorizzate', 'Le credenziali devono essere valorizzate', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_519', 'Le credenziali inserite non sono corrette', 'Le credenziali inserite non sono corrette', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log (id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES
(nextval('seq_auth_d_catalogo_log'), 'AUTH_LOG_001', 'Risposta del servizio: %s Stato risposta del servizio: %s', 'Risposta del servizio: %s Stato risposta del servizio: %s', '', '', now());
--	INCR vers.01C	(FINE)

INSERT INTO auth_t_configurazione(id, chiave, valore, descrizione, data_inserimento) 
VALUES
(nextval('seq_auth_t_configurazione'), 'TIME_TOK', '3600', 'Durata temporale del token', now());

