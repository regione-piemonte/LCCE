/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import it.csi.solconfig.configuratoreweb.business.service.LoginDataService;
import it.csi.iride2.policy.entity.Identita;
import it.csi.solconfig.configuratoreweb.business.dao.LoginDataLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ServiziLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.LoginDataDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ServiziDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Servizi;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LoginDataServiceImpl implements LoginDataService {

    @Autowired
    LoginDataLowDao loginDataLowDao;

    @Autowired
    ServiziLowDao serviziLowDao;

    @Override
    //Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    public void insertLoginDataDemo(Identita id, String token, String ipAddressClient, String ruolo, String azienda, String collocazione ){

        LoginDataDto loginDataDto = new LoginDataDto();
		loginDataDto.setCfRichiedente(id.getCodFiscale());
        loginDataDto.setClientIp(ipAddressClient);
        loginDataDto.setRemoteIp(ipAddressClient);
        loginDataDto.setToken(token);
        loginDataDto.setDataRichiestaToken(Utils.sysdate());
        loginDataDto.setRuoloRichiedente(ruolo);
        loginDataDto.setCollCodiceAzienda(azienda);
        loginDataDto.setCodiceCollocazione(collocazione);
        loginDataDto.setApplicazioneRichiesta("SOLCONFIG");
        loginDataDto.setApplicazioneChiamante("PUA");
        ServiziDto serviziDto = new ServiziDto();
        serviziDto = Utils.getFirstRecord(serviziLowDao.findByCodice(serviziDto, Servizi.AUTHENTICATION_2.getValue()));
		loginDataDto.setServiziDto(serviziDto);

		loginDataLowDao.insert(loginDataDto);

    }
}
