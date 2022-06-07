/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.TreeFunzionalitaLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.TreeFunzionalitaDto;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class TreeFunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<TreeFunzionalitaDto, Long> implements TreeFunzionalitaLowDao {


    @Override
    public List<TreeFunzionalitaDto> findFunzionalitaForTokenInfo2(String codiceFiscaleRichiedente, String ruoloRichiedente,
                                                               String codiceApplicazione, String codiceCollocazione, String codiceAzienda){

        Query query = entityManager.createNativeQuery("select funtree.fnztree_id, funtree.fnz_id, funtree.fnztree_id_parent, " +
                " funtree.data_inizio_validita " +
                " from auth_r_funzionalita_tree funtree " +
                "INNER join auth_t_funzionalita fun on (fun.fnz_id = funtree.fnz_id) " +
//                "INNER join auth_d_applicazione ap on (fun.applicazione_id = ap.id) " +
                "INNER join auth_r_abilitazione abi on (funtree.fnztree_id = abi.fnztree_id) " +
                "INNER join auth_r_ruolo_utente ruoute on (abi.id_ruolo_utente = ruoute.id) " +
                "INNER join auth_r_utente_collocazione utecoll on (abi.utecol_id = utecoll.utecol_id) " +
                "INNER join auth_t_utente ute on (utecoll.ute_id = ute.id) " +
                "INNER join auth_t_collocazione col on (utecoll.col_id = col.col_id) " +
                "INNER join auth_t_utente ute1 on (ruoute.id_utente = ute1.id) " +
                "INNER join auth_d_ruolo ruo on (ruoute.id_ruolo = ruo.id) " +
                " where " +
                "ute.codice_fiscale = :codice_fiscale AND " +
                "ruo.codice = :codice_ruolo AND " +
//                "ap.codice = :codice_applicazione and " +
                "col.col_codice = :codice_coll and " +
                "col.col_cod_azienda = :codice_azienda and "+
                "((:sysdate > ruoute.data_inizio_validita and ruoute.data_fine_validita is null) " +
                "or (:sysdate between ruoute.data_inizio_validita and ruoute.data_fine_validita)) AND " +
                "((:sysdate > abi.data_inizio_validita and abi.data_fine_validita is null) " +
                "or (:sysdate between abi.data_inizio_validita and abi.data_fine_validita)) AND " +
                "((:sysdate > fun.data_inizio_validita and fun.data_fine_validita is null) " +
                "or (:sysdate between fun.data_inizio_validita and fun.data_fine_validita)) ");


        query.setParameter("codice_fiscale", codiceFiscaleRichiedente);
        query.setParameter("codice_ruolo", ruoloRichiedente);
//        query.setParameter("codice_applicazione", codiceApplicazione);
        query.setParameter("codice_coll", codiceCollocazione);
        query.setParameter("codice_azienda", codiceAzienda);
        query.setParameter("sysdate", Utils.sysdate());


        return getResultList(query, TreeFunzionalitaDto.class);
    }

    @Override
    public List<TreeFunzionalitaDto> findFunzionalitaSons(TreeFunzionalitaDto treeFunzionalitaDto){

        Query query = entityManager.createNativeQuery("WITH RECURSIVE findSons AS (" +
                "       SELECT" +
                "             tree.fnztree_id, tree.fnz_id, tree.fnztree_id_parent, " +
                " tree.data_inizio_validita " +
                "       FROM" +
                "             auth_r_funzionalita_tree tree " +
                "       WHERE" +
                "       ((:sysdate > tree.data_inizio_validita and tree.data_fine_validita is null) or" +
                "             (:sysdate between tree.data_inizio_validita and tree.data_fine_validita)) AND" +
                "             fnztree_id = :idFunzione" +
                "       UNION" +
                "             SELECT" +
                "                    t.fnztree_id, t.fnz_id, t.fnztree_id_parent, " +
                " t.data_inizio_validita " +
                "             FROM" +
                "                    auth_r_funzionalita_tree t" +
                "             INNER JOIN findSons s ON s.fnztree_id = t.fnztree_id_parent  where " +
                "             ((:sysdate > t.data_inizio_validita and t.data_fine_validita is null) or " +
                "                             (:sysdate between t.data_inizio_validita and t.data_fine_validita))" +
                ") SELECT" +
                "       *" +
                "FROM" +
                "       findSons;");

        query.setParameter("idFunzione", treeFunzionalitaDto.getIdTreeFunzione());
        query.setParameter("sysdate", Utils.sysdate());



        return getResultList(query, TreeFunzionalitaDto.class);
    }

    @Override
    public List<TreeFunzionalitaDto> findFunzionalitaParents(TreeFunzionalitaDto treeFunzionalitaDto){

        Query query = entityManager.createNativeQuery("WITH RECURSIVE findParent AS (" +
                "       SELECT" +
                "             tree.fnztree_id, tree.fnz_id, tree.fnztree_id_parent, " +
                " tree.data_inizio_validita " +
                "       FROM" +
                "             auth_r_funzionalita_tree tree" +
                "       where" +
                "       ((:sysdate > tree.data_inizio_validita and tree.data_fine_validita is null) or" +
                "             (:sysdate between tree.data_inizio_validita and tree.data_fine_validita)) AND" +
                "             fnztree_id = :idFunzione" +
                "       UNION" +
                "             SELECT" +
                "                    t.fnztree_id, " +
                "                    t.fnz_id," +
                "                    t.fnztree_id_parent," +
                "                    t.data_inizio_validita" +
                "                    " +
                "             FROM" +
                "                    auth_r_funzionalita_tree t" +
                "             INNER JOIN findParent s ON s.fnztree_id_parent = t.fnztree_id where " +
                "             ((:sysdate > t.data_inizio_validita and t.data_fine_validita is null) or " +
                "                             (:sysdate between t.data_inizio_validita and t.data_fine_validita))" +
                ") SELECT" +
                "       *" +
                "FROM" +
                "       findParent;");

        query.setParameter("idFunzione", treeFunzionalitaDto.getIdTreeFunzione());
        query.setParameter("sysdate", Utils.sysdate());



        return getResultList(query, TreeFunzionalitaDto.class);
    }

}
