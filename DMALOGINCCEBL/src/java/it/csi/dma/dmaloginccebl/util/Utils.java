/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utils {
	
	public static String normalizeClassName(String classname) {
		if (classname == null)
			return null;
		int pos = classname.lastIndexOf('.');
		if (pos >= 0)
			return classname.substring(pos + 1);
		return classname;
	}

	public static final Date DT_NULL = Utils.toDate("01/01/1000");
	public static final Timestamp TS_NULL = Utils.toTimestamp("01/01/1000 00:00:00");

	public static final Date DT_NOT_NULL = Utils.toDate("01/01/1800");
	public static final Timestamp TS_NOT_NULL = Utils.toTimestamp("01/01/1800 00:00:00");

	public final static long SECOND_MILLIS = 1000;
	public final static long MINUTE_MILLIS = SECOND_MILLIS * 60;
	public final static long HOUR_MILLIS = MINUTE_MILLIS * 60;
	public final static long DAY_MILLIS = HOUR_MILLIS * 24;
	public final static long YEAR_MILLIS = DAY_MILLIS * 365;

	public static String concatenate(String... str) {
		String conStr = "";

		if (str != null) {

			for (String el : str) {

				if (isNotEmpty(el)) {
					conStr += " " + el;
				}
			}
		}

		return conStr.trim();
	}

	public static long dateDiff(Date date1, Date date2, long umes) {

		if (date1 == null) {
			date1 = sysdate();
		}

		if (date2 == null) {
			date2 = sysdate();
		}

		long elapsed = (date1.getTime() / umes) - (date2.getTime() / umes);
		return elapsed;
	}

	public static String extractAfter(String str, String fnd) {

		if (isNotEmpty(str) && isNotEmpty(str)) {
			int index = str.indexOf(fnd);

			String extStr = str.substring(index + fnd.length());
			return extStr;
		}

		return null;
	}

	public static String emptyToNull(String str) {
		return (isEmpty(str) ? null : str);
	}

	public static boolean isPositiveNumber(Number nbr) {

		if (nbr != null) {
			return nbr.longValue() >= 0;
		}

		return false;
	}

	public static String checkAndDefault(String str, String defStr) {

		if (isEmpty(str)) {
			return defStr;
		}

		return str;
	}

	public static <E> void addToListNoDuplicate(List<E> list, E elem) {
		if (list != null && !list.contains(elem)) {
			list.add(elem);
		}
	}

	public static boolean equalsOrDefault(String str, String defVal) {

		if (isNotEmpty(str)) {
			return str.equalsIgnoreCase(defVal);
		}

		return true;
	}

	public static String toString(Object obj) {
		return (obj != null ? obj.toString() : "");
	}

	public static <E> boolean isNotEmptyList(List<E> lst) {
		return (lst != null && lst.size() > 0);
	}

	public static <E> boolean isEmptyList(List<E> lst) {
		return (lst == null || lst.size() == 0);
	}

	public static byte[] saveFile(InputStream is, String filePath, String fileName) {
		byte[] buffer = null;

		try {
			buffer = IOUtils.toByteArray(is);
			saveFile(buffer, filePath, fileName);
		} catch (Exception e) {
			// nothing to do
		}

		return buffer;
	}

	public static void saveFile(byte[] content, String filePath, String fileName) {
		if (filePath != null) {
			try {
				File f = new File(filePath + "/" + fileName);
				OutputStream out = new FileOutputStream(f);

				out.write(content);
				out.close();
			} catch (Exception e) {
				// nothing to do
			}
		}
	}

	public static int findStrInArray(String sFnd, String... array) {

		if (sFnd == null || array == null) {
			return -1;
		}

		List<String> lst = Arrays.asList(array);
		int index = lst.indexOf(sFnd);

		return index;
	}

	public static <T> void copyObject(T orig, T dest) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			// nothing to do
		}
	}

	public static <T> void copyObjectWithConverter(T orig, T dest) {
		try {
			org.apache.commons.beanutils.converters.SqlTimestampConverter conv1 = new org.apache.commons.beanutils.converters.SqlTimestampConverter(
					null);

			org.apache.commons.beanutils.ConvertUtils.register(conv1, java.sql.Timestamp.class);

			org.apache.commons.beanutils.converters.SqlDateConverter conv2 = new org.apache.commons.beanutils.converters.SqlDateConverter(
					null);

			org.apache.commons.beanutils.ConvertUtils.register(conv2, java.sql.Date.class);

			org.apache.commons.beanutils.converters.LongConverter conv3 = new org.apache.commons.beanutils.converters.LongConverter(
					null);

			org.apache.commons.beanutils.ConvertUtils.register(conv3, java.lang.Long.class);

			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			// nothing to do
		}
	}

	public static <T> T cloneObject(T orig) {
		T copy = null;

		try {
			copy = cloneX(orig);
		} catch (Exception e) {
			// nothing to do
		}

		return copy;
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return (str != null && str.trim().length() > 0);
	}

	private static <T> T cloneX(T x) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		CloneOutput cout = new CloneOutput(bout);
		cout.writeObject(x);

		byte[] bytes = bout.toByteArray();

		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		CloneInput cin = new CloneInput(bin, cout);

		T clone = (T) cin.readObject();
		return clone;
	}

	private static class CloneOutput extends ObjectOutputStream {
		Queue<Class<?>> classQueue = new LinkedList<Class<?>>();

		CloneOutput(OutputStream out) throws IOException {
			super(out);
		}

		@Override
		protected void annotateClass(Class<?> c) {
			classQueue.add(c);
		}

		@Override
		protected void annotateProxyClass(Class<?> c) {
			classQueue.add(c);
		}
	}

	private static class CloneInput extends ObjectInputStream {
		private final CloneOutput output;

		CloneInput(InputStream in, CloneOutput output) throws IOException {
			super(in);
			this.output = output;
		}

		@Override
		protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
			Class<?> c = output.classQueue.poll();
			String expected = osc.getName();
			String found = (c == null) ? null : c.getName();

			if (!expected.equals(found)) {
				throw new InvalidClassException(
						"Classes desynchronized: " + "found " + found + " when expecting " + expected);
			}
			return c;
		}

		@Override
		protected Class<?> resolveProxyClass(String[] interfaceNames) throws IOException, ClassNotFoundException {
			return output.classQueue.poll();
		}
	}

	public static Long toLong(String val) {
		try {
			return Long.valueOf(val);
		} catch (Exception e) {
			return null;
		}
	}

	public static Float toFloat(String val) {
		try {
			return Float.valueOf(val);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T getFirstRecord(List<T> elenco) {

		if (elenco != null && elenco.size() > 0) {
			return elenco.get(0);
		}

		return null;
	}

	public static <T> T getFirstRecord(Collection<T> elenco) {

		if (elenco != null && elenco.size() > 0) {
			List<T> list = new ArrayList<T>();
			list.addAll(elenco);
			return list.get(0);
		}

		return null;
	}

	public static boolean listIsEmpty(List<?> elenco) {
		return (elenco == null || elenco.size() == 0);
	}

	public static boolean equals(String str1, String str2) {

		if (str1 != null && str2 != null) {
			return str1.equalsIgnoreCase(str2);
		}

		return false;
	}

	private static final String DT_COMPLEX = "dd/MM/yyyy HH:mm:ss";
	private static final String DT_SIMPLE = "dd/MM/yyyy";

	public static SimpleDateFormat getComplexDateFormat() {
		return createPatternDateFormat(DT_COMPLEX);
	}

	public static SimpleDateFormat getSimpleDateFormat() {
		return createPatternDateFormat(DT_SIMPLE);
	}

	public static java.sql.Date toSqlDate(Timestamp time) {
		try {
			java.sql.Date date = new java.sql.Date(time.getTime());
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	public static java.sql.Date toSqlDate(Date date) {
		try {
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static Date onlyData(Date date) {
		try {
			Date dt = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
			return dt;
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toDate(Timestamp tmp) {
		try {
			Date date = new Date(tmp.getTime());
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toDate(String value) {
		try {
			return getSimpleDateFormat().parse(value);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toComplexDate(String value) {
		try {
			return getComplexDateFormat().parse(value);
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp sysdate() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp toTimestamp(Date dt, long hour) {
		try {
			long mstoadd = 1000 * 60 * 60 * hour;
			return new Timestamp(dt.getTime() + mstoadd);
		} catch (Exception e) {
			return null;
		}
	}

	public static XMLGregorianCalendar toXmlGregorianCalendar(final Date date) {
		return toXmlGregorianCalendarFinal(date.getTime());
	}

	public static XMLGregorianCalendar toXmlGregorianCalendarFinal(final long date) {

		XMLGregorianCalendar res = null;

		try {
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(date);
			res = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (final DatatypeConfigurationException ex) {
			System.out.println("Unable to convert date '%s' to an XMLGregorianCalendar object");
		}
		return res;
	}

	public static Timestamp toTimestamp(Date dt) {
		try {
			return new Timestamp(dt.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp toTimestamp(String value) {
		try {
			Date dt = getComplexDateFormat().parse(value);
			return toTimestamp(dt);
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp toTimestampFromStringYYYYMMDDHHmmss(String value) {
		try {

			String anno = value.substring(0, 4);
			String mese = value.substring(4, 6);
			String giorno = value.substring(6, 8);
			String ora = value.substring(8, 10);
			String minuti = value.substring(10, 12);
			String secondi = value.substring(12, 14);

			SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date lFromDate1 = datetimeFormatter1
					.parse(anno + "-" + mese + "-" + giorno + " " + ora + ":" + minuti + ":" + secondi + "." + "000");
			System.out.println("gpsdate :" + lFromDate1);
			Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());

			return fromTS1;
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(java.util.Date data, boolean complex) {
		try {
			SimpleDateFormat df = getSimpleDateFormat();

			if (complex) {
				df = getComplexDateFormat();
			}

			String dateStr = df.format(data);
			return dateStr;
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(java.util.Date data, String pattern) {
		try {
			SimpleDateFormat df = null;

			if (DT_SIMPLE.equalsIgnoreCase(pattern)) {
				df = getSimpleDateFormat();
			} else if (DT_COMPLEX.equalsIgnoreCase(pattern)) {
				df = getComplexDateFormat();
			} else {
				df = createPatternDateFormat(pattern);
			}

			String dateStr = df.format(data);
			return dateStr;
		} catch (Exception e) {
			return null;
		}
	}

	public static SimpleDateFormat createPatternDateFormat(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf;
	}

	public static boolean equals(Object obj1, Object obj2) {

		if (obj1 == null) {
			return (obj2 == null);
		}

		if (obj2 == null) {
			return false;
		}

		return obj1.equals(obj2);
	}

	public static <E> String listToString(List<E> lst, String attrs) {
		String concStr = listToString(lst, attrs, " ", " ");
		return concStr.trim();
	}

	public static <E> String listToString(List<E> lst, String attrs, String sepElem) {
		String concStr = listToString(lst, attrs, " ", sepElem);
		return concStr.trim();
	}

	public static <E> String listToString(List<E> lst, String attrs, String sepInfo, String sepElem) {
		String rsStr = "";

		if (isNotEmptyList(lst)) {
			Class eCls = lst.get(0).getClass();
			String[] attrsLst = attrs.split(",");

			Map<String, Method> pMap = createMethodsMap(eCls, attrsLst);
			Iterator<E> iterator = lst.iterator();

			for (int j = 0; iterator.hasNext(); ++j) {
				E elem = iterator.next();

				if (elem != null) {

					for (int i = 0; i < attrsLst.length; ++i) {
						String attrName = attrsLst[i];
						Method method = pMap.get(attrName);

						try {
							Object attrVal = method.invoke(elem);

							if (attrVal != null) {
								rsStr += attrVal;
							}
						} catch (Exception e) {
						}

						if (i != attrsLst.length - 1) {
							rsStr += sepInfo;
						}
					}

					if (j != lst.size() - 1) {
						rsStr += sepElem;
					}
				}
			}
		}

		return rsStr;
	}

	private static Map<String, Method> createMethodsMap(Class cls, String[] attrsLst) {
		Map<String, Method> pMap = new HashMap<String, Method>();

		for (int i = 0; i < attrsLst.length; ++i) {
			String attrName = attrsLst[i];
			Method met = findGetMethodByName(cls, attrName);

			pMap.put(attrName, met);
		}

		return pMap;
	}

	public static <T> Method findGetMethodByName(Class<T> cls, String attrName) {
		Method method = null;

		try {
			method = findMethodByName(cls, "get" + attrName);
		} catch (Exception e) {
		}

		return method;
	}

	public static Object invokeMethod(Object obj, String methodName, InvokeParam... params) {
		try {
			Class[] cTypes = null;
			Object[] cValues = null;

			if (params != null) {
				List<Class> pTypes = new ArrayList<Class>();
				List<Object> pValues = new ArrayList<Object>();

				for (InvokeParam param : params) {

					if (param != null) {
						Class pCls = param.getCls();
						pTypes.add(pCls);

						Object pVal = param.getValue();
						pValues.add(pVal);
					}
				}

				cTypes = pTypes.toArray(new Class[pTypes.size()]);
				cValues = pValues.toArray(new Object[pValues.size()]);
			}

			Class cls = obj.getClass();
			Method method = cls.getDeclaredMethod(methodName, cTypes);

			return method.invoke(obj, cValues);
		} catch (Exception e) {
			return null;
		}
	}

	public static Method findMethodByName(Class cls, String methName) {
		Method methFound = null;
		Method[] methods = cls.getMethods();

		for (int i = 0; i < methods.length; i++) {
			String metName = methods[i].getName();

			if (metName.compareToIgnoreCase(methName) == 0) {
				methFound = methods[i];
				break;
			}
		}

		return methFound;
	}

	public static Object getValueByCmpx(Object obj, String cmpx) {

		if (obj != null) {

			if (isEmpty(cmpx)) {
				return null;
			}

			Object obj2 = obj;
			StringTokenizer tkn = new StringTokenizer(cmpx, ".");

			if (tkn.countTokens() > 0) {

				while (tkn.hasMoreTokens()) {
					String attrName = tkn.nextToken();
					obj2 = invokeGetMethod(obj2, attrName);

					if (obj2 == null) {
						break;
					}
				}
			} else {
				obj2 = invokeGetMethod(obj, cmpx);
			}

			return obj2;
		}

		return null;
	}

	public static Object invokeGetMethod(Object obj, String attrName) {
		String tMetName = "invokeGetMethod";
		Object cvoAttrValue = null;

		try {
			Method method = findMethodByName(obj, "get" + attrName);

			if (method != null) {
				Class retCls = method.getReturnType();

				if (retCls.isInstance(java.util.Date.class.newInstance())) {
					java.util.Date data = (java.util.Date) method.invoke(obj);
					cvoAttrValue = dateToString(data, false);
				} else {
					cvoAttrValue = method.invoke(obj);
				}
			}
		} catch (Exception e) {
		}

		return cvoAttrValue;
	}

	public static Method findMethodByName(Object obj, String methName) {
		return findClassMethodByName(obj.getClass(), methName);
	}

	public static Method findClassMethodByName(Class cls, String methName) {
		Method methFound = null;
		Method[] methods = cls.getMethods();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().compareToIgnoreCase(methName) == 0) {
				methFound = methods[i];
				break;
			}
		}

		return methFound;
	}

	public static Method findMethodByName(Object obj, String methName, Class parameter) {
		Method methFound = null;
		Method[] methods = obj.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().compareToIgnoreCase(methName) == 0) {
				Class[] parameters = methods[i].getParameterTypes();

				if (parameters != null && parameters.length == 1 && parameters[0].isAssignableFrom(parameter)) {
					methFound = methods[i];
					break;
				}
			}
		}

		return methFound;
	}

	public static class InvokeParam {
		private Class cls = null;
		private Object value = null;

		public InvokeParam(Class cls, Object value) {
			this.cls = cls;
			this.value = value;
		}

		public Class getCls() {
			return cls;
		}

		public void setCls(Class cls) {
			this.cls = cls;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	public static String xmlMessageFromObject(Object obj) {
		String xmlString = null;
		if (obj != null) {
			try {
				JAXBContext context = JAXBContext.newInstance(obj.getClass());
				Marshaller m = context.createMarshaller();

				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				StringWriter sw = new StringWriter();
				m.marshal(obj, sw);
				xmlString = sw.toString();

			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return xmlString;
	}
	
	public static String getValueFromHeader(NodeList nodeList, String value) {
		if(nodeList != null && nodeList.getLength() > 0) {
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				
				if(node.getLocalName() != null) {
					if(node.getLocalName().equals(value)) {
		            	return node.getTextContent();
		            }
		            
		            String valueFound =  getValueFromHeader(node.getChildNodes(), value);
		            if(valueFound != null) {
		            	return valueFound;
		            }
				}
		    }
		}
		return null;
	}

	public static NodeList getUsernameTokenFromHeader(NodeList nodeList) {
		if(nodeList != null && nodeList.getLength() > 0) {
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if(node.getLocalName() != null) {
					if(node.getLocalName().equals("UsernameToken")) {
						return node.getChildNodes();
					}

					NodeList valueFound =  getUsernameTokenFromHeader(node.getChildNodes());
					if(valueFound != null) {
						return valueFound;
					}
				}
			}
		}
		return null;
	}
	
}
