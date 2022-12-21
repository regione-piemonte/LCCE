/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FaqRuparDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.RichiestaCredenzialiView;
import it.csi.solconfig.configuratoreweb.business.service.RuparService;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.RichiestaCredenzialiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RichiestaCredenzialiDto;
import it.csi.solconfig.configuratoreweb.presentation.model.CredenzialiRuparModel;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;

	@Service
	@Transactional
	public class RuparServiceImpl  extends BaseServiceImpl implements RuparService {
		
		@Autowired
		RichiestaCredenzialiLowDao richiestaCredenzialiLowDao;

		@Autowired
		AbilitazioneLowDao abilitazioneLowDao;
		
		
		public PaginaDTO<RichiestaCredenzialiView> ricercaRichiesteRupar(CredenzialiRuparModel ruparModel, Data data) throws Exception{
			
			List<RichiestaCredenzialiDto> richiesteRuparList= new ArrayList<RichiestaCredenzialiDto>();
			if (ruparModel.getNumeroPagina() < 1 || ruparModel.getNumeroElementi() < 1) {
				log.error("ERROR: NumeroElementi e/o numeroPagina non corretti");
				return new PaginaDTO<>();
			}
			
			PaginaDTO<RichiestaCredenzialiView> paginaDTO = new PaginaDTO<>();
			paginaDTO.setElementiTotali(0);
			//dati da input
			
			//chiamata query ricercaRichiesteRupar
			
			richiesteRuparList= (List<RichiestaCredenzialiDto>) richiestaCredenzialiLowDao.findRichiesteRupar(ruparModel);
			
			if(richiesteRuparList != null && !richiesteRuparList.isEmpty()){

				/*
					Se l'utente che effettua la ricerca e' un operatore devo recuperare la lista di collocazioni
					abilitate al configuratore per poterle confrontare con quelle degli operatori trovati
					nella ricerca delle credenziali
				 */
				List<AbilitazioneDto> abilitazioniUserLoggato = getAbilitazioniUserLoggato(data);

				//Cambio oggetto per visualizzazione con date corrette
				List<RichiestaCredenzialiView> richiestaCredenzialiViews = new ArrayList<RichiestaCredenzialiView>();
				for(RichiestaCredenzialiDto richiestaCredenzialiDto : richiesteRuparList){
					RichiestaCredenzialiView richiestaCredenzialiView = new RichiestaCredenzialiView();
					richiestaCredenzialiView.setUtenteDto(richiestaCredenzialiDto.getUtenteDto());
					richiestaCredenzialiView.setDataRichiesta(richiestaCredenzialiDto.getDataRichiesta());
					richiestaCredenzialiView.setTicketRemedy(richiestaCredenzialiDto.getTicketRemedy());
					/*
						Se ho popolato la listad elle abilitazioni del user loggato
						vuol dire che devo confrontarle con quello dell'operatore della ricerca
					 */
					boolean isOperatoreVisibile = checkIfOperatoreVisibile(abilitazioniUserLoggato, richiestaCredenzialiDto);
					if(isOperatoreVisibile) richiestaCredenzialiView.setOperatoreDto(richiestaCredenzialiDto.getOperatoreDto());
					richiestaCredenzialiViews.add(richiestaCredenzialiView);
				}

				/*
				Salvo numero elementi totali e mi ritaglio dalla lista solo gli elementi
				che corrispondono alla pagina ed al numero massimo di elementi da visualizzare
				Parto dal numero pagina - 1 * il numero elementi (offset)
				e arrivo fino al numero elementi visualizzabili (offset + limit)
			 */
				

				int numTotElementi = richiestaCredenzialiViews.size();
				int limit = ruparModel.getNumeroElementi();
				int offset = (ruparModel.getNumeroPagina() - 1) * limit;

				if(limit > richiestaCredenzialiViews.size() || (offset + limit) > richiestaCredenzialiViews.size()){
					richiestaCredenzialiViews = richiestaCredenzialiViews.subList(offset, richiestaCredenzialiViews.size());
				}else{
					richiestaCredenzialiViews = richiestaCredenzialiViews.subList(offset, offset + limit);
				}

				paginaDTO.setElementi(richiestaCredenzialiViews);
				paginaDTO.setElementiTotali(numTotElementi);
				paginaDTO.setPagineTotali((int) Math.ceil((float) paginaDTO.getElementiTotali() / ruparModel.getNumeroElementi()));
			}
			
			return paginaDTO;
	}

		private boolean checkIfOperatoreVisibile(List<AbilitazioneDto> abilitazioniUserLoggato, RichiestaCredenzialiDto richiestaCredenzialiDto) {
			boolean isOperatoreVisibile = true; //setto valore a true cosi' per il superUser (per il quale non si fanno ulteriori controlli) e' sempre visibile
			if(abilitazioniUserLoggato != null && !abilitazioniUserLoggato.isEmpty()){
				List<AbilitazioneDto> abilitazioniOperatoreRicerca = abilitazioneLowDao.findByCodiceFiscaleAndApplicazioneAndDateValidita(
						richiestaCredenzialiDto.getOperatoreDto().getCodiceFiscale(), Constants.APPLICATION_CODE);
				isOperatoreVisibile = false; //setto di default il valore a false in questo caso per poter uscire dal ciclo appena viene trovata una corrispondenza
				if(abilitazioniOperatoreRicerca != null && !abilitazioniOperatoreRicerca.isEmpty()){
					for(AbilitazioneDto abilitazioneUser : abilitazioniUserLoggato){
						for(AbilitazioneDto abilitazioneOperatore : abilitazioniOperatoreRicerca){
							if(abilitazioneUser.getUtenteCollocazioneDto() != null && abilitazioneOperatore.getUtenteCollocazioneDto() != null
							&& abilitazioneUser.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda().equalsIgnoreCase(
									abilitazioneOperatore.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda())){
								isOperatoreVisibile = true;
								break;
							}
						}
						if(isOperatoreVisibile) break;
					}
				}
			}
			return isOperatoreVisibile;
		}

		private List<AbilitazioneDto> getAbilitazioniUserLoggato(Data data) {
			List<AbilitazioneDto> abilitazioniUserLoggato = null;
			if(data.getUtente().getProfilo() == null || !data.getUtente().getProfilo().equalsIgnoreCase(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue())){
				abilitazioniUserLoggato = abilitazioneLowDao.findByCodiceFiscaleAndApplicazioneAndDateValidita(
								data.getUtente().getCodiceFiscale(), Constants.APPLICATION_CODE);

			}
			return abilitazioniUserLoggato;
		}

		@Override
		public FaqRuparDto downloadFaq() throws Exception {
			
			return  richiestaCredenzialiLowDao.findPfd(Constants.FAQ_RUPAR);
			
			
		}

	}
