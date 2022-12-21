/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.mapper;

import java.math.BigDecimal;

public class BigDecimalFloatMapper extends BaseMapper<BigDecimal, Float> {

	@Override
	public Float to(BigDecimal source) {
		if(source == null) {
			return null;
		}
		return source.floatValue();
		
	}

	@Override
	public BigDecimal from(Float dest) {
		if(dest == null) {
			return null;
		}
		return BigDecimal.valueOf(dest);
		
	}

}
