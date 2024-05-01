package com.example.skylink.business.validations;

public interface IValidateSearchInput {

    // Searching flight fields
    String validAirportFrom (String airportFrom);
    String validAirportTo (String airportTo);
    String validDepartureDate (String departureDate);
    String validReturnDate (String departureDate, String returnDate);
}
