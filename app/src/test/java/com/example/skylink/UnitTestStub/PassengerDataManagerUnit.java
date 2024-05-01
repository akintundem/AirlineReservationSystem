package com.example.skylink.UnitTestStub;


import static junit.framework.TestCase.assertEquals;

import com.example.skylink.business.Implementations.PassengerDataManager;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Implementations.stub.BookingStub;
import com.example.skylink.persistence.Interfaces.iBookingDB;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PassengerDataManagerUnit {
    private iBookingDB bookingDatabase;
    private PassengerDataManager passengerDataManager;

    @Before
    public void setUp() {
        bookingDatabase = new BookingStub();
        passengerDataManager = new PassengerDataManager(bookingDatabase);
    }

    @Test
    public void testAddBooking() {
        iPassengerData passengerData = passengerDataManager.addBooking("Mr.", "John", "Doe", "123456789", "john.doe@example.com", 0);

        assertNotNull(passengerData);
        assertEquals("Mr.", passengerData.getTitle());
        assertEquals("John", passengerData.getFirstName());
        assertEquals("Doe", passengerData.getLastName());
        assertEquals("123456789", passengerData.getTelephoneNumber());
        assertEquals("john.doe@example.com", passengerData.getEmailAddress());
    }

    @Test
    public void testFindExistingBooking() {
        iPassengerData existingPassengerData = passengerDataManager.addBooking("Ms.", "Jane", "Smith", "987654321", "jane.smith@example.com", 1);

        boolean found = passengerDataManager.findBooking("Ms.", "Jane", "Smith", "987654321", "jane.smith@example.com", 1);

        assertTrue(found);
    }

    @Test
    public void testFindNonExistingBooking() {
        boolean found = passengerDataManager.findBooking("Mr.", "Non", "Existing", "555555555", "non.existing@example.com", 2);

        assertFalse(found);
    }

    @Test
    public void testAddBookingWithNullEmail() {
        iPassengerData passengerData = passengerDataManager.addBooking("Ms.", "Alice", "Wonderland", "123456789", null, 2);

        assertNull(passengerData);
    }

    @Test
    public void testAddBookingWithEmptyName() {
        iPassengerData passengerData = passengerDataManager.addBooking("Dr.", "", "", "987654321", "doctor@example.com", 3);

        assertNotNull(passengerData);
        assertEquals("Dr.", passengerData.getTitle());
        assertEquals("", passengerData.getFirstName()); // Adjust based on your requirements
        assertEquals("", passengerData.getLastName());  // Adjust based on your requirements
        assertEquals("987654321", passengerData.getTelephoneNumber());
        assertEquals("doctor@example.com", passengerData.getEmailAddress());
    }

    @Test
    public void testFindBookingWithEmptyPhoneNumber() {
        iPassengerData existingPassengerData = passengerDataManager.addBooking("Prof.", "Charles", "Xavier", "", "professor@example.com", 5);

        boolean found = passengerDataManager.findBooking("Prof.", "Charles", "Xavier", "", "professor@example.com", 5);

        assertTrue(found);
    }

}
