--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --

-- Inserimento Oscurato in Applicazione --

alter table auth_d_applicazione add column oscurato varchar(1);

-- Insert Servizi --

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'FAR_ADE', 'GetFarmacieAderenti', 'I', now());

INSERT INTO auth_d_servizi
(id, codice, descrizione, tipo_servizio, data_inserimento)
VALUES(nextval('seq_auth_d_servizi'), 'VER_FAR', 'VerificaFarmacista', 'I', now());

-- Insert Credenziali Servizi --

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'FAR_ADE'), (select id from auth_d_applicazione where codice = 'PUA'), 'userfarabi', '', now(), null, now(), pgp_sym_encrypt('passfarabi','mypass'));

INSERT INTO auth_t_credenziali_servizi
(id, id_servizio, id_applicazione, username, password_bkp, data_inizio_validita, data_fine_validita, data_inserimento, "password")
VALUES(nextval('seq_auth_t_credenziali_servizi'), (select id from auth_d_servizi where codice = 'VER_FAR'), (select id from auth_d_applicazione where codice = 'PUA'), 'userverfar', '', now(), null, now(), pgp_sym_encrypt('passverfar','mypass'));

-- Inserimento AUTH_ER_630 in Catalogo --

INSERT INTO auth_d_catalogo_log
(id, codice, descrizione, descrizione_log, fonte, tipo_errore, data_inserimento)
VALUES(nextval('seq_auth_d_catalogo_log'), 'AUTH_ER_630', 'Farmacista o Farmacia non abilitati al servizio', 'Farmacista o Farmacia non abilitati al servizio', 'LCCE', 'Bloccante', now());


-- Update descrione errore AUTH_LOG_000 su catalogo_log -- 

UPDATE auth_d_catalogo_log
SET descrizione='Chiamata al Servizio: Nome servizio: %s', descrizione_log='Chiamata al Servizio: Nome servizio: %s' 
WHERE codice = 'AUTH_LOG_000';


CREATE SEQUENCE seq_version_control_change_number
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;


INSERT INTO version_control
(change_number, release_version, description, release_type, script_name, checksum, installed_by, installed_on, execution_time, success)
VALUES(nextval('seq_version_control_change_number'), '1.0.7', 'Release 1.0.7', 'Incrementale', 'dmaloginccedb-1.0.6-1.0.7.sql', NULL, 'RTI', NOW(), 0, true);

