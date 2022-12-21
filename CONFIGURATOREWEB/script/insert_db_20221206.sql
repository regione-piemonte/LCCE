-- GIA ESEGUITO in data 7/12/2022

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
SELECT nextval('seq_auth_l_csi_log_audit'), 'CU030', 'Ricerca Applicazione', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='CU030');

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
SELECT nextval('seq_auth_l_csi_log_audit'), 'CU031', 'Inserimento Applicazione', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='CU031');

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
SELECT nextval('seq_auth_l_csi_log_audit'), 'CU032', 'Modifica Applicazione', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='CU032');

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
SELECT nextval('seq_auth_l_csi_log_audit'), 'CU033', 'Ricerca Funzionalita''', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='CU033');

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
SELECT nextval('seq_auth_l_csi_log_audit'), 'CU034', 'Inserimento Funzionalita''', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='CU034');

INSERT INTO lcce.auth_d_catalogo_log_audit_conf
(id, key_operazione, ogg_operazione, data_inserimento)
SELECT nextval('seq_auth_l_csi_log_audit'), 'CU035', 'Modifica Funzionalita''', now()
WHERE NOT EXISTS(SELECT 1 FROM auth_d_catalogo_log_audit_conf where key_operazione='CU035');

