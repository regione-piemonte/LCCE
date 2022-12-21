/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.mapper;

import it.csi.configuratorews.dto.configuratorews.ModelAbilitazione;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.ModelTokenInformazione;
import it.csi.configuratorews.dto.configuratorews.ParametroLogin;
import it.csi.configuratorews.interfacews.client.abilitazione.Abilitazione;
import it.csi.configuratorews.interfacews.client.abilitazione.GetAbilitazioniResponse;
import it.csi.configuratorews.interfacews.client.base.ParametriLogin;
import it.csi.configuratorews.interfacews.client.collocazioni.Collocazione;
import it.csi.configuratorews.interfacews.client.collocazioni.GetCollocazioniResponse;
import it.csi.configuratorews.interfacews.client.ruoliUtente.GetRuoliUtenteResponse;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;
import it.csi.configuratorews.interfacews.client.tokenInformation.Funzionalita;
import it.csi.configuratorews.interfacews.client.tokenInformation.GetTokenInformation2Response;
import it.csi.configuratorews.interfacews.client.tokenInformation.Richiedente;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProxyTokenInformationGetMapper {

	public ModelTokenInformazione mapSoapResponseToRest(GetTokenInformation2Response getTokenInformation2Response) {
		List<ModelTokenInformazione> modelTokenInformationList = new ArrayList<ModelTokenInformazione>();

		ModelTokenInformazione modelTokenInformazione = new ModelTokenInformazione();
		// Popola Funzionalita da Response a Model
		modelTokenInformazione.setFunzionalita(popolaFunzionalitaModel(getTokenInformation2Response));
		// Popola ParametriLogin da Response a Model
		modelTokenInformazione.setParametriLogin(popolaParametroLoginModel(getTokenInformation2Response));
		// Popola Richiedente da Response a Model
		modelTokenInformazione.setRichiedente(popolaRichiedenteModel(getTokenInformation2Response));


		return modelTokenInformazione;
	}

	/**
	 * @param getTokenInformation2Response
	 * @return
	 */
	public it.csi.configuratorews.dto.configuratorews.Richiedente popolaRichiedenteModel(
			GetTokenInformation2Response getTokenInformation2Response) {
		it.csi.configuratorews.dto.configuratorews.Richiedente richiedenteModel = new it.csi.configuratorews.dto.configuratorews.Richiedente();
		Richiedente richiedente = getTokenInformation2Response.getRichiedente();
		richiedenteModel.setCodiceFiscale(richiedente.getCodiceFiscale());
		richiedenteModel.setCognome(richiedente.getCognome());
		richiedenteModel.setNome(richiedente.getNome());
		richiedenteModel.setRuolo(richiedente.getRuolo());
		// Crea il Model della Collocazione per popolarla
		ModelCollocazione modelCollocazione = new ModelCollocazione();
		modelCollocazione.setCodiceAzienda(richiedente.getCodiceAzienda());
		modelCollocazione.setCodiceCollocazione(richiedente.getCodiceCollocazione());
		modelCollocazione.setDescrizioneAzienda(richiedente.getDescrizioneAzienda());
		modelCollocazione.setDescrizioneCollocazione(richiedente.getDescrizioneCollocazione());
		richiedenteModel.setCollocazione(modelCollocazione);

		return richiedenteModel;
	}

	/**
	 * @param getTokenInformation2Response
	 */
	public List<it.csi.configuratorews.dto.configuratorews.ParametroLogin> popolaParametroLoginModel(
			GetTokenInformation2Response getTokenInformation2Response) {
		List<it.csi.configuratorews.dto.configuratorews.ParametroLogin> parametroLoginModelList = null;
		if (getTokenInformation2Response.getParametriLogin() != null) {
			it.csi.configuratorews.dto.configuratorews.ParametroLogin parametroLoginModel = new ParametroLogin();
			parametroLoginModelList = new ArrayList<it.csi.configuratorews.dto.configuratorews.ParametroLogin>();
			ParametriLogin parametriLogin = getTokenInformation2Response.getParametriLogin();
			parametroLoginModel.setCodice(parametriLogin.getCodice());
			parametroLoginModel.setValore(parametriLogin.getValore());
			parametroLoginModelList.add(parametroLoginModel);
		}
		return parametroLoginModelList;
	}

	/**
	 * @param getTokenInformation2Response
	 * @return
	 */
	public List<it.csi.configuratorews.dto.configuratorews.Funzionalita> popolaFunzionalitaModel(
			GetTokenInformation2Response getTokenInformation2Response) {
		List<it.csi.configuratorews.dto.configuratorews.Funzionalita> funzionalitaModelList = new ArrayList<it.csi.configuratorews.dto.configuratorews.Funzionalita>();
		if (getTokenInformation2Response.getFunzionalitaAbilitate().getFunzionalita() != null
				&& !getTokenInformation2Response.getFunzionalitaAbilitate().getFunzionalita().isEmpty()) {
			for (Funzionalita funzionalita : getTokenInformation2Response.getFunzionalitaAbilitate()
					.getFunzionalita()) {
				it.csi.configuratorews.dto.configuratorews.Funzionalita funzionalitaModel = new it.csi.configuratorews.dto.configuratorews.Funzionalita();
				funzionalitaModel.setCodice(funzionalita.getCodiceFunzionalita());
				funzionalitaModel.setCodiceFunzionalitaPadre(funzionalita.getCodiceFunzionalitaPadre());
				funzionalitaModel.setDescrizione(funzionalita.getDescrizioneFunzionalita());
				funzionalitaModel.setDescrizioneFunzionalitaPadre(funzionalita.getDescrizioneFunzionalitaPadre());
				funzionalitaModelList.add(funzionalitaModel);
			}
		}
		return funzionalitaModelList;
	}
}
