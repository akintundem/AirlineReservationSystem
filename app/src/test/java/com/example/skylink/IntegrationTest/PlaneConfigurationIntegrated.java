package com.example.skylink.IntegrationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.PlaneConfiguration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PlaneConfigurationIntegrated {

    private PlaneConfiguration planeConfiguration;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for Passenger Database");
        this.tempDB = TestUtils.copyDB();
        this.planeConfiguration = new PlaneConfiguration(true);
        assertNotNull(planeConfiguration);
    }



    @Test
    public void testGetPlaneConfigurationWithValidInput() {
        // Test with valid aircraft name and seat type
        String[] result = planeConfiguration.getPlaneConfiguration("Boeing 737", "Economy");

        // Verify
        assertNotNull("Result should not be null", result);
        assertEquals("Aircraft name should match", "Boeing 737", result[0]);
        assertEquals("Economy class seats should match", "5", result[1]);
        assertEquals("Business class seats should match", "7", result[2]);
        assertEquals("First class seats should match", "7", result[3]);
        assertEquals("Total seats should match", "13", result[4]);
    }

    @Test
    public void testGetPlaneConfigurationWithInvalidAircraftName() {
        // Test with invalid aircraft name
        String[] result = planeConfiguration.getPlaneConfiguration("Invalid Aircraft", "Economy");

        // Verify that the result is null for an invalid aircraft name
        assertNull("Result should be null for an invalid aircraft name", result);
    }

    @Test
    public void testGetPlaneConfigurationWithInvalidSeatType() {
        // Test with invalid seat type
        String[] result = planeConfiguration.getPlaneConfiguration("Boeing 737", "invalidSeatType");

        // Verify that the result is null for an invalid seat type
        assertNull("Result should be null for an invalid seat type", result);
    }

    @Test
    public void testGetPlaneConfigurationWithValidInputAndBusinessClass() {
        // Test with valid aircraft name and business class seat type
        String[] result = planeConfiguration.getPlaneConfiguration("Airbus A320", "Business");

        // Verify
        assertNotNull("Result should not be null", result);
        assertEquals("Aircraft name should match", "Airbus A320", result[0]);
        assertEquals("Economy class seats should match", "5", result[1]);
        assertEquals("Business class seats should match", "6", result[2]);
        assertEquals("First class seats should match", "8", result[3]);
        assertEquals("Total seats should match", "15", result[4]);
    }

    @Test
    public void testGetPlaneConfigurationWithInvalidSeatTypeAndInvalidAircraftName() {
        // Test with both invalid seat type and aircraft name
        String[] result = planeConfiguration.getPlaneConfiguration("Invalid Aircraft", "invalidSeatType");

        // Verify that the result is null for both invalid seat type and aircraft name
        assertNull("Result should be null for an invalid seat type and aircraft name", result);
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
