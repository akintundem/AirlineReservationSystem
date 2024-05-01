package com.example.skylink.objects.Interfaces;// CreditCardService.java

public interface iCreditCard {
    String getCardNum();

    void setCardNum(String cardNum);

    String getExpiryDate();

    void setExpiryDate(String expiryDate);

    String getCvv();

    void setCvv(String cvv);

    String getCardholderName();

    void setCardholderName(String cardholderName);

    String getBillingAddress();

    void setBillingAddress(String billingAddress);
}
