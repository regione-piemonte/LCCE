/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.util.*;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneCollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneCollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client.Funzionalita;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaProfiloModel;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.service.RicercaProfiliService;

@Service
@Transactional
public class RicercaProfiliServiceImpl extends BaseServiceImpl implements RicercaProfiliService {

    @Autowired
    ApplicazioneLowDao applicazioneLowDao;
    
    @Autowired
    FunzionalitaLowDao funzionalitaLowDao;

    @Autowired
	AbilitazioneLowDao abilitazioneLowDao;

    @Autowired
	ApplicazioneCollocazioneLowDao applicazioneCollocazioneLowDao;
    
	@Override
    public Collection<ApplicazioneDto> ricercaListaApplicazioni(Data data) throws Exception{
    	Collection<ApplicazioneDto> applicazioneDtoList = new ArrayList<ApplicazioneDto>();
    	if(data.getUtente().getProfilo() != null && data.getUtente().getProfilo().equalsIgnoreCase(FunzionalitaEnum.SUPERUSERCONF_PROF.getValue())){
			ApplicazioneDto applicazioneDto = new ApplicazioneDto();
			applicazioneDto.setFlagConfiguratore("S");
			applicazioneDtoList = applicazioneLowDao.findByFilter(applicazioneDto);
		}else{
    		/*
    			Nel caso in cui non siamo SUPERUSER la lista di applicazioni da visualizzare sara' valorizzata con
    			tutte le applicazioni collegate alle collocazioni abilitte all'utente per l'applicazione configuratore
    			Dunque ricerco le abilitazioni dell'utente per l'applicazione configuratore
    			Con le abilitazioni ritrovate posso ricercare quali applicazioni sono collegate alla collocazione abilitata
    			Le applicazioni trovate saranno inserite nella lista visualizzata in pagina
    		 */
			recuperoApplicazioniNonSuperuser(applicazioneDtoList, data);
		}

    	/*
    		Rimuovo dalla lista il Configuratore se la funzionalita' OPListaSolconConfiguratore non e' abilitata all'utente
    	 */
    	boolean isListaSOLconConfiguratoreAbilitata = isFunzionalitaAbilitata(data.getUtente().getFunzionalitaAbilitate(),
				FunzionalitaEnum.OPListaSOLconConfiguratore.getValue());
    	if(!isListaSOLconConfiguratoreAbilitata){
			applicazioneDtoList.removeIf( applicazioneDto -> applicazioneDto.getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE));
		}

    	return applicazioneDtoList;
    	
    }

	private void recuperoApplicazioniNonSuperuser(Collection<ApplicazioneDto> applicazioneDtoList, Data data) {
		List<AbilitazioneDto> abilitazioneDtoList = abilitazioneLowDao.findByCodiceFiscaleAndApplicazioneAndDateValidita(
				data.getUtente().getCodiceFiscale(), Constants.APPLICATION_CODE);
		if(abilitazioneDtoList != null && !abilitazioneDtoList.isEmpty()){
			//Utilizzo un set per gestire eventuali duplicati
			Set<ApplicazioneDto> applicazioneDtoSet = new LinkedHashSet<>();
			for(AbilitazioneDto abilitazioneDto : abilitazioneDtoList){
				if(abilitazioneDto.getUtenteCollocazioneDto() != null){
					List<ApplicazioneCollocazioneDto> applicazioneCollocazioneDtoList =
							applicazioneCollocazioneLowDao.findByIdCollocazioneAndDateValidita(
									abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColId());
					if(applicazioneCollocazioneDtoList != null && !applicazioneCollocazioneDtoList.isEmpty()){
						for(ApplicazioneCollocazioneDto applicazioneCollocazioneDto : applicazioneCollocazioneDtoList){
							applicazioneDtoSet.add(applicazioneCollocazioneDto.getApplicazioneDto());
						}
					}
				}
			}
			applicazioneDtoList.addAll(applicazioneDtoSet);
		}
	}

	@Override
    public PaginaDTO<FunzionalitaDto> ricercaListaProfili(RicercaProfiloModel ricercaProfiloModel, Boolean flag, ApplicazioneDto applicazioneDto) throws Exception{

		List<FunzionalitaDto> funzionalitaDtoList = new ArrayList<FunzionalitaDto>();

		PaginaDTO<FunzionalitaDto> paginaDTO = new PaginaDTO<>();
		paginaDTO.setElementiTotali(0);

		FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
		funzionalitaDto.setCodiceFunzione(ricercaProfiloModel.getCodice());
		funzionalitaDto.setDescrizioneFunzione(ricercaProfiloModel.getDescrizione());
		funzionalitaDto.setApplicazioneDto(applicazioneDto);

		funzionalitaDtoList = funzionalitaLowDao.findProfili(funzionalitaDto,flag);

		if(funzionalitaDtoList != null && !funzionalitaDtoList.isEmpty()){
			/*
			Salvo numero elementi totali e mi ritaglio dalla lista solo gli elementi
			che corrispondono alla pagina ed al numero massimo di elementi da visualizzare
			Parto dal numero pagina - 1 * il numero elementi (offset)
			e arrivo fino al numero elementi visualizzabili (offset + limit)
		 */

			int numTotElementi = funzionalitaDtoList.size();
			int limit = ricercaProfiloModel.getNumeroElementi();
			int offset = (ricercaProfiloModel.getNumeroPagina() - 1) * limit;

			if(limit > funzionalitaDtoList.size() || (offset + limit) > funzionalitaDtoList.size()){
				funzionalitaDtoList = funzionalitaDtoList.subList(offset, funzionalitaDtoList.size());
			}else{
				funzionalitaDtoList = funzionalitaDtoList.subList(offset, offset + limit);
			}

			paginaDTO.setElementi(funzionalitaDtoList);
			paginaDTO.setElementiTotali(numTotElementi);
			paginaDTO.setPagineTotali((int) Math.ceil((float) paginaDTO.getElementiTotali() / ricercaProfiloModel.getNumeroElementi()));
		}
		return paginaDTO;
		
	}

    
}
