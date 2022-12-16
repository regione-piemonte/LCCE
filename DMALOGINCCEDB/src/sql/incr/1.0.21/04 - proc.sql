select * from auth_t_batch_chiusura_utenti_senza_accesso;
select * from auth_t_batch_chiusura_utenti_senza_accesso_attivita;

--drop table auth_t_batch_chiusura_utenti_senza_accesso_attivita
--drop table auth_t_batch_chiusura_utenti_senza_accesso

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
alter table auth_t_batch_chiusura_utenti_senza_accesso_attivita drop column batchat_dati_ricevuti;
alter table auth_t_batch_chiusura_utenti_senza_accesso_attivita drop column batchat_operazione_effettuata;

CREATE TABLE auth_t_batch_chiusura_utenti_senza_accesso_attivita (
	batchat_id bigserial NOT NULL,
	batchat_info text NOT NULL,
	batchat_dati_ricevuti text NULL,
	batchat_operazione_effettuata text NULL,
	batch_id int8 NULL,
	data_inserimento timestamp NOT NULL DEFAULT now(),
	data_aggiornamento timestamp NOT NULL DEFAULT now(),
	data_cancellazione timestamp NULL,
	CONSTRAINT pk_auth_t_batch_chiusura_utenti_senza_accesso_attivita PRIMARY KEY (batchat_id),
	CONSTRAINT auth_t_batch_collocazione_auth_t_batch_collocazione_attivita FOREIGN KEY (batch_id) REFERENCES lcce.auth_t_batch_chiusura_utenti_senza_accesso(batch_id)
);
CREATE INDEX idx_auth_t_batch_chiusura_utenti_no_acce__att1 ON lcce.auth_t_batch_chiusura_utenti_senza_accesso_attivita USING btree (batchat_info) WHERE (data_cancellazione IS NULL);




select fnc_chiusura_utenti_senza_accesso();
select * from auth_t_batch_chiusura_utenti_senza_accesso_attivita order by 1 desc;
select * from auth_t_batch_chiusura_utenti_senza_accesso order by 1 desc;


CREATE OR REPLACE FUNCTION fnc_chiusura_utenti_senza_accesso()
 RETURNS numeric
 LANGUAGE plpgsql
 SECURITY DEFINER
AS $function$
declare 
	v_batch_id BIGINT;
	c record;
	V_NUMERO_GIORNI_SENZA_ACCESSO  BIGINT;
	v_righe_aggiornate BIGINT;
	v_data_fine_validita timestamp =NOW();
	v_data_limite        timestamp;
	i bigint = 0;
	v_id_trattati text;
	v_step      text:='';
	v_id_utente bigint:=-1;
	/*
	INSERT INTO lcce.auth_t_configurazione
	(id,chiave, valore, descrizione, data_inserimento)
	VALUES( nextval('seq_auth_t_configurazione'),'NUMERO_GIORNI_SENZA_ACCESSO', '180', 'Numero giorni utile per chiusura utenti senza accesso', NOW());
	*/
begin
	v_step := '01';
	select nextval('auth_t_batch_chiusura_utenti_senza_accesso_batch_id_seq') into v_batch_id;
	--
	insert into auth_t_batch_chiusura_utenti_senza_accesso
	(batch_id ,batch_codice,data_inizio_attivita,batch_stato_id)
	values 
	(v_batch_id,'CHIUSURA_UTE_NO_ATT',clock_timestamp(),(select batch_stato_id from auth_d_batch_stato where batch_stato_codice='ATT' and DATA_CANCELLAZIONE is null ));
	---
    v_step := '02';
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'Inizio');
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'v_data_fine_validita = '|| coalesce(to_char(v_data_fine_validita,'dd/mm/yyyy hh24:mi:ss'),'N U L L'));
	--
	select valore::bigint into V_NUMERO_GIORNI_SENZA_ACCESSO from auth_t_configurazione where chiave='NUMERO_GIORNI_SENZA_ACCESSO';
	--v_data_limite =   now() - interval '180'||' day';
	select now() - (V_NUMERO_GIORNI_SENZA_ACCESSO||' day')::interval into v_data_limite;
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'v_data_limite = '|| coalesce(to_char(v_data_limite,'dd/mm/yyyy hh24:mi:ss'),'N U L L'));
	--
	v_step := '03';
	if (V_NUMERO_GIORNI_SENZA_ACCESSO is null or v_data_limite is null) then return 1/0; end if;
	-- i:=1/0;
	--
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'NUMERO_GIORNI_SENZA_ACCESSO = '||coalesce(V_NUMERO_GIORNI_SENZA_ACCESSO::text,'N U L L'));
	--
	for c in 
	(select * from
	(select id id_utente,ultimo_accesso_pua,data_inserimento from auth_t_utente where ultimo_accesso_pua <=  v_data_limite and ultimo_accesso_pua is not null
	union 
	select id id_utente,ultimo_accesso_pua,data_inserimento from auth_t_utente where data_inserimento  <= v_data_limite and  ultimo_accesso_pua is null and data_inserimento is not null
	)
	ut 
	where 
	(exists (select * from auth_t_preferenza where auth_t_preferenza.id_utente=ut.id_utente and coalesce(data_fine_validita ,now()+interval '1 day')>now() and data_cancellazione is null) or
	exists (select * from auth_r_abilitazione where 
	        auth_r_abilitazione.id_ruolo_utente in (select auth_r_ruolo_utente.id  from auth_r_ruolo_utente where auth_r_ruolo_utente.id_utente =ut.id_utente) and 
            coalesce(data_fine_validita ,now()+interval '1 day')>now() and auth_r_abilitazione.data_cancellazione is null) or
	exists (select * from auth_r_utente_collocazione where auth_r_utente_collocazione.ute_id  =ut.id_utente and coalesce(data_fine_validita ,now()+interval '1 day')>now()) or
	exists (select * from auth_r_ruolo_utente where auth_r_ruolo_utente.id_utente =ut.id_utente and coalesce(data_fine_validita ,now()+interval '1 day')>now()))
	and id_utente in (10376 , 9275,6,-8)
	order by id_utente
	--limit 1
	)
	loop 
		v_id_utente:=C.id_utente;
		v_step := '04';
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'------------');
		i=i+1;
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'id_utente = '|| c.id_utente);
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'ultimo_accesso_pua = '|| coalesce(to_char(c.ultimo_accesso_pua,'dd/mm/yyyy hh24:mi:ss'),'N U L L'));
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'data_inserimento = '|| coalesce(to_char(c.data_inserimento,'dd/mm/yyyy hh24:mi:ss'),'N U L L'));
		--
		v_step := '05';
		with QRY as
		(update auth_t_preferenza set data_fine_validita  = v_data_fine_validita/*,cf_operatore='fnc_chiusura_utenti_senza_accesso'*/,data_aggiornamento=now() where 
			id_utente=C.id_utente 
		 and coalesce(data_fine_validita ,now()+interval '1 day')>=now()
		 and data_cancellazione is null
		returning 1,preferenza_id)
		select count(*),string_agg(preferenza_id ::text,',') into v_righe_aggiornate,v_id_trattati from QRY;
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'v_righe_aggiornate (auth_t_preferenza) = '|| coalesce(v_righe_aggiornate::text,'N U L L')|| ', id = '|| coalesce(v_id_trattati,''));
		---
		v_step := '06';
		with QRY as
		(update auth_r_abilitazione set data_fine_validita  =v_data_fine_validita,cf_operatore='fnc_chiusura_utenti_senza_accesso',data_aggiornamento=now() where 
			id_ruolo_utente in (select auth_r_ruolo_utente.id  from auth_r_ruolo_utente where auth_r_ruolo_utente.id_utente =c.id_utente )
		 and coalesce(data_fine_validita ,now()+interval '1 day')>=now()
		 and data_cancellazione is null
		returning 1,id)
		select count(*),string_agg(id ::text,',') into v_righe_aggiornate,v_id_trattati from QRY;
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'v_righe_aggiornate (auth_r_abilitazione) = '|| coalesce(v_righe_aggiornate::text,'N U L L')|| ', id = '|| coalesce(v_id_trattati,''));
		---
		v_step := '07';
		with QRY as
		(update auth_r_utente_collocazione set data_fine_validita  = v_data_fine_validita,cf_operatore='fnc_chiusura_utenti_senza_accesso',data_aggiornamento=now() where 
			ute_id=C.id_utente 
		 and coalesce(data_fine_validita ,now()+interval '1 day')>=now()
		 and data_cancellazione is null
		returning 1,auth_r_utente_collocazione.utecol_id)
		select count(*),string_agg(utecol_id::text,',') into v_righe_aggiornate,v_id_trattati from QRY;
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'v_righe_aggiornate (auth_r_utente_collocazione) = '|| coalesce(v_righe_aggiornate::text,'N U L L')|| ', id = '|| coalesce(v_id_trattati,''));
		---
		v_step := '08';
		with QRY as
		(update auth_r_ruolo_utente set data_fine_validita  = v_data_fine_validita,cf_operatore='fnc_chiusura_utenti_senza_accesso',data_aggiornamento=now() where 
			id_utente=C.id_utente 
		 and coalesce(data_fine_validita ,now()+interval '1 day')>=now()
		-- and data_cancellazione is null
		returning 1,id)
		select count(*),string_agg(id::text,',') into v_righe_aggiornate,v_id_trattati from QRY;
		insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'v_righe_aggiornate (auth_r_ruolo_utente) = '|| coalesce(v_righe_aggiornate::text,'N U L L')|| ', id = '|| coalesce(v_id_trattati,''));
		---
	end loop;
	v_id_utente:=-1;
	v_step := '09';
	--raise notice '%' , v_batch_id::text;
	--
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'aggiornati '||i::text||' utenti!!!!!');
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'Fine');
	update auth_t_batch_chiusura_utenti_senza_accesso
	set data_fine_attivita = clock_timestamp() ,
	batch_stato_id=(select batch_stato_id from auth_d_batch_stato where batch_stato_codice='COMPL' and DATA_CANCELLAZIONE is null ),
	numero_utenti_trattati = i
	where batch_id=v_batch_id;
RETURN(0);
  -- gestione eccezioni ----
    exception
    when others then 
	insert into auth_t_batch_chiusura_utenti_senza_accesso
	(batch_id ,batch_codice,data_inizio_attivita,batch_stato_id)
	values 
	(v_batch_id,'CHIUSURA_UTE_NO_ATT',clock_timestamp(),(select batch_stato_id from auth_d_batch_stato where batch_stato_codice='ATT' and DATA_CANCELLAZIONE is null ));
	insert into auth_t_batch_chiusura_utenti_senza_accesso_attivita(batch_id,batchat_info)values(v_batch_id,'ERRORE V_step='||V_step||' ErroreDB = '
	||sqlstate::text ||' ' ||substring(SQLERRM from 1 for 1000)::text ||' (id_utente = '||v_id_utente::text||')');
RETURN(1); 
  -- fine gestione eccezioni ----
END;
$function$
;



select * from auth_t_batch_chiusura_utenti_senza_accesso;
select * from auth_t_batch_chiusura_utenti_senza_accesso_attivita order by 1 desc

select * from auth_t_batch_collocazione
select * from auth_t_batch_collocazione_attivita atbca 


-- 
data fine validita --> trunc(now())
operatore nome batch fnc_chiusura_utenti_senza_accesso
data aggiornamneto now()

select * from auth_t_preferenza  
select * from auth_r_abilitazione  
select * from auth_r_utente_collocazione  
select * from auth_r_ruolo_utente  

select * from auth_s_preferenza  
select * from auth_s_abilitazione  
select * from auth_s_utente_collocazione  
select * from auth_s_ruolo_utente  

select * from auth_t_batch_collocazione_attivita atbca 


select * from auth_t_utente  where ultimo_accesso_pua is null