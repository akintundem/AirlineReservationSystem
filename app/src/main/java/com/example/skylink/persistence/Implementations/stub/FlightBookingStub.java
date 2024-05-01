package com.example.skylink.persistence.Implementations.stub;

import com.example.skylink.objects.Interfaces.iBookingInfo;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iFlightBookingDB;

import java.util.HashMap;
import java.util.List;

public class FlightBookingStub implements iFlightBookingDB {

    @Override
    public long addFlightBooking(long user_id, String bound, iFlight flight , int price, String bookingNumber, String econBusiness, int bagCount, int petCount, int wifiOption, int wheelchairOption) {
        return 0;
    }

    @Override
    public List<iBookingInfo> getBookingInfoByUserId(long userId) {
        return null;
    }

    @Override
    public List<String> getFlightsByUserId(String direction, String bookingNumberByUserId) {
        return null;
    }


    @Override
    public iFlightBookingDB initialize() {
        return null;
    }

    @Override
    public iFlightBookingDB drop() {
        return null;
    }
}
