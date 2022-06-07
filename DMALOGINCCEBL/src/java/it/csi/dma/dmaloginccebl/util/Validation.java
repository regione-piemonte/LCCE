/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author Cristiano Masiero Project : dmawacm 20/set/2011 , 11:21:47
 * 
 * @version $Id$
 */
public class Validation {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_PATTERN = "^(\\+)?[0-9]+$"; 
	private static final String NAME_SURNAME_PATTERN = "[a-zA-Z][a-zA-Z'_\\s]*";
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	final static String DATE_FORMAT = "yyyyMMddHHmmss";
	
	public static boolean EmailAddress (final String hex)
	{
	    
	    if (hex == null || hex.trim().equals(""))
            return true;
	    
	    if(hex.indexOf("@") == -1)
	    	return false;
	    if(hex.indexOf(".") == -1)
	    	return false;
	    
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
	
	}
	
	public static void main(String[] args) {
		String mail = "maria.rossini1csi.it";
		boolean valid = EmailAddress(mail);
		System.out.println(valid);
	}
	
	public static boolean Phone (final String hex)
	{
	    
	    if (hex == null || hex.trim().equals(""))
            return true;
	    
	    Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
	
	}
	
	public static boolean CodiceFiscale (final String codiceFiscale)
	{
	    
	    if (codiceFiscale != null && codiceFiscale.length() == 16)
	        return true;
	    else
	        return false;
	    
	}
	
	public static boolean NameOrSurname4RicercaPaziente (final String hex){
	    
	    
        if ( hex == null || hex.length() < 2) {
            return false;
        }

        Pattern pattern = Pattern.compile(NAME_SURNAME_PATTERN);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
	    
	}
	
	
	public static boolean LuogoDiNascita (final String str){
	    
	    if (str == null || str.trim().equals(""))
	        return false;
	    else
	        return true;
	    
	}
	
	public static boolean DataDiNascita (final String gg, final String mm, final String aaaa){
	    
	    boolean result = true ;
        
	    String str = gg + "/" + mm + "/" + aaaa;
        try {
            sdf.parse(str);
        } catch (ParseException e) {
            result = false;
        }
	    
	    return result;
	    
	}
	
	public static boolean isDateValid(String date) 
	{
	        try {
	            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}
	
	
	
}
