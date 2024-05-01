package com.example.skylink.objects.Interfaces;

public interface ITripInvoice {

    long getUserID();
    int getTotalAmount();

    void setUserID(long userID);
    void setTotalAmount(int totalAmount);
}
