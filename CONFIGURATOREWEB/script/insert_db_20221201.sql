INSERT INTO lcce.auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
SELECT nextval('auth_d_messaggi_utente_seq'), 'MSGCONF041', 'Funzionalità non presente per l''applicativo ', 'W', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_messaggi_utente where codice='MSGCONF041');

INSERT INTO lcce.auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
SELECT nextval('auth_d_messaggi_utente_seq'), 'MSGCONF040', 'Il campo Codice e'' obbligatorio ', 'W', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_messaggi_utente where codice='MSGCONF040');

INSERT INTO lcce.auth_d_messaggi_utente
(id, codice, descrizione, tipo_messaggio, data_inserimento)
SELECT nextval('auth_d_messaggi_utente_seq'), 'MSGCONF039', 'Il campo Descrizione e'' obbligatorio ', 'W',  now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_messaggi_utente where codice='MSGCONF039');

