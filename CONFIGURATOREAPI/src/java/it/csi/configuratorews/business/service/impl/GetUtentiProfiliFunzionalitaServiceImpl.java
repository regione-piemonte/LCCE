/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.PermessoFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.dto.PermessoFunzionalitaDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.business.service.GetUtentiProfiliFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.ApplicazioneProfili;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.FunzionalitaTecnici;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.PermessoValido;
import it.csi.configuratorews.dto.configuratorews.ProfiloCollocazione;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalita;
import it.csi.configuratorews.dto.configuratorews.UtenteApplicazioni;
import it.csi.configuratorews.dto.configuratorews.UtenteProfiloFunzionalita;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetUtentiProfiliFunzionalitaServiceImpl implements GetUtentiProfiliFunzionalitaService {

	@Autowired
	UtenteLowDao utenteLowDao;
	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	@Autowired
	TreeFunzionalitaLowDao treeFunzionalitaLowDao;
	@Autowired
	ApplicazioneLowDao applicazioneLowDao;
	@Autowired
	PermessoFunzionalitaLowDao permessoFunzionalitaLowDao;	

	private LogUtil log = new LogUtil(this.getClass());
	

	@Override
	public Pagination<UtenteProfiloFunzionalita> getUtentiProfiliFunzionalita(String ruolo, String codiceCollocazione,
			String codiceAzienda, Integer limit, Integer offset) {
		
		Pagination<UtenteProfiloFunzionalita> profiliFunzionalitaPagination = new Pagination<UtenteProfiloFunzionalita>();
		
		List<UtenteDto> utenti = utenteLowDao.findByRuoloCollocazioneAzienda(ruolo, codiceCollocazione, codiceAzienda, limit, offset);		
		
		List<UtenteProfiloFunzionalita> listaUtenteProfilo = 
				popolaUtenteProfiloFunzionalitaList(utenti, codiceCollocazione, codiceAzienda, ruolo);
		
		profiliFunzionalitaPagination.setListaRis(listaUtenteProfilo);	
		
		Long count = utenteLowDao.countByRuoloCollocazioneAzienda(ruolo, codiceCollocazione, codiceAzienda);
		
		profiliFunzionalitaPagination.setCount(count);
		
		return profiliFunzionalitaPagination;
		
	}

	private List<UtenteProfiloFunzionalita> popolaUtenteProfiloFunzionalitaList(List<UtenteDto> utenti,
			String codiceCollocazione, String codiceAzienda, String ruolo) {
		
		List<UtenteProfiloFunzionalita> listaUtenteProfilo = new ArrayList<>();
		
		for (UtenteDto utenteDto : utenti) {
			
			UtenteProfiloFunzionalita utente = new UtenteProfiloFunzionalita();
			utente.setSesso(utenteDto.getSesso());
			utente.setNome(utenteDto.getNome());
			utente.setDataDiNascita(utenteDto.getDataNascita());
			utente.setComuneDiNascita(utenteDto.getComuneNascita());
			utente.setCognome(utenteDto.getCognome());
			utente.setCodiceFiscale(utenteDto.getCodiceFiscale());
			utente.setId(utenteDto.getId());
			
			List<ApplicazioneDto> applicazioneDtoList = applicazioneLowDao
					.findByUtenteAndCollocazioneOrAzienda(utente.getId(), codiceCollocazione, codiceAzienda);
						
			for(ApplicazioneDto appDto: applicazioneDtoList) {
				
				ApplicazioneProfili applicazione = new ApplicazioneProfili();					
				if (utente.getApplicazioni() == null) {
					utente.setApplicazioni(new HashSet<ApplicazioneProfili>());
				}					
				applicazione.setCodice(appDto.getCodice());
				applicazione.setDescrizione(appDto.getDescrizione());	
									
				if (applicazione.getProfiloCollocazione() == null) {
					applicazione.setProfiloCollocazione(new HashSet<ProfiloCollocazione>());
				}		

				List<AbilitazioneDto> abilitazioni = abilitazioneLowDao
						.findApplicazioniProfiliByUtenteRuoloCollocazioneOrAzienda(utente.getCodiceFiscale(), 
								ruolo, codiceCollocazione, codiceAzienda, appDto.getCodice());
				
				for (AbilitazioneDto abilitazioneDto : abilitazioni) {
					ProfiloFunzionalita profilo = new ProfiloFunzionalita();					
					profilo.setId(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getIdFunzione());
					profilo.setCodiceProfilo(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione());
					profilo.setDescrizioneProfilo(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDescrizioneFunzione());
					profilo.setFineValidita(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDataFineValidita());
					profilo.setInizioValidita(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDataInizioValidita());

					ProfiloCollocazione profiloCollocazione = new ProfiloCollocazione();
					profiloCollocazione.setProfiloFunzionalita(profilo);
					if (abilitazioneDto.getUtenteCollocazioneDto() != null) {
						CollocazioneUtente collocazione = fromAbilitazioneDtoToCollocazioneUtente(abilitazioneDto);
						profiloCollocazione.setCollocazione(collocazione);
					}
					
					if (profiloCollocazione.getProfiloFunzionalita() != null) {
						List<TreeFunzionalitaDto> idTreeProfiloList = treeFunzionalitaLowDao
								.findIdTreeByFnzId(profiloCollocazione.getProfiloFunzionalita().getId());

						if (idTreeProfiloList != null && idTreeProfiloList.get(0) != null) {
							List<TreeFunzionalitaDto> funzionalitaTree = treeFunzionalitaLowDao
									.findFunzionalitaByIdPadreProfilo(
											idTreeProfiloList.get(0).getIdTreeFunzione());
							List<FunzionalitaTecnici> dati = createProfili(funzionalitaTree);
							profiloCollocazione.getProfiloFunzionalita().setFunzionalita(dati);						
							
						}
					}
					
					applicazione.getProfiloCollocazione().add(profiloCollocazione);
								
				}
				if(applicazione.getProfiloCollocazione().size() > 0)
					utente.getApplicazioni().add(applicazione);
			}

			listaUtenteProfilo.add(utente);
		}
		
		Collections.sort(listaUtenteProfilo);
		
		return listaUtenteProfilo;
	}

//	@Override
//	public Pagination<UtenteProfiloFunzionalita> getUtentiProfiliFunzionalita(String ruolo, String collocazione, String codiceAzienda, Integer limit,
//			Integer offset) {
//		
//		Pagination<UtenteProfiloFunzionalita> profiliFunzionalitaPagination = new Pagination<UtenteProfiloFunzionalita>();
//		
//		List<UtenteDto> utenti = utenteLowDao.findByRuoloCollocazione(ruolo, collocazione, codiceAzienda, limit, offset);			
//		
//		List<UtenteProfiloFunzionalita> listaUtenteProfilo = createListUtente(utenti);
//		
//		List<AbilitazioneDto> abilitazioni = abilitazioneLowDao.findApplicazioniByRuoloCollocazioneOrAzienda(ruolo, collocazione, codiceAzienda);		
//		log.info("getProfiliFunzionalita", "OK applicazioni " + ruolo + collocazione);
//		
//		popolateApplicazioni(listaUtenteProfilo, abilitazioni);
//		
//		List<AbilitazioneDto> abilitazioniProfili = abilitazioneLowDao.findProfiliByRuoloCollocazioneOrAzienda(ruolo, collocazione, codiceAzienda);		
//		log.info("getProfiliFunzionalita", "OK profili " + ruolo + collocazione);
//		
//		popolateProfili(listaUtenteProfilo, abilitazioniProfili);		
//		log.info("getProfiliFunzionalita", "OK funzionalita " + ruolo + collocazione);
//		
//		profiliFunzionalitaPagination.setListaRis(listaUtenteProfilo);		
//		Long count = utenteLowDao.countByRuoloCollocazione(ruolo, collocazione, codiceAzienda);
//		profiliFunzionalitaPagination.setCount(count);
//		
//		return profiliFunzionalitaPagination;
//	}

	private List<FunzionalitaTecnici> createProfili(List<TreeFunzionalitaDto> funzionalitaTree) {
		List<FunzionalitaTecnici> funzionalita = new ArrayList<FunzionalitaTecnici>();
		if (funzionalitaTree != null) {
			for (TreeFunzionalitaDto tree : funzionalitaTree) {

				FunzionalitaTecnici funzionalitaTecnici = new FunzionalitaTecnici();
				funzionalitaTecnici.setId(tree.getFunzionalitaDto().getIdFunzione());
				funzionalitaTecnici.setCodice(tree.getFunzionalitaDto().getCodiceFunzione());
				funzionalitaTecnici.setDescrizione(tree.getFunzionalitaDto().getDescrizioneFunzione());
				funzionalitaTecnici.setFineValidita(tree.getFunzionalitaDto().getDataFineValidita());
				funzionalitaTecnici.setInizioValidita(tree.getFunzionalitaDto().getDataInizioValidita());
				
				//TODO: inserire i permessi
				List<PermessoValido> permessi = popolaPermessiByFunzionalita(tree.getFunzionalitaDto().getIdFunzione());
				funzionalitaTecnici.setPermessi(permessi);
				
				funzionalita.add(funzionalitaTecnici);
				log.debug("createProfili", "profilo added " + tree.getFunzionalitaDto().getCodiceFunzione());
			}
		}
		return funzionalita;
	}

	private List<PermessoValido> popolaPermessiByFunzionalita(Long idFunzione) {
		
		List<PermessoValido> permessi = new ArrayList<>();
		
		List<PermessoFunzionalitaDto> permessoFunzDtoList = permessoFunzionalitaLowDao
				.findPermessiByFunzId(idFunzione);
		
		for(PermessoFunzionalitaDto permessofunzDto: permessoFunzDtoList) {
			
			PermessoValido permesso = new PermessoValido();
			
			permesso.setCodicePermesso(permessofunzDto.getPermesso().getCodice());
			permesso.setCodiceTipologiaDato(permessofunzDto.getTipologiaDato().getCodice());
			
			permessi.add(permesso);
		}
		
		return permessi;
	}

	private void popolateProfili(List<UtenteProfiloFunzionalita> listaUtenteProfilo, List<AbilitazioneDto> abilitazioni) {
		if (abilitazioni != null) {
			for (AbilitazioneDto abilitazioneDto : abilitazioni) {
				UtenteProfiloFunzionalita utenteProfiloFunzionalita = findUtenteById(listaUtenteProfilo,
						abilitazioneDto.getRuoloUtenteDto().getUtenteDto().getId());
				if (utenteProfiloFunzionalita!=null && utenteProfiloFunzionalita.getApplicazioni() != null) {
					for (ApplicazioneProfili applicazione : utenteProfiloFunzionalita.getApplicazioni()) {
						if (abilitazioneDto.getApplicazioneDto().getCodice().equals(applicazione.getCodice())) {
							if (utenteProfiloFunzionalita != null) {
								if (applicazione.getProfiloCollocazione() == null) {
									applicazione.setProfiloCollocazione(new HashSet<ProfiloCollocazione>());
								}
								ProfiloCollocazione profiloCollocazione = new ProfiloCollocazione();
								ProfiloFunzionalita profilo = new ProfiloFunzionalita();
								profilo.setId(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getIdFunzione());
								profilo.setCodiceProfilo(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione());
								profilo.setDescrizioneProfilo(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDescrizioneFunzione());
								profilo.setFineValidita(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDataFineValidita());
								profilo.setInizioValidita(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDataInizioValidita());
								profiloCollocazione.setProfiloFunzionalita(profilo);
								if (abilitazioneDto.getUtenteCollocazioneDto() != null) {
									CollocazioneUtente collocazione = fromAbilitazioneDtoToCollocazioneUtente(abilitazioneDto);
									profiloCollocazione.setCollocazione(collocazione);
								}
								
								if (profiloCollocazione.getProfiloFunzionalita() != null) {
									List<TreeFunzionalitaDto> idTreeProfiloList = treeFunzionalitaLowDao
											.findIdTreeByFnzId(profiloCollocazione.getProfiloFunzionalita().getId());

									if (idTreeProfiloList != null && idTreeProfiloList.get(0) != null) {
										List<TreeFunzionalitaDto> funzionalitaTree = treeFunzionalitaLowDao
												.findFunzionalitaByIdPadreProfilo(
														idTreeProfiloList.get(0).getIdTreeFunzione());
										List<FunzionalitaTecnici> dati = createProfili(funzionalitaTree);
										profiloCollocazione.getProfiloFunzionalita().setFunzionalita(dati);
									}
								}
								applicazione.getProfiloCollocazione().add(profiloCollocazione);
							}
						}
					}
				}
			}
		}

	}

	private CollocazioneUtente fromAbilitazioneDtoToCollocazioneUtente(AbilitazioneDto abilitazione) {
		CollocazioneUtente result = new CollocazioneUtente();
		CollocazioneDto col = abilitazione.getUtenteCollocazioneDto().getCollocazioneDto();
		result.setCollocazioneCodice(col.getColCodice());
		result.setCollocazioneDescrizione(col.getColDescrizione());
		result.setCollocazioneCodiceAzienda(col.getColCodAzienda());
		result.setCollocazioneDescrizioneAzienda(col.getColDescAzienda());
		result.setDataInizioValidita(col.getDataInizioValidita());
		result.setDataFineValidita(col.getDataFineValidita());
		result.setStrutturaCodice(col.getCodStruttura());
		result.setStrutturaDescrizione(col.getDenomStruttura());
		result.setUoCodice(col.getCodUo());
		result.setUoDescrizione(col.getDenomUo());
		result.setMultiSpecCodice(col.getCodMultiSpec());
		result.setMultiSpecDescrizione(col.getDenomMultiSpec());
		result.setElementoOrganizzativoCodice(col.getCodiceElementoOrganizzativo());
		result.setElementoOrganizzativoDescrizione(col.getDescElemento());
		result.setAmbulatorioID(col.getIdAmbulatorio());
		result.setAmbulatorioDescrizione(col.getDenomAmbulatorio());
		result.setColTipoCodice(col.getCollocazioneTipoDto().getColTipoCodice());
		result.setColTipoDescrizione(col.getCollocazioneTipoDto().getColTipoDescrizione());
		return result;
	}

	private void popolateApplicazioni(List<UtenteProfiloFunzionalita> listaUtenteProfilo, List<AbilitazioneDto> abilitazioni) {
		if (abilitazioni != null) {
			for (AbilitazioneDto abilitazioneDto : abilitazioni) {
				UtenteProfiloFunzionalita utenteProfiloFunzionalita = findUtenteById(listaUtenteProfilo,
						abilitazioneDto.getRuoloUtenteDto().getUtenteDto().getId());
				if (utenteProfiloFunzionalita != null) {
					if (utenteProfiloFunzionalita.getApplicazioni() == null) {
						utenteProfiloFunzionalita.setApplicazioni(new HashSet<ApplicazioneProfili>());
					}
					ApplicazioneProfili applicazione = new ApplicazioneProfili();
					applicazione.setCodice(abilitazioneDto.getApplicazioneDto().getCodice());
					applicazione.setDescrizione(abilitazioneDto.getApplicazioneDto().getDescrizione());
					utenteProfiloFunzionalita.getApplicazioni().add(applicazione);
				}
			}
		}

	}

	private UtenteProfiloFunzionalita findUtenteById(List<UtenteProfiloFunzionalita> listaUtenteProfilo, Long id) {
		if (listaUtenteProfilo != null) {
			for (UtenteProfiloFunzionalita utenteProfiloFunzionalita : listaUtenteProfilo) {
				if (id == utenteProfiloFunzionalita.getId()) {
					return utenteProfiloFunzionalita;
				}
			}
		}
		return null;
	}

	private List<UtenteProfiloFunzionalita> createListUtente(List<UtenteDto> utenti) {
		List<UtenteProfiloFunzionalita> result = new ArrayList<UtenteProfiloFunzionalita>();
		if (utenti != null) {
			for (UtenteDto utenteDto : utenti) {
				UtenteProfiloFunzionalita utente = new UtenteProfiloFunzionalita();
				utente.setSesso(utenteDto.getSesso());
				utente.setNome(utenteDto.getNome());
				utente.setDataDiNascita(utenteDto.getDataNascita());
				utente.setComuneDiNascita(utenteDto.getComuneNascita());
				utente.setCognome(utenteDto.getCognome());
				utente.setCodiceFiscale(utenteDto.getCodiceFiscale());
				utente.setId(utenteDto.getId());
				result.add(utente);
			}
		}
		return result;
	}

	private List<UtenteApplicazioni> createListUtenteApplicazione(List<UtenteDto> utenti) {
		List<UtenteApplicazioni> result = new ArrayList<UtenteApplicazioni>();
		if (utenti != null) {
			for (UtenteDto utenteDto : utenti) {
				UtenteApplicazioni utente = new UtenteApplicazioni();
				utente.setSesso(utenteDto.getSesso());
				utente.setNome(utenteDto.getNome());
				utente.setDataDiNascita(utenteDto.getDataNascita());
				utente.setComuneDiNascita(utenteDto.getComuneNascita());
				utente.setCognome(utenteDto.getCognome());
				utente.setCodiceFiscale(utenteDto.getCodiceFiscale());
				utente.setId(utenteDto.getId());
				result.add(utente);
			}
		}
		return result;
	}

	@Override
	public Pagination<UtenteApplicazioni> getUtentiApplicazione(String ruolo, String collocazione, String codiceAzienda, Integer limit, Integer offset) {
		Pagination<UtenteApplicazioni> profiliFunzionalitaPagination = new Pagination<UtenteApplicazioni>();
		
		List<UtenteDto> utenti = utenteLowDao.findByRuoloCollocazione(ruolo, collocazione, codiceAzienda, limit, offset);
		log.info("getProfiliFunzionalita", "OK utenti " + ruolo + collocazione);
		
		List<UtenteApplicazioni> listaUtenteProfilo = createListUtenteApplicazione(utenti);
		
		Long count = utenteLowDao.countByRuoloCollocazione(ruolo, collocazione, codiceAzienda);
		profiliFunzionalitaPagination.setListaRis(listaUtenteProfilo);
		profiliFunzionalitaPagination.setCount(count);
		return profiliFunzionalitaPagination;
	}

	@Override
	public Pagination<ApplicazioneProfili> getUtentiAbilitazioni(String utente, String ruolo, String collocazione, String codiceAzienda, Integer limit,
			Integer offset) {
		Pagination<ApplicazioneProfili> profiliFunzionalitaPagination = new Pagination<ApplicazioneProfili>();
		List<String> applicazioni = abilitazioneLowDao.findListaApplicazioni(utente, ruolo, collocazione, codiceAzienda);
		log.info("getUtentiAbilitazioni", "OK findListaApplicazioni" + utente + ruolo + collocazione);
		List<String> applicazioniSelezionate = new ArrayList<String>();
		for (int i = offset; i < (offset + limit); i++) {
			if(applicazioni!=null && applicazioni.size()>i && applicazioni.size()>0) {
				applicazioniSelezionate.add(applicazioni.get(i));
			}
		}
		if (applicazioniSelezionate.size() > 0) {
			List<AbilitazioneDto> abilitazioniProfili = abilitazioneLowDao.findApplicazioniProfiliByUtenteRuoloCollocazione(utente, ruolo, collocazione,
					codiceAzienda, applicazioniSelezionate);
			log.info("getUtentiAbilitazioni", "OK profili e applicazioni" + utente + ruolo + collocazione);
			List<ApplicazioneProfili> listaUtenteProfilo = new ArrayList<ApplicazioneProfili>();
			popolateProfiliFunzionalita(listaUtenteProfilo, abilitazioniProfili);
			profiliFunzionalitaPagination.setListaRis(listaUtenteProfilo);
		}
		Long count = (long) applicazioni.size();
		profiliFunzionalitaPagination.setCount(count);
		return profiliFunzionalitaPagination;
	}

	private void popolateProfiliFunzionalita(List<ApplicazioneProfili> listaUtenteProfilo, List<AbilitazioneDto> abilitazioni) {
		if (abilitazioni != null) {
			for (AbilitazioneDto abilitazioneDto : abilitazioni) {
				ApplicazioneProfili applicazioneCurr = new ApplicazioneProfili();
				applicazioneCurr.setCodice(abilitazioneDto.getApplicazioneDto().getCodice());
				applicazioneCurr.setDescrizione(abilitazioneDto.getApplicazioneDto().getDescrizione());
				listaUtenteProfilo.add(applicazioneCurr);
				for (ApplicazioneProfili applicazione : listaUtenteProfilo) {
					if (abilitazioneDto.getApplicazioneDto().getCodice().equals(applicazione.getCodice())) {
						if (applicazione.getProfiloCollocazione() == null) {
							applicazione.setProfiloCollocazione(new HashSet<ProfiloCollocazione>());
						}
						ProfiloCollocazione profiloCollocazione = new ProfiloCollocazione();
						ProfiloFunzionalita profilo = new ProfiloFunzionalita();
						profilo.setId(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getIdFunzione());
						profilo.setCodiceProfilo(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione());
						profilo.setDescrizioneProfilo(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDescrizioneFunzione());
						profilo.setFineValidita(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDataFineValidita());
						profilo.setInizioValidita(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getDataInizioValidita());
						profiloCollocazione.setProfiloFunzionalita(profilo);
						if (abilitazioneDto.getUtenteCollocazioneDto() != null) {
							CollocazioneUtente collocazione = fromAbilitazioneDtoToCollocazioneUtente(abilitazioneDto);
							profiloCollocazione.setCollocazione(collocazione);
						}
						if (profiloCollocazione.getProfiloFunzionalita() != null) {														
							List<TreeFunzionalitaDto> funzionalitaTreeId = treeFunzionalitaLowDao
									.findIdTreeByFnzId(profiloCollocazione.getProfiloFunzionalita().getId());
							List<TreeFunzionalitaDto> funzionalitaTree = treeFunzionalitaLowDao.findFunzionalitaByIdPadreProfilo(
									funzionalitaTreeId.get(0).getIdTreeFunzione());
							List<FunzionalitaTecnici> dati = createProfili(funzionalitaTree);							
							profiloCollocazione.getProfiloFunzionalita().setFunzionalita(dati);
						}
						applicazione.getProfiloCollocazione().add(profiloCollocazione);
					}
				}
			}
		}

	}


}
