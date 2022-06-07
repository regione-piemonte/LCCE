--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --


--	SCRIPT INCREMENTALE CONFIGURAZIONE LOGINCCE
--	MEV FARMACIA DEI SERVIZI E PUA
--	VERSIONE: 01A
--	SCHEMA DATI: LCCE
--	CREATE DATE: 02/07/2020

DO
$$
BEGIN
	IF NOT EXISTS (	select * from auth_d_catalogo_log_audit where codice = 'AUTH_LOG_110') THEN
		INSERT INTO auth_d_catalogo_log_audit
		(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
		VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_110', 'E'' stato effettuato il login al PUA', NULL, NULL, now());
	ELSE
		UPDATE auth_d_catalogo_log_audit
		SET descrizione = 'E'' stato effettuato il login al PUA',
		descrizione_codice = NULL,
		tipo = NULL
		WHERE codice = 'AUTH_LOG_110';
	END IF;
END
$$;

DO
$$
BEGIN
	IF NOT EXISTS (	select * from auth_d_catalogo_log_audit where codice = 'AUTH_LOG_120') THEN
		INSERT INTO auth_d_catalogo_log_audit
		(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
		VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_120', 'E'' stato effettuato il logout dal PUA', NULL, NULL, now());
	ELSE
		UPDATE auth_d_catalogo_log_audit
		SET descrizione = 'E'' stato effettuato il logout dal PUA',
		descrizione_codice = NULL,
		tipo = NULL
		WHERE codice = 'AUTH_LOG_120';
	END IF;
END
$$;

DO
$$
BEGIN
	IF NOT EXISTS (	select * from auth_d_catalogo_log_audit where codice = 'AUTH_LOG_040') THEN
		INSERT INTO auth_d_catalogo_log_audit
		(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
		VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_040', 'Lista Collocazioni recuperata', NULL, NULL, now());
	ELSE
		UPDATE auth_d_catalogo_log_audit
		SET descrizione = 'Lista Collocazioni recuperata',
		descrizione_codice = NULL,
		tipo = NULL
		WHERE codice = 'AUTH_LOG_040';
	END IF;
END
$$;

DO
$$
BEGIN
	IF NOT EXISTS (	select * from auth_d_catalogo_log_audit where codice = 'AUTH_LOG_030') THEN
		INSERT INTO auth_d_catalogo_log_audit
		(id, codice, descrizione, descrizione_codice, tipo, data_inserimento)
		VALUES(nextval('seq_auth_d_catalogo_log_audit'), 'AUTH_LOG_030', 'Lista ruoli recuperata', NULL, NULL, now());
	ELSE
		UPDATE auth_d_catalogo_log_audit
		SET descrizione = 'Lista ruoli recuperata',
		descrizione_codice = NULL,
		tipo = NULL
		WHERE codice = 'AUTH_LOG_030';
	END IF;
END
$$;
