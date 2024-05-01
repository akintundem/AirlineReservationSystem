package com.example.skylink.objects.Interfaces;

import com.example.skylink.objects.Implementations.FlightSearch;

import java.util.HashMap;
import java.util.List;

public interface iFlightSearch {
    // Getter methods
    String getFlightDept();
    String getFlightArrival();
    String getFlightDeptDate();
    String getFlightReturnDate();
    int getTotalPassengers();
    boolean isOneWay();

    // Setter methods
    void setFlightDept(String flightDept);
    void setFlightArrival(String flightArrival);
    void setFlightDeptDate(String flightDeptDate);
    void setFlightReturnDate(String flightReturnDate);
    void setTotalPassengers(int totalPassengers);
    void setOneWay(boolean isOneWay);
}
