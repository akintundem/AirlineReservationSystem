package com.example.skylink.business.Implementations;

import com.example.skylink.application.Services;
import com.example.skylink.business.Interface.iPlaneConfiguration;
import com.example.skylink.objects.Interfaces.iAircraft;
import com.example.skylink.persistence.Interfaces.IFlightDB;

import java.util.Map;

public class PlaneConfiguration implements iPlaneConfiguration {
    IFlightDB flightHSQLDB;
    public PlaneConfiguration(boolean forProduction){
        this.flightHSQLDB = Services.getFlightDatabase();
    }
    public PlaneConfiguration(IFlightDB flightHSQLDB){
        this.flightHSQLDB = flightHSQLDB;
    }
    public String[] getPlaneConfiguration(String airCraftName, String econOrBus) {
        Map<String, iAircraft> allFlights = flightHSQLDB.getAircraftMap();
        if (allFlights.containsKey(airCraftName)  && ("Economy".equals(econOrBus) || "Business".equals(econOrBus))) {
            iAircraft aircraft = allFlights.get(airCraftName);
            if(aircraft != null){
                int busnumSeatPerRow = aircraft.getNumSeatPerRowBusiness();
                int busnumRows = aircraft.getNumRowsBusiness();
                int econnumSeatPerRow = aircraft.getNumSeatPerRowEcon();
                int econnumRows = aircraft.getNumRowsEcon();
                return new String[]{airCraftName, String.valueOf(busnumSeatPerRow), String.valueOf(busnumRows),
                        String.valueOf(econnumSeatPerRow), String.valueOf(econnumRows)};
            }
        }
        return null;
    }
}