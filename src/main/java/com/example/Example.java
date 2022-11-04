package src.main.java.com.example;

import src.main.java.com.espresso.EspressoDriver;

import java.sql.*;

public class Example {

    public static void main(String[] args) {
        Driver driver = new EspressoDriver();
        try {
            Connection con = driver.connect("jdbc:csv:/home/karol/IdeaProjects/Espresso/databases/", null);
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
