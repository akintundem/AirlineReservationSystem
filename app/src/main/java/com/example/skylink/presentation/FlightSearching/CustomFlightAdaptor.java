package com.example.skylink.presentation.FlightSearching;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.skylink.R;
import com.example.skylink.presentation.Session;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.example.skylink.presentation.UserInfo.User_info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CustomFlightAdaptor extends BaseAdapter {

    private final Context mContext;
    private final FlightDisplay flightResult;
    private List<List<List<iFlight>>> availableFlights;
    private final boolean isOneWay;
    private final iFlightSearch userInput;

    public CustomFlightAdaptor(Context context, List<List<List<iFlight>>> availableFlights, boolean isOneWay, iFlightSearch userInput) {
        mContext = context;
        this.availableFlights = availableFlights;
        this.isOneWay = isOneWay;
        this.userInput = userInput;

        if (context instanceof FlightDisplay) {
            flightResult = (FlightDisplay) context;
        } else {
            flightResult = null;
        }
    }

    @Override
    public int getCount() {
        return availableFlights.size();
    }

    @Override
    public List<List<iFlight>> getItem(int position) {
        return availableFlights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View listItemView;

        if (convertView == null) {
            listItemView = inflater.inflate(R.layout.flight_result, parent, false);
        } else {
            listItemView = convertView;
        }

        TextView orgCode = listItemView.findViewById(R.id.orgCodeTV);
        TextView midCode = listItemView.findViewById(R.id.midCodeTV);
        TextView destCode = listItemView.findViewById(R.id.destCodeTV);
        TextView timeTaken = listItemView.findViewById(R.id.timeTakenTV);
        TextView takeoffTime = listItemView.findViewById(R.id.takeoffTV);
        TextView landingTime = listItemView.findViewById(R.id.landingTimeTV);
        TextView econPrice = listItemView.findViewById(R.id.econPrice);
        TextView busnPrice = listItemView.findViewById(R.id.busnPrice);
        Button econBook = listItemView.findViewById(R.id.econPriceBtn);
        Button busnBook = listItemView.findViewById(R.id.busnPriceBtn);

        List<List<iFlight>> flightCardView = getItem(position);
        iFlight fromOrigin = flightCardView.get(0).get(0);

        if (flightCardView.size() > 1) {
            String middleAirports = "";

            middleAirports += fromOrigin.getArrival_icao() + " ";
            midCode.setText(middleAirports);

            iFlight lastFlight = flightCardView.get(1).get(0);

            destCode.setText(lastFlight.getArrival_icao());
            String getLandingTime = parseTime(lastFlight.getFlight_arr_date_time());

            if (getLandingTime != null) {
                landingTime.setText(getLandingTime);
            }

        } else {

            midCode.setText("");

            destCode.setText(fromOrigin.getArrival_icao());

            String getLandingTime = parseTime(fromOrigin.getFlight_arr_date_time());
            if (getLandingTime != null) {
                landingTime.setText(getLandingTime);
            }

        }

        String getTakeOffTime = parseTime(fromOrigin.getFlight_dept_date_time());


        orgCode.setText(fromOrigin.getDeparture_icao());
        econPrice.setText("" + fromOrigin.getEconPrice());
        busnPrice.setText("" + fromOrigin.getBusnPrice());

        if (getTakeOffTime != null) {
            takeoffTime.setText(getTakeOffTime);
        }

        econBook.setOnClickListener(v -> {
            bookFlight(econBook, econPrice, "Economy", v);
        });

        busnBook.setOnClickListener(v -> {
            bookFlight(busnBook, busnPrice, "Business", v);
        });

        return listItemView;
    }




    private void bookFlight(Button bookButton, TextView priceView, String priceType, View v) {
        int selectedFlightPrice = userInput.getTotalPassengers() * Integer.parseInt(priceView.getText().toString());

        View parentRow = (View) v.getParent();
        ListView listView = (ListView) parentRow.getParent().getParent().getParent().getParent().getParent();
        final int position = listView.getPositionForView(parentRow);

        List<List<iFlight>> flightCardView = getItem(position);

        if (flightResult != null) {
            HashMap<String, List<List<iFlight>>> selectedFlights = flightResult.getSelectedFlights();
            Session.getInstance().getBookingInfo().setpriceType("Price", priceType);
            if (isOneWay) {
                selectedFlights.put("Outbound", flightCardView);
                flightResult.setSelectedFlights(selectedFlights);

                Session.getInstance().getBookingInfo().setOutboundPrice(selectedFlightPrice);

                toNextActivity();

            } else {

                if (flightResult.getDepartureStatus()) {
                    selectedFlights.put("Inbound", flightCardView);
                    flightResult.setSelectedFlights(selectedFlights);

                    Session.getInstance().getBookingInfo().setInboundPrice(selectedFlightPrice);

                    toNextActivity();
                } else {
                    selectedFlights.put("Outbound", flightCardView);
                    flightResult.setSelectedFlights(selectedFlights);
                    flightResult.setDepartureStatus(true);

                    Session.getInstance().getBookingInfo().setOutboundPrice(selectedFlightPrice);

                    displayReturnFlight();
                }
            }

        }
    }


    private String parseTime (String dateTime) {
        String timeOnly = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            // Parse the string to obtain a Date object
            Date date = dateFormat.parse(dateTime);

            // Define the format for extracting time
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            // Format the Date object to extract time only
            if (date != null) {
                timeOnly = timeFormat.format(date);
            }

        } catch (ParseException e) {
            // Print an error message if parsing fails
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return timeOnly;

    }

    private void toNextActivity() {
        if (flightResult != null) {
            Session.getInstance().setSelectedFlights(flightResult.getSelectedFlights());

            Intent nextPageIntent = new Intent(flightResult, User_info.class);
            flightResult.startActivity(nextPageIntent);
        }
    }

    private void displayReturnFlight() {
        flightResult.updateFlights();
    }

    public void setAvailableFlights(List<List<List<iFlight>>> availableFlights) {
        this.availableFlights = availableFlights;
    }
}
