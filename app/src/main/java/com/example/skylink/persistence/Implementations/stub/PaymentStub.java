package com.example.skylink.persistence.Implementations.stub;

import com.example.skylink.objects.Interfaces.ITripInvoice;
import com.example.skylink.persistence.Interfaces.IPaymentDB;

import java.util.HashMap;

public class PaymentStub implements IPaymentDB {

    private final HashMap<Long, ITripInvoice> tripInvoices;
    private long idCounter = 1;

    public PaymentStub() {
        this.tripInvoices = new HashMap<>();
    }


    @Override
    public boolean addPayment(ITripInvoice tripInvoice) {
        long paymentID = idCounter++;
        tripInvoices.put(paymentID, tripInvoice);
        return true;
    }

    @Override
    public IPaymentDB initialize() {
        tripInvoices.clear();
        idCounter = 1;
        return this;
    }

    @Override
    public IPaymentDB drop() {
        tripInvoices.clear();
        idCounter = 1;
        return this;
    }
}
