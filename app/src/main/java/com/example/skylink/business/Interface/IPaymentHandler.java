package com.example.skylink.business.Interface;

import com.example.skylink.objects.Interfaces.ITripInvoice;

public interface IPaymentHandler {

    boolean addPayment(ITripInvoice tripInvoice, long sessionUserID);
}
