--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --


--	SCRIPT INCREMENTALE CONFIGURAZIONE LOGINCCE
--	MEV FARMACIA DEI SERVIZI E PUA
--	VERSIONE: 01C
--	SCHEMA DATI: LCCE
--	CREATE DATE: 26/06/2020
--	MODIFY DATE: 30/06/2020

--	INCR 04-06-2020
alter table auth_l_login_data add column ruolo_richiedente varchar(20);
alter table auth_l_login_data add column coll_codice_azienda text;
alter table auth_l_login_data add column codice_collocazione text;
alter table auth_l_login_data add column applicazione_richiesta text;
alter table auth_l_login_data add column applicazione_chiamante text;
alter table auth_l_login_data add column id_servizio_chiamante bigint;

alter table auth_l_login_data add constraint fk_login_data_servizi 
foreign key (id_servizio_chiamante) references auth_d_servizi(id);

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GET_AUTH2', 'GetAuthentication2', 'I', now());
INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'VER_LOG_COND2', 'VerifyLoginConditions2', 'E', now());
INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GET_TOK_INFO2', 'GetTokenInformation2', 'I', now());
INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GET_ABI', 'GetAbilitazioni', 'I', now());

--	INCR 10-06-2020
DO
$$
BEGIN
	IF NOT EXISTS (	select * from auth_d_catalogo_log where codice = 'AUTH_ER_626') THEN
		INSERT INTO auth_d_catalogo_log
		(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
		VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_626', 'Il parametro Applicazione Richiesta deve essere valorizzato', 'Il parametro Applicazione Richiesta deve essere valorizzato', 'LCCE', 'Bloccante', now());
	ELSE
		UPDATE auth_d_catalogo_log SET 
		descrizione = 'Il parametro Applicazione Richiesta deve essere valorizzato',
		descrizione_log = 'Il parametro Applicazione Richiesta deve essere valorizzato',
		fonte = 'LCCE',
		tipo_errore = 'Bloccante' 
		WHERE codice = 'AUTH_ER_626';
	END IF;
END
$$;

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_624', 'Il sistema non e'' stato configurato per consentire di accedere all''applicazione richiesta', 'Il sistema non e'' stato configurato per consentire di accedere all''applicazione richiesta', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_625', 'L''utente non risulta abilitato ad accedere all''applicazione richiesta', 'L''utente non risulta abilitato ad accedere all''applicazione richiesta', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_618', 'Collocazione Richiedente non valida', 'Collocazione Richiedente non valida', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_627', 'Codice Fiscale Richiedente e Collocazione Richiedente non congruenti', 'Codice Fiscale Richiedente e Collocazione Richiedente non congruenti', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_619', 'Codice Collocazione e Codice Azienda Richiedente non congruenti', 'Codice Collocazione e Codice Azienda Richiedente non congruenti', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_620', 'Non esiste alcuna abilitazione per l''utente connesso e la collocazione specificata', 'Non esiste alcuna abilitazione per l''utente connesso e la collocazione specificata', 'LCCE', 'Bloccante', now());

INSERT INTO auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval('seq_auth_t_configurazione'), 'TIME_TOK2', '3600', 'Durata temporale del token2', now());

--	INCR 05-06-2020
ALTER TABLE auth_l_login_data ALTER COLUMN cf_assistito DROP NOT NULL;
ALTER TABLE auth_l_login_data ALTER COLUMN id_abilitazione DROP NOT NULL;

--	INCR 10-06-2020
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_622', 'Il parametro Codice Azienda della collocazione deve essere valorizzato', 'Il parametro Codice Azienda della collocazione deve essere valorizzato', 'LCCE', 'Bloccante', now());
INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_623', 'Il parametro Codice Collocazione deve essere valorizzato', 'Il parametro Codice Collocazione deve essere valorizzato', 'LCCE', 'Bloccante', now());

DO
$$
BEGIN
	IF EXISTS (select * from auth_d_applicazione where codice = 'DMAWA') 
	THEN
		UPDATE auth_d_applicazione set
		urlverifyLoginConditions = 'https://xxx/CCVerifyLoginConditionsService'
		where codice = 'DMAWA';
	END IF;
END
$$;

DO
$$
BEGIN
	IF EXISTS (select * from auth_d_applicazione where codice = 'DMACMPA') 
	THEN
		UPDATE auth_d_applicazione set
		urlverifyLoginConditions = 'https://xxx/CCVerifyLoginConditionsService'
		where codice = 'DMACMPA';
	END IF;
END
$$;

DO
$$
BEGIN
	IF EXISTS (select * from auth_d_applicazione where codice = 'ROL') 
	THEN
		UPDATE auth_d_applicazione set
		urlverifyLoginConditions = 'https://xxx/CCVerifyLoginConditionsService'
		where codice = 'ROL';
	END IF;
END
$$;

DO
$$
BEGIN
	IF EXISTS (select * from auth_d_applicazione where codice = 'PUA') 
	THEN
		UPDATE auth_d_applicazione set
		urlverifyLoginConditions = 'https://xxx/CCVerifyLoginConditionsService'
		where codice = 'PUA';
	END IF;
END
$$;

--	INCR 12-06-2020
INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_100', 'Lista abilitazioni recuperata', NULL, NULL, now());

--	SEZIONE PUA (INIZIO)

ALTER TABLE auth_l_log_audit ALTER COLUMN id_messaggio DROP NOT NULL;

ALTER TABLE auth_l_log_audit add column col_cod_azienda text collate "default";

ALTER TABLE auth_l_log_audit add column codice_collocazione text collate "default";

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_050', 'E'' stato richiesto l''applicativo %s', null, null, NOW());

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_060', 'E'' stata attivata la funzionalita'' di Reportistica', null, null, NOW());

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_070', 'E'' stato selezionato il Ruolo %s', null, null, NOW());

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_080', 'E'' stata selezionata la collocazione %s', null, null, NOW());

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_090', 'E'' stato richiesto il Report %s, Da: %s A: %s per la collocazione %s', null, null, NOW());

--	CHANGE 30-06-2020	(INIZIO)
--	MODIFICATO VALORE tipo_servizio PER REP_OP_CONS E REP_REF_SCA
INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'REP_OP_CONS', 'ReportOperazioniConsensi', 'E', now());

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'REP_REF_SCA', 'ReportRefertiScaricati', 'E', now());

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GET_RUOLI_UTENTE', 'GetRuoliUtente', 'I', now());

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'GET_COLLOCAZIONI', 'GetCollocazioni', 'I', now());
--	CHANGE 30-06-2020	(FINE)

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'GET_AUTH2'), null, 'xxx', 'xxx', '2020-05-05 15:20:36.000', '2099-12-31 00:00:00.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'GET_RUOLI_UTENTE'), null, 'xxx', 'xxx', '2020-04-24 15:24:39.000', '2099-12-31 00:00:00.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'GET_ABI'), null, 'xxx', 'xxx', '2020-05-05 15:20:36.000', '2099-12-31 00:00:00.000', now(), pgp_sym_encrypt('userabi', 'mypass'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'VER_LOG_COND2'), null, 'xxx', 'xxx', '2019-01-01 00:00:00.000', '2099-12-31 23:59:59.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'GET_TOK_INFO2'), null, 'xxx', 'xxx', '2020-01-01 00:00:00.000', '2099-12-31 00:00:00.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'REP_OP_CONS'), (select id from auth_d_applicazione where codice = 'PUA'), 'xxx', 'xxx', '2020-06-09 10:55:50.144', '2099-12-31 00:00:00.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'REP_REF_SCA'), (select id from auth_d_applicazione where codice = 'PUA'), 'xxx', 'xxx', '2020-06-18 16:24:55.707', '2099-12-31 00:00:00.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

--	SEZIONE PUA (FINE)
