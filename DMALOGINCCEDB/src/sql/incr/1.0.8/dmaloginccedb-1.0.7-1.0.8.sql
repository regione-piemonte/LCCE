--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --


ALTER TABLE auth_l_messaggi ALTER COLUMN cf_richiedente TYPE varchar(50) USING cf_richiedente::varchar;
ALTER TABLE auth_l_messaggi ALTER COLUMN cf_assistito TYPE varchar(50) USING cf_assistito::varchar;



CREATE SEQUENCE seq_version_control_change_number
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;


INSERT INTO version_control
(change_number, release_version, description, release_type, script_name, checksum, installed_by, installed_on, execution_time, success)
VALUES(nextval('seq_version_control_change_number'), '1.0.8', 'Release 1.0.8', 'Incrementale', 'dmaloginccedb-1.0.7-1.0.8.sql', NULL, 'RTI', NOW(), 0, true);

