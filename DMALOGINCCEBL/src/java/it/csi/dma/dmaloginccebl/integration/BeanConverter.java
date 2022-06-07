/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;

public class BeanConverter {

	private Logger logger;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	private BeanUtilsBean2 bub;
	private ConvertUtilsBean cub;

	public BeanConverter() {		
		bub = new BeanUtilsBean2();
		cub = bub.getConvertUtils();
		cub.register(false, true, 0);
		CommonConverters.registerConverters(cub);
	}
	
	public ConvertUtilsBean getConvertUtilsBean() {
		return cub;
	}
	
	public void setConverters(Map<String,String> converters) throws Exception {
		for (String classToConvert : converters.keySet()) {
			String convClass = converters.get(classToConvert);			
			Converter conv = null;
			conv = (Converter)Class.forName(convClass).newInstance();			
			cub.register(conv, Class.forName(classToConvert));			
		}
	}
	
	

	private String[] decomposePath(String path) {
		String[] pathElems = path.split("\\.");
		String[] res = new String[pathElems.length];
		StringBuilder tmpEl = new StringBuilder(pathElems[0]);
		res[0] = pathElems[0];
		for (int k = 1; k < pathElems.length; k++) {
			res[k] = tmpEl.append(".").append(pathElems[k]).toString();
		}
		return res;
	}

	private Object checkIfPropIsNullAndGet(String[] pathElems, Object src) {
		Object value = null;
		for (String pathElem : pathElems) {
			try {
				value = bub.getPropertyUtils().getProperty(src, pathElem);
			} catch (Exception e) {
				return null;
			}
			if (value == null)
				return null;
		}
		return value;
	}

	private void checkIfPropIsNullAndCreate(String[] pathElems, Object dst)  {
		boolean isCurrPropertyNull = false;
		for (int i = 0; i < pathElems.length - 1; i++) {
			Object value = null;
			if (!isCurrPropertyNull) {
				value = null;
				try {
					value = bub.getPropertyUtils().getProperty(dst,
							pathElems[i]);
				} catch (Exception e) {
					if (logger != null){
						logger.error("Errore nel get di pathElems[i] dal dst object",e);
					} else {						
						e.printStackTrace();
					}
				}
			}
			if (isCurrPropertyNull || value == null) {
				isCurrPropertyNull = true;				
				try {
					Class clazz = bub.getPropertyUtils().getPropertyType(dst, pathElems[i]);					
					bub.copyProperty(dst, pathElems[i], clazz.newInstance());
				} catch (Exception e){
					if (logger != null){
						logger.error("Errore nel get/set di pathElems[i] nel dst object",e);
					} else {						
						e.printStackTrace();
					}
				}
			}
		}		
	}

	public void convert(Map<String, String> propsMap, Object dst, Object src) {
		PropertyUtilsBean propUtilBean = bub
				.getPropertyUtils();
		String methodName = "convertProperty";				
		for (String setter : propsMap.keySet()) {
			String getter = propsMap.get(setter);
			String[] getterDecomposed = decomposePath(getter);
			Object srcValue = checkIfPropIsNullAndGet(getterDecomposed, src);
			if (srcValue != null){
				String[] setterDecomposed = decomposePath(setter);
				checkIfPropIsNullAndCreate(setterDecomposed, dst);
				try {
					bub.copyProperty(dst, setter, srcValue);
				} catch (Exception e){
					if (logger != null){
						logger.error("Errore nel set di pathElems[i] nel dst object dal src object",e);
					} else {						
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void copyProperty(String propertyName, Object dst, Object src) {
		if (src != null){
			String[] setterDecomposed = decomposePath(propertyName);
			checkIfPropIsNullAndCreate(setterDecomposed, dst);
			try {
				bub.copyProperty(dst, propertyName, src);
			} catch (Exception e){
				if (logger != null){
					logger.error("Errore nel set di pathElems[i] nel dst object dal src object",e);
				} else {						
					e.printStackTrace();
				}
			}
		}
	}

}
