package com.example.skylink.presentation.FlightSearching;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.skylink.R;
import com.example.skylink.business.Implementations.FlightSorting;
import com.example.skylink.business.Implementations.SortingByDirectFlight;
import com.example.skylink.business.Implementations.SortingByEarliestDeparture;
import com.example.skylink.business.Implementations.SortingByPrice;
import com.example.skylink.business.Interface.ISortingOption;
import com.example.skylink.presentation.Session;
import com.example.skylink.business.Interface.iFlightSorting;
import com.example.skylink.objects.Implementations.Flights;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.example.skylink.objects.Interfaces.iFlights;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightDisplay extends AppCompatActivity {

    public static final String SORT_LOWEST_PRICE = "Lowest price";
    public static final String SORT_DIRECT_FLIGHT = "Direct flight";
    public static final String SORT_EARLIEST_DEPARTURE = "Earliest departure";

    private ListView showFlightLV;
    private Spinner sortingOptions;
    private TextView noFlightTV;
    private List<List<List<iFlight>>> availableFlights = new ArrayList<>();
    private boolean isOneWay = true;
    private CustomFlightAdaptor originAdaptor;
    private CustomFlightAdaptor returnAdaptor;
    private CustomFlightAdaptor currAdaptor;
    private boolean isDepartureSelected;
    private List<List<List<iFlight>>> tripOutbound = new ArrayList<>();
    private List<List<List<iFlight>>> tripInbound = new ArrayList<>();

    private HashMap<String, List<List<iFlight>>> selectedFlights = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);

        iFlightSearch flightSearchDetail = Session.getInstance().getFlightSearch();

        noFlightTV = findViewById(R.id.noFlightTextV);
        showFlightLV = findViewById(R.id.flightListView);


        iFlights flightData = new Flights( Session.getInstance().getFlightPathResults());

        HashMap<String, List<List<List<iFlight>>>> receivedData = null;

        if (flightData != null && !flightData.getData().isEmpty()) {

            displayUserSelection(flightSearchDetail);

                receivedData = flightData.getData();

            if (receivedData.containsKey("Outbound")) {

                extractFlightData(receivedData, isOneWay);

                sortingOptions = setupSpinner();

                setupListview(flightSearchDetail);

                sortingOptions.setOnItemSelectedListener(new spinnerItemSelectListner());

            } else {
                noFlightTV.setVisibility(View.VISIBLE);
                showFlightLV.setVisibility(View.GONE);
            }


        } else {
            noFlightTV.setVisibility(View.VISIBLE);
            showFlightLV.setVisibility(View.GONE);
        }

    }



    @SuppressLint("SetTextI18n")
    private void displayUserSelection(iFlightSearch flightSearchDetail) {
        if (flightSearchDetail != null) {
            String departingCity = flightSearchDetail.getFlightDept();
            String returningCity = flightSearchDetail.getFlightArrival();
            String departingDate = flightSearchDetail.getFlightDeptDate();
            String returningDate = flightSearchDetail.getFlightReturnDate();
            int totalPassengers = flightSearchDetail.getTotalPassengers();
            boolean isOneWay = flightSearchDetail.isOneWay();

            this.isOneWay = isOneWay;

            TextView toLocTV = findViewById(R.id.toLocTV);
            toLocTV.setText(departingCity);

            TextView fromLocTV = findViewById(R.id.fromLocTV);
            fromLocTV.setText(returningCity);

            TextView departDateTV = findViewById(R.id.departDateTV);
            departDateTV.setText(departingDate);

            LinearLayout returnDateSection = findViewById(R.id.returnDateLayout);
            TextView returnDateTV = findViewById(R.id.returnDateTV);

            TextView tripWayTV = findViewById(R.id.tripWayTV);

            if (isOneWay) {
                tripWayTV.setText("Oneway");
                returnDateSection.setVisibility(View.GONE);
            } else {
                tripWayTV.setText("Round-trip");
                returnDateSection.setVisibility(View.VISIBLE);
                returnDateTV.setText(returningDate);
            }

            TextView totalGuestTV = findViewById(R.id.totalGuestTV);
            totalGuestTV.setText(totalPassengers + " Traveler");

        }
    }

    private Spinner setupSpinner () {
        Spinner sortingOptions = findViewById(R.id.sortingListOption);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this, R.array.sorting_list_option, R.layout.custom_spinner_text);

        // Specify the layout to use when the list of choices appears.
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        sortingOptions.setAdapter(sortAdapter);

        return sortingOptions;
    }

    private void extractFlightData (HashMap< String,List<List<List<iFlight>>>>  receivedData, boolean isOneWay) {

        if (receivedData != null) {

            if (!isOneWay) {
                // Retrieve and process outbound flights
                if (receivedData.containsKey("Inbound")) {
                    tripInbound = receivedData.get("Inbound");
                }
            }

            // Retrieve and process inbound flights
            if (receivedData.containsKey("Outbound")) {
                tripOutbound = receivedData.get("Outbound");
            }

        }
    }

    private void setupListview (iFlightSearch flightSearchDetail) {

        isDepartureSelected = false;

        originAdaptor = new CustomFlightAdaptor(FlightDisplay.this, tripOutbound, isOneWay, flightSearchDetail);
        returnAdaptor = new CustomFlightAdaptor(FlightDisplay.this, tripInbound, isOneWay, flightSearchDetail);
        currAdaptor = originAdaptor;

        availableFlights = new ArrayList<>(tripOutbound);

        checkAdapter(availableFlights);
    }

    public class spinnerItemSelectListner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // Perform actions when an item is selected
            String selectedItem = parent.getItemAtPosition(position).toString();

            List<List<List<iFlight>>> filteredFlights = new ArrayList<>(availableFlights);

            if (filteredFlights.size() > 0) {
                ISortingOption sortingOption = new SortingByPrice();        // by default - sort by lowest price

                if (selectedItem.equals(SORT_LOWEST_PRICE)) {

                    sortingOption = new SortingByPrice();

                } else if (selectedItem.equals(SORT_DIRECT_FLIGHT)) {

                    sortingOption = new SortingByDirectFlight();

                } else if (selectedItem.equals(SORT_EARLIEST_DEPARTURE)) {

                    sortingOption = new SortingByEarliestDeparture();
                }

                iFlightSorting flightSorting = new FlightSorting(sortingOption);
                filteredFlights.sort(flightSorting);

                if (filteredFlights.size() > 0) {

                    currAdaptor.setAvailableFlights(filteredFlights);
                    currAdaptor.notifyDataSetChanged();

                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing if nothing is selected
        }
    }

    public void updateFlights() {
        if (!isOneWay) {
            if (isDepartureSelected) {
                currAdaptor = returnAdaptor;

                availableFlights = new ArrayList<>(tripInbound);

            } else {
                currAdaptor = originAdaptor;

                availableFlights =  new ArrayList<>(tripOutbound);
            }

            checkAdapter(availableFlights);

            sortingOptions.setOnItemSelectedListener(new spinnerItemSelectListner());
        }
    }


    private void checkAdapter(List<List<List<iFlight>>> availableFlights) {
        if (availableFlights.isEmpty()) {
            noFlightTV.setVisibility(View.VISIBLE);
            showFlightLV.setVisibility(View.GONE);

        } else {
            noFlightTV.setVisibility(View.GONE);
            showFlightLV.setVisibility(View.VISIBLE);

            showFlightLV.setAdapter(currAdaptor);

        }

    }

    @Override
    public void onBackPressed() {

        if (!isOneWay) {
            // if showing return flights, show departure flight
            if (isDepartureSelected) {
                isDepartureSelected = false;

                updateFlights();
            } else {
                super.onBackPressed();

            }

        } else {
            super.onBackPressed();

        }

    }

    public boolean getDepartureStatus() {
        return isDepartureSelected;
    }

    public void setDepartureStatus(boolean departureStatus) {
        isDepartureSelected = departureStatus;
    }

    public HashMap<String, List<List<iFlight>>> getSelectedFlights() {
        return selectedFlights;
    }

    public void setSelectedFlights(HashMap<String, List<List<iFlight>>> selectedFlights) {
        this.selectedFlights = selectedFlights;
    }
}