package com.example.skylink.business.Interface;

import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Implementations.FlightSearch;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightSearch;

import java.util.HashMap;
import java.util.List;

public interface iAirportPath {


    HashMap<String, List<List<List<iFlight>>>> findFlights(iFlightSearch flightSearch);


}
