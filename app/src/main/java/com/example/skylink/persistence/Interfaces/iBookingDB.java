package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Interfaces.iPassengerData;

import java.util.HashMap;

public interface iBookingDB {

    void addBooking(iPassengerData passengerData, long userId);

    boolean findBooking(iPassengerData searchPassengerData, long userId);

    void updateBookingInformation(String emailAddress, long userId, String bookingNumber, String seatNumber);
    HashMap<iPassengerData, String> getPassengersWithSeatNumbers(String bookingNumber);

}
