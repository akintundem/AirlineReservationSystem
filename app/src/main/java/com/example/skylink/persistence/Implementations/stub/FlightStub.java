package com.example.skylink.persistence.Implementations.stub;


import com.example.skylink.objects.Implementations.Aircraft;
import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Interfaces.iAircraft;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.persistence.Interfaces.IFlightDB;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightStub implements IFlightDB {
    private final List<iFlight> flights;
    private final Graph<String, DefaultWeightedEdge> airportGraph;
    private Map<String, iAircraft> aircraftMap =  new HashMap<>();


    public FlightStub() {
        this.airportGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
        this.flights = new ArrayList<>();
        addFlights();
        readConfig();
        addAircrafts();
    }

    public void addFlights() {
        addFlight("AC785", "YVR", "YEG", "08/03/2024 18:47", "18/03/2024 19:47", "Boeing 737", "Gate3", "Gate8", 1269, 1546);
        addFlight("AC489", "YEG", "YVR", "06/03/2024 18:47", "06/03/2024 20:47", "Boeing 777", "Gate1", "Gate8", 646, 997);
        addFlight("AC266", "YYC", "YOW", "07/03/2024 18:47", "07/03/2024 19:47", "Embraer E190", "Gate8", "Gate2", 1466, 1793);
        addFlight("AC392", "YEG", "YHM", "08/03/2024 18:47", "08/03/2024 22:47", "Boeing 777", "Gate9", "Gate9", 894, 1304);
        addFlight("AC267", "YYZ", "YWG", "06/03/2024 18:47", "06/03/2024 19:47", "Boeing 777", "Gate10", "Gate6", 901, 1159);
        addFlight("AC199", "YOW", "YHM", "07/04/2024 18:47", "07/03/2024 23:47", "Boeing 777", "Gate4", "Gate7", 873, 1077);
        addFlight("AC795", "YVR", "YHM", "08/04/2024 18:47", "08/03/2024 19:47", "Embraer E190", "Gate1", "Gate2", 849, 1214);
        addFlight("AC245", "YYZ", "YHM", "06/04/2024 18:47", "06/03/2024 21:47", "Boeing 737", "Gate8", "Gate5", 1053, 1360);
        addFlight("AC172", "YWG", "YZZ", "07/04/2024 18:47", "07/03/2024 19:47", "Airbus A320", "Gate4", "Gate6", 842, 1237);
        addFlight("AC989", "YOW", "YHM", "06/04/2024 18:47", "06/03/2024 20:47", "Embraer E190", "Gate6", "Gate10", 1325, 1622);
        addFlight("AC449", "YVR", "YZZ", "08/04/2024 18:47", "08/03/2024 19:47", "Boeing 737", "Gate4", "Gate1", 524, 1008);
        addFlight("AC350", "YYZ", "YVR", "07/04/2024 18:47", "07/03/2024 22:47", "Embraer E190", "Gate7", "Gate5", 1240, 1673);
        addFlight("AC750", "YZZ", "YHM", "06/04/2024 18:47", "06/03/2024 19:47", "Bombardier Q400", "Gate4", "Gate8", 871, 1298);
        addFlight("AC944", "YYZ", "YOW", "06/04/2024 18:47", "06/03/2024 19:47", "Boeing 777", "Gate9", "Gate3", 873, 1168);
        addFlight("AC203", "YOW", "YYZ", "08/04/2024 18:47", "08/03/2024 22:47", "Embraer E190", "Gate5", "Gate9", 836, 1297);
        addFlight("AC163", "YEG", "YHM", "07/04/2024 18:47", "07/03/2024 19:47", "Boeing 737", "Gate6", "Gate5", 1407, 1723);
        addFlight("AC943", "YEG", "YZZ", "08/04/2024 18:47", "08/03/2024 19:47", "Boeing 777", "Gate4", "Gate6", 702, 1152);
    }


    @Override
    public List<iFlight> findFlight(String departure, String arrival, String dept_time) {
        List<iFlight> results = new ArrayList<>();
        for (iFlight flight : flights) {
            if (flight.getDeparture_icao().equals(departure) && flight.getArrival_icao().equals(arrival) && flight.getFlight_dept_date_time().split(" ")[0].equals(dept_time) ) {  //&& flight.getFlight_dept_date_time().split(" ")[0].equals(dept_time)
                results.add(flight);
            }
        }
        return results;
    }

    @Override
    public Graph<String, DefaultWeightedEdge> getAirportGraph() {
        return airportGraph;
    }

    private void addAircrafts() {
        addAircraft("Boeing 737", 5, 7, 7, 13);
        addAircraft("Airbus A320", 5, 6, 8, 15);
        addAircraft("Embraer E190", 5, 9, 7, 15);
        addAircraft("Boeing 777", 6, 6, 6, 19);
        addAircraft("Bombardier Q400", 4, 9, 7, 12);
    }

    private void addAircraft(String aircraftName, int numSeatPerRowBusiness, int numRowsBusiness,
                             int numSeatPerRowEcon, int numRowsEcon) {
        Aircraft aircraft = new Aircraft(aircraftName, numSeatPerRowBusiness, numRowsBusiness, numSeatPerRowEcon, numRowsEcon);
        aircraftMap.put(aircraftName, aircraft);
    }


    @Override
    public Map<String, iAircraft>  getAircraftMap() {
        return aircraftMap;
    }

    @Override
    public List<iFlight> getFlightsByFlightNumbers(List<String> flightNumbers) {
        return null;
    }

    private void readConfig() {
        addAirport("YYZ");
        addAirport("YYC");
        addAirport("YUL");
        addAirport("YOW");
        addAirport("YEG");
        addAirport("YWG");
        addAirport("YVR");
        addAirport("YZZ");
        addAirport("YHM");

        addConnection("YYZ", "YYC", 1520);
        addConnection("YYZ", "YUL", 2645);
        addConnection("YYZ", "YOW", 153);
        addConnection("YYZ", "YEG", 179);
        addConnection("YYZ", "YWG", 1221);
        addConnection("YYZ", "YVR", 2133);
        addConnection("YYZ", "YZZ", 1539);
        addConnection("YYZ", "YHM", 4875);
        addConnection("YYC", "YUL", 1065);
        addConnection("YYC", "YOW", 854);
        addConnection("YYC", "YEG", 4579);
        addConnection("YYC", "YWG", 3444);
        addConnection("YYC", "YVR", 514);
        addConnection("YYC", "YZZ", 4939);
        addConnection("YYC", "YHM", 2206);
        addConnection("YUL", "YOW", 4183);
        addConnection("YUL", "YEG", 1110);
        addConnection("YUL", "YWG", 2057);
        addConnection("YUL", "YVR", 3480);
        addConnection("YUL", "YZZ", 3072);
        addConnection("YUL", "YHM", 2272);
        addConnection("YOW", "YEG", 3669);
        addConnection("YOW", "YWG", 90);
        addConnection("YOW", "YVR", 3503);
        addConnection("YOW", "YZZ", 2943);
        addConnection("YOW", "YHM", 1902);
        addConnection("YEG", "YWG", 4064);
        addConnection("YEG", "YVR", 1054);
        addConnection("YEG", "YZZ", 3622);
        addConnection("YEG", "YHM", 790);
        addConnection("YWG", "YVR", 1958);
        addConnection("YWG", "YZZ", 100);
        addConnection("YWG", "YHM", 1178);
        addConnection("YVR", "YZZ", 3025);
        addConnection("YVR", "YHM", 3485);
        addConnection("YZZ", "YHM", 2283);
        addConnection("YUL", "YYZ", 2301);
    }


    private void addFlight(String flightNumber, String departure_icao, String arrival_icao, String flight_dept_date_time, String flight_arr_date_time, String airCraft_Type, String depature_Gate, String arr_Gate, int econPrice, int busPrice) {
        flights.add(new Flight(flightNumber, departure_icao, arrival_icao, flight_dept_date_time, flight_arr_date_time, airCraft_Type, depature_Gate, arr_Gate, econPrice, busPrice));
    }

    // Helper Functions

    private void addAirport(String airport) {
        airportGraph.addVertex(airport);
    }

    private void addConnection(String source, String target, double weight) {
        DefaultWeightedEdge edge = airportGraph.addEdge(source, target);
        airportGraph.setEdgeWeight(edge, weight);
    }
}
