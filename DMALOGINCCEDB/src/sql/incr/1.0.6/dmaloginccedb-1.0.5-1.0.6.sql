--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --


-- SICUREZZA --

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, password)
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'NOTIFICA_CITTADINO'), (select id from auth_d_applicazione where codice = 'PUA'), 'xxx', '', '2019-01-01 00:00:00.000', '2099-12-31 23:59:59.000', now(), pgp_sym_encrypt('xxx', 'xxx'));

-- SICUREZZA --

alter table auth_d_applicazione add column descrizione_webapp varchar(150);
alter table auth_d_applicazione add column path_immagine varchar(250);
alter table auth_d_applicazione add column redirect_url varchar(250);
alter table auth_d_applicazione add column bottone varchar(1);

update auth_d_applicazione set descrizione_webapp = 'Fascicolo Sanitario Elettronico', path_immagine = 'im/icone/ruoli/ico_fascicolo_sanitario.svg', redirect_url = 'redirect:https://xxxx/tokenFSE.do?token=' where codice = 'DMACMPA';
update auth_d_applicazione set descrizione_webapp = 'Ritiro referti online', path_immagine = 'im/icone/ruoli/ico_ritiro_referti.svg', redirect_url = 'redirect:https://xxxx/tokenROL.do?token=' where codice = 'ROL';
update auth_d_applicazione set descrizione_webapp = 'Reportistica', redirect_url = 'redirect:/reportistica' where codice = 'PUA';
update auth_d_applicazione set bottone = 'S' where codice = 'DMACMPA';
update auth_d_applicazione set bottone = 'S' where codice = 'ROL';

INSERT INTO auth_d_applicazione
(id, codice, descrizione, pin_richiesto, urlverifyloginconditions, data_inserimento, descrizione_webapp, path_immagine, redirect_url, bottone)
VALUES(nextval('seq_auth_d_applicazione'), 'NTFCIT', 'Notifica Cittadino', 'N', NULL, now(), 'Invio comunicazioni al cittadino', 'im/icone/ruoli/ico_fascicolo_sanitario.svg', 'redirect:/notifica', 'S');

INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(NEXTVAL('auth_t_funzionalita_fnz_id_seq'), 'NTFCIT', 'notifica cittadino ', (SELECT fnz_tipo_id FROM auth_d_funzionalita_tipo WHERE fnz_tipo_codice='FUNZ'), (SELECT id FROM auth_d_applicazione where codice = 'NTFCIT'), '2020-01-01 00:00:00.000', NULL, now(), now(), NULL, 'CSI');

INSERT INTO auth_r_funzionalita_tree
(fnztree_id, fnz_id, fnztree_id_parent, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(NEXTVAL('auth_r_funzionalita_tree_fnztree_id_seq'), (SELECT fnz_id from auth_t_funzionalita where fnz_codice = 'NTFCIT'), NULL, '2020-01-01 00:00:00.000', NULL, now(), now(), NULL, 'CSI');

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_LOG_002', 'Comunicazione presa in carico', 'Comunicazione presa in carico', '', '', NOW());

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'NOTIFICA_CITTADINO', 'NotificaCittASR', 'I', NOW());

INSERT INTO auth_d_catalogo_log_audit
(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_130', 'E'' stato effettuato l’invio della notifica al cittadino', null, null, NOW());

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_628', 'Il campo %s deve essere valorizzato', 'Il campo %s deve essere valorizzato', 'LCCE', 'Bloccante', NOW());

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_629', 'Il campo %s non è valido', 'Il campo %s non è valido', 'LCCE', 'Bloccante', NOW());

CREATE TABLE version_control (
	change_number serial NOT NULL,
	release_version varchar(11) NULL,
	description varchar(200) NOT NULL,
	release_type varchar(20) NOT NULL,
	script_name varchar(1000) NOT NULL,
	checksum uuid NULL,
	installed_by varchar(100) NOT NULL,
	installed_on timestamp NOT NULL DEFAULT now(),
	execution_time int4 NOT NULL,
	success bool NOT NULL,
	CONSTRAINT version_control_pkey PRIMARY KEY (change_number)
);


CREATE SEQUENCE seq_version_control_change_number
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;


INSERT INTO version_control
(change_number, release_version, description, release_type, script_name, checksum, installed_by, installed_on, execution_time, success)
VALUES(nextval('seq_version_control_change_number'), '1.0.6', 'Release 1.0.6', 'Incrementale', 'dmaloginccedb-1.0.5-1.0.6.sql', NULL, 'RTI', NOW(), 0, true);

