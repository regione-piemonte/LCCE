/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoFunzionalitaLowDao;
import it.csi.configuratorews.business.dao.RuoloLowDao;
import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.PermessoFunzionalitaDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.business.service.GetProfiliFunzionalitaService;
import it.csi.configuratorews.dto.configuratorews.FunzionalitaTecnici;
import it.csi.configuratorews.dto.configuratorews.ModelFunzionalitaProfilo;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.PermessoValido;
import it.csi.configuratorews.dto.configuratorews.ProfiloConValidita;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalita;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class GetProfiliFunzionalitaServiceImpl implements GetProfiliFunzionalitaService{

	
	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;
	@Autowired
	TreeFunzionalitaLowDao treeFunzionalitaLowDao;
	@Autowired
	PermessoFunzionalitaLowDao permessoFunzionalitaLowDao;
	
	@Autowired
	RuoloLowDao ruoloLowDao;
	
	private LogUtil log = new LogUtil(this.getClass());
	
	@Override
	public Pagination<ProfiloFunzionalita> getProfiliFunzionalita(String codiceApplicazione, Integer limit, Integer offset,String codiceAzienda) {
		Pagination<ProfiloFunzionalita> profiliFunzionalitaPagination = new Pagination<ProfiloFunzionalita>();
		List<ProfiloFunzionalita> result = new ArrayList<ProfiloFunzionalita>();
		List<FunzionalitaDto> funzionalita =  funzionalitaLowDao.findFunzionalitaByCodiceApplicazione(codiceApplicazione,limit, offset,codiceAzienda);
		for(FunzionalitaDto funz:funzionalita) {
			ProfiloFunzionalita profiloFunzionalita = createFunzionalita(funz);
			result.add(profiloFunzionalita );
		}
		
		log.info("getProfiliFunzionalita", "OK funzionalita "+codiceApplicazione);
		for(ProfiloFunzionalita profiloFunzionalita:result) {		
			List<TreeFunzionalitaDto> funzionalitaTreeId = treeFunzionalitaLowDao.findByCodiceFunzioneAndApplicazione(profiloFunzionalita.getCodiceProfilo(),"PROF",codiceApplicazione);
			List<TreeFunzionalitaDto> funzionalitaTree = treeFunzionalitaLowDao.findFunzionalitaByIdPadreProfilo(funzionalitaTreeId.get(0).getIdTreeFunzione());
			List<FunzionalitaTecnici> dati = createProfili(funzionalitaTree);
			Collections.sort(dati);
			profiloFunzionalita.setFunzionalita(dati);
			
			//aggiunta lista dei ruoli associati
			List<RuoloDto> listaRuoli = ruoloLowDao.findByProfiloAndApplicazione(profiloFunzionalita.getCodiceProfilo(), codiceApplicazione);
			List<Ruolo> ruoli = fromRuoloDtoListToRuoloList(listaRuoli);
			profiloFunzionalita.setListaRuoli(ruoli);			
		}
		profiliFunzionalitaPagination.setListaRis(result);
		
		Long count = funzionalitaLowDao.countFunzionalitaByCodiceApplicazione(codiceApplicazione,codiceAzienda);
		profiliFunzionalitaPagination.setCount(count);
		
		return profiliFunzionalitaPagination;
	}
	
	private List<FunzionalitaTecnici> createProfili(List<TreeFunzionalitaDto> funzionalitaTree) {
		List<FunzionalitaTecnici> funzionalita = new ArrayList<FunzionalitaTecnici>();
		if(funzionalitaTree!=null) {
			for(TreeFunzionalitaDto tree:funzionalitaTree) {
				
				FunzionalitaTecnici funzionalitaTecnici = new FunzionalitaTecnici();
				Long idFunzione = tree.getFunzionalitaDto().getIdFunzione();
				funzionalitaTecnici.setId(idFunzione);
				String codiceFunzione = tree.getFunzionalitaDto().getCodiceFunzione();
				funzionalitaTecnici.setCodice(codiceFunzione);
				funzionalitaTecnici.setDescrizione(tree.getFunzionalitaDto().getDescrizioneFunzione());
				funzionalitaTecnici.setFineValidita(tree.getFunzionalitaDto().getDataFineValidita());
				funzionalitaTecnici.setInizioValidita(tree.getFunzionalitaDto().getDataInizioValidita());
				funzionalita.add(funzionalitaTecnici );
				log.debug("createProfili", "funzionalita added "+codiceFunzione);
				createPermesso(funzionalitaTecnici, idFunzione, codiceFunzione);
				
			}
		}
		return funzionalita;
	}

	private void createPermesso(FunzionalitaTecnici funzionalitaTecnici, Long idFunzione, String codiceFunzione) {
		List<PermessoFunzionalitaDto> permessoDb = permessoFunzionalitaLowDao.findPermessiByFunzId(idFunzione);
		if(permessoDb!=null && permessoDb.size()>0) {
			List<PermessoValido> PermessoMapped = new ArrayList<PermessoValido>();
			PermessoValido permesso = null;
			for(PermessoFunzionalitaDto permessoFunzionalita:permessoDb) {
				permesso = new PermessoValido();
				permesso.setCodicePermesso(permessoFunzionalita.getPermesso().getCodice());
				permesso.setCodiceTipologiaDato(permessoFunzionalita.getTipologiaDato().getCodice());
				permesso.setFineValidita(permessoFunzionalita.getDataFineValidita());
				permesso.setInizioValidita(permessoFunzionalita.getDataInizioValidita());
				PermessoMapped.add(permesso);
			}
			funzionalitaTecnici.setPermessi(PermessoMapped);
		}
		log.debug("createPermesso", "dati permesso added "+codiceFunzione);
	}
	
	private ProfiloFunzionalita createFunzionalita(FunzionalitaDto funz) {
		ProfiloFunzionalita profiloFunzionalita = new ProfiloFunzionalita();
		profiloFunzionalita.setCodiceProfilo(funz.getCodiceFunzione());
		profiloFunzionalita.setDescrizioneProfilo(funz.getDescrizioneFunzione());
		profiloFunzionalita.setFineValidita(funz.getDataFineValidita());
		profiloFunzionalita.setId(funz.getIdFunzione());
		profiloFunzionalita.setInizioValidita(funz.getDataInizioValidita());
		return profiloFunzionalita;
	}
	
	private List<Ruolo> fromRuoloDtoListToRuoloList(List<RuoloDto> ruoloDtoList) {
		
		List<Ruolo> ruoloList = new ArrayList<>();
		
		for(RuoloDto ruoloDto: ruoloDtoList) {
			Ruolo ruolo = new Ruolo();
			ruolo.setCodice(ruoloDto.getCodice());
			ruolo.setDescrizione(ruoloDto.getDescrizione());
			ruolo.setDataFineValidita(ruoloDto.getDataFineValidita());
			ruolo.setDataInizioValidita(ruoloDto.getDataInizioValidita());
			ruoloList.add(ruolo);
		}		
		
		return ruoloList;
		
	}

	@Override
	public Pagination<ModelFunzionalitaProfilo> getFunzionalitaProfiliByApp(String codiceApplicazione,
			String codiceAzienda, Integer limit, Integer offset) {

		Pagination<ModelFunzionalitaProfilo> result = new Pagination<ModelFunzionalitaProfilo>();
		List<ModelFunzionalitaProfilo> list = new ArrayList<ModelFunzionalitaProfilo>();
		
		List<FunzionalitaDto> funzionalita = funzionalitaLowDao.findFunzionalitaByCodiceApp(codiceApplicazione,
				codiceAzienda, Constants.FUNZIONALITA_TIPO_FUNZ, limit, offset);
		
		for(FunzionalitaDto fnzDto: funzionalita) {
			ModelFunzionalitaProfilo funz = new ModelFunzionalitaProfilo();
			funz.setCodiceFunzione(fnzDto.getCodiceFunzione());
			funz.setDescrizioneFunzione(fnzDto.getDescrizioneFunzione());
			funz.setDataFineValidita(fnzDto.getDataFineValidita());
			funz.setDataInizioValidita(fnzDto.getDataInizioValidita());
			funz.setIdFunzione(fnzDto.getIdFunzione());
			
			List<ProfiloConValidita> profili = new ArrayList<>();		
			
			List<TreeFunzionalitaDto> tree = treeFunzionalitaLowDao.findIdTreeByFnzId(fnzDto.getIdFunzione());
			for(TreeFunzionalitaDto t: tree) {
				TreeFunzionalitaDto padre = t.getFunzionalitaTreePadreDto();
				if(padre != null) {
					FunzionalitaDto funzionalitaPadre = padre.getFunzionalitaDto();
					if(funzionalitaPadre != null && funzionalitaPadre.getTipoFunzionalitaDto() != null) {
						if(funzionalitaPadre.getTipoFunzionalitaDto().getCodiceTipoFunzione().equals(Constants.FUNZIONALITA_TIPO_PROFILO)) {
							ProfiloConValidita profilo = new ProfiloConValidita();
							profilo.setCodice(funzionalitaPadre.getCodiceFunzione());
							profilo.setDescrizione(funzionalitaPadre.getDescrizioneFunzione());
							profilo.setDataInizioValidita(funzionalitaPadre.getDataInizioValidita());
							funzionalitaPadre.setDataFineValidita(funzionalitaPadre.getDataFineValidita());
							
							profili.add(profilo);
						}
					}
				}
			}
			
			funz.setProfili(profili);
			
			list.add(funz);
		}
		
		Long count = funzionalitaLowDao.countFunzionalitaByCodiceApp(codiceApplicazione, codiceAzienda,
				Constants.FUNZIONALITA_TIPO_FUNZ);
		
		result.setCount(count);
		result.setListaRis(list);

		return result;
	}

}
