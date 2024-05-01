package com.example.skylink.UnitTestStub;

import static org.junit.Assert.*;

import com.example.skylink.business.Implementations.PaymentHandler;
import com.example.skylink.objects.Implementations.TripInvoice;
import com.example.skylink.persistence.Implementations.stub.PaymentStub;
import org.junit.Before;
import org.junit.Test;

public class PaymentHandlerUnit {

    private PaymentHandler paymentHandler;

    @Before
    public void setUp() {
        PaymentStub paymentStub = new PaymentStub();
        paymentHandler = new PaymentHandler(paymentStub);
        // Set up any other necessary dependencies
    }

    @Test
    public void testAddPayment_Successful() {
        // Given
        TripInvoice tripInvoice = new TripInvoice(1, 100);

        // When
        boolean result = paymentHandler.addPayment(tripInvoice, 1);

        // Then
        assertTrue(result);
        // Add additional assertions or verifications if needed
    }

    @Test
    public void testAddPayment_InvalidSessionUserID() {
        // Given
        TripInvoice tripInvoice = new TripInvoice(1L, 200);

        // When
        boolean result = paymentHandler.addPayment(tripInvoice, 2L); // Different session ID

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

}

