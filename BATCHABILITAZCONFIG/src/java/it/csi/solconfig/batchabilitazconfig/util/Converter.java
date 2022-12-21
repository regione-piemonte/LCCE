/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
//import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class Converter {

	/**
	 * Converte un oggetto in input in hashmap
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static HashMap<String, Object> convertObjToMap(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		Field[] allFields = obj.getClass().getDeclaredFields();
		for (Field field : allFields) {
			field.setAccessible(true);
			Object value = field.get(obj);
			if(value != null)
				map.put(field.getName(), value);
		}
		return map;
	}

	public static HashMap<String, String> convertObjToMapString(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, String> map = new HashMap<String, String>();

		HashMap<String, Object> mapObject = convertObjToMap(obj);
		for (String s : mapObject.keySet()) {
			if (mapObject.get(s) instanceof Integer)
				map.put(s, ((Integer) mapObject.get(s)).toString());
			if (mapObject.get(s) instanceof Boolean)
				map.put(s, ((Boolean) mapObject.get(s)).toString());
			if (mapObject.get(s) instanceof String)
				map.put(s, (String) mapObject.get(s));
			if (mapObject.get(s) instanceof Date)
				map.put(s, Converter.getData(((Date) mapObject.get(s))));
		}
		return map;
	}

	public static boolean isValorizzato(String stringa) {
		if (stringa == null || stringa.trim().length() == 0)
			return false;
		return true;
	}

	/**
	 * Converte una stringa in un intero. Se la stringa non e' valorizzata
	 * restituisce 0
	 *
	 * @param stringa
	 * @return
	 */
	public static int getInt(String stringa) {
		if (isValorizzato(stringa))
			return Integer.parseInt(stringa);
		else
			return 0;
	}

	/**
	 * Converte una stringa in un long. Se la stringa non e' valorizzata restituisce
	 * 0
	 *
	 * @param stringa
	 * @return
	 */
	public static long getLong(String stringa) {
		if (isValorizzato(stringa))
			return Long.parseLong(stringa);
		else
			return 0;
	}

	/**
	 * Converte una stringa in un double. Se la stringa non e' valorizzata
	 * restituisce 0
	 *
	 * @param stringa
	 * @return
	 */
	public static double getDouble(String stringa) {
		if (isValorizzato(stringa))
			return Double.parseDouble(stringa);
		else
			return 0;
	}

	/**
	 * Converte una data in formato Date in una data in formato string in base al
	 * pattern e al locale forniti
	 *
	 * @param data
	 * @param pattern ad esempio "dd/MM/yyyy"
	 * @param locale
	 * @return
	 */
	public static String getData(Date data, String pattern) {
		if (data == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dataString = sdf.format(data);
		return dataString;
	}

	/**
	 * Converte una data in formato string in una data in formato Date. Controlla: -
	 * se il parametro in ingresso valorizzato - se la stringa rispetta il formato
	 * passato come pattern - se il giorno e il mese rispettano la realta'(Es: mese
	 * non maggiore di 12, giorni di febbraio nn superiori a 28)
	 *
	 * @param data
	 * @param pattern ad esempio "dd/MM/yyyy"
	 * @param locale
	 * @return
	 */
	public static Date getData(String data, String pattern, Locale locale) {
		if (!Checker.isValorizzato(data))
			return null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
			sdf.setLenient(false);
			Date dataDate = sdf.parse(data);

			return dataDate;
		} catch (ParseException pe) {
			// throw new ApplicationException("Errore di conversione da stringa a data.");
			return null;
		}
	}

	/**
	 * Converte una data in formato string in una data in formato Date. Controlla: -
	 * se il parametro in ingresso valorizzato - se la stringa rispetta il formato
	 * "dd/MM/yyyy" - se il giorno e il mese rispettano la realt�(Es: mese non
	 * maggiore di 12, giorni di febbraio nn superiori a 28)
	 *
	 * @param data data
	 * @return data nel formato Date
	 */
	public static Date getData(String data) {
		if (!Checker.isValorizzato(data))
			return null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			Date dataDate = sdf.parse(data);

			return dataDate;
		} catch (ParseException pe) {
			// throw new ApplicationException("Errore di conversione da stringa a data.");
			return null;
		}
	}

	/**
	 * Converte una data in formato string in una data in formato Date. Controlla: -
	 * se il parametro in ingresso valorizzato - se la stringa rispetta il formato
	 * "2019-04-29T13:52:25 00:00" viene troncato nel formato "yyyy-mm-dd" - e
	 * convertito in data
	 * 
	 * @param data data
	 * @return data nel formato Date
	 */
	public static String getDataAcc(String data) {
		if (!Checker.isValorizzato(data))
			return null;

		try {
			int index = data.indexOf("T");
			String newData = data.substring(0, index);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            sdf.setLenient(false);
//            Date dataDate = sdf.parse(newData);

			return newData;
		} catch (Exception pe) {
			// throw new ApplicationException("Errore di conversione da stringa a data.");
			return null;
		}
	}

	/**
	 * Converte una data in formato Timestamp in una data in formato Date.
	 *
	 * @param data data
	 * @return data nel formato Date
	 */

	public static Date getData(Timestamp data) {
		return new Date(data.getTime());
	}

	/**
	 * Restituisce giorno in formato string. (copiato perch� nelle sanita il
	 * Convertitore non � visibile)
	 *
	 * @param data data
	 * @return giorno
	 */
	public static String getGiorno(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(data);
	}

	/**
	 * Restituisce mese in formato string. (copiato perch� nelle sanita il
	 * Convertitore non � visibile)
	 *
	 * @param data data
	 * @return mese
	 */
	public static String getMese(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(data);
	}

	/**
	 * Restituisce anno in formato string. (copiato perch� nelle sanita il
	 * Convertitore non � visibile)
	 *
	 * @param data data
	 * @return anno
	 */
	public static String getAnno(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(data);
	}

	/**
	 * Restituisce lo stesso valore in input se pieno, se null restituisce "".
	 *
	 * @param value
	 * @return
	 */
	public static String getValoreStringa(String value) {
		if (value == null)
			value = "";
		return value;
	}

	/**
	 * Restituisce il valore intero del parametro passato se pieno se null
	 * restituisce 0.
	 *
	 * @param value
	 * @return
	 */
	public static int getValoreIntero(String param) {
		int value;
		if (param == null)
			value = 0;
		else
			value = Integer.parseInt(param);
		return value;
	}

	/**
	 * Converte una data in formato Date in una data in formato string. (copiato
	 * perch� nelle sanita il Convertitore non � visibile)
	 *
	 * @param data data
	 * @return data nel formato String
	 */
	public static String getData(Date data) {
		if (data == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataString = sdf.format(data);
		return dataString;
	}

	public static String getDataISO(Date data) {
		if (data == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dataString = sdf.format(data);
		return dataString;
	}

	public static XMLGregorianCalendar getXMLGregorianCalendar(Date data) {
		if (data == null) {
			return null;
		}

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		try {
			XMLGregorianCalendar t = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			t.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			return t;
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date getData(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}

		return getDataWithoutTime(calendar.toGregorianCalendar().getTime());
	}

	public static String getCodComuneByCodFiscale(String codFiscale) {
		return codFiscale.substring(11, 15);
	}

	public static Date aggiungiAnnoAData(Date dataScadenzaParametrizzata, int numAnni) {
		Calendar c = Calendar.getInstance();
		c.setTime(dataScadenzaParametrizzata);
		c.add(Calendar.YEAR, numAnni);

		return c.getTime();
	}

	public static Date aggiungiGiorniAData(Date dataScadenzaParametrizzata, int numGiorni) {
		Calendar c = Calendar.getInstance();
		c.setTime(dataScadenzaParametrizzata);
		c.add(Calendar.DAY_OF_YEAR, numGiorni);

		return c.getTime();
	}

	public static String getDataToString(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		Date d = calendar.toGregorianCalendar().getTime();
		return getData(d);
	}

	public static Date getLast365Days() {
		LocalDate localDate = LocalDate.now().minusDays(365);
		SimpleDateFormat actual = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat wanted = new SimpleDateFormat("dd/MM/yyyy");

		String reformatted;
		try {
			reformatted = wanted.format(actual.parse(localDate.toString()));
			Date date = wanted.parse(reformatted);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getLast1095Days() {
		LocalDate localDate = LocalDate.now().minusDays(1095);
		SimpleDateFormat actual = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat wanted = new SimpleDateFormat("dd/MM/yyyy");

		String reformatted;
		try {
			reformatted = wanted.format(actual.parse(localDate.toString()));
			Date date = wanted.parse(reformatted);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Long CalcolaEta(XMLGregorianCalendar datanascita) {
		Calendar calendario = Calendar.getInstance();
		int annodata = calendario.get(Calendar.YEAR);
		int mesedata = calendario.get(Calendar.MONTH) + 1;
		int giornodata = calendario.get(Calendar.DAY_OF_MONTH);
		int annoDataFin = datanascita.getYear();
		int meseDataFin = datanascita.getMonth();
		int giornoDataFin = datanascita.getDay();

		Calendar today = new GregorianCalendar(annodata, mesedata, giornodata);
		Calendar dataFin = new GregorianCalendar(annoDataFin, meseDataFin, giornoDataFin);
		long eta = ((today.getTime().getTime() - dataFin.getTime().getTime()) / (1000 * 60 * 60 * 24)) / 365;
		return eta;
	}

	/**
	 * Converte una data in formato Timestamp in una data in formato String
	 * italiana.
	 *
	 * @param data data
	 * @return data nel formato Date
	 */
	public static String getStringFromTimestamp(Timestamp data) {
		return getData(getData(data));
	}

//	private static ObjectMapper objMapper;
//
//	public static ObjectMapper getObjectMapper() {
//		if (null == objMapper) {
//			objMapper = new ObjectMapper();
//		}
//		return objMapper;
//	}
//
//	@SuppressWarnings("unchecked")
//	/**
//	 * Metodo ricorsivo che appiattisce una classe e restituisce una mappa di
//	 * chiave/valore che rappresenta tutti i singoli attributi (primitivi) della
//	 * classe.
//	 * 
//	 * @param mabpObj
//	 * @param obj
//	 * @return
//	 */
//	public static Map<String, Object> flatClass(Map<String, Object> mabpObj, Object obj) {
//		ObjectMapper mapObject = getObjectMapper();
//		Map<String, Object> mapObj = mapObject.convertValue(obj, Map.class);
//		for (Object key : mapObj.keySet()) {
//			Object value = mapObj.get(key);
//			System.out.println("key = " + key + " - value = " + value);
//			if (value == null)
//				mabpObj.put(key.toString(), null);
//			else {
//				if (value instanceof String)
//					mabpObj.put(key.toString(), value.toString());
//				else if (value instanceof Boolean)
//					mabpObj.put(key.toString(), value.toString());
//				else if (value instanceof Long)
//					mabpObj.put(key.toString(), value.toString());
//				else if (value instanceof Object)
//					mabpObj.putAll(flatClass(mabpObj, value));
//			}
//		}
//		return mabpObj;
//	}
//	
	
	public static Date getDataWithoutTime(Date data) {

		if (data != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return new Date(cal.getTimeInMillis());
		} else
			return null;

	}
}
