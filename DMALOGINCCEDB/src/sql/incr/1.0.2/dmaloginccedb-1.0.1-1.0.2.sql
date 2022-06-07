--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --

--	SCRIPT INCREMENTALE CONFIGURAZIONE LOGINCCE
--	VERSIONE: 01A
--	SCHEMA DATI: LCCE
--	CREATE DATE: 28/01/2020

--	Criptazione del campo “password” in auth_t_credenziali_servizi
ALTER TABLE  auth_t_credenziali_servizi ADD COLUMN password_crypt text;
UPDATE auth_t_credenziali_servizi t1 SET password_crypt = pgp_sym_encrypt(password_bkp::text, 'xxx');  -- aggiornare chiave cifratura per ambiente
ALTER TABLE auth_t_credenziali_servizi RENAME password TO password_bkp;
ALTER TABLE auth_t_credenziali_servizi RENAME password_crypt TO password

