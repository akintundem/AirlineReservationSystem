package com.example.skylink;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.skylink.Util.EspressoUtils;
import com.example.skylink.Util.UserInfoGenerator;
import com.example.skylink.presentation.FlightSearching.FlightDisplay;
import com.example.skylink.presentation.FlightSearching.FlightSearchP;
import com.example.skylink.presentation.User_Auth.SignInActivity;
import com.example.skylink.presentation.User_Auth.UpdateUserProfileActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FlightSearchActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void testFlightSearch() {
        String fromLocation = "Toronto - YYZ";
        String toLocation = "Vancouver - YVR";
        int year = 2024;
        int monthOfYear = 5;
        int dayOfMonth = 7;

        // Fix the logic here.

        String[] userInfo = UserInfoGenerator.generateUserInfo();

        EspressoUtils.signUp(userInfo[0], userInfo[1], userInfo[2]);

        EspressoUtils.verifyActivity(UpdateUserProfileActivity.class);

        EspressoUtils.updateUserInfo(userInfo[3], userInfo[4], userInfo[5], userInfo[6], userInfo[7], userInfo[8]);

        EspressoUtils.clickLogOut();

        EspressoUtils.signIn(userInfo[1],userInfo[2]);

        EspressoUtils.verifyActivity(FlightSearchP.class);

        EspressoUtils.performFlightSearch(fromLocation, toLocation, year, monthOfYear, dayOfMonth);

        EspressoUtils.verifyActivity(FlightDisplay.class);

        onView(withId(R.id.flightListView)).check(matches(isDisplayed()));
    }

}
