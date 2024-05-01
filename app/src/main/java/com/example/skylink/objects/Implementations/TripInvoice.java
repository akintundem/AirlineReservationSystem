package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.ITripInvoice;

public class TripInvoice implements ITripInvoice {

    private long userID;
    private int totalAmount;


    public TripInvoice(long userID, int totalAmount) {
        this.userID = userID;
        this.totalAmount = totalAmount;
    }

    public TripInvoice(){

    }

    @Override
    public long getUserID() {
        return userID;
    }

    @Override
    public int getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void setUserID(long userID) {
        this.userID = userID;
    }

    @Override
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
