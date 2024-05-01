package com.example.skylink.UnitTestStub;

import static org.junit.Assert.*;

import com.example.skylink.business.validations.IValidatePayment;
import com.example.skylink.business.validations.ValidatePayment;

import org.junit.Test;

public class ValidatePaymentUnit {

    @Test
    public void validCardNum_emptyCardNum_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCardNum("");      // empty card number field
        assertEquals("Card number cannot be empty", error);
    }

    @Test
    public void validCardNum_invalidLengthCardNum_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCardNum("123456789012345");   // card number only has 15 digits
        assertEquals("Invalid card number", error);
    }

    @Test
    public void validCardNum_validCardNum_returnsEmptyString() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCardNum("1234567890123456");  // valid card number with 16 digits
        assertEquals("", error);
    }

    @Test
    public void validExpiryDate_emptyExpiryDate_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validExpiryDate("");       // empty expiry date field
        assertEquals("Expiry date cannot be empty", error);
    }

    @Test
    public void validExpiryDate_invalidFormatExpiryDate_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validExpiryDate("122023");     // invalid date format
        assertEquals("Please follow date format (MMYY)", error);
    }

    @Test
    public void validExpiryDate_expiredExpiryDate_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validExpiryDate("0224");       // expired card
        assertEquals("Card is expired", error);
    }

    @Test
    public void validExpiryDate_futureExpiryDate_returnsEmptyString() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validExpiryDate("0534");       // valid expiry date
        assertEquals("", error);
    }

    @Test
    public void validCVV_emptyCVV_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCVV("");                  // empty CVV field
        assertEquals("CVV cannot be empty", error);
    }

    @Test
    public void validCVV_invalidLengthCVV_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCVV("12");                // CVV with only 2 digits
        assertEquals("Invalid cvv number", error);
    }

    @Test
    public void validCVV_validCVV_returnsEmptyString() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCVV("123");               // valid CVV number
        assertEquals("", error);
    }

    @Test
    public void validCardholderName_emptyCardholderName_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCardholderName("");       // empty cardholder name field
        assertEquals("Cardholder name cannot be empty", error);
    }

    @Test
    public void validCardholderName_nonEmptyCardholderName_returnsEmptyString() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validCardholderName("Hello world");   // valid cardholder name
        assertEquals("", error);
    }

    @Test
    public void validBillingAddress_emptyBillingAddress_returnsErrorMessage() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validBillingAddress("");       // empty billing address field
        assertEquals("Billing address cannot be empty", error);
    }

    @Test
    public void validBillingAddress_nonEmptyBillingAddress_returnsEmptyString() {
        IValidatePayment validator = new ValidatePayment();
        String error = validator.validBillingAddress("123 University Road");   // valid address
        assertEquals("", error);
    }


}