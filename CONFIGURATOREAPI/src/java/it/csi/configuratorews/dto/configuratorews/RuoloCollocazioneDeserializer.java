/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.KeyDeserializer;

public class RuoloCollocazioneDeserializer extends KeyDeserializer {

	@Override
	public Object deserializeKey(String key, DeserializationContext arg1) throws IOException, JsonProcessingException {
		String ruolo = key.substring(0, key.indexOf(","));
		String collocazione = key.substring(key.indexOf(",")+1, key.length()).trim();
		return new RuoloCollocazione(ruolo, collocazione);
	}

}
