package org.kava.espresso;

import java.sql.*;

import java.util.Properties;
import java.util.logging.Logger;

public class EspressoDriver implements Driver {
    private static final Driver registeredDriver;
    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 0;

    private static final String URL_PREFIX = "jdbc:espresso//";

    static {
        try {
            registeredDriver = new EspressoDriver();
            DriverManager.registerDriver(registeredDriver);
        }
        catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public EspressoDriver() {}

    private Properties parseURL(String URL, Properties properties) throws SQLException {
        if (!URL.startsWith(URL_PREFIX)) {
            throw new SQLException("URL prefix is not valid.");
        }
        int splitPosition;
        Properties newProperties = new Properties(properties);
        URL = URL.substring(URL_PREFIX.length());


        if (URL.contains("@")) {
            splitPosition = URL.indexOf("@");
            String userToken =  URL.substring(0, splitPosition);
            URL = URL.substring(splitPosition + 1);
            String user, password;
            if (userToken.contains(":")) {
                splitPosition = userToken.indexOf(":");
                user = userToken.substring(0, splitPosition);
                password = userToken.substring(splitPosition + 1);
            }
            else {
                password = "";
                user = userToken;
            }
            newProperties.setProperty("user", user);
            newProperties.setProperty("password", password);
        }
        else {
            newProperties.setProperty("user", "SA");
            newProperties.setProperty("password", "");
        }

        String databaseToken = "xdb";
        if (URL.contains("/")) {
            splitPosition = URL.indexOf("/");
            databaseToken = URL.substring(splitPosition + 1);
            URL = URL.substring(0, splitPosition);
        }
        newProperties.setProperty("db", databaseToken);

        String portToken = "9001";
        if (URL.contains(":")) {
            splitPosition = URL.indexOf(":");
            portToken = URL.substring(splitPosition + 1);
            URL = URL.substring(0, splitPosition);
        }
        newProperties.setProperty("port", portToken);

        String hostToken = "localhost";
        if (!URL.isEmpty()) {
            hostToken = URL;
        }
        newProperties.setProperty("host", hostToken);

        System.out.println(newProperties);
        return newProperties;
    }

    @Override
    public Connection connect(String URL, Properties properties) throws SQLException {
        Properties connectionProperties = parseURL(URL, properties);
        return new EspressoConnection(connectionProperties);
    }

    @Override
    public boolean acceptsURL(String URL) throws SQLException {
        parseURL(URL, null);
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String URL, Properties properties) throws SQLException {
        if (!acceptsURL(URL)) {
            return new DriverPropertyInfo[0];
        }

        DriverPropertyInfo[] driverPropertyInfo   = new DriverPropertyInfo[5];
        DriverPropertyInfo   singlePropertyInfo;
        String[] propertiesNames = new String[]{"host", "port", "database", "user", "password"};
        String[] defaultValues = new String[]{"localhost", "9001", "xdb", "SA", ""};

        if (properties == null) {
            properties = new Properties();
        }

        for (int i = 0; i < driverPropertyInfo.length; i++) {
            singlePropertyInfo = new DriverPropertyInfo(propertiesNames[i], null);
            singlePropertyInfo.value = properties.getProperty(propertiesNames[i], defaultValues[i]);
            singlePropertyInfo.required = false;
            driverPropertyInfo[i] = singlePropertyInfo;
        }

        return driverPropertyInfo;
    }

    @Override
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("This driver does not use java.util.logging.");
    }

}
