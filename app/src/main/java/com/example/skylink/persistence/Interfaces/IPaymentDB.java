package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Interfaces.ITripInvoice;

public interface IPaymentDB {

    boolean addPayment(ITripInvoice tripInvoice);
    IPaymentDB initialize();

    IPaymentDB drop();
}
