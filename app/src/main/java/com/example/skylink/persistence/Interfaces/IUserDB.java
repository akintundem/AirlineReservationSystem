package com.example.skylink.persistence.Interfaces;

import com.example.skylink.objects.Interfaces.IUserProperties;

public interface IUserDB {

    long addUser_Auth(IUserProperties user);

    boolean update_user_info(long user_id, IUserProperties user);

    String findPassword(String email);

    IUserDB initialize();

    IUserProperties getUserByEmail(String email);

    long getUserId(String email);

    IUserDB drop();
}
