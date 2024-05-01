package com.example.skylink.presentation;

import android.content.Context;
import com.example.skylink.objects.Interfaces.iAircraft;
import com.example.skylink.objects.Interfaces.iBookingInfo;
import com.example.skylink.objects.Interfaces.iCity;
import com.example.skylink.objects.Interfaces.iCreditCard;
import com.example.skylink.objects.Interfaces.iFlight;
import com.example.skylink.objects.Interfaces.iFlightInfo;
import com.example.skylink.objects.Interfaces.iFlightSearch;
import com.example.skylink.objects.Interfaces.iFlights;
import com.example.skylink.objects.Interfaces.iPassengerData;
import com.example.skylink.objects.Interfaces.ITripInvoice;
import com.example.skylink.objects.Interfaces.IUserProperties;

import java.util.HashMap;
import java.util.List;

public interface ISession {
    iAircraft getAircraft();
    void setAircraft(iAircraft aircraft);

    iCity getCity();
    void setCity(iCity city);

    iFlight getFlight();
    void setFlight(iFlight flight);

    iFlightInfo getFlightInfo();
    void setFlightInfo(iFlightInfo flightInfo);

    iFlights getFlights();
    void setFlights(iFlights flights);

    IUserProperties getUserProperties();
    void setUserProperties(IUserProperties userProperties);

    Context getContext();
    void setContext(Context context);

    iBookingInfo getBookingInfo();
    void setBookingInfo(iBookingInfo bookingInfo);

    iFlightSearch getFlightSearch();
    void setFlightSearch(iFlightSearch flightSearch);

    List<iPassengerData> getPassengerData();
    void setPassengerData(List<iPassengerData> passengerData);

    ITripInvoice getTripInvoice();
    void setTripInvoice(ITripInvoice tripInvoice);
    void setFlightPathResults(HashMap<String, List<List<List<iFlight>>>> flightPathResults);
    HashMap<String, List<List<List<iFlight>>>> getFlightPathResults();
    HashMap<String, List<List<iFlight>>> getSelectedFlights();
    List<iFlightInfo> getFlightInfoCompleted();
    void setFlightInfoCompleted(iFlightInfo flightInfoCompleted);
    iCreditCard getCreditCard();
    void setCreditCard(iCreditCard creditCard);
}
