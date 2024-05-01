package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.iCreditCard;

public class CreditCard implements iCreditCard {
    private String cardNum;
    private String expiryDate;
    private String cvv;
    private String cardholderName;
    private String billingAddress;

    // Constructor
    public CreditCard(String cardNum, String expiryDate, String cvv, String cardholderName, String billingAddress) {
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
        this.billingAddress = billingAddress;
    }

    public CreditCard(){

    }
    // Getters and Setters
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
}
