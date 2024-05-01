package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.iBookingInfo;

import java.util.HashMap;
import java.util.Map;

public class BookingInfo implements iBookingInfo {
    private long id;
    private String bookingNumber;
    private String econBus;
    private Map<String, String> priceType;
    private int outboundPrice, inboundPrice;
    private String direction;
    private int bagCount, petCount, wifiOption, wheelchairOption;
    private int addonsPrice;

    public BookingInfo(long id, String flightID, String econBus, String direction, int bagCount, int petCount, int wifiOption, int wheelchairOption) {
        this.id = id;
        this.bookingNumber = flightID;
        this.econBus = econBus;
        this.direction = direction;
        this.bagCount = bagCount;
        this.petCount = petCount;
        this.wifiOption = wifiOption;
        this.wheelchairOption = wheelchairOption;
    }

    public BookingInfo(){

    }
    public int getAddonsPrice() {
        return addonsPrice;
    }

    public void setAddonsPrice(int addonsPrice) {
        this.addonsPrice = addonsPrice;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    @Override
    public String getEconBus() {
        return econBus;
    }

    @Override
    public void setEconBus(String econBus) {
        this.econBus = econBus;
    }

    @Override
    public String getDirection() {
        return direction;
    }

    @Override
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getBagCount() {
        return bagCount;
    }

    public void setBagCount(int bagCount) {
        this.bagCount = bagCount;
    }

    public int getPetCount() {
        return petCount;
    }

    public void setPetCount(int petCount) {
        this.petCount = petCount;
    }

    public int getWifiOption() {
        return wifiOption;
    }

    public void setWifiOption(int wifiOption) {
        this.wifiOption = wifiOption;
    }

    public int getWheelchairOption() {
        return wheelchairOption;
    }

    public void setWheelchairOption(int wheelchairOption) {
        this.wheelchairOption = wheelchairOption;
    }
    public void setpriceType(String key, String value) {
        if (priceType == null) {
            priceType = new HashMap<>();
        }
        priceType.put(key, value);
    }

    public Map<String, String> getpriceType() {
        return priceType;
    }

    public int getOutboundPrice() {
        return outboundPrice;
    }

    @Override
    public void setOutboundPrice(int outboundPrice) {
        this.outboundPrice = outboundPrice;
    }

    @Override
    public int getInboundPrice() {
        return inboundPrice;
    }

    @Override
    public void setInboundPrice(int inboundPrice) {
        this.inboundPrice = inboundPrice;
    }




}