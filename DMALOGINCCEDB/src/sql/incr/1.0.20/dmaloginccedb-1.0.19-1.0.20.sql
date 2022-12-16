INSERT INTO auth_d_applicazione (id,codice,descrizione,pin_richiesto,
data_inserimento,descrizione_webapp,path_immagine,redirect_url,bottone,flag_configuratore) VALUES 
(nextval ('seq_auth_d_applicazione'),'ESENRED','Il servizio permette di inserire e/o gestire la domanda per l''esenzione dal ticket sanitario per motivi di reddito.','N',
now(),'Gestione autocertificazione esenzioni da reddito',
'im/icone/ruoli/.svg',
'redirect:https://esenzione-pa-sistemapiemonte.isan.csi.it/esenzioniweb/?token=',
'S','S');

INSERT INTO auth_d_applicazione (id,codice,descrizione,pin_richiesto,
data_inserimento,descrizione_webapp,path_immagine,redirect_url,bottone,flag_configuratore) VALUES 
(nextval ('seq_auth_d_applicazione'),'SCEREV','Scelta revoca','N',
now(),'Domande di scelta revoca medico ',
'im/icone/ruoli/.svg',
'redirect:https://scerev.ruparpiemonte.it/scerevweb/?token=',
'S','S');


INSERT INTO auth_d_applicazione (id,codice,descrizione,pin_richiesto,
data_inserimento,descrizione_webapp,path_immagine,redirect_url,bottone,flag_configuratore) VALUES 
(nextval ('seq_auth_d_applicazione'),'ESENZIONE','Servizio on line per la gestione ed il rilascio delle esenzioni per patologia.','N',
now(),'Esenzione per Patologia',
'im/icone/ruoli/ico_fascicolo_sanitario.svg',
'redirect:https://esenzione-pa-sistemapiemonte.isan.csi.it/esenzioniweb/?token=',
'S','S');


alter table auth_d_applicazione add column invio_mail_aura boolean null;
update auth_d_applicazione set invio_mail_aura=true where codice='SCEREV';

update auth_d_messaggi_utente 
	set descrizione='L''abilitazione per @APP@ è in corso, l''utente riceverà una comunicazione non appena sarà completata'
	where codice='MSGCONF036';
update auth_d_messaggi_utente 
	set descrizione='La disabilitazione per @APP@ è in corso, l''utente riceverà una comunicazione non appena sarà completata'
	where codice='MSGCONF037';
INSERT INTO auth_d_messaggi_utente
(codice, descrizione, tipo_messaggio, data_inserimento)
VALUES('MSGCONF038', 'Non e'' stato possibile inviare la mail verso AURA per @AZIONE@ @APP@', 'W', now());

INSERT INTO lcce.auth_t_configurazione (id,chiave,valore,descrizione,data_inserimento) VALUES (nextval('seq_auth_t_configurazione'),'CONF_MAIL_CORPO_ABI','L''abilitazione su @APP@ è in corso e l''utente riceverà una comunicazione non appena sarà completata','Testo aggiuntivo email utente per abilitazione','2022-08-03 10:20:17.704');
INSERT INTO lcce.auth_t_configurazione (id,chiave,valore,descrizione,data_inserimento) VALUES (nextval('seq_auth_t_configurazione'),'CONF_MAIL_CORPO_DISABI','La disabilitazione su @APP@ è in corso e l''utente riceverà una comunicazione non appena sarà completata','Testo aggiuntivo email utente per disabilitazione','2022-08-03 10:20:21.044');


/*
UPDATE auth_t_configurazione
    SET valore='L''abilitazione su @APP@ è in corso e l''utente riceverà una comunicazione non appena sarà completata',descrizione='Testo aggiuntivo email utente per abilitazione',chiave='CONF_MAIL_CORPO_ABI'
    WHERE id=52;
UPDATE auth_t_configurazione
    SET valore='La disabilitazione su @APP@ è in corso e l''utente riceverà una comunicazione non appena sarà completata',descrizione='Testo aggiuntivo email utente per disabilitazione',chiave='CONF_MAIL_CORPO_DISABI'
    WHERE id=53;
*/

delete from auth_t_configurazione 
	where chiave='OGG_MAIL_ABI_SERV_AURA';
delete from auth_t_configurazione 
	where chiave='TESTO_MAIL_ABI_SERV_AURA';
delete from auth_t_configurazione 
	where chiave='OGG_MAIL_DISABI_SERV_AURA';
delete from auth_t_configurazione 
	where chiave='TESTO_MAIL_DISABI_SERV_AURA';

INSERT INTO auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval('seq_auth_t_configurazione'), 'OGG_MAIL_SERV_AURA', 'Richiesta @AZIONE@ utente', 'Oggetto email per servizio AURA', now());
INSERT INTO auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval('seq_auth_t_configurazione'), 'TESTO_MAIL_SERV_AURA', 'Richiesta @AZIONE@ per utente @CODICE_FISCALE@, @NOME@, @COGNOME@, @EMAIL,TELEFONO@:', 'Testo email per servizio AURA', now());
INSERT INTO auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval('seq_auth_t_configurazione'), 'TESTO_ELENCO_SERV_AURA', 'ruolo: @COD_RUOLO@ - @DESC_RUOLO@, collocazione: @COL_CODICE@ - @COL_DESC@, azienda: @COD_AZIENDA@ - @DESC_AZIENDA@, applicazione: @APP@, profilo: @COD_PROF@ - @DESC_PROF@, data fine validità: @DATA_FINE_VAL@', 'Testo elenco abilitazioni/disabilitazioni per servizio AURA', now());

--dgrosso
INSERT INTO auth_d_batch_tipo
(batch_tipo_codice, batch_tipo_descrizione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
select 'CANC_AMM', 'ALLINEAMENTO OPESSAN', now(), null, now(), now(), null, 'CSI'
where not exists (select 1 from auth_d_batch_tipo where batch_tipo_codice='CANC_AMM')
;

-- Gaudenzi
ALTER TABLE lcce.auth_d_applicazione ADD flag_api_blocco_modifica varchar(1) NULL;

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.20','Release 1.0.20', 'Incrementale', 'dmaloginccedb-1.0.19-1.0.20.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,20,'20',NOW(),'dmaloginccedb-1.0.19-1.0.20.sql');
		END IF;
	END IF;
END
$$;
