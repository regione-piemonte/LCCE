--ATTENZIONE !!!!!!!!!!!!!!!!!
--ATTENZIONE !!!!!!!!!!!!!!!!!
--ATTENZIONE !!!!!!!!!!!!!!!!!
-- sostituire la pwd di prod dove presente *** mettere pwd prod ***
-- SISTEMARE MAIL AURA

-- modifica per default su campo not null (usato per notificatore)

ALTER TABLE lcce.auth_t_preferenza ADD data_inizio_validita timestamp NULL DEFAULT now();
alter table auth_t_preferenza add column data_fine_validita timestamp;
update auth_t_preferenza set data_inizio_validita=data_inserimento;
alter table auth_t_preferenza alter column data_inizio_validita set not null;

alter table auth_t_utente add column token_push text;

--ALTER TABLE auth_t_preferenza ALTER COLUMN data_inizio_validita SET DEFAULT now();

-- richiesta daloiso
select * into auth_t_utente_bck_crypt from auth_t_utente;

ALTER TABLE auth_t_utente ADD indirizzo_mail_CRYPT text;
ALTER TABLE auth_t_utente ADD numero_telefono_CRYPT text;

UPDATE auth_t_utente SET indirizzo_mail_CRYPT = pgp_sym_encrypt(indirizzo_mail, '@@password di cifratura Db@@' ),   -- in test 'mypass'
						 numero_telefono_CRYPT = pgp_sym_encrypt(numero_telefono, '@@password di cifratura Db@@');  -- in test 'mypass'

/*						
ALTER TABLE auth_t_utente drop COLUMN numero_telefono;
ALTER TABLE auth_t_utente drop COLUMN indirizzo_mail;
*/
ALTER TABLE auth_t_utente RENAME COLUMN numero_telefono TO numero_telefono_bck;
ALTER TABLE auth_t_utente RENAME COLUMN indirizzo_mail TO indirizzo_mail_bck;


ALTER TABLE auth_t_utente RENAME COLUMN numero_telefono_CRYPT TO numero_telefono;
ALTER TABLE auth_t_utente RENAME COLUMN indirizzo_mail_CRYPT TO indirizzo_mail;

-- aggiunte su script exprivia
--SELECT id, chiave, valore, descrizione, data_inserimento
--FROM lcce.auth_t_configurazione
--order by id desc;

																 --------------------------------------------------------
INSERT INTO lcce.auth_t_configurazione                           -- SISTEMARE MAIL vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv  -----
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'EMAIL_SERV_AURA', 'sanbanchedati@csi.it', 'Indirizzo email servizio AURA per abilitazioni disabilitazioni SCEREV', now());


INSERT INTO lcce.auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'OGG_MAIL_ABI_SERV_AURA', 'Richiesta abilitazione di un utente per SCEREV', 'Oggetto email per servizio AURA per abilitazione SCEREV', now());


INSERT INTO lcce.auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'TESTO_MAIL_ABI_SERV_AURA', 'Richiesta abilitazione per utente [codice fiscale, nome, cognome], ruolo [descrizione ruolo], collocazione [col codice, descrizione coll], profilo scerev [codice - descrizione]', 'Testo di email per servizio AURA per abilitazione SCEREV', now());


INSERT INTO lcce.auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'OGG_MAIL_DISABI_SERV_AURA', 'Richiesta disabilitazione di un utente per SCEREV', 'Oggetto email per servizio AURA per disabilitazione SCEREV', now());


INSERT INTO lcce.auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'TESTO_MAIL_DISABI_SERV_AURA', 'Richiesta disabilitazione per utente [codice fiscale, nome, cognome], ruolo [descrizione ruolo], collocazione [col codice, descrizione coll], profilo scerev [codice - descrizione]', 'Testo di email per servizio AURA per disabilitazione SCEREV', now());



INSERT INTO lcce.auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'CONF_MAIL_CORPO_ABI_SCEREV', 'L''abilitazione su Scelta Revoca è in corso e l''utente riceverà una comunicazione non appena sarà completata', 'Testo aggiuntivo email utente per abilitazione SCEREV', now());



INSERT INTO lcce.auth_t_configurazione
(id, chiave, valore, descrizione, data_inserimento)
VALUES(nextval ('seq_auth_t_configurazione'), 'CONF_MAIL_CORPO_DISABI_SCEREV', 'La disabilitazione su Scelta Revoca è in corso e l''utente riceverà una comunicazione non appena sarà completata', 'Testo aggiuntivo email utente per disabilitazione SCEREV', now());



INSERT INTO lcce.auth_d_messaggi_utente
(id,codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('lcce.seq_auth_d_messaggi_utente'),'MSGCONF036', 'L''abilitazione per Scelta Revoca è in corso, l''utente riceverà una comunicazione non appena sarà completata', 'S', now());


INSERT INTO lcce.auth_d_messaggi_utente
(id,codice, descrizione, tipo_messaggio, data_inserimento)
VALUES(nextval('lcce.seq_auth_d_messaggi_utente'),'MSGCONF037', 'La disabilitazione per Scelta Revoca è in corso, l''utente riceverà una comunicazione non appena sarà completata', 'S', now());


DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.16','Release 1.0.16', 'Incrementale', 'dmaloginccedb-1.0.15-1.0.16.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,16,'16',NOW(),'dmaloginccedb-1.0.15-1.0.16.sql');
		END IF;
	END IF;
END
$$;
