package com.example.skylink.business.Interface;

import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.objects.Interfaces.IUserProperties;

public interface IUserHandler {
    void createUser(IUserProperties userProperties, String rePassword) throws UserHandler.UserValidationException;

    boolean updateUserProfile(IUserProperties userProperties) throws UserHandler.UserValidationException;
    boolean signinUser(IUserProperties userProperties) throws UserHandler.UserValidationException;

    long getUserIdByEmail(String email);

    IUserProperties getUserByEmail(String email);

}
