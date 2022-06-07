--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --

--	SCRIPT INCREMENTALE CONFIGURAZIONE LOGINCCE
--	VERSIONE: 01A
--	SCHEMA DATI: LCCE
--	CREATE DATE: 27/03/2020

--	JIRA LCCE-10
ALTER TABLE auth_xml_servizi_richiamati RENAME TO auth_l_xml_servizi_richiamati;
ALTER SEQUENCE seq_auth_xml_servizi_richiamati RENAME TO seq_auth_l_xml_servizi_richiamati;

SELECT setval('seq_auth_l_xml_servizi_richiamati', COALESCE((SELECT MAX(id)+1 FROM auth_l_xml_servizi_richiamati), 1), false);

--	JIRA LCCE-9
SELECT setval('seq_auth_d_catalogo_log', COALESCE((SELECT MAX(id)+1 FROM auth_d_catalogo_log), 1), false);

insert into auth_d_catalogo_log 
(id, codice, descrizione, descrizione_log, fonte,tipo_errore, data_inserimento)
values
(nextval('seq_auth_d_catalogo_log'),'AUTH_ER_EXT','Errore durante la chiamata al servizio esterno','Errore durante la chiamata al servizio esterno',null,'Bloccante',now());
