package com.example.skylink.business.Implementations;

import com.example.skylink.application.Services;
import com.example.skylink.business.Interface.iFlightBookingHandler;
import com.example.skylink.objects.Implementations.FlightInfo;
import com.example.skylink.objects.Interfaces.iBookingInfo;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.persistence.Interfaces.iBookingDB;
import com.example.skylink.persistence.Interfaces.iFlightBookingDB;
import com.example.skylink.persistence.Interfaces.IFlightDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FlightBookingHandler implements iFlightBookingHandler {
    private iFlightBookingDB flightBookingDB;
    private iBookingDB bookingDB;
    private IFlightDB flightDB;

    public FlightBookingHandler(iFlightBookingDB flightBookingDB, iBookingDB bookingDB, IFlightDB flightDB) {
        this.flightBookingDB = flightBookingDB;
        this.bookingDB = bookingDB;
        this.flightDB = flightDB;
    }

    public FlightBookingHandler(boolean forProduction) {
        this.flightBookingDB = Services.getFlightBookingDB();
        this.bookingDB = Services.getBookDatabase();
        this.flightDB = Services.getFlightDatabase();

    }

    public FlightBookingHandler() { }

    @Override
    public void storeAddons(int bagNumber, int petNumber, int wifiOption, int wheelchairOption, List<iFlightInfo> flightInfoList) {

        if (flightInfoList != null && !flightInfoList.isEmpty()) {
            for (iFlightInfo flightInfo : flightInfoList) {

                if (flightInfo != null) {

                    flightInfo.setBagCount(bagNumber);
                    flightInfo.setPetCount(petNumber);
                    flightInfo.setWifiOption(wifiOption);
                    flightInfo.setWheelchairOption(wheelchairOption);

                }

            }
        }

    }

    public String addConfirmBookings(long user_id, List<iFlightInfo> flightInfo, int addonsPrice) {
        String bookingNumber = generateBookingNumber();

        if (flightInfo != null) {
            for(iFlightInfo flightInfoEntry : flightInfo){
                if(flightInfoEntry != null){
                    int price = calculateTotalPrice(flightInfoEntry, addonsPrice);
                    if(isValidDirection(flightInfoEntry.getBound())){
                        for (iFlight flight : flightInfoEntry.getFlight()) {
                            flightBookingDB.addFlightBooking(user_id, flightInfoEntry.getBound(), flight, price, bookingNumber, flightInfoEntry.getEconOrBus(), flightInfoEntry.getBagCount(), flightInfoEntry.getPetCount(), flightInfoEntry.getWifiOption(), flightInfoEntry.getWheelchairOption());
                        }
                    }
                }

            }
            iFlightInfo flight = flightInfo.get(0);
            if(flight != null){
                for (Map.Entry<iPassengerData, String> passengerEntry : flight.getSeatSelected().entrySet()) {
                    bookingDB.updateBookingInformation(passengerEntry.getKey().getEmailAddress(), user_id, bookingNumber, passengerEntry.getValue());
                }
            }

        }
        return bookingNumber;
    }

    private boolean isValidDirection(String direction) {
        return "Outbound".equals(direction) || "Inbound".equals(direction);
    }


    public List<iFlightInfo> getBookingDetails(long userID) {
        List<iFlightInfo> bookingDetailsList = new ArrayList<>();

        // Get all booking numbers for the user
        List<iBookingInfo> bookingNumbers =getUniqueBookings(flightBookingDB.getBookingInfoByUserId(userID));
        for (iBookingInfo bookingInfo : bookingNumbers) {
            iFlightInfo flightInfo = prepareFlightInfo(bookingInfo);
            if(flightInfo != null){
                flightInfo.setBookingNum(bookingInfo.getBookingNumber());
                bookingDetailsList.add(flightInfo);
            }
        }

        return bookingDetailsList.isEmpty() ? null : bookingDetailsList;
    }

    private static List<iBookingInfo> getUniqueBookings(List<iBookingInfo> bookingNumbers) {
        // Create a set to store unique booking identifiers
        Set<String> uniqueBookingIdentifiers = new HashSet<>();

        // Create a list to store unique bookings
        List<iBookingInfo> uniqueBookings = new ArrayList<>();

        // Iterate through each booking
        for (iBookingInfo booking : bookingNumbers) {
            // Create a unique identifier for each booking using booking number and direction
            String uniqueIdentifier = booking.getBookingNumber() + "_" + booking.getDirection();

            // Check if the unique identifier is already present in the set
            if (!uniqueBookingIdentifiers.contains(uniqueIdentifier)) {
                // If not present, add the unique identifier to the set and add the booking to the unique bookings list
                uniqueBookingIdentifiers.add(uniqueIdentifier);
                uniqueBookings.add(booking);
            }
        }

        return uniqueBookings;
    }




    private iFlightInfo prepareFlightInfo(iBookingInfo bookingInfo) {
        iFlightInfo flightInfo = new FlightInfo();
        flightInfo.setEconOrBus(bookingInfo.getEconBus());
        flightInfo.setBound(bookingInfo.getDirection());
        List<String> flightBookingBound = flightBookingDB.getFlightsByUserId(bookingInfo.getDirection(),bookingInfo.getBookingNumber());

        if (flightBookingBound.isEmpty()) {
            return null;
        }

        List<iFlight> flightBound = flightDB.getFlightsByFlightNumbers(flightBookingBound);
        flightInfo.setFlight(flightBound);

        flightInfo.setBagCount(bookingInfo.getBagCount());
        flightInfo.setPetCount(bookingInfo.getPetCount());
        flightInfo.setWifiOption(bookingInfo.getWifiOption());
        flightInfo.setWheelchairOption(bookingInfo.getWheelchairOption());

        return flightInfo;
    }




    private String generateBookingNumber() {
        String bookingNumber = UUID.randomUUID().toString().replaceAll("-", "");
        bookingNumber = bookingNumber.substring(0, 5);
        return bookingNumber;
    }

    private int calculateTotalPrice(iFlightInfo flightInfo, int addonsPrice) {
        int price = 0;
        int unitCost = 0;

        if (flightInfo.getEconOrBus().equals("Economy")) {
            for (iFlight flight : flightInfo.getFlight()) {
                unitCost += flight.getEconPrice();
            }
            unitCost += addonsPrice;
            price = flightInfo.getSeatSelected().size() * unitCost;

        } else if (flightInfo.getEconOrBus().equals("Business")) {
            for (iFlight flight : flightInfo.getFlight()) {
                unitCost += flight.getBusnPrice();
            }
            unitCost += addonsPrice;
            price = flightInfo.getSeatSelected().size() * unitCost;
        }

        return price;
    }


}
