/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configuration {

    private static Configuration instance;
    private Properties properties;
    private InputStream inputStream;

    private Configuration() throws IOException {
        properties = new Properties();
	
        // Da scommentare per i test in locale
        
        inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
		properties.load(inputStream);
		
		
        //FINE 	
        
        // Da scommentare per PROD-TST-DEV
        
//		String propFile = System.getProperty("PROP_FILE");
//		Properties props = new Properties();
//		properties.load(new FileInputStream(propFile));
	
		//FINE */		
        
    }

    private static Configuration getInstance() {
        if (instance == null) {
            try {
                instance = new Configuration();
            } catch (IOException e) {
                System.err.println("Error in loading configuration");
            }
        }
        return instance;
    }

    private String valueOf(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key) {
        return getInstance().valueOf(key);
    }

}