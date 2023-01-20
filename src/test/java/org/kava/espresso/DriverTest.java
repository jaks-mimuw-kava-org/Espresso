package org.kava.espresso;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class DriverTest {

    private final String connectionURL = "jdbc:espresso//SA:@localhost:9001/xdb";

    @Test
    public void connectionTest() {
        try {
            Connection connection = DriverManager.getConnection(connectionURL, null);
            Statement statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void executeQueryTest() {
        try {
            DriverManager.getConnection(connectionURL);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
