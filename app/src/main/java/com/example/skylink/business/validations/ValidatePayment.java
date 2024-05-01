package com.example.skylink.business.validations;

import java.time.LocalDate;

public class ValidatePayment implements IValidatePayment{

    private final int TOTAL_CARD_DIGITS = 16;
    private final int TOTAL_CVV_DIGITS = 3;
    private final int EXP_DATE_LENGTH = 4;
    private final int CURR_YEAR = 2024;
    private final int CURR_MONTH = 03;
    @Override
    public String validCardNum(String cardNum) {
        String error = "";

        if (cardNum == null || cardNum.isEmpty()) {
            error = "Card number cannot be empty";
        } else if (cardNum.length() != TOTAL_CARD_DIGITS) {
            error = "Invalid card number";
        }

        return error;
    }

    @Override
    public String validExpiryDate(String expiryDate) {
        String error = "";

        if (expiryDate == null || expiryDate.isEmpty()) {
            error = "Expiry date cannot be empty";
        } else if (expiryDate.length() != EXP_DATE_LENGTH) {
            error = "Please follow date format (MMYY)";
        } else {
            error = checkDate(expiryDate);      // check date validation
        }

        return error;
    }

    @Override
    public String validCVV(String cvv) {
        String error = "";

        if (cvv == null || cvv.isEmpty()) {
            error = "CVV cannot be empty";
        } else if (cvv.length() != TOTAL_CVV_DIGITS) {
            error = "Invalid cvv number";
        }

        return error;
    }

    @Override
    public String validCardholderName(String cardholderName) {
        String error = "";

        if (cardholderName == null || cardholderName.isEmpty()) {
            error = "Cardholder name cannot be empty";
        }

        return error;
    }

    @Override
    public String validBillingAddress(String billingAddress) {
        String error = "";

        if (billingAddress == null || billingAddress.isEmpty()) {
            error = "Billing address cannot be empty";
        }

        return error;
    }


    private String  checkDate(String expiryDate) {
        String error = "";

        // Extract the month and year
        int month = Integer.parseInt(expiryDate.substring(0, 2));
        int year = Integer.parseInt(expiryDate.substring(2, 4));

        // Check if the month is between 1 and 12
        if (month < 1 || month > 12) {
            error = "Invalid month number";
        }

        // Get the current year and month
        int currentYear = CURR_YEAR - 2000;     // get last 2 digits of the year
        int currentMonth = CURR_MONTH;

        // checks if android version is greater than 24.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentYear = LocalDate.now().getYear() - 2000;     // get last 2 digits of the year
            currentMonth = LocalDate.now().getMonthValue();
        }

        if (error.isEmpty() && year <= currentYear) {
            error = "Card is expired";

            // if card expires in upcoming months of current year, then it is not expired yet.
            if (year == currentYear && month > currentMonth) {
                error = "";
            }
        }

        return error;
    }


}
