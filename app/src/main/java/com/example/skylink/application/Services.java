package com.example.skylink.application;

import com.example.skylink.persistence.Implementations.hsqldb.BookingHSQLDB;
import com.example.skylink.persistence.Implementations.hsqldb.FlightBookingHSQLDB;
import com.example.skylink.persistence.Implementations.hsqldb.FlightHSQLDB;
import com.example.skylink.persistence.Implementations.hsqldb.PaymentHSQLDB;
import com.example.skylink.persistence.Implementations.hsqldb.UserHSQLDB;
import com.example.skylink.persistence.Interfaces.IFlightDB;
import com.example.skylink.persistence.Interfaces.IPaymentDB;
import com.example.skylink.persistence.Interfaces.IUserDB;
import com.example.skylink.persistence.Interfaces.iBookingDB;
import com.example.skylink.persistence.Interfaces.iFlightBookingDB;

public class Services {

    private static IFlightDB flightDatabase = null;
    private static IUserDB userDatabase = null;
    private static iBookingDB bookDatabase = null;
    private static IPaymentDB paymentDatabase = null;
    private static iFlightBookingDB flightBookingDatabase = null;

    public static synchronized IFlightDB getFlightDatabase() {
        if (flightDatabase == null) {
            flightDatabase = new FlightHSQLDB(Main.getDBPathName()).initialize();
        }
        return flightDatabase;
    }

    public static synchronized IUserDB getUserDatabase() {
        if (userDatabase == null) {
            userDatabase = new UserHSQLDB(Main.getDBPathName()).initialize();
        }
        return userDatabase;
    }

    public static synchronized iBookingDB getBookDatabase() {
        if (bookDatabase == null) {
            bookDatabase = new BookingHSQLDB(Main.getDBPathName()).initialize();
        }
        return bookDatabase;
    }

    public static synchronized IPaymentDB getPaymentDatabase() {
        if (paymentDatabase == null) {
            paymentDatabase = new PaymentHSQLDB(Main.getDBPathName()).initialize();
        }
        return paymentDatabase;
    }

    public static synchronized iFlightBookingDB getFlightBookingDB() {
        if (flightBookingDatabase == null) {
            flightBookingDatabase = new FlightBookingHSQLDB(Main.getDBPathName()).initialize();
        }
        return flightBookingDatabase;
    }


    public static synchronized void clean() {
        flightDatabase = null;
        userDatabase = null;
        bookDatabase = null;
        paymentDatabase = null;
        flightBookingDatabase = null;
    }
}
