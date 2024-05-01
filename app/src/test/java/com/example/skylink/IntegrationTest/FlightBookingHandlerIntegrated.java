package com.example.skylink.IntegrationTest;

import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.FlightBookingHandler;
import com.example.skylink.business.Implementations.PassengerDataManager;
import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.business.Interface.iFlightBookingHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Implementations.FlightInfo;
import com.example.skylink.objects.Implementations.PassengerData;
import com.example.skylink.objects.Implementations.UserProperties;
import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.presentation.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FlightBookingHandlerIntegrated {
    private iFlightBookingHandler bookingHandler;
    private File tempDB;
    private PassengerDataManager passengerDataManager;
    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for Booking Handler");
        this.tempDB = TestUtils.copyDB();
        this.bookingHandler = new FlightBookingHandler(true);
        this.passengerDataManager = new PassengerDataManager(true);
        assertNotNull(this.bookingHandler);
    }

    @Test
    public void testSinglePassengerSingleFlightBooking() {
        List<iFlightInfo> flightInfos = new ArrayList<>();

        // Create a user
        IUserProperties user = new UserProperties("John Doe", "john.doe@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        // Create sample flight data
        iFlight outboundFlight = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        List<iFlight> outboundFlights = new ArrayList<>();
        outboundFlights.add(outboundFlight);

        iFlightInfo flightInfo = new FlightInfo();
        flightInfo.setFlight(outboundFlights);
        flightInfo.setEconOrBus("Economy");
        flightInfo.setBound("Outbound");

        // Add sample passenger data
        iPassengerData passengerData = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passengerData, "G1");
        passengerDataManager.addBooking(passengerData.getTitle(), passengerData.getFirstName(), passengerData.getLastName(), passengerData.getTelephoneNumber(), passengerData.getEmailAddress(), sessionUserID);

        flightInfo.setSeatSelected(seatSelected);

        flightInfos.add(flightInfo);

        // Add flight booking
        String bookingNumber = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 0);
        assertNotNull(bookingNumber);
        assertTrue(bookingNumber.length() < 6);
    }

    @Test
    public void testMultipleBookings() {
        List<iFlightInfo> flightInfos = new ArrayList<>();
        // Create a user
        IUserProperties user = new UserProperties("John Doe", "john.doe@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }
        // Booking 1:
        iFlight outboundFlight = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        List<iFlight> outboundFlights = new ArrayList<>();
        outboundFlights.add(outboundFlight);

        iFlightInfo flightInfo = new FlightInfo();
        flightInfo.setFlight(outboundFlights);
        flightInfo.setEconOrBus("Economy");
        flightInfo.setBound("Outbound");
        flightInfo.setBagCount(1);
        flightInfo.setPetCount(0);
        flightInfo.setWifiOption(1);
        flightInfo.setWheelchairOption(0);

        // Add sample passenger data
        iPassengerData passengerData = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passengerData, "G1");
        passengerDataManager.addBooking(passengerData.getTitle(), passengerData.getFirstName(), passengerData.getLastName(), passengerData.getTelephoneNumber(), passengerData.getEmailAddress(), sessionUserID);

        flightInfo.setSeatSelected(seatSelected);

        flightInfos.add(flightInfo);

        String bookingNumber = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 100);
        assertNotNull(bookingNumber);
        assertTrue(bookingNumber.length() < 6);

        // Booking 2:
        List<iFlightInfo> flightInfos1 = new ArrayList<>();
        iFlight outboundFlight1 = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "08/03/2024 20:47", "Boeing 777", "Gate3", "Gate9", 750, 1200);
        List<iFlight> outboundFlights1 = new ArrayList<>();
        outboundFlights1.add(outboundFlight1);

        iFlightInfo flightInfo1 = new FlightInfo();
        flightInfo1.setFlight(outboundFlights1);
        flightInfo1.setEconOrBus("Economy");
        flightInfo1.setBound("Outbound");
        flightInfo1.setBagCount(1);
        flightInfo1.setPetCount(0);
        flightInfo1.setWifiOption(1);
        flightInfo1.setWheelchairOption(0);

        // Add sample passenger data
        iPassengerData passengerData1 = new PassengerData("Mr", "Ken", "Ferems", "123456489", "ken.doe@example.com");
        HashMap<iPassengerData, String> seatSelected1 = new HashMap<>();
        seatSelected1.put(passengerData1, "G1");
        passengerDataManager.addBooking(passengerData1.getTitle(), passengerData1.getFirstName(), passengerData1.getLastName(), passengerData1.getTelephoneNumber(), passengerData1.getEmailAddress(), sessionUserID);

        flightInfo1.setSeatSelected(seatSelected1);

        flightInfos1.add(flightInfo1);

        String bookingNumber1 = bookingHandler.addConfirmBookings(sessionUserID, flightInfos1, 100);
        assertNotNull(bookingNumber1);
        assertTrue(bookingNumber1.length() < 6);

        List<iFlightInfo> bookingDetailsList = bookingHandler.getBookingDetails(sessionUserID);

        assertNotNull(bookingDetailsList);
        assertFalse(bookingDetailsList.isEmpty());

    }

    @Test
    public void testMultiplePassengersMultipleFlightsBooking() {
        List<iFlightInfo> flightInfos = new ArrayList<>();
        // Create a user
        IUserProperties user = new UserProperties("John Doe", "john.doe@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }
        List<iFlight> outboundFlights = new ArrayList<>();
        // Create sample flight data
        iFlight outboundFlight1 = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        iFlight outboundFlight2 = new Flight("AC750", "YZZ", "YHM", "07/03/2024 08:00", "07/03/2024 10:00", "Airbus A320", "Gate2", "Gate5", 300, 500);
        outboundFlights.add(outboundFlight1);
        outboundFlights.add(outboundFlight2);

        List<iFlight> inboundFlights = new ArrayList<>();
        iFlight inboundFlight1 = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "08/03/2024 20:47", "Boeing 777", "Gate3", "Gate9", 750, 1200);
        iFlight inboundFlight2 = new Flight("AC986", "YYZ", "YUL", "09/03/2024 09:30", "09/03/2024 11:00", "Airbus A330", "Gate4", "Gate7", 400, 600);
        inboundFlights.add(inboundFlight1);
        inboundFlights.add(inboundFlight2);


        iFlightInfo outboundFlightInfo = new FlightInfo();
        outboundFlightInfo.setFlight(outboundFlights);
        outboundFlightInfo.setEconOrBus("Business");
        outboundFlightInfo.setBound("Outbound");

        iFlightInfo inboundFlightInfo = new FlightInfo();
        inboundFlightInfo.setFlight(inboundFlights);
        inboundFlightInfo.setEconOrBus("Business");
        inboundFlightInfo.setBound("Inbound");

        // Add sample passenger data
        iPassengerData passenger1 = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        iPassengerData passenger2 = new PassengerData("Ms", "Alice", "Smith", "987654321", "alice.smith@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passenger1, "G1");
        seatSelected.put(passenger2, "G2");

        passengerDataManager.addBooking(passenger1.getTitle(), passenger1.getFirstName(), passenger1.getLastName(), passenger1.getTelephoneNumber(), passenger1.getEmailAddress(), sessionUserID);
        passengerDataManager.addBooking(passenger2.getTitle(), passenger2.getFirstName(), passenger2.getLastName(), passenger2.getTelephoneNumber(), passenger2.getEmailAddress(), sessionUserID);

        outboundFlightInfo.setSeatSelected(seatSelected);
        inboundFlightInfo.setSeatSelected(seatSelected);

        flightInfos.add(outboundFlightInfo);
        flightInfos.add(inboundFlightInfo);

        // Add flight booking
        String bookingNumber = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 0);
        assertNotNull(bookingNumber);
        assertTrue(bookingNumber.length() < 6);


    }

    @Test
    public void testgetMultiplePassengersMultipleFlightsBooking() {
        List<iFlightInfo> flightInfos = new ArrayList<>();
        // Create a user
        IUserProperties user = new UserProperties("John Doe", "john.doe@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }
        List<iFlight> outboundFlights = new ArrayList<>();
        // Create sample flight data
        iFlight outboundFlight1 = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        iFlight outboundFlight2 = new Flight("AC750", "YZZ", "YHM", "07/03/2024 08:00", "07/03/2024 10:00", "Airbus A320", "Gate2", "Gate5", 300, 500);
        outboundFlights.add(outboundFlight1);
        outboundFlights.add(outboundFlight2);

        List<iFlight> inboundFlights = new ArrayList<>();
        iFlight inboundFlight1 = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "08/03/2024 20:47", "Boeing 777", "Gate3", "Gate9", 750, 1200);
        iFlight inboundFlight2 = new Flight("AC943", "YEG", "YZZ", "08/04/2024 18:47", "08/04/2024 19:47", "Boeing 777", "Gate4", "Gate7", 702, 1152);
        inboundFlights.add(inboundFlight1);
        inboundFlights.add(inboundFlight2);


        iFlightInfo outboundFlightInfo = new FlightInfo();
        outboundFlightInfo.setFlight(outboundFlights);
        outboundFlightInfo.setEconOrBus("Economy");
        outboundFlightInfo.setBound("Outbound");

        iFlightInfo inboundFlightInfo = new FlightInfo();
        inboundFlightInfo.setFlight(inboundFlights);
        inboundFlightInfo.setEconOrBus("Economy");
        inboundFlightInfo.setBound("Inbound");

        // Add sample passenger data
        iPassengerData passenger1 = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        iPassengerData passenger2 = new PassengerData("Ms", "Alice", "Smith", "987654321", "alice.smith@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passenger1, "G1");
        seatSelected.put(passenger2, "G2");

        passengerDataManager.addBooking(passenger1.getTitle(), passenger1.getFirstName(), passenger1.getLastName(), passenger1.getTelephoneNumber(), passenger1.getEmailAddress(), sessionUserID);
        passengerDataManager.addBooking(passenger2.getTitle(), passenger2.getFirstName(), passenger2.getLastName(), passenger2.getTelephoneNumber(), passenger2.getEmailAddress(), sessionUserID);

        outboundFlightInfo.setSeatSelected(seatSelected);
        inboundFlightInfo.setSeatSelected(seatSelected);

        flightInfos.add(outboundFlightInfo);
        flightInfos.add(inboundFlightInfo);

        // Add flight booking
        String bookingNumber = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 0);
        assertNotNull(bookingNumber);
        assertTrue(bookingNumber.length() < 6);

        List<iFlightInfo> bookingDetailsList = bookingHandler.getBookingDetails(sessionUserID);

        assertNotNull(bookingDetailsList);
        assertFalse(bookingDetailsList.isEmpty());
    }


    @Test
    public void testGetBookingDetails() {
        // Create a user and add a booking
        String userFullname = "Hello World";
        String userEmail = "wow@w.com";
        String userPassword = "password";
        String userRePassword = "password";
        long sessionUserID = 0;

        IUserProperties user = new UserProperties(userFullname, userEmail, userPassword);
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());

        try {
            userHandler.createUser(user, userRePassword);
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        List<iFlightInfo> flightInfos = new ArrayList<>();
        iFlightInfo outboundFlightInfo = new FlightInfo();
        iFlightInfo inboundFlightInfo = new FlightInfo();

        // Create sample flight data
        List<iFlight> outboundFlights = new ArrayList<>();
        iFlight outboundFlight = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        outboundFlights.add(outboundFlight);
        List<iFlight> inboundFlights = new ArrayList<>();
        iFlight inboundFlight = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "18/03/2024 19:47", "Boeing 737", "Gate3", "Gate8", 1269, 1546);
        inboundFlights.add(inboundFlight);
        outboundFlightInfo.setFlight(outboundFlights);
        outboundFlightInfo.setEconOrBus("Economy");
        inboundFlightInfo.setFlight(inboundFlights);
        inboundFlightInfo.setEconOrBus("Economy");


        // Add sample passenger data
        iPassengerData passengerData = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passengerData, "G1");
        iPassengerData passengerDataV = passengerDataManager.addBooking(passengerData.getTitle(),passengerData.getFirstName(),passengerData.getLastName(), passengerData.getTelephoneNumber(), passengerData.getEmailAddress(),sessionUserID);

        assertNotNull(passengerData);

        outboundFlightInfo.setSeatSelected(seatSelected);
        inboundFlightInfo.setSeatSelected(seatSelected);

        outboundFlightInfo.setBound("Outbound");
        inboundFlightInfo.setBound("Inbound");

        flightInfos.add(outboundFlightInfo);
        flightInfos.add(inboundFlightInfo);

        String bookingNumbers = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 0);

        assertNotNull(bookingNumbers);
        assertTrue(bookingNumbers.length() < 6);

        List<iFlightInfo> bookingDetailsList = bookingHandler.getBookingDetails(sessionUserID);

        assertNotNull(bookingDetailsList);
        assertFalse(bookingDetailsList.isEmpty());

    }

    @Test
    public void testmultipleGetBookingDetails() {
        // Create a user and add a booking
        String userFullname = "Hello World";
        String userEmail = "wow@w.com";
        String userPassword = "password";
        String userRePassword = "password";
        long sessionUserID = 0;

        IUserProperties user = new UserProperties(userFullname, userEmail, userPassword);
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());

        try {
            userHandler.createUser(user, userRePassword);
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        List<iFlightInfo> flightInfos = new ArrayList<>();
        iFlightInfo outboundFlightInfo = new FlightInfo();
        iFlightInfo inboundFlightInfo = new FlightInfo();

        // Create sample flight data
        List<iFlight> outboundFlights = new ArrayList<>();
        iFlight outboundFlight = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        iFlight outboundFlight1 = new Flight("AC750", "YZZ", "YHM", "06/03/2024 18:47", "06/03/2024 19:47", "Bombardier Q400", "Gate4", "Gate8", 871, 1298);

        outboundFlights.add(outboundFlight);
        outboundFlights.add(outboundFlight1);

        List<iFlight> inboundFlights = new ArrayList<>();
        iFlight inboundFlight = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "18/03/2024 19:47", "Boeing 737", "Gate3", "Gate8", 1269, 1546);
        inboundFlights.add(inboundFlight);
        outboundFlightInfo.setFlight(outboundFlights);
        outboundFlightInfo.setEconOrBus("Economy");
        inboundFlightInfo.setFlight(inboundFlights);
        inboundFlightInfo.setEconOrBus("Economy");


        // Add sample passenger data
        iPassengerData passengerData = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passengerData, "G1");
        iPassengerData passengerDataV = passengerDataManager.addBooking(passengerData.getTitle(), passengerData.getFirstName(), passengerData.getLastName(), passengerData.getTelephoneNumber(), passengerData.getEmailAddress(), sessionUserID);

        assertNotNull(passengerData);

        outboundFlightInfo.setSeatSelected(seatSelected);
        inboundFlightInfo.setSeatSelected(seatSelected);

        outboundFlightInfo.setBound("Outbound");
        inboundFlightInfo.setBound("Inbound");

        flightInfos.add(outboundFlightInfo);
        flightInfos.add(inboundFlightInfo);

        String bookingNumbers = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 0);

        assertNotNull(bookingNumbers);
        assertTrue(bookingNumbers.length() < 6);

        List<iFlightInfo> bookingDetailsList = bookingHandler.getBookingDetails(sessionUserID);

        assertNotNull(bookingDetailsList);
        assertFalse(bookingDetailsList.isEmpty());
    }

        @Test
    public void testAddConfirmBooking() {
        // Create a user and add a booking
        String userFullname = "Hello World";
        String userEmail = "wow@w.com";
        String userPassword = "password";
        String userRePassword = "password";
        long sessionUserID = 0;

        IUserProperties user = new UserProperties(userFullname, userEmail, userPassword);
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());

        try {
            userHandler.createUser(user, userRePassword);
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        List<iFlightInfo> flightInfos = new ArrayList<>();
        iFlightInfo outboundFlightInfo = new FlightInfo();
        iFlightInfo inboundFlightInfo = new FlightInfo();

        // Create sample flight data
        List<iFlight> outboundFlights = new ArrayList<>();
        iFlight outboundFlight = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        outboundFlights.add(outboundFlight);
        List<iFlight> inboundFlights = new ArrayList<>();
        iFlight inboundFlight = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "18/03/2024 19:47", "Boeing 737", "Gate3", "Gate8", 1269, 1546);
        inboundFlights.add(inboundFlight);
        outboundFlightInfo.setFlight(outboundFlights);
        outboundFlightInfo.setEconOrBus("Business");
        inboundFlightInfo.setFlight(inboundFlights);
        inboundFlightInfo.setEconOrBus("Business");


        // Add sample passenger data
        iPassengerData passengerData = new PassengerData("Mr", "John", "Doe", "123456789", "john.doe@example.com");
        HashMap<iPassengerData, String> seatSelected = new HashMap<>();
        seatSelected.put(passengerData, "G1");
        iPassengerData passengerDataV = passengerDataManager.addBooking(passengerData.getTitle(),passengerData.getFirstName(),passengerData.getLastName(), passengerData.getTelephoneNumber(), passengerData.getEmailAddress(),sessionUserID);

        assertNotNull(passengerData);

        outboundFlightInfo.setSeatSelected(seatSelected);
        inboundFlightInfo.setSeatSelected(seatSelected);

        outboundFlightInfo.setBound("Outbound");
        inboundFlightInfo.setBound("Inbound");

        flightInfos.add(outboundFlightInfo);
        flightInfos.add(inboundFlightInfo);

        String bookingNumbers = bookingHandler.addConfirmBookings(sessionUserID, flightInfos, 0);

        assertNotNull(bookingNumbers);
        assertTrue(bookingNumbers.length() < 6);
    }




    @After
    public void tearDown() {
        System.out.println("Reset database.");
        // reset DB
        this.tempDB.delete();

        // clear Services
        Services.clean();
    }

}
