/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;

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

    public static final String SEPARATOR = ";";

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

    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return (str != null && str.trim().length() > 0);
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

    public static boolean listIsNullOrEmpty(List<?> elenco) {
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

    public static Timestamp toTimestampFromStringDDMMYYYY(String value) {
        try {

            String giorno = value.substring(0, 2);
            String mese = value.substring(3, 5);
            String anno = value.substring(6, 10);

            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date lFromDate1 = datetimeFormatter1
                    .parse(anno + "-" + mese + "-" + giorno);
            Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());

            return fromTS1;
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp toTimestampFromStringDDMMYYYYEndOfDay(String value) {
        try {

            String giorno = value.substring(0, 2);
            String mese = value.substring(3, 5);
            String anno = value.substring(6, 10);

            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lFromDate1 = datetimeFormatter1
                    .parse(anno + "-" + mese + "-" + giorno + " " + "23:59:59");
            Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());

            return fromTS1;
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToString(Date data, boolean complex) {
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

    public static String dateToString(Date data, String pattern) {
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

    public static String timestampToString(Timestamp timestamp) {
        return timestampToString(timestamp, true);
    }

    public static String timestampToString(Timestamp timestamp, boolean isOnlyDate) {
        if (timestamp != null) {
            Date date = new Date();
            date.setTime(timestamp.getTime());
            return new SimpleDateFormat(isOnlyDate ? DT_SIMPLE : DT_COMPLEX).format(date);
        } else {
            return null;
        }
    }

    public static boolean isValidTime(Timestamp dataInizioValidita, Timestamp dataFineValidita) {
        Timestamp now = Utils.sysdate();
        if (dataFineValidita == null) {
            return now.after(dataInizioValidita) && now.before(Timestamp.valueOf("9999-12-31 00:00:00"));
        } else {
            return now.after(dataInizioValidita) && now.before(dataFineValidita);
        }
    }

    public static Timestamp truncateTimestamp(Timestamp timestamp, boolean isOraInizio) {
        if (timestamp != null) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(timestamp.getTime());
            c.set(Calendar.HOUR_OF_DAY, isOraInizio ? 0 : 23);
            c.set(Calendar.MINUTE, isOraInizio ? 0 : 59);
            c.set(Calendar.SECOND, isOraInizio ? 0 : 59);
            c.set(Calendar.MILLISECOND, isOraInizio ? 0 : 999);
            timestamp.setTime(c.getTimeInMillis());
            return timestamp;
        } else {
            return null;
        }
    }

    public static boolean isDataFineValiditaValida(Timestamp dataInizioValidita, Timestamp dataFineValidita) {
        boolean isDataFineValiditaValida;
        if (dataFineValidita != null) {
            isDataFineValiditaValida = (dataInizioValidita != null && dataFineValidita.after(dataInizioValidita))
                    || (dataInizioValidita == null && dataFineValidita.after(Utils.sysdate()));
        } else {
            isDataFineValiditaValida = true;
        }
        return isDataFineValiditaValida;
    }

    public static String timestampFormatDDMMYYYY(Timestamp date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
    
}
