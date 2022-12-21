/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.csi.solconfig.batchabilitazconfig.configuration.Configuration;

public class DBConnectionManager {

    public static final String DEFAULT_DRIVER_CLASS_NAME = Configuration.get("db.driverClassName");
    public static final String USERNAME = Configuration.get("db.username");
    public static final String PASSWORD = Configuration.get("db.password");
    public static final String JDBC_URL = Configuration.get("db.url");

    //-----------------------------------------------------------

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
       /* System.out.println(DEFAULT_DRIVER_CLASS_NAME);
        System.out.println(USERNAME);
        System.out.println(PASSWORD);
        System.out.println(JDBC_URL);*/
        return getConnection(DEFAULT_DRIVER_CLASS_NAME, USERNAME, PASSWORD);
    }

    private static Connection getConnection(String driverClassName, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        Connection connection = DriverManager.getConnection(JDBC_URL, user, password);
        return connection;
    }

    //-----------------------------------------------------------
}
