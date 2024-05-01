package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.iFlightSearch;

public class FlightSearch implements iFlightSearch {

    private String flightDept;
    private String flightArrival;
    private String flightDeptDate;
    private String flightReturnDate;
    private boolean isOneWay;
    private int  totalPassengers;

    public FlightSearch(String flightDept, String flightArrival, String flightDeptDate, String flightReturnDate, int totalPassengers, boolean isOneWay) {
        this.flightDept = flightDept;
        this.flightArrival = flightArrival;
        this.flightDeptDate = flightDeptDate;
        this.flightReturnDate = flightReturnDate;
        this.isOneWay = isOneWay;
        this.totalPassengers = totalPassengers;
    }
    public FlightSearch(){

    }

    public int getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(int totalPassengers) {
        this.totalPassengers = totalPassengers;
    }
    public String getFlightDept() {
        return flightDept;
    }

    public void setFlightDept(String flightDept) {
        this.flightDept = flightDept;
    }

    public String getFlightArrival() {
        return flightArrival;
    }

    public void setFlightArrival(String flightArrival) {
        this.flightArrival = flightArrival;
    }

    public String getFlightDeptDate() {
        return flightDeptDate;
    }

    public void setFlightDeptDate(String flightDeptDate) {
        this.flightDeptDate = flightDeptDate;
    }

    public String getFlightReturnDate() {
        return flightReturnDate;
    }

    public void setFlightReturnDate(String flightReturnDate) {
        this.flightReturnDate = flightReturnDate;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public void setOneWay(boolean oneWay) {
        isOneWay = oneWay;
    }
}
