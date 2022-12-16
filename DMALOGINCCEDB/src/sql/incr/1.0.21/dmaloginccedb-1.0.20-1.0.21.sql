ALTER TABLE lcce.auth_t_utente ADD email_preferenze text NULL;

ALTER TABLE lcce.auth_t_utente ADD telefono_preferenze text NULL;

ALTER TABLE lcce.auth_t_utente ADD token_push text NULL;



-- Trattamento dati Vezzetti
---**  File: 01 - 2022 08 02_td.sql  -- LANCIARE A MANO
/*
Aggiunta nuovo campo sulla tabella Collocazione
Deve essere aggiunto un flag sulla tabella T_COLLOCAZIONE.FONTE_ESTERNA per etichettare le collocazioni che saranno trasferite sul Configuratore con trattamento dati, 
ma che non provengono dalla fonte esterna OPESSAN/ARPE (Es.Carceri o case di cura private). 
La web app NON deve tenere conto di questo flag, quindi lâ€™elenco delle collocazioni deve comprendere anche questi record.
Per cui possono essere associate anche queste collocazioni a tutti gli utenti presenti sul configuratore, 
inseriti dal Configuratore stesso o derivanti da batch (esempio: Ã¨ possibile associare una casa di cura privata che ha flag FONTE_ESTERNA = SI 
ad un utente proveniente dal batch di OPESSAN).
*/
alter table auth_t_collocazione add column FONTE_ESTERNA text;
--update auth_t_collocazione set FONTE_ESTERNA='SI';

ALTER TABLE lcce.auth_d_sistemi_richiedenti ADD cod_azienda text NULL;


drop index idx_auth_r_funzionalita_tipologia_dato_permesso_01;

CREATE UNIQUE INDEX idx_auth_r_funzionalita_tipologia_dato_permesso_01 ON lcce.auth_r_funzionalita_tipologia_dato_permesso
USING btree (fnz_id, tipologia_dato_id, permesso_id,data_inizio_validita) WHERE (data_cancellazione IS NULL)




-- 10

CREATE TABLE auth_t_batch_chiusura_utenti_senza_accesso (
	batch_id bigserial NOT NULL,
	batch_codice text NOT NULL,
	data_inizio_attivita timestamp NOT NULL,
	batch_stato_id bigint not null,
	data_fine_attivita timestamp NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	data_aggiornamento timestamp NOT NULL DEFAULT now(),
	data_cancellazione timestamp NULL,
	CONSTRAINT auth_d_batch_stato_auth_t_batch_collocazione FOREIGN KEY (batch_stato_id) REFERENCES lcce.auth_d_batch_stato(batch_stato_id),
	CONSTRAINT pk_auth_t_batch_chiusura_utenti_senza_accesso PRIMARY KEY (batch_id)
);
CREATE UNIQUE INDEX idx_auth_t_batch_chiusura_utenti_no_acce_1 ON lcce.auth_t_batch_chiusura_utenti_senza_accesso USING btree (batch_codice, data_inizio_attivita) WHERE (data_cancellazione IS NULL);
alter table auth_t_batch_chiusura_utenti_senza_accesso add column numero_utenti_trattati bigint; 

CREATE TABLE auth_t_batch_chiusura_utenti_senza_accesso_attivita (
	batchat_id bigserial NOT NULL,
	batchat_info text NOT NULL,
	batch_id int8 NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	data_aggiornamento timestamp NOT NULL DEFAULT now(),
	data_cancellazione timestamp NULL,
	CONSTRAINT pk_auth_t_batch_chiusura_utenti_senza_accesso_attivita PRIMARY KEY (batchat_id),
	CONSTRAINT auth_t_batch_collocazione_auth_t_batch_collocazione_attivita FOREIGN KEY (batch_id) REFERENCES lcce.auth_t_batch_chiusura_utenti_senza_accesso(batch_id)
);
CREATE INDEX idx_auth_t_batch_chiusura_utenti_no_acce__att1 ON lcce.auth_t_batch_chiusura_utenti_senza_accesso_attivita USING btree (batchat_info) WHERE (data_cancellazione IS NULL);



-- A
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_t_preferenza_salvata
-- auth_t_preferenza_salvata
-- auth_t_preferenza_salvata
-- auth_t_preferenza_salvata
-- auth_t_preferenza_salvata
-- drop table auth_s_preferenza_salvata;
-- DROP TRIGGER trg_fnc_trg_bud_auth_t_preferenza_salvata ON lcce.auth_t_preferenza_salvata;
do $$
begin
-- 1
create table auth_s_preferenza_salvata as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_t_preferenza_salvata limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_t_preferenza_salvata()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_s_preferenza_salvata select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_t_preferenza_salvata before
delete
    or
update
    on
    auth_t_preferenza_salvata for each row execute procedure fnc_trg_bud_auth_t_preferenza_salvata();
end; $$   


-- B
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_t_preferenza
-- auth_t_preferenza
-- auth_t_preferenza
-- auth_t_preferenza
-- auth_t_preferenza
-- DROP TABLE auth_S_preferenza
-- DROP TRIGGER trg_fnc_trg_bud_auth_t_preferenza ON lcce.auth_t_preferenza;
-- 1
do $$
begin
-- 1
create table auth_s_preferenza as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_t_preferenza limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_t_preferenza()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_S_preferenza select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_t_preferenza before
delete
    or
update
    on
    auth_t_preferenza for each row execute procedure fnc_trg_bud_auth_t_preferenza();
end; $$ 


-- C
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_t_utente
-- auth_t_utente
-- auth_t_utente
-- auth_t_utente
-- auth_t_utente
-- 1
do $$
begin
-- 1
create table auth_s_utente as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_t_utente limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_t_utente()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_S_utente select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_t_utente before
delete
    or
update
    on
    auth_t_utente for each row execute procedure fnc_trg_bud_auth_t_utente();
end; $$ 



-- D
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_r_ruolo_utente
-- auth_r_ruolo_utente
-- auth_r_ruolo_utente
-- auth_r_ruolo_utente
do $$
begin
-- 1
create table auth_s_ruolo_utente as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_r_ruolo_utente limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_r_ruolo_utente()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_s_ruolo_utente select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_r_ruolo_utente before
delete
    or
update
    on
    auth_r_ruolo_utente for each row execute procedure fnc_trg_bud_auth_r_ruolo_utente();
end; $$ 


-- E
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_r_utente_collocazione
-- auth_r_utente_collocazione
-- auth_r_utente_collocazione
-- auth_r_utente_collocazione
-- auth_r_utente_collocazione
-- auth_r_utente_collocazione
-- auth_r_utente_collocazione
do $$
begin
-- 1
create table auth_s_utente_collocazione as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_r_utente_collocazione limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_r_utente_collocazione()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_s_utente_collocazione select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_r_utente_collocazione before
delete
    or
update
    on
    auth_r_utente_collocazione for each row execute procedure fnc_trg_bud_auth_r_utente_collocazione();
end; $$ 


-- F
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_r_abilitazione
-- auth_r_abilitazione
-- auth_r_abilitazione
-- auth_r_abilitazione
-- auth_r_abilitazione
do $$
begin
-- 1
create table auth_s_abilitazione as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_r_abilitazione limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_r_abilitazione()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_s_abilitazione select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_r_abilitazione before
delete
    or
update
    on
    auth_r_abilitazione for each row execute procedure fnc_trg_bud_auth_r_abilitazione();
end; $$ 


-- G
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- ######################################################################################################################################################
-- auth_t_collocazione
-- auth_t_collocazione
-- auth_t_collocazione
-- auth_t_collocazione
-- auth_t_collocazione
do $$
begin
-- 1
create table auth_s_collocazione as
select clock_timestamp() data_storicizzazione,null::text TG_OP,null::text utente_op,* from  auth_t_collocazione limit 0;
-- 
-- 2
CREATE OR REPLACE FUNCTION fnc_trg_bud_auth_t_collocazione()
 RETURNS trigger
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare
begin
	insert into auth_s_collocazione select clock_timestamp(),TG_OP,user, OLD.*;
	if TG_OP='UPDATE' THEN
    	return NEW;  
    else
    	return OLD;
    end if;
end;
$function$
;
-- 3
-- 
create trigger trg_fnc_trg_bud_auth_t_collocazione before
delete
    or
update
    on
    auth_t_collocazione for each row execute procedure fnc_trg_bud_auth_t_collocazione();
end; $$ 


---** FINE 
update auth_r_ruolo_utente set cf_operatore='-' where cf_operatore is null;
ALTER TABLE lcce.auth_r_ruolo_utente ALTER COLUMN cf_operatore SET NOT NULL;
update auth_r_abilitazione set cf_operatore='-' where cf_operatore is null;
ALTER TABLE lcce.auth_r_abilitazione ALTER COLUMN cf_operatore SET NOT NULL;


-- VEZZ 04/10/2022
ALTER TABLE lcce.auth_t_collocazione ADD cod_asr_terr text NULL;
ALTER TABLE lcce.auth_s_collocazione ADD cod_asr_terr text NULL;



update auth_t_collocazione
set cod_asr_terr= col_cod_azienda
where coalesce(col_fonte_id,-1) <> 2;
--
update auth_t_collocazione
set cod_asr_terr= (select auth_w_farmacie.cod_azienda  from auth_w_farmacie where  auth_w_farmacie.cod_farmacia=auth_t_collocazione.col_cod_azienda)
where col_fonte_id = 2;

DO
$$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'release_version') 
	THEN
		INSERT INTO version_control
		(release_version, description, release_type, script_name, installed_by, execution_time, success) 
		VALUES
		('1.0.21','Release 1.0.21', 'Incrementale', 'dmaloginccedb-1.0.20-1.0.21.sql','CSI', 0, true);
	ELSE 
		IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'version_control' and COLUMN_NAME = 'major_version')
		THEN 
			INSERT INTO version_control
			(major_version, minor_version, patch_version, string_version, ts_installation, note) 
			VALUES
			(1,0,20,'20',NOW(),'dmaloginccedb-1.0.20-1.0.21.sql');
		END IF;
	END IF;
END
$$;
