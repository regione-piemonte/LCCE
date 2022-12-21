/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.controller;

import it.csi.solconfig.configuratoreweb.business.dao.*;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoLogAuditConfDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CsiLogAuditDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.integration.log.LogDao;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpSession;

@Transactional
@Controller
@Scope("prototype")
public class BaseController {

    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    private static final String CODICE_PRODOTTO = "CONFIGURATORE";
    private static final String CODICE_LINEA_CLIENTE = "RP-01";

    @Autowired
    protected HttpSession session;

    @Autowired
    private UtenteLowDao utenteLowDao;

    @Autowired
    private ApplicazioneLowDao applicazioneLowDao;

    @Autowired
    private RuoloLowDao ruoloLowDao;

    @Autowired
    protected LogDao logDao;

    @Autowired
    protected CsiLogAuditLowDao csiLogAuditLowDao;

    @Autowired
    protected CatalogoLogAuditConfLowDao catalogoLogAuditConfLowDao;

    protected Data data;

    @Value("${codiceAmbiente:AmbienteDefault}")
    String codiceAmbiente;
    @Value("${codiceUnitaInstallazione:UnitaDefault}")
    String codiceUnitaInstallazione;
    @Value("${urlLogout}")
    String urlLogout;

    public Data getData() {
        if (data == null) {
            if (session.getAttribute("data") == null) {
                data = new Data();
                session.setAttribute("data", new Data());
            } else {
                data = (Data) session.getAttribute("data");
            }
        }
        return data;
    }

    public void initializeData() {
        this.data = new Data();
        session.setAttribute("data", new Data());
    }

    public void updateData(Data data) {
        if (data != null) {
            session.setAttribute("data", (Data) data);
        } else {
            session.removeAttribute("data");
        }
    }

    public void setData(Data data) {
        this.data = data;
        session.setAttribute("data", (Data) data);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void setLogAuditSOL(OperazioneEnum operazione, String key_operation) throws Exception {
        CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();
        String idApp = null;
        data = getData();

        if (validateParametriLogAudit(operazione) == true) {
            idApp = generaIdApp(codiceAmbiente, codiceUnitaInstallazione);

            csiLogAuditDto.setDataInserimento(Utils.sysdate());

            csiLogAuditDto.setId_app(idApp);

            csiLogAuditDto.setIdAdress(data.getUtente().getIpAddress());

            csiLogAuditDto.setOperazione(operazione.getValue());

            csiLogAuditDto.setCodiceFiscaleUtente(data.getUtente().getCodiceFiscale());

            if (key_operation != null && !key_operation.isEmpty()) {
                CatalogoLogAuditConfDto catalogoLogAuditConfDto = new CatalogoLogAuditConfDto();
                catalogoLogAuditConfDto.setKeyOperazione(key_operation);
                catalogoLogAuditConfDto = Utils
                        .getFirstRecord(catalogoLogAuditConfLowDao.findByFilter(catalogoLogAuditConfDto));
                csiLogAuditDto.setCatalogoLogAuditConf(catalogoLogAuditConfDto);
            }

            csiLogAuditLowDao.insert(csiLogAuditDto);
        }

    }

    /**
     * @param operazione
     * @param key_operation
     */
    public boolean validateParametriLogAudit(OperazioneEnum operazione) {
        if (operazione.getValue() == null || operazione.getValue().isEmpty()) {
            log.error("Operazione deve essere valorizzato");
            return false;
        }
        return true;
    }

    /**
     * @return idApplicazione
     */
    public String generaIdApp(String codiceAmbiente, String codiceUnitaInstallazione) {
        return CODICE_PRODOTTO + "_" + CODICE_LINEA_CLIENTE + "_" + codiceAmbiente + "_" + codiceUnitaInstallazione;
    }

    protected static void error(String field, String message, BindingResult bindingResult, Object form) {
        try {
            String objectName = form.getClass().getSimpleName();
            objectName = Character.toLowerCase(objectName.charAt(1)) + objectName.substring(1);
            String methodName = "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
            ObjectError error = new FieldError(objectName, field, form.getClass().getMethod(methodName).invoke(form), false,
                    new String[]{message}, null, message);
            bindingResult.addError(error);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void setLogAuditSOLNew(OperazioneEnum operazione, String key_operation,String codiceFiscaleOggOper,String uuId,String keyOper,String oggOper,Data data) throws Exception {
       // baseService.setLogAuditSOLNew(operazione, key_operation, codiceFiscaleOggOper, uuId, keyOper, oggOper, getData());
    }
}
