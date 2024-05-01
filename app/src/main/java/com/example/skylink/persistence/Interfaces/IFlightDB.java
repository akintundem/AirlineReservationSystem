package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Interfaces.iAircraft;
import com.example.skylink.objects.Interfaces.iFlight;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IFlightDB {
    void addFlights();
    List<iFlight> findFlight(String departure, String arrival, String dept_time);

    Graph<String, DefaultWeightedEdge> getAirportGraph();

//    Assuming this are all of the aircraft in the world.
    Map<String, iAircraft> getAircraftMap();
    List<iFlight> getFlightsByFlightNumbers(List<String> flightNumbers);
}
