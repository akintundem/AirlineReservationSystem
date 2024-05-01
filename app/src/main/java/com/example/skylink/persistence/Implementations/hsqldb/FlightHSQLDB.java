package com.example.skylink.persistence.Implementations.hsqldb;

import com.example.skylink.objects.Implementations.Aircraft;
import com.example.skylink.objects.Implementations.Flight;
import com.example.skylink.objects.Interfaces.iAircraft;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.persistence.Interfaces.IFlightDB;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FlightHSQLDB implements IFlightDB {

    private final String dbPath;


    private final Graph<String, DefaultWeightedEdge> airportGraph;
    private final String[] airports = {"YYZ", "YYC", "YUL", "YOW", "YEG", "YWG", "YVR", "YZZ", "YHM"};
    private final String[] distances = {
            "YYZ-YYC-1520", "YYZ-YUL-2645", "YYZ-YOW-153", "YYZ-YEG-179", "YYZ-YWG-1221", "YYZ-YVR-2133", "YYZ-YZZ-1539", "YYZ-YHM-4875",
            "YYC-YUL-1065", "YYC-YOW-854", "YYC-YEG-4579", "YYC-YWG-3444", "YYC-YVR-514", "YYC-YZZ-4939", "YYC-YHM-2206",
            "YUL-YOW-4183", "YUL-YEG-1110", "YUL-YWG-2057", "YUL-YVR-3480", "YUL-YZZ-3072", "YUL-YHM-2272",
            "YOW-YEG-3669", "YOW-YWG-90", "YOW-YVR-3503", "YOW-YZZ-2943", "YOW-YHM-1902",
            "YEG-YWG-4064", "YEG-YVR-1054", "YEG-YZZ-3622", "YEG-YHM-790",
            "YWG-YVR-1958", "YWG-YZZ-100", "YWG-YHM-1178",
            "YVR-YZZ-3025", "YVR-YHM-3485",
            "YZZ-YHM-2283",
            "YUL-YYZ-2301"
    };

    public Map<String, iAircraft> getAircraftMap() {
        Map<String, iAircraft> aircraftMap = new HashMap<>();

        try (Connection connection = connect()) {
            String query = "SELECT * FROM AIRCRAFTS";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String aircraftName = resultSet.getString("aircraftName");
                        int numSeatPerRowBusiness = resultSet.getInt("numSeatPerRowBusiness");
                        int numRowsBusiness = resultSet.getInt("numRowsBusiness");
                        int numSeatPerRowEcon = resultSet.getInt("numSeatPerRowEcon");
                        int numRowsEcon = resultSet.getInt("numRowsEcon");

                        Aircraft aircraft = new Aircraft(aircraftName, numSeatPerRowBusiness, numRowsBusiness, numSeatPerRowEcon, numRowsEcon);
                        aircraftMap.put(aircraftName, aircraft);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aircraftMap;
    }



    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS FLIGHTS("
            + "flightNumber VARCHAR(10) PRIMARY KEY, "
            + "departure_icao VARCHAR(4), "
            + "arrival_icao VARCHAR(4), "
            + "flight_dept_date_time VARCHAR(20), "
            + "flight_arr_date_time VARCHAR(20), "
            + "airCraft_Type VARCHAR(20), "
            + "departure_Gate VARCHAR(10), "
            + "arr_Gate VARCHAR(10), "
            + "econPrice INT, "
            + "busnPrice INT"
            + ")";

    private final String CREATE_AIRCRAFT_TABLE = "CREATE TABLE IF NOT EXISTS AIRCRAFTS("
            + "aircraftName VARCHAR(50) PRIMARY KEY, "
            + "numSeatPerRowBusiness INT, "
            + "numRowsBusiness INT, "
            + "numSeatPerRowEcon INT, "
            + "numRowsEcon INT"
            + ")";

    public Graph<String, DefaultWeightedEdge> getAirportGraph() {
        return airportGraph;
    }

    public FlightHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        this.airportGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);
        addAirportsAndDistances();
        addAircrafts();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private void addAirportsAndDistances() {
        for (String airport : airports) {
            addAirport(airport);
        }

        for (String distance : distances) {
            String[] parts = distance.split("-");
            addConnection(parts[0], parts[1], Double.parseDouble(parts[2]));
        }
    }

    private void addAirport(String airport) {
        airportGraph.addVertex(airport);
    }
    private void addConnection(String source, String target, double weight) {
        DefaultWeightedEdge edge = airportGraph.addEdge(source, target);
        airportGraph.setEdgeWeight(edge, weight);
    }

    @Override
    public void addFlights() {

    }


    @Override
    public List<iFlight> findFlight(String departure, String arrival, String dept_time) {
        List<iFlight> results = new ArrayList<>();

        String sql = "SELECT * FROM FLIGHTS WHERE departure_icao = ? AND arrival_icao = ? AND flight_dept_date_time LIKE ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departure);
            ps.setString(2, arrival);
            ps.setString(3, dept_time + "%"); // Assuming flight_dept_date_time format is like "18/02/2024 02:57"

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    results.add(new Flight(
                            rs.getString("flightNumber"),
                            rs.getString("departure_icao"),
                            rs.getString("arrival_icao"),
                            rs.getString("flight_dept_date_time"),
                            rs.getString("flight_arr_date_time"),
                            rs.getString("airCraft_Type"),
                            rs.getString("departure_Gate"),
                            rs.getString("arr_Gate"),
                            rs.getInt("econPrice"),
                            rs.getInt("busnPrice")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
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
        try (Connection connection = connect()) {
            String query = "INSERT INTO AIRCRAFTS (aircraftName, numSeatPerRowBusiness, numRowsBusiness, numSeatPerRowEcon, numRowsEcon) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, aircraftName);
                preparedStatement.setInt(2, numSeatPerRowBusiness);
                preparedStatement.setInt(3, numRowsBusiness);
                preparedStatement.setInt(4, numSeatPerRowEcon);
                preparedStatement.setInt(5, numRowsEcon);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public List<iFlight> getFlightsByFlightNumbers(List<String> flightNumbers) {
        List<iFlight> flights = new ArrayList<>();
        String sql = "SELECT * FROM FLIGHTS WHERE flightNumber = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String flightNumber : flightNumbers) {
                ps.setString(1, flightNumber);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Flight flight = new Flight(
                                rs.getString("flightNumber"),
                                rs.getString("departure_icao"),
                                rs.getString("arrival_icao"),
                                rs.getString("flight_dept_date_time"),
                                rs.getString("flight_arr_date_time"),
                                rs.getString("airCraft_Type"),
                                rs.getString("departure_Gate"),
                                rs.getString("arr_Gate"),
                                rs.getInt("econPrice"),
                                rs.getInt("busnPrice")
                        );
                        flights.add(flight);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return flights;
    }



    public FlightHSQLDB initialize() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE);
            stmt.executeUpdate(CREATE_AIRCRAFT_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public IFlightDB drop() {
        String sql = "DROP TABLE FLIGHTS";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

}
