package com.example.skylink.business.validations;

public interface IValidatePayment {

    // payment field
    String validCardNum(String cardNum);
    String validExpiryDate(String expiryDate);
    String validCVV(String cvv);
    String validCardholderName(String cardholderName);
    String validBillingAddress(String billingAddress);
}
