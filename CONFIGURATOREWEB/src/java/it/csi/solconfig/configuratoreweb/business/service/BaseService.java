/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import javax.servlet.http.HttpServletRequest;

import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;

import java.sql.Timestamp;
import java.util.List;

public interface BaseService {

	public MessaggiUtenteDto aggiungiErrori(String codiciErrore, Object... parametri) throws Exception;

	public MessaggiUtenteDto ricercaMessaggiErrore(MessaggiUtenteDto messaggiUtenteDto) throws Exception;

	public MessaggiUtenteDto creaCopia(MessaggiUtenteDto dto) throws Exception;
//	public <T> Paginazione pagineLista(Collection<T> list);

	public String getIpAddressClient(HttpServletRequest request);

	public void organizzaAlberoFunzionalita(Nodo albero) throws Exception;

    boolean isFunzionalitaAbilitata(List<String> funzionalitaAbilitate, String codiceFunzionalita);
    
    void saveLogServiziEsterni(Data data, String serviceCode, String xmlRequest, String xmlResponse, 
			 Timestamp chiamata, Timestamp risposta, String esito, String error);
    
    public String ricercaMessaggiErroreByCod(String messaggio);

	void setLogAuditSOLNew(OperazioneEnum operazione, String keyOperation, String codiceFiscaleOggOper,
			String uuId, String keyOper, String oggOper, Data data) throws Exception; 
}
