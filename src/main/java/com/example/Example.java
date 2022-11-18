package com.example;

import org.kava.espresso.EspressoDriver;

import java.sql.*;

public class Example {

    public static void main(String[] args) {
        Driver driver = new EspressoDriver();
        try {
            Connection con = driver.connect("jdbc:csv:databases/", null);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM simple_database.csv;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
