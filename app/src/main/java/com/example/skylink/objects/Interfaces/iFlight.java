package com.example.skylink.objects.Interfaces;

public interface iFlight {
    // Getter methods
    String getFlightNumber();
    String getDeparture_icao();
    String getArrival_icao();
    String getFlight_dept_date_time();
    String getFlight_arr_date_time();
    String getAirCraft_Type();
    String getDepature_Gate();
    String getArr_Gate();
    int getEconPrice();
    int getBusnPrice();

    // toString method
    String toString();
}
