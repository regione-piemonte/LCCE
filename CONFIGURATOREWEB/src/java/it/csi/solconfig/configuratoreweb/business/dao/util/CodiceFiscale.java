/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.util;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodiceFiscale {
	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	// italiani
	private static final String CF_PATTERN = "^(?:[A-Z][AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$";

	
	private static CodiceFiscale me;
	
	@Value("${cfValidation:true}")
	private String str_validation_enabled;

	@PostConstruct
	public void postConstruct() {
		me=this;
		log.info("[CodiceFiscale::postConstruct] isValidationEnabled::" + isValidationEnabled());
		log.info("[CodiceFiscale::postConstruct] str_validation_enabled::" + str_validation_enabled);		
	}

	private boolean isValidationEnabled() {
		boolean validationEnabled = true;
		try {
			validationEnabled = !("false".equalsIgnoreCase(str_validation_enabled));
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return validationEnabled;
	}

	private boolean p_checkValiditaCodiceFiscale(String codice) {
		if (!isValidationEnabled())
			return true;

		if (codice != null) {
			codice = codice.toUpperCase();
			return codice.matches(CF_PATTERN) && checkUltimaLettera(codice);
		}

		return false;
	}
	
	
	public static boolean checkValiditaCodiceFiscale(String codice) {
		return me.p_checkValiditaCodiceFiscale(codice);
	}

	private static boolean checkUltimaLettera(String cf) {

		if ("".equals(cf) || cf == null || cf.length() != 16)
			return false;

		cf = cf.toUpperCase();

		String validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i < 16; i++) {
			if (validi.indexOf(cf.charAt(i)) == -1)
				return false;
		}

		String set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";

		int s = 0;

		for (int k = 1; k <= 13; k += 2)
			s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(k))));

		for (int l = 0; l <= 14; l += 2)
			s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(l))));

		return s % 26 == (int) cf.charAt(15) - (int) "A".charAt(0);

	}
}