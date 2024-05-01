package com.example.skylink.business.Interface;

import com.example.skylink.objects.Interfaces.iFlightInfo;

import java.util.List;

public interface iFlightBookingHandler {

    void storeAddons(int bagNumber, int petNumber, int wifiOption, int wheelchairOption, List<iFlightInfo> flightInfoList);
    String addConfirmBookings(long user_id, List<iFlightInfo> flightInfo, int addonsPrice);
    List<iFlightInfo> getBookingDetails(long userID);
}
