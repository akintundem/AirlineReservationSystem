package com.example.skylink.business.validations;

import androidx.core.util.PatternsCompat;       // using this android import to validate email format
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
public class ValidateUserProperties implements IValidateUserProperties {

    @Override
    public String validTitle(String title) {
        String error = "";

        if (title == null || title.isEmpty()) {
            error = "Title cannot be empty";
        }

        return error;
    }

    @Override
    public String validFirstname(String firstname) {
        String error = "";

        if (firstname == null || firstname.isEmpty()) {
            error = "First name cannot be empty";
        }

        return error;
    }

    @Override
    public String validLastname(String lastname) {
        String error = "";

        if (lastname == null || lastname.isEmpty()) {
            error = "Last name cannot be empty";
        }

        return error;
    }

    @Override
    public String validEmail(String email) {
        String error = "";

        if (email == null || email.isEmpty()) {
            error = "Email cannot be empty";
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            error = "Invalid email format";
        }

        return error;
    }

    @Override
    public String validPassword(String password) {
        String error = "";

        if (password == null || password.isEmpty()) {
            error = "Password cannot be empty";
        } else if (password.length() < 8) {
            error = "Password must be at least 8 characters long";
        }

        return error;
    }

    @Override
    public String validRePassword(String password, String rePassword) {
        String error = "";

        error = validPassword(rePassword);


        if (error.isEmpty()) {

            if (!password.equals(rePassword)) {
                error = "Password do not match";
            }
        }

        return error;
    }

    @Override
    public String validFullname(String name) {
        String error = "";

        if (name == null || name.isEmpty()) {
            error = "Name cannot be empty";
        }

        return error;
    }

    public String validAddress(String address) {
        String error = "";

        if (address == null || address.isEmpty()) {
            error = "Address cannot be empty";
        }

        return error;
    }

    public String validCity(String city) {
        String error = "";

        if (city == null || city.isEmpty()) {
            error = "City cannot be empty";
        }

        return error;
    }

    public String validProvince(String province) {
        String error = "";
// just a string of 2 letters
        if (province == null || province.isEmpty()) {
            error = "Province cannot be empty";
        }

        return error;
    }

    public String validPhone(String phoneNum) {
        String error = "";

        String regex = "\\d{10}";   // checks for 10 digit number

        Pattern phonePattern = Pattern.compile(regex);

        if (phoneNum == null || phoneNum.isEmpty()) {
            error = "Phone number cannot be empty";
        } else if (!phonePattern.matcher(phoneNum).matches()) {
            error = "Invalid phone number";
        }

        return error;
    }

    public String validDOB(String dob) {
        String error = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        if (dob == null || dob.isEmpty()) {
            error = "Date of birth cannot be empty";
        } else if (!dob.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            error = "Date of birth must be in the format YYYY-MM-DD";
        } else {
            formatter.setLenient(false);
            try {
                formatter.parse(dob);
            } catch (ParseException e) {
                error = "Invalid date provided";
            }
        }

        return error;
    }

    public String validGender(String gender) {
        String error = "";

        if (gender == null || gender.isEmpty()) {
            error = "Gender cannot be empty";
        }

        return error;
    }

}