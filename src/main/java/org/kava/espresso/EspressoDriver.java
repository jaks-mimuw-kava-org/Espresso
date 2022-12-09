package org.kava.espresso;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.*;
public class EspressoDriver implements Driver {
    private static final Driver registeredDriver;

    private static final Pattern URL_PATTERN = Pattern.compile("^jdbc:csv//([a-z]+)@(\\d+)/path=(.*)$");
    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 0;

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

    private Properties parseURL(String URL) throws SQLException {
        if (URL == null) {
            throw new SQLException("URL is null.");
        }
        Matcher URLMatcher = URL_PATTERN.matcher(URL);
        if (URLMatcher.find()) {
            Properties props = new Properties();
            props.setProperty("HOST", URLMatcher.group(1));
            props.setProperty("PORT", URLMatcher.group(2));
            props.setProperty("PATH", URLMatcher.group(3));
            return props;
        }
        throw new SQLException("URL is not valid.");
    }


    @Override
    public Connection connect(String URL, Properties properties) throws SQLException {
        Properties URLProperties = parseURL(URL);
        String databasePathString = URLProperties.getProperty("PATH");
        Path databasePath = Paths.get(databasePathString);
        return new EspressoConnection(databasePath, URLProperties);
    }

    @Override
    public boolean acceptsURL(String URL) throws SQLException {
        parseURL(URL);
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String URL, Properties properties) throws SQLException {
        return new DriverPropertyInfo[0];
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
