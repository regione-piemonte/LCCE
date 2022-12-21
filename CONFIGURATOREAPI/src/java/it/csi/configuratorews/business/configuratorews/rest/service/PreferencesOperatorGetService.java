/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dao.PreferenzaFruitoreLowDao;
import it.csi.configuratorews.business.dao.PreferenzaLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.business.dto.PreferenzaFruitoreDto;
import it.csi.configuratorews.business.dto.PreferenzaSalvataDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.dto.configuratorews.Applicazione;
import it.csi.configuratorews.dto.configuratorews.Fruitore;
import it.csi.configuratorews.dto.configuratorews.ItemPreference;
import it.csi.configuratorews.dto.configuratorews.Preference;
import it.csi.configuratorews.dto.configuratorews.Preferences;
import it.csi.configuratorews.dto.configuratorews.RuoloCollocazione;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PreferencesOperatorGetService extends SOLRESTBaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CODICE_LOG_AUDIT = "AUTH_LOG_167";
	private static final String CODICE_SERVIZIO = "GETPREFERENCESOPER";
	@Autowired
	LogAuditService logAuditService;
	@Autowired
	PreferenzaLowDao preferenzaLowDao;
	@Autowired
	PreferenzaFruitoreLowDao preferenzaFruitoreLowDao;

	private String cf;

	public static final String url = "/preferences/${utente}/search";

	public PreferencesOperatorGetService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String input) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.cf = input;
	}

	@Override
	protected Response execute() {

		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PreferencesOperatorGetService versione 1.0.0");
		String response = "error";
		try {
			if (StringUtils.isEmpty(cf)) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			Preferences preferenzeResult = new Preferences();
			preferenzeResult.setItemPreference(new ArrayList<ItemPreference>());
			ItemPreference temp = null;
			// save preferences saved at false
			List<PreferenzaFruitoreDto> preferenzeFruitore = preferenzaFruitoreLowDao.findAllActive();
			for (PreferenzaFruitoreDto preferenzaFruitore : preferenzeFruitore) {
				if (preferenzaFruitore.getApplicazioneDto() != null) {
					Set<String> applicazioni = new HashSet<String>();
					applicazioni.add(preferenzaFruitore.getApplicazioneDto().getCodice());
					List<PreferenzaDto> resultQueryApplicazione = preferenzaLowDao.findApplicazionePreferenzeByCFAndApplicazioni(this.cf, applicazioni);
					
					
					if (resultQueryApplicazione != null) {
						for (PreferenzaDto preferenzaDto : resultQueryApplicazione) {
							boolean preferenzeSalvateCancellate = isPreferenzeSalvateCancellate(preferenzaDto.getPreferenzaSalvata());
							if (preferenzeSalvateCancellate) {
								// se esiste fruitore associato all'applicazione...
								PreferenzaFruitoreDto fruitoreTrovato = searchFruitore(preferenzaFruitore, preferenzaDto.getApplicazioneDto());
								if (fruitoreTrovato != null) {
									temp = new ItemPreference();
									Applicazione applicazione = new Applicazione();
									applicazione.setCodice(preferenzaDto.getApplicazioneDto().getCodice());
									applicazione.setDescrizione(preferenzaDto.getApplicazioneDto().getDescrizione());
									temp.setApplicazione(applicazione);
									Fruitore fruitore = new Fruitore();
									fruitore.setCodice(preferenzaFruitore.getNomeFruitore());
									fruitore.setDescrizione(preferenzaFruitore.getDescrizioneFruitore());
									temp.setFruitore(fruitore);
									temp.setTipoPreferenza(ItemPreference.APPLICAZIONE);
									Preference preferenza = new Preference();
									preferenza.setEmail(false);
									preferenza.setPush(false);
									preferenza.setSms(false);
									preferenza.setEmailSelectable(preferenzaFruitore.getEmail() != null && preferenzaFruitore.getEmail());
									preferenza.setPushSelectable(preferenzaFruitore.getPush() != null && preferenzaFruitore.getPush());
									preferenza.setSmsSelectable(preferenzaFruitore.getSms() != null && preferenzaFruitore.getSms());
									temp.setPreferenza(preferenza);
									preferenzeResult.getItemPreference().add(temp);
								}
							}
						}
					}
				} else if (preferenzaFruitore.getCollocazioneDto() != null) {
					Set<RuoloCollocazione> ruoloCollocazione = new HashSet<RuoloCollocazione>();
					ruoloCollocazione.add(new RuoloCollocazione(preferenzaFruitore.getRuoloDto().getCodice(),preferenzaFruitore.getCollocazioneDto().getColCodice()));
					List<PreferenzaDto> resultQueryApplicazione = preferenzaLowDao.findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(this.cf, ruoloCollocazione);
					if (resultQueryApplicazione != null) {
						for (PreferenzaDto preferenzaDto : resultQueryApplicazione) {
							boolean preferenzeSalvateCancellate = isPreferenzeSalvateCancellate(preferenzaDto.getPreferenzaSalvata());
							if (preferenzeSalvateCancellate) {
								PreferenzaFruitoreDto fruitoreTrovato = searchFruitore(preferenzaFruitore, preferenzaDto.getRuoloDto(),
										preferenzaDto.getCollocazioneDto());
								if (fruitoreTrovato != null) {
									temp = new ItemPreference();
									temp.setRuolo(preferenzaFruitore.getRuoloDto().getCodice());
									temp.setDescrizioneRuolo(preferenzaFruitore.getRuoloDto().getDescrizione());
									temp.setCollocazione(preferenzaFruitore.getCollocazioneDto().getColCodice());
									temp.setDescrizioneCollocazione(preferenzaFruitore.getCollocazioneDto().getColDescrizione());
									Fruitore fruitore = new Fruitore();
									fruitore.setCodice(preferenzaFruitore.getNomeFruitore());
									fruitore.setDescrizione(preferenzaFruitore.getDescrizioneFruitore());
									temp.setFruitore(fruitore);
									temp.setTipoPreferenza(ItemPreference.RUOLO_COLLOCAZIONE);
									Preference preferenza = new Preference();
									preferenza.setEmail(false);
									preferenza.setPush(false);
									preferenza.setSms(false);
									preferenza.setEmailSelectable(preferenzaFruitore.getEmail() != null && preferenzaFruitore.getEmail());
									preferenza.setPushSelectable(preferenzaFruitore.getPush() != null && preferenzaFruitore.getPush());
									preferenza.setSmsSelectable(preferenzaFruitore.getSms() != null && preferenzaFruitore.getSms());
									temp.setPreferenza(preferenza);
									preferenzeResult.getItemPreference().add(temp);
								}
							}
						}
					}
				} else {
					Set<String> ruolo = new HashSet<String>();
					ruolo.add(preferenzaFruitore.getRuoloDto().getCodice());
					List<PreferenzaDto> resultQueryApplicazione = preferenzaLowDao.findRuoloPreferenzeByCFAndRuolo(this.cf, ruolo);
					if (resultQueryApplicazione != null) {
						for (PreferenzaDto preferenzaDto : resultQueryApplicazione) {
							boolean preferenzeSalvateCancellate = isPreferenzeSalvateCancellate(preferenzaDto.getPreferenzaSalvata());
							if (preferenzeSalvateCancellate) {
								PreferenzaFruitoreDto fruitoreTrovato = searchFruitore(preferenzaFruitore, preferenzaDto.getRuoloDto(),
										preferenzaDto.getCollocazioneDto());
								if (fruitoreTrovato != null ) {
									temp = new ItemPreference();
									temp.setRuolo(preferenzaFruitore.getRuoloDto().getCodice());
									temp.setDescrizioneRuolo(preferenzaFruitore.getRuoloDto().getDescrizione());
									Fruitore fruitore = new Fruitore();
									fruitore.setCodice(preferenzaFruitore.getNomeFruitore());
									fruitore.setDescrizione(preferenzaFruitore.getDescrizioneFruitore());
									temp.setFruitore(fruitore);
									temp.setTipoPreferenza(ItemPreference.RUOLO);
									Preference preferenza = new Preference();
									preferenza.setEmail(false);
									preferenza.setPush(false);
									preferenza.setSms(false);
									preferenza.setEmailSelectable(preferenzaFruitore.getEmail() != null && preferenzaFruitore.getEmail());
									preferenza.setPushSelectable(preferenzaFruitore.getPush() != null && preferenzaFruitore.getPush());
									preferenza.setSmsSelectable(preferenzaFruitore.getSms() != null && preferenzaFruitore.getSms());
									temp.setPreferenza(preferenza);
									preferenzeResult.getItemPreference().add(temp);
								}
							}
						}
					}
				}
			}

			List<PreferenzaDto> resultQueryApplicazione = preferenzaLowDao.findApplicazionePreferenzeByCFAndApplicazioni(this.cf, null,true);
			if (resultQueryApplicazione != null) {
				for (PreferenzaDto preferenzaDto : resultQueryApplicazione) {
					if (preferenzaDto.getPreferenzaSalvata() != null) {
						for (PreferenzaSalvataDto preferenzaSalvata : preferenzaDto.getPreferenzaSalvata()) {
							temp = new ItemPreference();
							Applicazione applicazione = new Applicazione();
							applicazione.setCodice(preferenzaDto.getApplicazioneDto().getCodice());
							applicazione.setDescrizione(preferenzaDto.getApplicazioneDto().getDescrizione());
							temp.setApplicazione(applicazione);
							Fruitore fruitore = new Fruitore();
							fruitore.setCodice(preferenzaSalvata.getPreferenzaFruitoreDto().getNomeFruitore());
							fruitore.setDescrizione(preferenzaSalvata.getPreferenzaFruitoreDto().getDescrizioneFruitore());
							temp.setFruitore(fruitore);
							temp.setTipoPreferenza(ItemPreference.APPLICAZIONE);
							Preference preferenza = new Preference();
							if(preferenzaSalvata.getEmail()==null) {
								preferenzaSalvata.setEmail(false);
							}
							if(preferenzaSalvata.getPush()==null) {
								preferenzaSalvata.setPush(false);
							}
							if(preferenzaSalvata.getSms()==null) {
								preferenzaSalvata.setSms(false);
							}
							preferenza.setEmail(preferenzaSalvata.getEmail());
							preferenza.setPush(preferenzaSalvata.getPush());
							preferenza.setSms(preferenzaSalvata.getSms());
							preferenza.setEmailSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getEmail() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getEmail());
							preferenza.setPushSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getPush() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getPush());
							preferenza.setSmsSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getSms() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getSms());
							temp.setPreferenza(preferenza);
							preferenzeResult.getItemPreference().add(temp);
						}
					}
				}
			}
			log.info(METHOD_NAME, "applicazioni concluso per cf: " + cf);
			List<PreferenzaDto> resultQueryRuoloCollocazione = preferenzaLowDao.findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(this.cf, null,true);
			if (resultQueryRuoloCollocazione != null) {
				for (PreferenzaDto preferenzaDto : resultQueryRuoloCollocazione) {
					if (preferenzaDto.getPreferenzaSalvata() != null) {
						for (PreferenzaSalvataDto preferenzaSalvata : preferenzaDto.getPreferenzaSalvata()) {
							temp = new ItemPreference();
							temp.setRuolo(preferenzaDto.getRuoloDto().getCodice());
							temp.setDescrizioneRuolo(preferenzaDto.getRuoloDto().getDescrizione());
							temp.setCollocazione(preferenzaDto.getCollocazioneDto().getColCodice());
							temp.setDescrizioneCollocazione(preferenzaDto.getCollocazioneDto().getColDescrizione());
							Fruitore fruitore = new Fruitore();
							fruitore.setCodice(preferenzaSalvata.getPreferenzaFruitoreDto().getNomeFruitore());
							fruitore.setDescrizione(preferenzaSalvata.getPreferenzaFruitoreDto().getDescrizioneFruitore());
							temp.setFruitore(fruitore);
							temp.setTipoPreferenza(ItemPreference.RUOLO_COLLOCAZIONE);
							Preference preferenza = new Preference();
							if(preferenzaSalvata.getEmail()==null) {
								preferenzaSalvata.setEmail(false);
							}
							if(preferenzaSalvata.getPush()==null) {
								preferenzaSalvata.setPush(false);
							}
							if(preferenzaSalvata.getSms()==null) {
								preferenzaSalvata.setSms(false);
							}
							preferenza.setEmail(preferenzaSalvata.getEmail());
							preferenza.setPush(preferenzaSalvata.getPush());
							preferenza.setSms(preferenzaSalvata.getSms());
							preferenza.setEmailSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getEmail() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getEmail());
							preferenza.setPushSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getPush() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getPush());
							preferenza.setSmsSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getSms() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getSms());
							temp.setPreferenza(preferenza);
							preferenzeResult.getItemPreference().add(temp);
						}
					}
				}
			}
			log.info(METHOD_NAME, "ruolo collocazione concluso per cf: " + cf);

			List<PreferenzaDto> resultQueryRuolo = preferenzaLowDao.findRuoloPreferenzeByCFAndRuolo(this.cf, null,true);
			if (resultQueryRuolo != null) {
				for (PreferenzaDto preferenzaDto : resultQueryRuolo) {
					if (preferenzaDto.getPreferenzaSalvata() != null) {
						for (PreferenzaSalvataDto preferenzaSalvata : preferenzaDto.getPreferenzaSalvata()) {
							temp = new ItemPreference();
							temp.setRuolo(preferenzaDto.getRuoloDto().getCodice());
							temp.setDescrizioneRuolo(preferenzaDto.getRuoloDto().getDescrizione());
							Fruitore fruitore = new Fruitore();
							fruitore.setCodice(preferenzaSalvata.getPreferenzaFruitoreDto().getNomeFruitore());
							fruitore.setDescrizione(preferenzaSalvata.getPreferenzaFruitoreDto().getDescrizioneFruitore());
							temp.setFruitore(fruitore);
							temp.setTipoPreferenza(ItemPreference.RUOLO);
							Preference preferenza = new Preference();
							if(preferenzaSalvata.getEmail()==null) {
								preferenzaSalvata.setEmail(false);
							}
							if(preferenzaSalvata.getPush()==null) {
								preferenzaSalvata.setPush(false);
							}
							if(preferenzaSalvata.getSms()==null) {
								preferenzaSalvata.setSms(false);
							}
							preferenza.setEmail(preferenzaSalvata.getEmail());
							preferenza.setPush(preferenzaSalvata.getPush());
							preferenza.setSms(preferenzaSalvata.getSms());
							preferenza.setEmailSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getEmail() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getEmail());
							preferenza.setPushSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getPush() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getPush());
							preferenza.setSmsSelectable(
									preferenzaSalvata.getPreferenzaFruitoreDto().getSms() != null && preferenzaSalvata.getPreferenzaFruitoreDto().getSms());
							temp.setPreferenza(preferenza);
							preferenzeResult.getItemPreference().add(temp);
						}
					}
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(preferenzeResult);
			return Response.ok(preferenzeResult).build();
		} catch (RESTException e) {
			log.error("PreferenceGet", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("PreferenceGet", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/* Update logAudit */
			try {
				String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				if (logAuditService.insertLogAudit(request, CODICE_LOG_AUDIT, shibIdentitaCodiceFiscale, CODICE_SERVIZIO, xForwadedForInHeader, xRequestID, xCodiceServizio)) {
					log.info(METHOD_NAME, "log Audit in PreferencesOperatorGetService inserito correttamente");
				} else {
					throw new Exception("Errore inserimento log Audit in PreferencesOperatorGetService");
				}
			} catch (Exception e) {
				log.error("PreferencesOperatorGetService", "log audit: ", e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
		}
	}

	private boolean isPreferenzeSalvateCancellate(List<PreferenzaSalvataDto> preferenzaSalvata) {
		boolean result = true;
		if(preferenzaSalvata!=null &&!preferenzaSalvata.isEmpty()) {
			for(PreferenzaSalvataDto currPreferenzaSalvata:preferenzaSalvata) {
				if(currPreferenzaSalvata.getDataCancellazione()==null) {
					result=false;
				}
			}
		}
		return result;
	}

	private PreferenzaFruitoreDto searchFruitore(PreferenzaFruitoreDto preferenza, RuoloDto ruoloDto, CollocazioneDto collocazioneDto) {
		if (ruoloDto != null) {
			if (preferenza.getRuoloDto() != null && preferenza.getRuoloDto().getCodice().equals(ruoloDto.getCodice()) && collocazioneDto == null) {
				return preferenza;
			}

			if (collocazioneDto != null && preferenza.getRuoloDto() != null && preferenza.getCollocazioneDto() != null
					&& preferenza.getRuoloDto().getCodice().equals(ruoloDto.getCodice())
					&& preferenza.getCollocazioneDto().getColCodice().equals(collocazioneDto.getColCodice())) {
				return preferenza;
			}
		}
		return null;
	}

	private PreferenzaFruitoreDto searchFruitore(PreferenzaFruitoreDto preferenzaFruitore, ApplicazioneDto applicazioneDto) {
		if (applicazioneDto != null) {
			if (preferenzaFruitore.getApplicazioneDto() != null && preferenzaFruitore.getApplicazioneDto().getCodice().equals(applicazioneDto.getCodice())) {
				return preferenzaFruitore;
			}
		}
		return null;
	}

}
