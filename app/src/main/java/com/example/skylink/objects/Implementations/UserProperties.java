package com.example.skylink.objects.Implementations;

import com.example.skylink.objects.Interfaces.IUserProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserProperties implements IUserProperties {

    private long user_id;

    private String fullName;
    private String email;
    private String password;
    private String gender;
    private String address;
    private String phone;
    private String dateOfBirth;
    private String countryOfOrigin;

    public UserProperties(String fullName, String email, String password, String gender, String address, String phone, String dateOfBirth, String countryOfOrigin) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.countryOfOrigin = countryOfOrigin;
    }
    public UserProperties(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.countryOfOrigin = countryOfOrigin;
    }

    public UserProperties(){

    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }


    public UserProperties(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }
}
