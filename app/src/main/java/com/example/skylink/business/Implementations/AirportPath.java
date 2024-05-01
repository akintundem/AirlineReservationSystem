package com.example.skylink.business.Implementations;

import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.skylink.application.Services;
import com.example.skylink.business.Interface.iAirportPath;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.example.skylink.persistence.Interfaces.IFlightDB;

public class AirportPath implements iAirportPath {
    IFlightDB flightHSQLDB;
    Graph<String, DefaultWeightedEdge> airportGraph;



    public AirportPath(boolean forProduction){
        this.flightHSQLDB = Services.getFlightDatabase();
        this.airportGraph = flightHSQLDB.getAirportGraph();
    }


    public AirportPath(IFlightDB flightHSQLDB, Graph<String, DefaultWeightedEdge> airportGraph) {
        this.flightHSQLDB = flightHSQLDB;
        this.airportGraph = airportGraph;
    }

    private List<List<String>> findAllPaths(String source, String destination) {
        List<List<String>> allPaths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        path.add(source);
        findAllPathsDFS(source, destination, visited, path, allPaths);
        return allPaths;
    }

    private void findAllPathsDFS(String current, String destination, Set<String> visited, List<String> path, List<List<String>> allPaths) {
        visited.add(current);

        if (current.equals(destination)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            for (DefaultWeightedEdge edge : airportGraph.outgoingEdgesOf(current)) {
                String neighbor = airportGraph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    path.add(neighbor);
                    findAllPathsDFS(neighbor, destination, visited, path, allPaths);
                    path.remove(path.size() - 1);
                }
            }
        }

        visited.remove(current);
    }


    private List<List<List<iFlight>>> pullFlights(List<List<String>> flightPaths, String date) {

        if (flightPaths == null || flightPaths.isEmpty() || date == null) {
            return null;
        }

        List<List<List<iFlight>>> flightsFound = new ArrayList<>();

        for (List<String> path : flightPaths) {
            List<List<iFlight>> pathFlights = new ArrayList<>();

            for (int i = 0; i < path.size() - 1; i++) {
                String currentHub = path.get(i);
                String nextHub = path.get(i + 1);

                List<iFlight> flights = flightHSQLDB.findFlight(currentHub, nextHub, date);

                if (flights != null && !flights.isEmpty()) {
                    pathFlights.add(flights);
                } else {
                    pathFlights.clear();
                    break;
                }
            }
            if (!pathFlights.isEmpty()) {
                flightsFound.add(pathFlights);
            }
        }

        return flightsFound.isEmpty() ? null : flightsFound;
    }



    private List<List<List<iFlight>>> findFlight(String date,List<List<String>> findAllPossiblePathsFromOriginToDestination){
        if (findAllPossiblePathsFromOriginToDestination.isEmpty()) {
            return null;
        }
        List<List<String>> pathsFromOriginToDestination = filterPaths(findAllPossiblePathsFromOriginToDestination);

        if (pathsFromOriginToDestination.isEmpty()) {
            return null;
        }

        return pullFlights(pathsFromOriginToDestination, date);
    }

    private List<List<String>> filterPaths(List<List<String>> allPaths) {
        List<List<String>> filteredPaths = new ArrayList<>();

        for (List<String> path : allPaths) {
            if (path.size() <= 3) {
                filteredPaths.add(path);

            }
        }
        return filteredPaths;
    }

    private List<List<String>> reversePaths(List<List<String>> originalPaths) {
        List<List<String>> reversedPaths = new ArrayList<>();

        for (List<String> path : originalPaths) {
            List<String> reversedPath = new ArrayList<>(path);
            Collections.reverse(reversedPath);
            reversedPaths.add(reversedPath);
        }

        return reversedPaths;
    }

    private boolean isValidFlightSearch(iFlightSearch flightSearch) {
        // Add your validation logic here
        // For example, check if required fields are not null or empty
        return flightSearch.getFlightDept() != null && !flightSearch.getFlightDept().isEmpty()
                && flightSearch.getFlightArrival() != null && !flightSearch.getFlightArrival().isEmpty()
                && flightSearch.getFlightDeptDate() != null && !flightSearch.getFlightDeptDate().isEmpty()
                && flightSearch.getTotalPassengers() > 0;  // Add more conditions as needed
    }


    public HashMap< String,List<List<List<iFlight>>>> findFlights(iFlightSearch flightSearch) {

        if (!isValidFlightSearch(flightSearch)) {
            return null;
        }

        HashMap<String, List<List<List<iFlight>>>> itinerary = new HashMap<>();
        List<List<String>> findAllPossiblePathsFromOriginToDestination = findAllPaths(flightSearch.getFlightDept(), flightSearch.getFlightArrival());
        List<List<List<iFlight>>> outBoundFlights = findFlight(flightSearch.getFlightDeptDate(),findAllPossiblePathsFromOriginToDestination);
        if (outBoundFlights != null) {
            itinerary.put("Outbound", outBoundFlights);
        }
        if (!flightSearch.isOneWay() && outBoundFlights != null) {
            List<List<List<iFlight>>> inBoundFlights = findFlight(flightSearch.getFlightReturnDate(),reversePaths(findAllPossiblePathsFromOriginToDestination));
            if (inBoundFlights != null) {
                itinerary.put("Inbound", inBoundFlights);
            }
        }
        return itinerary;
    }

}
