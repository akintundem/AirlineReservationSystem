package com.example.skylink.persistence.Implementations.stub;

import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iBookingDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class BookingStub implements iBookingDB {
    private final List<iPassengerData> passengerData;

    public BookingStub(){
        this.passengerData = new ArrayList<>();
    }

    public void addBooking(iPassengerData passengerData,long userId){
        this.passengerData.add(passengerData);
    }

    public boolean findBooking(iPassengerData searchPassengerData,long userId) {
        for (iPassengerData b : passengerData) {
            if (b.getTitle().equals(searchPassengerData.getTitle()) &&
                    b.getFirstName().equals(searchPassengerData.getFirstName()) &&
                    b.getLastName().equals(searchPassengerData.getLastName()) &&
                    b.getTelephoneNumber().equals(searchPassengerData.getTelephoneNumber()) &&
                    b.getEmailAddress().equals(searchPassengerData.getEmailAddress())) {
                return  true;
            }
        }
        return false;
    }

    @Override
    public void updateBookingInformation(String emailAddress, long userId, String bookingNumber, String seatNumber) {

    }

    @Override
    public HashMap<iPassengerData, String> getPassengersWithSeatNumbers(String bookingNumber) {
        return null;
    }


}
