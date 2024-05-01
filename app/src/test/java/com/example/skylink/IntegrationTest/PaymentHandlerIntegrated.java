package com.example.skylink.IntegrationTest;

import static org.junit.Assert.*;

import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.PaymentHandler;
import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.objects.Implementations.TripInvoice;
import com.example.skylink.objects.Implementations.UserProperties;
import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.presentation.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PaymentHandlerIntegrated {

    private PaymentHandler paymentHandler;
    private File tempDB;
    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for Passenger Database");
        this.tempDB = TestUtils.copyDB();
        this.paymentHandler = new PaymentHandler(true);
        assertNotNull(this.paymentHandler);
    }

    @Test
    public void testAddPayment_Successful() {

        String userFullname = "Hello World";
        String userEmail = "wow@w.com";
        String userPassword = "password";
        String userRePassword = "password";
        long sessionUserID = 0;

        IUserProperties user = new UserProperties(userFullname, userEmail, userPassword);
        IUserHandler handler = new UserHandler(Services.getUserDatabase());

        try {
            handler.createUser(user, userRePassword);
            sessionUserID = Session.getInstance().getUserProperties().getUser_id();
        } catch (UserHandler.UserValidationException e) {
            fail("Should not fail");
        }

        // Given
        TripInvoice tripInvoice = new TripInvoice(sessionUserID, 100);

        // When
        boolean result = paymentHandler.addPayment(tripInvoice, sessionUserID);

        // Then
        assertTrue(result);
        // Add additional assertions or verifications if needed
    }

    @Test
    public void testAddPayment_InvalidSessionUserID() {
        // Given
        TripInvoice tripInvoice = new TripInvoice(2L, 150);

        // When
        boolean result = paymentHandler.addPayment(tripInvoice, 1L);    // Different session ID

        // Then
        assertFalse(result);
        // Add additional assertions or verifications if needed
    }

    @Test
    public void testAddPayment_NullTripInvoice() {
        // When
        boolean result = paymentHandler.addPayment(null, 1L);

        // Then
        assertFalse(result);
        // Add additional assertions or verifications if needed
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

