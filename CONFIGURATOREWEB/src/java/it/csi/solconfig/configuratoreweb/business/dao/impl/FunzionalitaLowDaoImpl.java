/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaTreeDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.ProfiloDTO;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class FunzionalitaLowDaoImpl extends EntityBaseLowDaoImpl<FunzionalitaDto, Long> implements FunzionalitaLowDao {

    public static String CODICE_TIPO_PROFILO = "PROF";
    public static String CODICE_TIPO_FUNZIONALITA = "FUNZ";

    public static String CODICE_TIPO_FUNZIONALITA_APP = "APP";

    @Override
    public List<FunzionalitaDto> findProfili(FunzionalitaDto funzionalitaDto, Boolean flag) {
        String tabella = getTabName(funzionalitaDto);
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
        if (funzionalitaDto.getCodiceFunzione() != null && !funzionalitaDto.getCodiceFunzione().isEmpty()) {
            queryBuilder.append("and UPPER(fnz_codice) like UPPER(:codice) ");
        }
        if (funzionalitaDto.getDescrizioneFunzione() != null && !funzionalitaDto.getDescrizioneFunzione().isEmpty()) {
            queryBuilder.append("and UPPER(fnz_descrizione) like UPPER(:descrizione) ");

        }
        if (funzionalitaDto.getApplicazioneDto().getId() != null) {
            queryBuilder.append("and applicazione_id = :applicazione_id ");

        }
        queryBuilder.append("and tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        if (flag != null) {
            if (flag == true) {

                queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
                queryBuilder.append("or (fz.dataFineValidita is null ");
                queryBuilder.append("and :data >= fz.dataInizioValidita)) ");

            }
            if (flag == false) {

                queryBuilder.append("and (( :data < fz.dataInizioValidita or :data > fz.dataFineValidita) ");
                queryBuilder.append("or (fz.dataFineValidita is null ");
                queryBuilder.append("and fz.dataInizioValidita is null)) ");
            }
        }
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        if (funzionalitaDto.getCodiceFunzione() != null && !funzionalitaDto.getCodiceFunzione().isEmpty()) {
            query.setParameter("codice", funzionalitaDto.getCodiceFunzione() + '%');
        }
        if (funzionalitaDto.getDescrizioneFunzione() != null && !funzionalitaDto.getDescrizioneFunzione().isEmpty()) {
            query.setParameter("descrizione", funzionalitaDto.getDescrizioneFunzione() + '%');
        }
        if (funzionalitaDto.getApplicazioneDto().getId() != null) {
            query.setParameter("applicazione_id", funzionalitaDto.getApplicazioneDto().getId());
        }
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_PROFILO);
        if (flag != null && (flag == true || flag == false)) {
            query.setParameter("data", data);
        }
        return query.getResultList();

    }

    @Override
    public List<FunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto) {

        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
        queryBuilder.append("and applicazione_id = :id ");
        queryBuilder.append("and tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append("or (fz.dataFineValidita is null ");
        queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        query.setParameter("id", applicazioneDto.getId());
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_FUNZIONALITA);
        query.setParameter("data", data);

        return query.getResultList();
    }

    @Override
    public List<ProfiloDTO> findProfiliByIdApplicazione(Long idApplicazione, boolean isOPListaProfiliCompleta, boolean superUser) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.ProfiloDTO(" +
                "f.idFunzione, f.descrizioneFunzione, f.codiceFunzione ) " +
                "FROM FunzionalitaDto f JOIN f.tipoFunzionalitaDto tf " +
                "WHERE f.applicazioneDto.id = :idApplicazione AND tf.codiceTipoFunzione = :codiceTipoFunzionalita ");
        
        if(!superUser) {
        	queryBuilder.append("AND f.codiceFunzione <> :codiceSuperUser ");
        }
        if(!isOPListaProfiliCompleta){
            queryBuilder.append("AND f.codiceFunzione <> :codiceTitolare ");
        }

        queryBuilder.append(" AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) " +
                "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN tf.dataInizioValidita AND tf.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > tf.dataInizioValidita AND tf.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < tf.dataFineValidita AND tf.dataInizioValidita IS NULL) " +
                "OR (tf.dataInizioValidita IS NULL AND tf.dataFineValidita IS NULL)) ");

        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult, ProfiloDTO.class);

        query.setParameter("idApplicazione", idApplicazione);
        query.setParameter("codiceTipoFunzionalita", CODICE_TIPO_PROFILO);
        if(!superUser) {
        	query.setParameter("codiceSuperUser", FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());
        }

        if(!isOPListaProfiliCompleta){
            query.setParameter("codiceTitolare", FunzionalitaEnum.CONF_TITOLARE_PROF.getValue());
        }

        return query.getResultList();
    }

    @Override
    public List<FunzionalitaDto> findFunzionalitaApplicazione(ApplicazioneDto applicazioneDto) {

        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
        queryBuilder.append("and applicazione_id = :id ");
        queryBuilder.append("and tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append("and (( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append("or (fz.dataFineValidita is null ");
        queryBuilder.append("and :data >= fz.dataInizioValidita)) ");
        
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        query.setParameter("id", applicazioneDto.getId());
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_FUNZIONALITA);
        query.setParameter("data", data);
        
        List<FunzionalitaDto> listToReturn = query.getResultList();

        return listToReturn;
    }

    @Override
    public List<FunzionalitaDto> findFunzionalita(FunzionalitaDto funzionalitaDto) {

        String tabella = getTabName(new FunzionalitaDto());
        Timestamp data = Utils.sysdate();
        StringBuilder queryBuilder = new StringBuilder("from " + tabella + " fz WHERE 1=1 ");
        
        if (funzionalitaDto.getIdFunzione() != null)
        	queryBuilder.append(" and lower(idFunzione = :id ");
        if ((funzionalitaDto.getCodiceFunzione() != null) && (!funzionalitaDto.getCodiceFunzione().isEmpty()))
        	queryBuilder.append(" and lower(codiceFunzione) LIKE CONCAT('%', :codice, '%') ");
        if ((funzionalitaDto.getDescrizioneFunzione() != null) && (!funzionalitaDto.getDescrizioneFunzione().isEmpty()))
        	queryBuilder.append(" and descrizioneFunzione) LIKE CONCAT('%', :descrizione, '%') ");
        if (funzionalitaDto.getApplicazioneDto() != null && funzionalitaDto.getApplicazioneDto().getId() != null)
        	queryBuilder.append(" and applicazioneDto.id = :idApplicazione ");
        
        queryBuilder.append(" and tipoFunzionalitaDto.codiceTipoFunzione = :fnz_tipo_codice ");
        queryBuilder.append(" and ( ");
        queryBuilder.append(" 	( :data between fz.dataInizioValidita and fz.dataFineValidita) ");
        queryBuilder.append(" 	or (fz.dataFineValidita is null and :data >= fz.dataInizioValidita) ");
        queryBuilder.append(" ) ");
        
        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult);
        
        if (funzionalitaDto.getIdFunzione() != null)
        	query.setParameter("id", funzionalitaDto.getIdFunzione());
        if ((funzionalitaDto.getCodiceFunzione() != null) && (!funzionalitaDto.getCodiceFunzione().isEmpty()))
        	query.setParameter("codice", funzionalitaDto.getCodiceFunzione().toLowerCase());
        if ((funzionalitaDto.getDescrizioneFunzione() != null) && (!funzionalitaDto.getDescrizioneFunzione().isEmpty()))
        	query.setParameter("descrizione", funzionalitaDto.getDescrizioneFunzione().toLowerCase());
        if (funzionalitaDto.getApplicazioneDto() != null && funzionalitaDto.getApplicazioneDto().getId() != null)
        	query.setParameter("idApplicazione", funzionalitaDto.getApplicazioneDto().getId());
        
        query.setParameter("fnz_tipo_codice", CODICE_TIPO_FUNZIONALITA);
        query.setParameter("data", Utils.sysdate());
        
        List<FunzionalitaDto> listToReturn = query.getResultList();

        return listToReturn;
    }

    @Override
    public FunzionalitaDTO findFunzionalitaById(Long idFunzionalita) {
        List<FunzionalitaDTO> funzionalitaDTOList = entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaDTO(" +
                        "f.idFunzione, f.descrizioneFunzione, f.codiceFunzione ) " +
                        "FROM FunzionalitaDto f JOIN f.tipoFunzionalitaDto tf " +
                        "WHERE f.idFunzione = :idFunzionalita AND tf.codiceTipoFunzione = :codiceTipoFunzionalita " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) " +
                        "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN tf.dataInizioValidita AND tf.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > tf.dataInizioValidita AND tf.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < tf.dataFineValidita AND tf.dataInizioValidita IS NULL) " +
                        "OR (tf.dataInizioValidita IS NULL AND tf.dataFineValidita IS NULL)) ", FunzionalitaDTO.class)
                .setParameter("idFunzionalita", idFunzionalita)
                .setParameter("codiceTipoFunzionalita", CODICE_TIPO_FUNZIONALITA)
                .getResultList();

        return !funzionalitaDTOList.isEmpty() ? funzionalitaDTOList.get(0) : null;
    }

    @Override
    public FunzionalitaDTO findFunzionalitaByCodice(String codice) {
        List<FunzionalitaDTO> funzionalitaDTOList = entityManager.createQuery(
                "SELECT new it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaDTO(" +
                        "f.idFunzione, f.descrizioneFunzione, f.codiceFunzione ) " +
                        "FROM FunzionalitaDto f JOIN f.tipoFunzionalitaDto tf " +
                        "WHERE f.codiceFunzione = :codice AND tf.codiceTipoFunzione = :codiceTipoFunzionalita " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) " +
                        "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) " +
                        "AND ((CURRENT_TIMESTAMP BETWEEN tf.dataInizioValidita AND tf.dataFineValidita) " +
                        "OR (CURRENT_TIMESTAMP > tf.dataInizioValidita AND tf.dataFineValidita IS NULL) " +
                        "OR (CURRENT_TIMESTAMP < tf.dataFineValidita AND tf.dataInizioValidita IS NULL) " +
                        "OR (tf.dataInizioValidita IS NULL AND tf.dataFineValidita IS NULL)) ", FunzionalitaDTO.class)
                .setParameter("codice", codice)
                .setParameter("codiceTipoFunzionalita", CODICE_TIPO_FUNZIONALITA)
                .getResultList();

        return !funzionalitaDTOList.isEmpty() ? funzionalitaDTOList.get(0) : null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<FunzionalitaTreeDTO> findAllFunzionalitaByIdProfilo(Long idProfilo) {
        List<Object[]> objectList = entityManager.createNativeQuery(
                "with recursive findSons as (" +
                        "    select tree.fnztree_id," +
                        "           tree.fnz_id," +
                        "           tree.fnztree_id_parent," +
                        "           tree.data_inizio_validita," +
                        "           tree2.fnz_id as fnz_id_parent" +
                        "    from auth_r_funzionalita_tree tree" +
                        "             left join auth_r_funzionalita_tree tree2 on tree.fnztree_id_parent = tree2.fnztree_id" +
                        "    where ((CURRENT_TIMESTAMP > tree.data_inizio_validita" +
                        "        and tree.data_fine_validita is null)" +
                        "        or (CURRENT_TIMESTAMP between tree.data_inizio_validita and tree.data_fine_validita))" +
                        "      and tree.data_cancellazione is null and tree.fnz_id = ?1 " +
                        "    union" +
                        "    select t.fnztree_id," +
                        "           t.fnz_id," +
                        "           t.fnztree_id_parent," +
                        "           t.data_inizio_validita," +
                        "           t2.fnz_id as fnz_id_parent" +
                        "    from auth_r_funzionalita_tree t" +
                        "             left join auth_r_funzionalita_tree t2 on t.fnztree_id_parent = t2.fnztree_id" +
                        "             inner join findSons s on s.fnztree_id = t.fnztree_id_parent" +
                        " where ((CURRENT_TIMESTAMP > t.data_inizio_validita" +
                        " and t.data_fine_validita is null)" +
                        " or (CURRENT_TIMESTAMP between t.data_inizio_validita and t.data_fine_validita))" +
                        " and t.data_cancellazione is null"+
                        ") " +
                        "select findSons.fnz_id as id, findSons.fnz_id_parent as parent " +
                        "from findSons")
                .setParameter(1, idProfilo)
                .getResultList();

        return objectList.stream().map(o -> new FunzionalitaTreeDTO(
                Optional.ofNullable(o[0]).map(BigInteger.class::cast).map(BigInteger::longValue).orElse(null),
                Optional.ofNullable(o[1]).map(BigInteger.class::cast).map(BigInteger::longValue).orElse(null)
        )).collect(Collectors.toList());
    }
    
    public List<ProfiloDTO> findProfiliByIdApplicazioneAndRuolo(Long idApplicazione, boolean isOPListaProfiliCompleta,Long idRuolo, boolean superUser) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.ProfiloDTO(" +
                "f.idFunzione, f.descrizioneFunzione, f.codiceFunzione ) " +
                "FROM RuoloProfilo p JOIN p.funzionalita f JOIN f.tipoFunzionalitaDto tf JOIN p.ruolo r " +
                "WHERE f.applicazioneDto.id = :idApplicazione AND tf.codiceTipoFunzione = :codiceTipoFunzionalita "
                + "AND r.id=:idRuolo " );

        if(!superUser) {
        	queryBuilder.append("AND f.codiceFunzione <> :codiceSuperUser ");
        }
        
        if(!isOPListaProfiliCompleta){
            queryBuilder.append("AND f.codiceFunzione <> :codiceTitolare ");
        }

        queryBuilder.append(" AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) " +
                "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN tf.dataInizioValidita AND tf.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > tf.dataInizioValidita AND tf.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < tf.dataFineValidita AND tf.dataInizioValidita IS NULL) " +
                "OR (tf.dataInizioValidita IS NULL AND tf.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) " +
                "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN p.dataInizioValidita AND p.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > p.dataInizioValidita AND p.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < p.dataFineValidita AND p.dataInizioValidita IS NULL) " +
                "OR (p.dataInizioValidita IS NULL AND p.dataFineValidita IS NULL)) ");

        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult, ProfiloDTO.class);

        query.setParameter("idApplicazione", idApplicazione);
        query.setParameter("codiceTipoFunzionalita", CODICE_TIPO_PROFILO);
        if(!superUser) {
        	query.setParameter("codiceSuperUser", FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());
        }
        query.setParameter("idRuolo", idRuolo);

        if(!isOPListaProfiliCompleta){
            query.setParameter("codiceTitolare", FunzionalitaEnum.CONF_TITOLARE_PROF.getValue());
        }

        return query.getResultList();
    }
    
    public List<ProfiloDTO> findProfiliByIdApplicazioneAndCollocazione(Long idApplicazione, boolean isOPListaProfiliCompleta,Long idCollocazione, boolean superUser) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.ProfiloDTO(" +
                "f.idFunzione, f.descrizioneFunzione, f.codiceFunzione ) " +
                "FROM ColAziendaProfilo p JOIN p.funzionalita f JOIN f.tipoFunzionalitaDto tf JOIN p.collocazione r " +
                "WHERE f.applicazioneDto.id = :idApplicazione AND tf.codiceTipoFunzione = :codiceTipoFunzionalita "
                + "AND r.colId=:idCollocazione " );

        if(!superUser) {
        	queryBuilder.append("AND f.codiceFunzione <> :codiceSuperUser ");
        }
        
        if(!isOPListaProfiliCompleta){
            queryBuilder.append("AND f.codiceFunzione <> :codiceTitolare ");
        }

        queryBuilder.append(" AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) " +
                "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN tf.dataInizioValidita AND tf.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > tf.dataInizioValidita AND tf.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < tf.dataFineValidita AND tf.dataInizioValidita IS NULL) " +
                "OR (tf.dataInizioValidita IS NULL AND tf.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) " +
                "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) " +
                "AND ((CURRENT_TIMESTAMP BETWEEN p.dataInizioValidita AND p.dataFineValidita) " +
                "OR (CURRENT_TIMESTAMP > p.dataInizioValidita AND p.dataFineValidita IS NULL) " +
                "OR (CURRENT_TIMESTAMP < p.dataFineValidita AND p.dataInizioValidita IS NULL) " +
                "OR (p.dataInizioValidita IS NULL AND p.dataFineValidita IS NULL)) ");

        String queryResult = queryBuilder.toString();
        Query query = entityManager.createQuery(queryResult, ProfiloDTO.class);

        query.setParameter("idApplicazione", idApplicazione);
        query.setParameter("codiceTipoFunzionalita", CODICE_TIPO_PROFILO);
        if(!superUser) {
        	query.setParameter("codiceSuperUser", FunzionalitaEnum.SUPERUSERCONF_PROF.getValue());
        }
        query.setParameter("idCollocazione", idCollocazione);

        if(!isOPListaProfiliCompleta){
            query.setParameter("codiceTitolare", FunzionalitaEnum.CONF_TITOLARE_PROF.getValue());
        }

        return query.getResultList();
    }
    

    /**
     * @param className
     * @return
     */
    public String getTabName(Object className) {
        return className.getClass().getName();
    }
    
    

}
