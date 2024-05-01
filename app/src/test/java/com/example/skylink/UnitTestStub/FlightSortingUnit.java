package com.example.skylink.UnitTestStub;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.skylink.business.Implementations.FlightSorting;
import com.example.skylink.business.Implementations.SortingByDirectFlight;
import com.example.skylink.business.Implementations.SortingByEarliestDeparture;
import com.example.skylink.business.Implementations.SortingByPrice;
import com.example.skylink.business.Interface.ISortingOption;
import com.example.skylink.business.Interface.iFlightSorting;
import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Interfaces.iFlight;

import java.util.ArrayList;
import java.util.List;

public class FlightSortingUnit {

    private List<List<iFlight>> inner1;
    private List<List<iFlight>> inner2;
    private List<List<iFlight>> indirectFlight1;
    private List<List<iFlight>> indirectFlight2;


    @Before
    public void setup() {
        iFlight flight1 = new Flight("AC785", "YVR", "YEG", "08/03/2024 18:47", "12/03/2024 18:47", "Boeing 737", "Gate3", "Gate8", 1000, 1200);
        iFlight flight2 = new Flight("AC489", "YEG", "YVR", "06/03/2024 18:47", "12/03/2024 18:47", "Boeing 777", "Gate1", "Gate8", 900, 1100);
        iFlight flight3 = new Flight("AC392", "YEG", "YHM", "13/03/2024 18:47", "14/03/2024 18:47", "Boeing 777", "Gate9", "Gate9", 700, 950);
        iFlight flight4 = new Flight("AC395", "YVR", "YHM", "13/03/2024 18:47", "14/03/2024 18:47", "Boeing 725", "Gate3", "Gate2", 850, 1050);

        List<iFlight> in1 = new ArrayList<>();
        in1.add(flight1);

        List<iFlight> in2 = new ArrayList<>();
        in2.add(flight2);

        List<iFlight> in3 = new ArrayList<>();
        in3.add(flight3);

        List<iFlight> in4 = new ArrayList<>();
        in4.add(flight4);

        inner1 = new ArrayList<>();
        inner1.add(in1);            // this flight has YVR -> YEG flight

        inner2 = new ArrayList<>();
        inner2.add(in2);            // this flight has YEG -> YER flight

        indirectFlight1 = new ArrayList<>();
        indirectFlight1.addAll(inner1);
        indirectFlight1.add(in3);     // this flight has YVR -> YEG -> YHM flights

        indirectFlight2 = new ArrayList<>();
        indirectFlight2.addAll(inner2);
        indirectFlight2.add(in4);     // this flight has YEG -> YVR -> YHM flights
    }

    @Test
    public void testSortByEarliestDeparture() {
        ISortingOption sortingOption = new SortingByEarliestDeparture();
        iFlightSorting flightSorting = new FlightSorting(sortingOption);

        List<List<List<iFlight>>> flightToSort = new ArrayList<>();
        flightToSort.add(inner1);       // late departure flight added first
        flightToSort.add(inner2);       // early departure flight added last

        // sorted departure flights
        List<List<List<iFlight>>> sortedFlightList = new ArrayList<>();
        sortedFlightList.add(inner2);
        sortedFlightList.add(inner1);

        flightToSort.sort(flightSorting);      // should sort 'flightToSort' by earliest departure

        assertEquals(flightToSort, sortedFlightList);
    }

    @Test
    public void testSortByLowestPrice() {
        ISortingOption sortingOption = new SortingByPrice();
        iFlightSorting flightSorting = new FlightSorting(sortingOption);

        List<List<List<iFlight>>> flightToSort = new ArrayList<>();
        flightToSort.add(inner1);       // high price is on top
        flightToSort.add(inner2);       // low price is on bottom

        // sorted price flights
        List<List<List<iFlight>>> sortedFlightList = new ArrayList<>();
        sortedFlightList.add(inner2);
        sortedFlightList.add(inner1);

        flightToSort.sort(flightSorting);      // should sort 'flightToSort' by lowest price

        assertEquals(flightToSort, sortedFlightList);

    }

    @Test
    public void testSortByDirectFlightsFirst() {
        ISortingOption sortingOption = new SortingByDirectFlight();
        iFlightSorting flightSorting = new FlightSorting(sortingOption);

        List<List<List<iFlight>>> flightToSort = new ArrayList<>();
        flightToSort.add(indirectFlight1);       // indirect flight added first
        flightToSort.add(inner2);       // direct flight added last

        // sorted direct flight
        List<List<List<iFlight>>> sortedFlightList = new ArrayList<>();
        sortedFlightList.add(inner2);
        sortedFlightList.add(indirectFlight1);

        flightToSort.sort(flightSorting);      // should sort 'flightToSort' by direct flight first

        assertEquals(flightToSort, sortedFlightList);

    }

    @Test
    public void testSortTwoDirectFlights() {
        ISortingOption sortingOption = new SortingByDirectFlight();
        iFlightSorting flightSorting = new FlightSorting(sortingOption);

        List<List<List<iFlight>>> flightToSort = new ArrayList<>();
        flightToSort.add(indirectFlight1);       // indirect flight one
        flightToSort.add(indirectFlight2);       // indirect flight two

        // sorted flight will be same as above list since there is no direct flight
        List<List<List<iFlight>>> sortedFlightList = new ArrayList<>();
        sortedFlightList.add(indirectFlight1);
        sortedFlightList.add(indirectFlight2);

        flightToSort.sort(flightSorting);      // should return 'flightToSort' as it is since there is no direct flights

        assertEquals(flightToSort, sortedFlightList);

    }

}
