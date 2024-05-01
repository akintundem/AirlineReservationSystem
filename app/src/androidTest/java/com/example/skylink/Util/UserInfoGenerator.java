package com.example.skylink.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserInfoGenerator {
    private static final String[] FIRST_NAMES = {"James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph", "Charles", "Thomas"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
    private static final String[] CANADIAN_FIRST_NAMES = {"Toronto", "Montreal", "Vancouver", "Calgary", "Edmonton", "Ottawa", "Quebec City", "Winnipeg", "Hamilton", "Kitchener"};
    private static final String[] CANADIAN_PROVINCE_INITIALS = {"AB", "BC", "MB", "NB", "NL", "NS", "ON", "PE", "QC", "SK"};
    private static final String[] STREET_NAMES = {"Main", "Oak", "Maple", "Cedar", "Elm", "Pine", "Birch", "Willow", "Ash", "Cypress"};
    private static final String[] STREET_SUFFIXES = {"St", "Ave", "Blvd", "Rd", "Ln", "Ct", "Dr", "Pl", "Way"};

    private static final Random random = new Random();
    public static String[] generateUserInfo() {
        String[] userInfo = new String[9];
        userInfo[0] =generateRandomFullName(); // fullName
        userInfo[1] = generateRandomEmail(); // email
        userInfo[2] = generateRandomString(12); // password
        userInfo[3] = generateRandomStreetName(); // address
        userInfo[4] = generateRandomCities(); // city
        userInfo[5] = generateRandomProvince(); // province
        userInfo[6] = generateRandomPhoneNumber(); // phone
        userInfo[7] = generateRandomDateOfBirth(); // dob
        userInfo[8] = generateRandomGender(); // gender
        return userInfo;
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }
    private static String generateRandomStreetName() {
        String streetName = STREET_NAMES[random.nextInt(STREET_NAMES.length)];
        String streetSuffix = STREET_SUFFIXES[random.nextInt(STREET_SUFFIXES.length)];
        return streetName + " " + streetSuffix;
    }
    private static String generateRandomProvince() {
        String firstName = CANADIAN_PROVINCE_INITIALS[random.nextInt(CANADIAN_PROVINCE_INITIALS.length)];
        return firstName;
    }
    private static String generateRandomCities() {
        String firstName = CANADIAN_FIRST_NAMES[random.nextInt(CANADIAN_FIRST_NAMES.length)];
        return firstName;
    }

    private static String generateRandomFullName() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    private static String generateRandomEmail() {
        return generateRandomString(10) + "@" + generateRandomString(5) + ".com";
    }

    private static String generateRandomPhoneNumber() {
        return generateRandomNumericString(10);
    }

    private static String generateRandomNumericString(int length) {
        String digits = "0123456789";
        StringBuilder randomNumericString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomNumericString.append(digits.charAt(random.nextInt(digits.length())));
        }
        return randomNumericString.toString();
    }

    private static String generateRandomDateOfBirth() {
        // Generate random date of birth between 01/01/1950 and 01/01/2005
        int minYear = 1950;
        int maxYear = 2005;
        Random random = new Random();
        int year = random.nextInt(maxYear - minYear + 1) + minYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // Assuming all months have 28 days
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    private static String generateRandomGender() {
        String[] genders = {"M", "F", "Other"}; // Add more options if needed
        Random random = new Random();
        return genders[random.nextInt(genders.length)];
    }
}
