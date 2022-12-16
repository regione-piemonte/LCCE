update auth_r_ruolo_utente set cf_operatore='-' where cf_operatore is  null;
ALTER TABLE lcce.auth_r_ruolo_utente ALTER COLUMN cf_operatore SET NOT NULL;
update auth_r_abilitazione set cf_operatore='-' where cf_operatore is  null;
ALTER TABLE lcce.auth_r_abilitazione ALTER COLUMN cf_operatore SET NOT NULL;


INSERT INTO lcce.auth_r_ruolo_utente
(id, id_utente, id_ruolo, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, cf_operatore, flag_configuratore, col_fonte_id)
VALUES(9999999999998, 8, 11, '2020-01-14 00:00:00.000', NULL, '2020-04-22 12:44:29.871', '2020-04-22 12:44:29.871', NULL, NULL, NULL);
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, fnztree_id, utecol_id, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(44444444411414, 9188, NULL, 'cdb394a3-965f-42cf-9379-88ec887335bdd1', '2020-09-01 16:13:17.721', NULL, '2020-09-01 16:13:17.721', 1, 9916, '2020-09-01 16:13:17.721', NULL, null);



-- trigger


select 'ALTER table '||table_name||' ALTER COLUMN '||column_name||' DROP NOT NULL;' ,*
from information_schema.columns c where is_nullable='NO' and table_schema='lcce' and 
lower(table_name) like lower('%auth_s%');

select * from PG_PROC where proname like 'f%auth/_t/_%' escape  '/' or proname like 'f%auth/_r/_%' escape  '/' order by 1;

select distinct 'select count(*) , '''||table_name||''' from '||table_name||' union' from information_schema.columns where table_name like 'auth_s%' order by 1

select count(*) , 'auth_s_abilitazione' from auth_s_abilitazione union
select count(*) , 'auth_s_collocazione' from auth_s_collocazione union
select count(*) , 'auth_s_preferenza' from auth_s_preferenza union
select count(*) , 'auth_s_preferenza_salvata' from auth_s_preferenza_salvata union
select count(*) , 'auth_s_ruolo_utente' from auth_s_ruolo_utente union
select count(*) , 'auth_s_utente_collocazione' from auth_s_utente_collocazione union
select count(*) , 'auth_s_utente' from auth_s_utente order by 1;


select * from auth_s_preferenza_salvata order by 1 desc;







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

-- 4
-- prove
update auth_t_preferenza_salvata set data_cancellazione=now();
select data_cancellazione,* from auth_t_preferenza_salvata;
select data_cancellazione,* from auth_s_preferenza_salvata;
delete from auth_t_preferenza_salvata;
select * from auth_t_preferenza_salvata;
select * from auth_s_preferenza_salvata order by 1 desc;


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
create table auth_S_preferenza as
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

-- 4
-- prove
update auth_t_preferenza set data_cancellazione=now();
select data_cancellazione,* from auth_t_preferenza;
select data_cancellazione,* from auth_s_preferenza;
-- 
delete from auth_t_preferenza_SALVATA;
delete from auth_t_preferenza;
select * from auth_t_preferenza;
select * from auth_s_preferenza order by 1 desc;

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
create table auth_S_utente as
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

-- 4
-- prove
update auth_t_utente set data_fine_validita =now();
select data_fine_validita,* from auth_t_utente;
select data_fine_validita,* from auth_s_utente;
-- 
INSERT INTO lcce.auth_t_utente
(id, nome, cognome, codice_fiscale, data_inserimento, data_aggiornamento, id_aura, data_nascita, comune_nascita, sesso, id_contratto, flag_configuratore, id_operatore, data_inizio_validita, data_fine_validita, provincia, ultimo_accesso_pua, email_preferenze, telefono_preferenze, token_push, indirizzo_mail, numero_telefono)
VALUES(66614785, 'Nicola', 'Gaudenzi', 'GDNNCL71D20L219x', '2022-04-07 14:43:31.281', '2022-04-07 14:46:23.310', NULL, '1971-04-20 00:00:00.000', 'TORINO', 'M', 1, 'S', 7, '2022-04-07 00:00:00.000', NULL, 'torino', NULL, NULL, NULL, NULL, '\xc30d04070302e352644692ca459773d24701f95c96dba629900821890d373866bd339faf059af58ad1071abf8c886d8e5a20026d49809142125ccb414d2c8231375fa9479d5f462486be6d44030fcbf8eabc387d7e746b6a', '\xc30d0407030245726390952cd3e067d23b0198bc3e503a269d1a5b32308199d43de34ee12c01bdbbe23eff7fa2c069422f8dbcec21ffa77a43d6d277dd1f790134dffb7ef1e0256b171101d1');
delete from auth_t_utente where ID=66614785;
select * from auth_t_utente where ID=66614785;
select * from auth_s_utente where ID=66614785 order by 1 desc;


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

-- 4
-- prove
update auth_r_ruolo_utente set data_fine_validita =now();
select data_fine_validita,* from auth_r_ruolo_utente;
select data_fine_validita,* from auth_s_ruolo_utente;
-- 
delete from auth_r_ruolo_utente where not exists (select * from auth_r_abilitazione where auth_r_abilitazione.id_ruolo_utente=auth_r_ruolo_utente.id) ;
select * from auth_r_ruolo_utente where not exists (select * from auth_r_abilitazione where auth_r_abilitazione.id_ruolo_utente=auth_r_ruolo_utente.id) ;
select * from auth_s_ruolo_utente order by 1 desc;


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

-- 4
-- prove
update auth_r_utente_collocazione set data_fine_validita =now();
select data_fine_validita,* from auth_r_utente_collocazione;
select data_fine_validita,* from auth_s_utente_collocazione;
-- 
delete from auth_r_utente_collocazione where not exists (select * from auth_r_abilitazione where auth_r_abilitazione.utecol_id=auth_r_utente_collocazione.utecol_id ) ;
select * from auth_r_utente_collocazione where not exists (select * from auth_r_abilitazione where auth_r_abilitazione.utecol_id=auth_r_utente_collocazione.utecol_id ) ;
select * from auth_s_utente_collocazione order by 1 desc;


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

-- 4
-- prove
update auth_r_abilitazione set data_fine_validita =now();
select data_fine_validita,* from auth_r_abilitazione;
select data_fine_validita,* from auth_s_abilitazione;
-- 
select * from auth_r_abilitazione 
where not exists (select * from auth_l_messaggi where auth_l_messaggi.id_abilitazione =auth_r_abilitazione.id  ) limit 1;
delete from auth_r_abilitazione where id=11414;
select * from auth_r_abilitazione  where id=11414;
select * from auth_s_abilitazione  where id=11414;



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

-- 4
-- prove
update auth_t_collocazione set data_fine_validita =now();
select data_fine_validita,* from auth_t_collocazione;
select data_fine_validita,* from auth_s_collocazione;
-- 
select * from auth_t_collocazione 
where not exists (select * from auth_r_utente_collocazione where auth_r_utente_collocazione.col_id  =auth_t_collocazione.col_id  ) limit 1;
delete from auth_t_collocazione where col_id=36318;
select * from auth_t_collocazione  where col_id=36318;
select * from auth_s_collocazione  where col_id=36318;
