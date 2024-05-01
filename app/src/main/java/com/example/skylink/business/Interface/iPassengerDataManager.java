package com.example.skylink.business.Interface;

import com.example.skylink.objects.Interfaces.iPassengerData;

public interface iPassengerDataManager {
    iPassengerData addBooking(String title, String firstName, String lastName, String telephoneNumber, String emailAddress, long userId);
    boolean findBooking(String title, String firstName, String lastName, String telephoneNumber, String emailAddress, long userId);
}
