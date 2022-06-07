/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration;

import java.util.HashMap;
import java.util.Map;


/**
 * Servizio di marshalling/unmarshalling dei DTO in oggetti del protocollo di
 * interoperabilità
 * 
 * @author Alberto Lagna
 * @author Emanuele Scaglia
 * 
 */
public class Marshaller extends GeneralMarshaller {


	static String[][] COUPLES_CONSENSO = { { "consensoAllaCostituzioneDMA", "statoConsensoDma" },
			{ "derogaEmergenza", "derogaEmergenza" }, { "modalitaDiAlimentazione.codice", "modalitaAlimentazione" },
			{ "dataDiValidazione", "dataOraConferimento" }, { "dataDiValidazione", "dataOraRevoca" } };
	
	//SB CONSENSO R5
	//CONSENSO CC
//	static String[][] COUPLES_CONSENSO_CC_NEW = { { "alimentazioneFse", "alimentazioneFse" },{ "consultazioneFse", "consultazioneFse" },{ "pregressoFse", "pregressoFse" },
//			{ "dataAlimentazioneFse", "dataAlimentazioneFse" }, { "dataConsultazioneFse", "dataConsultazioneFse" },{ "dataPregressoFse", "dataPregressoFse" },
//			{ "alimentazioneFseNota", "alimentazioneFseNota" }, { "consultazioneFseNota", "consultazioneFseNota" }, { "pregressoFseNota", "pregressoFseNota" }  };
	
	//CONSENSO OLD
	static String[][] COUPLES_CONSENSO_CC_OLD = { { "alimentaFse", "alimentazioneFse" },{ "ablConsOpeAmm", "consultazioneFse" },{ "pregresso", "pregressoFse" } };

	static String[][] COUPLES_RUOLO = { { "codiceRuolo", "codice" }, { "descrizioneRuolo", "descrizione" } };

	static String[][] COUPLES_PROFILO = { { "codiceProfilo", "codice" }, { "descrizioneProfilo", "descrizione" } };

	static String[][] COUPLES_AZIENDA_SANITARIA = { { "codice", "codice" }, { "descrizione", "descrizione" } };

	

	private Map<String, String> getMap(String[][] couples, boolean reverse) {

		Map<String, String> map = new HashMap<String, String>();
		int keyIndex = 0;
		int valueIndex = 1;
		if (reverse) {
			keyIndex = 1;
			valueIndex = 0;
		}
		for (int counter = 0; counter < couples.length; counter++) {
			map.put(couples[counter][keyIndex], couples[counter][valueIndex]);
		}
		return map;
	}

//	public it.csi.dma.dmadd.interfacews.Configurazione from(ConfigurazioneLowDto confDto) {
//		it.csi.dma.dmadd.interfacews.Configurazione conf = new it.csi.dma.dmadd.interfacews.Configurazione();
//		if (confDto != null) {
//			conf.setChiave(confDto.getChiave());
//			conf.setValore(confDto.getValore());
//		}
//		return conf;
//	}
}
