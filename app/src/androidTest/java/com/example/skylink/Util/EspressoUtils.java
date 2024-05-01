package com.example.skylink.Util;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;

import com.example.skylink.R;

import org.hamcrest.Matchers;

public class EspressoUtils {


    public static void signUp(String fullName, String email, String password) {
        // Open Sign Up Screen.
        Espresso.onView(ViewMatchers.withId(R.id.tvSignInClick)).perform(click());

        // Fill in the form with provided values
        Espresso.onView(ViewMatchers.withId(R.id.etFullname))
                .perform(ViewActions.typeText(fullName), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etRePassword))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        // Close soft keyboard
        Espresso.closeSoftKeyboard();

        // Click sign up button
        Espresso.onView(ViewMatchers.withId(R.id.btnSignUp)).perform(click());
    }

    public static void updateUserInfo(String address, String city, String province, String phone, String dob, String gender) {
        // Second screen is displayed. Perform actions on update user info page

        // Enter Address
        onView(withId(R.id.etAddress)).perform(clearText());        // Clear the EditText field first

        Espresso.onView(ViewMatchers.withId(R.id.etAddress))
                .perform(ViewActions.typeText(address), ViewActions.closeSoftKeyboard());

        // Enter City
        onView(withId(R.id.etCity)).perform(clearText());        // Clear the EditText field first

        Espresso.onView(ViewMatchers.withId(R.id.etCity))
                .perform(ViewActions.typeText(city), ViewActions.closeSoftKeyboard());

        // Enter Province
        onView(withId(R.id.etProvince)).perform(clearText());        // Clear the EditText field first

        Espresso.onView(ViewMatchers.withId(R.id.etProvince))
                .perform(ViewActions.typeText(province), ViewActions.closeSoftKeyboard());

        // Enter Phone
        onView(withId(R.id.etPhone)).perform(clearText());        // Clear the EditText field first

        Espresso.onView(ViewMatchers.withId(R.id.etPhone))
                .perform(ViewActions.typeText(phone), ViewActions.closeSoftKeyboard());

        // Enter Dob
        onView(withId(R.id.etDoB)).perform(clearText());        // Clear the EditText field first

        Espresso.onView(ViewMatchers.withId(R.id.etDoB))
                .perform(ViewActions.typeText(dob), ViewActions.closeSoftKeyboard());

        // Enter Gender
        onView(withId(R.id.etGender)).perform(clearText());        // Clear the EditText field first

        Espresso.onView(ViewMatchers.withId(R.id.etGender))
                .perform(ViewActions.typeText(gender), ViewActions.closeSoftKeyboard());

        // Verify if the form is completely filled
        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit)).perform(ViewActions.click());
    }
    public static void verifyActivity(Class<? extends Activity> activityClass) {
        // Check if the activity is currently running
        try {
            Class.forName(activityClass.getName());
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Activity not found: " + activityClass.getName());
        }
    }


    public static void signIn(String email, String password) {
        // Enter username
        onView(withId(R.id.etEmail))
                .perform(typeText(email), closeSoftKeyboard());

        // Enter password
        onView(withId(R.id.etPassword))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.btnSignIn)).perform(click());
    }

    public static void clickLogOut() {
        // Open the navigation drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        // Click on the "Log Out" menu item
        onView(withId(R.id.nav_logout)).perform(ViewActions.click());
    }


    public static void performFlightSearch(String fromLocation, String toLocation, int year, int monthOfYear, int dayOfMonth) {
        // Select 'From' location
        onView(withId(R.id.autoComplete_from)).perform(click());
        onView(withText(fromLocation)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        // Select 'To' location
        onView(withId(R.id.autoComplete_to)).perform(click());
        onView(withText(toLocation)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        // Select departure date
        onView(withId(R.id.et_departure)).perform(click());
        onView(withClassName(Matchers.equalTo("android.widget.DatePicker")))
                .perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withText("OK")).perform(click());

        // Click on the search button
        onView(withId(R.id.searchBtn)).perform(click());
    }


    public static void selectSpinnerItem(int spinnerId, String itemText) {
        // Find the spinner view
        ViewInteraction spinnerInteraction = onView(withId(spinnerId));

        // Perform a tap action on the spinner to open its dropdown menu
        spinnerInteraction.perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER));

        // Click on the desired dropdown item
        onView(withText(itemText)).perform(click());
    }

    public static void performUserInfo(String title, String firstname, String lastname, String telephone, String email) {

        // Fill in passenger information
        onView(withId(R.id.etTitle)).perform(typeText(title));
        onView(withId(R.id.etFirstName)).perform(typeText(firstname));
        onView(withId(R.id.etLastName)).perform(typeText(lastname));
        onView(withId(R.id.etTelephoneNumber)).perform(typeText(telephone));
        onView(withId(R.id.etEmailAddress)).perform(typeText(email));

        // Close soft keyboard
        Espresso.closeSoftKeyboard();

        // Click submit
        onView(withId(R.id.submitBtn)).perform(ViewActions.click());
    }
}
