package com.example.skylink.IntegrationTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.business.Implementations.AirportPath;
import com.example.skylink.business.Interface.iAirportPath;
import com.example.skylink.objects.Implementations.FlightSearch;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.example.skylink.application.Services;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AirportPathIntegrated {

    iAirportPath airportPath;
    private File tempDB;
    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for airportPath");
        this.tempDB = TestUtils.copyDB();
        this.airportPath = new AirportPath(true);
        assertNotNull(this.airportPath);
    }



    @Test
    public void testFindFlights_OneWay() {
        iFlightSearch flightSearch = new FlightSearch("YEG", "YVR", "06/05/2024", null, 2, true);
        HashMap<String, List<List<List<iFlight>>>> itinerary = airportPath.findFlights(flightSearch);

        assertNotNull(itinerary);
        assertTrue(itinerary.containsKey("Outbound"));
        assertFalse(itinerary.containsKey("Inbound"));

        List<List<List<iFlight>>> outboundFlights = itinerary.get("Outbound");
        assertNotNull(outboundFlights);
        assertFalse(outboundFlights.isEmpty());
    }

    @Test
    public void testFindFlights_Return() {
        iFlightSearch flightSearch = new FlightSearch("YEG", "YVR", "06/05/2024", "08/05/2024", 2, false);
        HashMap<String, List<List<List<iFlight>>>> itinerary = airportPath.findFlights(flightSearch);

        assertNotNull(itinerary);
        assertTrue(itinerary.containsKey("Outbound"));
        assertTrue(itinerary.containsKey("Inbound"));

        List<List<List<iFlight>>> outboundFlights = itinerary.get("Outbound");
        assertNotNull(outboundFlights);
        assertFalse(outboundFlights.isEmpty());
    }

    @Test
    public void testFindFlights_NoPaths() {
        // Test when no paths are available between airports
        iFlightSearch flightSearch = new FlightSearch(null, null, "06/03/2024", "06/03/2024",2,true);
        HashMap<String, List<List<List<iFlight>>>> itinerary = airportPath.findFlights(flightSearch);
        assertNull(itinerary);

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
