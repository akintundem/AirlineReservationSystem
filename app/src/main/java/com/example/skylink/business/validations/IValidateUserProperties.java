package com.example.skylink.business.validations;

public interface IValidateUserProperties {

    String validEmail(String email);
    String validTitle(String title);
    String validFirstname(String firstname);
    String validLastname(String lastname);
    String validPassword(String password);
    String validRePassword(String password, String rePassword);
    String validFullname(String name);
    String validAddress(String name);
    String validCity(String name);
    String validProvince(String name);
    String validPhone(String name);
    String validDOB(String name);
    String validGender(String name);
}
