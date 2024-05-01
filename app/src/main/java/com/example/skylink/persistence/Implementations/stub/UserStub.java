package com.example.skylink.persistence.Implementations.stub;

import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.persistence.Interfaces.IUserDB;


import java.util.HashMap;

public class UserStub implements IUserDB {
    private final HashMap<Long, IUserProperties> users;
    private long idCounter = 1;

    public UserStub() {
        this.users = new HashMap<>();
    }

    @Override
    public long addUser_Auth(IUserProperties user) {
        long userId = idCounter++;
        users.put(userId, user);
        return userId;
    }

    @Override
    public boolean update_user_info(long user_id, IUserProperties user) {
        IUserProperties storedUser = users.get(user_id);
        if (storedUser != null) {
            // Update user information
            storedUser.setFullName(user.getFullName());
            storedUser.setEmail(user.getEmail());
            storedUser.setPassword(user.getPassword());
            storedUser.setGender(user.getGender());
            storedUser.setAddress(user.getAddress());
            storedUser.setPhone(user.getPhone());
            storedUser.setDateOfBirth(user.getDateOfBirth());
            return true;
        }
        return false; // User not found
    }

    @Override
    public String findPassword(String email) {
        for (IUserProperties user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user.getPassword();
            }
        }
        return null; // User not found
    }

    @Override
    public IUserDB initialize() {
        users.clear();
        idCounter = 1;
        return this;
    }

    @Override
    public IUserDB drop() {
        users.clear();
        idCounter = 1;
        return this;
    }

    @Override
    public IUserProperties getUserByEmail(String email) {
        for (IUserProperties user : users.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // User not found
    }

    @Override
    public long getUserId(String email) {
        // TODO add implemention for getting user id by email
        return -1;
    }

}
