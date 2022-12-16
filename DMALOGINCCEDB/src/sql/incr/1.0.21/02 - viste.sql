
select * from auth_v_tree_UTENTI
-- 
drop view auth_v_tree_UTENTI;
drop view auth_v_tree;
create view auth_v_tree as
select 
  applicazione_id,
  (select auth_d_applicazione.descrizione  from auth_d_applicazione where id=applicazione_id) applicazione,
  (select auth_d_applicazione.codice  from auth_d_applicazione where id=applicazione_id) codice_applicazione,
  auth_t_funzionalita.fnz_tipo_id || '-' ||auth_d_funzionalita_tipo.fnz_tipo_descrizione tipo_funzionalita,
  auth_t_funzionalita.fnz_codice ,
  auth_t_funzionalita.fnz_descrizione ,
  --t0.fnztree_id_parent ,
  --
  --(select t1.fnz_id  from auth_r_funzionalita_tree t1 where t1.fnztree_id=t0.fnztree_id_parent),
  (select auth_t_funzionalita.fnz_descrizione  from auth_r_funzionalita_tree t1,auth_t_funzionalita where t1.fnztree_id=t0.fnztree_id_parent
  and t1.fnz_id = auth_t_funzionalita.fnz_id 
  ) fnz_descrizione_parent,
  (select auth_d_funzionalita_tipo.fnz_tipo_descrizione  from auth_r_funzionalita_tree t1,auth_t_funzionalita,auth_d_funzionalita_tipo where t1.fnztree_id=t0.fnztree_id_parent
  and t1.fnz_id = auth_t_funzionalita.fnz_id 
  and auth_d_funzionalita_tipo.fnz_tipo_id =auth_t_funzionalita.fnz_tipo_id 
  ) tipo_funzionalita_parent,
  --
  t0.fnztree_id 
  from 
auth_r_funzionalita_tree t0,
auth_t_funzionalita,
auth_d_funzionalita_tipo
where 
t0.fnz_id =auth_t_funzionalita.fnz_id 
and auth_d_funzionalita_tipo.fnz_tipo_id =auth_t_funzionalita.fnz_tipo_id 
--
and      (auth_t_funzionalita.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_t_funzionalita.data_fine_validita,now()+interval '1 day')> now())
      and auth_t_funzionalita.data_cancellazione is null 
and      (t0.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(t0.data_fine_validita,now()+interval '1 day')> now())
      and t0.data_cancellazione is null 
and      (auth_d_funzionalita_tipo.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_d_funzionalita_tipo.data_fine_validita,now()+interval '1 day')> now())
      and auth_d_funzionalita_tipo.data_cancellazione is null 
----
order by 2,auth_t_funzionalita.fnz_tipo_id ,4 nulls first,3;


drop view auth_v_tree_UTENTI;
--
create VIEW auth_v_tree_UTENTI as
select 
auth_r_ruolo_utente.id_utente, 
auth_t_utente.codice_fiscale ,
auth_t_utente.cognome ,
auth_t_utente.nome ,
auth_d_ruolo.codice  codice_RUOLO,
auth_d_ruolo.descrizione descrizione_RUOLO,
col_codice,
 col_descrizione,
--
 col_cod_azienda,
--
 col_desc_azienda,auth_t_collocazione.col_id ,
--
auth_v_tree.*,
auth_r_abilitazione.id ID_auth_r_abilitazione
from 
auth_r_abilitazione,
auth_v_tree,
auth_r_ruolo_utente,
auth_t_utente,
auth_d_ruolo ,
auth_r_utente_collocazione,
auth_t_collocazione
where 
auth_v_tree.fnztree_id=auth_r_abilitazione.fnztree_id and
auth_r_abilitazione.id_ruolo_utente = auth_r_ruolo_utente.id and
auth_t_utente.id = auth_r_ruolo_utente.id_utente and
auth_d_ruolo.id = auth_r_ruolo_utente.id_ruolo and
auth_r_utente_collocazione.col_id = auth_t_collocazione.col_id and
auth_r_utente_collocazione.ute_id = auth_t_utente.id and
auth_r_utente_collocazione.utecol_id  = auth_r_abilitazione.utecol_id  
--
and      (auth_r_abilitazione.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_r_abilitazione.data_fine_validita,now()+interval '1 day')> now())
      and auth_r_abilitazione.data_cancellazione is null 
and      (auth_r_ruolo_utente.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_r_ruolo_utente.data_fine_validita,now()+interval '1 day')> now())
and      (auth_t_utente.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_t_utente.data_fine_validita,now()+interval '1 day')> now())
 and      (auth_d_ruolo.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_d_ruolo.data_fine_validita,now()+interval '1 day')> now())
---------
         and      (auth_r_utente_collocazione.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_r_utente_collocazione.data_fine_validita,now()+interval '1 day')> now())
      and auth_r_utente_collocazione.data_cancellazione is null 
-----------  
         and      (auth_t_collocazione.data_fine_validita=to_timestamp('2099-12-31 00:00:00','yyyy-mm-dd hh24:mi:ss')
         or coalesce(auth_t_collocazione.data_fine_validita,now()+interval '1 day')> now())
      and auth_t_collocazione.data_cancellazione is null        
--
order by 1,applicazione,tipo_funzionalita,fnz_descrizione
;
