--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --

-- confi
UPDATE auth_d_applicazione
SET redirect_url='redirect:https://xxx/tokenCRF.do?token='
WHERE codice='FARMABOP';

INSERT INTO version_control
(change_number, release_version, description, release_type, script_name, checksum, installed_by, installed_on, execution_time, success)
VALUES(nextval('seq_version_control_change_number'), '1.0.9', 'Release 1.0.9', 'Incrementale', 'dmaloginccedb-1.0.8-1.0.9.sql', NULL, 'RTI', NOW(), 0, true);

