/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneTipoDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
public class RuoloLowDaoImpl extends CatalogoBaseLowDaoImpl<RuoloDto, Long> implements RuoloLowDao {

	@Override
	public Collection<RuoloDto> findByUtenteCodiceFiscale(String codiceFiscale) {
		Query query = entityManager.createQuery(
				"SELECT r FROM RuoloDto r, RuoloUtenteDto ru, UtenteDto u WHERE r.id=ru.ruoloDto.id AND u.id=ru.utenteDto.id AND u.codiceFiscale=:codiceFiscale AND :data BETWEEN ru.dataInizioValidita AND ru.dataFineValidita");
		query.setParameter("codiceFiscale", codiceFiscale);
		query.setParameter("data", Utils.sysdate());
		return query.getResultList();
	}

	@Override
	public List<RuoloDTO> findAll(boolean superUser) {
		return entityManager
				.createQuery("SELECT new it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO("
						+ "r.id, CONCAT(r.codice, ' - ', r.descrizione))" + "FROM RuoloDto r "
						+ "WHERE (r.visibilitaConf <> 'N' OR r.visibilitaConf IS NULL) "
						+ (superUser ? "" : "AND r.flagConfiguratore = 'S' ")
						+ "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
						+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
						+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
						+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) ", RuoloDTO.class)
				.getResultList();
	}

	@Override
	public List<RuoloDTO> findAllNoFilter() {
		return entityManager
				.createQuery("SELECT new it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO("
						+ "r.id, CONCAT(r.codice, ' - ', r.descrizione))" + "FROM RuoloDto r " + "WHERE "
						+ "  ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
						+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
						+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
						+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) ", RuoloDTO.class)
				.getResultList();
	}

	@Override
	public List<RuoloDTO> findByIdUtente(Long idUtente) {
		return entityManager.createQuery("SELECT new it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO("
				+ "r.id, CONCAT(r.codice, ' - ', r.descrizione), "
				+ "CASE WHEN ((r.flagConfiguratore = 'S' and (r.visibilitaConf <> 'N' OR r.visibilitaConf IS NULL)) "
				+ "AND ru.colFonteId IS NULL) THEN true ELSE false END) "
				+ "FROM RuoloUtenteDto ru JOIN ru.ruoloDto r JOIN ru.utenteDto u "
				+ "WHERE (r.visibilitaConf <> 'N' OR r.visibilitaConf IS NULL) AND u.id = :idUtente "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
				+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) "
				+ "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) ", RuoloDTO.class)
				.setParameter("idUtente", idUtente).getResultList();
	}

	@Override
	public List<RuoloDTO> ricercaRuoliNonConfiguratore(boolean superUser) {
		return entityManager
				.createQuery("SELECT new it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO("
						+ "r.id, CONCAT(r.codice, ' - ', r.descrizione))" + "FROM RuoloDto r " + "WHERE "
						+ (superUser ? "" : "(r.flagConfiguratore <> 'S' OR r.flagConfiguratore IS NULL) AND ")
						+ "((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
						+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
						+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
						+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) ", RuoloDTO.class)
				.getResultList();
	}

	@Override
	public List<RuoloDto> findRuoli(RuoloDto ruoloDto, String flagAttivo, String flagNonAttivo,
			Boolean flagConfiguratore) {

		StringBuilder queryBuilder = new StringBuilder("from " + ruoloDto.getClass().getName() + " t" + " WHERE 1=1");

		if (ruoloDto.getCodice() != null && !ruoloDto.getCodice().isEmpty()) {
			queryBuilder.append("AND UPPER(t.codice) like UPPER(:codice) ");
		}
		if (ruoloDto.getDescrizione() != null && !ruoloDto.getDescrizione().isEmpty()) {
			queryBuilder.append("AND UPPER(t.descrizione) like UPPER(:descrizione) ");
		}

		/*
		 * filtri stato attivo
		 */
		if (flagNonAttivo == null && "true".equalsIgnoreCase(flagAttivo)) {
			queryBuilder.append("and ((:data between t.dataInizioValidita and t.dataFineValidita) ");
			queryBuilder.append("or (t.dataFineValidita is null ");
			queryBuilder.append("and :data >= dataInizioValidita)) ");
			// stato non attivo
		}
		if (flagAttivo == null && "true".equalsIgnoreCase(flagNonAttivo)) {
			queryBuilder.append("and ((:data < t.dataInizioValidita or :data > t.dataFineValidita) ");
			queryBuilder.append("or (t.dataInizioValidita is null ");
			queryBuilder.append("and t.dataFineValidita is null)) ");
		}

		// filtro inserito da configuratore
		if (flagConfiguratore != null && flagConfiguratore == true) {
			queryBuilder.append("AND t.flagConfiguratore = :flagConfiguratore");
		}

		// ruoli visibili da configuratore
		queryBuilder.append(" AND (t.visibilitaConf != :visibilitaConf");
		queryBuilder.append(" OR t.visibilitaConf is null)");

		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);

		if (ruoloDto.getCodice() != null && !ruoloDto.getCodice().isEmpty()) {
			query.setParameter("codice", ruoloDto.getCodice() + '%');
		}
		if (ruoloDto.getDescrizione() != null && !ruoloDto.getDescrizione().isEmpty()) {
			query.setParameter("descrizione", ruoloDto.getDescrizione() + '%');
		}
		if (flagNonAttivo == null && "true".equalsIgnoreCase(flagAttivo)
				|| (flagAttivo == null && "true".equalsIgnoreCase(flagNonAttivo))) {
			query.setParameter("data", Utils.sysdate());
		}
		if (flagConfiguratore != null && flagConfiguratore == true) {
			query.setParameter("flagConfiguratore", "S");
		}
		query.setParameter("visibilitaConf", "N");

		return query.getResultList();
	}

	@Override
	public List<RuoloSelezionabileDto> getRuoliSelezionabiliByRuolo(Long idRuoloOperatore) {
		StringBuilder queryBuilder = new StringBuilder(
				"from " + RuoloSelezionabileDto.class.getName() + " where ruoloOperatore.id=:ruoloOperatore and dataFineValidita  IS NULL ");
		Query query = entityManager.createQuery(queryBuilder.toString());

		query.setParameter("ruoloOperatore", idRuoloOperatore);

		return query.getResultList();
	}

	@Override
	public List<RuoloDTO> getRuoliSelezionabili(String cfOperatore, String codiceCollocazione, String codRuolo) {
		List<RuoloSelezionabileDto> resultList = new ArrayList<RuoloSelezionabileDto>();
		RuoloDto ruoloOperatore = new RuoloDto();
		try {
			ruoloOperatore = entityManager
					.createQuery("Select r.ruoloDto from RuoloUtenteDto r "
							+ "where r.ruoloDto.codice = :codRuolo AND r.utenteDto.codiceFiscale = :cfOperatore "
							+ "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
							+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
							+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
							+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) ", RuoloDto.class)
					.setParameter("codRuolo", codRuolo).setParameter("cfOperatore", cfOperatore).getSingleResult();
		} catch (NoResultException e) {
			ruoloOperatore = null;
		}

		CollocazioneDto collocazioneOperatore = new CollocazioneDto();
		try {
			collocazioneOperatore = entityManager.createQuery("Select c.collocazioneDto from UtenteCollocazioneDto c "
					+ "where c.collocazioneDto.colCodice = :colCodice AND c.utenteDto.codiceFiscale = :cfOperatore "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
					+ "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) ", CollocazioneDto.class)
					.setParameter("colCodice", codiceCollocazione).setParameter("cfOperatore", cfOperatore)
					.getSingleResult();
		} catch (NoResultException e) {
			collocazioneOperatore = null;
		}

		if (ruoloOperatore != null && collocazioneOperatore != null) {
			resultList = entityManager.createQuery(
					"SELECT DISTINCT rr from RuoloSelezionabileDto rr " + "WHERE rr.ruoloOperatore = :ruoloOperatore "
							+ "AND (rr.collocazioneTipo = :tipoCollocazioneOperatore OR rr.collocazioneTipo is NULL) "
							+ "AND ((CURRENT_TIMESTAMP BETWEEN rr.dataInizioValidita AND rr.dataFineValidita) "
							+ "OR (CURRENT_TIMESTAMP > rr.dataInizioValidita AND rr.dataFineValidita IS NULL) "
							+ "OR (CURRENT_TIMESTAMP < rr.dataFineValidita AND rr.dataInizioValidita IS NULL) "
							+ "OR (rr.dataInizioValidita IS NULL AND rr.dataFineValidita IS NULL)) ",
					RuoloSelezionabileDto.class).setParameter("ruoloOperatore", ruoloOperatore)
					.setParameter("tipoCollocazioneOperatore", collocazioneOperatore.getCollocazioneTipoDto())
					.getResultList();
		}

		List<RuoloDTO> mapListRuoloSelezionabileDtoToListRuoloDTO = mapListRuoloSelezionabileDtoToListRuoloDTO(
				resultList);
		return mapListRuoloSelezionabileDtoToListRuoloDTO;

	}

	private List<RuoloDTO> mapListRuoloSelezionabileDtoToListRuoloDTO(List<RuoloSelezionabileDto> resultList) {
		return resultList.stream()
				.map(o -> new RuoloDTO(Optional.ofNullable(o.getRuoloSelezionabile()).map(RuoloDto::getId).orElse(null),
						Optional.ofNullable(o.getCollocazioneTipo()).map(CollocazioneTipoDto::getIdColTipo)
								.map(String::valueOf).orElse(null)))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getRuoliCompatibili(Long idRuolo) {
		List<Object> resultList = entityManager
				.createNativeQuery("SELECT DISTINCT id_ruolo_compatibile " + "FROM auth_r_ruolo_compatibilita "
						+ "WHERE id_ruolo = :idRuolo "
						+ "AND ((CURRENT_TIMESTAMP BETWEEN data_inizio_val AND data_fine_val) "
						+ "OR (CURRENT_TIMESTAMP > data_inizio_val AND data_fine_val IS NULL) "
						+ "OR (CURRENT_TIMESTAMP < data_fine_val AND data_inizio_val IS NULL) "
						+ "OR (data_inizio_val IS NULL AND data_fine_val IS NULL)) ")
				.setParameter("idRuolo", idRuolo).getResultList();
		return resultList.stream().map(String::valueOf).collect(Collectors.toList());
	}

	@Override
	public List<RuoloDTO> findRuoliByIdProfilo(Long id) {
		return entityManager.createQuery("SELECT new it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO( "
				+ "r.id, CONCAT(r.codice, ' - ', r.descrizione)) "
				+ "FROM RuoloProfilo rp JOIN rp.ruolo r JOIN rp.funzionalita f " + "WHERE f.idFunzione = :idFunz "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN rp.dataInizioValidita AND rp.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > rp.dataInizioValidita AND rp.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < rp.dataFineValidita AND rp.dataInizioValidita IS NULL) "
				+ "OR (rp.dataInizioValidita IS NULL AND rp.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
				+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL))", RuoloDTO.class)
				.setParameter("idFunz", id).getResultList();
	}

	@Override
	public RuoloDto findByQualificaOpessan(String qualifica) {
		try {
			return entityManager.createQuery("SELECT rq.ruoloDto FROM RuoloOpessanQualificaDto rq "
					+ "WHERE rq.opessanCategoriaQualificaDto.opessanQualificaDto.opqCodice = :qualifica "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.dataInizioValidita AND rq.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.dataInizioValidita AND rq.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.dataFineValidita AND rq.dataInizioValidita IS NULL) "
					+ "OR (rq.dataInizioValidita IS NULL AND rq.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.ruoloDto.dataInizioValidita AND rq.ruoloDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.ruoloDto.dataInizioValidita AND rq.ruoloDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.ruoloDto.dataFineValidita AND rq.ruoloDto.dataInizioValidita IS NULL) "
					+ "OR (rq.ruoloDto.dataInizioValidita IS NULL AND rq.ruoloDto.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.opessanCategoriaQualificaDto.dataInizioValidita AND rq.opessanCategoriaQualificaDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.opessanCategoriaQualificaDto.dataInizioValidita AND rq.opessanCategoriaQualificaDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.opessanCategoriaQualificaDto.dataFineValidita AND rq.opessanCategoriaQualificaDto.dataInizioValidita IS NULL) "
					+ "OR (rq.opessanCategoriaQualificaDto.dataInizioValidita IS NULL AND rq.opessanCategoriaQualificaDto.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataInizioValidita AND rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataInizioValidita AND rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataFineValidita AND rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataInizioValidita IS NULL) "
					+ "OR (rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataInizioValidita IS NULL AND rq.opessanCategoriaQualificaDto.opessanQualificaDto.dataFineValidita IS NULL)) ",
					RuoloDto.class).setParameter("qualifica", qualifica).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public RuoloDto findByPosizioneAndProfilo(String descrPosizioneFunzionale, String descrProfiloProfessionale) {
		try {
			return entityManager.createQuery("SELECT rq.ruoloDto FROM RuoloOpessanPosizioneDto rq "
					+ "WHERE rq.opessanProfiloPosizioneDto.opessanProfiloDto.opppCodice = :profilo "
					+ "AND rq.opessanProfiloPosizioneDto.opessanPosizioneDto.oppfCodice = :posizione "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.dataInizioValidita AND rq.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.dataInizioValidita AND rq.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.dataFineValidita AND rq.dataInizioValidita IS NULL) "
					+ "OR (rq.dataInizioValidita IS NULL AND rq.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.ruoloDto.dataInizioValidita AND rq.ruoloDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.ruoloDto.dataInizioValidita AND rq.ruoloDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.ruoloDto.dataFineValidita AND rq.ruoloDto.dataInizioValidita IS NULL) "
					+ "OR (rq.ruoloDto.dataInizioValidita IS NULL AND rq.ruoloDto.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.opessanProfiloPosizioneDto.dataInizioValidita AND rq.opessanProfiloPosizioneDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.opessanProfiloPosizioneDto.dataInizioValidita AND rq.opessanProfiloPosizioneDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.opessanProfiloPosizioneDto.dataFineValidita AND rq.opessanProfiloPosizioneDto.dataInizioValidita IS NULL) "
					+ "OR (rq.opessanProfiloPosizioneDto.dataInizioValidita IS NULL AND rq.opessanProfiloPosizioneDto.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataInizioValidita AND rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataInizioValidita AND rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataFineValidita AND rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataInizioValidita IS NULL) "
					+ "OR (rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataInizioValidita IS NULL AND rq.opessanProfiloPosizioneDto.opessanProfiloDto.dataFineValidita IS NULL)) "
					+ "AND ((CURRENT_TIMESTAMP BETWEEN rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataInizioValidita AND rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataFineValidita) "
					+ "OR (CURRENT_TIMESTAMP > rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataInizioValidita AND rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataFineValidita IS NULL) "
					+ "OR (CURRENT_TIMESTAMP < rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataFineValidita AND rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataInizioValidita IS NULL) "
					+ "OR (rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataInizioValidita IS NULL AND rq.opessanProfiloPosizioneDto.opessanPosizioneDto.dataFineValidita IS NULL)) ",
					RuoloDto.class).setParameter("profilo", descrProfiloProfessionale)
					.setParameter("posizione", descrPosizioneFunzionale).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public RuoloDto findByCodice(String codice) {
		try {
			return entityManager.createQuery("SELECT r FROM RuoloDto r WHERE r.codice = :codice"
						  			, RuoloDto.class).setParameter("codice", codice).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
								
	}

	@Override
	public void updateRuoloSelezionabile(RuoloSelezionabileDto rsel) {
		entityManager.persist(rsel);
		entityManager.flush();

	}

	@Override
	public void insertRuoloCompatibile(long idruolo, String newRuoliComp) {
		Query query = entityManager.createNativeQuery(
				"INSERT INTO auth_r_ruolo_compatibilita (id_ruolo, id_ruolo_compatibile, data_inizio_val, data_inserimento) VALUES(:id_ruolo, :id_ruolo_compatibile, now(),  now())");

		log.info("[RuoloLowDaoImpl::insertRuoloCompatibile] idruolo:=" + idruolo + ", newRuoliComp:=" + newRuoliComp);

		if (newRuoliComp != null && newRuoliComp.trim().length() > 0) {
			query.setParameter("id_ruolo", idruolo);
			query.setParameter("id_ruolo_compatibile", Long.parseLong(newRuoliComp));

			query.executeUpdate();
		}
	}

	@Override
	public void updateDataFineRuoliCompatibili(long idruolo, List<String> delRuoliComp) {
		if (delRuoliComp != null && delRuoliComp.size() > 0) {
			List<Long> data = delRuoliComp.stream().map(Long::parseLong).collect(Collectors.toList());
			Query query = entityManager.createNativeQuery(
					"update auth_r_ruolo_compatibilita set data_fine_val=now() where id_ruolo=:idruolo and id_ruolo_compatibile in (:ruoli) and data_fine_val is null");

			log.info("[RuoloLowDaoImpl::updateDataFineRuoliCompatibili] idruolo:=" + idruolo + ", delRuoliComp:=" + delRuoliComp);

			query.setParameter("idruolo", idruolo);
			query.setParameter("ruoli", data);

			query.executeUpdate();
		}
	}

	@Override
	public void insertRuoliSelezionabile(long idRuoloOp, String idRuoloSel, String idCollTipo, int id) {
		
		Query query = entityManager.createNativeQuery(
				"INSERT INTO auth_r_ruolo_ruoli (id_ruolo_operatore, id_ruolo_selezionabile, col_tipo_id, data_inizio_val, data_inserimento) VALUES(:id_ruolo_operatore, :id_ruolo_selezionabile, " //
						+ (RuoloSelCollTipo.isNull(idCollTipo) ? "null" : ":col_tipo_id")//
						+ ", now(), now())");

		query.setParameter("id_ruolo_operatore", idRuoloOp);
		query.setParameter("id_ruolo_selezionabile", Long.parseLong(idRuoloSel));
		
		log.info("[RuoloLowDaoImpl::insertRuoliSelezionabile] idRuoloOp:=" + idRuoloOp + ", idRuoloSel:=" + idRuoloSel);

		
		if (!RuoloSelCollTipo.isNull(idCollTipo))
			query.setParameter("col_tipo_id", RuoloSelCollTipo.parseLong(idCollTipo));

		id = query.executeUpdate();

	}
	
	@Override
	public void updateDataFineRuoliSelezionabile(long idRuoloOp, String idRuoloSel, int id) {
		Query query = entityManager.createNativeQuery(
				"UPDATE auth_r_ruolo_ruoli SET data_fine_val=now() WHERE id_ruolo_operatore=:id_ruolo_operatore and id_ruolo_selezionabile=:id_ruolo_selezionabile and data_fine_val is null");

		log.info("[RuoloLowDaoImpl::updateDataFineRuoliSelezionabile] idRuoloOp:=" + idRuoloOp + ", idRuoloSel:=" + idRuoloSel);
		
		query.setParameter("id_ruolo_operatore", idRuoloOp);
		query.setParameter("id_ruolo_selezionabile", Long.parseLong(idRuoloSel));

		id = query.executeUpdate();

	}

	@Override
	public void updateRuoliSelezionabile(long idRuoloOp, String idRuoloSel, String idCollTipo, int id) {
		final String sql="UPDATE auth_r_ruolo_ruoli SET col_tipo_id="//
				+ (RuoloSelCollTipo.isNull(idCollTipo) ? "null" : ":col_tipo_id")//
				+ " WHERE id_ruolo_operatore=:id_ruolo_operatore and id_ruolo_selezionabile=:id_ruolo_selezionabile and data_fine_val is null";
		Query query = entityManager.createNativeQuery(sql);

		log.info("[RuoloLowDaoImpl::updateRuoliSelezionabile] idRuoloOp:=" + idRuoloOp + ", idRuoloSel:=" + idRuoloSel
				+ ", idCollTipo:=" + idCollTipo);
		
		query.setParameter("id_ruolo_operatore", idRuoloOp);
		query.setParameter("id_ruolo_selezionabile", Long.parseLong(idRuoloSel));
		
		
		if (!RuoloSelCollTipo.isNull(idCollTipo))
			query.setParameter("col_tipo_id", RuoloSelCollTipo.parseLong(idCollTipo));

		id = query.executeUpdate();

	}

    @Override
    public Collection<RuoloDto> findByCodiceAndDataValidita(String codiceRuolo) {
        Query query = entityManager.createQuery(
                "SELECT r FROM RuoloDto r " +
                        " WHERE r.codice = :codiceRuolo " +
                        "AND ((:data BETWEEN r.dataInizioValidita AND r.dataFineValidita) OR (:data >= r.dataInizioValidita and r.dataFineValidita is null))");
        query.setParameter("codiceRuolo", codiceRuolo);
        query.setParameter("data", Utils.sysdate());
        return query.getResultList();
    }

	private void cleanRuoliSelezionabile(long id) {
		final String sql = //
				"update auth_r_ruolo_compatibilita " + //
						"set data_fine_val = now() " + //
						"WHERE id_ruocomp IN " + //
						"(SELECT id_ruocomp " + //
						"    FROM  " + //
						"        (SELECT  " + //
						"         ROW_NUMBER() OVER( PARTITION BY id_ruolo , id_ruolo_compatibile, data_fine_val  " + //
						"        ORDER BY  data_fine_val DESC ) AS row_num, " + //
						"        id_ruocomp  " + //
						"        FROM auth_r_ruolo_compatibilita ) t " + //
						"        where id_ruolo =:id_ruolo " + //
						"        and data_fine_val is null " + //
						"        and t.row_num > 1 )";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("id_ruolo", id);

		query.executeUpdate();
		
	}

	private void cleanRuoliCompatibili(long id) {
		final String sql = //
				"update auth_r_ruolo_ruoli " + //
						"set data_fine_val = now() " + //
						"WHERE id IN " + //
						"(SELECT id " + //
						"    FROM  " + //
						"        (SELECT  " + //
						"         ROW_NUMBER() OVER( PARTITION BY id_ruolo_operatore , id_ruolo_selezionabile, data_fine_val  "
						+ //
						"        ORDER BY  data_fine_val DESC ) AS row_num, " + //
						"        id " + //
						"        FROM auth_r_ruolo_ruoli ) t " + //
						"        where id_ruolo_operatore =:id_ruolo_operatore " + //
						"        and data_fine_val is null " + //
						"        and t.row_num > 1 ) ";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("id_ruolo_operatore", id);

		query.executeUpdate();
	}
	
	@Override
	public void cleanRuoli(long id) {
		cleanRuoliCompatibili(id);
		cleanRuoliSelezionabile(id);
		
	}


	


}
