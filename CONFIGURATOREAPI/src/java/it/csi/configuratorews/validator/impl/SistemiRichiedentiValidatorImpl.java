/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.List;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.csi.configuratorews.business.dao.SistemiRichiedentiLowDao;
import it.csi.configuratorews.business.dto.SistemiRichiedentiDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.SistemiRichiedentiValidator;

@Component
public class SistemiRichiedentiValidatorImpl extends BaseValidatorImpl implements SistemiRichiedentiValidator {

	@Autowired
	SistemiRichiedentiLowDao sistemiRichiedentiLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceAzienda) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		List<SistemiRichiedentiDto> result = sistemiRichiedentiLowDao.findByValidCodice(xCodiceServizio, codiceAzienda);
		if (result == null || result.size() == 0) {
			return generateErrore(Constants.SISTEMA_NON_PRESENTE, Constants.X_CODICE_SERVIZIO);
		}

		return null;
	}

	@Override
	public ErroreBuilder validateJWT(HttpHeaders httpHeaders, String xCodiceServizio) throws Exception { 
		
		try {
			String jwtAssertion = httpHeaders.getRequestHeader("X-JWT-Assertion").get(0);
			DecodedJWT decodedeJWT = JWT.decode(jwtAssertion);
			org.apache.commons.codec.binary.Base64 b = new Base64();
			JSONObject payload = new JSONObject(new String (b.decodeBase64(decodedeJWT.getPayload())));
			String applicationname = payload.getString("http://wso2.org/claims/applicationname");
			if (applicationname == null || applicationname.isEmpty() || !applicationname.equals(xCodiceServizio)) {
				return generateErrore(Constants.SISTEMA_NON_VALIDO, Constants.X_CODICE_SERVIZIO);
			}
		} catch (Exception e) {
			return generateErrore(Constants.SISTEMA_NON_VALIDO, Constants.X_CODICE_SERVIZIO);
		}

		return null;
	}

}
