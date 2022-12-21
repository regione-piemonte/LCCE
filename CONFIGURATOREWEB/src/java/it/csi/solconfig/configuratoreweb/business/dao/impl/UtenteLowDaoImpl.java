/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteLowDao;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RisultatiRicercaUtenteDTO;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UtenteLowDaoImpl extends EntityBaseLowDaoImpl<UtenteDto, Long> implements UtenteLowDao {

    @Override
    public UtenteDto salva(UtenteDto utenteDto) {
        return insert(utenteDto);
    }

    @Override
    public void modifica(UtenteDto utenteDto) {
        update(utenteDto);
    }

    @Override
    public Collection<UtenteDto> findByCodiceFiscale(String codiceFiscale) {
        Query query = entityManager.createQuery("FROM UtenteDto u WHERE UPPER(u.codiceFiscale) = UPPER(:codiceFiscale)");
        query.setParameter("codiceFiscale", codiceFiscale);
        return query.getResultList();
    }

    @Override
    public List<RisultatiRicercaUtenteDTO> esportaUtentiSuperUser(String codiceFiscale,String codicefiscaleOperatore) {
        List<Object[]> objectList = entityManager.createNativeQuery(
        				"select distinct  "
                		+ "	utente.nome, "
                		+ "	utente.cognome, "
                		+ "	utente.codice_fiscale, "
                		+ "	utente.desc1, "
                		+ "	utente.case1, "
                		+ "	utente.Flag_configuratore, "
                		+ "	utente.desc2, "
                		+ "	utente.case2, "
                		+ "	utente.profili, "
                		+ "	utente.solflagconfiguratore, "
                		+ "	utente.codiceruolo "
                		+ "	from "
                		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3(true, false)
                        +") as utente "
                        + " where substring(utente.codAzienda,4) in  "
                        + "	(select az.cod_azienda  "
                        + "	from auth_r_utente_visibilita_azienda as aruva, "
                        + "	auth_t_utente as ut, "
                        + "	auth_d_azienda as az "
                        + "	where  "
                        + "	aruva.id_azienda=az.id_azienda "
                        + "	and aruva.id_utente=ut.id "
                        + "	and ut.codice_fiscale=?2)")
                .setParameter(1, codiceFiscale)
                .setParameter(2, codicefiscaleOperatore)
                .getResultList();

        return mapListObjectToListRisultatiRicercaUtenteDTO(objectList);
    }

    @Override
    public List<RisultatiRicercaUtenteDTO> esportaUtentiOperatore(String codiceFiscale, List<Long> listCollocazione,String codiceFiscaleOperatore) {
        List<Object[]> objectList = entityManager.createNativeQuery(
        			"select  distinct"
                		+ "	utente.nome, "
                		+ "	utente.cognome, "
                		+ "	utente.codice_fiscale, "
                		+ "	utente.desc1, "
                		+ "	utente.case1, "
                		+ "	utente.Flag_configuratore, "
                		+ "	utente.desc2, "
                		+ "	utente.case2, "
                		+ "	utente.profili, "
                		+ "	utente.solflagconfiguratore, "
                		+ "	utente.codiceruolo "
                		+ "	from "
                		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3(false, false)
                        +") as utente "
                        + " where substring(utente.codAzienda,4) in  "
                        + "	(select az.cod_azienda  "
                        + "	from auth_r_utente_visibilita_azienda as aruva, "
                        + "	auth_t_utente as ut, "
                        + "	auth_d_azienda as az "
                        + "	where  "
                        + "	aruva.id_azienda=az.id_azienda "
                        + "	and aruva.id_utente=ut.id "
                        + "	and ut.codice_fiscale=?2)")
                .setParameter(1, codiceFiscale)
             //   .setParameter("colId", listCollocazione)
                .setParameter(2, codiceFiscaleOperatore)
                .getResultList();

        return mapListObjectToListRisultatiRicercaUtenteDTO(objectList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtenteDaSuperUserByCodiceFiscale(
            String codiceFiscale, Integer numeroPagina, Integer numeroElementi,String codiceFiscaleOperatore) {

        Integer countResults = entityManager.createNativeQuery(
        		"select  distinct"
        		+ "	utente.nome, "
        		+ "	utente.cognome, "
        		+ "	utente.codice_fiscale, "
        		+ "	utente.desc1, "
        		+ "	utente.case1, "
        		+ "	utente.Flag_configuratore, "
        		+ "	utente.desc2, "
        		+ "	utente.case2, "
        		+ "	utente.profili, "
        		+ "	utente.solflagconfiguratore, "
        		+ "	utente.codiceruolo "
        		+ "	from "
        		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3(true, true)
                        +") as utente ")
                .setParameter(1, codiceFiscale.toUpperCase())
                //.setParameter(2, codiceFiscaleOperatore.toUpperCase())
                .getResultList().size();
        
//        String prova= getSelectCaso1() +
//                "union " +
//                getSelectCaso2() +
//                "union " +
//                getSelectCaso3(true, true) +
//                "limit ?2 offset ?3 ";

        List<Object[]> objectList = entityManager.createNativeQuery(
        				"select  distinct"
                		+ "	utente.nome, "
                		+ "	utente.cognome, "
                		+ "	utente.codice_fiscale, "
                		+ "	utente.desc1, "
                		+ "	utente.case1, "
                		+ "	utente.Flag_configuratore, "
                		+ "	utente.desc2, "
                		+ "	utente.case2, "
                		+ "	utente.profili, "
                		+ "	utente.solflagconfiguratore, "
                		+ "	utente.codiceruolo "
                		+ "	from "
                		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3(true, true) +
                        ") as utente "+
                        "limit ?3 offset ?4 ")
                .setParameter(1, codiceFiscale.toUpperCase())
               // .setParameter(2, codiceFiscaleOperatore.toUpperCase())
                .setParameter(3, numeroElementi)
                .setParameter(4, (numeroPagina - 1) * numeroElementi)
                .getResultList();

        return getPaginaDTO(numeroElementi, BigInteger.valueOf(countResults), objectList,false,false);
    }

    @Override
    public PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtenteDaOperatoreByCodiceFiscale(
            String codiceFiscale, List<Long> listCollocazione, Integer numeroPagina, Integer numeroElementi,String codiceFiscaleOperatore,boolean isDelegato,boolean isTitolare) {

        Integer countResults = entityManager.createNativeQuery(
        			"select distinct "
                		+ "	utente.nome, "
                		+ "	utente.cognome, "
                		+ "	utente.codice_fiscale, "
                		+ "	utente.desc1, "
                		+ "	utente.case1, "
                		+ "	utente.Flag_configuratore, "
                		+ "	utente.desc2, "
                		+ "	utente.case2, "
                		+ "	utente.profili, "
                		+ "	utente.solflagconfiguratore, "
                		+ "	utente.codiceruolo "
                		+ "	from "
                		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3FromOperatore(false, true)
                        +") as utente "
                        + " where utente.codAzienda in  "
                        + "	(select az.cod_azienda  "
                        + "	from auth_r_utente_visibilita_azienda as aruva, "
                        + "	auth_t_utente as ut, "
                        + "	auth_d_azienda as az "
                        + "	where  "
                        + "	aruva.id_azienda=az.id_azienda "
                        + "	and aruva.id_utente=ut.id "
                        + "	and ut.codice_fiscale=?2"
                        + " and CURRENT_TIMESTAMP between coalesce(aruva.data_inizio_val, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruva.data_fine_val, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS'))) ")
                .setParameter(1, codiceFiscale.toUpperCase())
             //   .setParameter("colId", listCollocazione)
                .setParameter(2, codiceFiscaleOperatore.toUpperCase())
                .getResultList().size();
        
//        String test=getSelectCaso1() +
//                "union " +
//                getSelectCaso2() +
//                "union " +
//                getSelectCaso3(false, true) +
//                "limit ?2 offset ?3 ";

        List<Object[]> objectList = entityManager.createNativeQuery(
        				"select  distinct"
                		+ "	utente.nome, "
                		+ "	utente.cognome, "
                		+ "	utente.codice_fiscale, "
                		+ "	utente.desc1, "
                		+ "	utente.case1, "
                		+ "	utente.Flag_configuratore, "
                		+ "	utente.desc2, "
                		+ "	utente.case2, "
                		+ "	utente.profili, "
                		+ "	utente.solflagconfiguratore, "
                		+ "	utente.codiceruolo "
                		+ "	from "
                		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3FromOperatore(false, true) 
                        +") as utente "
                        + " where utente.codAzienda in  "
                        + "	(select az.cod_azienda  "
                        + "	from auth_r_utente_visibilita_azienda as aruva, "
                        + "	auth_t_utente as ut, "
                        + "	auth_d_azienda as az "
                        + "	where  "
                        + "	aruva.id_azienda=az.id_azienda "
                        + "	and aruva.id_utente=ut.id "
                        + "	and ut.codice_fiscale=?2 "
                        + " and CURRENT_TIMESTAMP between coalesce(aruva.data_inizio_val, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruva.data_fine_val, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS'))) "+
                        " limit ?3 offset ?4 ")
                .setParameter(1, codiceFiscale.toUpperCase())
                .setParameter(2, codiceFiscaleOperatore.toUpperCase())
                .setParameter(3, numeroElementi)
                .setParameter(4, (numeroPagina - 1) * numeroElementi)
                //.setParameter("colId", listCollocazione)
                .getResultList();

        return getPaginaDTO(numeroElementi, BigInteger.valueOf(countResults), objectList,isDelegato,isTitolare);
    }

    private String getSelectCaso1() {
        return "select distinct " +
                "atu.nome, " +
                "atu.cognome, " +
                "atu.codice_fiscale, " +
                "null as desc1, " +
                "null as case1, " +
                "atu.Flag_configuratore, " +
                "null as desc2, " +
                "case " +
                "when atu.data_fine_validita is null " +
                "or atu.data_fine_validita >= CURRENT_TIMESTAMP then 'Attivo' " +
                "else 'Non attivo' " +
                "end as case2, " +
                "null as profili, " +
                "null as solflagconfiguratore, " +
                "null as codiceruolo,"
                +" null as codAzienda " +
                "from auth_t_utente atu " +
                "where atu.codice_fiscale = ?1 " +
                "and " +
                "atu.id not in ( " +
                "select arru.id_utente from auth_r_ruolo_utente arru " +
                "where " +
                "CURRENT_TIMESTAMP between coalesce(arru.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( arru.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS'))) ";
    }

    private String getSelectCaso2() {
        return "select distinct atu.nome, " +
                "atu.cognome, " +
                "atu.codice_fiscale, " +
                "adr.descrizione as desc1, " +
                "null as case1, " +
                "atu.Flag_configuratore, " +
                "null as desc2, " +
                "case " +
                "when atu.data_fine_validita is null " +
                "or atu.data_fine_validita >= CURRENT_TIMESTAMP then 'Attivo' " +
                "else 'Non attivo' " +
                "end as case2, " +
                "null as profili, " +
                "null as solflagconfiguratore, " +
                "adr.codice as codiceruolo,"
                + " null as codAzienda " +
                "from auth_t_utente atu " +
                "inner join auth_r_ruolo_utente arru on arru.id_utente = atu.id " +
                "and CURRENT_TIMESTAMP between coalesce(arru.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( arru.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_d_ruolo adr on arru.id_ruolo = adr.id " +
                "and CURRENT_TIMESTAMP between coalesce(adr.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( adr.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "where atu.codice_fiscale = ?1 " +
                "and arru.id not in ( " +
                "select distinct ara.id_ruolo_utente from auth_r_abilitazione ara inner join auth_r_ruolo_utente aru on ara.id_ruolo_utente = aru.id inner join auth_t_utente ut on aru.id_utente = ut.id " +
                "where ut.codice_fiscale = ?1 and ara.id_applicazione is not null and " +
                "CURRENT_TIMESTAMP between coalesce(ara.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( ara.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS'))) ";
    }

    private String getSelectCaso3(Boolean isSuperUser, Boolean isRicerca) {
        String onlyCollocazioneStruttura = isRicerca
                ? "atc.col_cod_azienda || ' ' || atc.col_desc_azienda"
                : "atc.col_cod_azienda || '|' || atc.col_desc_azienda";
        
//        String onlyCollocazioneStruttura = isRicerca
//                ? "ada3.cod_azienda || ' ' || ada3.desc_azienda"
//                : "ada3.cod_azienda || '|' || ada3.desc_azienda";

        String collocazioneCompleta = isRicerca
                ? "atc.col_cod_azienda || ' ' || atc.col_desc_azienda || '<br>' || atc.col_codice || ' ' || atc.col_descrizione"
                : "atc.col_cod_azienda || '|' || atc.col_desc_azienda || '|' || atc.col_codice || '|' || atc.col_descrizione";
        
//        String collocazioneCompleta = isRicerca
//             //   ? "ada3.cod_azienda || ' ' || ada3.desc_azienda || '<br>' || ada3.cod_azienda|| ' ' || ada3.desc_azienda"
//        		? "ada3.cod_azienda || ' ' || ada3.desc_azienda"
//                : "ada3.cod_azienda || '|' || ada3.desc_azienda || '|' || ada3.cod_azienda|| '|' || ada3.desc_azienda";

        String collocazioneCondition = isSuperUser ? "1=1 " : "1=1 ";
       


        String applicazioneFromConfiguratore = isRicerca ? "and ada2.flag_configuratore = 'S' " : "and 1=1 ";

        String campoFunzione = !isRicerca ? "string_agg(f.fnz_codice || ' - ' || f.fnz_descrizione, ';') " : "null ";

        String campoSolFlagConfiguratore = !isRicerca ? "ada2.flag_configuratore " : "null ";

        String joinTabellaFunzione = !isRicerca
                ? "left outer join auth_r_funzionalita_tree arft on " +
                "ara.fnztree_id = arft.fnztree_id " +
                "and CURRENT_TIMESTAMP between coalesce(arft.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( arft.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "left outer join auth_t_funzionalita f on " +
                "f.fnz_id = arft.fnz_id " +
                "and CURRENT_TIMESTAMP BETWEEN coalesce(f.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce(f.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "where atu.codice_fiscale = ?1 " +
                "group by atu.nome, atu.cognome, atu.codice_fiscale, adr.descrizione, atc.col_id, atc.col_codice, atc.col_cod_azienda, atc.col_desc_azienda, atc.col_descrizione, atu.Flag_configuratore, ada2.descrizione, atu.data_fine_validita, ada2.flag_configuratore, adr.visibilita_conf, adr.codice "
                : "where atu.codice_fiscale = ?1 ";

       String caso3= "select distinct atu.nome, " +
                "atu.cognome, " +
                "atu.codice_fiscale, " +
                "adr.descrizione as desc1, " +
                "case " +
                "when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " +
                "case " +
                "when atc.flag_azienda = 'S' then (" + onlyCollocazioneStruttura + ") " +
                "else (" + collocazioneCompleta + ") " +
                "end " +
                "else null " +
                "end as case1, " +
                "atu.Flag_configuratore, " +
                "case " +
                "when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " +
                "ada2.descrizione  " +
                "else null " +
                "end as desc2, " +
                "case " +
                "when atu.data_fine_validita is null " +
                "or atu.data_fine_validita >= CURRENT_TIMESTAMP then 'Attivo' " +
                "else 'Non attivo' " +
                "end as case2, " +
                "case when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " + campoFunzione + "else null end as profili, " +
                "case when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " + campoSolFlagConfiguratore + "else null end as solflagconfiguratore, " +
                "adr.codice as codiceruolo, "
                +" atc.col_cod_azienda as codAzienda " +
                "from auth_t_utente atu " +
                "inner join auth_r_ruolo_utente arru on arru.id_utente = atu.id " +
                "and CURRENT_TIMESTAMP between coalesce(arru.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( arru.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_d_ruolo adr on arru.id_ruolo = adr.id " +
                "and CURRENT_TIMESTAMP between coalesce(adr.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( adr.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_r_abilitazione ara on ara.id_ruolo_utente = arru.id " +
                "and CURRENT_TIMESTAMP between coalesce(ara.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( ara.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_d_applicazione ada2 on ara.id_applicazione = ada2.id " + applicazioneFromConfiguratore +
                "inner join auth_r_utente_collocazione aruc on ara.utecol_id = aruc.utecol_id " +
                "and CURRENT_TIMESTAMP between coalesce(aruc.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruc.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_t_collocazione atc on " +
                "aruc.col_id = atc.col_id " +
                "and CURRENT_TIMESTAMP between coalesce(atc.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( atc.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) "
             //   + " inner join auth_r_utente_visibilita_azienda aruva on "
             //   + "	aruva.id_utente = atu.id  "
             //   + "	and CURRENT_TIMESTAMP between coalesce(aruva.data_inizio_val, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruva.data_fine_val, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) "+
               + joinTabellaFunzione;
        

        
       return caso3;
    }

    private PaginaDTO<RisultatiRicercaUtenteDTO> getPaginaDTO(Integer numeroElementi, BigInteger countResults, List<Object[]> objectList,boolean isDelegato,boolean isTitolare) {
        List<RisultatiRicercaUtenteDTO> ricercaUtenteDTOList = mapListObjectToListRisultatiRicercaUtenteDTO(objectList);
        if(isDelegato) {
        	ricercaUtenteDTOList.removeIf(f->f.getServizioOnLine().equalsIgnoreCase("Configuratore dei servizi sanitari digitali"));
        }
        
        if(isTitolare) {
        	ricercaUtenteDTOList.removeIf(f->f.getServizioOnLine().equalsIgnoreCase("Configuratore dei servizi sanitari digitali") 
        			&& f.getProfili().equalsIgnoreCase("CONF_TITOLARE - Profilo operatore Titolare"));
        }
      

        PaginaDTO<RisultatiRicercaUtenteDTO> paginaDTO = new PaginaDTO<>();
        paginaDTO.setElementi(ricercaUtenteDTOList);
        paginaDTO.setElementiTotali(countResults.longValue());
        paginaDTO.setPagineTotali((int) Math.ceil((float) paginaDTO.getElementiTotali() / numeroElementi));

        return paginaDTO;
    }

    private List<RisultatiRicercaUtenteDTO> mapListObjectToListRisultatiRicercaUtenteDTO(List<Object[]> objectList) {
        return objectList.stream().map(o -> new RisultatiRicercaUtenteDTO(
                Optional.ofNullable(o[0]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[1]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[2]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[3]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[4]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[5]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[6]).map(String.class::cast).orElse(null),
                Optional.ofNullable(o[7]).map(String.class::cast).orElse(null),
                o.length > 8 ? Optional.ofNullable(o[8]).map(String.class::cast).orElse(null) : null,
                o.length > 9 ? Optional.ofNullable(o[9]).map(String.class::cast).orElse(null) : null,
                o.length > 10 ? Optional.ofNullable(o[10]).map(String.class::cast).orElse(null) : null
        )).collect(Collectors.toList());
    }

	@Override
	public boolean checkUtente(String codiceFiscale) {

		List<Object[]> objectList = entityManager
				.createNativeQuery("select atu.codice_fiscale  " + "	from auth_t_utente atu  "
						+ "	inner join auth_r_ruolo_utente arru on atu.id=arru.id_utente  "
						+ "	inner join auth_r_abilitazione ara on ara.id_ruolo_utente=arru.id  "
						+ "	where atu.codice_fiscale = ?1")
				.setParameter(1, codiceFiscale.toUpperCase()).getResultList();
		if (objectList.size() != 0) {
			return true;
		} else {

			return false;
		}

	}
	//Costruisco un utente con i soli dati anagrafici e con il solo primo record in caso di mancata visibilita ma utente presente
	@Override
	public PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtenteDaSuperUserByCodiceFiscaleFiltrato(String codiceFiscale,
			Integer numeroPagina, Integer numeroElementi, String codiceFiscaleOperatore) {

        Integer countResults = entityManager.createNativeQuery(
        		"select  distinct"
        		+ "	utente.nome, "
        		+ "	utente.cognome, "
        		+ "	utente.codice_fiscale, "
        		+ "	null as test1, "
        		+ "	null as test2, "
        		+ "	null as test4, "
        		+ "	null as test5, "
        		+ "	null as test6, "
        		+ "	null as test7, "
        		+ "	null as test8, "
        		+ "	null as test9 "
        		+ "	from "
        		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3(true, true)
                        +") as utente "
                    +"limit 1")
                .setParameter(1, codiceFiscale.toUpperCase())
               // .setParameter(2, codiceFiscaleOperatore.toUpperCase())
                .getResultList().size();
        

        List<Object[]> objectList = entityManager.createNativeQuery(
        				"select  distinct"
                		+ "	utente.nome, "
                		+ "	utente.cognome, "
                		+ "	utente.codice_fiscale, "
                		+ "	null as test1, "
                		+ "	null as test2, "
                		+ "	null as test4, "
                		+ "	null as test5, "
                		+ "	null as test6, "
                		+ "	null as test7, "
                		+ "	null as test8, "
                		+ "	null as test9 "
                		+ "	from "
                		+ "	("+
                getSelectCaso1() +
                        "union " +
                        getSelectCaso2() +
                        "union " +
                        getSelectCaso3(true, true) +
                        ") as utente "
                        +"limit 1")
                .setParameter(1, codiceFiscale.toUpperCase())
                //.setParameter(2, codiceFiscaleOperatore.toUpperCase())
                //.setParameter(2, numeroElementi)
                //.setParameter(3, (numeroPagina - 1) * numeroElementi)
                .getResultList();

        return getPaginaDTO(numeroElementi, BigInteger.valueOf(countResults), objectList,false,false);
    }

	@Override
	public List<UtenteDto> findByIdRuoloAndIdCollocazione(Long idRuolo, Long idCollocazione) {
		return entityManager.createQuery(
				"Select u from UtenteDto u join u.ruoloUtenteList ru join u.utenteCollocazioneList uc " +
				"where ru.ruoloDto.id = :idRuolo " +
				"and uc.collocazioneDto.colId = :idCollocazione " +
				"AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ", 
                UtenteDto.class)
				.setParameter("idRuolo", idRuolo)
				.setParameter("idCollocazione", idCollocazione)
				.getResultList();
				
	}

	@Override
	public Long countByIdRuoloAndIdCollocazione(long idRuolo, long idCollocazione) {
		return entityManager.createQuery(
				"Select count(u) from UtenteDto u join u.ruoloUtenteList ru join u.utenteCollocazioneList uc " +
				"where ru.ruoloDto.id = :idRuolo " +
				"and uc.collocazioneDto.colId = :idCollocazione " +
				"AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ", 
                Long.class)
				.setParameter("idRuolo", idRuolo)
				.setParameter("idCollocazione", idCollocazione)
				.getSingleResult();
	}

	@Override
	public List<UtenteDto> findByIds(List<Long> ids) {
		return entityManager.createQuery(
				"Select u from UtenteDto u " +
				"where u.id IN (:ids) " +
				"AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) ", 
                UtenteDto.class)
				.setParameter("ids", ids)
				.getResultList();
	}

	@Override
	public List<UtenteDto> findByIdRuoloAndIdAzienda(Long idRuolo, Long idAzienda) {
		return entityManager.createQuery(
				"Select u from UtenteDto u join u.ruoloUtenteList ru join u.utenteCollocazioneList uc " +
				"where ru.ruoloDto.id = :idRuolo " +
				"and uc.collocazioneDto.colCodAzienda IN " +
				"(select c.colCodAzienda from CollocazioneDto c where colId = :idAzienda and flagAzienda = 'S' " +
				"AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) " +
                "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL))) " +
				"AND ((CURRENT_TIMESTAMP BETWEEN u.dataInizioValidita AND u.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > u.dataInizioValidita AND u.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < u.dataFineValidita AND u.dataInizioValidita IS NULL) " +
                "OR (u.dataInizioValidita IS NULL AND u.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) " +
                "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) " +
                "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) " +
                "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > ru.ruoloDto.dataInizioValidita AND ru.ruoloDto.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < ru.ruoloDto.dataFineValidita AND ru.ruoloDto.dataInizioValidita IS NULL) " +
                "OR (ru.ruoloDto.dataInizioValidita IS NULL AND ru.ruoloDto.dataFineValidita IS NULL)) ", 
                UtenteDto.class)
				.setParameter("idRuolo", idRuolo)
				.setParameter("idAzienda", idAzienda)
				.getResultList();
				
	}
	
    private String getSelectCaso3FromOperatore(Boolean isSuperUser, Boolean isRicerca) {
        String onlyCollocazioneStruttura = isRicerca
                ? "atc.col_cod_azienda || ' ' || atc.col_desc_azienda"
                : "atc.col_cod_azienda || '|' || atc.col_desc_azienda";
        
//        String onlyCollocazioneStruttura = isRicerca
//                ? "ada3.cod_azienda || ' ' || ada3.desc_azienda"
//                : "ada3.cod_azienda || '|' || ada3.desc_azienda";

        String collocazioneCompleta = isRicerca
                ? "atc.col_cod_azienda || ' ' || atc.col_desc_azienda || '<br>' || atc.col_codice || ' ' || atc.col_descrizione"
                : "atc.col_cod_azienda || '|' || atc.col_desc_azienda || '|' || atc.col_codice || '|' || atc.col_descrizione";
        
//        String collocazioneCompleta = isRicerca
//             //   ? "ada3.cod_azienda || ' ' || ada3.desc_azienda || '<br>' || ada3.cod_azienda|| ' ' || ada3.desc_azienda"
//        		? "ada3.cod_azienda || ' ' || ada3.desc_azienda"
//                : "ada3.cod_azienda || '|' || ada3.desc_azienda || '|' || ada3.cod_azienda|| '|' || ada3.desc_azienda";

        String collocazioneCondition = isSuperUser ? "1=1 " : "1=1 ";
       


        String applicazioneFromConfiguratore = isRicerca ? "and ada2.flag_configuratore = 'S' " : "and 1=1 ";

        String campoFunzione = isRicerca ? "string_agg(f.fnz_codice || ' - ' || f.fnz_descrizione, ';') " : "null ";

        String campoSolFlagConfiguratore = !isRicerca ? "ada2.flag_configuratore " : "null ";

        String joinTabellaFunzione = isRicerca
                ? "left outer join auth_r_funzionalita_tree arft on " +
                "ara.fnztree_id = arft.fnztree_id " +
                "and CURRENT_TIMESTAMP between coalesce(arft.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( arft.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "left outer join auth_t_funzionalita f on " +
                "f.fnz_id = arft.fnz_id " +
                "and CURRENT_TIMESTAMP BETWEEN coalesce(f.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce(f.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "where atu.codice_fiscale = ?1 " +
                "group by atu.nome, atu.cognome, atu.codice_fiscale, adr.descrizione, atc.col_id, atc.col_codice, atc.col_cod_azienda, atc.col_desc_azienda, atc.col_descrizione, atu.Flag_configuratore, ada2.descrizione, atu.data_fine_validita, ada2.flag_configuratore, adr.visibilita_conf, adr.codice "
                : "where atu.codice_fiscale = ?1 ";

       String caso3= "select distinct atu.nome, " +
                "atu.cognome, " +
                "atu.codice_fiscale, " +
                "adr.descrizione as desc1, " +
                "case " +
                "when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " +
                "case " +
                "when atc.flag_azienda = 'S' then (" + onlyCollocazioneStruttura + ") " +
                "else (" + collocazioneCompleta + ") " +
                "end " +
                "else null " +
                "end as case1, " +
                "atu.Flag_configuratore, " +
                "case " +
                "when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " +
                "ada2.descrizione  " +
                "else null " +
                "end as desc2, " +
                "case " +
                "when atu.data_fine_validita is null " +
                "or atu.data_fine_validita >= CURRENT_TIMESTAMP then 'Attivo' " +
                "else 'Non attivo' " +
                "end as case2, " +
                "case when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " + campoFunzione + "else null end as profili, " +
                "case when ((adr.visibilita_conf = 'S' or adr.visibilita_conf is null) and " + collocazioneCondition + ") then " + campoSolFlagConfiguratore + "else null end as solflagconfiguratore, " +
                "adr.codice as codiceruolo, "
                +" atc.col_cod_azienda as codAzienda " +
                "from auth_t_utente atu " +
                "inner join auth_r_ruolo_utente arru on arru.id_utente = atu.id " +
                "and CURRENT_TIMESTAMP between coalesce(arru.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( arru.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_d_ruolo adr on arru.id_ruolo = adr.id " +
                "and CURRENT_TIMESTAMP between coalesce(adr.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( adr.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_r_abilitazione ara on ara.id_ruolo_utente = arru.id " +
                "and CURRENT_TIMESTAMP between coalesce(ara.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( ara.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_d_applicazione ada2 on ara.id_applicazione = ada2.id " + applicazioneFromConfiguratore +
                "inner join auth_r_utente_collocazione aruc on ara.utecol_id = aruc.utecol_id " +
                "and CURRENT_TIMESTAMP between coalesce(aruc.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruc.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) " +
                "inner join auth_t_collocazione atc on " +
                "aruc.col_id = atc.col_id " +
                "and CURRENT_TIMESTAMP between coalesce(atc.data_inizio_validita, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( atc.data_fine_validita, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) "
             //   + " inner join auth_r_utente_visibilita_azienda aruva on "
             //   + "	aruva.id_utente = atu.id  "
             //   + "	and CURRENT_TIMESTAMP between coalesce(aruva.data_inizio_val, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruva.data_fine_val, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')) "+
               + joinTabellaFunzione;
        

        
       return caso3;
    }


	
}
