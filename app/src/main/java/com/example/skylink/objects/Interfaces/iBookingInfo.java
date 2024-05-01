package com.example.skylink.objects.Interfaces;

import java.util.Map;

public interface iBookingInfo {
    // Get the unique identifier of the booking
    long getId();

    // Set the unique identifier of the booking
    void setId(long id);

    // Get the booking number
    String getBookingNumber();

    // Set the booking number
    void setBookingNumber(String bookingNumber);

    // Get the type of booking (e.g., Economy, Business)
    String getEconBus();

    // Set the type of booking (e.g., Economy, Business)
    void setEconBus(String econBus);

    // Get the direction of the flight
    String getDirection();

    // Set the direction of the flight
    void setDirection(String direction);

    int getBagCount();

    void setBagCount(int bagCount);

    int getPetCount();

    void setPetCount(int petCount);

    int getWifiOption();
    void setWifiOption(int wifiOption);

    int getWheelchairOption();

    void setWheelchairOption(int wheelchairOption);
    void setpriceType(String key, String value);
    Map<String, String> getpriceType();
    int getOutboundPrice();
    void setOutboundPrice(int outboundPrice);
    int getInboundPrice();
    void setInboundPrice(int inboundPrice);
    int getAddonsPrice();
    void setAddonsPrice(int addonsPrice);
}
