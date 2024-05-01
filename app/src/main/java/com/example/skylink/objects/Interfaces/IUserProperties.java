package com.example.skylink.objects.Interfaces;

public interface IUserProperties {
    // Getter methods
    String getFullName();
    String getEmail();
    String getPassword();
    String getGender();
    String getAddress();
    String getPhone();
    String getDateOfBirth();
    String getCountryOfOrigin();

    // Setter methods
    void setFullName(String fullName);
    void setEmail(String email);
    void setPassword(String password);
    void setGender(String gender);
    void setAddress(String address);
    void setPhone(String phone);
    void setDateOfBirth(String dateOfBirth);
    void setCountryOfOrigin(String countryOfOrigin);
    void setUser_id(long user_id);
    long getUser_id();
}
