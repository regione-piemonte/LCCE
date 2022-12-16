-- 1
/*
Aggiornamento della tabella utenti con la valorizzazione dei campi (nome, cognome, codice fiscale, data di nascita, comune di nascita, provincia di nascita e sesso). 
*/
update auth_t_utente set
indirizzo_mail =
UPPER((select MIN( o.email) from auth_w_opessan_operatori o where o.codice_fiscale=auth_t_utente.codice_fiscale and o.email is not null)),
provincia =
UPPER((select distinct o.provincia_nascita from auth_w_opessan_operatori o where o.codice_fiscale=auth_t_utente.codice_fiscale and o.provincia_nascita is not null)),
data_nascita =
(select distinct to_timestamp(o.data_nascita,'ddmmyyyy') from auth_w_opessan_operatori o where o.codice_fiscale=auth_t_utente.codice_fiscale and o.data_nascita is not null),
comune_nascita =
UPPER((select distinct o.comune_nascita from auth_w_opessan_operatori o where o.codice_fiscale=auth_t_utente.codice_fiscale and o.comune_nascita is not null)),
sesso =
UPPER((select distinct o.sesso from auth_w_opessan_operatori o where o.codice_fiscale=auth_t_utente.codice_fiscale and o.sesso is not null))
where
indirizzo_mail is null and
provincia is null and
data_nascita is null and
comune_nascita is null and
sesso is null and
exists (select * from auth_w_opessan_operatori where auth_w_opessan_operatori.codice_fiscale=auth_t_utente.codice_fiscale);

-- 2
/*
Aggiornamento delle fonti dati sulle relazioni utente/ruolo e utente/collocazione.
A tal fine Ã¨ necessario per ogni codice fiscale presente in t_utente verificare se c'Ã¨ il corrispettivo su opessan. 
In caso affermativo occorre valorizzare il campo id_fonte_cod sulle tabelle, r_utente_ruolo e su r_utente_collocazione  auth_w_collocazione_utenti 
*/

select * from auth_r_ruolo_utente where id_utente in (
select id from auth_t_utente 
where auth_t_utente.codice_fiscale in (select codice_fiscale from auth_w_collocazione_utenti));

select * from auth_r_utente_collocazione where auth_r_utente_collocazione.ute_id  in (
select id from auth_t_utente 
where auth_t_utente.codice_fiscale in (select codice_fiscale from auth_w_collocazione_utenti));

-- 3
/*
Nella tabella con le collocazioni occorre:
o)   Bk
i)   Valorizzare il campo flag_azienda=S per le aziende
ii)  Valorizzare il campo flag_azienda=S per  le farmacie
iii) Modificare i record con â€˜-â€˜ per registrare nel campo col_codice il codice azienda
iv)  Modificare i record delle farmacie valorizzando il campo col_cod_azienda con il codice regionale della farmacia
v)   Modificare i record delle farmacie valorizzando il campo col_desc_azienda con la descrizione della farmacia (col_descrizione fino alla prima @)
*/
-- o)
create table zzz_auth_T_collocazioni_202209xx as select * from auth_t_collocazione;
select * from zzz_auth_T_collocazioni_202209xx;
-- i)
UPDATE lcce.auth_t_collocazione SET  flag_azienda='S' WHERE col_codice = '-';
-- ii)
UPDATE lcce.auth_t_collocazione SET  flag_azienda='S' where col_tipo_id = '2';
-- iii)
update auth_t_collocazione set col_codice=col_cod_azienda where col_codice ='-';
-- iv)
update auth_t_collocazione set col_cod_azienda=substr(col_codice,instr(col_codice,'@')+1) where col_tipo_id =2 and coalesce (flag_azienda,'.')='S';
-- v)
update auth_t_collocazione set col_desc_azienda=SUBSTRING(col_descrizione,1,INSTR(col_descrizione,'@')-1) where col_tipo_id =2; 


-- 4
/*Nella tabella D_RUOLO, impostare a  N U L L  il FLAG_CONFIGURATORE per il ruolo OPCSI, tutti gli altri devono avere il flag impostato a S.*/
select flag_configuratore,count(*) from auth_d_ruolo group by flag_configuratore; 
UPDATE lcce.auth_d_ruolo SET flag_configuratore=null WHERE id=30 and CODICE='OPCSI';

-- 5
/*Nella tabella auth_d_collocazione_tipo modificare codice e descrizione per col_tipo_id=1 impostando col_tipo_codice=AZI (lasciare AMM) col_tipo_descrizione=â€�AZIENDA SANITARIAâ€�*/
select * from auth_d_collocazione_tipo;
UPDATE lcce.auth_d_collocazione_tipo SET col_tipo_descrizione='AZIENDA SANITARIA' WHERE col_tipo_id=1;

-- 6
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
update auth_t_collocazione set FONTE_ESTERNA='SI';

select cf_operatore,COUNT(*) from auth_t_collocazione where  data_cancellazione is null
group by cf_operatore;


-- 7
/*
verificare l'alberatura profili/ funzionalitÃ  creando il record di tipo 3 (applicazione) 
--> tutte le funzionalitÃ  (tipo 2) devono essere legate anche al record di tipo 3
*/
-- QRY DI VERIFICA
--comment on view auth_v_tree is 'TREE con descrizioni (creata da Vez)';
-- 1
select * from auth_t_funzionalita where applicazione_id = 10 and fnz_tipo_id =3;
select * from auth_r_funzionalita_tree where fnz_id = 407;
--
select *  from auth_r_funzionalita_tree where fnz_id =
(select fnz_id from auth_t_funzionalita where applicazione_id = 10 and fnz_tipo_id =3);
--
select * from auth_t_funzionalita where applicazione_id = 10 and fnz_tipo_id =2;
--
select fnz_id from auth_t_funzionalita t where t.applicazione_id = 10 and fnz_tipo_id =2
except
select fnz_id from auth_r_funzionalita_tree where fnz_id in 
(select fnz_id from auth_t_funzionalita t where t.applicazione_id = 10 and fnz_tipo_id =2)
and auth_r_funzionalita_tree.fnztree_id_parent = 
(select fnztree_id  from auth_r_funzionalita_tree where fnz_id =
(select fnz_id from auth_t_funzionalita t where t.applicazione_id = 10 and fnz_tipo_id =3))

-- qry che trova la applicazioni con "problemi"
select * from auth_d_applicazione t1 
where  
 exists (
select fnz_id from auth_t_funzionalita t where t.applicazione_id = t1.id and fnz_tipo_id =2
except
select fnz_id from auth_r_funzionalita_tree where fnz_id in 
(select fnz_id from auth_t_funzionalita t where t.applicazione_id = t1.id and fnz_tipo_id =2)
and auth_r_funzionalita_tree.fnztree_id_parent = 
(select fnztree_id  from auth_r_funzionalita_tree where fnz_id =
(select fnz_id from auth_t_funzionalita t where t.applicazione_id = t1.id and fnz_tipo_id =3))
) and codice<>'PUA';


/*
select * from auth_r_applicazione_collocazione  order by data_inserimento desc;

INSERT INTO lcce.auth_r_applicazione_collocazione
( id,id_coll, id_app, data_inserimento)
VALUES(nextval ('auth_r_applicazione_collocazione_id') , 46, 10, now());

DELETE FROM lcce.auth_r_applicazione_collocazione
WHERE id=3;
DELETE FROM lcce.auth_r_applicazione_collocazione
WHERE id=2;
DELETE FROM lcce.auth_r_applicazione_collocazione
WHERE id=1;
select * from auth_r_applicazione_collocazione
*/
/*
-- CUP (10) (fatto)
-- CUP (10) (fatto)
-- 
INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_inserimento, data_aggiornamento, cf_operatore)
select 
nextval('auth_t_funzionalita_fnz_id_seq') fnz_id, 
'CUP' fnz_codice, 
'CUP' fnz_descrizione, 
3 fnz_tipo_id, 
10 applicazione_id, 
'2021-01-01 00:00:00'::timestamp data_inizio_validita, 
now() data_inserimento, 
now() data_aggiornamento, 
'CSI-VEZ_10' cf_operatore;
--
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select
(select fnz_id from auth_t_funzionalita where fnz_codice='CUP') fnz_id,
null fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_10';
---
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select 
fnz_id,
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'CUP'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL) fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_10'
from auth_t_funzionalita where 
auth_t_funzionalita.Applicazione_id = 10 and 
auth_t_funzionalita.fnz_tipo_id = 2;

-- DMACMPA (4) (fatto)
-- DMACMPA (4) (fatto)
select * from auth_v_tree  where applicazione_id in (4,-32) order by applicazione_id,tipo_funzionalita,fnz_descrizione,fnz_descrizione_parent,tipo_funzionalita_parent;
select * from auth_t_funzionalita where applicazione_id=4 and fnz_tipo_id=3;

INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_inserimento, data_aggiornamento, cf_operatore)
select 
nextval('auth_t_funzionalita_fnz_id_seq') fnz_id, 
'DMACMPA' fnz_codice, 
'FSE Punto Assistito' fnz_descrizione, 
3 fnz_tipo_id, 
4 applicazione_id, 
'2021-01-01 00:00:00'::timestamp data_inizio_validita, 
now() data_inserimento, 
now() data_aggiornamento, 
'CSI-VEZ_4' cf_operatore;
--
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select
(select fnz_id from auth_t_funzionalita where fnz_codice='DMACMPA') fnz_id,
null fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_4';
---
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select 
fnz_id,
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'DMACMPA'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL) fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_4'
from auth_t_funzionalita where 
auth_t_funzionalita.Applicazione_id = 4 and 
auth_t_funzionalita.fnz_tipo_id = 2;


-- NTFCIT (7) (fatto)
-- NTFCIT (7) (fatto)
select * from auth_v_tree  where applicazione_id in (7,-32) order by applicazione_id,tipo_funzionalita,fnz_descrizione,fnz_descrizione_parent,tipo_funzionalita_parent;
select * from auth_t_funzionalita where applicazione_id=7 and fnz_tipo_id=3;
select * from auth_t_funzionalita where fnz_codice='NTFCIT';
select * from auth_r_funzionalita_tree where fnztree_id in (1261,30);

UPDATE lcce.auth_r_funzionalita_tree
	SET fnztree_id_parent=1261
	WHERE fnztree_id=30;


INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_inserimento, data_aggiornamento, cf_operatore)
select 
nextval('auth_t_funzionalita_fnz_id_seq') fnz_id, 
'NTFCIT_APP' fnz_codice, 
'Notifica Cittadino' fnz_descrizione, 
3 fnz_tipo_id, 
7 applicazione_id, 
'2021-01-01 00:00:00'::timestamp data_inizio_validita, 
now() data_inserimento, 
now() data_aggiornamento, 
'CSI-VEZ_7' cf_operatore;
--
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select
(select fnz_id from auth_t_funzionalita where fnz_codice='NTFCIT_APP') fnz_id,
null fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_7';
---
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select 
fnz_id,
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'NTFCIT_APP'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL) fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_7'
from auth_t_funzionalita where 
auth_t_funzionalita.Applicazione_id = 7 and 
auth_t_funzionalita.fnz_tipo_id = 2;

-- DMAWA_PUA (12) (fatto)
-- DMAWA_PUA (12) (fatto)
select * from auth_v_tree  where applicazione_id in (12,-32) order by applicazione_id,tipo_funzionalita,fnz_descrizione,fnz_descrizione_parent,tipo_funzionalita_parent;
select * from auth_t_funzionalita where applicazione_id=12 and fnz_tipo_id=3;
select * from auth_t_funzionalita where fnz_codice='DMAWA_PUA';


INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_inserimento, data_aggiornamento, cf_operatore)
select 
nextval('auth_t_funzionalita_fnz_id_seq') fnz_id, 
'DMAWA_PUA' fnz_codice, 
'Fascicolo Sanitario Elettronico (Medico)' fnz_descrizione, 
3 fnz_tipo_id, 
12 applicazione_id, 
'2021-01-01 00:00:00'::timestamp data_inizio_validita, 
now() data_inserimento, 
now() data_aggiornamento, 
'CSI-VEZ_12' cf_operatore;
--
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select
(select fnz_id from auth_t_funzionalita where fnz_codice='DMAWA_PUA') fnz_id,
null fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_12';
---
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select 
fnz_id,
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'DMAWA_PUA'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL) fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_12'
from auth_t_funzionalita where 
auth_t_funzionalita.Applicazione_id = 12 and 
auth_t_funzionalita.fnz_tipo_id = 2;

-- ROL (5) (fatto)
-- ROL (5) (fatto)
select * from auth_v_tree  where applicazione_id in (5,-32) order by applicazione_id,tipo_funzionalita,fnz_descrizione,fnz_descrizione_parent,tipo_funzionalita_parent;
select * from auth_t_funzionalita where applicazione_id=5 and fnz_tipo_id=3;
select * from auth_t_funzionalita where fnz_codice='ROL';
select * from auth_r_funzionalita_tree where fnztree_id=25;


INSERT INTO auth_t_funzionalita
(fnz_id, fnz_codice, fnz_descrizione, fnz_tipo_id, applicazione_id, data_inizio_validita, data_inserimento, data_aggiornamento, cf_operatore)
select 
nextval('auth_t_funzionalita_fnz_id_seq') fnz_id, 
'ROL' fnz_codice, 
'Ritiro Referti On Line' fnz_descrizione, 
3 fnz_tipo_id, 
5 applicazione_id, 
'2021-01-01 00:00:00'::timestamp data_inizio_validita, 
now() data_inserimento, 
now() data_aggiornamento, 
'CSI-VEZ_5' cf_operatore;
--
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select
(select fnz_id from auth_t_funzionalita where fnz_codice='ROL') fnz_id,
null fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_5';
---
insert into auth_r_funzionalita_tree
(fnz_id,fnztree_id_parent,data_inizio_validita,cf_operatore)
select 
fnz_id,
(select T2.fnztree_id from auth_r_funzionalita_tree t2,auth_t_funzionalita x  where X.fnz_codice = 'ROL'
and x.fnz_id = t2.fnz_id and T2.fnztree_id_parent is NULL) fnztree_id_parent,
'2021-01-01 00:00:00'::timestamp  data_inizio_validita,
'CSI-VEZ_5'
from auth_t_funzionalita where 
auth_t_funzionalita.Applicazione_id = 5 and 
auth_t_funzionalita.fnz_tipo_id = 2;

*/

-- 8 (FATTO)
/*verificare se tutti gli utenti sono collegati ai profili e non alle funzionalitÃ */

----
select * from auth_v_tree_UTENTI qry_1
where  /*exists (select * from auth_v_tree_UTENTI qry_2 where qry_2.id_utente=qry_1.id_utente and 
                                            qry_2.applicazione_id=qry_1.applicazione_id
                                            and qry_2.tipo_funzionalita = '1-Profilo')
     and  */ exists (select * from auth_v_tree_UTENTI qry_2 where qry_2.id_utente=qry_1.id_utente and 
                                            qry_2.applicazione_id=qry_1.applicazione_id
                                            and qry_2.tipo_funzionalita = '2-Funzionalita')  
                                           --and fnz_descrizione_parent is not NULL
                                            ;
                                           
select * from auth_v_tree_UTENTI where codice_fiscale= 'BNTMNL82B47A783O'  ;     


/*
select * from auth_r_abilitazione where ID in (4018,
4020,
4021,
4019);

-- Auto-generated SQL script #202208081513
UPDATE lcce.auth_r_abilitazione
	SET fnztree_id=1262
	WHERE ID in (4018,
4020,
4021,
4019);
*/

                                          
/*                                           
select * from auth_r_abilitazione where ID in (48463,
47132,
47131,
47128,
47129);

delete from auth_r_abilitazione where ID in (48463,
47132,
47131,
47128,
47129);


*/

/*
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(47128, 3446, 269, 34808, NULL, '5e166494-6f36-403d-9298-182f76c7ae3c', '2020-01-01 00:00:00.000', NULL, '2022-03-03 15:39:31.012', '2022-03-03 15:39:31.012', NULL, 'CSI');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(47129, 72114, 272, 34804, NULL, '8b8fd4b0-eef2-4749-bdaf-b5e337ee52d5', '2020-01-01 00:00:00.000', NULL, '2022-03-03 16:05:52.136', '2022-03-03 16:05:52.136', NULL, 'CSI');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(47131, 2430, 272, 2431, NULL, '7d0fdead-7deb-4524-9c3b-dd58939906cd', '2020-01-01 00:00:00.000', NULL, '2022-03-04 12:11:15.052', '2022-03-04 12:11:15.052', NULL, 'CSI');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(47132, 2430, 272, 34809, NULL, '98788627-54ef-4aec-964f-df785c019bf1', '2020-01-01 00:00:00.000', NULL, '2022-03-04 14:04:25.685', '2022-03-04 14:04:25.685', NULL, 'CSI');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(48463, 2430, 276, 2431, NULL, 'd498de95-e660-469d-b83a-785db12a8435', '2020-01-01 00:00:00.000', NULL, '2022-03-23 09:18:05.888', '2022-03-23 09:18:05.888', NULL, 'CSI');
*/


-- 9
/*configurare matrice Legame SOL-ASL (auth_r_applicazione_collocazione) per sol esistenti (a partire dagli utenti)*/

-- 1� INSERT
select distinct COL_ID,applicazione_id--,id_utente  
from auth_v_tree_UTENTI 
where 
  (col_id, coalesce (applicazione_id,-1)) 
not in (select id_coll,id_app from auth_r_applicazione_collocazione) 
and 'S' = coalesce ((select FLAG_AZIENDA from auth_t_collocazione where auth_t_collocazione.col_id =auth_v_tree_UTENTI.COL_ID  ),'=')
order by 2 nulls first,1;
-- 2� INSERT
select distinct 
(select col_id  from auth_t_collocazione where auth_t_collocazione.col_cod_azienda  = auth_v_tree_UTENTI.col_cod_azienda and FLAG_AZIENDA='S')col_id
,applicazione_id--,id_utente  
from auth_v_tree_UTENTI 
where 
  (col_id, coalesce (applicazione_id,-1)) 
not in (select id_coll,id_app from auth_r_applicazione_collocazione) 
and 'S' <> coalesce ((select FLAG_AZIENDA from auth_t_collocazione where auth_t_collocazione.col_id =auth_v_tree_UTENTI.COL_ID  ),'=')
order by 2 nulls first,1
;


select * from (     
 SELECT
  o.conname AS constraint_name,
  (SELECT nspname FROM pg_namespace WHERE oid=m.relnamespace) AS source_schema,
  m.relname AS source_table,
  (SELECT a.attname FROM pg_attribute a WHERE a.attrelid = m.oid AND a.attnum = o.conkey[1] AND a.attisdropped = false) AS source_column,
  (SELECT nspname FROM pg_namespace WHERE oid=f.relnamespace) AS target_schema,
  f.relname AS target_table,
  (SELECT a.attname FROM pg_attribute a WHERE a.attrelid = f.oid AND a.attnum = o.confkey[1] AND a.attisdropped = false) AS target_column
FROM
  pg_constraint o LEFT JOIN pg_class f ON f.oid = o.confrelid LEFT JOIN pg_class m ON m.oid = o.conrelid
WHERE
  o.contype = 'f' AND o.conrelid IN (SELECT oid FROM pg_class c WHERE c.relkind = 'r')) aa
  where target_table='auth_d_applicazione'
  ;     

-- 10
 /*r_abilitazioni --> inserire l'id_applicazione della funzionalitÃ  (NB. fare attenzione alla web app medico) 
   --> da cambiare applicazione per CCE e farlo uguale a DMAWA applicazione (da verificare)*/
 
 -- CHIEDERE ok
 select * from auth_r_abilitazione WHERE fnztree_id is null;
 
 select 
 aa.*,
 (select auth_d_applicazione.descrizione  from auth_d_applicazione where auth_d_applicazione.id=aa.id_applicazione) descrizione_app,
 (select auth_d_applicazione.descrizione  from auth_d_applicazione where auth_d_applicazione.id=aa.id_applicazione_calc) descrizione_app_calc
 from (
   select id_applicazione,id_applicazione_calc,count(*) cc from (
          select id_applicazione,
          (select auth_v_tree.applicazione_id  from auth_v_tree where auth_v_tree.fnztree_id =auth_r_abilitazione.fnztree_id) id_applicazione_calc
          from auth_r_abilitazione
          where fnztree_id is not null
          ) a
 group by id_applicazione,id_applicazione_calc) aa
 order by 1 nulls last;

select 
id_applicazione,
(select auth_v_tree.applicazione_id  from auth_v_tree where auth_v_tree.fnztree_id =auth_r_abilitazione.fnztree_id) id_applicazione_calc,
*
from auth_r_abilitazione   where   id_applicazione  is null;






-- CASO id_applicazione=11 (FATTO)
/*
select * from auth_r_abilitazione where id_applicazione=11;
select * from auth_r_abilitazione where fnztree_id=38 and id_applicazione is not null;

select * from auth_v_tree where  fnztree_id=38;

select * from auth_r_funzionalita_tree   where  fnztree_id=38;
SELECT x.applicazione_id  FROM lcce.auth_t_funzionalita x WHERE x.fnz_id = 34
*/
/*
-- Auto-generated SQL script #202208091054
UPDATE lcce.auth_r_abilitazione
	SET id_applicazione=12
	WHERE id=34079;
UPDATE lcce.auth_r_abilitazione
	SET id_applicazione=12
	WHERE id=45158;
UPDATE lcce.auth_r_abilitazione
	SET id_applicazione=12
	WHERE id=46408;
*/
 
-- 11
 /*verificare che tutte le funzionalitÃ  abbiano l'id_applicazione in t_funzionalitÃ */
 select applicazione_id,* from auth_t_funzionalita  where applicazione_id is null
 
-- 12
/*gli operatori CSI devono essere collegati alla collocazione CSI PIEMONTE*/
select * from auth_d_ruolo where id=30 ;
select * from auth_t_collocazione where UPPER(col_descrizione) like '%CSI PIEMONTE%'; --> 30934

-- QRY DI VERIFICA
select auth_t_collocazione.*,* from auth_r_utente_collocazione,
auth_t_collocazione  
where ute_id in (select auth_r_ruolo_utente.id_utente  from auth_r_ruolo_utente where id_ruolo =30)
and auth_r_utente_collocazione.col_id  = auth_t_collocazione.col_id 
order by col_descrizione
;

-- UTENTI CON PIU COLLOCAZIONI
select ute_id,COUNT(*) from auth_r_utente_collocazione where ute_id in (
select auth_r_ruolo_utente.id_utente  from auth_r_ruolo_utente where id_ruolo =30)
group by ute_id
order by 2 DESC

-- A (UPDATE PER ROGNA --> HA DOPPIA COLLOCAZIONE)
-- 30934 --> CSI@@	CSI PIEMONTE	010000	CSI PIEMONTE
/*
select * from auth_r_utente_collocazione where ute_id in (
select auth_r_ruolo_utente.id_utente  from auth_r_ruolo_utente where id_ruolo =30)
and ute_id=24461;
-- B
13727 --> 63
35390 --> 51

UPDATE lcce.auth_r_utente_collocazione
	SET data_cancellazione='2021-10-04 13:22:00.042'
	WHERE utecol_id=13727;
UPDATE lcce.auth_r_utente_collocazione
	SET col_id=30934
	WHERE utecol_id=35390;

select * from auth_r_utente_collocazione where utecol_id in (13727,35390);

-- B
select * from auth_r_abilitazione where utecol_id in (13727,35390) order by utecol_id;

UPDATE lcce.auth_r_abilitazione
	SET utecol_id=35390
	WHERE id=25249;
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=35390
	WHERE id=25250;
*/
SELECT * FROM auth_T_utente where ID=24461;
select * from auth_r_utente_collocazione where ute_id =24461;
-- NOW
select * from auth_t_collocazione where col_id in (
63,    -- 1234567890123@999CSI	FARMACIA CSI@VIA ROMA@1@TORINO@	010301	FARMACIA CSI
30934  -- CSI@@	CSI PIEMONTE	010000	CSI PIEMONTE
);
-- OLD
select * from auth_t_collocazione where col_id in (63,51);

UPDATE lcce.auth_r_utente_collocazione
	SET data_cancellazione=null
	WHERE utecol_id=13727;   --> 63,    -- 1234567890123@999CSI	FARMACIA CSI@VIA ROMA@1@TORINO@	010301	FARMACIA CSI
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=13727      --> 63,    -- 1234567890123@999CSI	FARMACIA CSI@VIA ROMA@1@TORINO@	010301	FARMACIA CSI
	WHERE id=25249;
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=13727      --> 63,    -- 1234567890123@999CSI	FARMACIA CSI@VIA ROMA@1@TORINO@	010301	FARMACIA CSI
	WHERE id=25250;


select * from auth_v_tree_utenti where id_utente = 24461


select * from auth_r_abilitazione where id_ruolo_utente in 
(select id  from auth_r_ruolo_utente where id_utente = 24461)
and ID in (25250,25249)
;

select * from auth_t_collocazione atc ;

010000    CSI PIEMONTE
select * from auth_w_collocazione_utenti_manuale where codice_fiscale = 'BNTMNL82B47A783O' 


-- Auto-generated SQL script #202208101021
UPDATE lcce.auth_w_collocazione_utenti_manuale
	SET cod_asr='010000',desc_asr='CSI PIEMONTE'
	WHERE id=387477 and codice_fiscale = 'BNTMNL82B47A783O' ;
UPDATE lcce.auth_w_collocazione_utenti_manuale
	SET cod_asr='010000',desc_asr='CSI PIEMONTE'
	WHERE id=387478 and codice_fiscale = 'BNTMNL82B47A783O' ;
UPDATE lcce.auth_w_collocazione_utenti_manuale
	SET cod_asr='010000',desc_asr='CSI PIEMONTE'
	WHERE id=387479 and codice_fiscale = 'BNTMNL82B47A783O' ;



/*INSERT INTO lcce.auth_r_utente_collocazione
(utecol_id, ute_id, col_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore, flag_configuratore)
VALUES(13727, 24461, 63, '2021-10-04 13:22:00.042', NULL, '2021-10-04 13:22:00.042', '2021-10-04 13:22:00.042', NULL, 'Batch Farmacie', NULL);
INSERT INTO lcce.auth_r_utente_collocazione
(utecol_id, ute_id, col_id, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore, flag_configuratore)
VALUES(35390, 24461, 51, '2022-03-23 12:15:55.383', '2099-12-31 00:00:00.000', '2022-03-23 12:15:55.383', '2022-03-23 12:15:55.383', NULL, 'Batch Amministrativi', NULL);
-------
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(25249, 24810, 1, 13727, NULL, 'a7e39dd0-9f42-4f6a-8388-3f9bf144a3c7', '2021-10-04 13:22:00.042', NULL, '2021-10-04 13:22:00.042', '2021-10-04 13:22:00.042', NULL, 'Batch Farmacie');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(25250, 24810, 35, 13727, NULL, '42df7baa-84f9-4521-b57a-6916d60e0c0e', '2021-10-04 13:22:00.042', NULL, '2021-10-04 13:22:00.042', '2021-10-04 13:22:00.042', NULL, 'Batch Farmacie');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(60743, 73930, 43, 35390, 16, '00c928ee-8869-410b-ab01-b55a22e90005', '2022-05-02 00:00:00.000', NULL, '2022-05-02 14:27:52.210', '2022-05-02 14:27:52.210', NULL, 'BNTMNL82B47A783O');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(48553, 72542, 268, 35390, NULL, 'b9cdf19d-25f1-482d-b39f-9c9211054291', '2022-03-23 12:15:55.383', '2099-12-31 00:00:00.000', '2022-03-23 12:15:55.383', '2022-03-23 12:15:55.383', NULL, 'Batch Amministrativi');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(48554, 72542, 271, 35390, NULL, '4be27d24-1abb-4ebd-b6ff-befaaec5e4cc', '2022-03-23 12:15:55.383', '2099-12-31 00:00:00.000', '2022-03-23 12:15:55.383', '2022-03-23 12:15:55.383', NULL, 'Batch Amministrativi');
INSERT INTO lcce.auth_r_abilitazione
(id, id_ruolo_utente, fnztree_id, utecol_id, id_applicazione, codice_abilitazione, data_inizio_validita, data_fine_validita, data_inserimento, data_aggiornamento, data_cancellazione, cf_operatore)
VALUES(48555, 72542, 7, 35390, NULL, 'e704ea72-293f-46ac-8e4f-f848f29e319d', '2022-03-23 12:15:55.383', '2099-12-31 00:00:00.000', '2022-03-23 12:15:55.383', '2022-03-23 12:15:55.383', NULL, 'Batch Amministrativi');

*/

select auth_v_tree_utenti.*,
auth_r_abilitazione.*
from auth_v_tree_utenti ,
auth_r_abilitazione
where id_utente =2102 and auth_v_tree_utenti.id_auth_r_abilitazione =auth_r_abilitazione.id 
order by id_auth_r_abilitazione 
;

select * from auth_r_abilitazione where auth_r_abilitazione.id_auth_r_abilitazione =auth_v_tree_utenti.id_auth_r_abilitazione  

-- CASO BONTEMPI  30934 (FATTO)
/*
select * from auth_t_utente where codice_fiscale like 'BNTMNL%';

select * from auth_r_ruolo_utente where id_utente =2102;

select * from auth_r_utente_collocazione where ute_id =2102 and utecol_id <> 3064; 

select * from auth_r_abilitazione where utecol_id in (2103,21263,64015) order by utecol_id; -- TENIAMO LA 2103

-- A
do $$
BEGIN
UPDATE lcce.auth_r_utente_collocazione
	SET col_id=30934
	WHERE utecol_id=2103;
UPDATE lcce.auth_r_utente_collocazione
	SET data_cancellazione=NOW()
	WHERE utecol_id=21263;
UPDATE lcce.auth_r_utente_collocazione
	SET data_cancellazione=NOW()
	WHERE utecol_id=64015;
-- B
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=2103
	WHERE id=34075;
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=2103
	WHERE id=34076;
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=2103
	WHERE id=47381;
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=2103
	WHERE id=47382;
UPDATE lcce.auth_r_abilitazione
	SET utecol_id=2103
	WHERE id=79392;
end; $$

*/

-- 13
/*creare azienda CSI Piemonte*/
INSERT INTO auth_d_azienda
(cod_azienda, desc_azienda, data_inizio_val, data_fine_val)
VALUES('010000', 'CSI', '2022-01-01', null);


-- 14
/*creare legame nella r_utente_visibulita_azienda per csi solo gli operatori csi*/

insert into r_utente_visibulita_azienda (ID_UTENTE,ID_AZIENDA) 
select id_utente,'????'  from auth_r_ruolo_utente where id_ruolo =30
union 
select 2102,'????'  --> BONTEMPI 

INSERT INTO lcce.auth_r_utente_visibilita_azienda
( id_utente, id_azienda, data_inizio_val,  cf_operatore, data_operazione, data_inserimento)
select DISTINCT id_utente,
      (select auth_d_azienda.id_azienda  from auth_d_azienda   where  auth_d_azienda.cod_azienda = auth_v_tree_UTENTI.col_cod_azienda),
            NOW(),
      'VEZ',
      NOW(),
      NOW()
      from auth_v_tree_UTENTI where Applicazione_id = (select id  from auth_d_applicazione where codice='SOLCONFIG')
and 'S' = coalesce ((select FLAG_AZIENDA from auth_t_collocazione where auth_t_collocazione.col_id =auth_v_tree_UTENTI.COL_ID  ),'=')
and not exists (select * from auth_r_utente_visibilita_azienda
where 
auth_r_utente_visibilita_azienda.id_utente =auth_v_tree_UTENTI.id_utente  and 
auth_r_utente_visibilita_azienda.id_azienda = (select auth_d_azienda.id_azienda  from auth_d_azienda   where  auth_d_azienda.cod_azienda = auth_v_tree_UTENTI.col_cod_azienda)
);


-- 15 (FATTO)
/*Creare collocazione per CSI */
/*
INSERT INTO lcce.auth_t_collocazione (col_codice,col_descrizione,col_cod_azienda,col_desc_azienda,col_tipo_id,col_fonte_id,data_inizio_validita,data_fine_validita,data_inserimento,data_aggiornamento,data_cancellazione,cf_operatore,flag_azienda,cod_struttura,denom_struttura,cod_uo,denom_uo,cod_multi_spec,denom_multi_spec,codice_elemento_organizzativo,desc_elemento,id_ambulatorio,denom_ambulatorio) 
VALUES 
('010000','CSI','010000','CSI PIEMONTE',1,1,'2020-07-29 09:04:43.425','2099-12-31 00:00:00','2020-07-29 09:04:43.425797','2020-07-29 12:08:38.18036',NULL,'CSI','S',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
*/
-- 16
/*creare tutte le aziende in d_azienda con lo stesso codice dell'az in t_collocazione*/
-- 2
-- SELECT * FROM auth_d_azienda
insert into auth_d_azienda
(cod_azienda ,desc_azienda,data_inizio_val)
select col_cod_azienda ,col_desc_azienda,NOW() data_inizio_val
from auth_t_collocazione atc where flag_azienda ='S'
and not exists (select * from auth_d_azienda   where auth_d_azienda.cod_azienda=col_cod_azienda


-- 17
/*tutte le persone associate al configuratore devono avere il legame con l'azienda delle sue collocazioni nella tab auth_r_utente_visibilita_azienda*/

select id  from auth_d_applicazione where codice='SOLCONFIG';

select DISTINCT id_utente,cognome,NOME,auth_v_tree_UTENTI.col_id 
from auth_v_tree_UTENTI where Applicazione_id = (select id  from auth_d_applicazione where codice='SOLCONFIG')
and 'S' = coalesce ((select FLAG_AZIENDA from auth_t_collocazione where auth_t_collocazione.col_id =auth_v_tree_UTENTI.COL_ID  ),'=')
order by 2
;



select * from auth_r_utente_visibilita_azienda


-- 18
/*18.	popolare le tab nuove dei ruoli di relazione (vi devo fornire gli excel)
a.	AUTH_R_RUOLO_COMPATIBILITA  
b.	AUTH_R_RUOLO_PROFILO e 
c.	auth_r_ruolo_ruoli (in questa tab ruolo OPCSI deve avere tutti i ruoli) 
d.	verificare se gli utenti rispettano queste compatibilitÃ 
*/


-- 19
/*auth_r_funzionalita_tipologia_dato_permesso: modificare 
 * lâ€™id funz indicando lâ€™id funzionalitÃ  di tipo funzionalitÃ  e non di tipo profilo ïƒ  da verificare con i verticali*/

-- DA CHIDERE: la tipologia tipologia_dato_permesso Ã¨ da spostare da l profilo alla funzionalita?


select * from auth_r_funzionalita_tipologia_dato_permesso

select * from auth_v_tree ;
select * from auth_v_tree_UTENTI  ;

select 
fnz_id ,
(SELECT auth_d_funzionalita_tipo.fnz_tipo_descrizione  FROM lcce.auth_t_funzionalita x,auth_d_funzionalita_tipo  
WHERE x.fnz_id = 84 and X.fnz_tipo_id=auth_d_funzionalita_tipo.fnz_tipo_id),*
from auth_r_funzionalita_tipologia_dato_permesso;

select * from auth_t_funzionalita X1 where X1.applicazione_id in (
SELECT X.applicazione_id FROM lcce.auth_t_funzionalita x
WHERE x.fnz_id = 84)

SELECT * FROM lcce.auth_t_funzionalita x
WHERE x.fnz_id = 84





































/*
--(select t1.fnz_id  from auth_r_funzionalita_tree t1 where t0.fnztree_id =t0.fnz_id  ),
select 
(select auth_t_funzionalita.fnz_descrizione from auth_r_funzionalita_tree t1,auth_t_funzionalita where t0.fnztree_id_parent =t1.fnztree_id
and t1.fnz_id=auth_t_funzionalita.fnz_id 
), 
(select auth_t_funzionalita.fnz_descrizione from auth_t_funzionalita where t0.fnz_id=auth_t_funzionalita.fnz_id ),
'#################',
* 
from auth_r_funzionalita_tree t0 where t0.fnz_id in 
(select auth_t_funzionalita.fnz_id  from auth_t_funzionalita where fnz_tipo_id
in (SELECT fnz_tipo_id FROM auth_d_funzionalita_tipo where fnz_tipo_descrizione='Funzionalita')
and applicazione_id = 32 
)
order by 2
;

select 
(select auth_t_funzionalita.fnz_descrizione from auth_r_funzionalita_tree t1,auth_t_funzionalita where t0.fnztree_id_parent =t1.fnztree_id
and t1.fnz_id=auth_t_funzionalita.fnz_id 
), 
(select auth_t_funzionalita.fnz_descrizione from auth_t_funzionalita where t0.fnz_id=auth_t_funzionalita.fnz_id ),
'#################',
* 
from auth_r_funzionalita_tree t0 where t0.fnz_id in 
(select auth_t_funzionalita.fnz_id  from auth_t_funzionalita where fnz_tipo_id
in (SELECT fnz_tipo_id FROM auth_d_funzionalita_tipo where fnz_tipo_descrizione='Applicazione')
and applicazione_id = 32 
)
order by 1,2;


select 
(select auth_t_funzionalita.fnz_descrizione from auth_t_funzionalita where t0.fnz_id=auth_t_funzionalita.fnz_id ),
'############################',
* from auth_r_funzionalita_tree t0 where t0.fnz_id in 
(select auth_t_funzionalita.fnz_id  from auth_t_funzionalita where fnz_tipo_id
in (SELECT fnz_tipo_id FROM auth_d_funzionalita_tipo where fnz_tipo_descrizione='Profilo')
and  applicazione_id = 32 
)
order by 2;

-- funzionalita
select 
(select auth_t_funzionalita.applicazione_id  from auth_t_funzionalita where t0.fnz_id=auth_t_funzionalita.fnz_id ),
(select auth_t_funzionalita.fnz_descrizione from auth_t_funzionalita where t0.fnz_id=auth_t_funzionalita.fnz_id ),
'############################',
*
from auth_r_funzionalita_tree t0 where fnztree_id_parent=565
order by 1



