module Espresso {
    requires java.sql;
    provides java.sql.Driver with org.kava.espresso.EspressoDriver;
}