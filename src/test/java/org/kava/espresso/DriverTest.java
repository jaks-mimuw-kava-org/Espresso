package org.kava.espresso;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {
    @Test
    public void checkSelect() {
        String url = "jdbc:csv:src/main/resources/";
        String query = "SELECT * FROM simple_database.csv;";
        try {
            Connection con = DriverManager.getConnection(url);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
