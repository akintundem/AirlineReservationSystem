package com.example.skylink.IntegrationTest;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.PassengerDataManager;
import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.objects.Implementations.UserProperties;
import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iBookingDB;
import com.example.skylink.presentation.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PassengerDataManagerIntegrated {
    private iBookingDB bookingDatabase;
    private PassengerDataManager passengerDataManager;
    private File tempDB;
    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for Passenger Database");
        this.tempDB = TestUtils.copyDB();
        this.passengerDataManager = new PassengerDataManager(true);
        assertNotNull(this.passengerDataManager);
    }

    @Test
    public void testAddBooking() {
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

        iPassengerData passengerData = passengerDataManager.addBooking("Mr.", "John", "Doe", "123456789", "john.doe@example.com", sessionUserID);

        assertNotNull(passengerData);
        assertEquals("Mr.", passengerData.getTitle());
        assertEquals("John", passengerData.getFirstName());
        assertEquals("Doe", passengerData.getLastName());
        assertEquals("123456789", passengerData.getTelephoneNumber());
        assertEquals("john.doe@example.com", passengerData.getEmailAddress());
    }

    @Test
    public void testFindExistingBooking() {
        // Create a user
        IUserProperties user = new UserProperties("Jane Smith", "jane.smith@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        iPassengerData existingPassengerData = passengerDataManager.addBooking("Ms.", "Jane", "Smith", "987654321", "jane.smith@example.com", sessionUserID);

        boolean found = passengerDataManager.findBooking("Ms.", "Jane", "Smith", "987654321", "jane.smith@example.com", sessionUserID);

        assertTrue(found);
    }

    @Test
    public void testFindNonExistingBooking() {
        boolean found = passengerDataManager.findBooking("Mr.", "Non", "Existing", "555555555", "non.existing@example.com", 0);

        assertFalse(found);
    }

    @Test
    public void testAddBookingWithNullEmail() {
        // Create a user
        IUserProperties user = new UserProperties("Alice Wonderland", "alice.wonderland@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        iPassengerData passengerData = passengerDataManager.addBooking("Ms.", "Alice", "Wonderland", "123456789", null, sessionUserID);

        assertNull(passengerData);
    }

    @Test
    public void testAddBookingWithEmptyName() {
        // Create a user
        IUserProperties user = new UserProperties("Alice Wonderland", "doctor@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        iPassengerData passengerData = passengerDataManager.addBooking("Dr.", "", "", "987654321", "doctor@example.com", sessionUserID);

        assertNotNull(passengerData);
        assertEquals("Dr.", passengerData.getTitle());
        assertEquals("", passengerData.getFirstName());
        assertEquals("", passengerData.getLastName());
        assertEquals("987654321", passengerData.getTelephoneNumber());
        assertEquals("doctor@example.com", passengerData.getEmailAddress());
    }

    @Test
    public void testFindBookingWithEmptyPhoneNumber() {
        // Create a user
        IUserProperties user = new UserProperties("Charles Xavier", "professor@example.com", "password");
        IUserHandler userHandler = new UserHandler(Services.getUserDatabase());
        long sessionUserID = 0;

        try {
            userHandler.createUser(user, "password");
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("User creation should not fail");
        }

        iPassengerData existingPassengerData = passengerDataManager.addBooking("Prof.", "Charles", "Xavier", "", "professor@example.com", sessionUserID);

        boolean found = passengerDataManager.findBooking("Prof.", "Charles", "Xavier", "", "professor@example.com", sessionUserID);

        assertTrue(found);
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
