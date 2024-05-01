package com.example.skylink.business.validations;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateSearchInput implements IValidateSearchInput{
    @Override
    public String validAirportFrom(String airportFrom) {
        String error = "";

        if (airportFrom == null || airportFrom.isEmpty()) {
            error = "Origin airport cannot be empty";
        }

        return error;
    }

    @Override
    public String validAirportTo(String airportTo) {
        String error = "";

        if (airportTo == null || airportTo.isEmpty()) {
            error = "Destination airport cannot be empty";
        }

        return error;
    }

    @Override
    public String validDepartureDate(String departureDate) {
        String error = "";

        if (departureDate == null || departureDate.isEmpty()) {
            error = "Please select departure date";
        }

        return error;
    }

    @Override
    public String validReturnDate(String departureDate, String returnDate) {
        String error = "";

        if (returnDate == null || returnDate.isEmpty()) {
            error = "Please select return date";
        } else {
            error = checkDates(departureDate, returnDate);
        }

        return error;
    }

    private String checkDates (String departureDt, String returnDt) {
        String error = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (!departureDt.isEmpty() && !returnDt.isEmpty()) {

            try {
                Date departDate = dateFormat.parse(departureDt);
                Date returnDate = dateFormat.parse(returnDt);

                if (departDate.after(returnDate)) {
                    error = "Please select return date that comes after departure date";
                }

            } catch (ParseException e) {
                error = "Invalid date format";
            }


        } else {
            error = "Please enter departure date";
        }

        return error;
    }
}
