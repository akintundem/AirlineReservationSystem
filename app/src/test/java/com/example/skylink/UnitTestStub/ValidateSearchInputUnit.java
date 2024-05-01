package com.example.skylink.UnitTestStub;

import static org.junit.Assert.*;

import com.example.skylink.business.validations.IValidateSearchInput;
import com.example.skylink.business.validations.ValidateSearchInput;

import org.junit.Test;

public class ValidateSearchInputUnit {

    @Test
    public void validAirportFrom_emptyAirportFrom_returnsErrorMessage() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validAirportFrom("");          // empty 'from' field
        assertEquals("Origin airport cannot be empty", error);
    }

    @Test
    public void validAirportFrom_nonEmptyAirportFrom_returnsEmptyString() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validAirportFrom("YYC");       // valid 'from' field
        assertEquals("", error);
    }

    @Test
    public void validAirportTo_emptyAirportTo_returnsErrorMessage() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validAirportTo("");        // empty 'to' field
        assertEquals("Destination airport cannot be empty", error);
    }

    @Test
    public void validAirportTo_nonEmptyAirportTo_returnsEmptyString() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validAirportTo("YWG");     // valid 'to' field
        assertEquals("", error);
    }

    @Test
    public void validDepartureDate_emptyDepartureDate_returnsErrorMessage() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validDepartureDate("");    // empty departure date field
        assertEquals("Please select departure date", error);
    }

    @Test
    public void validDepartureDate_nonEmptyDepartureDate_returnsEmptyString() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validDepartureDate("2024-03-15");      // valid departure date
        assertEquals("", error);
    }

    @Test
    public void validReturnDate_emptyReturnDate_returnsErrorMessage() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validReturnDate("2024-03-10", "");     // empty return date field
        assertEquals("Please select return date", error);
    }

    @Test
    public void validReturnDate_invalidReturnDate_returnsErrorMessage() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validReturnDate("10/03/2024", "05/03/2024");   // return date already passed
        assertEquals("Please select return date that comes after departure date", error);
    }

    @Test
    public void validReturnDate_validReturnDate_returnsEmptyString() {
        IValidateSearchInput validator = new ValidateSearchInput();
        String error = validator.validReturnDate("10/03/2024", "15/03/2024");   // valid departure and return date
        assertEquals("", error);
    }


}