-- GIA ESEGUITO in data 7/12/2022
INSERT INTO lcce.auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
SELECT nextval('auth_d_messaggi_utente_seq'), 'MASS_ERR_004', 'Nessuna funzionalita'' di secondo livello presente', 'E', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='MASS_ERR_004');
