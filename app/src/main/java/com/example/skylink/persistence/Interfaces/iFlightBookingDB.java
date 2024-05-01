package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Implementations.BookingInfo;
import com.example.skylink.objects.Interfaces.iBookingInfo;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;

import java.util.HashMap;
import java.util.List;

public interface iFlightBookingDB {
    long addFlightBooking(long user_id, String bound, iFlight flight , int price, String bookingNumber, String econBusiness, int bagCount, int petCount, int wifiOption, int wheelchairOption);
    List<iBookingInfo> getBookingInfoByUserId(long userId);
    List<String> getFlightsByUserId(String direction,String bookingNumberByUserId);
    iFlightBookingDB initialize();
    iFlightBookingDB drop();
}
