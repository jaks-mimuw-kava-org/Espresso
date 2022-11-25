package org.kava.espresso;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {
    @Test
    public void checkSelect() {
        Driver driver = new EspressoDriver();
        try {
            Connection con = driver.connect("jdbc:csv:src/main/resources/", null);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM simple_database.csv;");
            rs.next();
            assertEquals(rs.getString(0), "A");
            assertEquals(rs.getString(1), "B");
            rs.next();
            assertEquals(rs.getString(0), "C");
            assertEquals(rs.getString(1), "D");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
