/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodiceFiscaleValidator implements ConstraintValidator<CodiceFiscale, String> {

    @Override
    public void initialize(CodiceFiscale codiceFiscale) {
        // empty
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.isEmpty() || it.csi.solconfig.configuratoreweb.business.dao.util.CodiceFiscale.checkValiditaCodiceFiscale(s);
    }
}
