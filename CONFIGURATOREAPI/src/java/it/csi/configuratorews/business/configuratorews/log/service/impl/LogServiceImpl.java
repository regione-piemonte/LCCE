/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.log.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.dao.CatalogoLogAuditConfLowDao;
import it.csi.configuratorews.business.dao.CatalogoLogAuditLowDao;
import it.csi.configuratorews.business.dao.CsiLogAuditLowDao;
import it.csi.configuratorews.business.dto.CatalogoLogAuditConfDto;
import it.csi.configuratorews.business.dto.CatalogoLogAuditDto;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor=Exception.class)
public class LogServiceImpl implements LogService {

    Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    
    private static final String CODICE_PRODOTTO = "CONFIGURATORE";
    private static final String CODICE_LINEA_CLIENTE = "RP-01";
    
    @Autowired
    protected CsiLogAuditLowDao csiLogAuditLowDao;

    @Autowired
    protected CatalogoLogAuditConfLowDao catalogoLogAuditConfLowDao;
    
    @Value("${codiceAmbiente:AmbienteDefault}")
    String codiceAmbiente;
    @Value("${codiceUnitaInstallazione:UnitaDefault}")
    String codiceUnitaInstallazione;

    @Override
    public CsiLogAuditDto logAttivazione(Operazione operazione, String key_operation, String uUid, String xForwardedFor,
                               String shibIdentitaCodiceFiscale, String xRequestID, String xCodiceServizio, String request) {

        CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();

        try{

            csiLogAuditDto.setDataInserimento(Utils.sysdate());

            csiLogAuditDto.setId_app(generaIdApp(codiceAmbiente, codiceUnitaInstallazione));

            csiLogAuditDto.setIpAdress(xForwardedFor);

            csiLogAuditDto.setOperazione(operazione.getValue());

            csiLogAuditDto.setCodiceFiscaleUtente(shibIdentitaCodiceFiscale);

            csiLogAuditDto.setUuId(uUid);

            csiLogAuditDto.setXcodServizio(xCodiceServizio);

            csiLogAuditDto.setIdRequest(xRequestID);

            csiLogAuditDto.setRequest(request);

            if (key_operation.length() != 0) {
                CatalogoLogAuditConfDto catalogoLogAuditConfDto = new CatalogoLogAuditConfDto();
                catalogoLogAuditConfDto.setKeyOperazione(key_operation);
                catalogoLogAuditConfDto = Utils
                        .getFirstRecord(catalogoLogAuditConfLowDao.findByFilter(catalogoLogAuditConfDto));
                csiLogAuditDto.setCatalogoLogAuditConf(catalogoLogAuditConfDto);
            }

            csiLogAuditDto = csiLogAuditLowDao.insert(csiLogAuditDto);
        }catch(Exception e){
            log.error("Errore durante la scrittura dei log: " + e);
            e.printStackTrace();
        }

        return csiLogAuditDto;
    }
    
    

    @Override
	public void updateLog(CsiLogAuditDto csiLogAuditDto, String response){
        try {
//            CsiLogAuditDto updateAuditDto = new CsiLogAuditDto();
            csiLogAuditDto = csiLogAuditLowDao.findByPrimaryId(csiLogAuditDto.getId());
        	csiLogAuditDto.setResponse(response);
        	csiLogAuditDto.setDataOraResponse(Utils.sysdate());
            csiLogAuditLowDao.update(csiLogAuditDto);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Errore update log " + e);
        }
	}



	/**
     * @return idApplicazione
     */
    public String generaIdApp(String codiceAmbiente, String codiceUnitaInstallazione) {
        return CODICE_PRODOTTO + "_" + CODICE_LINEA_CLIENTE + "_" + codiceAmbiente + "_" + codiceUnitaInstallazione;
    }
}
