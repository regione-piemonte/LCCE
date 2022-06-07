/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.SiNo;


public final class CommonConverters {
	
	private CommonConverters(){}
	
	public static class SiNoConverter implements Converter {
		public Object convert(Class clazz, Object obj) {		
			if (clazz.equals(SiNo.class)){			
				return SiNo.fromValue(obj.toString());
			} else {			
				return ((SiNo)obj).getValue();
			}			
		}
	}
	
	
	
	public static class RisultatoCodiceConverter implements Converter {
		public Object convert(Class clazz, Object obj) {
			if (clazz.equals(RisultatoCodice.class)){			
				return RisultatoCodice.fromValue(obj.toString());
			} else {			
				return ((RisultatoCodice)obj).getValue();
			}			
		}		
	}
	
	
		
	
	public static void registerConverters(ConvertUtilsBean cub){
		cub.register(new SiNoConverter(), SiNo.class);
		cub.register(new RisultatoCodiceConverter(), RisultatoCodice.class);
	}
	
}
