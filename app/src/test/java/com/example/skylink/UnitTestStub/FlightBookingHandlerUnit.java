package com.example.skylink.UnitTestStub;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.skylink.business.Implementations.FlightBookingHandler;
import com.example.skylink.business.Interface.iFlightBookingHandler;
import com.example.skylink.objects.Implementations.BookingInfo;
import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Implementations.FlightInfo;
import com.example.skylink.objects.Implementations.PassengerData;
import com.example.skylink.objects.Interfaces.iBookingInfo;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.IFlightDB;
import com.example.skylink.persistence.Interfaces.iBookingDB;
import com.example.skylink.persistence.Interfaces.iFlightBookingDB;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightBookingHandlerUnit {

    private iFlightBookingDB mockFlightBookingDB;
    private iBookingDB mockBookingDB;
    private IFlightDB mockFlightDB;

    private iFlightInfo flightInfoMock;
    private iFlight flightMock;
    private iFlightBookingHandler flightBookingHandler;
    @Before
    public void setUp() {
        // mocking database classes
        mockFlightBookingDB = mock(iFlightBookingDB.class);
        mockBookingDB = mock(iBookingDB.class);
        mockFlightDB = mock(IFlightDB.class);

        // mocking objects
        flightInfoMock = mock(FlightInfo.class);
        flightMock = mock(Flight.class);

        // initializing class to test
        flightBookingHandler = new FlightBookingHandler(mockFlightBookingDB, mockBookingDB, mockFlightDB);

    }

    @Test
    public void testStoreAddonsSuccess() {
        // Mock flightInfoList
        List<iFlightInfo> flightInfoListMock = new ArrayList<>();
        flightInfoListMock.add(flightInfoMock);

        iFlightBookingHandler flightBookingHandler = new FlightBookingHandler();

        // Call the method
        flightBookingHandler.storeAddons(1, 2, 1, 0, flightInfoListMock);

        // Verify that the method sets the attributes of flightInfo
        verify(flightInfoMock).setBagCount(1);
        verify(flightInfoMock).setPetCount(2);
        verify(flightInfoMock).setWifiOption(1);
        verify(flightInfoMock).setWheelchairOption(0);

    }

    @Test
    public void testStoreAddonsFail() {
        // Mock flightInfoList
        List<iFlightInfo> flightInfoListMock = null;

        iFlightBookingHandler flightBookingHandler = new FlightBookingHandler();

        // Call the method
        flightBookingHandler.storeAddons(1, 2, 1, 0, flightInfoListMock);

        // verify set methods are not executed since flightInfo list is null
        verifyNoMoreInteractions(flightInfoMock);

    }

    @Test
    public void testAddBookingEconFlightSuccess() {
        // Mock flight list
        List<iFlight> flights = new ArrayList<>();
        flights.add(flightMock);

        // Mock flightInfo list
        List<iFlightInfo> flightInfoListMock = new ArrayList<>();
        flightInfoListMock.add(flightInfoMock);

        when(flightInfoMock.getEconOrBus()).thenReturn("Economy");
        when(flightInfoMock.getSeatSelected()).thenReturn(new HashMap<iPassengerData, String>());
        when(flightInfoMock.getBound()).thenReturn("Outbound");
        when(flightInfoMock.getFlight()).thenReturn(flights);


        String bookingNumber = flightBookingHandler.addConfirmBookings(0, flightInfoListMock, 50);

        Assert.assertNotNull(bookingNumber);

        // verify the flight is actually getting booked
        verify(mockFlightBookingDB).addFlightBooking(0L, "Outbound", flightMock, 0, bookingNumber, "Economy", 0, 0, 0, 0);
    }

    @Test
    public void testAddBookingBusnFlightSuccess() {
        // Mock flight list
        List<iFlight> flights = new ArrayList<>();
        flights.add(flightMock);

        // Mock flightInfo list
        List<iFlightInfo> flightInfoListMock = new ArrayList<>();
        flightInfoListMock.add(flightInfoMock);

        when(flightInfoMock.getEconOrBus()).thenReturn("Business");
        when(flightInfoMock.getSeatSelected()).thenReturn(new HashMap<iPassengerData, String>());
        when(flightInfoMock.getBound()).thenReturn("Outbound");
        when(flightInfoMock.getFlight()).thenReturn(flights);


        String bookingNumber = flightBookingHandler.addConfirmBookings(0, flightInfoListMock, 50);

        Assert.assertNotNull(bookingNumber);

        // verify the flight is actually getting booked
        verify(mockFlightBookingDB).addFlightBooking(0L, "Outbound", flightMock, 0, bookingNumber, "Business", 0, 0, 0, 0);
    }

    @Test
    public void testGetBooking() {

        // mock passengerData for hashmap
        iPassengerData mockPassengerData = mock(PassengerData.class);

        // setup bookingInfo list that stores trip's detail
        iBookingInfo bookingInfo = new BookingInfo(123, "AC489", "Economy", "Outbound", 1, 0, 0, 0);
        List<iBookingInfo> bookingInfoList = new ArrayList<>();
        bookingInfoList.add(bookingInfo);

        // setup list that contains flight number
        List<String> flightNumbers = new ArrayList<>();
        flightNumbers.add("AC489");

        // setup flight list that contains flight detail
        List<iFlight> flightList = new ArrayList<>();
        flightList.add(flightMock);

        // setup hashmap that contains passenger data mapped to their sear number
        HashMap<iPassengerData, String> passengers = new HashMap<>();
        passengers.put(mockPassengerData, "2C");

        when(mockFlightBookingDB.getBookingInfoByUserId(0)).thenReturn(bookingInfoList);
        when(mockFlightBookingDB.getFlightsByUserId(bookingInfo.getDirection(),bookingInfo.getBookingNumber())).thenReturn(flightNumbers);
        when(mockFlightDB.getFlightsByFlightNumbers(flightNumbers)).thenReturn(flightList);
        when(mockBookingDB.getPassengersWithSeatNumbers(bookingInfo.getBookingNumber())).thenReturn(passengers);

        List<iFlightInfo> flightInfoList = flightBookingHandler.getBookingDetails(0);

        Assert.assertNotNull(flightInfoList);
    }
}