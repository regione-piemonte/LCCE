/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.solconfig.configuratoreweb.business.dao.CatalogoLogAuditConfLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.CatalogoLogLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.CsiLogAuditLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.MessaggiErroreLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.MessaggiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.MessaggiUtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ServiziLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ServiziRichiamatiXmlLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TreeFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoLogAuditConfDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoLogDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CsiLogAuditDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiErroreDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ServiziRichiamatiXmlDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.service.BaseService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;

@Component
@Transactional
public class BaseServiceImpl implements BaseService {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    @Value("${codiceAmbiente:AmbienteDefault}")
    String codiceAmbiente;
    @Value("${codiceUnitaInstallazione:UnitaDefault}")
    String codiceUnitaInstallazione;
	
	@Autowired
	MessaggiUtenteLowDao messaggiUtenteLowDao;
	
	@Autowired
	TreeFunzionalitaLowDao treeFunzionalitaLowDao;
	 
    @Autowired
    private ServiziLowDao serviziLowDao;
    
    @Autowired
    private MessaggiLowDao messaggiLowDao;
    
    @Autowired
    private ServiziRichiamatiXmlLowDao serviziRichiamatiXmlLowDao;
    
    @Autowired
    private CatalogoLogLowDao catalogoLogLowDao;
    
    @Autowired
    private MessaggiErroreLowDao messaggiErroreLowDao;
    
    @Autowired
    private RuoloLowDao ruoloLowDao;
    
    @Autowired
    private CatalogoLogAuditConfLowDao catalogoLogAuditConfLowDao;
    
    @Autowired
    private CsiLogAuditLowDao csiLogAuditLowDao;

	@Override
	/**
	 * @param messaggiWarningList
	 * @throws Exception
	 */
	public MessaggiUtenteDto aggiungiErrori(String codiceErrore, Object... parametri) throws Exception {

		MessaggiUtenteDto messaggiUtenteDto = new MessaggiUtenteDto();
		if (codiceErrore != null && !codiceErrore.isEmpty()) {
			// ricerca del messaggioErrore da visualizzare
			messaggiUtenteDto.setCodice(codiceErrore);
			messaggiUtenteDto = ricercaMessaggiErrore(messaggiUtenteDto);

			if (messaggiUtenteDto != null) {
				// creo una copia del messaggioErrore
				MessaggiUtenteDto messaggioDaVisualizzare = creaCopia(messaggiUtenteDto);

				String pattern = messaggioDaVisualizzare.getDescrizione();

				if (parametri != null && parametri.length > 0) {
					messaggioDaVisualizzare.setDescrizione(MessageFormat.format(pattern.replace("", ""), parametri));
					return messaggioDaVisualizzare;
				}
			}

		}
		
		
		return messaggiUtenteDto;
	}
	
	
	@Override
	public MessaggiUtenteDto creaCopia(MessaggiUtenteDto dto) throws Exception {
		MessaggiUtenteDto clone = new MessaggiUtenteDto();
		
		clone.setCodice(dto.getCodice());
		clone.setDescrizione(dto.getDescrizione());
		clone.setId(dto.getId());
		clone.setDataInserimento(dto.getDataInserimento());
		clone.setTipoMessaggio(dto.getTipoMessaggio());
		
		return clone;	
	}

	@Override
	public MessaggiUtenteDto ricercaMessaggiErrore(MessaggiUtenteDto dto) throws Exception {
		dto = Utils.getFirstRecord(messaggiUtenteLowDao.findByFilter(dto));
		return dto ;
	}

	@Override
	public String getIpAddressClient(HttpServletRequest request) {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
	
	@Override
	public void organizzaAlberoFunzionalita(Nodo albero) throws Exception {

		//Cerco i figli del nodo padre
		List<TreeFunzionalitaDto> idfigli = treeFunzionalitaLowDao.findByIdPadre(albero);
		List<Nodo> figli = new ArrayList<Nodo>();
		//aggiungo volta per volta tutti i figli (se ci sono), 
		if (idfigli != null && !idfigli.isEmpty()) {
			for (TreeFunzionalitaDto idfiglio : idfigli) {
				Nodo figlio = new Nodo();
				figlio.setTreeFunzionalitaDto(idfiglio);
				figli.add(figlio); 
			}
			albero.setFigli(figli);
			//Reitero l'intero processo per ogni figlio
			for (Nodo figlio : figli) {
				figlio.setParent(albero);
				organizzaAlberoFunzionalita(figlio);
			}
		}
		
	}

	@Override
	public boolean isFunzionalitaAbilitata(List<String> funzionalitaAbilitate, String codiceFunzionalita) {
		for(String funzionalita : funzionalitaAbilitate){
			if(codiceFunzionalita.equalsIgnoreCase(funzionalita)) return true;
		}
		return false;
	}
	
	@Override
	public void saveLogServiziEsterni(Data data, String serviceCode, String xmlRequest, String xmlResponse, 
			 Timestamp chiamata, Timestamp risposta, String esito, String error) {
		ServiziDto servizio = new ServiziDto();
		servizio.setCodice(serviceCode);
		servizio = serviziLowDao.findByCodice(servizio).iterator().next();
		
		MessaggiDto messaggiDto = new MessaggiDto();
		messaggiDto.setServiziDto(servizio);
		messaggiDto.setCertificato(servizio.getDescrizione());
		messaggiDto.setApplicazione(ConstantsWebApp.APPL_CONF);
		messaggiDto.setCfRichiedente(data.getUtente().getCodiceFiscale());
		messaggiDto.setRuoloRichiedente(data.getCodiceRuoloSelezionato());
		//messaggiDto.setClientIp("");
		messaggiDto.setToken(UUID.randomUUID().toString());
		messaggiDto.setDataRicezione(chiamata);
		messaggiDto.setDataInserimento(chiamata);
		messaggiDto.setDataRisposta(risposta);
		messaggiDto.setDataAggiornamento(risposta);
		messaggiDto.setEsito(esito);
		messaggiDto = messaggiLowDao.insert(messaggiDto);
		
		ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = new ServiziRichiamatiXmlDto();
		serviziRichiamatiXmlDto.setMessaggiDto(messaggiDto);
		serviziRichiamatiXmlDto.setServiziDto(servizio);
		serviziRichiamatiXmlDto.setXmlIn(xmlRequest.getBytes());
		serviziRichiamatiXmlDto.setXmlOut(xmlResponse != null ? xmlResponse.getBytes() : null);
		serviziRichiamatiXmlDto.setDataChiamata(chiamata);
		serviziRichiamatiXmlDto.setDataInserimento(chiamata);
		serviziRichiamatiXmlDto.setDataRisposta(risposta);
		serviziRichiamatiXmlDto.setDataAggiornamento(risposta);
		serviziRichiamatiXmlDto.setEsito(esito);	
		serviziRichiamatiXmlDto = serviziRichiamatiXmlLowDao.inserisciCompleto(serviziRichiamatiXmlDto);
		
		if(error != null) {
			CatalogoLogDto catalogoLog = new CatalogoLogDto();
			catalogoLog.setCodice(error);
			catalogoLog = catalogoLogLowDao.findByCodice(catalogoLog).iterator().next();
			MessaggiErroreDto msgErroreDto = new MessaggiErroreDto();
			msgErroreDto.setMessaggiDto(messaggiDto);
			msgErroreDto.setCatalogoLogDto(catalogoLog);
			msgErroreDto.setCodiceErrore(catalogoLog.getCodice());
			msgErroreDto.setDescrizioneErrore(catalogoLog.getDescrizione());
			msgErroreDto.setControllore(ConstantsWebApp.APPL_CONF);
			msgErroreDto.setTipoErrore(catalogoLog.getTipoErrore());
			msgErroreDto.setDataInserimento(risposta);
			messaggiErroreLowDao.insert(msgErroreDto);
		}
	}


	@Override
	public String ricercaMessaggiErroreByCod(String messaggio) {
		
		MessaggiUtenteDto mes=messaggiErroreLowDao.ricercaMessaggiErroreByCod(messaggio);
		
		return mes.getDescrizione();
		
	}
	
	
	@Override
	  public void setLogAuditSOLNew(OperazioneEnum operazione, String keyOperation,String codiceFiscaleOggOper,String uuId,String keyOper,String oggOper,Data data) throws Exception {
	        CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();
	        String idApp = null;
	        
	        RuoloDto ruoloDto = ruoloLowDao.findByCodice(data.getCodiceRuoloSelezionato());

	        if (validateParametriLogAudit(operazione) == true) {
	            idApp = generaIdApp(codiceAmbiente, codiceUnitaInstallazione);

	            csiLogAuditDto.setDataInserimento(Utils.sysdate());

	            csiLogAuditDto.setId_app(idApp);

	            csiLogAuditDto.setIdAdress(data.getUtente().getIpAddress());

	            csiLogAuditDto.setOperazione(operazione.getValue());

	            csiLogAuditDto.setCodiceFiscaleUtente(data.getUtente().getCodiceFiscale());
	            
	            csiLogAuditDto.setCfOggettoOperazione(codiceFiscaleOggOper);
	            
	            csiLogAuditDto.setRuoloDto(ruoloDto);
	            
	            csiLogAuditDto.setKeyOper(keyOper);
	            
	            csiLogAuditDto.setUuId(uuId);
	            
	            csiLogAuditDto.setOggOper(oggOper);

	            if (keyOperation != null && !keyOperation.isEmpty()) {
	                CatalogoLogAuditConfDto catalogoLogAuditConfDto = new CatalogoLogAuditConfDto();
	                catalogoLogAuditConfDto.setKeyOperazione(keyOperation);
	                catalogoLogAuditConfDto = Utils
	                        .getFirstRecord(catalogoLogAuditConfLowDao.findByFilter(catalogoLogAuditConfDto));
	                csiLogAuditDto.setCatalogoLogAuditConf(catalogoLogAuditConfDto);
	            }

	            csiLogAuditLowDao.insert(csiLogAuditDto);
	        }

	    }
	
	  public boolean validateParametriLogAudit(OperazioneEnum operazione) {
	        if (operazione.getValue() == null || operazione.getValue().isEmpty()) {
	            log.error("Operazione deve essere valorizzato");
	            return false;
	        }
	        return true;
	    }
	  
	  public String generaIdApp(String codiceAmbiente, String codiceUnitaInstallazione) {
	        return ConstantsWebApp.CODICE_PRODOTTO + "_" + ConstantsWebApp.CODICE_LINEA_CLIENTE + "_" + codiceAmbiente + "_" + codiceUnitaInstallazione;
	    }

}
