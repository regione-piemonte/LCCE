/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy;

import it.csi.solconfig.configuratoreweb.business.dao.ConfigurazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ConfigurazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.util.Utils;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;


@Component
public class BaseRemedyClient {

    protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

    @Autowired
    ConfigurazioneLowDao configurazioneLowDao;

    // Parametri header
    protected static final String X_REQUEST_ID = "X-Request-ID";
    protected static final String X_FORWARDED_FOR = "X-Forwarded-For";
    protected static final String AUTHORIZATION = "Authorization";

    protected static final String RIEPILOGO = "riepilogo";
    protected static final String TIPOLOGIA = "tipologia";
    protected static final String NOTE = "note";
    protected static final String NOME_ALLEGATO_1 = "nomeAllegato1";
    protected static final String NOME_FILE = "nome_file.csv";




    protected String getValoreConf(String codice) throws Exception {
        ConfigurazioneDto configurazioneDto = new ConfigurazioneDto();
        configurazioneDto.setChiave(codice);
        configurazioneDto = Utils.getFirstRecord(configurazioneLowDao.findByFilter(configurazioneDto));
        if(configurazioneDto != null) return configurazioneDto.getValore();
        return null;
    }
}
